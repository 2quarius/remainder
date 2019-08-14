package com.example.trail.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.trail.NewTask.SimpleTask.Task;
import com.example.trail.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class SettingFragmnet extends Fragment {
    private ImageButton btnAccountSetHead;
    private Button btnAccountSet;
    private Button btnThemeSet;
    private Button btnTableList;
    private Button btnVoice;
    private Button btnAbout;
    private Button backup;
    private SettingFragmnet settingFragmnet;
    private FloatingActionButton fab;
    private String account;
    final  private String FILE_NAME = "information.txt";
    public interface backClass{
        void sTasks(List<Task> mTasks);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_setting,container,false);
        fab=getActivity().findViewById(R.id.fab_addTask);
        fab.setVisibility(View.INVISIBLE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        account = bundle.getString("account");
        //跳转到Account界面
        btnAccountSetHead=view.findViewById(R.id.btn_headPic);
        btnAccountSetHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (account.length()<=0||account.equals("failed")) {
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getActivity(),ShowAccountActivity.class);
                    intent.putExtra("account",account);
                    startActivity(intent);
                }
            }

        });
        btnAccountSet=view.findViewById(R.id.btn_accountSetting);
        btnAccountSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (account.length()<=0) {
                    Intent intent = new Intent(getActivity(),AccountActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(getActivity(),ShowAccountActivity.class);
                    intent.putExtra("account",account);
                    startActivity(intent);
                }
            }

        });
        //跳转到桌面清单设置界面
        /*btnTableList=view.findViewById(R.id.btn_tableList);
        btnTableList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),TableListSetActivity.class);
                startActivity(intent);
            }
        });*/
        //跳转到主题设置界面
        btnThemeSet=view.findViewById(R.id.btn_theme);
        btnThemeSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ThemeActivity.class);
                startActivity(intent);
            }
        });
        //跳转到声音设置界面
        btnVoice=view.findViewById(R.id.btn_voice);
        btnVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),VoiceActivity.class);
                startActivity(intent);
            }
        });
        //跳转到关于我们界面
        btnAbout=view.findViewById(R.id.btn_about);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AboutActivity.class);
                startActivity(intent);
            }
        });
        backup=view.findViewById(R.id.btn_backup);
//        backup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final List<Task> bTasks=new ArrayList<>();
//                BmobQuery<Task> cloudList=new BmobQuery<>();
//                cloudList.findObjects(new FindListener<Task>() {
//                    @Override
//                    public void done(List<Task> list, BmobException e) {
//                        for(int i=0;i<list.size();i++){
//                            if(list.get(i).getUsername().equals("999")){
//                                Log.d(TAG, "done: "+list.get(i).getTitle());
//                                Toast.makeText(getActivity(),list.get(i).getTitle(),Toast.LENGTH_SHORT).show();
//                                Task task=new Task();
//                                task.setTitle(list.get(i).getTitle());
//                                task.setDescription(list.get(i).getDescription());
//                                bTasks.add(task);
//                            }
//                        }
//                        ((SettingFragmnet.backClass)getActivity()).sTasks(bTasks);
//                    }
//                });
//
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fab=getActivity().findViewById(R.id.fab_addTask);
        fab.setVisibility(View.VISIBLE);
    }

}
