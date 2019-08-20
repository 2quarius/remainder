package com.example.trail.Setting;

import com.example.trail.Utility.EnumPack.KeyConstants;
import com.example.trail.Utility.UIHelper.SkinChange.CalendarSchemeColor;
import com.example.trail.Utility.UIHelper.SkinChange.CalendarSelectedThemeColor;
import com.example.trail.Utility.UIHelper.SkinChange.CardBackgroundColor;
import com.example.trail.Utility.UIHelper.SkinChange.EditTextTextColorHint;
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
        SkinConfig.addSupportAttr("cardBackgroundColor",new CardBackgroundColor());
        SkinConfig.addSupportAttr("scheme_text_color",new CalendarSchemeColor());
        SkinConfig.addSupportAttr("selected_theme_color",new CalendarSelectedThemeColor());
        SkinConfig.addSupportAttr("hintTextColor",new EditTextTextColorHint());
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
