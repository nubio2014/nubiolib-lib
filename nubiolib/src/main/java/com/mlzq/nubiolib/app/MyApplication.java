package com.mlzq.nubiolib.app;


import android.app.Application;
import android.graphics.Typeface;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.mlzq.nubiolib.http.LruBitmapCache;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.io.File;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;

public class MyApplication extends Application {

    public static final String TAG = MyApplication.class.getSimpleName();

    public static final boolean isLog = false;//是否打印Log
    public static final boolean isKillSystem = false;//是否处理崩溃事件

    private static MyApplication mInstance;



    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;


        /**********************Log日志管理start***************************************/
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // 可选)是否显示线程信息。默认的真实
                .methodCount(3)         // (可选) 有多少行显示方法。默认值2
                .methodOffset(7)        // (Optional)隐藏内部抵消方法调用。默认的5
                //	.logStrategy(customLog) // (Optional) 更改日志打印策略。默认的日志的猫
                .tag("lu")  // (Optional)
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        /**********************Log日志管理end***************************************/


        /******************网络请求设置start ***********************************/
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
//        CookieJarImpl cookieJar1 = new CookieJarImpl(new MemoryCookieStore());
        OkHttpClient okHttpClient;

            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                    .readTimeout(10000L, TimeUnit.MILLISECONDS)
                    .addInterceptor(new LoggerInterceptor("TAG", isLog))
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    })
                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                    .build();

        OkHttpUtils.initClient(okHttpClient);
        /******************网络请求设置end ***********************************/


        //字体设置
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/kt.ttf");
        try {
            Field field = Typeface.class.getDeclaredField("SERIF");
            field.setAccessible(true);
            field.set(null, tf);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public static void delete(String fileName) {
        //   L.e("删除路径：" + fileName);
        if (fileName != null) {
            try {
                File file = new File(fileName);
                if (file != null) {
                    deleteFile(file);
                }
            } catch (Exception e) {
                // L.e("Holle");
            }
        }
        Log.e("HTML-Download：", "———————————>>>>删除方法执行完毕<<<<———————————");
    }

    public static void deleteFile(File file) {
        if (file == null || file.length() <= 0) {
            return;
        }
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                }
            } else {
                Log.e("HTML-Download：", "———————————>>>>删除路径下文件失败1<<<<———————————");
            }
            file.delete();
        } else {
            Log.e("HTML-Download：", "———————————>>>>删除路径下文件失败2<<<<———————————");
        }
    }

}