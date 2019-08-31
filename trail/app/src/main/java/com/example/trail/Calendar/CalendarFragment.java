package com.example.trail.Calendar;

import android.content.Context;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trail.MainActivity;
import com.example.trail.NewTask.SimpleTask.Task;
import com.example.trail.R;
import com.example.trail.Utility.Adapters.ExampleSectionExpandableDataProvider;
import com.example.trail.Utility.Adapters.ExpandableDraggableWithSectionExampleAdapter;
import com.example.trail.Utility.Utils.TimeUtil;
import com.h6ah4i.android.widget.advrecyclerview.animator.DraggableItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.decoration.ItemShadowDecorator;
import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.yinglan.scrolllayout.ScrollLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import solid.ren.skinlibrary.base.SkinBaseFragment;


public class CalendarFragment extends SkinBaseFragment implements
            CalendarView.OnCalendarSelectListener,
        CalendarView.OnYearChangeListener, RecyclerViewExpandableItemManager.OnGroupCollapseListener,
        RecyclerViewExpandableItemManager.OnGroupExpandListener {
    //used in data provider, to show toast
    public static Context mContext;

    private static final String MONTH = "月";
    private static final String DAY = "日";
    private static final String LUNAR = "今日";
    private static final String HAS = "点击或上滑打开";
    private static final String HAS_NOT = "今日暂无任务";
    private List<Task> mTasks = new ArrayList<>();

    private Parcelable eimSavedState;
    private static final String SAVED_STATE_EXPANDABLE_ITEM_MANAGER = "RecyclerViewExpandableItemManager";
    private static final boolean ALLOW_ITEMS_MOVE_ACROSS_SECTIONS = true; // Set this flag "true" to change draggable item range

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.Adapter mWrappedAdapter;
    private RecyclerViewExpandableItemManager mRecyclerViewExpandableItemManager;
    private RecyclerViewDragDropManager mRecyclerViewDragDropManager;

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
    public CalendarFragment(){
        super();
    }
    public void refresh(List<Task> tasks) {
        mTasks = tasks;
        initData();
        flashAdapter(TimeUtil.Date2Calendar(new Date()));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_calendar,container,false);
        initView(view);
        mContext = getContext();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //noinspection ConstantConditions
        mRecyclerView = getView().findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(requireContext());

        eimSavedState = (savedInstanceState != null) ? savedInstanceState.getParcelable(SAVED_STATE_EXPANDABLE_ITEM_MANAGER) : null;
    }

    @Override
    public void onStart() {
        super.onStart();
        mTasks = ((MainActivity)getActivity()).getTasks();
        initData();
        Calendar cal = TimeUtil.Date2Calendar(new Date());
        flashAdapter(cal);
    }

    @Override
    public void onDestroyView() {
        if (mRecyclerViewDragDropManager != null) {
            mRecyclerViewDragDropManager.release();
            mRecyclerViewDragDropManager = null;
        }

        if (mRecyclerViewExpandableItemManager != null) {
            mRecyclerViewExpandableItemManager.release();
            mRecyclerViewExpandableItemManager = null;
        }

        if (mRecyclerView != null) {
            mRecyclerView.setItemAnimator(null);
            mRecyclerView.setAdapter(null);
            mRecyclerView = null;
        }

        if (mWrappedAdapter != null) {
            WrapperAdapterUtils.releaseAll(mWrappedAdapter);
            mWrappedAdapter = null;
        }
        mAdapter = null;
        mLayoutManager = null;

        super.onDestroyView();
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        //ui change
        mScrollLayout.setVisibility(View.VISIBLE);
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
        //advanced recyclerview in scroll layout change
        flashAdapter(calendar);
    }

    private void flashAdapter(Calendar calendar) {
        List<Task> tasks = new ArrayList<>();
        for (Task task:mTasks){
            try {

                if (task.getExpireTime().after(TimeUtil.Calendar2Date(calendar))&&
                        task.getExpireTime().before(TimeUtil.tomorrow(TimeUtil.Calendar2Date(calendar)))){
                    tasks.add(task);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        if (tasks.size()>0){
            text_foot.setText(HAS);
        }
        else {
            text_foot.setText(HAS_NOT);
        }
        setAdapters(tasks);
    }

    @Override
    public void onYearChange(int year) {
        monthDay.setText(String.valueOf(year));
    }

    @Override
    public void onGroupCollapse(int groupPosition, boolean fromUser, Object payload) {
    }

    @Override
    public void onGroupExpand(int groupPosition, boolean fromUser, Object payload) {
        if (fromUser) {
            adjustScrollPositionOnGroupExpanded(groupPosition);
        }
    }
    protected static String constructMonthDay(int m, int d)
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

        monthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!calendarLayout.isExpand()) {
                    calendarLayout.expand();
                    return;
                }
                mScrollLayout.setVisibility(View.INVISIBLE);
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
    private void initData() {
        Map<String, Calendar> map = new HashMap<>();
        for (Task task:mTasks){
            if (task.getExpireTime()==null){
                continue;
            }
            java.util.Calendar calendar = TimeUtil.Date2Cal(task.getExpireTime());
            int color;
            switch (task.getPriority()){
                case NONE:
                    color = 0xFFaacc44;
                    break;
                case LITTLE:
                    color = 0xFFedc56d;
                    break;
                case MIDDLE:
                    color = 0xFF13acf0;
                    break;
                case EUGEN:
                    color = 0xFFdf1356;
                    break;
                default:
                    color = 0xFFbc13f0;
            }
            map.put(getSchemeCalendar(calendar.get(java.util.Calendar.YEAR),
                                      calendar.get(java.util.Calendar.MONTH)+1,
                                      calendar.get(java.util.Calendar.DAY_OF_MONTH),color,task.getTitle()).toString(),
                    getSchemeCalendar(calendar.get(java.util.Calendar.YEAR),
                                      calendar.get(java.util.Calendar.MONTH)+1,
                                      calendar.get(java.util.Calendar.DAY_OF_MONTH),color,task.getTitle()));
        }
        //此方法在巨大的数据量上不影响遍历性能，推荐使用
        calendar.setSchemeDate(map);

        this.year.setText(String.valueOf(calendar.getCurYear()));
        monthDay.setText(constructMonthDay(calendar.getCurMonth(),calendar.getCurDay()));
        lunar.setText(LUNAR);
        currentDay.setText(String.valueOf(calendar.getCurDay()));
    }
    private void setAdapters(List<Task> tasks) {
        mRecyclerViewExpandableItemManager = new RecyclerViewExpandableItemManager(eimSavedState);
        mRecyclerViewExpandableItemManager.setOnGroupExpandListener(this);
        mRecyclerViewExpandableItemManager.setOnGroupCollapseListener(this);

        // drag & drop manager
        mRecyclerViewDragDropManager = new RecyclerViewDragDropManager();
        mRecyclerViewDragDropManager.setDraggingItemShadowDrawable(
                (NinePatchDrawable) ContextCompat.getDrawable(requireContext(), R.drawable.material_shadow_z3));

        //adapter
        ExampleSectionExpandableDataProvider mDataProvider = new ExampleSectionExpandableDataProvider(tasks);
        final ExpandableDraggableWithSectionExampleAdapter myItemAdapter = new ExpandableDraggableWithSectionExampleAdapter(mDataProvider);

        mAdapter = myItemAdapter;

        mWrappedAdapter = mRecyclerViewExpandableItemManager.createWrappedAdapter(myItemAdapter);       // wrap for expanding
        mWrappedAdapter = mRecyclerViewDragDropManager.createWrappedAdapter(mWrappedAdapter);           // wrap for dragging

        mRecyclerViewDragDropManager.setCheckCanDropEnabled(ALLOW_ITEMS_MOVE_ACROSS_SECTIONS);
        myItemAdapter.setAllowItemsMoveAcrossSections(ALLOW_ITEMS_MOVE_ACROSS_SECTIONS);

        final GeneralItemAnimator animator = new DraggableItemAnimator();

        // Change animations are enabled by default since support-v7-recyclerview v22.
        // Need to disable them when using animation indicator.
        animator.setSupportsChangeAnimations(false);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mWrappedAdapter);  // requires *wrapped* adapter
        mRecyclerView.setItemAnimator(animator);
        mRecyclerView.setHasFixedSize(false);

        // additional decorations
        //noinspection StatementWithEmptyBody
        if (supportsViewElevation()) {
            // Lollipop or later has native drop shadow feature. ItemShadowDecorator is not required.
        } else {
            mRecyclerView.addItemDecoration(new ItemShadowDecorator((NinePatchDrawable) ContextCompat.getDrawable(requireContext(), R.drawable.material_shadow_z1)));
        }
        mRecyclerView.addItemDecoration(new SimpleListDividerDecorator(ContextCompat.getDrawable(requireContext(), R.drawable.list_divider_h), true));


        // NOTE:
        // The initialization order is very important! This order determines the priority of touch event handling.
        //
        // priority: DragAndDrop > ExpandableItem
        mRecyclerViewDragDropManager.attachRecyclerView(mRecyclerView);
        mRecyclerViewExpandableItemManager.attachRecyclerView(mRecyclerView);
    }

    private void adjustScrollPositionOnGroupExpanded(int groupPosition) {
        int childItemHeight = getActivity().getResources().getDimensionPixelSize(R.dimen.list_item_height);
        int topMargin = (int) (getActivity().getResources().getDisplayMetrics().density * 16); // top-spacing: 16dp
        int bottomMargin = topMargin; // bottom-spacing: 16dp

        mRecyclerViewExpandableItemManager.scrollToGroup(groupPosition, childItemHeight, topMargin, bottomMargin);
    }

    private boolean supportsViewElevation() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
    }
}
