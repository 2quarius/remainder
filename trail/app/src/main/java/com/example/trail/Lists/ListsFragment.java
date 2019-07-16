package com.example.trail.Lists;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.trail.MainActivity;
import com.example.trail.NewTask.AddTaskActivity;
import com.example.trail.NewTask.SimpleTask.Task;
import com.example.trail.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class ListsFragment extends Fragment {
    private EditText mSearchTagEdit;
    private static final int MODIFY_TASK_REQUEST_CODE = 9;
    private RecyclerView view;
    private static ContentAdapter adapter;
    private static List<Boolean> finished = new ArrayList<>();
    private static List<String> titles=new ArrayList<>();
    private static List<String> descriptions=new ArrayList<>();
    private static List<Task> mTasks;
    public interface callbackClass{
        public void setTasks(List<Task> mTasks);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
//                R.layout.recycler_view, container, false);
        View viewP=inflater.inflate(
                R.layout.recycler_view, container, false);
        RecyclerView recyclerView =viewP.findViewById(R.id.my_recycler_view);
        adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSearchTagEdit=viewP.findViewById(R.id.search_input);
        mSearchTagEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence sequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence sequence, int i, int i1, int i2) {
                System.out.println("change");
                adapter.getFilter().filter(sequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        helper.attachToRecyclerView(recyclerView);
        view = recyclerView;
        return viewP;
    }
    @Override
    public void onStart() {
        super.onStart();
        mTasks = ((MainActivity)getActivity()).getTasks();
        adapter = new ContentAdapter(view.getContext());
        view.setAdapter(adapter);
    }
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        mTasks = ((MainActivity) activity).getTasks();
    }
    private void notifyFather(){
        ((callbackClass)getActivity()).setTasks(mTasks);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
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
                    Intent intent = new Intent(getActivity(), AddTaskActivity.class);
                    intent.putExtra("position",getAdapterPosition());
                    intent.putExtra("task",mTasks.get(getAdapterPosition()));
                    startActivityForResult(intent,MODIFY_TASK_REQUEST_CODE);
                }
            });
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (compoundButton.isPressed()) {
                        if (isChecked) {
                            toBottom(titles, getAdapterPosition());
                            toBottom(descriptions, getAdapterPosition());
                            finished.set(getAdapterPosition(), isChecked);
                            toBottom(finished, getAdapterPosition());
                            mTasks.get(getAdapterPosition()).setDone(isChecked);
                            toBottom(mTasks,getAdapterPosition());
                        } else {
                            toTop(titles, getAdapterPosition());
                            toTop(descriptions, getAdapterPosition());
                            finished.set(getAdapterPosition(), isChecked);
                            toTop(finished, getAdapterPosition());
                            mTasks.get(getAdapterPosition()).setDone(isChecked);
                            toTop(mTasks,getAdapterPosition());
                        }
                        ListsFragment.this.notifyFather();
                        Handler handler = new Handler();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
                private <E> void toBottom(List<E> lists, int position){
                    if (position<lists.size()){
                        E tmp = lists.remove(position);
                        lists.add(tmp);

                    }
                }
                private <E> void toTop(List<E> lists,int position){
                    if (position<lists.size()){
                        E tmp = lists.remove(position);
                        lists.add(0,tmp);
                    }
                }
            });
        }
    }
    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> implements Filterable {
//        private List<String> mFilterTitle = new ArrayList<>();
//        private List<String> mFilterDesc = new ArrayList<>();
//        private List<Boolean> mFilterFinish = new ArrayList<>();
        private List<Task> mFilterTasks = new ArrayList<>();
        public ContentAdapter(Context context) {
            mFilterTasks=mTasks;
            if (mFilterTasks!=null&&mFilterTasks.size()==titles.size()){
                int i =0;
                for (Task t: mFilterTasks){
                    titles.set(i,t.getTitle());
                    descriptions.set(i,t.getDescription());
                    finished.set(i++,t.isDone());
                }
            }
            else if (mFilterTasks.size()>titles.size()&&titles.size()==0){
                for (int i = 0; i < mFilterTasks.size(); i++) {
                    titles.add(mFilterTasks.get(i).getTitle());
                    descriptions.add(mFilterTasks.get(i).getDescription());
                    finished.add(mFilterTasks.get(i).getDone());
                }
            }
            else if (mFilterTasks.size()>titles.size()&&titles.size()>0){
                titles.add(mFilterTasks.get(mFilterTasks.size()-1).getTitle());
                descriptions.add(mFilterTasks.get(mFilterTasks.size()-1).getDescription());
                finished.add(mFilterTasks.get(mFilterTasks.size()-1).getDone());
            }

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.checkBox.setChecked(finished.get(position));
            holder.name.setText(titles.get(position));
            holder.description.setText(descriptions.get(position));

        }

        @Override
        public int getItemCount() {
            return titles.size();
        }
        @Override
        public Filter getFilter() {
            System.out.println("filter in");
            return new Filter() {
                //执行过滤操作
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String charString = charSequence.toString();
                    if (charString.isEmpty()) {
                        //没有过滤的内容，则使用源数据
                        mFilterTasks=mTasks;
                    } else {
                        List<Task> filteredList = new ArrayList<>();
                        for (int i =0;i<titles.size();i++) {
                            //这里根据需求，添加匹配规则
                            if (titles.get(i).contains(charString)) {
//                                mFilterTitle.add(titles.get(i));
//                                mFilterFinish.add(finished.get(i));
//                                mFilterDesc.add(descriptions.get(i));
                                filteredList.add(mTasks.get(i));
                                System.out.println(titles.get(i));
                            }
                        }
                        mFilterTasks=filteredList;
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = mFilterTasks;
                    return filterResults;
                }
                //把过滤后的值返回出来
                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    mFilterTasks = (ArrayList<Task>) filterResults.values;
                    System.out.println(mFilterTasks.size());
                    titles.clear();
                    descriptions.clear();
                    finished.clear();
                    for (int i=0;i<mFilterTasks.size();i++){
                        titles.add(mFilterTasks.get(i).getTitle());
                        descriptions.add(mFilterTasks.get(i).getDescription());
                        finished.add(mFilterTasks.get(i).getDone());
                    }
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
            };
        }

        public  void delete(int position){
            System.out.println("delete");
            if(position < 0 || position > getItemCount()){
                return;
            }
            System.out.println("remove");
            titles.remove(position);
            descriptions.remove(position);
            finished.remove(position);
            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
//            adapter.notifyItemRemoved(position);
//            adapter.notifyItemRangeChanged(Math.min(position, this.getItemCount()-1), this.getItemCount()-1 - position +1);
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
            return makeMovementFlags(dragFrlg,ItemTouchHelper.START);
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
                    Collections.swap(finished,i,i+1);
                    Collections.swap(mTasks,i,i+1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(titles, i, i - 1);
                    Collections.swap(descriptions, i, i - 1);
                    Collections.swap(finished,i,i-1);
                    Collections.swap(mTasks, i, i - 1);
                }
            }
            notifyFather();
            adapter.notifyItemMoved(fromPosition, toPosition);
            adapter.notifyItemRangeChanged(Math.min(fromPosition, toPosition), Math.abs(fromPosition - toPosition) +1);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            //侧滑删除可以使用；
            adapter.delete(viewHolder.getAdapterPosition());
            mTasks.remove(viewHolder.getAdapterPosition());
            notifyFather();
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
                viewHolder.itemView.setBackgroundColor(getResources().getColor(R.color.divider));
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        /**
         * 移动过程中重新绘制Item，随着滑动的距离，设置Item的透明度
         */
        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {
            float y = Math.abs(dY) + 0.1f;
            float height = viewHolder.itemView.getHeight();
            float alpha = 1f - y / height;
            viewHolder.itemView.setAlpha(alpha);
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState,
                    isCurrentlyActive);
        }
        /**
         * 手指松开的时候还原高亮
         * @param recyclerView
         * @param viewHolder
         */
        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            viewHolder.itemView.setBackgroundColor(getResources().getColor(R.color.inputLine));
            viewHolder.itemView.setAlpha(1.0f);
            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            }); //完成拖动后刷新适配器，这样拖动后删除就不会错乱
        }

        /**
         * Item是否支持滑动
         *
         * @return
         *          true  支持滑动操作
         *          false 不支持滑动操作
         */
        @Override
        public boolean isItemViewSwipeEnabled() {
            return true;
        }

    });
}
