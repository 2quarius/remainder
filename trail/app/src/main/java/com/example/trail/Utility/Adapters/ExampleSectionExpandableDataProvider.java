package com.example.trail.Utility.Adapters;

import android.util.Pair;

import com.example.trail.NewTask.SimpleTask.MiniTask;
import com.example.trail.NewTask.SimpleTask.Task;
import com.example.trail.Utility.Utils.TimeUtil;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ExampleSectionExpandableDataProvider extends AbstractExpandableDataProvider {
    private List<Pair<Long,Task>> mTasks = new ArrayList<>();
    private List<Pair<ConcreteGroupData, List<ChildData>>> mData;

    // for undo group item
    private Pair<ConcreteGroupData, List<ChildData>> mLastRemovedGroup;
    private int mLastRemovedGroupPosition = -1;

    // for undo child item
    private ChildData mLastRemovedChild;
    private long mLastRemovedChildParentGroupId = -1;
    private int mLastRemovedChildPosition = -1;

    public ExampleSectionExpandableDataProvider(List<Task> tasks) {
        //按时间划分任务，粒度为一小时
        //  hour   list of   title-miniTaskTitles
        Map<String,List<Task>> titlesByTime = groupByTime(tasks);

        mData = new LinkedList<Pair<ConcreteGroupData, List<ChildData>>>();

        long groupId = -1;
        for (Map.Entry<String,List<Task>> entry:titlesByTime.entrySet()){
            //section header (can be took as group with no children)
            final ConcreteGroupData sectionHeader = new ConcreteGroupData(++groupId,true,entry.getKey());
            mData.add(new Pair<ConcreteGroupData, List<ChildData>>(sectionHeader,new ArrayList<ChildData>()));
            mTasks.add(new Pair<>(groupId,new Task()));
            //group (titles) and children (mini task titles)
            for (Task groupChildren:entry.getValue()) {
                final ConcreteGroupData group = new ConcreteGroupData(++groupId, false, groupChildren.getTitle());
                final List<ChildData> children = new ArrayList<>();
                for (MiniTask mini:groupChildren.getMiniTasks()){
                    final long childId = group.generateNewChildId();
                    children.add(new ConcreteChildData(childId,mini.getContent()));
                }
                mData.add(new Pair<>(group,children));
                mTasks.add(new Pair<>(groupId,groupChildren));
            }
        }
    }

    private Map<String,List<Task>> groupByTime(List<Task> tasks) {
        Map<String,List<Task>> result = new HashMap<>();
        for (Task task:tasks){
            Date expire = task.getExpireTime();
            int hour = TimeUtil.Date2Cal(expire).get(Calendar.HOUR);
            String shour = duration(hour);
            if (result.containsKey(shour)){
                result.get(shour).add(task);
            }
            else {
                List<Task> sub = new ArrayList<>();
                sub.add(task);
                result.put(shour,sub);
            }
        }
        return result;
    }

    private String duration(int hour){
        StringBuilder sb = new StringBuilder();
        sb.append(hour);
        sb.append(":00-");
        sb.append(hour+1);
        sb.append(":00");
        return sb.toString();
    }
    @Override
    public int getGroupCount() {
        return mData.size();
    }

    @Override
    public int getChildCount(int groupPosition) {
        return mData.get(groupPosition).second.size();
    }

    @Override
    public GroupData getGroupItem(int groupPosition) {
        if (groupPosition < 0 || groupPosition >= getGroupCount()) {
            throw new IndexOutOfBoundsException("groupPosition = " + groupPosition);
        }

        return mData.get(groupPosition).first;
    }

    @Override
    public ChildData getChildItem(int groupPosition, int childPosition) {
        if (groupPosition < 0 || groupPosition >= getGroupCount()) {
            throw new IndexOutOfBoundsException("groupPosition = " + groupPosition);
        }

        final List<ChildData> children = mData.get(groupPosition).second;

        if (childPosition < 0 || childPosition >= children.size()) {
            throw new IndexOutOfBoundsException("childPosition = " + childPosition);
        }

        return children.get(childPosition);
    }

    @Override
    public void moveGroupItem(int fromGroupPosition, int toGroupPosition) {
        if (fromGroupPosition == toGroupPosition) {
            return;
        }
        int position = toGroupPosition;
        if (toGroupPosition<fromGroupPosition){
            position-=1;
        }
        Date toDate = getGroupItem(position).isSectionHeader()?
                TimeUtil.String2Date(mTasks.get(Math.toIntExact(getGroupItem(fromGroupPosition).getGroupId())).second.getExpireTime(), getGroupItem(position).getText()):
                mTasks.get(Math.toIntExact(getGroupItem(position).getGroupId())).second.getExpireTime();
        mTasks.get(Math.toIntExact(getGroupItem(fromGroupPosition).getGroupId())).second.setExpireTime(toDate);
        final Pair<ConcreteGroupData, List<ChildData>> item = mData.remove(fromGroupPosition);
        mData.add(toGroupPosition, item);
    }

    @Override
    public void moveChildItem(int fromGroupPosition, int fromChildPosition, int toGroupPosition, int toChildPosition) {
        if ((fromGroupPosition == toGroupPosition) && (fromChildPosition == toChildPosition)) {
            return;
        }

        final Pair<ConcreteGroupData, List<ChildData>> fromGroup = mData.get(fromGroupPosition);
        final Pair<ConcreteGroupData, List<ChildData>> toGroup = mData.get(toGroupPosition);

        if (fromGroup.first.isSectionHeader()) {
            throw new IllegalStateException("Source group is a section header!");
        }
        if (toGroup.first.isSectionHeader()) {
            throw new IllegalStateException("Destination group is a section header!");
        }

        mTasks.get(toGroupPosition).second.getMiniTasks().add(mTasks.get(fromGroupPosition).second.getMiniTasks().remove(fromChildPosition));
        final ConcreteChildData item = (ConcreteChildData) fromGroup.second.remove(fromChildPosition);

        if (toGroupPosition != fromGroupPosition) {
            // assign a new ID
            final long newId = ((ConcreteGroupData) toGroup.first).generateNewChildId();
            item.setChildId(newId);
        }

        toGroup.second.add(toChildPosition, item);
    }

    @Override
    public void removeGroupItem(int groupPosition) {
        mLastRemovedGroup = mData.remove(groupPosition);
        mLastRemovedGroupPosition = groupPosition;

        mLastRemovedChild = null;
        mLastRemovedChildParentGroupId = -1;
        mLastRemovedChildPosition = -1;
    }

    @Override
    public void removeChildItem(int groupPosition, int childPosition) {
        mLastRemovedChild = mData.get(groupPosition).second.remove(childPosition);
        mLastRemovedChildParentGroupId = mData.get(groupPosition).first.getGroupId();
        mLastRemovedChildPosition = childPosition;

        mLastRemovedGroup = null;
        mLastRemovedGroupPosition = -1;
    }


    @Override
    public long undoLastRemoval() {
        if (mLastRemovedGroup != null) {
            return undoGroupRemoval();
        } else if (mLastRemovedChild != null) {
            return undoChildRemoval();
        } else {
            return RecyclerViewExpandableItemManager.NO_EXPANDABLE_POSITION;
        }
    }

    private long undoGroupRemoval() {
        int insertedPosition;
        if (mLastRemovedGroupPosition >= 0 && mLastRemovedGroupPosition < mData.size()) {
            insertedPosition = mLastRemovedGroupPosition;
        } else {
            insertedPosition = mData.size();
        }

        mData.add(insertedPosition, mLastRemovedGroup);

        mLastRemovedGroup = null;
        mLastRemovedGroupPosition = -1;

        return RecyclerViewExpandableItemManager.getPackedPositionForGroup(insertedPosition);
    }

    private long undoChildRemoval() {
        Pair<ConcreteGroupData, List<ChildData>> group = null;
        int groupPosition = -1;

        // find the group
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).first.getGroupId() == mLastRemovedChildParentGroupId) {
                group = mData.get(i);
                groupPosition = i;
                break;
            }
        }

        if (group == null) {
            return RecyclerViewExpandableItemManager.NO_EXPANDABLE_POSITION;
        }

        int insertedPosition;
        if (mLastRemovedChildPosition >= 0 && mLastRemovedChildPosition < group.second.size()) {
            insertedPosition = mLastRemovedChildPosition;
        } else {
            insertedPosition = group.second.size();
        }

        group.second.add(insertedPosition, mLastRemovedChild);

        mLastRemovedChildParentGroupId = -1;
        mLastRemovedChildPosition = -1;
        mLastRemovedChild = null;

        return RecyclerViewExpandableItemManager.getPackedPositionForChild(groupPosition, insertedPosition);
    }

    public static final class ConcreteGroupData extends GroupData {

        private final long mId;
        private final boolean mIsSectionHeader;
        private final String mText;
        private boolean mPinned;
        private long mNextChildId;

        ConcreteGroupData(long id, boolean isSectionHeader, String text) {
            mId = id;
            mIsSectionHeader = isSectionHeader;
            mText = text;
            mNextChildId = 0;
        }

        @Override
        public long getGroupId() {
            return mId;
        }

        @Override
        public boolean isSectionHeader() {
            return mIsSectionHeader;
        }

        @Override
        public String getText() {
            return mText;
        }

        @Override
        public void setPinned(boolean pinned) {
            mPinned = pinned;
        }

        @Override
        public boolean isPinned() {
            return mPinned;
        }

        public long generateNewChildId() {
            final long id = mNextChildId;
            mNextChildId += 1;
            return id;
        }
    }

    public static final class ConcreteChildData extends ChildData {

        private long mId;
        private final String mText;
        private boolean mPinned;

        ConcreteChildData(long id, String text) {
            mId = id;
            mText = text;
        }

        @Override
        public long getChildId() {
            return mId;
        }

        @Override
        public String getText() {
            return mText;
        }

        @Override
        public void setPinned(boolean pinnedToSwipeLeft) {
            mPinned = pinnedToSwipeLeft;
        }

        @Override
        public boolean isPinned() {
            return mPinned;
        }

        public void setChildId(long id) {
            this.mId = id;
        }
    }
}
