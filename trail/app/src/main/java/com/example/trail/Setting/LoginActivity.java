package com.example.trail.Setting;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trail.R;
import com.example.trail.Utility.EnumPack.KeyConstants;
import com.example.trail.Utility.Utils.BmobUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends Activity implements View.OnClickListener{
    private EditText etAccount, etPwd;

    private Button btnQq, btnWeixin;

    private Button btnLogin, btnRegister;
    private String account,pwd;
    public static Tencent mTencent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        User user = BmobUser.getCurrentUser(User.class);
        if(user!=null){
            //TODO 备份
            this.finish();
        }
    }
    private void initView(){
        etAccount = (EditText)findViewById(R.id.et_account);
        etPwd = (EditText)findViewById(R.id.et_pwd);

        btnQq = (Button)findViewById(R.id.btn_qq);
        btnWeixin = (Button)findViewById(R.id.btn_weixin);
        btnLogin = (Button)findViewById(R.id.btn_login);
        btnRegister = (Button)findViewById(R.id.btn_register);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnQq.setOnClickListener(this);
        btnWeixin.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login://登陆
                account = etAccount.getText().toString().trim();
                pwd = etPwd.getText().toString().trim();
                if(account.equals("")){
                    toast("填写你的用户名");
                    return;
                }
                if(pwd.equals("")){
                    toast("填写你的密码");
                    return;
                }
                final User user = new User();
                user.setUsername(account);
                user.setPassword(pwd);
                user.login(new SaveListener<BmobUser>() {
                    @Override
                    public void done(BmobUser user, BmobException e) {
                        if (e==null){
                            toast("登录成功："+user.getUsername());
                            LoginActivity.this.finish();
                        } else {
                            toast("登录失败：" + e.getMessage());
                        }
                    }
                });
                break;
            case R.id.btn_register://注册
                account= etAccount.getText().toString().trim();
                pwd = etPwd.getText().toString().trim();
                if(account.equals("")){
                    toast("填写你的用户名");
                    return;
                }
                if(pwd.equals("")){
                    toast("填写你的密码");
                    return;
                }
                User u = new User();
                u.setUsername(account);
                u.setPassword(pwd);
                u.signUp(new SaveListener<BmobUser>() {
                    @Override
                    public void done(BmobUser user, BmobException e) {
                        if (e==null){
                            toast("注册成功");
                        } else {
                            toast("注册失败：" + e.getMessage());
                        }
                    }
                });
                break;
            case R.id.btn_qq://QQ授权登录
                qqAuthorize();
                break;
            case R.id.btn_weixin:
                //微信登陆，文档可查看：https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&lang=zh_CN&token=0ba3e6d1a13e26f864ead7c8d3e90b15a3c6c34c
                //发起微信登陆授权的请求
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "bmob_third_login_demo";//可随便写，微信会原样返回
                boolean isOk = BmobApplication.api.sendReq(req);
                Log.i("smile", "是否发送成功："+isOk);
                break;
            default:
                break;
        }
    }
    public void loginWithAuth(final BmobUser.BmobThirdUserAuth authInfo){
        BmobUser.loginWithAuthData(authInfo, new LogInListener<JSONObject>() {
            @Override
            public void done(JSONObject jsonObject, BmobException e) {
                if (e==null){
                    //TODO qq登录成功，返回上一级
                    Log.i("smile",authInfo.getSnsType()+"登陆成功返回:"+jsonObject);
                    BmobUtils.queryTaskCollectors();
                    LoginActivity.this.finish();
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    intent.putExtra("json", jsonObject.toString());
//                    intent.putExtra("from", authInfo.getSnsType());
//                    startActivity(intent);
                } else {
                    Log.e("BMOB", e.toString());
                    toast("第三方登陆失败："+e.getMessage());
                }
            }
        });
    }
    private void qqAuthorize(){
        if(mTencent==null){
            mTencent = Tencent.createInstance(KeyConstants.QQ_APP_ID, getApplicationContext());
        }
//        mTencent.logout(this);
        mTencent.login(this, "all", new IUiListener() {
            @Override
            public void onComplete(Object arg0) {
                if(arg0!=null){
                    JSONObject jsonObject = (JSONObject) arg0;
                    try {
                        String token = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_ACCESS_TOKEN);
                        String expires = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_EXPIRES_IN);
                        String openId = jsonObject.getString(com.tencent.connect.common.Constants.PARAM_OPEN_ID);
                        BmobUser.BmobThirdUserAuth authInfo = new BmobUser.BmobThirdUserAuth(BmobUser.BmobThirdUserAuth.SNS_TYPE_QQ, token, expires, openId);
                        loginWithAuth(authInfo);
                    } catch (JSONException e) {
                    }
                }
            }

            @Override
            public void onError(UiError arg0) {
                toast("QQ授权出错："+arg0.errorCode+"--"+arg0.errorDetail);
            }

            @Override
            public void onCancel() {
                toast("取消qq授权");
            }
        });
    }
    private void toast(String msg){
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

}
