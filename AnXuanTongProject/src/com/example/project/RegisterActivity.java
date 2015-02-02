/**    
 * �ļ���RegisterActivity.java    
 *    
 * �汾��Ϣ��    
 * ���ڣ�2014-11-10    
 * Copyright ���� Corporation 2014     
 * ��Ȩ����    
 *    
 */
package com.example.project;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.pojo.CoalType;
import com.example.pojo.GridViewBean;
import com.example.pojo.adapter.MyAdapter;
import com.example.util.MyProgressDialog;
import com.example.util.StringUtils;
import com.example.util.http.IHttpRequestDriver;
import com.example.util.http.imp.HttpRequestDriverImp;
import com.hengtong.library.async.AsyncHttpResponseHandler;
import com.hengtong.library.enty.HTResponseObject;
import com.hengtong.library.utils.ToastUtil;
import com.hengtong.library.utils.ValidUtil;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import com.anxuantong.ym.R;
/**
 * 用户注册
 */
public class RegisterActivity extends Activity {
	private TextView titleTv, backTv;
	private String userCategoryID;
	private EditText usernameEt, phoneEt, passwordEt, companyEt;
	private Spinner typeSpinner;
	private String strUsername, strPhone, strPassword, strCompany, strType;
	private Button registerBtn;
	private List<CoalType> coalTypeList = new ArrayList<CoalType>();
	private MyProgressDialog dialogPro;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_info_activity);
		dialogPro = new MyProgressDialog(this);
		backTv = (TextView) findViewById(R.id.leftImage);
		backTv.setVisibility(View.VISIBLE);
		backTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		titleTv = (TextView) findViewById(R.id.centerTitle);
		titleTv.setText("用户注册");
		usernameEt = (EditText) findViewById(R.id.et_my_name);
		phoneEt = (EditText) findViewById(R.id.et_my_phone);
		passwordEt = (EditText) findViewById(R.id.et_my_password);
		companyEt = (EditText) findViewById(R.id.et_my_danwei);
		typeSpinner = (Spinner) findViewById(R.id.sp_my_type);
		typeSpinner.setPrompt("请选择");
		registerBtn = (Button) findViewById(R.id.register_btn);
		initData();
		registerBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (StringUtils.isNullOrEmpty(usernameEt.getText().toString().trim())) {
					ToastUtil.showShortToast(RegisterActivity.this, "请输入用户名");
				} 
				else if (StringUtils.isNullOrEmpty(phoneEt.getText().toString().trim())) {
					ToastUtil.showShortToast(RegisterActivity.this, "请输入电话号码");
					// 验证是否是11位手机号
				} 
				else if (!ValidUtil.isMoble(phoneEt.getText().toString().trim())) {
					ToastUtil.showShortToast(RegisterActivity.this,
							"请输入正确的手机号");
				}
				else if (StringUtils.isNullOrEmpty(passwordEt.getText().toString().trim())) {
					ToastUtil.showShortToast(RegisterActivity.this, "请输入密码");
				}
				//判断为有效密码
				else if (passwordEt.getText().toString().trim().length()<=5) {
					ToastUtil.showShortToast(RegisterActivity.this, "密码长度不足6位,请重新输入");
				}
				
				else if (StringUtils.isNullOrEmpty(companyEt.getText().toString().trim())) {
					ToastUtil.showShortToast(RegisterActivity.this, "请输入所属单位");
				} 
				else if (StringUtils.isNullOrEmpty(userCategoryID)) {
					ToastUtil.showShortToast(RegisterActivity.this, "请先选择所属地区");
				} 
				else {
					registerUser(userCategoryID, usernameEt.getText()
							.toString().trim(), phoneEt.getText().toString()
							.trim(), passwordEt.getText().toString().trim(),
							companyEt.getText().toString().trim());
					dialogPro.showProgessDialog("", "", true);
				}
			}
		});

	}

	private void initData() {
		GetUserCategory();
	}

	/**
	 */
	private void GetUserCategory() {
		final IHttpRequestDriver request = HttpRequestDriverImp
				.getSingleInstance(RegisterActivity.this);
		request.doGetUserCategory(new AsyncHttpResponseHandler() {
			@Override
			public void onStart() {
				super.onStart();
				dialogPro.showProgessDialog("", "", true);
			}

			@Override
			public void onFinish() {
				super.onFinish();
				dialogPro.dismissProgessDialog();
			}

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				dialogPro.dismissProgessDialog();
				HTResponseObject htResponseObject = request
						.handlerParseJson(new String(responseBody).toString());
				if (htResponseObject.ismSuccess()) {// �ɹ�
					dialogPro.dismissProgessDialog();
					if (htResponseObject.getmResult() == null)
						return;
					String result = htResponseObject.getmResult().toString();
					parseJson(result);
					MyAdapter adapter = new MyAdapter(RegisterActivity.this,
							coalTypeList);
					typeSpinner.setAdapter(adapter);
					typeSpinner
							.setOnItemSelectedListener(new OnItemSelectedListener() {

								@Override
								public void onItemSelected(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									userCategoryID = coalTypeList.get(arg2)
											.getUserCategoryID();
								}

								@Override
								public void onNothingSelected(
										AdapterView<?> arg0) {

								}
							});
				} else {
					dialogPro.dismissProgessDialog();
					Toast.makeText(RegisterActivity.this,
							"" + htResponseObject.getmError(),
							Toast.LENGTH_SHORT).show();
					dialogPro.dismissProgessDialog();
				}

			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				dialogPro.dismissProgessDialog();
			}
		});
	}

	private class MySpinnerAdapter extends BaseAdapter {
		private Context context;
		private List<CoalType> list;

		public MySpinnerAdapter() {
			super();
		}

		public MySpinnerAdapter(Context context, List<CoalType> list) {
			super();
			this.context = context;
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			return list.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			View view = LayoutInflater.from(context).inflate(
					R.layout.spinnerlayout, null);
			if (view != null) {
				TextView titleTv_adapter = (TextView) view
						.findViewById(R.id.sp_tv);
				titleTv_adapter.setText(list.get(position)
						.getUserCategoryName());
			}
			return view;
		}
	}

	private void registerUser(final String userCategoryID,
			final String userName, final String phoneNo, final String password,
			final String company) {
		final IHttpRequestDriver request = HttpRequestDriverImp
				.getSingleInstance(this);
		request.doRegister(userCategoryID, userName, phoneNo, password,
				company, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						dialogPro.dismissProgessDialog();
						HTResponseObject htResponseObject = request
								.handlerParseJson(new String(responseBody)
										.toString());
						if (htResponseObject.ismSuccess()) {// �ɹ�
							if (htResponseObject.getmResult() == null)
								return;
							String result = htResponseObject.getmResult()
									.toString();
							Toast.makeText(RegisterActivity.this, "注册成功",
									Toast.LENGTH_SHORT).show();
							dialogPro.dismissProgessDialog();
							finish();
							JSONObject jsonObject;
							try {
								jsonObject = new JSONObject(result);
								String resu = jsonObject.getString("Success");
								// if(resu.equals("true")){
								// Toast.makeText(RegisterActivity.this, "注册成功",
								// Toast.LENGTH_SHORT).show();
								// finish();
								// }
							} catch (JSONException e) {
								e.printStackTrace();
							}
						} else {
							Toast.makeText(RegisterActivity.this,
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
}
