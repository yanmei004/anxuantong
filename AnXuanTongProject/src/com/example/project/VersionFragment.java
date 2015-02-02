package com.example.project;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.menu.ResideMenu;
import com.example.pojo.GridViewBean;
import com.example.update.UpdateManager;
import com.example.util.Contst;
import com.example.util.MyProgressDialog;
import com.example.util.http.IHttpRequestDriver;
import com.example.util.http.imp.HttpRequestDriverImp;
import com.hengtong.library.async.AsyncHttpResponseHandler;
import com.hengtong.library.enty.HTResponseObject;
import com.hengtong.library.enty.Update;
import com.anxuantong.ym.R;
/**
 * User: yanmei update 20140917 Mail: 1095553746@qq.com
 */
public class VersionFragment extends Fragment {
	private View parentView;
	private ResideMenu resideMenu;
	private TextView tv;
	private MyProgressDialog progressDialog;
	private TextView tileTv, versionTv;
	private Button checkBtn;//
	private String versionStr;
    private Update update;
    private int versionCode;
    private String versionName;
	public VersionFragment() {
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
		parentView = inflater.inflate(R.layout.act_version_activity, container,
				false);
		progressDialog = new MyProgressDialog(getActivity());
		return parentView;
	}

	private void initData() {
//		GetInfosCategory();
	}

	/**
	 * 
	 * getVirsion(��ȡ�汾��Ϣ)
	 */
	private void getVirsion() {
		final IHttpRequestDriver request = HttpRequestDriverImp
				.getSingleInstance(getActivity());
		request.GetLastVersionInfo(new AsyncHttpResponseHandler() {
			@Override
			public void onStart() {
				super.onStart();
				progressDialog.showProgessDialog("", "", true);
			}

			@Override
			public void onFinish() {
				super.onFinish();
				progressDialog.dismissProgessDialog();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,byte[] responseBody) {
				progressDialog.dismissProgessDialog();
				HTResponseObject htResponseObject = request.handlerParseJson(new String(responseBody).toString());
				if (htResponseObject.ismSuccess()) {// �ɹ�
					
					if (htResponseObject.getmResult() == null)
						return;
					String result = htResponseObject.getmResult().toString();
					update=new Update(result);
					// versionStr
					JSONObject jsonObjectVer;
					try {
						jsonObjectVer = new JSONObject(result);
//						versionStr = jsonObjectVer.getString("Version");
//						versionTv.setText("android��  ��Ȼ�汾:"+versionStr);
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

	private void checkUpdate() {
		UpdateManager.getUpdateManager().checkAppUpdate(getActivity(), true);
	}
	private void parseJson(String result) {
		try {
			JSONArray newsJson = new JSONArray(result);
			if (newsJson != null) {
				for (int i = 0; i < newsJson.length(); i++) {// ����
					JSONObject json = (JSONObject) newsJson.opt(i);
					GridViewBean bean = new GridViewBean(json);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void setUpViewsNew() {
		MyMenuActivity parentActivity = (MyMenuActivity) getActivity();
		parentView = getView();
		resideMenu = parentActivity.getResideMenu();
		tv = (TextView) parentView.findViewById(R.id.about_tv);
		tileTv = (TextView) parentView.findViewById(R.id.centerTitle);
		checkBtn = (Button) parentView.findViewById(R.id.btn_checkupdate);
		checkBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				checkUpdate();
			}
		});
		getCurrentVersion();
		versionTv = (TextView) parentView.findViewById(R.id.about_version);
		versionTv.setText("版本号："+versionName);
		tileTv.setText("关于我们");
		initData();
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
	 * 获取当前客户端版本信
	 */
	private void getCurrentVersion() {
		try {
			PackageInfo info = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
			// curVersionName = info.versionName;
			versionCode = info.versionCode;
			versionName = info.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace(System.err);
		}
	}
}
