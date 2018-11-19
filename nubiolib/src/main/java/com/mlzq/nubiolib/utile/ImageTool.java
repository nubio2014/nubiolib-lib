package com.mlzq.nubiolib.utile;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dev on 2018/1/4.
 */

public class ImageTool {

    public String addImgUrl(String baseurl,String contentstr) {
        if (contentstr != null && contentstr.length() > 0) {
            Document doc = Jsoup.parse(contentstr);
            Elements pngsElement = doc.select("img[src]");
            for (Element element : pngsElement) {
                String imgurl2 = element.attr("src");
                if (imgurl2.trim().startsWith("/")) {
                    imgurl2 = baseurl+ imgurl2;
                    element.attr("src", imgurl2);
                }
            }
            String content2 = doc.toString();
            return content2;
        }
        return "";

    }


//    public static void showDialogImg(Activity context, String url) {
//        final Dialog dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
//        View view = LayoutInflater.from(context).inflate(R.layout.networkimg, null);
//        NetworkImageView imgclose = (NetworkImageView) view.findViewById(R.id.networkimg);
//        dialog.setContentView(view);
//        dialog.show();
//        Window dialogWindow = dialog.getWindow();
//        dialogWindow.setGravity(Gravity.CENTER);
//        dialog.show();
//        final WindowManager manager = context.getWindowManager();
//        Display display = manager.getDefaultDisplay();
//        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
//        lp.width = (int) (display.getWidth() * 0.8);
//        dialog.getWindow().setAttributes(lp);
//        imgclose.setDefaultImageResId(R.drawable.faile);
//        imgclose.setErrorImageResId(R.drawable.faile);
//        imgclose.setImageUrl(url, MyApplication.getInstance().getImageLoader());
//        imgclose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        imgclose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                dialog.dismiss();
//            }
//        });
//
//    }



    public static String getSex(int sex) {
        String sexstr = "";
        if (sex == 1) {
            sexstr = "先生";
        } else if (sex == 2) {
            sexstr = "女士";
        } else if (sex == 0) {
            sexstr = "未知";
        }
        return sexstr;
    }


    public static Bitmap getBitmapFromUrl(String url, double width, double height) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 设置了此属性一定要记得将值设置为false
        Bitmap bitmap = BitmapFactory.decodeFile(url);
        // 防止OOM发生
        options.inJustDecodeBounds = false;
        int mWidth = bitmap.getWidth();
        int mHeight = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = 1;
        float scaleHeight = 1;
        // 按照固定宽高进行缩放
        if (mWidth <= mHeight) {
            scaleWidth = (float) (width / mWidth);
            scaleHeight = (float) (height / mHeight);
        } else {
            scaleWidth = (float) (height / mWidth);
            scaleHeight = (float) (width / mHeight);
        }
        // 按照固定大小对图片进行缩放
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, mWidth, mHeight, matrix, true);
        // 用完了记得回收
        bitmap.recycle();
        return newBitmap;
    }

    /**
     * 压缩图片（质量压缩）
     *
     * @param bitmap
     */
    public static File compressImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 900) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            long length = baos.toByteArray().length;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String filename = format.format(date);
        File file = new File(Environment.getExternalStorageDirectory(), filename + ".png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {

                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        recycleBitmap(bitmap);
        return file;
    }

    public static void recycleBitmap(Bitmap... bitmaps) {
        if (bitmaps == null) {
            return;
        }
        for (Bitmap bm : bitmaps) {
            if (null != bm && !bm.isRecycled()) {
                bm.recycle();
            }
        }
    }




    /**
     * @param uri
     * @return
     */
    public static String getPath(Activity context,Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    return null;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    return null;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor actualimagecursor =context.managedQuery(uri, proj,null,null,null);
        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor.getString(actual_image_column_index);
        String end = img_path.substring(img_path.length() - 4);
        if (0 != end.compareToIgnoreCase(".jpg") && 0 != end.compareToIgnoreCase(".png")) {
            return null;
        }
        return img_path;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


}
