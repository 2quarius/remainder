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

public class CalendarFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements
            CalendarView.OnCalendarSelectListener,
            CalendarView.OnYearChangeListener {
        private static final String MONTH = "月";
        private static final String DAY = "日";
        private static final String LUNAR = "今日";
        private static String constructMonthDay(int m,int d)
        {
            return String.format("%d%s%d%s", m, MONTH, d, DAY);
        }
        public CalendarView calendar;
        public TextView monthDay;
        public TextView year;
        public TextView lunar;
        public TextView currentDay;
        public CalendarLayout calendarLayout;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_calendar, parent, false));
            calendar = (CalendarView) itemView.findViewById(R.id.calendar_view);
            monthDay = (TextView) itemView.findViewById(R.id.tv_month_day);
            year = (TextView) itemView.findViewById(R.id.tv_year);
            lunar = (TextView) itemView.findViewById(R.id.tv_lunar);
            currentDay = (TextView) itemView.findViewById(R.id.tv_current_day);
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
            itemView.findViewById(R.id.fl_current).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    calendar.scrollToCurrent();
                }
            });
            calendarLayout = (CalendarLayout) itemView.findViewById(R.id.calendar_layout);
            calendar.setOnCalendarSelectListener(this);
            calendar.setOnYearChangeListener(this);
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

    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of Card in RecyclerView.
        private final int LENGTH=1;

        private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
            Calendar calendar = new Calendar();
            calendar.setYear(year);
            calendar.setMonth(month);
            calendar.setDay(day);
            calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
            calendar.setScheme(text);
            return calendar;
        }

        public ContentAdapter(Context context) {
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            int year = holder.calendar.getCurYear();
            int month = holder.calendar.getCurMonth();
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
            holder.calendar.setSchemeDate(map);
            holder.year.setText(String.valueOf(holder.calendar.getCurYear()));
            holder.monthDay.setText(ViewHolder.constructMonthDay(holder.calendar.getCurMonth(),holder.calendar.getCurDay()));
            holder.lunar.setText(ViewHolder.LUNAR);
            holder.currentDay.setText(String.valueOf(holder.calendar.getCurDay()));

        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }
}
