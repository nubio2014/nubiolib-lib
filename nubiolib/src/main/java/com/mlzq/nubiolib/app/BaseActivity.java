package com.mlzq.nubiolib.app;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mlzq.nubiolib.R;

import java.util.ArrayList;
import java.util.List;


public class BaseActivity extends BaseNoTitleActivity {
    TextView title, tv_right, tv_left;
    ImageView img_right, img_left;
    LinearLayout base_content, title_left;
    RelativeLayout base_title_bg;
    private Context mContext;


    private PermissionListener mListener;
    private static final int PERMISSION_REQUESTCODE = 100;

    // 要申请的权限
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.base_activity);
        mContext=this;

        AppManager.getAppManager().addActivity(this);
        title = (TextView) findViewById(R.id.txt_title);
        tv_right = (TextView) findViewById(R.id.title_right);
        tv_left = (TextView) findViewById(R.id.title_left_name);
        img_right = (ImageView) findViewById(R.id.title_rightico);
        img_left = (ImageView) findViewById(R.id.title_left_image);
        title_left = (LinearLayout) findViewById(R.id.title_left);
        base_content = (LinearLayout) findViewById(R.id.base_content);
        base_title_bg = (RelativeLayout) findViewById(R.id.base_title_bg);
        setLeftListener();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            StatusBarUtil.immersive(this);
            Drawable background = base_title_bg.getBackground();
            ColorDrawable colorDrawable = (ColorDrawable) background;
            int color = colorDrawable.getColor();
            StatusBarUtil.immersive(this, color);
            StatusBarUtil.setPaddingSmart(this, base_title_bg);
            StatusBarUtil.darkMode(this);
        }
        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // 检查该权限是否已经获取
            int i = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (i != PackageManager.PERMISSION_GRANTED) {
                // 如果没有授予该权限，就去提示用户请求
                requestRunPermisssion(permissions, new PermissionListener() {
                    @Override
                    public void onGranted() {
                        //表示所有权限都授权了
                        Toast.makeText(BaseActivity.this, "权限获取成功", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onDenied(List<String> deniedPermission) {
                        for (String permission : deniedPermission) {
                            Toast.makeText(BaseActivity.this, "被拒绝的权限：" + permission, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }
    }
    public void requestRunPermisssion(String[] permissions, PermissionListener listener){
        mListener = listener;
        List<String> permissionLists = new ArrayList<>();
        for(String permission : permissions){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                permissionLists.add(permission);
            }
        }

        if(!permissionLists.isEmpty()){
            ActivityCompat.requestPermissions(this, permissionLists.toArray(new String[permissionLists.size()]), PERMISSION_REQUESTCODE);
        }else{
            //表示全都授权了
            mListener.onGranted();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_REQUESTCODE:
                if(grantResults.length > 0){
                    //存放没授权的权限
                    List<String> deniedPermissions = new ArrayList<>();
                    for(int i = 0; i < grantResults.length; i++){
                        int grantResult = grantResults[i];
                        String permission = permissions[i];
                        if(grantResult != PackageManager.PERMISSION_GRANTED){
                            deniedPermissions.add(permission);
                        }
                    }
                    if(deniedPermissions.isEmpty()){
                        //说明都授权了
                        mListener.onGranted();
                    }else{
                        mListener.onDenied(deniedPermissions);
                    }
                }
                break;
            default:
                break;
        }
    }


    /**
     * 已授权、未授权的接口回调
     */
    public interface PermissionListener {

        void onGranted();//已授权

        void onDenied(List<String> deniedPermission);//未授权

    }



    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {

            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }





    public void setLeftName(String name) {
        tv_left.setText(name);
    }

    public void setLeftNameAndColor(String name, int color) {
        tv_left.setText(name);
        tv_left.setTextColor(color);
    }


    public void setTitleNameAndTextColor(String name, int namecolor) {
        title.setText(name);
        title.setTextColor(getResources().getColor(namecolor));
    }

    public void setTitleName(String name) {
        title.setText(name);

    }
    public void setBaseTitleColor( int namecolor) {

        title.setTextColor(getResources().getColor(namecolor));
    }
    public void setTitleSize(float size) {
        title.setTextSize(size);

    }

    public void setTitleBG(int color) {
        base_title_bg.setBackgroundColor(getResources().getColor(color));
    }

    public void setTitleVisiable(int titleVisiable) {
        base_title_bg.setVisibility(titleVisiable);

    }


    public void setRightText(String text) {
        tv_right.setText(text);
        img_right.setVisibility(View.INVISIBLE);
        tv_right.setVisibility(View.VISIBLE);
    }

    public void setRightText(String text, int color) {
        tv_right.setText(text);
        tv_right.setTextColor(getResources().getColor(color));
        img_right.setVisibility(View.INVISIBLE);
        tv_right.setVisibility(View.VISIBLE);
    }
    public void setRightText(String text, String color) {
        tv_right.setText(text);
        tv_right.setTextColor(Color.parseColor(color));
        img_right.setVisibility(View.INVISIBLE);
        tv_right.setVisibility(View.VISIBLE);
    }

    public void setRightIcon(int resId) {
        img_right.setImageResource(resId);
        img_right.setVisibility(View.VISIBLE);
        tv_right.setVisibility(View.GONE);
    }



    public void setRightListener(View.OnClickListener listener) {
        if (img_right.getVisibility() == View.VISIBLE) {
            img_right.setOnClickListener(listener);
        }
        if (tv_right.getVisibility() == View.VISIBLE) {
            tv_right.setOnClickListener(listener);
        }
    }
    /**
     * 左图标
     */
    public void setLeftIcon(int resId) {
        img_left.setImageDrawable(getResources().getDrawable(resId));

    }
    /**
     * 左图标是否显示
     */
    public void setLeftVisiable(int view) {
        img_left.setVisibility(view);

    }
    /**
     * 左点击事件
     */
    public void setLeftListener(View.OnClickListener listener) {
        title_left.setOnClickListener(listener);
    }

    /**
     * 默认左点击事件
     */
    public void setLeftListener() {
        title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 设置页面
     * @param view
     */
    public void setContent(int view) {
        LayoutInflater inflater = LayoutInflater.from(this);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        try {
            base_content.addView(inflater.inflate(view, null), params);
        }catch (Exception e){e.printStackTrace();}
    }
    public String baseGetString(int id){
        return mContext.getString(id);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//       AppManager.getAppManager().finishActivity();
            finish();
            // return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //  peerClient.removeObserver(observer);
    }


    @Override
    public void onResume() {
        super.onResume();

    }



}
