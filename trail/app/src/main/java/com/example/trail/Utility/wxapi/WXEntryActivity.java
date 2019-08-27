package com.example.trail.Utility.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.trail.Http.HttpUtil;
import com.example.trail.MainActivity;
import com.example.trail.R;
import com.example.trail.Setting.BmobApplication;
import com.example.trail.Utility.EnumPack.KeyConstants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weixin);
        BmobApplication.api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        BmobApplication.api.handleIntent(intent, this);
    }

    //微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX://
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                break;
            default:
                break;
        }

    }

    //第三方应用请求微信登陆的响应结果会通过该方法回调给第三方， 也就是说微信登录授权成功后，会回调到该方法，
    @Override
    public void onResp(BaseResp resp) {
        Log.i("smile", "响应code = "+resp.errCode);
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                final String code = ((SendAuth.Resp) resp).code;
                // 通过拿到的code，去请求token
                new Thread() {
                    @Override
                    public void run() {
                        String result = HttpUtil.getResponse("https://api.weixin.qq.com/sns/oauth2/access_token?appid="+ KeyConstants.WEIXIN_APP_ID+"&secret="+KeyConstants.WEIXIN_APP_SECRET+"&code="+code+"&grant_type=authorization_code");
                        Log.i("smile", "微信平台返回的token:" + result);
                        loginByWX(result);
                    }

                }.start();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Toast.makeText(WXEntryActivity.this, "用户拒绝授权", Toast.LENGTH_LONG).show();
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Toast.makeText(WXEntryActivity.this, "用户取消授权", Toast.LENGTH_LONG).show();
                finish();
                break;
            default:
                break;
        }
    }


    /**微信授权登录
     * @method loginByWX
     * @param result
     * @return void
     * @exception
     */
    private void loginByWX(String result){
        JSONObject obj;
        try {
            obj = new JSONObject(result);
            String token = obj.getString("access_token");
            String expires = String.valueOf(obj.getLong("expires_in"));
            String openid = obj.getString("openid");
            // 调用Bmob提供的授权登录方法进行微信登陆，登录成功后，你就可以在后台管理界面的User表中看到微信登陆后的用户啦
            final BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(BmobUser.BmobThirdUserAuth.SNS_TYPE_WEIXIN, token, expires, openid);
            BmobUser.loginWithAuthData(authInfo, new LogInListener<JSONObject>() {
                @Override
                public void done(JSONObject jsonObject, BmobException e) {
                    if (e==null){
                        Log.i("smile", authInfo.getSnsType() + "登陆成功返回:"+ jsonObject);
                        Intent intent = new Intent(WXEntryActivity.this, MainActivity.class);
                        intent.putExtra("json", jsonObject.toString());
                        intent.putExtra("from", authInfo.getSnsType());
                        startActivity(intent);
                    } else {
                        Toast.makeText(WXEntryActivity.this,
                                       "第三方登陆失败：" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        } catch (JSONException e) {
        }
    }
}

