package com.mlzq.nubiolib.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mlzq.nubiolib.R;

/**
 *
 */
public class ShowDialog {
	static Dialog dialog2;
	static Context act;

//	public static Dialog showCustomDialogTime(final Context context) {
//		act = context;
//		// 使用AlterDialog
//		LinearLayout view = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.loading_time, null);
//		GifImageView img= (GifImageView) view.findViewById(R.id.gif);
//		GifDrawable gifDrawable = null;
//		try {
//			gifDrawable = new GifDrawable(context.getResources(), R.drawable.icon_gif);
//			img.setBackground(gifDrawable);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		AlertDialog.Builder builder = new AlertDialog.Builder(context);
//		final AlertDialog dialog = builder.create();
//		dialog.setCancelable(true);
//		dialog.setCanceledOnTouchOutside(false);
//		dialog.show();
//		Window window = dialog.getWindow();
//		// 设置显示位置
//		WindowManager.LayoutParams lp = window.getAttributes();
//		window.setGravity(Gravity.CENTER);// 居中显示
//		window.setAttributes(lp);
//		window.setContentView(view);
//
//		final GifDrawable finalGifDrawable = gifDrawable;
//		new Handler().postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				finalGifDrawable.stop();
//				Bundle bundle = new Bundle();
//				bundle.putString("Link","http://app.profit.blihvip.com/");
//				Intent it3 = new Intent();
//				it3.putExtras(bundle);
//				it3.setClass(context, BrowserActivity.class);
//				context.startActivity(it3);
//			}
//		},3000);
//
//
//
//		return dialog;
//	}
//	public static Dialog showCustomDialog(Context context) {
//		act = context;
//		// 使用AlterDialog
//		RelativeLayout view = (RelativeLayout) LayoutInflater.from(
//				context).inflate(R.layout.loading, null);
//		ImageView img= (ImageView) view.findViewById(R.id.loading_img);
//		//img.setImageResource(R.drawable.loading_image_amin);
//		AnimationDrawable animationDrawable = (AnimationDrawable) img.getDrawable();
//		animationDrawable.start();
//		AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.ActionSheetDialogStyle);
//		final AlertDialog dialog = builder.create();
//		dialog.setCancelable(true);
//		dialog.setCanceledOnTouchOutside(false);
//		dialog.show();
//		Window window = dialog.getWindow();
//		// 设置显示位置
//		WindowManager.LayoutParams lp = window.getAttributes();
//		window.setGravity(Gravity.CENTER);// 居中显示
//		window.setAttributes(lp);
//		window.setContentView(view);
//		dialog2 = dialog;
//		return dialog2;
//	}
//	static Handler handler = new Handler() {
//		public void handleMessage(android.os.Message msg) {
//			Toast.makeText(act, msg.obj.toString(), Toast.LENGTH_SHORT).show();
//		};
//	};
//
//	public static void closeDialog(Dialog mDialogUtils) {
//		if (mDialogUtils != null && mDialogUtils.isShowing()) {
//			mDialogUtils.dismiss();
//		}
//	}
//
	public static void showUniteDialog(Context context, String msg) {
		final Dialog dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_show, null);
		TextView tv_msg = (TextView) view.findViewById(R.id.ds_msg);
		TextView click = (TextView) view.findViewById(R.id.tv_click);
		dialog.setContentView(view);
		//获取当前Activity所在的窗体
		Window dialogWindow = dialog.getWindow();
		//设置Dialog从窗体居中弹出
		dialogWindow.setGravity(Gravity.CENTER);
		dialog.show();
//		WindowManager windowManager = getWindowManager();
//		Display display = windowManager.getDefaultDisplay();
		int width = context.getResources().getDisplayMetrics().widthPixels;
		int heigh = context.getResources().getDisplayMetrics().heightPixels;
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.width = (int) (width * 0.75); //设置宽度
		dialog.getWindow().setAttributes(lp);

//		WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
//		params.gravity = Gravity.BOTTOM;
//		dialog.getWindow().setAttributes(params);
		tv_msg.setText(msg);
		click.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}






//
//
//
//	public static void showServiceDialog(Context context, String msg,String content) {
//		final Dialog dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
//		View view = LayoutInflater.from(context).inflate(R.layout.resigterservice, null);
//		TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
//		WebView webview = (WebView) view.findViewById(R.id.web_content);
//		TextView click=view.findViewById(R.id.tv_close);
//		dialog.setContentView(view);
//		//获取当前Activity所在的窗体
//		Window dialogWindow = dialog.getWindow();
//		//设置Dialog从窗体居中弹出
//		dialogWindow.setGravity(Gravity.CENTER);
//		dialog.show();
////		WindowManager windowManager = getWindowManager();
////		Display display = windowManager.getDefaultDisplay();
//		int width = context.getResources().getDisplayMetrics().widthPixels;
//		int heigh = context.getResources().getDisplayMetrics().heightPixels;
//		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
//		lp.width = (int) (width * 0.75); //设置宽度
//		lp.height=(int)(heigh*0.75);
//		dialog.getWindow().setAttributes(lp);
//		setWebView(webview);
////		WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
////		params.gravity = Gravity.BOTTOM;
////		dialog.getWindow().setAttributes(params);
//		tv_title.setText(msg);
//
//
//		Document doc = Jsoup.parse(content);
//		Elements pngsElement = doc.select("img[src]");
//		for (Element element : pngsElement) {
//			String imgurl = element.attr("src");
//			if (imgurl.startsWith("https:") || imgurl.startsWith("http:")) {
//
//			}else{
//				imgurl = MyUrl.BASEURL + imgurl;
//				element.attr("src", imgurl);
//			}
//		}
//		webview.loadData(doc.toString(), "text/html; charset=UTF-8", "UTF-8");
//		click.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//			}
//		});
//	}
//	/**
//	 * WebView设置
//	 */
//	public static void setWebView(WebView mWebview) {
//		//启用支持javascript
//		//  WebSettings settings = webmenu.getSettings();
//		mWebview.getSettings().setJavaScriptEnabled(true);
//		//  webmenu.addJavascriptInterface(new JsObject(),"injectedObject");
//		//mWebview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存
//		mWebview.getSettings().setSupportZoom(true); // 支持缩放
//		mWebview.getSettings().setTextSize(WebSettings.TextSize.NORMAL);
//		mWebview.getSettings().setUseWideViewPort(true);//设//将图片调整到适合webview的大小
//		mWebview.getSettings().setLoadWithOverviewMode(true); //// 缩放至屏幕的大小
//		mWebview.getSettings().setBuiltInZoomControls(false);// 设置缩放
//		mWebview.getSettings().setDisplayZoomControls(false);//隐藏显示缩放按钮
//		mWebview.getSettings().setDomStorageEnabled(true);//DOM储存API打开
//		mWebview.getSettings().setDefaultFontSize(36);
//
//		//覆盖mWebview默认使用第三方或系统默认浏览器打开网页的行为，使网页用mWebview打开
//		// 设置加载进来的页面自适应手机屏幕
//
//		mWebview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//		//mWebview.getSettings().setMediaPlaybackRequiresUserGesture(false);
//		mWebview.setWebViewClient(new WebViewClient() {
//			@Override
//			public boolean shouldOverrideUrlLoading(WebView view, String url) {
//				// TODO Auto-generated method stub
//				//返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//				view.loadUrl(url);
//				return true;
//			}
//		});
//		mWebview.setWebChromeClient(new WebChromeClient() {
//			@Override
//			public void onProgressChanged(WebView view, int newProgress) {
//				// TODO Auto-generated method stub
//				if (newProgress == 100) {
//					// 网页加载完成
//				} else {
//
//				}
//
//			}
//
//			@Override
//			public void onReceivedTitle(WebView view, String title) {
//				// TODO Auto-generated method stub
//				super.onReceivedTitle(view, title);
//				//tv_title.setText(title);
//			}
//
//		});
//	}
//
//
//
//
//	public void Init(Context context,List<GeneralBean>list,View showAsDropDown) {
//		LinearLayout view = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.select_garden, null);
//		//  创建PopupWindow对象，指定宽度和高度
//		SupportPopupWindow	window = new SupportPopupWindow(view, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
//		// 设置动画
//		window.setAnimationStyle(R.style.ActionSheetDialogStyle);
//		//  设置背景颜色
//		window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
//		// 设置可以获取焦点
//		window.setFocusable(true);
//		//  设置可以触摸弹出框以外的区域
//		window.setOutsideTouchable(true);
//		// 更新popupwindow的状态
//		window.update();
//		window.showAsDropDown(showAsDropDown);
//		BaseAdapters<GeneralBean>adapters=new BaseAdapters<GeneralBean>(context,list,R.layout.item_selectgarden_item) {
//			@Override
//			public void convert(BaseViewHolder helper, GeneralBean item, int position) {
//
//			}
//		};
//
//	}



	public static Dialog showCustomDialog(Context context) {
		act=context;

		// 使用AlterDialog
		RelativeLayout view = (RelativeLayout) LayoutInflater.from(
				context).inflate(R.layout.loading, null);
		view.setBackgroundColor(Color.parseColor("#00000000"));
		AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.ActionSheetDialogStyle);
		final AlertDialog dialog = builder.create();
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		Window window = dialog.getWindow();
		// 设置显示位置
		WindowManager.LayoutParams lp = window.getAttributes();
		window.setGravity(Gravity.CENTER);// 居中显示
		window.setAttributes(lp);
		window.setContentView(view);
		dialog2 = dialog;
		return dialog2;
	}

	public static void closeDialog(Dialog mDialogUtils) {
		if (mDialogUtils != null && mDialogUtils.isShowing()) {
			mDialogUtils.dismiss();
		}
	}

}
