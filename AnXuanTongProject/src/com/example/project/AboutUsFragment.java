package com.example.project;

import java.io.File;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.anxuantong.ym.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.menu.ResideMenu;
import com.example.pojo.GridViewBean;
import com.example.update.UpdateManager;
import com.example.util.MyProgressDialog;
import com.example.util.MySharedUtil;
import com.example.util.http.IHttpRequestDriver;
import com.example.util.http.imp.HttpRequestDriverImp;
import com.hengtong.library.async.AsyncHttpResponseHandler;
import com.hengtong.library.enty.HTResponseObject;
import com.hengtong.library.enty.Update;

/**
 * User: yanmei 
 Mail: 1095553746@qq.com  
 关于我们
 */
public class AboutUsFragment extends Fragment {
	private View parentView;
	private ResideMenu resideMenu;
	private MyProgressDialog progressDialog;
	private WebView contentTv;
	public AboutUsFragment() {
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
		parentView = inflater.inflate(R.layout.act_aboutus_activity, container,
				false);
		return parentView;

	}

	private void setUpViewsNew() {
		MyMenuActivity parentActivity = (MyMenuActivity) getActivity();
		parentView = getView();
		resideMenu = parentActivity.getResideMenu();
		contentTv=(WebView) parentView.findViewById(R.id.aboutus_tv);
		progressDialog = new MyProgressDialog(getActivity());
		GetSystemInfo();
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
	 * GetSystemInfo
	 */
	private void getSystemInfo() {
		final IHttpRequestDriver request = HttpRequestDriverImp.getSingleInstance(getActivity());
		request.doGetSystemInfo("我们",new AsyncHttpResponseHandler() {
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
				if (htResponseObject.ismSuccess()) {// �ɹ�
					if (htResponseObject.getmResult() == null)
						return;
					String result = htResponseObject.getmResult().toString();
					JSONObject jsonObjectVer;
					try {
						jsonObjectVer = new JSONObject(result);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					Toast.makeText(getActivity(),
							"错误信息" + htResponseObject.getmError(),
							Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
			}
		});
	}
	
	/**
	 * GetInfosCategory(关于我们)
	 */
	private void GetSystemInfo() {
		final IHttpRequestDriver request = HttpRequestDriverImp.getSingleInstance(getActivity());
		request.doGetSystemInfo("我们", new AsyncHttpResponseHandler() {
			@Override
			public void onStart() {
				super.onStart();
				progressDialog.showProgessDialog("", "", true);
			}

			@Override
			public void onFinish() {
				super.onFinish();
				progressDialog.dismiss();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				progressDialog.dismissProgessDialog();
				HTResponseObject htResponseObject = request
						.handlerParseJson(new String(responseBody).toString());
				if (htResponseObject.ismSuccess()) {// �ɹ�
					if (htResponseObject.getmResult() == null)
						return;
					String result = htResponseObject.getmResult().toString();
					JSONObject jsonObject;
					try {
						jsonObject = new JSONObject(result);
						String SystemInfoContent = jsonObject.getString("SystemInfoContent");
//						contentTv.setText(SystemInfoContent);
						contentTv.loadDataWithBaseURL(null,SystemInfoContent, "text/html", "UTF-8", null);
					} catch (JSONException e) {
						e.printStackTrace();
					}

				} else {
					Toast.makeText(getActivity(),
							"" + htResponseObject.getmError(),
							Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				progressDialog.dismiss();
			}
		});
	}
}
