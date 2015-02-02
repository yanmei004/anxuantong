package com.example.project;

import java.io.File;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cmcc.gz.lib.controls.pulltorefresh.internal.Utils;

import com.example.menu.ResideMenu;
import com.example.util.MySharedUtil;
import com.example.util.http.IHttpRequestDriver;
import com.example.util.http.imp.HttpRequestDriverImp;
import com.hengtong.library.async.AsyncHttpResponseHandler;
import com.hengtong.library.enty.HTResponseObject;
import com.anxuantong.ym.R;
/**
 * User: yanmei update 20140917 Mail: 1095553746@qq.com 设置页面 ：清理和获取版本分享
 */
public class SettingFragment extends Fragment {
	private View parentView;
	private ResideMenu resideMenu;
	private RelativeLayout swapReLayout, shareReLayout;
	private String urlStr="";
	private Button mbtnlogout;
	private SharedPreferences sp;
	public SettingFragment() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		parentView = inflater.inflate(R.layout.act_setting_activity, container,
				false);
		return parentView;

	}

	private void setUpViewsNew() {
		MyMenuActivity parentActivity = (MyMenuActivity) getActivity();
		parentView = getView();
		resideMenu = parentActivity.getResideMenu();
		swapReLayout = (RelativeLayout) parentView
				.findViewById(R.id.setting_ly_qchc);
		shareReLayout = (RelativeLayout) parentView
				.findViewById(R.id.setting_ly_sign_in);
		sp = getActivity().getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
		mbtnlogout = (Button) parentView.findViewById(R.id.btn_logout);
		mbtnlogout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("温馨提示");
				builder.setMessage("确定要退出登录吗？");
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						sp.edit().putBoolean("ISCHECK", false).commit();//记住密码不选
						sp.edit().putBoolean("AUTO_ISCHECK", false).commit();//自动登录不选
						Editor editor = sp.edit();
						editor.putString("USER_NAME", "");
						editor.putString("PASSWORD","");
						editor.commit();
//						Utils.setBind(thisActivity, false, UserInfoUtils.getPhone(), UserInfoUtils.getCommunityCode());
//						UserInfoUtils.clear();
						Intent intent = new Intent(getActivity(), InfoLoginActivity.class);
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
						startActivity(intent);
//						doExitChat();
//						AppUtils.getInstance().exit();
					}
				});
				builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				builder.show();
			}
		});
		swapReLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new AlertDialog.Builder(getActivity()).setTitle("温馨提示").setMessage("确认清除缓存").setPositiveButton("是", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
						File file = getActivity().getCacheDir();
						if (file != null && file.exists() && file.isDirectory()) {
							for (File item : file.listFiles()) {
								item.delete();
							}
							file.delete();
						}
						getActivity().deleteDatabase("webview.db");
						getActivity().deleteDatabase("webviewCache.db");
						WebView webView = new WebView(getActivity());
						webView.clearCache(true);
						Toast.makeText(getActivity(), "清理完成",Toast.LENGTH_SHORT).show();
					}
				}).setNegativeButton("否", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				}).create().show();
			}
		});
		shareReLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				MySharedUtil sharedUtil = new MySharedUtil(
					getActivity());
				//http://ds.gzaqxj.com:81/home/AppDown
				sharedUtil.show(shareReLayout, "安宣通还不错，快来下载吧！！！下载地址：" + "http://ds.gzaqxj.com:81/home/AppDown");
			}
		});
	}

	private void setUpViews() {
		MyMenuActivity parentActivity = (MyMenuActivity) getActivity();
		resideMenu = parentActivity.getResideMenu();

		parentView.findViewById(R.id.btn_open_menu).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
					}
				});
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setUpViewsNew();
	}

	private void stopRequestData() {
		// mListView.
		// mListView.stopRefresh();
		// mListView.stopLoadMore();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	/**
	 * 
	 * getVirsion(获取版本信息)
	 */
	private void getVirsion() {
		final IHttpRequestDriver request = HttpRequestDriverImp
				.getSingleInstance(getActivity());
		request.GetLastVersionInfo(new AsyncHttpResponseHandler() {
			@Override
			public void onStart() {
				super.onStart();
			}

			@Override
			public void onFinish() {
				super.onFinish();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				HTResponseObject htResponseObject = request
						.handlerParseJson(new String(responseBody).toString());
				if (htResponseObject.ismSuccess()) {// 成功
					if (htResponseObject.getmResult() == null)
						return;
					String result = htResponseObject.getmResult().toString();
					JSONObject jsonObjectVer;
					try {
						jsonObjectVer = new JSONObject(result);
						urlStr = jsonObjectVer.getString("Url");
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					Toast.makeText(getActivity(),
							"获取版本信息" + htResponseObject.getmError(),
							Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
			}
		});

	}
}
