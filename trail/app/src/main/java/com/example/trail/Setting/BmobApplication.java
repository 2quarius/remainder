package com.example.trail.Setting;

import com.example.trail.Utility.EnumPack.KeyConstants;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import solid.ren.skinlibrary.SkinConfig;
import solid.ren.skinlibrary.base.SkinBaseApplication;

public class BmobApplication extends SkinBaseApplication {

    // IWXAPI 是第三方app和微信通信的openapi接口
    public static IWXAPI api;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        SkinConfig.setCanChangeStatusColor(true);
        SkinConfig.setCanChangeFont(true);
        SkinConfig.setDebug(true);
        SkinConfig.saveSkinPath(getApplicationContext(),"/Users/sixplus/AndroidStudioProjects/remainder/trail/app/src/main/assets");
        SkinConfig.enableGlobalSkinApply();
        register2WX();
    }

    // 注册微信，相当于微信初始化操作
    public void register2WX() {
        api = WXAPIFactory.createWXAPI(this, KeyConstants.WEIXIN_APP_ID, true);
        api.registerApp(KeyConstants.WEIXIN_APP_ID);
    }
}
