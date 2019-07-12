package com.example.trail.NewTask;

import com.example.trail.MainActivity;
import com.example.trail.NewTask.SimpleTask.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;


import com.example.trail.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTaskActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private Integer position;
    private Task task;
    private EditText mTitleEditText;
    private EditText mDescriptionEditText;
    private FloatingActionButton mSendTaskFAB;
    private EditText mExpireDateEditText;
    private EditText mExpireTimeEditText;
    private SwitchCompat mRemindMeSwitch;
    private LinearLayout mRemindDateLayout;
    private EditText mDateEditText;
    private EditText mTimeEditText;
    private Spinner mRepeatType;
    private TextView mDateTimeReminderTextView;
    private void hideKeyboard(EditText et)
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Intent intent = getIntent();
        position = intent.getIntExtra("position",-1);
        task = intent.getSerializableExtra("task")!=null? (Task) intent.getSerializableExtra("task") :new Task();
        mTitleEditText = (EditText) findViewById(R.id.title);
        mDescriptionEditText = (EditText) findViewById(R.id.description);
        mSendTaskFAB = (FloatingActionButton) findViewById(R.id.send_task_fab);
        mExpireDateEditText = (EditText) findViewById(R.id.expire_date_et);
        mExpireTimeEditText = (EditText) findViewById(R.id.expire_time_et);
        mRemindMeSwitch = (SwitchCompat) findViewById(R.id.remind_me_switch);
        mRemindDateLayout = (LinearLayout) findViewById(R.id.remind_date_layout);
        mDateEditText = (EditText) findViewById(R.id.remind_date);
        mTimeEditText = (EditText) findViewById(R.id.remind_time);
        mRepeatType = (Spinner) findViewById(R.id.repeat_type);
        mDateTimeReminderTextView = (TextView) findViewById(R.id.date_time_reminder_tv);

        mTitleEditText.requestFocus();
        mTitleEditText.setText(task.getTitle());
        mDescriptionEditText.setText(task.getDescription());
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        mTitleEditText.setSelection(mTitleEditText.length());
        mTitleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                task.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mDescriptionEditText.setText(task.getDescription());
        mDescriptionEditText.setSelection(mDescriptionEditText.length());
        mDescriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                task.setDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mSendTaskFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTitleEditText.length()<=0){
                    mTitleEditText.setError("please enter a todo");
                }
                else if (position==-1){
                    Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
                    intent.putExtra("task",task);
                    setResult(RESULT_OK,intent);
                    AddTaskActivity.this.finish();
//                    startActivity(intent);
                }
                else if (position!=-1){
                    Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
                    intent.putExtra("position",position);
                    intent.putExtra("task",task);
                    setResult(RESULT_OK,intent);
                    AddTaskActivity.this.finish();
                }
            }
        });

        mExpireDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = task.getExpireTime() == null ? new Date():task.getExpireTime();
                hideKeyboard(mTitleEditText);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(AddTaskActivity.this, year, month, day);
                datePickerDialog.setAccentColor(getResources().getColor(R.color.inputLine));
                datePickerDialog.show(getFragmentManager(),"ExpireDate");
            }
        });

        mExpireTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = task.getExpireTime() == null ? new Date():task.getExpireTime();
                hideKeyboard(mTitleEditText);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
                            Calendar calendar = Calendar.getInstance();
                            if (task.getRemindTime() != null) {
                                calendar.setTime(task.getRemindTime());
                            }
                            int year = calendar.get(Calendar.YEAR);
                            int month = calendar.get(Calendar.MONTH);
                            int day = calendar.get(Calendar.DAY_OF_MONTH);
                            Log.d("OskarSchindler", "Time set: " + hourOfDay);
                            calendar.set(year, month, day, hourOfDay, minute, 0);
                            task.setExpireTime(calendar.getTime());
                            setTimeEditText(task.getExpireTime(),mExpireTimeEditText);
                    }
                }, hour, minute, DateFormat.is24HourFormat(getApplicationContext()));
                timePickerDialog.setAccentColor(getResources().getColor(R.color.inputLine));
                timePickerDialog.show(getFragmentManager(), "ExpireTime");
            }
        });
        setEnterDateLayoutVisible(mRemindMeSwitch.isChecked());
        mRemindMeSwitch.setChecked(task.getRemindTime() != null);
        if (task.getRemindTime() != null){
            setDateAndTimeEditText();
            setEnterDateLayoutVisibleWithAnimations(true);
            hideKeyboard(mTitleEditText);
            hideKeyboard(mDescriptionEditText);
        }
        mRemindMeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    task.setRemindTime(null);
                }
                setDateAndTimeEditText();
                setEnterDateLayoutVisibleWithAnimations(isChecked);
                hideKeyboard(mTitleEditText);
                hideKeyboard(mDescriptionEditText);
            }
        });
        mDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = task.getRemindTime() == null ? new Date():task.getRemindTime();
                hideKeyboard(mTitleEditText);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(AddTaskActivity.this, year, month, day);
                datePickerDialog.setAccentColor(getResources().getColor(R.color.inputLine));
                datePickerDialog.show(getFragmentManager(),"DateFragment");
            }
        });

        mTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = task.getRemindTime() == null ? new Date():task.getRemindTime();
                hideKeyboard(mTitleEditText);

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(AddTaskActivity.this, hour, minute, DateFormat.is24HourFormat(getApplicationContext()));
                timePickerDialog.setAccentColor(getResources().getColor(R.color.inputLine));
                timePickerDialog.show(getFragmentManager(), "TimeFragment");
            }
        });
        if (task.getRemindTime() == null) {
            mRemindMeSwitch.setChecked(false);
            mDateTimeReminderTextView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if (view.getTag().equals("ExpireDate")){
            Calendar calendar = Calendar.getInstance();
            int hour, minute;
            Calendar reminderCalendar = Calendar.getInstance();
            reminderCalendar.set(year, monthOfYear, dayOfMonth);
            if (reminderCalendar.before(calendar)) {
                return;
            }
            if (task.getExpireTime() != null) {
                calendar.setTime(task.getExpireTime());
            }
            if (DateFormat.is24HourFormat(getApplicationContext())) {
                hour = calendar.get(Calendar.HOUR_OF_DAY);
            } else {
                hour = calendar.get(Calendar.HOUR);
            }
            minute = calendar.get(Calendar.MINUTE);

            calendar.set(year, monthOfYear, dayOfMonth, hour, minute);
            task.setExpireTime(calendar.getTime());
            setDateEditText(task.getExpireTime(),mExpireDateEditText);
        }
        setDate(year,monthOfYear,dayOfMonth);
    }
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        setTime(hourOfDay,minute);
    }
    private void setEnterDateLayoutVisible(boolean checked) {
        if (checked) {
            mRemindDateLayout.setVisibility(View.VISIBLE);
        } else {
            mRemindDateLayout.setVisibility(View.INVISIBLE);
        }
    }
    private void setDateAndTimeEditText() {
        if (task.getRemindTime() != null) {
            String userDate = formatDate("d MMM, yyyy", task.getRemindTime());
            String formatToUse;
            if (DateFormat.is24HourFormat(getApplicationContext())) {
                formatToUse = "k:mm";
            } else {
                formatToUse = "h:mm a";

            }
            String userTime = formatDate(formatToUse, task.getRemindTime());
            mTimeEditText.setText(userTime);
            mDateEditText.setText(userDate);

        } else {
            mDateEditText.setText(getString(R.string.date_reminder_default));
            boolean time24 = DateFormat.is24HourFormat(getApplicationContext());
            Calendar cal = Calendar.getInstance();
            if (time24) {
                cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + 1);
            } else {
                cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + 1);
            }
            cal.set(Calendar.MINUTE, 0);
            task.setRemindTime(cal.getTime());
            mDateEditText.setText(task.getRemindTime().toString().substring(0,11));
            Log.d("OskarSchindler", "Imagined Date: " + task.getRemindTime());
            String timeString;
            if (time24) {
                timeString = formatDate("k:mm", task.getRemindTime());
            } else {
                timeString = formatDate("h:mm a", task.getRemindTime());
            }
            mTimeEditText.setText(timeString);
        }
    }
    private void setEnterDateLayoutVisibleWithAnimations(boolean checked) {
        if (checked) {
            setReminderTextView();
            mRemindDateLayout.animate().alpha(1.0f).setDuration(500).setListener(
                    new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            mRemindDateLayout.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    }
            );
        } else {
            mRemindDateLayout.animate().alpha(0.0f).setDuration(500).setListener(
                    new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            mRemindDateLayout.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    }
            );
        }

    }
    private void setDate(int year,int monthOfYear, int dayOfMonth){
        Calendar calendar = Calendar.getInstance();
        int hour, minute;
        Calendar reminderCalendar = Calendar.getInstance();
        reminderCalendar.set(year, monthOfYear, dayOfMonth);

        if (reminderCalendar.before(calendar)) {
            return;
        }
        if (task.getRemindTime() != null) {
            calendar.setTime(task.getRemindTime());
        }
        if (DateFormat.is24HourFormat(getApplicationContext())) {
            hour = calendar.get(Calendar.HOUR_OF_DAY);
        } else {
            hour = calendar.get(Calendar.HOUR);
        }
        minute = calendar.get(Calendar.MINUTE);

        calendar.set(year, monthOfYear, dayOfMonth, hour, minute);
        task.setRemindTime(calendar.getTime());
        setReminderTextView();
        setDateEditText(task.getRemindTime(),mDateEditText);
    }
    private void setTime(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        if (task.getRemindTime() != null) {
            calendar.setTime(task.getRemindTime());
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Log.d("OskarSchindler", "Time set: " + hour);
        calendar.set(year, month, day, hour, minute, 0);
        task.setRemindTime(calendar.getTime());

        setReminderTextView();
        setTimeEditText(task.getRemindTime(),mTimeEditText);
    }
    private void setReminderTextView() {
        if (task.getRemindTime() != null) {
            mDateTimeReminderTextView.setVisibility(View.VISIBLE);
            Date date = task.getRemindTime();
            if (date.before(new Date())) {
                Log.d("OskarSchindler", "DATE is " + date);
                mDateTimeReminderTextView.setText(getString(R.string.date_error_check_again));
                mDateTimeReminderTextView.setTextColor(getResources().getColor(R.color.warning));
                return;
            }
            String dateString = formatDate("d MMM, yyyy", date);
            String timeString;
            String amPmString = "";

            if (DateFormat.is24HourFormat(getApplicationContext())) {
                timeString = formatDate("k:mm", date);
            } else {
                timeString = formatDate("h:mm", date);
                amPmString = formatDate("a", date);
            }
            String finalString = String.format(getResources().getString(R.string.remind_date_and_time), dateString, timeString, amPmString);
            mDateTimeReminderTextView.setTextColor(getResources().getColor(R.color.ok));
            mDateTimeReminderTextView.setText(finalString);
        } else {
            mDateTimeReminderTextView.setVisibility(View.INVISIBLE);

        }
    }
    private void setDateEditText(Date date,EditText editText) {
        String dateFormat = "d MMM, yyyy";
        editText.setText(formatDate(dateFormat, date));
    }
    private void setTimeEditText(Date date,EditText editText) {
        String dateFormat;
        if (DateFormat.is24HourFormat(getApplicationContext())) {
            dateFormat = "k:mm";
        } else {
            dateFormat = "h:mm a";
        }
        editText.setText(formatDate(dateFormat, date));
    }
    private static String formatDate(String formatString, Date dateToFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString);
        return simpleDateFormat.format(dateToFormat);
    }

}
