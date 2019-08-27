package com.example.trail.AlarmRemind;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trail.R;

public class AlarmRemindActivity extends AppCompatActivity {
    private TextView title;
    private  TextView description;
    private Button endRemind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_remind);

        title = findViewById(R.id.show_title);
        description = findViewById(R.id.show_description);
        endRemind = findViewById(R.id.btn_end_remind);

        Intent intent = getIntent();
        String thetitle = intent.getStringExtra("title");
        String thedescription = intent.getStringExtra("description");
        //title.setText("aaaa");
        //description.setText("aaaa");
        title.setText(thetitle);
        description.setText(thedescription);

        endRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
