package com.example.trail.NewTask.SimpleTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyLocation implements Serializable {
    private String place;
    private double latitude;
    private double longitude;
    public MyLocation(JSONObject s) throws JSONException {
        if (s == null) return;
        else {
            place = s.getString("location");
            latitude = s.getDouble("latitude");
            longitude = s.getDouble("longitude");
        }
    }
    public MyLocation(String s){
        place = s;
    }
    @Override
    public String toString(){
        return place+"|"+latitude+"|"+longitude;
    }
}
