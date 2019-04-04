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

import java.io.File;
import java.lang.reflect.Field;

//import com.zhy.http.okhttp.OkHttpUtils;
//import com.zhy.http.okhttp.https.HttpsUtils;
//import com.zhy.http.okhttp.log.LoggerInterceptor;

//import okhttp3.OkHttpClient;

public class MyNubioLibApplication extends Application {

    public static final String TAG = MyNubioLibApplication.class.getSimpleName();

    public static boolean isLog=false ;//是否打印Log
    public  String logName="nubiolib" ;//是否打印Log

    private static MyNubioLibApplication mInstance;



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
                .tag("nubiolib")  // (Optional)
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
        /**********************Log日志管理end***************************************/


//        /******************网络请求设置start ***********************************/
//        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
////        CookieJarImpl cookieJar1 = new CookieJarImpl(new MemoryCookieStore());
//        OkHttpClient okHttpClient;
//
//            okHttpClient = new OkHttpClient.Builder()
//                    .connectTimeout(10000L, TimeUnit.MILLISECONDS)
//                    .readTimeout(10000L, TimeUnit.MILLISECONDS)
//                    .addInterceptor(new LoggerInterceptor("TAG", isLog))
//                    .hostnameVerifier(new HostnameVerifier() {
//                        @Override
//                        public boolean verify(String hostname, SSLSession session) {
//                            return true;
//                        }
//                    })
//                    .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
//                    .build();
//
//        OkHttpUtils.initClient(okHttpClient);
//        /******************网络请求设置end ***********************************/


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
        //网络请求设置
        try {
            // InputStream inputStream =getApplicationContext().getAssets().open("srca.cer");
            //初始化nohttp（在此处其实可以调用setDialogGetListener设置全局请求加载框）
//            RxNoHttpUtils.rxNoHttpInit(getApplicationContext())
//                    //是否维护Cookie
//                    .setCookieEnable(false)
//                    //是否缓存进数据库DBCacheStore
//                    .setDbEnable(isLog)
//                    //是否开启debug调试
//                    .isDebug(isLog)
//                    //设置debug打印Name
//                    .setDebugName(logName)
////                    设置全局连接超时时间。单位秒，默认30s。
//                    .setConnectTimeout(10)
//                    //设置全局服务器响应超时时间，单位秒，默认30s。
//                    .setReadTimeout(10)
//                    //设置全局默认加载对话框
//                    //.setDialogGetListener("全局加载框获取接口")
//                    //设置底层用那种方式去请求
//                    .setRxRequestUtilsWhy(NoHttpInit.OKHTTP)
//                    //设置下载线程池并发数量
//                    .setThreadPoolSize(3)
//                    //设置网络请求队列并发数量
//                    .setRunRequestSize(4)
//                    //设置带证书安全协议请求
//                    //.setInputStreamSSL(new InputStream())
//                    //设置无证书安全协议请求
//                    //.setInputStreamSSL()
//                    //添加全局请求头
//                    //.addHeader("app>>head","app_head_global")
//                    //添加全局请求参数-只支持String类型
//                    // .addParam("app_param","app_param_global")
//                    //设置Cookie管理监听。
//                    // .setCookieStoreListener(new DBCookieStore.CookieStoreListener())
//                    //设置主机验证
//                    // .setHostnameVerifier(new HostnameVerifier())
//                    //设置全局重试次数，配置后每个请求失败都会重试设置的次数。
//                    .setRetry(3)
//                    .setAnUnknownErrorHint("网络繁忙请稍后再试！")
//                    //开始初始化Nohttp
//                    .startInit();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void setDebug(boolean isDebug,String logName){
      this.isLog=isDebug;
      this.logName=logName;
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

    public static synchronized MyNubioLibApplication getInstance() {
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