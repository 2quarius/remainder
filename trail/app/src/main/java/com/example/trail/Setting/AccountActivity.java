package com.example.trail.Setting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trail.Http.HttpUtil;
import com.example.trail.MainActivity;
import com.example.trail.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;

public class AccountActivity extends AppCompatActivity {
    private Button btnLogin;
    private Button btnRegist;
    private ImageButton btnQQ;
    private ImageButton btnWechat;
    private ImageButton btnJaccount;
    private EditText username;
    private EditText password;
    private Button back;
    final  private String FILE_NAME2 = "information.txt";
    final  private String FILE_NAME3 = "theme.txt";
    private boolean flag1=true;
    private boolean flag2=true;
    private String accessToken = null;
    private String name = null;

    private void setTheTheme() {
        String theme = "";
        try {
            FileInputStream ios = openFileInput(FILE_NAME3);
            byte[] temp = new byte[10];
            StringBuilder sb = new StringBuilder("");
            int len = 0;
            while ((len = ios.read(temp)) > 0){
                sb.append(new String(temp, 0, len));
            }
            ios.close();
            theme = sb.toString();
        }catch (Exception e) {
            //Log.d("errMsg", e.toString());
        }
        if (theme.equals("purple")) {
            setTheme(R.style.LightTheme);
        }
        else if (theme.equals("black")){
            setTheme(R.style.NightTheme);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheTheme();
        setContentView(R.layout.activity_account);
        btnLogin = findViewById(R.id.btn_login);
        btnRegist = findViewById(R.id.btn_regist);
        btnQQ = findViewById(R.id.btn_qq);
        btnWechat = findViewById(R.id.btn_wechat);
        btnJaccount=findViewById(R.id.btn_jaccount);

        back = findViewById(R.id.btn_accountBack);
        back.bringToFront();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {//登录
            @Override
            public void onClick(View view) {
                username = findViewById(R.id.et_username);
                final String un = username.getText().toString();

                password = findViewById(R.id.et_password);
                final String pw = password.getText().toString();

                if(un.length()<=0){ //是否输入用户名
                    Toast.makeText(AccountActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                }
                else if(pw.length()<=0){ //是否输入密码
                    Toast.makeText(AccountActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                }
                else {//判断是否符合条件
                    BmobQuery<User> users=new BmobQuery<>();
                    users.findObjects(new FindListener<User>() {
                        @Override
                        public void done(List<User> list, BmobException e) {
                            for(int i=0;i<list.size();i++){
                                if(list.get(i).getUsername().equals(un)){
                                    if(list.get(i).getPassword().equals(pw)){
                                        Toast.makeText(AccountActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                                        save(un);
                                        Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                    flag1=false;
                                }
                            }
                            flag2=false;
                        }
                    });
                    if(!flag1) Toast.makeText(AccountActivity.this,"密码错误，请重新输入",Toast.LENGTH_SHORT).show();
                    if(!flag2) Toast.makeText(AccountActivity.this,"不存在此用户，请注册",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnRegist.setOnClickListener(new View.OnClickListener() { //注册
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this,RegistActivity.class);
                startActivity(intent);
            }
        });
        btnQQ.setOnClickListener(new View.OnClickListener() { //qq
            @Override
            public void onClick(View view) {
                Toast.makeText(AccountActivity.this,"qq登录功能未实现",Toast.LENGTH_SHORT).show();
            }
        });
        btnWechat.setOnClickListener(new View.OnClickListener() { //微信
            @Override
            public void onClick(View view) {
                Toast.makeText(AccountActivity.this,"微信登录功能未实现",Toast.LENGTH_SHORT).show();
            }
        });
        btnJaccount.setOnClickListener(new View.OnClickListener() { //jaccount
            @Override

            public void onClick(View view) {
                Request.Builder reqBuild = new Request.Builder();
                HttpUrl.Builder urlBuilder =HttpUrl.parse(" https://jaccount.sjtu.edu.cn/oauth2/authorize")
                        .newBuilder();
                urlBuilder.addQueryParameter("response_type", "code");
                urlBuilder.addQueryParameter("scope", "openid");
                urlBuilder.addQueryParameter("client_id", "3q6TNuBfQXWJ8XypOTNx");
                urlBuilder.addQueryParameter("redirect_uri", "http://202.120.40.8:30335/login/jaccount");//baidu网址改成后端url
                reqBuild.url(urlBuilder.build());

                popDialog(reqBuild.build());
            }
        });
    }
    private void popDialog(Request url) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
        final AlertDialog customDialog;
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.activity_login_jaccount,null);
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
                    accessToken = getToken(url);
                    HttpUtil.sendRequestWithOkHttp(url, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()){
                                String body = response.body().string();
                                int index = body.indexOf("<h1>");
                                int end = body.indexOf("</h1>");
                                name = body.substring(index+4,end);
                            }
                        }
                    });
                }
            }
            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                Toast.makeText(AccountActivity.this, "网页加载出错！", Toast.LENGTH_LONG);

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
                Log.e("hao", "WebView3:"+view.getUrl()+"\\n"+"   URL3:"+url);
                super.onLoadResource(view, url);
            }

        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        customDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (name!=null){
                    save(name);
                    Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
        customDialog.show();
    }
    private String getToken(String url)
    {
        int index = url.indexOf("=");
        return url.substring(index+1);
    }

    @Override
    protected void onStart(){
        super.onStart();
        setTheTheme();
    }

    private void save(String text) {
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME2, Context.MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            //Log.d("errMsg", e.toString());
        }
    }
}
