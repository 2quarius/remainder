package com.example.trail.Setting;

import com.example.trail.NewTask.Collection.TaskCollector;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class User extends BmobUser {
    //仅jaccount账号登录才有此字段
    private String accessToken;
    private BmobFile avatar;
    private List<TaskCollector> taskCollectors;
}
