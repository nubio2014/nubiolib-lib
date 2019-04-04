//package com.mlzq.nubiolib.example;
//
//import android.Manifest;
//import android.app.Activity;
//import android.content.ContentResolver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.content.FileProvider;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.View;
//import android.view.WindowManager;
//import android.view.animation.AnimationUtils;
//import android.widget.AdapterView;
//import android.widget.LinearLayout;
//import android.widget.PopupWindow;
//import android.widget.TextView;
//
//import com.google.gson.GsonBuilder;
//import com.google.gson.reflect.TypeToken;
//import com.mlzq.nubiolib.Dialog.ToastUtils;
//import com.mlzq.nubiolib.R;
//import com.mlzq.nubiolib.addpicture.CustomConstants;
//import com.mlzq.nubiolib.addpicture.ImageBucketChooseActivity;
//import com.mlzq.nubiolib.addpicture.ImageItem;
//import com.mlzq.nubiolib.addpicture.IntentConstants;
//import com.mlzq.nubiolib.addpicture.SubmitImageAdapter;
//import com.mlzq.nubiolib.http.L;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Dev on 2018/9/12.
// * desc :
// */
//
//public class photoexample {
//    public  List<ImageItem> mDataList = new ArrayList<ImageItem>();
//    SubmitImageAdapter mAdapter;
//    Uri cameraUri;
//    private String path = "";
//    public  List<Object> imgUrlList = new ArrayList<>();
//private Context context=this;
//    /**
//     * 自动获取相机权限
//     */
//    private  final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;
//
//
//    /**
//     * oncreate
//     */
//      mDataList.clear();
//        imgUrlList.clear();
//    initData();
//    initView();
//
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        notifyDataChanged(); //当在ImageZoomActivity中删除图片时，返回这里需要刷新
//    }
//    private void initData() {
//        getTempFromPref();
//        List<ImageItem> incomingDataList = (List<ImageItem>) getIntent().getSerializableExtra("image_list");
//        if (incomingDataList != null) {
//            mDataList.addAll(incomingDataList);
//            for (int i = 0; i < incomingDataList.size(); i++) {
//                Log.e("图片地址：", mDataList.get(i).sourcePath);
//            }
//        }
//    }
//    private void initView() {
//        L.d("数组：" + mDataList + "");
//        mAaGrid.setSelector(new ColorDrawable(Color.TRANSPARENT));
//        mAdapter = new SubmitImageAdapter(this, mDataList, 3);
//        mAaGrid.setAdapter(mAdapter);
//
//        mAaGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                CustomConstants.MAX_IMAGE_SIZE = 3;
//                CustomConstants.APPLICATION_NAME = "myApp";
//                CustomConstants.PREF_TEMP_IMAGES = "pref_temp_images";
//                IntentConstants.EXTRA_IMAGE_LIST = "image_list";
//                IntentConstants.EXTRA_BUCKET_NAME = "buck_name";
//                IntentConstants.EXTRA_CAN_ADD_IMAGE_SIZE = "can_adbd_image_size";
//                if (position == getDataSize()) {
//                    new PopupWindows(context.this, mAaGrid);
//                }
//
//            }
//        });
//    }
//    public class PopupWindows extends PopupWindow {
//        public PopupWindows(final Activity mContext, View parent) {
//            View view = View.inflate(mContext, R.layout.item_popupwindow, null);
//            view.startAnimation(AnimationUtils.loadAnimation(mContext,
//                    R.anim.actionsheet_dialog_in));
//            LinearLayout ll_popup = (LinearLayout) view
//                    .findViewById(R.id.ll_popup);
//            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
//                    R.anim.actionsheet_dialog_in));
//
//            setWidth(WindowManager.LayoutParams.MATCH_PARENT);
//            setHeight(WindowManager.LayoutParams.MATCH_PARENT);
//            setFocusable(true);
//            setOutsideTouchable(true);
//            setContentView(view);
//            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
//            update();
//
//            TextView bt1 = (TextView) view.findViewById(R.id.item_popupwindows_camera);
//            TextView bt2 = (TextView) view
//                    .findViewById(R.id.item_popupwindows_Photo);
//            TextView bt3 = (TextView) view
//                    .findViewById(R.id.item_popupwindows_cancel);
//            bt1.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    takePhoto();
//                    dismiss();
//                }
//            });
//            bt2.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    CustomConstants.context = context.this;
//                    Intent intent = new Intent(mContext,
//                            ImageBucketChooseActivity.class);
//                    intent.putExtra(IntentConstants.EXTRA_CAN_ADD_IMAGE_SIZE, getAvailableSize());
//                    mContext.startActivityForResult(intent, 1);
//                    //finish();
//                    dismiss();
//                }
//            });
//            bt3.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    dismiss();
//                }
//            });
//        }
//    }
//    //获取图片数量
//    private int getAvailableSize() {
//        int availSize = CustomConstants.MAX_IMAGE_SIZE - mDataList.size();
//        if (availSize >= 0) {
//            return availSize;
//        }
//        return 0;
//    }
//    private int getDataSize() {
//        return mDataList == null ? 0 : mDataList.size();
//    }
//    private void getTempFromPref() {
//        SharedPreferences sp = getSharedPreferences(CustomConstants.APPLICATION_NAME, MODE_PRIVATE);
//        String prefStr = sp.getString(CustomConstants.PREF_TEMP_IMAGES, null);
//        if (!TextUtils.isEmpty(prefStr)) {
//            List<ImageItem> tempImages = new GsonBuilder().serializeNulls().create().fromJson(prefStr, new TypeToken<List<ImageItem>>() {
//            }.getType());//(prefStr, ImageItem.class);
//            mDataList = tempImages;
//        }
//    }
//    public void takePhoto() {
//        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//        File vFile = new File(Environment.getExternalStorageDirectory()
//                + "/myimage/", String.valueOf(System.currentTimeMillis())
//                + ".jpg");
//        if (!vFile.exists()) {
//            File vDirPath = vFile.getParentFile();
//            vDirPath.mkdirs();
//        } else {
//            if (vFile.exists()) {
//                vFile.delete();
//            }
//        }
//        path = vFile.getAbsolutePath();
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
//                ToastUtils.showToast(this, "您已经拒绝过一次");
//            }
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
//        } else {
//            if (hasSdcard()) {
//                cameraUri = Uri.fromFile(vFile);
//
//                //通过FileProvider创建一个content类型的Uri
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    cameraUri = FileProvider.getUriForFile(context.this, getPackageName() + ".fileprovider", vFile);
//
//                }
//
//                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
//                startActivityForResult(openCameraIntent, 0);
//
//            } else {
//                ToastUtils.showToast(this, "设备没有SD卡！");
//            }
//        }
//    }
//    private void notifyDataChanged() {
//        mAdapter.notifyDataSetChanged();
//    }
//    private void removeTempFromPref() {
//        SharedPreferences sp = getSharedPreferences(
//                CustomConstants.APPLICATION_NAME, MODE_PRIVATE);
//        sp.edit().remove(CustomConstants.PREF_TEMP_IMAGES).commit();
//    }
//    public static boolean hasSdcard() {
//        String state = Environment.getExternalStorageState();
//        return state.equals(Environment.MEDIA_MOUNTED);
//    }
//    public String getRealFilePath(final Context context, final Uri uri) {
//        if (null == uri) return null;
//        final String scheme = uri.getScheme();
//        String data = null;
//        if (scheme == null)
//            data = uri.getPath();
//        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
//            data = uri.getPath();
//        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
//            data = path;
//        }
//        return data;
//    }
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case 0:
//                if (mDataList.size() < CustomConstants.MAX_IMAGE_SIZE && resultCode == -1 && !TextUtils.isEmpty(path)) {
//
//                    final ImageItem item = new ImageItem();
//                    L.e("" + cameraUri);
//                    item.sourcePath = getRealFilePath(context.this, cameraUri);
//                    L.e("" + cameraUri);
//                    mDataList.add(item);
//                }
//
//                break;
//            case 1:
//                if (data != null) {
//                    try {
//                        getTempFromPref();
//                        List<ImageItem> incomingDataList = (List<ImageItem>) data.getSerializableExtra("image_list");
//                        L.d("incomingDataList数组：" + incomingDataList);
//                        if (incomingDataList != null) {
//                            mDataList.addAll(incomingDataList);
//                            for (int i = 0; i < incomingDataList.size(); i++) {
//                                Log.e("图片地址：", mDataList.get(i).sourcePath);
//                            }
//                        }
//                    } catch (Exception e) {
//                    }
//                }
//                break;
//        }
//    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        imgUrlList.clear();
//        mDataList.clear();
//        removeTempFromPref();
//    }
//}
