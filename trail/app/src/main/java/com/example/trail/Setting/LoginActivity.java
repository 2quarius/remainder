package com.example.trail.Setting;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.trail.Http.HttpUtil;
import com.example.trail.R;
import com.example.trail.Utility.EnumPack.KeyConstants;
import com.example.trail.Utility.Utils.BmobUtils;
import com.example.trail.Utility.Utils.DESUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import solid.ren.skinlibrary.base.SkinBaseActivity;


public class LoginActivity extends SkinBaseActivity implements View.OnClickListener{
    private EditText etAccount, etPwd;
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
        LinearLayout back = findViewById(R.id.back);
        TextView title = findViewById(R.id.title);
        title.setText("登录");
        etAccount = findViewById(R.id.et_account);
        etPwd = findViewById(R.id.et_pwd);

        ImageButton btnQq = findViewById(R.id.btn_qq);
        ImageButton btnWeixin = findViewById(R.id.btn_weixin);
        ImageButton btnJaccount = findViewById(R.id.btn_jaccount);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnRegister = findViewById(R.id.btn_register);
        back.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        btnQq.setOnClickListener(this);
        btnWeixin.setOnClickListener(this);
        btnJaccount.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                this.finish();
                break;
            case R.id.btn_login://登录
                String account = etAccount.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
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
                user.login(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e==null){
                            toast("登录成功："+user.getUsername());
                            BmobUtils.queryTaskCollectors();
                            setResult(RESULT_OK);
                            LoginActivity.this.finish();
                        } else {
                            toast("登录失败：" + e.getMessage());
                        }
                    }
                });
                break;
            case R.id.btn_register://注册
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
                User u = new User();
                u.setUsername(account);
                u.setPassword(pwd);
                u.signUp(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
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
            case R.id.btn_jaccount:
                Request.Builder reqBuild = new Request.Builder();
                HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(" https://jaccount.sjtu.edu.cn/oauth2/authorize"))
                        .newBuilder();
                urlBuilder.addQueryParameter("response_type", "code");
                urlBuilder.addQueryParameter("scope", "openid");
                urlBuilder.addQueryParameter("client_id", "3q6TNuBfQXWJ8XypOTNx");
                urlBuilder.addQueryParameter("redirect_uri", "http://202.120.40.8:30335/login/jaccount");//baidu网址改成后端url
                reqBuild.url(urlBuilder.build());

                popDialog(reqBuild.build());
                break;
            default:
                break;
        }
    }
    private void loginWithAuth(final BmobUser.BmobThirdUserAuth authInfo){
        BmobUser.loginWithAuthData(authInfo, new LogInListener<JSONObject>() {
            @Override
            public void done(JSONObject jsonObject, BmobException e) {
                if (e==null){
                    Log.i("LoginActivity",authInfo.getSnsType()+"登陆成功返回:"+jsonObject);
                    BmobUtils.queryTaskCollectors();
                    setResult(RESULT_OK);
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
                    } catch (JSONException ignored) {
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
    @SuppressLint("SetJavaScriptEnabled")
    private void popDialog(Request url) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        final AlertDialog customDialog;
        LayoutInflater inflater = getLayoutInflater();
        @SuppressLint("InflateParams") View layout = inflater.inflate(R.layout.activity_login_jaccount, null);
        builder.setView(layout);
        customDialog = builder.create();
        final WebView webView = layout.findViewById(R.id.jaccount);
        webView.loadUrl(url.url().toString());
        //设置不用系统浏览器打开,直接显示在当前Webview
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                System.out.println("something error");
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url == null) return false;
                try{
                    if(!url.startsWith("http://") && !url.startsWith("https://")){
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    }
                }catch (Exception e){//防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return true;//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
                }
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        //设置WebChromeClient类
        webView.setWebChromeClient(new WebChromeClient() {
            //获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                System.out.println("标题在这里");
                builder.setTitle(title);
            }
            //获取加载进度
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    String progress = newProgress + "%";
                    builder.setMessage(progress);
                    System.out.println(progress);
                } else if (newProgress == 100) {
                    String progress = newProgress + "%";
                    builder.setMessage(progress);
                    System.out.println(progress);
                }
            }
        });
        //设置WebViewClient类
        webView.setWebViewClient(new WebViewClient() {
            //设置加载前的函数
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }
            //设置结束加载函数
            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.startsWith("http://202.120.40.8")){
                    final String accessToken = getToken(url);
                    HttpUtil.sendRequestWithOkHttp(url, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        }
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()){
                                String body = response.body().string();
                                int index = body.indexOf("<h1>");
                                int end = body.indexOf("</h1>");
                                final String name = body.substring(index+4, end);
                                User user = new User();
                                try {
                                    user.setUsername(DESUtils.encrypt(name));
                                    user.setPassword(DESUtils.encrypt(name));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                user.setAccessToken(accessToken);
                                final boolean[] signed = {false};
                                final boolean[] finished = {false};
                                user.signUp(new SaveListener<User>() {
                                    @Override
                                    public void done(User user, BmobException e) {
                                        if (e.getMessage().contains("already taken")){
                                            signed[0] = true;
                                        }
                                        finished[0] = true;
                                    }
                                });
                                while (!finished[0]){
                                }
                                if (signed[0]) {
                                    user.login(new SaveListener<User>() {
                                        @Override
                                        public void done(User user, BmobException e) {
                                            if (e==null){
                                                toast("登录成功！");
                                                customDialog.dismiss();
                                                BmobUtils.queryTaskCollectors();
                                                setResult(RESULT_OK);
                                                LoginActivity.this.finish();
                                            }
                                            else {
                                                toast("登录失败："+e.getMessage());
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
            }
            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                toast("网页加载出错！");

                customDialog.setTitle("ERROR");
                customDialog.setMessage(description);
                customDialog.setButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                });
                customDialog.show();
            }
            @Override
            public void onLoadResource(WebView view, String url) {
                Log.e("LoginActivity", "WebView:"+view.getUrl()+"\\n"+"   URL:"+url);
                super.onLoadResource(view, url);
            }

        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        customDialog.show();
    }
    private String getToken(String url)
    {
        int index = url.indexOf("=");
        return url.substring(index+1);
    }
    private void toast(String msg){
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

}
