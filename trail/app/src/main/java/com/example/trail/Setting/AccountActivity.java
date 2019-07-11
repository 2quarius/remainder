package com.example.trail.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trail.R;

public class AccountActivity extends AppCompatActivity {
    private Button btnLogin;
    private Button btnRegister;
    private EditText username;
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        btnLogin=findViewById(R.id.btn_login);
        btnRegister=findViewById(R.id.btn_register);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AccountActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AccountActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getUsername(){
        username=findViewById(R.id.et_username);
        String un=username.getText().toString();
        return un;
    }

    public String getPassword(){
        password=findViewById(R.id.et_password);
        String pw=password.getText().toString();
        return pw;
    }

}
