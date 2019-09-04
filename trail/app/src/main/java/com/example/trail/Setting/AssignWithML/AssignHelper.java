package com.example.trail.Setting.AssignWithML;

import android.util.Log;

import com.example.trail.NewTask.Collection.TaskCollector;
import com.example.trail.NewTask.SimpleTask.Task;
import com.example.trail.Utility.Utils.TimeUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.trail.Utility.EnumPack.KeyConstants.BIGML_API_KEY;
import static com.example.trail.Utility.EnumPack.KeyConstants.BIGML_USERNAME;
@Getter
@Setter
@AllArgsConstructor
public class AssignHelper {
    public interface HelperListener{
        public void onPredict(String predictTimeInMills);
    }
    private Task mTask;
    private HelperListener helperListener;
    private static final String HEADER_KEY = "Content-Type";
    private static final String HEADER_VAL = "application/json";
    private static final String baseUrl = "https://bigml.io/andromeda/";
    private static final String BIGML_AUTH= "username="+BIGML_USERNAME+";api_key="+BIGML_API_KEY;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private void sendPostRequest(String bodyKey,String bodyVal,String urlSpec,Callback callback){
        JSONObject json = new JSONObject();
        try {
            json.put(bodyKey,bodyVal);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(JSON, json.toString());
        Request request = new Request.Builder()
                .url(baseUrl+urlSpec+"?"+BIGML_AUTH)
                .header(HEADER_KEY,HEADER_VAL)
                .post(requestBody)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(callback);
    }
    public void createInlineSource(List<TaskCollector> taskCollectors) throws Exception{
        sendPostRequest("data", exportTaskToCsv(taskCollectors), "source", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code()==201) {
                    String id = parseJsonWithJsonObject(response, "resource");
                    if (id != null) {
                        createDataSource(id);
                    }
                }
                else {
                    Log.d("bigml",response.code()+"\t"+response.message());
                }
            }
        });
    }

    private void createDataSource(String id) {
        sendPostRequest("source", id, "dataset", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code()==201) {
                    String id = parseJsonWithJsonObject(response,"resource");
                    if (id!=null){
                        createModel(id);
                    }
                }
                else {
                    Log.d("bigml",response.code()+"\t"+response.message());
                }
            }
        });
    }

    private void createModel(String id) {
        sendPostRequest("dataset", id, "model", new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code()==201) {
                    String id = parseJsonWithJsonObject(response,"resource");
                    if (id!=null){
                        createPrediction(id);
                    }
                }
                else {
                    Log.d("bigml",response.code()+"\t"+response.message());
                }
            }
        });
    }

    private void createPrediction(String id) {
        JSONObject json = new JSONObject();
        try {
            json.put("model",id);
            JSONObject input = new JSONObject();
//            input.put("000000","google");
//            input.put("000001","google");
//            input.put("000002","1568000480");
//            input.put("000003","1567994400");
//            input.put("000004","每周");
//            input.put("000005","东上202|120|30");
//            input.put("000006","3");
            input.put("000000",mTask.getTitle());
            input.put("000001",mTask.getDescription());
            try {
                input.put("000002", TimeUtil.Date2Cal(mTask.getExpireTime()).getTimeInMillis());
            }catch (Exception ignore){}
            try {
                input.put("000003",TimeUtil.Date2Cal(mTask.getStartTime()).getTimeInMillis());
            }catch (Exception ignore){}
            try {
                input.put("000004",mTask.getRemindCycle().getCycle());
            }catch (Exception ignore){}
            try {
                input.put("000005",mTask.getLocation().toString());
            }catch (Exception ignore){}
            try {
                input.put("000006",mTask.getPriority().getPriority());
            }catch (Exception ignore){}
            json.put("input_data",input);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(JSON, json.toString());
        Request request = new Request.Builder()
                .url(baseUrl+"prediction?"+BIGML_AUTH)
                .header(HEADER_KEY,HEADER_VAL)
                .post(requestBody)
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code()==201) {
                    String id = parseJsonWithJsonObject(response,"output");
                    if (id!=null){
                        if (helperListener!=null){
                            helperListener.onPredict(id);
                        }
                    }
                }
                else {
                    Log.d("bigml",response.code()+"\t"+response.message());
                }

            }
        });
    }

    /**
     * 获取source id
     * 返回的json示例：
     * {
     *     "category": 0,
     *     "code": 201,
     *     ...
     *     "created": "2019-08-29T03:33:58.783448",
     *     ...
     *     "resource": "source/5d6747a65299630409014856",
     *     ...
     *     "status": {
     *         "code": 1,
     *         "message": "The source creation request has been queued and will be processed soon",
     *         "progress": 0
     *     },
     *     ...
     *     "updated": "2019-08-29T03:33:58.783471"
     * }
     * @param response
     * @return
     * @throws IOException
     */
    private static String parseJsonWithJsonObject(Response response,String key) throws IOException {
        String responseData=response.body().string();
        try{
            JSONObject jsonArray=new JSONObject(responseData);
            String id = jsonArray.getString(key);
            return id;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    private String exportTaskToCsv(List<TaskCollector> taskCollectors){
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
                    if (task.getRemindCycle()!=null) {
                        rowData.put("5", task.getRemindCycle().getCycle());
                    }else {
                        rowData.put("5",null);
                    }
                    if (task.getLocation()!=null){
                        rowData.put("6",task.getLocation());
                    }else {
                        rowData.put("6",null);
                    }
                    if (task.getPriority()!=null) {
                        rowData.put("7", task.getPriority().getPriority());
                    }else {
                        rowData.put("7",null);
                    }
                    rowData.put("8", TimeUtil.Date2Cal(task.getRemindTime()).getTimeInMillis());
                    exportData.add(rowData);
                }
            }
        }
        LinkedHashMap<String, String> header = new LinkedHashMap<>();
        header.put("1", "title");
        header.put("2", "description");
        header.put("3", "expireTime");
        header.put("4","startTime");
        header.put("5","remindCycle");
        header.put("6","location");
        header.put("7","priority");
        header.put("8","remindTime");
        return exportCSV(header,exportData);
    }
    private String exportCSV(LinkedHashMap<String, String> headers, List<LinkedHashMap<String, Object>> exportData) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedWriter buffCvsWriter = null;

        try {
            // 编码gb2312，处理excel打开csv的时候会出现的标题中文乱码
            buffCvsWriter = new BufferedWriter(new OutputStreamWriter(baos, "gb2312"));
            // 写入cvs文件的头部
            Map.Entry propertyEntry = null;
            for (Iterator<Map.Entry<String, String>> propertyIterator = headers.entrySet().iterator(); propertyIterator.hasNext(); ) {
                propertyEntry = propertyIterator.next();
                buffCvsWriter.write( propertyEntry.getValue().toString() );
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
                        buffCvsWriter.write( propertyEntry.getValue().toString() );
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
        return baos.toString();
    }
}
