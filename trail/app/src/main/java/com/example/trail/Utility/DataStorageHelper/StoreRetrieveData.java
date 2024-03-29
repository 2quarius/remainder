package com.example.trail.Utility.DataStorageHelper;

import android.content.Context;

import com.example.trail.NewTask.Collection.TaskCollector;
import com.example.trail.NewTask.SimpleTask.Task;

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
