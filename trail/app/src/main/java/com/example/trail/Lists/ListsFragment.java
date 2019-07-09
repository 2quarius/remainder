package com.example.trail.Lists;


import android.content.Context;
import android.content.res.Resources;
import android.icu.util.RangeValueIterator;

import android.graphics.Color;

import android.os.Bundle;
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
    private ContentAdapter adapter;
    private ContentAdapterF adapterF;
    private List<String> upT=new ArrayList<String>();
    private List<String> upDesc=new ArrayList<String>();
//    private String[] mPlaces;
//    private String[] mPlaceDesc;
    private List<String> belowT=new ArrayList<String>();;
    private List<String> belowDesc=new ArrayList<String>();;
    /*@Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.fragment_lists);
        RecyclerView recyclerView=
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.recycler2,container,false);
        RecyclerView recyclerView =view.findViewById(R.id.my_recycler_view_unfinished);
        adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        helper.attachToRecyclerView(recyclerView);
        //finished task on below
        RecyclerView recyclerViewFinished =view.findViewById(R.id.my_recycler_view_finished);
        adapterF = new ContentAdapterF(recyclerViewFinished.getContext());
        recyclerViewFinished.setAdapter(adapterF);
        recyclerViewFinished.setHasFixedSize(true);
        recyclerViewFinished.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView description;
        public int position;
//        public CardView card;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_lists, parent, false));
            final CheckBox checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
//            card=(CardView) itemView.findViewById(R.id.card_view);

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
                public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                    if(checkBox.isChecked()){
                        //分开的已完成和未完成，此处删掉未完成的，移动到已完成的数组
                        //adapter.removeData(getAdapterPosition());
                        int fromPosition = getAdapterPosition();
                        int toPosition=adapter.LENGTH-1;
                            for (int i = fromPosition; i < toPosition; i++) {
//                                System.out.println("1");
                                Collections.swap(upT, i, i + 1);
                                Collections.swap(upDesc, i, i + 1);
                            }
                        adapter.notifyItemMoved(fromPosition, toPosition);
                        adapter.notifyItemRangeChanged(Math.min(fromPosition, toPosition), Math.abs(fromPosition - toPosition) +1);
                    }else {int fromPosition = getAdapterPosition();
                        for (int i = fromPosition; i > 0; i--) {
//                                System.out.println("1");
                            Collections.swap(upT, i, i - 1);
                            Collections.swap(upDesc, i, i - 1);
                        }
                        adapter.notifyItemMoved(fromPosition, 0);
                        adapter.notifyItemRangeChanged(Math.min(fromPosition, 0), Math.abs(fromPosition) +1);

                    }
                }
            });
        }

    }
    public class ViewHolderF extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView description;
        //        public CardView card;
        public ViewHolderF(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_lists, parent, false));
            final CheckBox checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
//            card=(CardView) itemView.findViewById(R.id.card_view);
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
                public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
                    if(!checkBox.isChecked()){
                        //card.setCardBackgroundColor();
                        //如果是分开的已完成和未完成，此处删掉未完成的，增加已完成的数组
                        //removeData(getAdapterPosition());
                        adapter.removeData(getAdapterPosition());
                    }
                }
            });
        }
    }
    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        private int LENGTH;
//  删除数据
        public void removeData(int position) {
            upT.remove(position);
            upDesc.remove(position);
            //删除动画
            notifyItemRemoved(position);
            notifyDataSetChanged();
        }

        public ContentAdapter(Context context) {
            Resources resources = context.getResources();
            upT.add("Palais Garnie");
            upT.add("Piazza del Duomo");
            upT.add("Manhattan");
            upT.add("Senso");
            upT.add("Sultan");
            upT.add("china");
            upDesc.add("The Palais Garnier which locates in Paris was built from 1861 for\n" +
                    "        the Paris Opera");
            upDesc.add("Piazza del Duomo the cathedral which locates in Florence.");
            upDesc.add("Manhattan is the district in New York City");
            upDesc.add("Senso-ji is the Shrine locates in Asakusa Toky");
            upDesc.add("Sultan Ahmed Mosque is mosque locates in Istabul, Turkey");
            upDesc.add("Senso-ji is the Shrine locates in Asakusa Toky");
            LENGTH=upT.size();
            // finish=resources.getStringArray(R.array.)
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            //System.out.println(position);
            holder.name.setText(upT.get(position));
            holder.description.setText(upDesc.get(position));

        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }
    public class ContentAdapterF extends RecyclerView.Adapter<ViewHolderF> {
        // Set numbers of Card in RecyclerView.
        private int LENGTH;

//        private final String[] mPlaces;
//        private final String[] mPlaceDesc;
        /*private final String[] finish;
         */
        public void removeData(int position) {
            belowT.remove(position);
            belowDesc.remove(position);
            //删除动画
            notifyItemRemoved(position);
            notifyDataSetChanged();
        }

        public ContentAdapterF(Context context) {
            Resources resources = context.getResources();
            belowT.add("111");
            belowDesc.add("111desc");
            LENGTH=belowDesc.size();
            // finish=resources.getStringArray(R.array.)
        }

        @Override
        public ViewHolderF onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolderF(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolderF holder, int position) {
            //System.out.println(position);
            holder.name.setText(belowT.get(position));
            holder.description.setText(belowDesc.get(position));
        }

        @Override
        public int getItemCount() {
            return LENGTH;
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
                    Collections.swap(upT, i, i + 1);
                    Collections.swap(upDesc, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(upT, i, i - 1);
                    Collections.swap(upDesc, i, i - 1);
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
