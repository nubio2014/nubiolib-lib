package com.mlzq.nubiolib.app;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.mlzq.nubiolib.other.ShowImageUtils;
import com.mlzq.nubiolib.widget.CircleNetworkImageView;
import com.mlzq.nubiolib.widget.rImageView;

/**
 * Created by Dev on 2017/8/9.
 */

public class BaseViewHolder {
    //现在对于int作为键的官方推荐用SparseArray替代HashMap
    private SparseArray<View> views;
    private int mPosition;
    public View mConvertView;
    private Context context;
  //  ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();

    public BaseViewHolder(Context context, int postion, int layoutId, ViewGroup parent) {
        this.views = new SparseArray<View>();
        mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        this.context = context;
        this.mPosition = postion;
        mConvertView.setTag(this);

    }



    public BaseViewHolder setWidgetIsVisiable(int viewId, int isVisiable) {
        View view = getView(viewId);
        view.setVisibility(isVisiable);
        return this;
    }

    /**
     * 设置选择框
     *
     * @param viewId 控件ID
     * @return
     */
    public BaseViewHolder setCheck(int viewId, boolean ischeck) {
        CheckBox tv_txt = getView(viewId);
        tv_txt.setChecked(ischeck);
        return this;
    }

    /**
     * 拿到一个ViewHolder对象
     */

    public static BaseViewHolder get(Context context, View convertView, int position, int layoutId, ViewGroup parent) {
        // BaseViewHolder holder;
        if (convertView == null) {
            return new BaseViewHolder(context, position, layoutId, parent);
        } else {
            return (BaseViewHolder) convertView.getTag();
        }
        //return holder;
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     */

    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public View getmConvertView() {
        return mConvertView;
    }

    public int getPosition() {
        return mPosition;
    }

    public BaseViewHolder setMaxLines(int viewId, int num) {
        TextView tv_txt = getView(viewId);
        tv_txt.setMaxLines(num);
        return this;
    }

    public BaseViewHolder setNetImage(Context context, int viewId, String url) {
        ImageView tv_txt = getView(viewId);
        ShowImageUtils.showImageViewGone(context, tv_txt, url);
        return this;
    }


    public BaseViewHolder setLinMarTop(int viewId, int num) {
        TextView tv_txt = getView(viewId);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tv_txt.getLayoutParams();
        layoutParams.setMargins(0, num, 0, 0);//4个参数按顺序分别是左上右下
        tv_txt.setLayoutParams(layoutParams); //mView是控件
        return this;
    }

    public BaseViewHolder setTextAndColor(int viewId, String text, int color) {
        TextView tv_txt = getView(viewId);
        tv_txt.setText(text);
        tv_txt.setTextColor(color);
        return this;
    }

    public BaseViewHolder setTextAndColor2(int viewId, String text, String color) {
        TextView tv_txt = getView(viewId);
        tv_txt.setText(text);
        tv_txt.setTextColor(Color.parseColor(color));
        return this;
    }

    /**
     * 设置字符串
     *
     * @param viewId 控件ID
     * @param text
     * @return
     */
    public BaseViewHolder setText(int viewId, String text) {
        TextView tv_txt = getView(viewId);
        tv_txt.setText(text);
        return this;
    }

    /**
     * 设置文字居中和宽度铺满
     **/
    public BaseViewHolder setTextCenter(int viewId) {
        TextView tv_txt = getView(viewId);
        LinearLayout.LayoutParams paramses = (LinearLayout.LayoutParams) tv_txt.getLayoutParams();
        paramses.width = WindowManager.LayoutParams.MATCH_PARENT;
        tv_txt.setLayoutParams(paramses);
        tv_txt.setGravity(Gravity.CENTER);
        return this;
    }

    public BaseViewHolder setTextListener(int viewId, View.OnClickListener listener) {
        TextView tv_txt = getView(viewId);
        tv_txt.setOnClickListener(listener);
        return this;
    }

    public BaseViewHolder setTextColor(int viewId, int tc) {
        TextView tv_txt = getView(viewId);
        tv_txt.setTextColor(tc);
        return this;
    }


    /**
     * @param viewId  控件ID
     * @param text_bg
     * @return
     */
    public BaseViewHolder setTextBG(int viewId, int text_bg) {
        TextView tv_txt = getView(viewId);
        tv_txt.setBackgroundResource(text_bg);
        return this;
    }
/************************************图片控件**********************************************************************************************************/

    public BaseViewHolder setNetworkImageView(int viewId,int error,String url ){
         NetworkImageView img=getView(viewId);//   tv_txt=getView(viewId);
         img.setDefaultImageResId(error);
        img.setErrorImageResId(error);
       // img.setImageUrl(url,imageLoader);

    return this;
}
    public BaseViewHolder setNetworkImageViewLocal(int viewId,int id ){
        NetworkImageView img=getView(viewId);//   tv_txt=getView(viewId);
        img.setDefaultImageResId(id);


        return this;
    }
    /**
     * 设置原型图标
     * @param viewId
     * @param url
     * @param color
     * @return
     */
    public BaseViewHolder setCircleNetworkImageView(int viewId,String url ,int error,int color,boolean isColor){
        CircleNetworkImageView img=getView(viewId);//   tv_txt=getView(viewId);
        img.setDefaultImageResId(error);
     //   img.setImageUrl(url,imageLoader);
        img.setBorderColor(color);
        if (isColor)
            img.setBorderWidth(2);
        else
            img.setBorderWidth(0);
        img.setErrorImageResId(error);

        return this;
    }
    /**
     * 设置本地图片
     *
     * @param viewId
     * @param img
     * @return
     */
    public BaseViewHolder setLocalImage(int viewId, int img) {
        ImageView iv = getView(viewId);
        iv.setImageResource(img);
        return this;
    }

    /**
     * 设置本地图片是否显示
     *
     * @param viewId
     * @param img
     * @return
     */
    public BaseViewHolder setImage(int viewId, int isshow, int img) {
        ImageView iv = getView(viewId);//   tv_txt=getView(viewId);
        iv.setVisibility(isshow);
        iv.setImageResource(img);
        return this;
    }

    /**
     * 设置本地图片是否显示
     *
     * @param viewId
     * @return
     */
    public BaseViewHolder setImageNet(Context context, int viewId, String url) {
        rImageView iv = getView(viewId);
        ShowImageUtils.showImageView(context, url, iv);
        return this;
    }

    /**
     * 设置本地图片是否显示
     *
     * @param viewId
     * @return
     */
    public BaseViewHolder setImageNetwork(Context context, int viewId, String url) {
        ImageView iv = getView(viewId);
        ShowImageUtils.showImageView(context, url, iv);
        return this;
    }


    /**
     * 设置选择框
     *
     * @param viewId 控件ID
     * @param text
     * @return
     */
    public BaseViewHolder setcbText(int viewId, String text, boolean ischeck) {
        CheckBox tv_txt = getView(viewId);
        tv_txt.setText(text);
        tv_txt.setChecked(ischeck);
        return this;
    }

    public BaseViewHolder setcbText(int viewId, String text) {
        CheckBox tv_txt = getView(viewId);
        tv_txt.setText(text);
        return this;
    }


//    /***
//     * 九宫格控件
//     */
//    public  BaseViewHolder setNineGrifView(int viewId, final Context context, final List<MoreBean.ContentBean>list) {
//        MyNineGridView nineGridView=getView(viewId);
//        BaseAdapters<MoreBean.ContentBean> adapters1=new BaseAdapters<MoreBean.ContentBean>(context,list,R.layout.item_grid_netimgandtext) {
//            @Override
//            public void convert(BaseViewHolder helper, MoreBean.ContentBean item) {
//                helper.setText(R.id.grid_item_name,item.getName());
//                helper.setNetworkImageView(R.id.grid_item_img, ImageTool.imgUrl(item.getIcon()));
//                //   L.d("图片地址："+MyUrl.BASEURL+item.getIcon());
//            }
//        };
//        nineGridView.setAdapter(adapters1);
//        nineGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                MoreBean.ContentBean bean=list.get(position);
//                Bundle bundle = new Bundle();
//                if (bean.getLinkUrl() != null) {
//                    bundle.putString("Link", bean.getLinkUrl());
//                    Intent it = new Intent();
//                    it.putExtras(bundle);
//                    it.setClass(context, WebViewChorm.class);
//                    context.startActivity(it);
//                }
//
//                // ToastUtils.showToast(context,list.get(position).getLinkUrl());
//            }
//        });
//        return this;
//    }

}
