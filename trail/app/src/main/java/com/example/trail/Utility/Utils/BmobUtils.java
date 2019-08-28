package com.example.trail.Utility.Utils;

import android.util.Log;

import com.example.trail.MainActivity;
import com.example.trail.NewTask.Collection.TaskCollector;
import com.example.trail.Setting.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class BmobUtils {
    public static void updateTaskCollector(List<TaskCollector> taskCollectors){
        if (BmobUser.isLogin()){
            User user = BmobUser.getCurrentUser(User.class);
            user.setTaskCollectors(taskCollectors);
            user.update(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e==null){
                        Log.d("BMOB","用户taskCollectors信息更新成功");
                    }
                    else {
                        Log.d("BMOB",e.toString());
                    }
                }
            });
        }
    }
    public static void queryTaskCollectors(){
        if (BmobUser.isLogin()){
            User user = BmobUser.getCurrentUser(User.class);
            BmobQuery<User> bmobQuery = new BmobQuery<>();
            bmobQuery.addWhereEqualTo("objectId",user.getObjectId());
            bmobQuery.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> list, BmobException e) {
                    if (e==null){
                        MainActivity.taskCollectors = list.get(0).getTaskCollectors();
                        Log.d("BMOB",String.valueOf(list.get(0).getTaskCollectors().size()));
                    }
                    else {
                        Log.d("BMOB",e.toString());
                    }
                }
            });
        }
    }
}
