package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.demo.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;

@RestController
public class JaccountController {
    @Autowired
    RestTemplate restTemplate;

    private String tokenUrl = "https://jaccount.sjtu.edu.cn/oauth2/token";
    private String profileApi = "https://api.sjtu.edu.cn/v1/me/profile";
    private String lessonsApi = "https://api.sjtu.edu.cn/v1/me/lessons";
    private String client_id = "3q6TNuBfQXWJ8XypOTNx";
    private String client_secret = "3708E0E6D34C0BE0ECFB547C2C89CB1926EA1AE937BA204F";
    private String grant_type = "authorization_code";
    private String base_url = "http://202.120.40.8:30335";

    /**
     * @api {get} /login
     * @apiDescription 用Oauth2的code进行user登陆
     * @apiName login
     * @apiGroup jaccount
     * @apiVersion 1.0.0
     * @apiParam {String} code
     */
    @ResponseBody
    @RequestMapping(value = "/login/jaccount", method = RequestMethod.GET)
    public Response login(@RequestParam("code")String code,HttpServletResponse response) {
        ResponseEntity<String> responseEntity;

        // post header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        // post body
        MultiValueMap<String,String> param = new LinkedMultiValueMap<>();
        param.add("code",code);
        param.add("client_id",client_id);
        param.add("client_secret",client_secret);
        param.add("grant_type",grant_type);
        param.add("redirect_uri",base_url+"/login/jaccount");

        // request for access token with http request
        HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(param,headers);
        responseEntity = restTemplate.postForEntity(tokenUrl,request,String.class);

        // use access token to get user profile with http request
        JSONObject responseJson = JSONObject.parseObject(responseEntity.getBody());
        String token = responseJson.getString("access_token");
        try{
            String url = base_url+"/jaccount/profile?access_token="+token;
            response.sendRedirect(url);
        }catch (Exception e){
            e.printStackTrace();
        }
        Response res = new Response();
        res.setCode("200");
        JSONObject object = new JSONObject();
        object.put("access_token",token);
        res.setData(object);
        return res;
    }
    @ResponseBody
    @GetMapping(value = "/jaccount/profile")
    public String getProfile(@RequestParam(value = "access_token")String token){
        String url = profileApi
                +"?access_token="+token;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        JSONObject responseJson = JSONObject.parseObject(responseEntity.getBody());
        String name = responseJson.getJSONArray("entities").getJSONObject(0).getString("name");
        StringBuilder result = new StringBuilder();
        result.append("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>login</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>"+name+"</h1>"+
                "<h2>欢迎登录👏</h2>"+
                "\n" +
                "</body>\n" +
                "</html>");
        return result.toString();
    }
    @ResponseBody
    @RequestMapping(value = "/jaccount/lessons", method = RequestMethod.GET)
    public Response getLessons(@RequestParam(value = "access_token")String token){
        String url = lessonsApi
                +"?access_token="+token;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

        JSONObject responseJson = JSONObject.parseObject(responseEntity.getBody());
        Response response = new Response();
        try{
            JSONObject code = responseJson.getJSONObject("code");
            JSONObject course = responseJson.getJSONObject("course");
            JSONArray classes = responseJson.getJSONArray("classes");
            JSONObject object = toJSONObject(code,course,classes);
            response.setCode("200");
            response.setData(object);
        } catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    private JSONObject toJSONObject(JSONObject code, JSONObject course, JSONArray classes) {
        JSONObject object = new JSONObject();
        object.put("code",code);
        String courseCode = course.getString("code");
        String courseName = course.getString("name");
        JSONObject tmp = new JSONObject();
        tmp.put("code",courseCode);
        tmp.put("name",courseName);
        object.put("course",tmp);
        JSONArray clses = new JSONArray();
        for (int i = 0;i<classes.size();i++){
            JSONObject obj = classes.getJSONObject(i);
            JSONObject schedule = obj.getJSONObject("schedule");
            int week = schedule.getIntValue("week");
            int day = schedule.getIntValue("day");
            String period = schedule.getString("period");
            int last = schedule.getIntValue("last");
            JSONObject t = new JSONObject();
            t.put("week",week);
            t.put("day",day);
            t.put("period",period);
            t.put("last",last);
            clses.add(t);
        }
        object.put("classes",clses);
        return object;
    }

}
