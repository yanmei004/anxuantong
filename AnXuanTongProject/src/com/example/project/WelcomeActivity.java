/**    
 * �ļ���WelcomeActivity.java    
 *    
 * �汾��Ϣ��    
 * ���ڣ�2014-11-11    
 * Copyright ���� Corporation 2014     
 * ��Ȩ����    
 *    
 */
package com.example.project;

import org.apache.http.Header;
import org.json.JSONObject;

import com.example.update.UpdateManager;
import com.example.util.Contst;
import com.example.util.http.IHttpRequestDriver;
import com.example.util.http.imp.HttpRequestDriverImp;
import com.hengtong.library.async.AsyncHttpResponseHandler;
import com.hengtong.library.enty.HTResponseObject;
import com.hengtong.library.enty.Update;
import com.hengtong.library.utils.ToastUtil;
import com.hengtong.library.utils.struct.NetWorkInfo;

import android.app.Activity;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.Toast;
import com.anxuantong.ym.R;
/**
 * @version 欢迎页面 请先连接网络
 */
public class WelcomeActivity extends Activity {
	private Boolean flag;
//	private String versionStr;
	private int versionStr;//版本编号
    private Update update;
    private String versionName;//版本号
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome_activity);
		flag = NetWorkInfo.isConnectingToInternet(WelcomeActivity.this);
		if (!flag) {
			ToastUtil.showShortToast(WelcomeActivity.this, "请先连接网络");
			finish();
		}else {
			//获取版本信息
		getVirsion();
		ImageView logoView = (ImageView) findViewById(R.id.iv_logo);
		AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
		animation.setDuration(1500);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				startActivity(new Intent(WelcomeActivity.this,
						InfoLoginActivity.class));
				finish();
			}
		});
		logoView.startAnimation(animation);
	}
	}
	
	private void getVirsion() {
		final IHttpRequestDriver request = HttpRequestDriverImp
				.getSingleInstance(WelcomeActivity.this);
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
				if (htResponseObject.ismSuccess()) {// �ɹ�
					if (htResponseObject.getmResult() == null)
						return;
					String result = htResponseObject.getmResult().toString();
					update=new Update(result);
					versionStr=update.getVersionCode();
					versionName=update.getVersionName();
					Contst.version=versionStr+"";
//					Contst.versionName=versionName;
					// versionStr
				} else {
					Toast.makeText(WelcomeActivity.this,
							"" + htResponseObject.getmError(),
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
		UpdateManager.getUpdateManager().checkAppUpdate(WelcomeActivity.this, true);
	}
}
