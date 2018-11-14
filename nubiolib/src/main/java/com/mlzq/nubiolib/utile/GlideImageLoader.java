package com.mlzq.nubiolib.utile;

import android.content.Context;
import android.widget.ImageView;

import com.mlzq.nubiolib.R;
import com.mlzq.nubiolib.http.L;
import com.mlzq.nubiolib.other.ShowImageUtils;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by Dev on 2018/6/12.
 * desc :
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        L.e("轮播地址"+path+"");
        ShowImageUtils.showImageView(context.getApplicationContext(), R.drawable.faile,path+"",imageView);
    }
}
