package com.example.trail.Calendar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trail.R;

import java.util.HashMap;
import java.util.Map;


public class CalendarFragment extends Fragment implements
            CalendarView.OnCalendarSelectListener,
        CalendarView.OnYearChangeListener{
    private static final String MONTH = "月";
    private static final String DAY = "日";
    private static final String LUNAR = "今日";
    public CalendarView calendar;
    public TextView monthDay;
    public TextView year;
    public TextView lunar;
    public TextView currentDay;
    public CalendarLayout calendarLayout;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_calendar,container,false);
        calendar = (CalendarView) view.findViewById(R.id.calendar_view);
        monthDay = (TextView) view.findViewById(R.id.tv_month_day);
        year = (TextView) view.findViewById(R.id.tv_year);
        lunar = (TextView) view.findViewById(R.id.tv_lunar);
        currentDay = (TextView) view.findViewById(R.id.tv_current_day);
        initData();
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
        calendarLayout = (CalendarLayout) view.findViewById(R.id.calendar_layout);
        calendar.setOnCalendarSelectListener(this);
        calendar.setOnYearChangeListener(this);
        return view;
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

}
