package com.example.trail.Lists;


import android.content.Context;
import android.content.res.Resources;
import android.icu.util.RangeValueIterator;

import android.graphics.Color;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trail.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;



public class ListsFragment extends Fragment {
    private static ContentAdapter adapter;
    private static List<String> finished = new ArrayList<>();
    private static List<String> titles=new ArrayList<>();
    private static List<String> descriptions=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        helper.attachToRecyclerView(recyclerView);
        return recyclerView;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBox;
        public TextView name;
        public TextView description;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_lists, parent, false));
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            name = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("click");
                }
            });
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (compoundButton.isPressed()) {
                        if (isChecked) {
                            toBottom(titles, getAdapterPosition());
                            toBottom(descriptions, getAdapterPosition());
                            finished.set(getAdapterPosition(), String.valueOf(isChecked));
                            toBottom(finished, getAdapterPosition());
                        } else {
                            toTop(titles, getAdapterPosition());
                            toTop(descriptions, getAdapterPosition());
                            finished.set(getAdapterPosition(), String.valueOf(isChecked));
                            toTop(finished, getAdapterPosition());
                        }
                        Handler handler = new Handler();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
                private void toBottom(List<String> lists,int position){
                    if (position<lists.size()){
                        String tmp = lists.remove(position);
                        lists.add(tmp);

                    }
                }
                private void toTop(List<String> lists,int position){
                    if (position<lists.size()){
                        String tmp = lists.remove(position);
                        lists.add(0,tmp);
                    }
                }
            });
        }
    }
    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {

        public ContentAdapter(Context context) {
            titles.clear();
            titles.add("Palais Garnie");
            titles.add("Piazza del Duomo");
            titles.add("Manhattan");
            titles.add("Senso");
            titles.add("Sultan");
            titles.add("china");
            descriptions.clear();
            descriptions.add("The Palais Garnier which locates in Paris was built from 1861 for\n" +
                    "        the Paris Opera");
            descriptions.add("Piazza del Duomo the cathedral which locates in Florence.");
            descriptions.add("Manhattan is the district in New York City");
            descriptions.add("Senso-ji is the Shrine locates in Asakusa Toky");
            descriptions.add("Sultan Ahmed Mosque is mosque locates in Istabul, Turkey");
            descriptions.add("Senso-ji is the Shrine locates in Asakusa Toky");
            finished.clear();
            finished.add("false");
            finished.add("false");
            finished.add("false");
            finished.add("false");
            finished.add("false");
            finished.add("false");
            // finish=resources.getStringArray(R.array.)
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.checkBox.setChecked(Boolean.valueOf(finished.get(position)));
            holder.name.setText(titles.get(position));
            holder.description.setText(descriptions.get(position));

        }

        @Override
        public int getItemCount() {
            return titles.size();
        }
    }

    ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFrlg = 0;
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager){
                //System.out.println("grid");
                dragFrlg = ItemTouchHelper.UP|ItemTouchHelper.DOWN|ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
            }else if(recyclerView.getLayoutManager() instanceof LinearLayoutManager){
                //System.out.println("grid");
                dragFrlg = ItemTouchHelper.UP|ItemTouchHelper.DOWN;
            }
            return makeMovementFlags(dragFrlg,0);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            //滑动事件  下面注释的代码，滑动后数据和条目错乱，被舍弃
//            Collections.swap(datas,viewHolder.getAdapterPosition(),target.getAdapterPosition());
//            ap.notifyItemMoved(viewHolder.getAdapterPosition(),target.getAdapterPosition());
            //得到当拖拽的viewHolder的Position
            int fromPosition = viewHolder.getAdapterPosition();
            //拿到当前拖拽到的item的viewHolder
            int toPosition = target.getAdapterPosition();
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(titles, i, i + 1);
                    Collections.swap(descriptions, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(titles, i, i - 1);
                    Collections.swap(descriptions, i, i - 1);
                }
            }
            adapter.notifyItemMoved(fromPosition, toPosition);
            adapter.notifyItemRangeChanged(Math.min(fromPosition, toPosition), Math.abs(fromPosition - toPosition) +1);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            //侧滑删除可以使用；
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }
        /**
         * 长按选中Item的时候开始调用
         * 长按高亮
         * @param viewHolder
         * @param actionState
         */
        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                viewHolder.itemView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        /**
         * 手指松开的时候还原高亮
         * @param recyclerView
         * @param viewHolder
         */
        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            viewHolder.itemView.setBackgroundColor(0);
            adapter.notifyDataSetChanged();  //完成拖动后刷新适配器，这样拖动后删除就不会错乱
        }
    });
}
