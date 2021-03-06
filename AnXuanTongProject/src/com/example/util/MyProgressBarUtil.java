package com.example.util;

import com.anxuantong.ym.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * 自定义控件之progressbar等待滚动条的util工具�? * @author panmp
 *
 */
public class MyProgressBarUtil extends Dialog{
	private Context mContext = null;  
	private Dialog mDialog = null;
	
	public MyProgressBarUtil(Context context) {
		super(context);  
        this.mContext = context;  
	}
	public MyProgressBarUtil(Context context, int theme) {  
        super(context, theme);  
    } 
	/**
	 * 自定义样式等待滚动弹出框
	 * @param title  滚动条标�?	 * @param content 滚动条内�?	 * @param cancelable 设置进度条滚动中是否取消的表�? true表示取消，false表示不取�?	 * @author panmp
	 */
	public void showProgessDialog(String pTitle,String pContent,boolean pCancelable){
		if (null == mDialog){
			mDialog = new MyProgressBarUtil(this.mContext,R.style.ProgressDialog);  
			mDialog.setContentView(R.layout.myprogressbarutil);  
			mDialog.getWindow().getAttributes().gravity = Gravity.CENTER; 
			ImageView bImageView = (ImageView) mDialog.findViewById(R.id.loadingImageView);  
	        AnimationDrawable animationDrawable = (AnimationDrawable) bImageView.getBackground();  
	        animationDrawable.start(); 
		}
		if(!"".equals(pContent)){ //
			//mDialog.setMessage(pContent);
		}
		if(!"".equals(pTitle)){
			//mDialog.setTitle(pTitle);	
		}
		mDialog.setCancelable(pCancelable);
		mDialog.show();
		WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
		lp.dimAmount =0f;
		mDialog.getWindow().setAttributes(lp);
	}
	
	/**
	 * 关闭自定义等待进度框
	 * @author panmp
	 */
	public void dismissProgessBarDialog(){
		if(null != mDialog){
			mDialog.dismiss();
		}
	}
	 
}
