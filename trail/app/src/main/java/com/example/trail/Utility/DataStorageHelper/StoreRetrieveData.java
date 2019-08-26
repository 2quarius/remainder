package com.example.trail.Utility.DataStorageHelper;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.trail.NewTask.Collection.TaskCollector;
import com.example.trail.NewTask.SimpleTask.Task;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class StoreRetrieveData {
    private Context mContext;
    private String mFileName;

    public StoreRetrieveData(Context context, String filename) {
        mContext = context;
        mFileName = filename;
    }

    private static JSONArray listOfTask2JSONArray(ArrayList<Task> items) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (Task item : items) {
            JSONObject jsonObject = item.toJSON();
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }
    private static JSONArray toJSONArray(ArrayList<TaskCollector> collectors) throws JSONException{
        JSONArray jsonArray = new JSONArray();
        for (TaskCollector collector:collectors){
            JSONArray tasks = listOfTask2JSONArray(collector.getTasks());
            JSONObject object = collector.toJSON(tasks);
            jsonArray.put(object);
        }
        return jsonArray;
    }
    public <K,V> void saveToFile(Map<K,V> alarms) throws IOException {
        SharedPreferences sp = mContext.getSharedPreferences(mFileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(alarms);
        editor.putString("alarms",json);
        editor.apply();
    }
    public <V> HashMap<Integer,V> loadFromFile(Class<V> clsV){
        SharedPreferences sp = mContext.getSharedPreferences(mFileName, Context.MODE_PRIVATE);
        String json = sp.getString("alarms", "");
        HashMap<Integer, V> map = new HashMap<>();
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(json);
        if (jsonElement instanceof JsonObject){
            JsonObject object = jsonElement.getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entries = object.entrySet();
            if (entries!=null){
                Iterator<Map.Entry<String, JsonElement>> iterator = entries.iterator();
                while (iterator.hasNext()){
                    map.put(Integer.valueOf(iterator.next().getKey()), (V) iterator.next().getValue());
                }
            }
        }
        return map;
    }
    public void saveToFile(ArrayList<TaskCollector> items) throws JSONException, IOException {
        FileOutputStream fileOutputStream;
        OutputStreamWriter outputStreamWriter;
        fileOutputStream = mContext.openFileOutput(mFileName, Context.MODE_PRIVATE);
        outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        outputStreamWriter.write(toJSONArray(items).toString());
        outputStreamWriter.close();
        fileOutputStream.close();
    }

    public ArrayList<TaskCollector> loadFromFile() throws IOException, JSONException {
        ArrayList<TaskCollector> collectors = new ArrayList<>();
        BufferedReader bufferedReader = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = mContext.openFileInput(mFileName);
            StringBuilder builder = new StringBuilder();
            String line;
            bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
            JSONArray jsonArray = (JSONArray) new JSONTokener(builder.toString()).nextValue();
            for (int i=0;i<jsonArray.length();i++){
                TaskCollector collector = new TaskCollector(jsonArray.getJSONObject(i));
                collectors.add(collector);
            }


        } catch (FileNotFoundException fnfe) {
            //do nothing about it
            //file won't exist first time app is run
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (fileInputStream != null) {
                fileInputStream.close();
            }

        }
        return collectors;
    }
}
