package com.example.util;

import java.util.List;

import com.anxuantong.ym.R;
import com.example.util.shared.AppInfo;
import com.example.util.shared.MyShareCustomAdapter;
import com.example.util.shared.SharedUtil;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

/**
 * 自定义分享列表的工具�? * @author lin
 */
public class MySharedUtil {

	private Activity activity;
	private PopupWindow mSharePopupWindow = null;
	private OnShareItemClickListener shareItemClickListener;

	public void setShareItemClickListener(
			OnShareItemClickListener shareItemClickListener) {
		this.shareItemClickListener = shareItemClickListener;
	}

	/**
	 * 屏幕密度
	 */
	private float density;

	public MySharedUtil(Activity activity) {

		this.activity = activity;

		// 获取手机屏幕信息
		DisplayMetrics metric = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
		density = metric.density; // 屏幕密度�?.75 / 1.0 / 1.5�?
	}

	/**
	 * 完全自定义弹出分享列表的popwindow
	 * 
	 * @param parent
	 *            该组件主要是给popwindow�?��坐标的标�? * @param dialogView
	 *            popwindow显示列表的布�?iew
	 * @param shareList
	 *            popwindow显示列表的布�?��的listview
	 * @param xPos
	 *            xoff,yoff基于anchor的左下角进行偏移。正数表示下方右边，负数表示（上方左边）
	 * @param yPos
	 * @param content
	 *            分享的内�? * @param adapter 自定义布�?��的�?配器
	 * @author lin
	 */
	public void initSharePopupWindow(View parent, View dialogView,
			ListView shareList, int xPos, int yPos, final String content,
			final ListAdapter adapter) {
		PopupWindow sharePopupWindow = null;

		if (null == sharePopupWindow) {
			// 加载布局文件
			List<AppInfo> shareAppInfos = new SharedUtil(activity)
					.getShareAppList();
			shareList.setAdapter(adapter);

			shareList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Intent shareIntent = new Intent(Intent.ACTION_SEND);
					AppInfo appInfo = (AppInfo) adapter.getItem(position);
					shareIntent.setComponent(new ComponentName(appInfo
							.getAppPkgName(), appInfo.getAppLauncherClassName()));
					shareIntent.setType("text/plain");
					// 这里就是组织内容了，
					shareIntent.putExtra(Intent.EXTRA_TEXT, content);
					shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					activity.startActivity(shareIntent);
				}
			});

			sharePopupWindow = new PopupWindow(dialogView,
					(int) (160 * density),
					LinearLayout.LayoutParams.WRAP_CONTENT);
		}
		// 使其聚焦
		sharePopupWindow.setFocusable(true);
		// 设置允许在外点击消失
		sharePopupWindow.setOutsideTouchable(true);
		// 这个是为了点击�?返回Back”也能使其消失，并且并不会影响你的背�?
		// sharePopupWindow.setBackgroundDrawable(new BitmapDrawable());
		// xoff,yoff基于anchor的左下角进行偏移。正数表示下方右边，负数表示（上方左边）
		// showAsDropDown(parent, xPos, yPos);
		sharePopupWindow.showAsDropDown(parent, xPos, yPos);
	}

	/**
	 * 默认初始化分享列�? * @param parent 该组件主要是给popwindow�?��坐标的标�? * @param content
	 * 分享的内�? * @author lin
	 */
	public void initDefaultSharePopupWindow(View parent, final String content) {

		PopupWindow sharePopupWindow = null;
		View view = null;
		ListView shareList = null;
		if (null == sharePopupWindow) {
			// 加载布局文件
			view = LayoutInflater.from(activity).inflate(
					R.layout.my_shared_dialog, null);
			shareList = (ListView) view.findViewById(R.id.share_list);
			List<AppInfo> shareAppInfos = new SharedUtil(activity)
					.getShareAppList();
			final MyShareCustomAdapter adapter = new MyShareCustomAdapter(
					activity, shareAppInfos);
			shareList.setAdapter(adapter);

			shareList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					Intent shareIntent = new Intent(Intent.ACTION_SEND);
					AppInfo appInfo = (AppInfo) adapter.getItem(position);
					shareIntent.setComponent(new ComponentName(appInfo
							.getAppPkgName(), appInfo.getAppLauncherClassName()));
					shareIntent.setType("text/plain");
					// shareIntent.setType("*/*");
					// 这里就是组织内容了，
					shareIntent.putExtra(Intent.EXTRA_TEXT, content);
					shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					activity.startActivity(shareIntent);
				}
			});

			sharePopupWindow = new PopupWindow(view, (int) (160 * density),
					LinearLayout.LayoutParams.WRAP_CONTENT);
		}
		// 使其聚焦
		sharePopupWindow.setFocusable(true);
		// 设置允许在外点击消失
		sharePopupWindow.setOutsideTouchable(true);
		// 这个是为了点击�?返回Back”也能使其消失，并且并不会影响你的背�?
		// sharePopupWindow.setBackgroundDrawable(new BitmapDrawable());
		// xoff,yoff基于anchor的左下角进行偏移。正数表示下方右边，负数表示（上方左边）
		// showAsDropDown(parent, xPos, yPos);
		sharePopupWindow.showAsDropDown(parent, -5, 5);

	}

	// /**
	// * 默认初始化分享列�? // * @param parent 该组件主要是给popwindow�?��坐标的标�? // * @param
	// content 分享的内�? // * @author lin
	// */
	// public void showSharePopupWindowByBottom(View parent, final String
	// content) {
	//
	// View view = null;
	// GridView shareList = null;
	// if (mSharePopupWindow == null) {
	// // 加载布局文件
	// view =
	// LayoutInflater.from(activity).inflate(R.layout.my_shared_grid_dialog,
	// null);
	// shareList = (GridView) view.findViewById(R.id.grid_share_list);
	// List<AppInfo> shareAppInfos = new SharedUtil(activity).getShareAppList();
	// final MyShareCustomAdapter adapter = new MyShareCustomAdapter(activity,
	// shareAppInfos);
	// shareList.setAdapter(adapter);
	//
	// shareList.setOnItemClickListener(new OnItemClickListener() {
	//
	// @Override
	// public void onItemClick(AdapterView<?> parent, View view, int position,
	// long id) {
	// // TODO Auto-generated method stub
	// if (mSharePopupWindow != null) {
	// mSharePopupWindow.dismiss();
	// }
	// Intent shareIntent = new Intent(Intent.ACTION_SEND);
	// AppInfo appInfo = (AppInfo) adapter.getItem(position);
	// shareIntent.setComponent(new ComponentName(appInfo.getAppPkgName(),
	// appInfo.getAppLauncherClassName()));
	// shareIntent.setType("text/plain");
	// // shareIntent.setType("*/*");
	// // 这里就是组织内容了，
	// shareIntent.putExtra(Intent.EXTRA_TEXT, content);
	// shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	// activity.startActivity(shareIntent);
	// }
	// });
	//
	// mSharePopupWindow = new PopupWindow(view,
	// LinearLayout.LayoutParams.MATCH_PARENT,
	// LinearLayout.LayoutParams.WRAP_CONTENT, true);
	// }
	// // 使其聚焦
	// mSharePopupWindow.setFocusable(true);
	// // 设置允许在外点击消失
	// mSharePopupWindow.setOutsideTouchable(true);
	// // 这个是为了点击�?返回Back”也能使其消失，并且并不会影响你的背�? // ColorDrawable dw = new
	// ColorDrawable(0xb0000000);
	// mSharePopupWindow.setBackgroundDrawable(dw);
	// // xoff,yoff基于anchor的左下角进行偏移。正数表示下方右边，负数表示（上方左边）
	// // showAsDropDown(parent, xPos, yPos);
	// //添加动画
	// mSharePopupWindow.setAnimationStyle(R.style.popwin_anim_style);
	// mSharePopupWindow.showAtLocation(parent, Gravity.BOTTOM |
	// Gravity.CENTER_HORIZONTAL, 0, 0);
	// mSharePopupWindow.getContentView().setOnKeyListener(new OnKeyListener() {
	// @Override
	// public boolean onKey(View arg0, int keyCode, KeyEvent event) {
	// if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0 &&
	// event.getAction() == KeyEvent.ACTION_DOWN) {
	// if (mSharePopupWindow != null && mSharePopupWindow.isShowing()) {
	// mSharePopupWindow.dismiss();
	// }
	// return true;
	// }
	// return false;
	//
	// }
	//
	// });
	//
	// }

	/**
	 * 默认初始化分享列�? * @param parent 该组件主要是给popwindow�?��坐标的标�? * @param content
	 * 分享的内�? * @author lin
	 */
	public void show(View parent, final String content) {

		View view = null;
		GridView shareList = null;
		if (mSharePopupWindow == null) {
			// 加载布局文件
			view = LayoutInflater.from(activity).inflate(
					R.layout.my_shared_grid_dialog, null);
			shareList = (GridView) view.findViewById(R.id.grid_share_list);
			List<AppInfo> shareAppInfos = new SharedUtil(activity)
					.getShareAppList();
			final MyShareCustomAdapter adapter = new MyShareCustomAdapter(
					activity, shareAppInfos);
			shareList.setAdapter(adapter);

			shareList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					if (mSharePopupWindow != null) {
						mSharePopupWindow.dismiss();
					}
					if (shareItemClickListener != null) {
						shareItemClickListener.onItemClick();
					}

					Intent shareIntent = new Intent(Intent.ACTION_SEND);
					AppInfo appInfo = (AppInfo) adapter.getItem(position);
					shareIntent.setComponent(new ComponentName(appInfo
							.getAppPkgName(), appInfo.getAppLauncherClassName()));
					shareIntent.setType("text/plain");
					// shareIntent.setType("*/*");
					// 这里就是组织内容了，
					shareIntent.putExtra(Intent.EXTRA_TEXT, content);
					shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					activity.startActivity(shareIntent);
				}
			});

			mSharePopupWindow = new PopupWindow(view,
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT, true);
		}
		// 使其聚焦
		mSharePopupWindow.setFocusable(true);
		// 设置允许在外点击消失
		mSharePopupWindow.setOutsideTouchable(true);
		// 这个是为了点击�?返回Back”也能使其消失，并且并不会影响你的背�?
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		mSharePopupWindow.setBackgroundDrawable(dw);
		// xoff,yoff基于anchor的左下角进行偏移。正数表示下方右边，负数表示（上方左边）
		// showAsDropDown(parent, xPos, yPos);
		// 添加动画
		mSharePopupWindow
				.setAnimationStyle(R.style.popup_bottomshow_anim_style);
		mSharePopupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
	}

	// 分享自定义事件监听器
	public interface OnShareItemClickListener {
		public void onItemClick();
	}
}
