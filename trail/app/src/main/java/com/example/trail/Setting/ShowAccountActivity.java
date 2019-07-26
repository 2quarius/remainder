package com.example.trail.Setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trail.MainActivity;
import com.example.trail.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ShowAccountActivity extends AppCompatActivity {
    private String account;
    final  private String FILE_NAME = "information.txt";
    final  private String FILE_NAME3 = "theme.txt";
    private TextView showaccount;
    private Button logout;
    private ImageButton back;

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
        setContentView(R.layout.activity_show_account);
        Intent intent = getIntent();
        account = intent.getStringExtra("account");
        showaccount = findViewById(R.id.showaccount);
        showaccount.setText(account);
        logout = findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {//注销
            @Override
            public void onClick(View view) {
                String text = "";
                try {
                    FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
                    fos.write(text.getBytes());
                    fos.flush();
                    fos.close();

                } catch (Exception e) {
                    //Log.d("errMsg", e.toString());
                }
                Intent intent = new Intent(ShowAccountActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        back=findViewById(R.id.btn_showAccountBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        setTheTheme();
    }
}
