package com.example.trail.Utility.Utils;

import com.example.trail.NewTask.Collection.TaskCollector;
import com.example.trail.NewTask.SimpleTask.Task;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.trail.Utility.EnumPack.KeyConstants.BIGML_API_KEY;
import static com.example.trail.Utility.EnumPack.KeyConstants.BIGML_USERNAME;

public class AssignUtils {
    private static final String HEADER_KEY = "content-type";
    private static final String HEADER_VAL = "application/json";
    private static final String baseUrl = "https://bigml.io/andromeda/";
    private static final String BIGML_AUTH= "username="+BIGML_USERNAME+";api_key="+BIGML_API_KEY;

    //TODO async task
    public static void createInlineSource(List<TaskCollector> taskCollectors) throws Exception{
        RequestBody requestBody = new FormBody.Builder()
                .add("data",exportTaskToCsv(taskCollectors).toString())
                .build();
        Request request = new Request.Builder()
                .url(baseUrl+"source?"+BIGML_AUTH)
                .header(HEADER_KEY,HEADER_VAL)
                .post(requestBody)
                .build();
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()){
            System.out.println(response.body().string());
        }
    }
    private static byte[] exportTaskToCsv(List<TaskCollector> taskCollectors){
        List<LinkedHashMap<String, Object>> exportData = new ArrayList<>();
        //具体数据
        for (TaskCollector taskCollector:taskCollectors){
            for (Task task:taskCollector.getTasks()){
                if (task.getRemindTime()!=null){
                    LinkedHashMap<String, Object> rowData = new LinkedHashMap<>();
                    rowData.put("1", task.getTitle());
                    rowData.put("2", task.getDescription());
                    try {
                        rowData.put("3", TimeUtil.Date2Cal(task.getExpireTime()).getTimeInMillis());
                    } catch (Exception e){
                        rowData.put("3",null);
                    }
                    try {
                        rowData.put("4", TimeUtil.Date2Cal(task.getStartTime()).getTimeInMillis());
                    } catch (Exception e){
                        rowData.put("4",null);
                    }
                    rowData.put("5", TimeUtil.Date2Cal(task.getRemindTime()).getTimeInMillis());
                    if (task.getRemindCycle()!=null) {
                        rowData.put("6", task.getRemindCycle().getCycle());
                    }else {
                        rowData.put("6",null);
                    }
                    if (task.getLocation()!=null){
                        rowData.put("7",task.getLocation());
                    }else {
                        rowData.put("7",null);
                    }
                    if (task.getPriority()!=null) {
                        rowData.put("8", task.getPriority().getPriority());
                    }else {
                        rowData.put("8",null);
                    }
                    exportData.add(rowData);
                }
            }
        }
        LinkedHashMap<String, String> header = new LinkedHashMap<>();
        header.put("1", "title");
        header.put("2", "description");
        header.put("3", "expireTime");
        header.put("4","startTime");
        header.put("5","remindTime");
        header.put("6","remindCycle");
        header.put("7","location");
        header.put("8","priority");
        return exportCSV(header,exportData);
    }
    private static byte[] exportCSV(LinkedHashMap<String, String> headers, List<LinkedHashMap<String, Object>> exportData) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedWriter buffCvsWriter = null;

        try {
            // 编码gb2312，处理excel打开csv的时候会出现的标题中文乱码
            buffCvsWriter = new BufferedWriter(new OutputStreamWriter(baos, "gb2312"));
            // 写入cvs文件的头部
            Map.Entry propertyEntry = null;
            for (Iterator<Map.Entry<String, String>> propertyIterator = headers.entrySet().iterator(); propertyIterator.hasNext(); ) {
                propertyEntry = propertyIterator.next();
                buffCvsWriter.write("\"" + propertyEntry.getValue().toString() + "\"");
                if (propertyIterator.hasNext()) {
                    buffCvsWriter.write(",");
                }
            }
            buffCvsWriter.newLine();
            // 写入文件内容
            LinkedHashMap row = null;
            for (Iterator<LinkedHashMap<String, Object>> iterator = exportData.iterator(); iterator.hasNext(); ) {
                row = iterator.next();
                for (Iterator<Map.Entry> propertyIterator = row.entrySet().iterator(); propertyIterator.hasNext(); ) {
                    propertyEntry = propertyIterator.next();
                    if (propertyEntry.getValue()!=null) {
                        buffCvsWriter.write("\"" + propertyEntry.getValue().toString() + "\"");
                    }
                    else {
                        buffCvsWriter.write("\"\"");
                    }
                    if (propertyIterator.hasNext()) {
                        buffCvsWriter.write(",");
                    }
                }
                if (iterator.hasNext()) {
                    buffCvsWriter.newLine();
                }
            }
            // 记得刷新缓冲区，不然数可能会不全的，当然close的话也会flush的，不加也没问题
            buffCvsWriter.flush();
        } catch (IOException e) {

        } finally {
            // 释放资源
            if (buffCvsWriter != null) {
                try {
                    buffCvsWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return baos.toByteArray();
    }
}
