package com.example.trail.NewTask;

import android.animation.Animator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.example.trail.MainActivity;
import com.example.trail.NewTask.SimpleTask.MiniTask;
import com.example.trail.NewTask.SimpleTask.MyLocation;
import com.example.trail.NewTask.SimpleTask.Priority;
import com.example.trail.NewTask.SimpleTask.RemindCycle;
import com.example.trail.NewTask.SimpleTask.Task;
import com.example.trail.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddTaskActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private static final String CHOOSE_A_PLACE = "Place for remind";
    private Integer position;
    private Task task;
    private List<MiniTask> miniTasks = new ArrayList<>();
    //title
    private EditText mTitleEditText;
    //description
    private EditText mDescriptionEditText;
    //ok
    private FloatingActionButton mSendTaskFAB;
    //priority
    private Spinner mPriority;
    //ddl
    private EditText mExpireDateEditText;
    private EditText mExpireTimeEditText;
    //remind
    private SwitchCompat mRemindMeSwitch;
    private LinearLayout mRemindDateLayout;
    private EditText mDateEditText;
    private EditText mTimeEditText;
    private Spinner mRepeatType;
    private TextView mDateTimeReminderTextView;
    //place
    private TextView mExpirePlaceTextView;
    private SwitchCompat mExpirePlaceSwitch;
    private String address;
    private LatLng mLatLng;
    //mini task
    private TextView mMiniTaskText;
    private SwitchCompat mMiniTaskSwitch;
    private LinearLayout mMiniTaskLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Intent intent = getIntent();
        position = intent.getIntExtra("position",-1);
        task = intent.getSerializableExtra("task")!=null? (Task) intent.getSerializableExtra("task") :new Task();
        miniTasks = task.getMiniTasks();
        initLayoutElement();
        setTextByTask();
        installListener();
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
    private void hideKeyboard(EditText et)
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }
    private void installListener() {
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
                Matcher m = Pattern.compile("(\\d{1,2})month(\\d{1,2})day").matcher(s.toString());
                Matcher m2 = Pattern.compile("(\\d{2}):(\\d{2})").matcher(s.toString());
                Matcher m3 = Pattern.compile("(\\d{1,2})-(\\d{1,2})").matcher(s.toString());
                if (m.find()&&task.getExpireTime() == null){
                    System.out.println("find date");
                    Date date = task.getExpireTime() == null ? new Date():task.getExpireTime();
                    hideKeyboard(mTitleEditText);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    int year = calendar.get(Calendar.YEAR);
                    int month = Integer.parseInt(m.group(1));
                    int day = Integer.parseInt(m.group(2));
                    //System.out.println(month);

                    DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(AddTaskActivity.this, year, month-1, day);
                    datePickerDialog.setAccentColor(getResources().getColor(R.color.inputLine));
                    datePickerDialog.show(getFragmentManager(),"ExpireDate");
                }
                else if (m2.find()&&task.getExpireTime() == null){
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
                    //set time
                    int hour = Integer.parseInt(m2.group(1));
                    int minute = Integer.parseInt(m2.group(2));

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
        mPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println(mPriority.getSelectedItem().toString());
                task.setPriority(Priority.match(mPriority.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
        mRemindMeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    task.setRemindTime(null);
                }
                setDateAndTimeEditText(isChecked);
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
        mRepeatType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                task.setRemindCycle(RemindCycle.match(mRepeatType.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mExpirePlaceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mExpirePlaceSwitch.isChecked()) {
                    showDialog();
                }
            }
        });
        mMiniTaskSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mMiniTaskSwitch.isChecked()) {
                    mMiniTaskText.setText("mini tasks:");
                    mMiniTaskSwitch.setVisibility(View.INVISIBLE);
                    //或许还需要将switch disable掉
                    ViewGroup parent = (ViewGroup) mMiniTaskSwitch.getParent();
                    parent.addView(addButton());
                    addNewLine();
                }
            }
        });
    }

    private void addNewLine() {
        final LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.mini_task_checkbox, null);
        final CheckBox checkBox = linearLayout.findViewById(R.id.done);
        final EditText editText = linearLayout.findViewById(R.id.content);
        final int index = miniTasks.size();
        final MiniTask miniTask = new MiniTask();
        miniTask.setDone(false);
        miniTasks.add(miniTask);
        checkBox.setChecked(false);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                miniTask.setDone(b);
                miniTasks.get(index).setDone(b);
                task.setMiniTasks(miniTasks);
            }
        });
        editText.setHint("回车添加新任务");
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                miniTask.setContent(charSequence.toString());
                miniTasks.get(index).setContent(charSequence.toString());
                task.setMiniTasks(miniTasks);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //去掉回车符
                String s = editable.toString();
                if (s.indexOf("\n")>=0){
                    editText.setText(s.replace("\n",""));
                }
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                //回车添加行
                if (i == EditorInfo.IME_ACTION_SEND
                        || i == EditorInfo.IME_ACTION_DONE
                        || (keyEvent != null && KeyEvent.KEYCODE_ENTER == keyEvent.getKeyCode() && KeyEvent.ACTION_DOWN == keyEvent.getAction()))
                {
                    if (editText.getText().length()>0) {
                        addNewLine();
                    }
                }
                //不能删除
                return false;
            }
        });
        mMiniTaskLayout.addView(linearLayout);
        editText.requestFocus();
    }

    private void setTextByTask() {
        //set title text
        mTitleEditText.requestFocus();
        mTitleEditText.setText(task.getTitle());
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        mTitleEditText.setSelection(mTitleEditText.length());
        //set description text
        mDescriptionEditText.setText(task.getDescription());
        mDescriptionEditText.setSelection(mDescriptionEditText.length());
        //set priority
        mPriority.setSelected(task.getPriority()!=null);
        if (task.getPriority()!=null) {
            mPriority.setSelection(Priority.EUGEN.getPriority() - task.getPriority().getPriority());
        }
        //set expire time
        setExpireTime(task.getExpireTime()!=null);
        //set remind me switch and under text
        mRemindMeSwitch.setChecked(task.getRemindTime() != null);
        setEnterDateLayoutVisible(mRemindMeSwitch.isChecked());
        setDateAndTimeEditText(mRemindMeSwitch.isChecked());
        setEnterDateLayoutVisibleWithAnimations(mRemindMeSwitch.isChecked());
        //set expire place switch
        mExpirePlaceSwitch.setChecked(task.getLocation() != null);
        mExpirePlaceTextView.setText(task.getLocation()!=null?task.getLocation().getPlace():mExpirePlaceTextView.getText());
        //set mini task
        mMiniTaskSwitch.setChecked(task.getMiniTasks()!=null&&task.getMiniTasks().size()>0);
        if (mMiniTaskSwitch.isChecked()){
            mMiniTaskText.setText("mini tasks:");
            mMiniTaskSwitch.setVisibility(View.INVISIBLE);
            //或许还需要将switch disable掉
            ViewGroup parent = (ViewGroup) mMiniTaskSwitch.getParent();
            parent.addView(addButton());
            for (final MiniTask miniTask:task.getMiniTasks()){
                LinearLayout linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.mini_task_checkbox,null);
                CheckBox checkBox = linearLayout.findViewById(R.id.done);
                final EditText editText = linearLayout.findViewById(R.id.content);
                checkBox.setChecked(miniTask.getDone());
                editText.setText(miniTask.getContent());
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        miniTask.setDone(b);
                    }
                });
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        miniTask.setContent(charSequence.toString());
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });
                editText.setFocusable(false);
                mMiniTaskLayout.addView(linearLayout);
            }
        }
    }

    private View addButton() {
        ImageButton imgb = (ImageButton) getLayoutInflater().inflate(R.layout.mini_task_add_button,null);
        imgb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewLine();
            }
        });
        return imgb;
    }
    private void setExpireTime(boolean b) {
        if (b)
        {
            String userDate = formatDate("d MMM, yyyy", task.getExpireTime());
            String formatToUse;
            if (DateFormat.is24HourFormat(getApplicationContext())) {
                formatToUse = "k:mm";
            } else {
                formatToUse = "h:mm a";

            }
            String userTime = formatDate(formatToUse, task.getExpireTime());
            mExpireTimeEditText.setText(userTime);
            mExpireDateEditText.setText(userDate);
        }
    }

    private void initLayoutElement() {
        mTitleEditText = (EditText) findViewById(R.id.title);
        mDescriptionEditText = (EditText) findViewById(R.id.description);
        mSendTaskFAB = (FloatingActionButton) findViewById(R.id.send_task_fab);
        mPriority = (Spinner) findViewById(R.id.priority_s);
        mExpireDateEditText = (EditText) findViewById(R.id.expire_date_et);
        mExpireTimeEditText = (EditText) findViewById(R.id.expire_time_et);
        mRemindMeSwitch = (SwitchCompat) findViewById(R.id.remind_me_switch);
        mRemindDateLayout = (LinearLayout) findViewById(R.id.remind_date_layout);
        mDateEditText = (EditText) findViewById(R.id.remind_date);
        mTimeEditText = (EditText) findViewById(R.id.remind_time);
        mRepeatType = (Spinner) findViewById(R.id.repeat_type);
        mDateTimeReminderTextView = (TextView) findViewById(R.id.date_time_reminder_tv);
        mExpirePlaceTextView = (TextView) findViewById(R.id.expire_place_tv);
        mExpirePlaceSwitch = (SwitchCompat) findViewById(R.id.remind_place_switch);
        mMiniTaskText = (TextView) findViewById(R.id.mini_task_tv);
        mMiniTaskSwitch = (SwitchCompat) findViewById(R.id.mini_task_switch);
        mMiniTaskLayout = (LinearLayout) findViewById(R.id.mini_task_input_layout);
    }

    protected void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog customDialog;

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog,null);
        builder.setTitle(CHOOSE_A_PLACE);
        builder.setView(layout);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setExpirePlaceTextView(address);
                MyLocation location = new MyLocation(address);
                location.setLatitude(mLatLng.latitude);
                location.setLongitude(mLatLng.longitude);
                task.setLocation(location);
                mExpirePlaceSwitch.setChecked(task.getLocation()!=null);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setExpirePlaceTextView(mExpirePlaceTextView.getText().toString());
                mExpirePlaceSwitch.setChecked(task.getLocation()!=null);
            }
        });
        customDialog = builder.create();
        //TODO:设置点击事件
        MapView mapView = layout.findViewById(R.id.dialog_map);
        final BaiduMap map = mapView.getMap();
        final BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.location);
        map.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
                //获取经纬度
                mLatLng = latLng;
                double latitude = latLng.latitude;
                double longitude = latLng.longitude;
                System.out.println("latitude=" + latitude + ",longitude=" + longitude);
                //先清除图层
                map.clear();
                // 定义Maker坐标点
                LatLng point = new LatLng(latitude, longitude);
                // 构建MarkerOption，用于在地图上添加Marker
                MarkerOptions options = new MarkerOptions().position(point)
                        .icon(bitmap);
                // 在地图上添加Marker，并显示
                map.addOverlay(options);
                //设置地图新中心点
                map.setMapStatus(MapStatusUpdateFactory.newLatLng(point));
                //实例化一个地理编码查询对象
                GeoCoder geoCoder = GeoCoder.newInstance();
                //设置反地理编码位置坐标
                ReverseGeoCodeOption op = new ReverseGeoCodeOption();
                op.location(latLng);
                geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
                    @Override
                    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
                        //获取点击的坐标地址
                        address = arg0.getAddress();
                    }

                    @Override
                    public void onGetGeoCodeResult(GeoCodeResult arg0) {
                    }
                });
                //发起反地理编码请求(经纬度->地址信息)
                geoCoder.reverseGeoCode(op);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
        customDialog.show();
    }
    //TODO:设置mExpirePlaceTextView文字显示
    private void setExpirePlaceTextView(String place) {
        mExpirePlaceTextView.setText(place);
    }


    private void setEnterDateLayoutVisible(boolean checked) {
        if (checked) {
            mRemindDateLayout.setVisibility(View.VISIBLE);
        } else {
            mRemindDateLayout.setVisibility(View.INVISIBLE);
        }
    }
    private void setDateAndTimeEditText(boolean checked) {
        if (checked) {
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
