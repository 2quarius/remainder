package com.example.trail.Calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.trail.R;
import com.example.trail.Utility.Adapters.ListViewAdapter;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.yinglan.scrolllayout.ScrollLayout;

import java.util.HashMap;
import java.util.Map;


public class CalendarFragment extends Fragment implements
            CalendarView.OnCalendarSelectListener,
        CalendarView.OnYearChangeListener{
    private static final String MONTH = "月";
    private static final String DAY = "日";
    private static final String LUNAR = "今日";
    private TextView monthDay;
    private TextView year;
    private TextView lunar;
    private TextView currentDay;
    private CalendarLayout calendarLayout;
    public CalendarView calendar;
    private ScrollLayout mScrollLayout;
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
        initData();
        return view;
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
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(new ListViewAdapter(getContext()));

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
        mScrollLayout.setExitOffset(500);
        mScrollLayout.setToOpen();
        mScrollLayout.setIsSupportExit(true);
        mScrollLayout.setAllowHorizontalScroll(true);
        mScrollLayout.setOnScrollChangedListener(mOnScrollChangedListener);
//        mScrollLayout.setToExit();

//        mScrollLayout.getBackground().setAlpha(0);
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

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
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
    }

    @Override
    public void onYearChange(int year) {
        monthDay.setText(String.valueOf(year));
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
    private void initData()
    {
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
        this.year.setText(String.valueOf(calendar.getCurYear()));
        monthDay.setText(constructMonthDay(calendar.getCurMonth(),calendar.getCurDay()));
        lunar.setText(LUNAR);
        currentDay.setText(String.valueOf(calendar.getCurDay()));
    }

}
