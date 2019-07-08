package com.example.trail.NewTask;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.trail.EventsObject.Event;
import com.example.trail.NewTask.task.DayTasks;
import com.example.trail.NewTask.task.MonthTasks;
import com.example.trail.NewTask.task.Task;
import com.example.trail.NewTask.task.Time;
import com.example.trail.R;

public class NewTaskActivity extends Activity {
    private RadioGroup time;
    private RadioGroup importance;
    private RadioButton timePoint;
    private RadioButton timeExtention;
    //时刻参数
    private Spinner year;
    private Spinner month;
    private Spinner day;
    private Spinner hour;
    private Spinner minute;
    //时段开始参数
    private Spinner beginYear;
    private Spinner beginMonth;
    private Spinner beginDay;
    private Spinner beginHour;
    private Spinner beginMinute;
    //时段结束参数
    private Spinner endYear;
    private Spinner endMonth;
    private Spinner endDay;
    private Spinner endHour;
    private Spinner endMinute;
    //重复
    private Spinner recycling;
    //提前时间
    private EditText edAdvanceTime;
    //place
    private Button btnPlace;
    //重要度
    private RadioButton imFirst;
    private RadioButton imSecond;
    private RadioButton imThird;
    private RadioButton imForth;
    private RadioButton imFifth;
    //类别
    private Spinner type;
    //标题
    private EditText title;
    //内容
    private EditText content;
    //保存
    private Button save;

    public static MonthTasks monthTasks;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newtask);

        //初始化
        content=findViewById(R.id.TaskContent);
        title=findViewById(R.id.TaskTital);
        type=findViewById(R.id.type);

        time=findViewById(R.id.time);
        importance=findViewById(R.id.improtance);
        timePoint=findViewById(R.id.timepoint);
        timeExtention=findViewById(R.id.timeextention);

        recycling=findViewById(R.id.recycling);
        edAdvanceTime=findViewById(R.id.AdvanceTime);
        btnPlace=findViewById(R.id.place);

        year=findViewById(R.id.Year);
        month=findViewById(R.id.Month);
        day=findViewById(R.id.Day);
        hour=findViewById(R.id.Hour);
        minute=findViewById(R.id.Minute);

        beginYear=findViewById(R.id.BeginYear);
        beginMonth=findViewById(R.id.BeginMonth);
        beginDay=findViewById(R.id.BeginDay);
        beginHour=findViewById(R.id.BeginHour);
        beginMinute=findViewById(R.id.BeginMinute);

        endYear=findViewById(R.id.EndYear);
        endMonth=findViewById(R.id.EndMonth);
        endDay=findViewById(R.id.EndDay);
        endHour=findViewById(R.id.EndHour);
        endMinute=findViewById(R.id.EndMinute);

        imFirst=findViewById(R.id.first);
        imSecond=findViewById(R.id.second);
        imThird=findViewById(R.id.third);
        imForth=findViewById(R.id.forth);
        imFifth=findViewById(R.id.fifth);

        save=findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Task task=new Task();
                task.setContent(content.toString());
                task.setTital(content.toString());
                task.setDone(0);
                task.setRecycling(recycling.getId());
                task.setTimeAdvance(Integer.parseInt(edAdvanceTime.toString()));

                //设置时间
                time.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if(i==timePoint.getId()){//时间点
                            Time startTime=timeFormat(year,month,day,hour,minute);
                            Time endTime=startTime;
                            task.setStartTime(startTime);
                            task.setEndTime(endTime);
                        }else {//时间段
                            Time startTime=timeFormat(beginYear,beginMonth,beginDay,beginHour,beginMinute);
                            Time endTime=timeFormat(endYear,endMonth,endDay,endHour,endMinute);
                            task.setStartTime(startTime);
                            task.setEndTime(endTime);
                        }
                    }
                });

                //设置重要性
                importance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if(i==imFirst.getId()){
                            task.setPriority(1);
                        }if(i==imSecond.getId()){
                            task.setPriority(2);
                        }if(i==imThird.getId()){
                            task.setPriority(3);
                        }if(i==imForth.getId()){
                            task.setPriority(4);
                        }if(i==imFifth.getId()){
                            task.setPriority(5);
                        }else {
                            task.setPriority(3);//未勾选重要性，设为中间值3
                        }
                    }
                });
                int day=Integer.parseInt(task.getStartTime().getDay());
                monthTasks=new MonthTasks();
                monthTasks.addTask(day,task);
            }
        });

    }


    //格式化时间
    private Time timeFormat(Spinner year,Spinner month,Spinner day,Spinner hour,Spinner minute){
        String sYear=year.getSelectedItem().toString();
        String sMonth=month.getSelectedItem().toString();
        String sDay=day.getSelectedItem().toString();
        String sHour=hour.getSelectedItem().toString();
        String sMinute=minute.getSelectedItem().toString();

        Time time=new Time();
        time.setYear(sYear);
        time.setMonth(sMonth);
        time.setDay(sDay);
        time.setHour(sHour);
        time.setMinute(sMinute);

        return time;
    }


}
