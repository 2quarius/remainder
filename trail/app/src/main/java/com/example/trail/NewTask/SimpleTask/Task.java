package com.example.trail.NewTask.SimpleTask;

import com.example.trail.Utility.EnumPack.TaskConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task extends BmobObject implements Serializable {
    private String username;
    private String taskId;
    private String title;
    private String description = null;
    private List<MiniTask> miniTasks = new ArrayList<>();
    private List<String> tags = new ArrayList<>();

    private Date startTime = null;
    private Date expireTime = null;

    private Date remindTime = null;
    private RemindCycle remindCycle = RemindCycle.DAILY;

    private Priority priority = Priority.NONE;

    private MyLocation location = null;

    private Boolean done = false;

    public Boolean isDone()
    {
        return done == null ? false : done;
    }
    public Task(String title){
        this.title = title;
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
            JSONArray object = new JSONArray(o.getString(String.valueOf(TaskConstants.MINITASK)));
            deformMiniTask(object);
        } catch (JSONException e){
            e.printStackTrace();
        }
        try{
            tags = deformTags(o.getString(String.valueOf(TaskConstants.TAGS)));
        }catch (JSONException e){
            e.printStackTrace();
        }
        try{
            startTime = (Date) o.get(String.valueOf(TaskConstants.START_TIME));
        }catch (JSONException e){
            e.printStackTrace();
        }
        try{
            expireTime = new Date(o.getString(String.valueOf(TaskConstants.EXPIRE_TIME)));
        }catch (JSONException e){
            e.printStackTrace();
        }
        try{
            remindTime = new Date(o.getString(String.valueOf(TaskConstants.REMIND_TIME)));
        }catch (JSONException e){
            e.printStackTrace();
        }
        try{
            remindCycle = RemindCycle.match(o.getString(String.valueOf(TaskConstants.REMIND_CYCLE)));
        }catch (JSONException e){
            e.printStackTrace();
        }
        try{
            priority = Priority.match(o.getString(String.valueOf(TaskConstants.PRIORITY)));
        }catch (JSONException e){
            e.printStackTrace();
        }
        try{
            JSONObject object = new JSONObject(o.getString(String.valueOf(TaskConstants.LOCATION)));
            location = new MyLocation(object);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }


    public JSONObject toJSON() throws JSONException {
        JSONObject object = new JSONObject();
        object.put(String.valueOf(TaskConstants.TITLE), title);
        object.put(String.valueOf(TaskConstants.DESCRIPTION),description);
        object.put(String.valueOf(TaskConstants.MINITASK),formMiniTask());
        object.put(String.valueOf(TaskConstants.TAGS),formTags());
        object.put(String.valueOf(TaskConstants.START_TIME),startTime);
        object.put(String.valueOf(TaskConstants.EXPIRE_TIME),expireTime);
        object.put(String.valueOf(TaskConstants.REMIND_TIME),remindTime);
        object.put(String.valueOf(TaskConstants.REMIND_CYCLE),remindCycle);
        object.put(String.valueOf(TaskConstants.PRIORITY),priority);
        object.put(String.valueOf(TaskConstants.LOCATION),formLocation());
        object.put(String.valueOf(TaskConstants.DONE),done);
        return object;
    }
    private void deformMiniTask(JSONArray array) throws JSONException {
        for (int i = 0; i< array.length();i++)
        {
            JSONObject o = array.getJSONObject(i);
            miniTasks.add(new MiniTask(o.getString("content"),o.getBoolean("done")));
        }
    }
    private String formMiniTask() throws JSONException {
        JSONArray array = new JSONArray();
        for(MiniTask miniTask:miniTasks)
        {
            JSONObject obj = new JSONObject();
            obj.put("content",miniTask.getContent());
            obj.put("done",miniTask.getDone());
            array.put(obj);
        }
        return array.toString();
    }
    private String formLocation() throws JSONException {
        JSONObject object = new JSONObject();
        if (location==null){return null;}
        else {
            object.put("location",location.getPlace());
            object.put("latitude",location.getLatitude());
            object.put("longitude",location.getLongitude());
        }
        return object.toString();
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
