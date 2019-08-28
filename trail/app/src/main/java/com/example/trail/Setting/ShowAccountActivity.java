package com.example.trail.Setting;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.trail.MainActivity;
import com.example.trail.R;
import com.example.trail.Utility.UIHelper.AccountView.AccountView;
import com.example.trail.Utility.Utils.BmobUtils;
import com.example.trail.Utility.Utils.DESUtils;

import cn.bmob.v3.BmobUser;
import solid.ren.skinlibrary.base.SkinBaseActivity;

public class ShowAccountActivity extends SkinBaseActivity {
    private static final int REQUEST_CODE_CHOOSE = 20;
    private AccountView mAvatar;
    private TextView mUsername;
    private LinearLayout mBtnBack;
    private LinearLayout mBtnLogout;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_account);
        mAvatar = findViewById(R.id.account_view);
        mUsername = findViewById(R.id.account_username);
        User user = BmobUser.getCurrentUser(User.class);
        if (user!=null){
            String username = null;
            if (user.getAccessToken()!=null){
                try {
                    username = DESUtils.decrypt(user.getUsername());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                username = user.getUsername();
            }
            //TODO avatar
            mAvatar.setImageSource(R.drawable.dummy_image);
            mUsername.setText(username);
        }
        mAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Matisse.from(ShowAccountActivity.this)
//                        .choose(MimeType.ofImage())
//                        .countable(true)
//                        .maxSelectable(9)
//                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
//                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
//                        .thumbnailScale(0.85f)
//                        .imageEngine(new GlideEngine())
//                        .forResult(REQUEST_CODE_CHOOSE);
//                PictureSelector.create(ShowAccountActivity.this)
//                        .openGallery(PictureMimeType.ofImage())
//                        .maxSelectNum(2)
//                        .minSelectNum(1)
//                        .imageSpanCount(4)
//                        .selectionMode(PictureConfig.SINGLE)
//                        .previewImage(true)
//                        .isCamera(true)
//                        .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
//                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
//                        .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//                        .circleDimmedLayer(true)// 是否圆形裁剪 true or false
//                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
            }
        });
        mBtnBack = findViewById(R.id.btn_back);
        mBtnLogout = findViewById(R.id.btn_logout);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowAccountActivity.this.finish();
            }
        });
        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (BmobUser.isLogin()){
                    BmobUtils.updateTaskCollector(MainActivity.taskCollectors);
                    BmobUser.logOut();
                    setResult(RESULT_CANCELED);
                    ShowAccountActivity.this.finish();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        List<Uri> mSelected;

//        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
//            mSelected = Matisse.obtainResult(data);
//            Log.d("Matisse", "mSelected: " + mSelected);
//        }
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case PictureConfig.CHOOSE_REQUEST:
//                    // 图片、视频、音频选择结果回调
//                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
//                    // 例如 LocalMedia 里面返回三种path
//                    // 1.media.getPath(); 为原图path
//                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
//                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
//                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
//                    System.out.println(selectList.get(0).getCutPath());
//                    break;
//            }
//        }
    }
}
