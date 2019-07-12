package com.example.trail.NewTask.SimpleTask;

import android.location.Location;

import com.example.trail.Utility.TaskConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task implements Serializable {
    private String title;
    private String description = null;
    private List<String> tags = new ArrayList<>();

    private Date startTime = null;
    private Date expireTime = null;

    private Date remindTime = null;
    private RemindCycle remindCycle = RemindCycle.DAILY;

    private Priority priority = Priority.NONE;

    private Location location = null;

    private Boolean done = false;
    public Boolean isDone()
    {
        return done == null ? false : done;
    }
    public Task(JSONObject o) {
        try{
            title = o.getString(String.valueOf(TaskConstants.TITLE));
        }catch (JSONException e){
            e.printStackTrace();
        }
        try{
            description = o.getString(String.valueOf(TaskConstants.DESCRIPTION));
        }catch (JSONException e){
            e.printStackTrace();
        }
        try{
            tags = deformTags(o.getString(String.valueOf(TaskConstants.TAGS)));
        }catch (JSONException e){
            e.printStackTrace();
        }
        try{
            startTime = new Date(o.getInt(String.valueOf(TaskConstants.START_TIME)));
        }catch (JSONException e){
            e.printStackTrace();
        }
        try{
            description = o.getString(String.valueOf(TaskConstants.DESCRIPTION));
        }catch (JSONException e){
            e.printStackTrace();
        }
        try{
            description = o.getString(String.valueOf(TaskConstants.DESCRIPTION));
        }catch (JSONException e){
            e.printStackTrace();
        }
        init(o, TaskConstants.START_TIME);
        init(o, TaskConstants.EXPIRE_TIME);
        init(o, TaskConstants.REMIND_TIME);
        init(o, TaskConstants.REMIND_CYCLE);
        init(o, TaskConstants.PRIORITY);
        init(o, TaskConstants.LOCATION);
    }
    private <T> void init(JSONObject o, TaskConstants constant)
    {
        try {
            T element = (T) o.get(String.valueOf(constant));
        } catch (JSONException e) {
            e.printStackTrace();
//            element = null;
            return;
        }
    }
//        try {
//            title = o.getString(String.valueOf(TaskConstants.TITLE));
//        }catch (JSONException e) {
//
//        }
//        description = o.getString(String.valueOf(TaskConstants.DESCRIPTION));
//        tags = deformTags(o.getString(String.valueOf(TaskConstants.TAGS)));
////        startTime = (Date) o.get(String.valueOf(TaskConstants.START_TIME));
////        expireTime = (Date) o.get(String.valueOf(TaskConstants.EXPIRE_TIME));
////        remindTime = (Date) o.get(String.valueOf(TaskConstants.REMIND_TIME));
////        remindCycle = (RemindCycle) o.get(String.valueOf(TaskConstants.REMIND_CYCLE));
////        priority = (Priority) o.get(String.valueOf(TaskConstants.PRIORITY));
////        location = (Location) o.get(String.valueOf(TaskConstants.LOCATION));
//        done = o.getBoolean(String.valueOf(TaskConstants.DONE));
//        } catch (JSONException e) {
//            e.printStackTrace();
//            description = null;
//            tags = null;
//            startTime = null;
//            expireTime = null;
//            remindTime = null;
//            remindCycle = RemindCycle.DAILY;
//            priority = Priority.NONE;
//            location = null;
//        }

    public JSONObject toJSON() throws JSONException {
        JSONObject object = new JSONObject();
        object.put(String.valueOf(TaskConstants.TITLE), title);
        object.put(String.valueOf(TaskConstants.DESCRIPTION),description);
        object.put(String.valueOf(TaskConstants.TAGS),formTags());
        object.put(String.valueOf(TaskConstants.START_TIME),startTime);
        object.put(String.valueOf(TaskConstants.EXPIRE_TIME),expireTime);
        object.put(String.valueOf(TaskConstants.REMIND_TIME),remindTime);
        object.put(String.valueOf(TaskConstants.REMIND_CYCLE),remindCycle);
        object.put(String.valueOf(TaskConstants.PRIORITY),priority);
        object.put(String.valueOf(TaskConstants.LOCATION),location);
        object.put(String.valueOf(TaskConstants.DONE),done);
        return object;
    }
    private String formTags()
    {
        StringBuilder s = new StringBuilder();
        if (tags == null) {return null;}
        for (String tag: tags)
        {
            s.append(tag);
            s.append(",");
        }
        if (s.length()>0){
            s.deleteCharAt(s.length()-1);
        }
        return s.toString();
    }
    private List<String> deformTags(String string)
    {
        if (string==null){
            return null;
        }
        StringBuilder s = new StringBuilder();
        List<String> newTags = new ArrayList<>();
        int i = 0;
        while (i < string.length())
        {
            if (string.charAt(i)==',')
            {
                newTags.add(s.toString());
                s.delete(0,s.length()-1);
            }
            else {
                s.append(string.charAt(i));
            }
            i++;
        }
        if (s.length()>0) {
            newTags.add(s.toString());
        }
        return newTags;
    }
}
