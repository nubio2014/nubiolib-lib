package com.mlzq.nubiolib.utile;

import android.app.DownloadManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * @Description:主要功能:文件管理
 * @Prject: CommonUtilLibrary
 * @Package: com.jingewenku.abrahamcaijin.commonutil
 * @author: AbrahamCaiJin
 * @date: 2017年05月16日 15:30
 * @Copyright: 个人版权所有
 * @Company:
 * @version: 1.0.0
 */

public class FileUtils {

    /**
     * 文本复制
     * @param context
     * @param content
     */
    public static void copyContext(Context context,String content){
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager)context. getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", content);
// 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
    }



    private FileUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }
    private static final String TAG = FileUtils.class.getSimpleName();


    /**
     * 创建目录
     *
     * @param context
     * @param dirName 文件夹名称
     * @return
     */
    public static File createFileDir(Context context, String dirName) {
        String filePath;
        // 如SD卡已存在，则存储；反之存在data目录下
        if (isMountedSDCard()) {
            // SD卡路径
            filePath = Environment.getExternalStorageDirectory() + File.separator + dirName;
        } else {
            filePath = context.getCacheDir().getPath() + File.separator + dirName;
        }
        File destDir = new File(filePath);
        if (!destDir.exists()) {
            boolean isCreate = destDir.mkdirs();
            Log.i("FileUtils", filePath + " has created. " + isCreate);
        }
        return destDir;
    }

    /**
     * 删除文件（若为目录，则递归删除子目录和文件）
     *
     * @param file
     * @param delThisPath true代表删除参数指定file，false代表保留参数指定file
     */
    public static void delFile(File file, boolean delThisPath) {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] subFiles = file.listFiles();
            if (subFiles != null) {
                int num = subFiles.length;
                // 删除子目录和文件
                for (int i = 0; i < num; i++) {
                    delFile(subFiles[i], true);
                }
            }
        }
        if (delThisPath) {
            file.delete();
        }
    }

    /**
     * 获取文件大小，单位为byte（若为目录，则包括所有子目录和文件）
     *
     * @param file
     * @return
     */
    public static long getFileSize(File file) {
        long size = 0;
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] subFiles = file.listFiles();
                if (subFiles != null) {
                    int num = subFiles.length;
                    for (int i = 0; i < num; i++) {
                        size += getFileSize(subFiles[i]);
                    }
                }
            } else {
                size += file.length();
            }
        }
        return size;
    }

    /**
     * 保存Bitmap到指定目录
     *
     * @param dir      目录
     * @param fileName 文件名
     * @param bitmap
     * @throws IOException
     */
    public static void saveBitmap(File dir, String fileName, Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        File file = new File(dir, fileName);
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断某目录下文件是否存在
     *
     * @param dir      目录
     * @param fileName 文件名
     * @return
     */
    public static boolean isFileExists(File dir, String fileName) {
        return new File(dir, fileName).exists();
    }



    /**
     * 检查是否已挂载SD卡镜像（是否存在SD卡）
     *
     * @return
     */
    public static boolean isMountedSDCard() {
        if (Environment.MEDIA_MOUNTED.equals(Environment
            .getExternalStorageState())) {
            return true;
        } else {
            Log.w(TAG, "SDCARD is not MOUNTED !");
            return false;
        }
    }

    /**
     * 获取SD卡剩余容量（单位Byte）
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static long gainSDFreeSize() {
        if (isMountedSDCard()) {
            // 取得SD卡文件路径
            File path = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(path.getPath());
            // 获取单个数据块的大小(Byte)
            long blockSize = sf.getBlockSize();
            // 空闲的数据块的数量
            long freeBlocks = sf.getAvailableBlocks();

            // 返回SD卡空闲大小
            return freeBlocks * blockSize; // 单位Byte
        } else {
            return 0;
        }
    }

    /**
     * 获取SD卡总容量（单位Byte）
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static long gainSDAllSize() {
        if (isMountedSDCard()) {
            // 取得SD卡文件路径
            File path = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(path.getPath());
            // 获取单个数据块的大小(Byte)
            long blockSize = sf.getBlockSize();
            // 获取所有数据块数
            long allBlocks = sf.getBlockCount();
            // 返回SD卡大小（Byte）
            return allBlocks * blockSize;
        } else {
            return 0;
        }
    }

    /**
     * 获取可用的SD卡路径（若SD卡不没有挂载则返回""）
     *
     * @return
     */

    public static String gainSDCardPath() {
        if (isMountedSDCard()) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            if (!sdcardDir.canWrite()) {
                Log.w(TAG, "SDCARD can not write !");
            }
            return sdcardDir.getPath();
        }
        return "";
    }

    /**
     * 以行为单位读取文件内容，一次读一整行，常用于读面向行的格式化文件
     *
     * @param filePath
     *            文件路径
     */
    public static String readFileByLines(String filePath) throws IOException {
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath),
                System.getProperty("file.encoding")));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
                sb.append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        return sb.toString();

    }

    /**
     * 以行为单位读取文件内容，一次读一整行，常用于读面向行的格式化文件
     *
     * @param filePath
     *            文件路径
     * @param encoding
     *            写文件编码
     */
    public static String readFileByLines(String filePath, String encoding)
        throws IOException {
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath), encoding));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
                sb.append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        return sb.toString();
    }

    /**
     * 保存内容
     *
     * @param filePath
     *            文件路径
     * @param content
     *            保存的内容
     * @throws IOException
     */
    public static void saveToFile(String filePath, String content)
        throws IOException {
        saveToFile(filePath, content, System.getProperty("file.encoding"));
    }

    /**
     * 指定编码保存内容
     *
     * @param filePath
     *            文件路径
     * @param content
     *            保存的内容
     * @param encoding
     *            写文件编码
     * @throws IOException
     */
    public static void saveToFile(String filePath, String content,
        String encoding) throws IOException {
        BufferedWriter writer = null;
        File file = new File(filePath);
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file, false), encoding));
            writer.write(content);

        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 追加文本
     *
     * @param content
     *            需要追加的内容
     * @param file
     *            待追加文件源
     * @throws IOException
     */
    public static void appendToFile(String content, File file)
        throws IOException {
        appendToFile(content, file, System.getProperty("file.encoding"));
    }

    /**
     * 追加文本
     *
     * @param content
     *            需要追加的内容
     * @param file
     *            待追加文件源
     * @param encoding
     *            文件编码
     * @throws IOException
     */
    public static void appendToFile(String content, File file, String encoding)
        throws IOException {
        BufferedWriter writer = null;
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file, true), encoding));
            writer.write(content);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath
     *            文件路径
     * @return 是否存在
     * @throws Exception
     */
    public static Boolean isExsit(String filePath) {
        Boolean flag = false;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                flag = true;
            }
        } catch (Exception e) {
            Log.e("判断文件失败-->" , e.getMessage());
        }

        return flag;
    }

    /**
     * 快速读取程序应用包下的文件内容
     *
     * @param context
     *            上下文
     * @param filename
     *            文件名称
     * @return 文件内容
     * @throws IOException
     */
    public static String read(Context context, String filename)
        throws IOException {
        FileInputStream inStream = context.openFileInput(filename);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        return new String(data);
    }

    /**
     * 读取指定目录文件的文件内容
     *
     * @param fileName
     *            文件名称
     * @return 文件内容
     * @throws Exception
     */
    @SuppressWarnings("resource")
    public static String read(String fileName) throws IOException {
        FileInputStream inStream = new FileInputStream(fileName);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        return new String(data);
    }

    /***
     * 以行为单位读取文件内容，一次读一整行，常用于读面向行的格式化文件
     *
     * @param fileName
     *            文件名称
     * @param encoding
     *            文件编码
     * @return 字符串内容
     * @throws IOException
     */
    public static String read(String fileName, String encoding)
        throws IOException {
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(fileName), encoding));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                sb.append(tempString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        return sb.toString();
    }

    /**
     * 读取raw目录的文件内容
     *
     * @param context
     *            内容上下文
     * @param rawFileId
     *            raw文件名id
     * @return
     */
    public static String readRawValue(Context context, int rawFileId) {
        String result = "";
        try {
            InputStream is = context.getResources().openRawResource(rawFileId);
            int len = is.available();
            byte[] buffer = new byte[len];
            is.read(buffer);
            result = new String(buffer, "UTF-8");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取assets目录的文件内容
     *
     * @param context
     *            内容上下文
     * @param fileName
     *            文件名称，包含扩展名
     * @return
     */
    public static String readAssetsValue(Context context, String fileName) {
        String result = "";
        try {
            InputStream is = context.getResources().getAssets().open(fileName);
            int len = is.available();
            byte[] buffer = new byte[len];
            is.read(buffer);
            result = new String(buffer, "UTF-8");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取assets目录的文件内容
     *
     * @param context
     *            内容上下文
     * @param fileName
     *            文件名称，包含扩展名
     * @return
     */
    public static List<String> readAssetsListValue(Context context,
        String fileName) {
        List<String> list = new ArrayList<String>();
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(in,
                "UTF-8"));
            String str = null;
            while ((str = br.readLine()) != null) {
                list.add(str);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取SharedPreferences文件内容
     *
     * @param context
     *            上下文
     * @param fileNameNoExt
     *            文件名称（不用带后缀名）
     * @return
     */
    public static Map<String, ?> readShrePerface(Context context,
        String fileNameNoExt) {
        SharedPreferences preferences = context.getSharedPreferences(
            fileNameNoExt, Context.MODE_PRIVATE);
        return preferences.getAll();
    }

    /**
     * 写入SharedPreferences文件内容
     *
     * @param context
     *            上下文
     * @param fileNameNoExt
     *            文件名称（不用带后缀名）
     * @param values
     *            需要写入的数据Map(String,Boolean,Float,Long,Integer)
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void writeShrePerface(Context context, String fileNameNoExt,
        Map<String, ?> values) {
        try {
            SharedPreferences preferences = context.getSharedPreferences(
                fileNameNoExt, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            for (Iterator iterator = values.entrySet().iterator(); iterator
                .hasNext();) {
                Map.Entry<String, ?> entry = (Map.Entry<String, ?>) iterator
                    .next();
                if (entry.getValue() instanceof String) {
                    editor.putString(entry.getKey(), (String) entry.getValue());
                } else if (entry.getValue() instanceof Boolean) {
                    editor.putBoolean(entry.getKey(),
                        (Boolean) entry.getValue());
                } else if (entry.getValue() instanceof Float) {
                    editor.putFloat(entry.getKey(), (Float) entry.getValue());
                } else if (entry.getValue() instanceof Long) {
                    editor.putLong(entry.getKey(), (Long) entry.getValue());
                } else if (entry.getValue() instanceof Integer) {
                    editor.putInt(entry.getKey(), (Integer) entry.getValue());
                }
            }
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入应用程序包files目录下文件
     *
     * @param context
     *            上下文
     * @param fileName
     *            文件名称
     * @param content
     *            文件内容
     */
    public static void write(Context context, String fileName, String content) {
        try {

            FileOutputStream outStream = context.openFileOutput(fileName,
                Context.MODE_PRIVATE);
            outStream.write(content.getBytes());
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入应用程序包files目录下文件
     *
     * @param context
     *            上下文
     * @param fileName
     *            文件名称
     * @param content
     *            文件内容
     */
    public static void write(Context context, String fileName, byte[] content) {
        try {

            FileOutputStream outStream = context.openFileOutput(fileName,
                Context.MODE_PRIVATE);
            outStream.write(content);
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入应用程序包files目录下文件
     *
     * @param context
     *            上下文
     * @param fileName
     *            文件名称
     * @param modeType
     *            文件写入模式（Context.MODE_PRIVATE、Context.MODE_APPEND、Context.
     *            MODE_WORLD_READABLE、Context.MODE_WORLD_WRITEABLE）
     * @param content
     *            文件内容
     */
    public static void write(Context context, String fileName, byte[] content,
        int modeType) {
        try {

            FileOutputStream outStream = context.openFileOutput(fileName,
                modeType);
            outStream.write(content);
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 指定编码将内容写入目标文件
     *
     * @param target
     *            目标文件
     * @param content
     *            文件内容
     * @param encoding
     *            写入文件编码
     * @throws Exception
     */
    public static void write(File target, String content, String encoding)
        throws IOException {
        BufferedWriter writer = null;
        try {
            if (!target.getParentFile().exists()) {
                target.getParentFile().mkdirs();
            }
            writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(target, false), encoding));
            writer.write(content);

        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 指定目录写入文件内容
     *
     * @param filePath
     *            文件路径+文件名
     * @param content
     *            文件内容
     * @throws IOException
     */
    public static void write(String filePath, byte[] content)
        throws IOException {
        FileOutputStream fos = null;

        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(file);
            fos.write(content);
            fos.flush();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * 写入文件
     *
     * @param inputStream 下载文件的字节流对象
     * @param filePath 文件的存放路径
     *            (带文件名称)
     * @throws IOException
     */
    public static File write(InputStream inputStream, String filePath)
        throws IOException {
        OutputStream outputStream = null;
        // 在指定目录创建一个空文件并获取文件对象
        File mFile = new File(filePath);
        if (!mFile.getParentFile().exists())
            mFile.getParentFile().mkdirs();
        try {
            outputStream = new FileOutputStream(mFile);
            byte buffer[] = new byte[4 * 1024];
            int lenght = 0;
            while ((lenght = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, lenght);
            }
            outputStream.flush();
            return mFile;
        } catch (IOException e) {
            Log.e(TAG, "写入文件失败，原因：" + e.getMessage());
            throw e;
        } finally {
            try {
                inputStream.close();
                if (outputStream != null) {
                    outputStream.close();
                    outputStream = null;
                }

            } catch (IOException e) {
            }
        }
    }

    /**
     * 指定目录写入文件内容
     *
     * @param filePath
     *            文件路径+文件名
     * @param bitmap
     *            文件内容
     * @throws IOException
     */
    public static void saveAsJPEG(Bitmap bitmap, String filePath)
        throws IOException {
        FileOutputStream fos = null;

        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * 指定目录写入文件内容
     *
     * @param filePath
     *            文件路径+文件名
     * @param bitmap
     *            文件内容
     * @throws IOException
     */
    public static void saveAsPNG(Bitmap bitmap, String filePath)
        throws IOException {
        FileOutputStream fos = null;

        try {
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * 将文件转成字符串
     *
     * @param file
     *            文件
     * @return
     * @throws Exception
     */
//    public static String getStringFromFile(File file) throws Exception {
//        FileInputStream fin = new FileInputStream(file);
//        String ret = convertStreamToString(fin);
//        // Make sure you close all streams.
//        fin.close();
//        return ret;
//    }

    /**
     * 复制文件
     * @param in
     * @param out
     */
    public static void copyFile(InputStream in, OutputStream out) {
        try {
            byte[] b = new byte[2 * 1024 * 1024]; //2M memory
            int len = -1;
            while ((len = in.read(b)) > 0) {
                out.write(b, 0, len);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     *快速复制
     * @param in
     * @param out
     */
    public static void copyFileFast(File in, File out) {
        FileChannel filein = null;
        FileChannel fileout = null;
        try {
            filein = new FileInputStream(in).getChannel();
            fileout = new FileOutputStream(out).getChannel();
            filein.transferTo(0, filein.size(), fileout);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                filein.close();
                fileout.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *分享文件
     * @param context
     * @param title
     * @param filePath
     */
    public static void shareFile(Context context, String title, String filePath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        Uri uri = Uri.parse("file://" + filePath);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(intent, title));
    }



    /**
     * 格式化文件大小
     * @param context
     * @param size
     * @return
     */
    public static String formatFileSize(Context context, long size) {
        return Formatter.formatFileSize(context, size);
    }

    /**
     * 将输入流写入到文件
     * @param is
     * @param file
     */
    public static void Stream2File(InputStream is, File file) {
        byte[] b = new byte[1024];
        int len;
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            while ((len = is.read(b)) != -1) {
                os.write(b, 0, len);
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
          //  closeIO(is, os);
        }
    }

    /**
     * 创建文件夹(支持覆盖已存在的同名文件夹)
     * @param filePath
     * @param recreate
     * @return
     */
    public static boolean createFolder(String filePath, boolean recreate) {
        String folderName = getFolderName(filePath);
        if (folderName == null || folderName.length() == 0 || folderName.trim().length() == 0) {
            return false;
        }
        File folder = new File(folderName);
        if (folder.exists()) {
            if (recreate) {
                deleteFile(folderName);
                return folder.mkdirs();
            } else {
                return true;
            }
        } else {
            return folder.mkdirs();
        }
    }

    public static boolean deleteFile(String filename) {
        return new File(filename).delete();
    }


    /**
     * 判断字符串中是否包含中文
     * @param str
     * 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
    /**
     * 重命名文件\文件夹
     * @param filepath
     * @param newName
     * @return
     */
    public static boolean rename(String filepath, String newName) {
        File file = new File(filepath);
        return file.exists() && file.renameTo(new File(newName));
    }

    /**
     * 获取文件夹名称
     * @param filePath
     * @return
     */
    public static String getFolderName(String filePath) {
        if (filePath == null || filePath.length() == 0 || filePath.trim().length() == 0) {
            return filePath;
        }
        int filePos = filePath.lastIndexOf(File.separator);
        return (filePos == -1) ? "" : filePath.substring(0, filePos);
    }

    /**
     * 获取文件夹下所有文件
     * @param path
     * @return
     */
    public static ArrayList<File> getFilesArray(String path) {
        File file = new File(path);
        File files[] = file.listFiles();
        ArrayList<File> listFile = new ArrayList<File>();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    listFile.add(files[i]);
                }
                if (files[i].isDirectory()) {
                    listFile.addAll(getFilesArray(files[i].toString()));
                }
            }
        }
        return listFile;
    }

    /**
     * 打开图片
     * @param mContext
     * @param imagePath
     */
    public static void openImage(Context mContext, String imagePath) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(imagePath));
        intent.setDataAndType(uri, "image/*");
        mContext.startActivity(intent);
    }

    /**
     * 打开视频
     * @param mContext
     * @param videoPath
     */
    public static void openVideo(Context mContext, String videoPath) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(videoPath));
        intent.setDataAndType(uri, "video/*");
        mContext.startActivity(intent);
    }

    /**
     * 打开URL
     * @param mContext
     * @param url
     */
    public static void openURL(Context mContext, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        mContext.startActivity(intent);
    }

    /**
     * 下载文件
     * @param context
     * @param fileurl
     */
    public static void downloadFile(Context context, String fileurl) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileurl));
        request.setDestinationInExternalPublicDir("/Download/", fileurl.substring(fileurl.lastIndexOf("/") + 1));
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(request);
    }

    /**
     * 通过APKURL升级应用
     * @param context
     * @param fileurl
     */
//    public static void upgradeApp(Context context, String fileurl) {
//        Intent intent = new Intent(context, DownloadService.class);
//        intent.putExtra("fileurl", fileurl);
//        context.startService(intent);
//    }
    //是否复制
    public static boolean COPY_FALG = false;
    public static boolean copyFile(File src, File desc){
        //创建字节流对象(输入,输出)
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        //创建文件输入流,输入流对象
        FileInputStream srcInputStream  = null;
        FileOutputStream descOutputStream= null;
        //记录同文件复制数量操作
        int count = 0;
        //是否存在相同文件
        boolean boolCover = false;
        //单文件复制操作实现
        if(src.isFile()){
            try {
                //获取需要复制下目录列表文件数组
                File[] list = desc.listFiles();
                //获取复制文件名
                String srcname = src.toString().substring(src.toString().lastIndexOf("\\")+1, src.toString().length()).trim();
                if(null != list)
                {
                    if(list.length > 0)
                    {
                        //循环判断复制目录下是否和源文名相同
                        for(int i = 0; i < list.length; i++)
                        {
                            //获取复制目录下文件名
                            String descname = list[i].toString().substring(list[i].toString().lastIndexOf("\\")+1, list[i].toString().length()).trim();
                            //判定复制文件名和目录文件名相同，记录重复数为1
                            if(srcname.equals(descname)){
                                count = count + 1;
                                boolCover = true;
                            }
                            if(descname.indexOf("copy") != -1 && descname.indexOf(srcname.substring(srcname.indexOf(")")+1, srcname.length())) != -1){
                                count = count + 1;
                            }
                        }
                    }
                }
                //存在重复文件信息
                if(boolCover)
                {
                    if(count == 1)
                    {
                        if(desc.toString().indexOf(".") != -1)
                        {
                            //向磁盘中写入： 复件 + 复制文件名称
                            descOutputStream = new FileOutputStream(desc.toString() + "\\copy " );
                        }else
                        {
                            //向磁盘中写入： 复件 + 复制文件名称
                            descOutputStream = new FileOutputStream(desc.toString() + "\\copy " + srcname);
                        }
                    }else{
                        if(desc.toString().indexOf(".") != -1)
                        {
                            //向磁盘中写入： 复件(记录数)+ 复制文件名称
                            descOutputStream = new FileOutputStream(desc.toString() + "\\copy ("+count+") ");
                        }else
                        {
                            //向磁盘中写入： 复件(记录数)+ 复制文件名称
                            descOutputStream = new FileOutputStream(desc.toString() + "\\复件 ("+count+") " + srcname);
                        }
                    }
                }else{
                    if(desc.toString().indexOf(".") != -1)
                    {
                        descOutputStream = new FileOutputStream(desc.toString() + "\\" );
                    }else
                    {
                        descOutputStream = new FileOutputStream(desc.toString() + "\\" + srcname);
                    }
                }
                byte[] buf = new byte[1];
                srcInputStream = new FileInputStream(src);
                bis = new BufferedInputStream(srcInputStream);
                bos = new BufferedOutputStream(descOutputStream);
                while(bis.read(buf) != -1){
                    bos.write(buf);
                    bos.flush();
                }
                COPY_FALG = true;
            } catch (Exception e) {
                COPY_FALG = false;
                e.printStackTrace();
                Log.e("copyFile:","文件复制操作出现异常！" + e.getMessage());
            }finally{
                try {
                    if(bis != null){
                        bis.close();
                    }
                    if(bos != null){
                        bos.close();
                    }
                } catch (IOException e) {
                    COPY_FALG = false;
                    e.printStackTrace();
                    Log.e("copyFile:", "文件复制操作，数据流关闭出现异常！" + e.getMessage());
                }
            }
        }else if(src.isDirectory()){
            //创建目录
            desc.mkdir();
            File[] list = src.listFiles();
            //循环向目标目录写如内容
            for(int i = 0; i < list.length; i++){
                String fileName = list[i].getAbsolutePath().substring(src.getAbsolutePath().length(), list[i].getAbsolutePath().length());
                File descFile = new File(desc.getAbsolutePath()+ fileName);
                copyFile(list[i], descFile);
            }
        }
        return COPY_FALG;
    }


    /**
     * 检查SD卡是否挂载
     *
     * @return
     */
    public static boolean checkSDcard(Context context){
        boolean flag = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (!flag) {
            Toast.makeText(context, "请插入手机存储卡再使用本功能", Toast.LENGTH_SHORT).show();
        }
        return flag;
    }
}
