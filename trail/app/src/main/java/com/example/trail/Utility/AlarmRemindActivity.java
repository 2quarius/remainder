package com.example.trail.Utility;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trail.NewTask.SimpleTask.Task;
import com.example.trail.R;
import com.example.trail.Setting.AccountActivity;
import com.example.trail.Setting.RegistActivity;

public class AlarmRemindActivity extends AppCompatActivity {
    private TextView title;
    private  TextView description;
    private Button endRemind;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_remind);

        /*title = findViewById(R.id.show_title);
        description = findViewById(R.id.show_description);
        endRemind = findViewById(R.id.btn_end_remind);

        Intent intent = getIntent();
        task = (Task) intent.getSerializableExtra("task");
        title.setText(task.title);
        description.setText(task.description);
        Intent newintent = new Intent();
        intent.setAction("alarm_remind");
        sendBroadcast(newintent);

        endRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });*/
    }
}
