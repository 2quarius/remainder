package com.example.trail.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.trail.R;

public class InformationActivity extends AppCompatActivity {
    private EditText name;
    private EditText age;
    private EditText gender;
    private EditText phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
    }
    public String getName(){
        name=findViewById(R.id.et_name);
        String res=name.getText().toString();
        return res;
    }
    public String getAge(){
        age=findViewById(R.id.et_age);
        String res=age.getText().toString();
        return res;
    }
    public String getGender(){
        gender =findViewById(R.id.et_gender);
        String res=gender.getText().toString();
        return res;
    }
    public String getphone(){
        phone =findViewById(R.id.et_phone);
        String res=phone.getText().toString();
        return res;
    }
}
