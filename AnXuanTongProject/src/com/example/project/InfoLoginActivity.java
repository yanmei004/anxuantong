package com.example.project;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import com.anxuantong.ym.R;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.example.pojo.CoalType;
import com.example.util.Contst;
import com.example.util.MyProgressDialog;
import com.example.util.StringUtils;
import com.example.util.http.IHttpRequestDriver;
import com.example.util.http.imp.HttpRequestDriverImp;
import com.hengtong.library.async.AsyncHttpResponseHandler;
import com.hengtong.library.enty.HTResponseObject;
import com.hengtong.library.utils.DesTool;
import com.hengtong.library.utils.DesUtil;
import com.hengtong.library.utils.ToastUtil;

/**
 * 
 * @author yanmei 登陆页面
 */
public class InfoLoginActivity extends Activity {
	// 自登陆
	private CheckBox rem_pw, auto_login;
	private String userNameValue, passwordValue;
	private SharedPreferences sp;
	private TextView titleTv;
	private EditText usernameEt, phoneEt, passwordEt;
	private String strPhone, strPassword;
	private Button registerBtn, unRegisterBtn;
	private List<CoalType> coalTypeList = new ArrayList<CoalType>();
	private String versionStr;
	private MyProgressDialog dialogPro;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_info_login_activity);
		// 获得实例对象
		sp = this.getSharedPreferences("userInfo", Context.MODE_WORLD_READABLE);
		init();

	}

	private void init() {
		versionStr = Contst.version;
		rem_pw = (CheckBox) findViewById(R.id.cb_mima);
		auto_login = (CheckBox) findViewById(R.id.cb_auto);
		titleTv = (TextView) findViewById(R.id.centerTitle);
		titleTv.setVisibility(View.VISIBLE);
		titleTv.setText("用户登陆");
		dialogPro = new MyProgressDialog(this);
		registerBtn = (Button) findViewById(R.id.register_btn);
		phoneEt = (EditText) findViewById(R.id.et_my_phone);
		unRegisterBtn = (Button) findViewById(R.id.unregister_);
		passwordEt = (EditText) findViewById(R.id.et_my_password);

		// 判断记住密码多选框的状态
		if (sp.getBoolean("ISCHECK", false)) {
			rem_pw.setChecked(true);
			phoneEt.setText(sp.getString("USER_NAME", ""));
			passwordEt.setText(sp.getString("PASSWORD", ""));
			if (sp.getBoolean("AUTO_ISCHECK", false)) {
				auto_login.setChecked(true);
				LoginUser(phoneEt.getText().toString().trim(), passwordEt
						.getText().toString().trim(), versionStr);
				dialogPro.showProgessDialog("", "", true);
			}
		}
		// 监听记住密码多选框按钮事件
		rem_pw.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (rem_pw.isChecked()) {

					sp.edit().putBoolean("ISCHECK", true).commit();

				} else {

					sp.edit().putBoolean("ISCHECK", false).commit();

				}

			}
		});

		// 监听自动登录多选框事件
		auto_login.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (auto_login.isChecked()) {
					sp.edit().putBoolean("AUTO_ISCHECK", true).commit();

				} else {
					sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
				}
			}
		});
		registerBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (StringUtils.isNullOrEmpty(phoneEt.getText().toString().trim())) {
					ToastUtil.showShortToast(InfoLoginActivity.this, "请输入电话号码");
				} else if (StringUtils.isNullOrEmpty(passwordEt.getText().toString().trim())) {
					ToastUtil.showShortToast(InfoLoginActivity.this, "请输入密码");
				} else {
					if (sp.getBoolean("ISCHECK", false)) {
						if (passwordEt.getText().toString().trim().equals(sp.getString("PASSWORD", ""))) {
							LoginUser(phoneEt.getText().toString().trim(),passwordEt.getText().toString().trim(),versionStr);
							dialogPro.showProgessDialog("", "", true);
						} else {
							LoginUser(phoneEt.getText().toString().trim(),passwordEt.getText().toString().trim(), versionStr);
							dialogPro.showProgessDialog("", "", true);
						}
					} else {
							 LoginUser(phoneEt.getText().toString().trim(),passwordEt.getText().toString().trim(),versionStr);
							 dialogPro.showProgessDialog("", "", true);
					}

				}
			}
		});

		unRegisterBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(InfoLoginActivity.this,
						RegisterActivity.class);
				startActivity(intent);
			}
		});
	}

	private void parseJson(String result) {
		try {
			JSONArray newsJson = new JSONArray(result);
			if (newsJson != null) {
				for (int i = 0; i < newsJson.length(); i++) {// ����
					JSONObject json = (JSONObject) newsJson.opt(i);
					CoalType coalType = new CoalType(json);
					coalTypeList.add(coalType);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void LoginUser(String phoneNo, String password, String version) {
		final IHttpRequestDriver request = HttpRequestDriverImp
				.getSingleInstance(InfoLoginActivity.this);
		request.login(phoneNo, password, version,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						dialogPro.dismissProgessDialog();
						HTResponseObject htResponseObject = request
								.handlerParseJson(new String(responseBody)
										.toString());
						if (htResponseObject.ismSuccess()) {
							if (htResponseObject.getmResult() == null)
								return;
							String result = htResponseObject.getmResult()
									.toString();
							try {
								JSONObject jsonObject = new JSONObject(result);
								userNameValue = jsonObject.optString("PhoneNo");
								passwordValue = jsonObject
										.optString("Password");
							} catch (JSONException e) {
								e.printStackTrace();
							}
							// 解析记住密码
							if (rem_pw.isChecked()) {
								// 记住用户名、密码、
								Editor editor = sp.edit();
								editor.putString("USER_NAME", userNameValue);
								editor.putString("PASSWORD", passwordValue);
								editor.commit();
							}
							Intent intent = new Intent(InfoLoginActivity.this,
									MyMenuActivity.class);
							startActivity(intent);
							Toast.makeText(InfoLoginActivity.this, "登陆成功",
									Toast.LENGTH_SHORT).show();
							InfoLoginActivity.this.finish();
						} else {
							Toast.makeText(InfoLoginActivity.this,
									"" + htResponseObject.getmError(),
									Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onStart() {
						super.onStart();
						dialogPro.showProgessDialog("", "", true);
					}

					@Override
					public void onFinish() {
						super.onFinish();
						dialogPro.dismiss();
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						dialogPro.dismiss();
					}

				});
	}

	private byte[] desCrypto(byte[] datasource, String password) {
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(password.getBytes());
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			// 现在，获取数据并加密
			// 正式执行加密操作
			return cipher.doFinal(datasource);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}
}
