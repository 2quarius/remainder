package com.example.trail.NewTask.Collection;

import com.example.trail.NewTask.SimpleTask.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import cn.bmob.v3.BmobObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class TaskCollector extends BmobObject implements Serializable {
    private String username = null;
    private String name;
    private ArrayList<Task> tasks = new ArrayList<>();
    public TaskCollector(String name, ArrayList<Task> tasks){
        this.name = name;
        this.tasks = tasks;
    }
    public TaskCollector(JSONObject o)
    {
        try {
            name = o.getString("name");
        } catch (JSONException e){
            e.printStackTrace();
        }
        try {
            JSONArray array = o.getJSONArray("tasks");
            for (int i = 0; i < array.length(); i++) {
                Task item = new Task(array.getJSONObject(i));
                tasks.add(item);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
    public JSONObject toJSON(JSONArray tasks) throws JSONException
    {
        JSONObject object = new JSONObject();
        object.put("name",name);
        object.put("tasks",tasks);
        return object;
    }
}
