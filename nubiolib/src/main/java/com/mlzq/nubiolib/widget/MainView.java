package com.mlzq.nubiolib.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/****
 * 写字板
 */
public class MainView extends View {
    private Paint paint;
    private Canvas cacheCanvas;
    private Bitmap cachebBitmap;
    private Path path;

    private int clr_bg, clr_fg;

    /**
     * 是否已经签名
     */
    public boolean isTouched = false;

    private void init() {
        clr_bg = Color.WHITE;
    //    clr_fg = Color.CYAN;
        clr_fg = Color.BLACK;
        isTouched = false;
        paint = new Paint();
        paint.setAntiAlias(true); // 抗锯齿
        paint.setStrokeWidth(10); // 线条宽度
        paint.setStyle(Paint.Style.STROKE); // 画轮廓
        paint.setColor(clr_fg); // 颜色
        path = new Path();
        // 创建一张屏幕大小的位图，作为缓冲
//        cachebBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
//        cacheCanvas = new Canvas(cachebBitmap);
//        cacheCanvas.drawColor(clr_bg);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //创建跟view一样大的bitmap，用来保存签名
        try {
            cachebBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            cacheCanvas = new Canvas(cachebBitmap);
            cacheCanvas.drawColor(clr_bg);
            isTouched = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap getBitmap() {
        return cachebBitmap;
    }

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MainView(Context context) {
        super(context);
        init();
    }

    public MainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(clr_bg);
        // 绘制上一次的，否则不连贯  
        canvas.drawBitmap(cachebBitmap, 0, 0, null);
        canvas.drawPath(path, paint);
    }

    /**
     * 清空画布
     */
    public void clear() {
        path.reset();
        isTouched = false;
        cacheCanvas.drawColor(clr_bg);
        invalidate();
    }

    /**
     * 保存画板
     *
     * @param path 保存到路径
     */
    public void save(String path) throws IOException {
        Bitmap bitmap = cachebBitmap;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, bos);
        byte[] buffer = bos.toByteArray();
        if (buffer != null) {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(buffer);
            outputStream.close();
        }
    }

    public void delete(String fileName) {
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

    public void deleteFile(File file) {
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

    private float cur_x, cur_y;
    private boolean isMoving;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub  
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                cur_x = x;
                cur_y = y;
                path.moveTo(cur_x, cur_y);
                isMoving = true;
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                if (!isMoving)
                    break;
                isTouched = true;
                // 二次曲线方式绘制  
                path.quadTo(cur_x, cur_y, x, y);
                // 下面这个方法貌似跟上面一样  
                // path.lineTo(x, y);  
                cur_x = x;
                cur_y = y;
                break;
            }

            case MotionEvent.ACTION_UP: {
                // 鼠标弹起保存最后状态  
                cacheCanvas.drawPath(path, paint);
                path.reset();
                isMoving = false;
                break;
            }
        }

        // 通知刷新界面  
        invalidate();

        return true;
    }

}  