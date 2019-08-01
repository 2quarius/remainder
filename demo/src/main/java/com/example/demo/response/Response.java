package com.example.demo.response;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class Response {
    private String code;
    private JSONObject data;
}
