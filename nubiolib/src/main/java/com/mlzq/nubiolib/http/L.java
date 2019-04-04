package com.mlzq.nubiolib.http;

import com.mlzq.nubiolib.app.MyNubioLibApplication;
import com.orhanobut.logger.Logger;

/**
 * Created by Dev on 2017/12/15.
 */

public class L {

//    Logger
//            .init("mytag")    //LOG TAG默认是PRETTYLOGGER
//            .methodCount(3)                 // 决定打印多少行（每一行代表一个方法）默认：2
//            .hideThreadInfo()               // 隐藏线程信息 默认：显示
//            .logLevel(LogLevel.NONE)        // 是否显示Log 默认：LogLevel.FULL（全部显示） //.logLevel(LogLevel.NONE);//产品上线设置日志不输出
//            .methodOffset(2)                // 默认：0
//            .logAdapter(new AndroidLogAdapter()); //可以自己构造适配器默认：AndroidLogAdapter
//2.打印不同level的Log
//Logger.v(String message); // VERBOSE级别，可添加占位符
//Logger.d(Object object); // DEBUG级别，打印对象
//Logger.d(String message); // DEBUG级别，可添加占位符
//Logger.i(String message); // INFO级别，可添加占位符
//Logger.w(String message); // WARN级别，可添加占位符
//Logger.e(String message); // ERROR级别，可添加占位符
//Logger.e(Throwable throwable, String message); // ERROR级别，可添加占位符
//Logger.wtf(String message); // ASSERT级别，可添加占位符
//Logger.xml(String xml);
//Logger.json(String json);


//
//    FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
//            .showThreadInfo(false)  // 可选)是否显示线程信息。默认的真实
//            .methodCount(0)         // (可选) 有多少行显示方法。默认值2
//            .methodOffset(7)        // (Optional)隐藏内部抵消方法调用。默认的5
//            //	.logStrategy(customLog) // (Optional) 更改日志打印策略。默认的日志的猫
//            .tag(TagName)   // (Optional)
//            .build();
//
//		Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));

    public static void json(String json) {
        if (MyNubioLibApplication.isLog)
            Logger.json(json);
    }

    public static void e(String str) {
        if (MyNubioLibApplication.isLog) Logger.e(str);
    }

    //调用 Logger.t("临时TAG名").d()
    public static void e(String tag, String msg) {
        if (MyNubioLibApplication.isLog) Logger.t(tag).d(msg);
    }

    public static void d(String str) {
        if (MyNubioLibApplication.isLog) Logger.d(str);
    }

    public static void xml(String str) {
        if (MyNubioLibApplication.isLog) Logger.xml(str);
    }
}
