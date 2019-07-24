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

import java.io.FileOutputStream;

public class ShowAccountActivity extends AppCompatActivity {
    private String account;
    final  private String FILE_NAME = "information.txt";
    private TextView showaccount;
    private Button logout;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        back=findViewById(R.id.btn_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
