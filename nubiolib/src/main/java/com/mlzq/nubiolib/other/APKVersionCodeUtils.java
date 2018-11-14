package com.mlzq.nubiolib.other;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.View;

import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

public class APKVersionCodeUtils {

    /**
     * 获取当前本地apk的版本
     *
     * @param mContext
     * @return
     */
    public static int getVersionCode(Context mContext) {
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = mContext.getPackageManager().
                    getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取版本号名称
     *
     * @param context 上下文
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    /**
     * 导航栏，状态栏隐藏
     * @param activity
     */
    /**
     * 设置Activity的statusBar隐藏
     * @param activity
     */
    public static void setNavigationBar(Activity activity,int visible){
        View decorView = activity.getWindow().getDecorView();
        //显示NavigationBar
        if (View.GONE == visible){
            int option = SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            decorView.setSystemUiVisibility(option);
        }
    }


}