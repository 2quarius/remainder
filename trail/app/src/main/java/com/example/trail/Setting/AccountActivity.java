package com.example.trail.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.trail.MainActivity;
import com.example.trail.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AccountActivity extends AppCompatActivity {
    private Button btnLogin;
    private Button btnRegist;
    private ImageButton btnQQ;
    private ImageButton btnWechat;
    private ImageButton btnJaccount;
    private EditText username;
    private EditText password;
    private Button back;
    final  private String FILE_NAME = "account.txt";
    final  private String FILE_NAME2 = "information.txt";
    final  private String FILE_NAME3 = "theme.txt";

    private void setTheTheme() {
        String theme = "";
        try {
            FileInputStream ios = openFileInput(FILE_NAME3);
            byte[] temp = new byte[10];
            StringBuilder sb = new StringBuilder("");
            int len = 0;
            while ((len = ios.read(temp)) > 0){
                sb.append(new String(temp, 0, len));
            }
            ios.close();
            theme = sb.toString();
        }catch (Exception e) {
            //Log.d("errMsg", e.toString());
        }
        if (theme.equals("purple")) {
            setTheme(R.style.LightTheme);
        }
        else if (theme.equals("black")){
            setTheme(R.style.NightTheme);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheTheme();
        setContentView(R.layout.activity_account);
        btnLogin = findViewById(R.id.btn_login);
        btnRegist = findViewById(R.id.btn_regist);
        btnQQ = findViewById(R.id.btn_qq);
        btnWechat = findViewById(R.id.btn_wechat);
        btnJaccount=findViewById(R.id.btn_jaccount);

        back = findViewById(R.id.btn_accountBack);
        back.bringToFront();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {//登录
            @Override
            public void onClick(View view) {
                username = findViewById(R.id.et_username);
                String un = username.getText().toString();

                password = findViewById(R.id.et_password);
                String pw = password.getText().toString();

                if(un.length()<=0){ //是否输入用户名
                    Toast.makeText(AccountActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                }
                else if(pw.length()<=0){ //是否输入密码
                    Toast.makeText(AccountActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                }
                else {//判断是否符合条件
                    if(!searchAccount(un)){
                        Toast.makeText(AccountActivity.this,"不存在此用户，请注册",Toast.LENGTH_SHORT).show();
                    }
                    else if(!searchPassword(un,pw)){
                        Toast.makeText(AccountActivity.this,"密码错误，请重新输入",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(AccountActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                        save(un);
                        Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
        btnRegist.setOnClickListener(new View.OnClickListener() { //注册
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this,RegistActivity.class);
                startActivity(intent);
            }
        });
        btnQQ.setOnClickListener(new View.OnClickListener() { //qq
            @Override
            public void onClick(View view) {
                Toast.makeText(AccountActivity.this,"qq登录功能未实现",Toast.LENGTH_SHORT).show();
            }
        });
        btnWechat.setOnClickListener(new View.OnClickListener() { //微信
            @Override
            public void onClick(View view) {
                Toast.makeText(AccountActivity.this,"微信登录功能未实现",Toast.LENGTH_SHORT).show();
            }
        });
        btnJaccount.setOnClickListener(new View.OnClickListener() { //jaccount
            @Override

            public void onClick(View view) {
                new Thread(){
                    @Override
                    public void run()
                    {
                        OkHttpClient getCode = new OkHttpClient();
                        Request.Builder reqBuild = new Request.Builder();
                        HttpUrl.Builder urlBuilder =HttpUrl.parse(" https://jaccount.sjtu.edu.cn/oauth2/authorize")
                                .newBuilder();
                        urlBuilder.addQueryParameter("response_type", "code");
                        urlBuilder.addQueryParameter("scope", "openid");
                        urlBuilder.addQueryParameter("client_id", "3q6TNuBfQXWJ8XypOTNx");
                        urlBuilder.addQueryParameter("redirect_uri", "https://www.baidu.com");//baidu网址改成后端url
                        reqBuild.url(urlBuilder.build());
                        Request request = reqBuild.build();
                        Response response = null;
                        try {
                            response = getCode.newCall(request).execute();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                        Headers responseHeaders = response.headers();
                        for (int i = 0; i < responseHeaders.size(); i++) {
                            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                        }
                        System.out.println(response.body());
                        System.out.println("getCode");
                    }
                }.start();

                Toast.makeText(AccountActivity.this,"jaccount登录功能未实现",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        setTheTheme();
    }

    public void save(String text) {
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME2, Context.MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            //Log.d("errMsg", e.toString());
        }
    }

    private String readFile(){
        String textContent = "";
        try {
            FileInputStream ios = openFileInput(FILE_NAME);
            byte[] temp = new byte[1024];
            StringBuilder sb = new StringBuilder("");
            int len = 0;
            while ((len = ios.read(temp)) > 0){
                sb.append(new String(temp, 0, len));
            }
            ios.close();
            textContent = sb.toString();
        }catch (Exception e) {
            //Log.d("errMsg", e.toString());
        }
        return textContent;
    }
    //查询text
    private boolean searchFile(String text){
        String textContent=readFile();
        if(textContent.contains(text))return true;
        else return false;
    }

    //用户名判断
    private boolean searchAccount(String account){
        String text=readFile();
        String pattern="Account: "+account+"\n";
        if(text.contains(pattern)) return true;
        else return false;
    }

    //密码判断
    private boolean searchPassword(String account,String password){
        String textContent = readFile();
        String pattern = "Account: " + account + "\nPassword: " + password + "\n";
        if(textContent.contains(pattern))return true;
        else return false;
    }

}
