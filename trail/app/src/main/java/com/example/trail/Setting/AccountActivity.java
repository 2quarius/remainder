package com.example.trail.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trail.MainActivity;
import com.example.trail.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class AccountActivity extends AppCompatActivity {
    private Button btnLogin;
    private Button btnRegister;
    private EditText username;
    private EditText password;
    final  private String FILE_NAME = "information.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        btnLogin=findViewById(R.id.btn_login);
        btnRegister=findViewById(R.id.btn_register);
        btnLogin.setOnClickListener(new View.OnClickListener() {//登录
            @Override
            public void onClick(View view) {
                if(getUsername().length()<=0){ //是否输入密码
                    Toast.makeText(AccountActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                }
                else if(getPassword().length()<=0){
                    Toast.makeText(AccountActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                }
                else {//判断是否符合条件
                    if(!searchAccount(getUsername())){
                        Toast.makeText(AccountActivity.this,"不存在此用户，请注册",Toast.LENGTH_SHORT).show();
                    }
                    else if(!searchPassword(getUsername(),getPassword())){
                        Toast.makeText(AccountActivity.this,"密码错误，请重新输入",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(AccountActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(AccountActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {//注册
            @Override
            public void onClick(View view) {
                if(getUsername().length()<=0){//用户名 密码不能为空
                    Toast.makeText(AccountActivity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                }
                else if(getPassword().length()<=0){//用户名 密码不能为空
                    Toast.makeText(AccountActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(AccountActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    String inputAccount="Account: "+getUsername();
                    save(inputAccount);
                    String inputPassword="Password: "+getPassword();
                    save(inputPassword);
                }
            }
        });
    }

    public String getUsername(){
        username=findViewById(R.id.et_username);
        String un="";
        un=username.getText().toString();
        return un;
    }

    public String getPassword(){
        password=findViewById(R.id.et_password);
        String pw=password.getText().toString();
        return pw;
    }

    public void save(String text) {
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_APPEND);
            fos.write(text.getBytes());
            fos.write("\n".getBytes());//格式化
            fos.flush();
            fos.close();

        } catch (Exception e) {
            Log.d("errMsg", e.toString());
        }
    }

    private String readFile(){
        String textContent="";
        try {
            FileInputStream ios = openFileInput(FILE_NAME);
            byte[] temp = new byte[1024];
            StringBuilder sb = new StringBuilder("");
            int len = 0;
            while ((len = ios.read(temp)) > 0){
                sb.append(new String(temp, 0, len));
            }
            ios.close();
            textContent= sb.toString();
        }catch (Exception e) {
            Log.d("errMsg", e.toString());
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
    public boolean searchAccount(String account){
        String text=readFile();
        String pattern="Account: "+account+"\n";
        if(text.contains(pattern)) return true;
        else return false;
    }

    //密码判断
    public boolean searchPassword(String account,String password){
        String textContent=readFile();
        String pattern="Account: "+account+"\nPassword: "+password+"\n";
        if(textContent.contains(pattern))return true;
        else return false;
    }

}
