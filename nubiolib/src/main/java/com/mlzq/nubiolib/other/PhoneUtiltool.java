package com.mlzq.nubiolib.other;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 获取手机信息工具类
 * @author
 */
public abstract class PhoneUtiltool {
 
 
    private static  TelephonyManager tm;
    private static  Context act;
    private String IMSI;
    
    public PhoneUtiltool(Context act){
        tm = (TelephonyManager) act.getSystemService(Context.TELEPHONY_SERVICE);
        this.act = act;
    }

  
    
 
    
    /**获取手机号码*/
    public   String getPhoneNumber(){
    	return tm.getLine1Number();
    }
    
    /**获取手机运营商*/
	public String getProvidersName() {
		String ProvidersName = null;
		// 返回唯一的用户ID;就是这张卡的编号神马的
		IMSI = tm.getSubscriberId();
		// IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
//		Log.i("tag",IMSI);
		if(IMSI == null || "".equals(IMSI)){
			ProvidersName = "获取手机号码失败";
		}else
			if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
			ProvidersName = "中国移动";
		} else if (IMSI.startsWith("46001")) {
			ProvidersName = "中国联通";
		} else if (IMSI.startsWith("46003")) {
			ProvidersName = "中国电信";
		}
		return ProvidersName;
	}
    
    /**获取手机型号*/
    public  String getModel(){
        return Build.MODEL;
    }

    /**
     * 获取手机CPU指令集
     */
//    public   String getCpu() {
//
//    	String cpuInfo;
//    	 if (!AbStrUtil.isEmpty(Build.CPU_ABI)){
//    		 cpuInfo = Build.CPU_ABI; //CPU指令集
//         }else {
//        	 cpuInfo=Build.CPU_ABI2; //CPU指令集
//         }
//        return cpuInfo;
//    }
     /**
     * 运行内存
     */
    public   String getTotalMemory() {

    	 String str1 = "/proc/meminfo";// 系统内存信息文件
         String str2;
         String[] arrayOfString;
         long initial_memory = 0;

         try {
             FileReader localFileReader = new FileReader(str1);
             BufferedReader localBufferedReader = new BufferedReader(
                     localFileReader, 8192);
             str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

             arrayOfString = str2.split("\\s+");
             for (String num : arrayOfString) {
                 //Log.i(str2, num + "\t");
             }

             initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
             localBufferedReader.close();

         } catch (IOException e) {
         }
         return Formatter.formatFileSize(act, initial_memory);// Byte转换为KB或者MB，内存大小规格化

    }
     /** 机身可用存储空间*/
    public   String getAvailMemory() {
        ActivityManager am = (ActivityManager) act.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return Formatter.formatFileSize(act, mi.availMem);
    }

    /**获取手机android系统版本*/
    public    String getAndroidVersion(){
        return Build.VERSION.RELEASE;
    }

    /**获取手机系统版本*/
    public    String getVersion(){
        return Build.DISPLAY;
    }


    /**获取app版本*/

    public    int getVersionCode() {
        return getPackageInfo(act).versionCode;
    }

    private static PackageInfo getPackageInfo(Context act) {
        PackageInfo pi = null;

        try {
            PackageManager pm = act.getPackageManager();
            pi = pm.getPackageInfo(act.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }
    /**备注*/
    public    String getRemark(){
        return "安卓";
    }


    /*******************************************************************************************************************/
  /**
   * 获取版本号
   * @param context
   * @return
   */
  	public static String getVersion(Context context){
  		int version = 0;
  		try {
  			version = context.getPackageManager().getPackageInfo(
                      "com.zdd.wlb", 0).versionCode;
          } catch (Exception e) {
          	 System.out.println("获取版本号异常！");
          }
  		return String.valueOf(version);
  	}
	public static String  type() {
		JSONObject object=new JSONObject();
		try {
			object.put("Type", 0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object.toString();

	}
  	/**
  	 * 获取版本名
  	 * @param context
  	 * @return
  	 */
  	public static String getVersionName(Context context){
  		String versionName = null;
  		try {
  			versionName = context.getPackageManager().getPackageInfo(
  					"com.zdd.wlb", 0).versionName;
  		} catch (Exception e) {
  			 System.out.println("获取版本名异常！");
  		}
  		return versionName;
  	}


	//获取服务器版本号
	static String serverJson = null;


    /**是否处于飞行模式 */
    public boolean isAirModeOpen() {
        return (Settings.System.getInt(act.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) == 1 ? true : false);
    }



    /**获取网络类型（暂时用不到）*/
    public int getNetWorkType(){
        return tm==null?0:tm.getNetworkType();
    }

    /**获取手机sim卡的序列号（IMSI）*/
    public String getIMSI(){
        return tm==null?null:tm.getSubscriberId();
    }

    /**获取手机IMEI*/
    public String getIMEI(){
        return tm==null?null:tm.getDeviceId();
    }



    /**获取手机品牌*/
    public static String getBrand(){
        return Build.BRAND;
    }
 
 
    
//    /**获得手机系统总内存*/
//    public static String getTotalMemory2() {  
//        String str1 = "/proc/meminfo";// 系统内存信息文件   
//        String str2;  
//        String[] arrayOfString;  
//        long initial_memory = 0;  
// 
//        try {  
//            FileReader localFileReader = new FileReader(str1);  
//            BufferedReader localBufferedReader = new BufferedReader(  
//                    localFileReader, 8192);  
//            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小   
// 
//            arrayOfString = str2.split("\\s+");  
// 
//            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte   
//            localBufferedReader.close();  
// 
//        } catch (IOException e) {  
//        }  
//        return Formatter.formatFileSize(act, initial_memory);// Byte转换为KB或者MB，内存大小规格化   
//    }
 
//    /**获取手机屏幕宽*/
//    public int getScreenWidth(){
//        return act.getWindowManager().getDefaultDisplay().getWidth();
//    }
// 
//    /**获取手机屏高宽*/
//    public int getScreenHeight(){
//        return act.getWindowManager().getDefaultDisplay().getHeight();
//    }
     
//    /**获取应用包名*/
//    public String getPackageName(){
//        return act.getPackageName();
//    }
// 
//    /** 
//     * 获取手机MAC地址 
//     * 只有手机开启wifi才能获取到mac地址 
//     */ 
//    public String getMacAddress(){  
//        String result = "";  
//        WifiManager wifiManager = (WifiManager) act.getSystemService(Context.WIFI_SERVICE);  
//        WifiInfo wifiInfo = wifiManager.getConnectionInfo();  
//        result = wifiInfo.getMacAddress();  
//        return result;  
//    }  
// 
//    /** 
//     * 获取手机CPU信息 //1-cpu型号  //2-cpu频率  
//     */ 
//    public String[] getCpuInfo() {  
//        String str1 = "/proc/cpuinfo";  
//        String str2 = "";  
//        String[] cpuInfo = {"", ""};  //1-cpu型号  //2-cpu频率  
//        String[] arrayOfString;  
//        try {  
//            FileReader fr = new FileReader(str1);  
//            BufferedReader localBufferedReader = new BufferedReader(fr, 8192);  
//            str2 = localBufferedReader.readLine();  
//            arrayOfString = str2.split("\\s+");  
//            for (int i = 2; i < arrayOfString.length; i++) {  
//                cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";  
//            }  
//            str2 = localBufferedReader.readLine();  
//            arrayOfString = str2.split("\\s+");  
//            cpuInfo[1] += arrayOfString[2];  
//            localBufferedReader.close();  
//        } catch (IOException e) {
//        }
//        return cpuInfo;  
//    }
//     
//    /**获取Application中的meta-data内容*/
//    public String getMetaData(String name){
//        String result = "";
//        try {
//            ApplicationInfo appInfo = act.getPackageManager()
//                    .getApplicationInfo(getPackageName(),PackageManager.GET_META_DATA);
//            result = appInfo.metaData.getString(name);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//

	double size = 0.0;

	/**
	 * 计算文件或者文件夹的大小 ，单位 MB
	 * @param file 要计算的文件或者文件夹 ， 类型：java.io.File
	 * @return 大小，单位：MB
	 */
	public static  double getSize(File file) {
		//判断文件是否存在
		if (file.exists()) {
			//如果是目录则递归计算其内容的总大小，如果是文件则直接返回其大小
			if (!file.isFile()) {
				//获取文件大小
				File[] fl = file.listFiles();
				double ss = 0;
				for (File f : fl)
					ss += getSize(f);
				return ss;
			} else {
				double ss = (double) file.length() / 1024 / 1024;
				System.out.println(file.getName() + " : " + ss + "MB");
				return ss;
			}
		} else {
			System.out.println("文件或者文件夹不存在，请检查路径是否正确！");
			return 0.0;
		}
	}
	/**删除文件夹及其目录下的文件**/
	public void deleteDir(Context context, File dir){
		// File dir=new File(path);
		if (dir==null||!dir.exists()||!dir.isDirectory()){
			return;
		}
		for (File file:dir.listFiles()) {
			if (file.isFile()){
				file.delete();
			}else if (file.isDirectory()){
				deleteDir(context,file);
			}
		}
		//dir.delete();
		ButtonOnClick();
		Log.e("删除成功",dir+"");
	}

	public abstract  void ButtonOnClick();
}