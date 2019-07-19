package com.example.trail.TimeRemind;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.trail.MainActivity;
import com.example.trail.NewTask.SimpleTask.Task;
import com.example.trail.R;

public class AddTasktoServiceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_taskto_service);
    }

    public void onStart(Intent data) {
        Task task = (Task) data.getSerializableExtra("Task");
        Intent intent = new Intent(AddTasktoServiceActivity.this, TimeRemindService.class);
        intent.putExtra("task",task);
        startService(intent);
    }
}
