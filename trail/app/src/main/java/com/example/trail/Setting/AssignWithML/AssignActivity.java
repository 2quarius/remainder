package com.example.trail.Setting.AssignWithML;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.trail.MainActivity;
import com.example.trail.NewTask.Collection.TaskCollector;
import com.example.trail.NewTask.SimpleTask.Task;
import com.example.trail.R;
import com.example.trail.Utility.Utils.TimeUtil;
import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import solid.ren.skinlibrary.base.SkinBaseActivity;

public class AssignActivity extends SkinBaseActivity implements CalendarPickerController {
    private LinearLayout back;
    private TextView title;
    private AgendaCalendarView mAgendaCalendarView;
    private List<AssignHelper> helpers = new ArrayList<>();
    private List<CalendarEvent> eventList = new ArrayList<>();
    private Handler uiHandler = new Handler(){
      @Override
      public void handleMessage(Message msg){
          if (mAgendaCalendarView!=null){
              Calendar minDate = Calendar.getInstance();
              Calendar maxDate = Calendar.getInstance();

              minDate.add(Calendar.MONTH, -2);
              minDate.set(Calendar.DAY_OF_MONTH, 1);
              maxDate.add(Calendar.YEAR, 1);

              mAgendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), AssignActivity.this);
          }
      }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign);
        back = findViewById(R.id.back);
        title = findViewById(R.id.title);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AssignActivity.this.finish();
            }
        });
        title.setText(getResources().getString(R.string.assign));
        mAgendaCalendarView = findViewById(R.id.agenda_calendar_view);
        mockList(eventList);
    }
    private void mockList(final List<CalendarEvent> eventList) {
        try {
            for (TaskCollector taskCollector : MainActivity.taskCollectors) {
                for (Task task : taskCollector.getTasks()) {
                    if (task.getExpireTime() != null && task.getRemindTime() == null) {
                        AssignHelper helper = new AssignHelper(task, null);
                        helper.createInlineSource(MainActivity.taskCollectors);
                        helpers.add(helper);
                    } else if (task.getExpireTime() != null && task.getRemindTime() != null && task.getExpireTime().after(task.getRemindTime())) {
                        Calendar start = TimeUtil.Date2Cal(task.getRemindTime());
                        Calendar end = TimeUtil.Date2Cal(task.getExpireTime());
                        int color;
                        switch (task.getPriority()){
                            case NONE:
                                color = R.color.event_color_04;
                                break;
                            case LITTLE:
                                color = R.color.event_color_03;
                                break;
                            case MIDDLE:
                                color = R.color.event_color_02;
                                break;
                            case EUGEN:
                                color = R.color.event_color_01;
                                break;
                            default:
                                color = R.color.event_color_04;
                                break;
                        }
                        BaseCalendarEvent event = new BaseCalendarEvent(task.getTitle(),
                                                                        task.getDescription(),
                                                                        (task.getLocation()==null?"":task.getLocation().getPlace()),
                                                                        color, start,end,false);
                        eventList.add(event);
                    }
                }
            }
            for (final AssignHelper helper : helpers) {
                helper.setHelperListener(new AssignHelper.HelperListener() {
                    @Override
                    public void onPredict(String predictTimeInMills) {
                        Calendar minDate = Calendar.getInstance();
                        Calendar maxDate = Calendar.getInstance();

                        minDate.add(Calendar.MONTH, -2);
                        minDate.set(Calendar.DAY_OF_MONTH, 1);
                        maxDate.add(Calendar.YEAR, 1);

                        Task task = helper.getMTask();
                        if (predictTimeInMills.length() == 13) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(Long.valueOf(predictTimeInMills));
                            Calendar end = TimeUtil.Date2Cal(task.getExpireTime());
                            int color;
                            switch (task.getPriority()){
                                case NONE:
                                    color = R.color.event_color_04;
                                    break;
                                case LITTLE:
                                    color = R.color.event_color_03;
                                    break;
                                case MIDDLE:
                                    color = R.color.event_color_02;
                                    break;
                                case EUGEN:
                                    color = R.color.event_color_01;
                                    break;
                                default:
                                    color = R.color.event_color_04;
                                    break;
                            }
                            BaseCalendarEvent event = new BaseCalendarEvent(task.getTitle(),
                                                                            task.getDescription(),
                                                                            (task.getLocation()==null?"":task.getLocation().getPlace()),
                                                                            color, calendar,end,false);
                            eventList.add(event);
                            uiHandler.sendMessage(new Message());
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDaySelected(DayItem dayItem) {
    }

    @Override
    public void onEventSelected(CalendarEvent event) {
    }

    @Override
    public void onScrollToDate(Calendar calendar) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));
        }
    }
}
