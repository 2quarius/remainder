package com.example.trail.NewTask;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;

import com.example.trail.NewTask.task.MonthTasks;
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

    String theyear;
    String themonth;
    String thebeginYear;
    String thebeginMonth;
    String theendYear;
    String theendMonth;
    ArrayAdapter<CharSequence> dayadapter28;
    ArrayAdapter<CharSequence> dayadapter29;
    ArrayAdapter<CharSequence> dayadapter30;
    ArrayAdapter<CharSequence> dayadapter31;

    private void changeDay() {
        switch (themonth) {
            case "1":
            case "3":
            case "5":
            case "7":
            case "8":
            case "10":
            case "12":
                day.setAdapter(dayadapter31);
                break;
            case "2":
                int theYear = Integer.parseInt(theyear);
                if ((theYear%4==0&&theYear%100!=0)||theYear%400==0) {
                    day.setAdapter(dayadapter29);
                }
                else{
                    day.setAdapter(dayadapter28);
                }
                break;
            default:
                day.setAdapter(dayadapter30);
                break;
        }
    }

    private void changebeginDay() {
        switch (thebeginMonth) {
            case "1":
            case "3":
            case "5":
            case "7":
            case "8":
            case "10":
            case "12":
                beginDay.setAdapter(dayadapter31);
                break;
            case "2":
                int theYear = Integer.parseInt(thebeginYear);
                if ((theYear%4==0&&theYear%100!=0)||theYear%400==0) {
                    beginDay.setAdapter(dayadapter29);
                }
                else{
                    beginDay.setAdapter(dayadapter28);
                }
                break;
            default:
                beginDay.setAdapter(dayadapter30);
                break;
        }
    }

    private void changeendDay() {
        switch (theendMonth) {
            case "1":
            case "3":
            case "5":
            case "7":
            case "8":
            case "10":
            case "12":
                endDay.setAdapter(dayadapter31);
                break;
            case "2":
                int theYear = Integer.parseInt(theendYear);
                if ((theYear%4==0&&theYear%100!=0)||theYear%400==0) {
                    endDay.setAdapter(dayadapter29);
                }
                else{
                    endDay.setAdapter(dayadapter28);
                }
                break;
            default:
                endDay.setAdapter(dayadapter30);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newtask);

        dayadapter28 = ArrayAdapter.createFromResource(this, R.array.day28, android.R.layout.simple_spinner_item);
        dayadapter28.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dayadapter29 = ArrayAdapter.createFromResource(this, R.array.day29, android.R.layout.simple_spinner_item);
        dayadapter29.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dayadapter30 = ArrayAdapter.createFromResource(this, R.array.day30, android.R.layout.simple_spinner_item);
        dayadapter30.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dayadapter31 = ArrayAdapter.createFromResource(this, R.array.day31, android.R.layout.simple_spinner_item);
        dayadapter31.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        year = findViewById(R.id.Year);
        month = findViewById(R.id.Month);
        day = findViewById(R.id.Day);
        hour = findViewById(R.id.Hour);
        minute = findViewById(R.id.Minute);
        theyear = (String) year.getSelectedItem();
        themonth = (String) month.getSelectedItem();
        changeDay();
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                theyear = (String) year.getSelectedItem();
                changeDay();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                themonth = (String) month.getSelectedItem();
                changeDay();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        beginYear = findViewById(R.id.BeginYear);
        beginMonth = findViewById(R.id.BeginMonth);
        beginDay = findViewById(R.id.BeginDay);
        beginHour = findViewById(R.id.BeginHour);
        beginMinute = findViewById(R.id.BeginMinute);
        thebeginYear = (String) beginYear.getSelectedItem();
        thebeginMonth = (String) beginMonth.getSelectedItem();
        changebeginDay();

        beginYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                thebeginYear = (String) beginYear.getSelectedItem();
                changebeginDay();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        beginMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                thebeginMonth = (String) beginMonth.getSelectedItem();
                changebeginDay();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        endYear = findViewById(R.id.EndYear);
        endMonth = findViewById(R.id.EndMonth);
        endDay = findViewById(R.id.EndDay);
        endHour = findViewById(R.id.EndHour);
        endMinute = findViewById(R.id.EndMinute);
        theendYear = (String) endYear.getSelectedItem();
        theendMonth = (String) endMonth.getSelectedItem();
        changeendDay();

        endYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                theendYear = (String) endYear.getSelectedItem();
                changeendDay();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        endMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                theendMonth = (String) endMonth.getSelectedItem();
                changeendDay();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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