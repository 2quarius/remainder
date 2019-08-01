package com.example.trail.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class RegistActivity extends AppCompatActivity {
    private Button btnRegist;
    private EditText username;
    private EditText password;
    private EditText passwordagain;
    private Button back;
    final  private String FILE_NAME = "account.txt";
    final  private String FILE_NAME2 = "information.txt";
    final  private String FILE_NAME3 = "theme.txt";
    private boolean flag=true;//判断username是否重复

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
        setContentView(R.layout.activity_regist);
        btnRegist=findViewById(R.id.btn_regist1);

        back = findViewById(R.id.btn_registBack);
        back.bringToFront();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnRegist.setOnClickListener(new View.OnClickListener() {//登录
            @Override
            public void onClick(View view) {
                username = findViewById(R.id.et_username1);
                final String un = username.getText().toString();

                password = findViewById(R.id.et_password1);
                final String pw = password.getText().toString();

                passwordagain = findViewById(R.id.et_passwordAgain);
                String pwa = passwordagain.getText().toString();


                BmobQuery<User> tempList=new BmobQuery<>();
                tempList.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        for(int i=0;i<list.size();i++){
                            if(list.get(i).getUsername().equals(un)){
                                flag=false;
                            }
                        }
                    }
                });


                if(flag){
                    if(un.length()<=0){
                        Toast.makeText(RegistActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                    }
                    else if(pw.length()<=0){
                        Toast.makeText(RegistActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                    }
                    else if(pw.length()<6){
                        Toast.makeText(RegistActivity.this,"密码长度需大于六位",Toast.LENGTH_SHORT).show();
                    }
                    else if(pwa.length()<=0){
                        Toast.makeText(RegistActivity.this,"请再次输入密码",Toast.LENGTH_SHORT).show();
                    }
                    else if(!pw.equals(pwa)){
                        Toast.makeText(RegistActivity.this,"两次密码不一致，请重新输入",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(RegistActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                        save(un,pw);
                        saveToBack(un,pw);
                        Intent intent = new Intent(RegistActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
                else {
                    Toast.makeText(RegistActivity.this,"当前用户名已存在",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        setTheTheme();
    }
    private void saveToBack(String un,String pw){
        User user=new User();
        user.setUsername(un);
        user.setPassword(pw);
        user.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
            }
        });
    }
    private void save(String un, String pw) {
        String text = "Account: " + un + "\n" + "Password: " + pw + "\n";
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_APPEND);
            fos.write(text.getBytes());
            fos.flush();
            fos.close();

            fos = openFileOutput(FILE_NAME2, Context.MODE_PRIVATE);
            fos.write(un.getBytes());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            //Log.d("errMsg", e.toString());
        }
    }
}
