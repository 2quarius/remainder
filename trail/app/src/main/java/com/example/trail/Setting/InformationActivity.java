package com.example.trail.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.trail.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class InformationActivity extends AppCompatActivity {
    private EditText name;
    private EditText age;
    private EditText gender;
    private EditText phone;
    private Button btnSave;
    private ImageButton back;
    final  private String FILE_NAME = "information.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        //返回
        back=findViewById(R.id.btn_infoBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //保存
        btnSave=findViewById(R.id.btn_saveInfo);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!searchName(getName())||
                        !searchAge(getAge())||
                        !searchGender(getGender())||
                        !searchPhone(getphone())) {
                    save("name: "+getName());
                    save("age: "+getAge());
                    save("gender: "+getGender());
                    save("phone: "+getphone());
                    Toast.makeText(InformationActivity.this,"保存成功",Toast.LENGTH_SHORT).show();;
                }
                else if(getName().length()<=0
                        &&getAge().length()<=0
                        &&getGender().length()<=0
                        &&getphone().length()<=0){
                    Toast.makeText(InformationActivity.this,"信息不能为空",Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(InformationActivity.this,"信息已存在",Toast.LENGTH_SHORT).show();;
            }
        });

        //读取
        name=findViewById(R.id.et_name);
        age=findViewById(R.id.et_age);
        gender=findViewById(R.id.et_gender);
        phone=findViewById(R.id.et_phone);
//        name.setText("name");
//        age.setText("age");
//        gender.setText("gender");
//        phone.setText("phone");
        String content=readFile();
        if(content.contains("name")) name.setText(findName());
        if(content.contains("age")) age.setText(findAge());
        if(content.contains("gender")) gender.setText(findGender());
        if(content.contains("phone")) phone.setText(findPhone());
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

    //在文件中搜索信息
    private String findName(){
        String text=readFile();
        String name="";
        int beginIndex=text.lastIndexOf("name: ");
        for(int i=beginIndex;i<text.length();){
            if(text.charAt(i)!='\n') i++;
            else {
                name=text.substring(beginIndex+6,i);
                break;
            }
        }
        return name;
    }


    private String findAge(){
        String text=readFile();
        String age="";
        int beginIndex=text.lastIndexOf("age: ");
        for(int i=beginIndex;i<text.length();){
            if(text.charAt(i)!='\n') i++;
            else {
                age=text.substring(beginIndex+5,i);
                break;
            }
        }
        return age;
    }


    private String findGender(){
        String text=readFile();
        String gender="";
        int beginIndex=text.lastIndexOf("gender: ");
        for(int i=beginIndex;i<text.length();){
            if(text.charAt(i)!='\n') i++;
            else {
                gender=text.substring(beginIndex+8,i);
                break;
            }
        }
        return gender;
    }

    private String findPhone(){
        String text=readFile();
        String phone="";
        int beginIndex=text.lastIndexOf("phone: ");
        for(int i=beginIndex;i<text.length();){
            if(text.charAt(i)!='\n') i++;
            else {
                phone=text.substring(beginIndex+7,i);
                break;
            }
        }
        return phone;
    }
    private boolean searchName(String name){
        String text=readFile();
        if(text.contains("name: "+name+"\n")) return true;
        else return false;
    }

    private boolean searchAge(String age){
        String text=readFile();
        if(text.contains("age: "+age+"\n")) return true;
        else return false;
    }

    private boolean searchGender(String gender){
        String text=readFile();
        if(text.contains("gender: "+gender+"\n")) return true;
        else return false;
    }

    private boolean searchPhone(String phone){
        String text=readFile();
        if(text.contains("phone: "+phone+"\n")) return true;
        else return false;
    }

    private void save(String text) {
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
//    private void clear(String text) {
//        try {
//            FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_ENABLE_WRITE_AHEAD_LOGGING);
//            fos.write(text.getBytes());
//            fos.write("\n".getBytes());//格式化
//            fos.flush();
//            fos.close();
//
//        } catch (Exception e) {
//            Log.d("errMsg", e.toString());
//        }
//    }
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
}
