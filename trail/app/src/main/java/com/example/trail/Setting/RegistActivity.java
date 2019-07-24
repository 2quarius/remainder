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

public class RegistActivity extends AppCompatActivity {
    private Button btnRegist;
    private EditText username;
    private EditText password;
    private EditText passwordagain;
    private ImageButton back;
    final  private String FILE_NAME = "account.txt";
    final  private String FILE_NAME2 = "information.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        btnRegist=findViewById(R.id.btn_regist);

        back=findViewById(R.id.btn_accountBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnRegist.setOnClickListener(new View.OnClickListener() {//登录
            @Override
            public void onClick(View view) {
                username = findViewById(R.id.et_username);
                String un = username.getText().toString();

                password = findViewById(R.id.et_password);
                String pw = password.getText().toString();

                passwordagain = findViewById(R.id.et_passwordAgain);
                String pwa = passwordagain.getText().toString();

                if(!searchAccount(un)){
                    if(un.length()<=0){
                        Toast.makeText(RegistActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                    }
                    else if(pw.length()<=0){
                        Toast.makeText(RegistActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
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
                        Intent intent = new Intent(RegistActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
                else {
                    Toast.makeText(RegistActivity.this,"用户名已存在",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void save(String un, String pw) {
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
            Log.d("errMsg", e.toString());
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
            Log.d("errMsg", e.toString());
        }
        return textContent;
    }
    //查询text
    private boolean searchFile(String text){
        String textContent = readFile();
        if(textContent.contains(text))return true;
        else return false;
    }

    //用户名判断
    private boolean searchAccount(String account){
        String text = readFile();
        String pattern="Account: " + account+"\n";
        if(text.contains(pattern)) return true;
        else return false;
    }

    //密码判断
    private boolean searchPassword(String account,String password){
        String textContent = readFile();
        String pattern = "Account: " + account + "\nPassword: " + password+"\n";
        if(textContent.contains(pattern))return true;
        else return false;
    }

}
