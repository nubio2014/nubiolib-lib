package com.mlzq.nubiolib.Dialog;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Dev on 2017/9/27.
 */

public class ToastUtils {
    private  static String oldMsg;
    private static Toast toast=null;
    private static  long oneTime=0;
    private static  long twoTime=0;

    private  static String oldMsg2;
    private static Toast toast2=null;
    private static  long oneTime2=0;
    private static  long twoTime2=0;



    public static  void showToast(Context context, String message){
        if (toast2==null){
            toast2= Toast.makeText(context,message, Toast.LENGTH_SHORT);
            toast2.show();
        }else{
            twoTime2= System.currentTimeMillis();
            if (message!=null) {
                if (message.equals(oldMsg2)) {
                    toast2.show();
                } else {
                    oldMsg2 = message;
                    toast2.setText(message);
                    toast2.show();
                }
            }
        }
        oneTime2=twoTime2;
    }


    public static  void showToastLong(Context context, String message){
        if (toast==null){
            toast= Toast.makeText(context,message, Toast.LENGTH_LONG);
            toast.show();
        }else{
            twoTime= System.currentTimeMillis();
            if (message.equals(oldMsg)){
                toast.show();
            }else{
                oldMsg=message;
                toast.setText(message);
                toast.show();
            }
        }
        oneTime=twoTime;
    }
}
