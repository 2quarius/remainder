package com.example.trail.Calendar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.trail.MainActivity;
import com.example.trail.NewTask.SimpleTask.Task;
import com.example.trail.R;
import com.example.trail.Utility.Adapters.Cheeses;
import com.example.trail.Utility.Adapters.DynamicGridAdapter;
import com.example.trail.Utility.TimeUtil;
import com.example.trail.Utility.UIHelper.IOnBackPressed;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.yinglan.scrolllayout.ScrollLayout;

import org.askerov.dynamicgrid.DynamicGridView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CalendarFragment extends Fragment implements IOnBackPressed,
            CalendarView.OnCalendarSelectListener,
        CalendarView.OnYearChangeListener{
    private static final String MONTH = "月";
    private static final String DAY = "日";
    private static final String LUNAR = "今日";
    private static final String HAS = "点击或上滑打开";
    private static final String HAS_NOT = "本周暂无任务";
    private static final String TAG = MainActivity.class.getName();
    private List<Task> mTasks = new ArrayList<>();
    private TextView monthDay;
    private TextView year;
    private TextView lunar;
    private TextView currentDay;
    private CalendarLayout calendarLayout;
    public CalendarView calendar;
    private ScrollLayout mScrollLayout;
    private DynamicGridView gridView;
    private TextView text_foot;
    private ScrollLayout.OnScrollChangedListener mOnScrollChangedListener = new ScrollLayout.OnScrollChangedListener() {
        @Override
        public void onScrollProgressChanged(float currentProgress) {
            if (currentProgress >= 0) {
                float precent = 255 * currentProgress;
                if (precent > 255) {
                    precent = 255;
                } else if (precent < 0) {
                    precent = 0;
                }
                mScrollLayout.getBackground().setAlpha(255 - (int) precent);
            }
            if (text_foot.getVisibility() == View.VISIBLE)
                text_foot.setVisibility(View.GONE);
        }

        @Override
        public void onScrollFinished(ScrollLayout.Status currentStatus) {
            if (currentStatus.equals(ScrollLayout.Status.EXIT)) {
                text_foot.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onChildScroll(int top) {
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_calendar,container,false);
        initView(view);
        try {
            initData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public boolean onBackPressed() {
        if (gridView.isEditMode()) {
            gridView.stopEditMode();
            return true;
        } else {
            return false;
        }
    }
    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        //ui change
        lunar.setVisibility(View.VISIBLE);
        year.setVisibility(View.VISIBLE);
        monthDay.setText(constructMonthDay(calendar.getMonth(),calendar.getDay()));
        if(calendar.getDay()!=this.calendar.getCurDay()){
            this.lunar.setText("");
        }
        else {
            this.lunar.setText(LUNAR);
        }
        year.setText(String.valueOf(calendar.getYear()));
        //dynamic grid in scroll layout change
//        try {
//            loadWeekTask(calendar);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onYearChange(int year) {
        monthDay.setText(String.valueOf(year));
    }
    private void loadWeekTask(Calendar calendar) throws ParseException {
        Date start = TimeUtil.getFirstDayOfWeek(calendar);
        Date stop = TimeUtil.getFirstDayOfNextWeek(calendar);
        List<String> sunday = new ArrayList<>();
        List<String> monday = new ArrayList<>();
        List<String> tuesday = new ArrayList<>();
        List<String> wednesday = new ArrayList<>();
        List<String> thursday = new ArrayList<>();
        List<String> friday = new ArrayList<>();
        List<String> saturday = new ArrayList<>();
        int i = 0;
        for (Task task:mTasks){
            Date expire = task.getExpireTime();
            if (expire!=null&&expire.before(stop)&&expire.after(start)){
                java.util.Calendar cal = TimeUtil.Date2Cal(expire);
                String title = task.getTitle();
                switch (cal.get(java.util.Calendar.DAY_OF_WEEK)){
                    case java.util.Calendar.SUNDAY:
                        sunday.add(title);
                        i = Math.max(sunday.size(),i);
                        break;
                    case java.util.Calendar.MONDAY:
                        monday.add(title);
                        i = Math.max(monday.size(),i);
                        break;
                    case java.util.Calendar.TUESDAY:
                        tuesday.add(title);
                        i = Math.max(tuesday.size(),i);
                        break;
                    case java.util.Calendar.WEDNESDAY:
                        wednesday.add(title);
                        i = Math.max(wednesday.size(),i);
                        break;
                    case java.util.Calendar.THURSDAY:
                        thursday.add(title);
                        i = Math.max(thursday.size(),i);
                        break;
                    case java.util.Calendar.FRIDAY:
                        friday.add(title);
                        i = Math.max(friday.size(),i);
                        break;
                    case java.util.Calendar.SATURDAY:
                        saturday.add(title);;
                        i = Math.max(saturday.size(),i);
                        break;
                    default:
                        break;
                }
            }
        }
        List<String> all = new ArrayList<>();
        for (int j = 0; j < i;j++){
            try {
                all.add(monday.get(j));
            } catch (Exception e){
                all.add("null");
            }
            try {
                all.add(tuesday.get(j));
            } catch (Exception e){
                all.add("null");
            }
            try {
                all.add(wednesday.get(j));
            } catch (Exception e){
                all.add("null");
            }
            try {
                all.add(thursday.get(j));
            } catch (Exception e){
                all.add("null");
            }
            try {
                all.add(friday.get(j));
            } catch (Exception e){
                all.add("null");
            }
            try {
                all.add(saturday.get(j));
            } catch (Exception e){
                all.add("null");
            }
            try {
                all.add(sunday.get(j));
            } catch (Exception e){
                all.add("null");
            }
        }
        gridView.setAdapter(new DynamicGridAdapter(getContext(), all, getResources().getInteger(R.integer.column_count)));
        if (i == 0){
            text_foot.setText(HAS_NOT);
        }
        else {
            text_foot.setText(HAS);
        }
    }

    private static String constructMonthDay(int m,int d)
    {
        return String.format("%d%s%d%s", m, MONTH, d, DAY);
    }
    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }
    private void initView(View view) {
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.root);
        monthDay = (TextView) view.findViewById(R.id.tv_month_day);
        year = (TextView) view.findViewById(R.id.tv_year);
        lunar = (TextView) view.findViewById(R.id.tv_lunar);
        currentDay = (TextView) view.findViewById(R.id.tv_current_day);
        calendarLayout = (CalendarLayout) view.findViewById(R.id.calendar_layout);
        calendar = (CalendarView) view.findViewById(R.id.calendar_view);
        mScrollLayout = (ScrollLayout) view.findViewById(R.id.scroll_down_layout);
        text_foot = (TextView) view.findViewById(R.id.text_foot);
        gridView = (DynamicGridView) view.findViewById(R.id.dynamic_grid);
        gridView.setAdapter(new DynamicGridAdapter(getContext(),
                                                     new ArrayList<String>(Arrays.asList(Cheeses.sCheeseStrings)),
                                                     getResources().getInteger(R.integer.column_count)));
        monthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!calendarLayout.isExpand()) {
                    calendarLayout.expand();
                    return;
                }
                calendar.showYearSelectLayout(calendar.getCurYear());
                lunar.setVisibility(View.GONE);
                year.setVisibility(View.GONE);
                monthDay.setText(String.valueOf(calendar.getCurYear()));
            }
        });
        view.findViewById(R.id.fl_current).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.scrollToCurrent();
            }
        });
        calendar.setOnCalendarSelectListener(this);
        calendar.setOnYearChangeListener(this);

        mScrollLayout.setMinOffset(0);
        mScrollLayout.setMaxOffset(800);
        mScrollLayout.setExitOffset(480);
        mScrollLayout.setOnScrollChangedListener(mOnScrollChangedListener);
        mScrollLayout.setToExit();

        mScrollLayout.getBackground().setAlpha(0);
        gridView.setOnDragListener(new DynamicGridView.OnDragListener() {
            @Override
            public void onDragStarted(int position) {
                Log.d(TAG, "drag started at position " + position);
            }

            @Override
            public void onDragPositionsChanged(int oldPosition, int newPosition) {
                /**
                 * TODO:交换任务时间
                 */
                Log.d(TAG, String.format("drag item position changed from %d to %d", oldPosition, newPosition));
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                gridView.startEditMode(position);
                return true;
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), parent.getAdapter().getItem(position).toString(),
                               Toast.LENGTH_SHORT).show();
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScrollLayout.scrollToExit();
            }
        });
        text_foot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollLayout.setToOpen();
            }
        });
    }
    private void initData() throws ParseException {
        int year = calendar.getCurYear();
        int month = calendar.getCurMonth();
        Map<String, Calendar> map = new HashMap<>();
        map.put(getSchemeCalendar(year, month, 3, 0xFF40db25, "20").toString(),
                getSchemeCalendar(year, month, 3, 0xFF40db25, "20"));
        map.put(getSchemeCalendar(year, month, 6, 0xFFe69138, "33").toString(),
                getSchemeCalendar(year, month, 6, 0xFFe69138, "33"));
        map.put(getSchemeCalendar(year, month, 9, 0xFFdf1356, "25").toString(),
                getSchemeCalendar(year, month, 9, 0xFFdf1356, "25"));
        map.put(getSchemeCalendar(year, month, 13, 0xFFedc56d, "50").toString(),
                getSchemeCalendar(year, month, 13, 0xFFedc56d, "50"));
        map.put(getSchemeCalendar(year, month, 14, 0xFFedc56d, "80").toString(),
                getSchemeCalendar(year, month, 14, 0xFFedc56d, "80"));
        map.put(getSchemeCalendar(year, month, 15, 0xFFaacc44, "20").toString(),
                getSchemeCalendar(year, month, 15, 0xFFaacc44, "20"));
        map.put(getSchemeCalendar(year, month, 18, 0xFFbc13f0, "70").toString(),
                getSchemeCalendar(year, month, 18, 0xFFbc13f0, "70"));
        map.put(getSchemeCalendar(year, month, 25, 0xFF13acf0, "36").toString(),
                getSchemeCalendar(year, month, 25, 0xFF13acf0, "36"));
        map.put(getSchemeCalendar(year, month, 27, 0xFF13acf0, "95").toString(),
                getSchemeCalendar(year, month, 27, 0xFF13acf0, "95"));
        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        calendar.setSchemeDate(map);
//
//        Task t1 = new Task();
//        Calendar c1 = new Calendar();
//        c1.setYear(year);
//        c1.setMonth(month);
//        c1.setDay(3);
//        t1.setTitle("20");
//        t1.setExpireTime(TimeUtil.Calendar2Date(c1));
//        mTasks.add(t1);
//        Task t2 = new Task();
//        Calendar c2 = new Calendar();
//        c2.setYear(year);
//        c2.setMonth(month);
//        c2.setDay(6);
//        t2.setTitle("33");
//        t2.setExpireTime(TimeUtil.Calendar2Date(c2));
//        mTasks.add(t2);
//        Task t3 = new Task();
//        Calendar c3 = new Calendar();
//        c3.setYear(year);
//        c3.setMonth(month);
//        c3.setDay(9);
//        t3.setTitle("25");
//        t3.setExpireTime(TimeUtil.Calendar2Date(c3));
//        mTasks.add(t3);
//        Task t4 = new Task();
//        Calendar c4 = new Calendar();
//        c4.setYear(year);
//        c4.setMonth(month);
//        c4.setDay(13);
//        t4.setTitle("50");
//        t4.setExpireTime(TimeUtil.Calendar2Date(c4));
//        mTasks.add(t4);
//        Task t5 = new Task();
//        Calendar c5 = new Calendar();
//        c5.setYear(year);
//        c5.setMonth(month);
//        c5.setDay(14);
//        t5.setTitle("80");
//        t5.setExpireTime(TimeUtil.Calendar2Date(c5));
//        mTasks.add(t5);
//        Task t6 = new Task();
//        Calendar c6 = new Calendar();
//        c6.setYear(year);
//        c6.setMonth(month);
//        c6.setDay(15);
//        t6.setTitle("20");
//        t6.setExpireTime(TimeUtil.Calendar2Date(c6));
//        mTasks.add(t6);
//        Task t7 = new Task();
//        Calendar c7 = new Calendar();
//        c7.setYear(year);
//        c7.setMonth(month);
//        c7.setDay(18);
//        t7.setTitle("70");
//        t7.setExpireTime(TimeUtil.Calendar2Date(c7));
//        mTasks.add(t7);
//        Task t8 = new Task();
//        Calendar c8 = new Calendar();
//        c8.setYear(year);
//        c8.setMonth(month);
//        c8.setDay(25);
//        t8.setTitle("36");
//        t8.setExpireTime(TimeUtil.Calendar2Date(c8));
//        mTasks.add(t8);
//        Task t9 = new Task();
//        Calendar c9 = new Calendar();
//        c9.setYear(year);
//        c9.setMonth(month);
//        c9.setDay(27);
//        t9.setTitle("95");
//        t9.setExpireTime(TimeUtil.Calendar2Date(c9));
//        mTasks.add(t9);
        this.year.setText(String.valueOf(calendar.getCurYear()));
        monthDay.setText(constructMonthDay(calendar.getCurMonth(),calendar.getCurDay()));
        lunar.setText(LUNAR);
        currentDay.setText(String.valueOf(calendar.getCurDay()));
//        try {
//            loadWeekTask(calendar.getCurrentWeekCalendars().get(0));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }

}
