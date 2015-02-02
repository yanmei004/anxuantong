/**    
 * �ļ�����HttpRequestDriverImp.java    
 *    
 * �汾��Ϣ��    
 * ���ڣ�2014-10-3    
 * Copyright ���� Corporation 2014     
 * ��Ȩ����    
 *    
 */
package com.example.util.http.imp;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.project.applican.MyApplication;
import com.example.util.Contst;
import com.example.util.MyVersion;
import com.example.util.http.IHttpRequestDriver;
import com.hengtong.library.async.AsyncHttpResponseHandler;
import com.hengtong.library.enty.HTRequestObject;
import com.hengtong.library.enty.HTResponseObject;
import com.hengtong.library.enty.HTVersionItem;
import com.hengtong.library.manager.HTAsyncNetManager;
import com.hengtong.library.utils.StringUtils;

/**
 * 
 * ��Ŀ���ƣ�AnXuanTongProject �����ƣ�HttpRequestDriverImp ��������imp�ǳ������ʵ�ַ�ʽ �����ˣ�yanmei
 * ����ʱ�䣺2014-10-3 ����4:49:59 �޸��ˣ�yanmei �޸�ʱ�䣺2014-10-3 ����4:49:59 �޸ı�ע��
 * 
 * @version
 */
public class HttpRequestDriverImp implements IHttpRequestDriver {
	private static IHttpRequestDriver httpRequestDriver;
	private static Context mContext;
	private static MyApplication mApp = MyApplication.getSelf();

	private HttpRequestDriverImp() {

	}

	/**
	 * method desc�����������Ϣ
	 * 
	 * @return
	 */
	private String handlerError(String aError) {
		if (!StringUtils.isNullOrEmpty(aError)) {
			int start = aError.indexOf(":");
			aError = aError.substring(start + 1);
		} else {
			aError = "����ʧ��";
		}
		return aError;
	}

	/**
	 * method desc�����������Ϣ����
	 * 
	 * @return
	 */
	private String handlerErrorCode(String aError) {
		if (!StringUtils.isNullOrEmpty(aError)) {
			if (aError.contains("��������޷����л�")) {
				return aError;
			}
			int start = aError.indexOf("#");
			int end = aError.indexOf(":");
			aError = aError.substring(start + 1, end);
		}
		return aError;
	}

	// *******************************************����ӿڷ���********************************
	private void excutePost(HTRequestObject aRequestObject,
			AsyncHttpResponseHandler handler) {
		HTAsyncNetManager.post(Contst.API_URL, aRequestObject, handler);
	}

	public static IHttpRequestDriver getSingleInstance(Context aContext) {
		if (httpRequestDriver == null)
			httpRequestDriver = new HttpRequestDriverImp();
		mContext = aContext;
		if (mApp == null)
			mApp = MyApplication.getSelf();
		return httpRequestDriver;
	}

	@Override
	public HTResponseObject handlerParseJson(String json) {
		HTResponseObject mResponseObj = new HTResponseObject();
		try {
			if (!StringUtils.isNullOrEmpty(json)) {
				JSONObject jsonObject = new JSONObject(json);
				mResponseObj.setmError(handlerError(jsonObject.optString(
						"Error", "")));
				mResponseObj.setmErrorCode(handlerErrorCode(jsonObject
						.optString("Error", "")));
				mResponseObj.setmSuccess(jsonObject.optBoolean("Success"));
				mResponseObj.setmResult(jsonObject.optString("Result", ""));
				if ("0002".equals(mResponseObj.getmErrorCode())) {
					Log.e("yanmei", "===0002" + mResponseObj.getmErrorCode());
				}
			} else {
				Toast.makeText(mContext, "��������������Ϊ�գ������������", Toast.LENGTH_LONG)
						.show();
			}
		} catch (JSONException e) {
			Toast.makeText(mContext, "json���ݽ����쳣����", Toast.LENGTH_LONG).show();
		}
		return mResponseObj;
	}

	// �õ����ŷ����б�
	@Override
	public void doGetInfosCategory(int pageIndex, int pageSize,
			AsyncHttpResponseHandler handler) {
		HTRequestObject aRequestObject = new HTRequestObject();
		aRequestObject.setmForamt("json");
		aRequestObject.setmMethod("GetInfosCategory");
		aRequestObject.setmToken("");
		aRequestObject.setmTypeName("Infos");
		aRequestObject.setmParms("pageIndex", pageIndex);
		aRequestObject.setmParms("pageSize", pageSize);
		excutePost(aRequestObject, handler);

	}

	// �õ�������Ϣ
	@Override
	public void doGetInfos(String categoryID,int isTop, String infosTitle, int pageIndex,
			int pageSize, AsyncHttpResponseHandler handler) {
		HTRequestObject aRequestObject = new HTRequestObject();
		aRequestObject.setmForamt("json");
		aRequestObject.setmMethod("GetInfos");
		aRequestObject.setmToken("");
		aRequestObject.setmTypeName("Infos");
		aRequestObject.setmParms("categoryID", categoryID);
		aRequestObject.setmParms("isTop", isTop);
		aRequestObject.setmParms("infosTitle", infosTitle);
		aRequestObject.setmParms("pageIndex", pageIndex);
		aRequestObject.setmParms("pageSize", pageSize);
		excutePost(aRequestObject, handler);
	}

	/*
	 * (non-Javadoc) ��ȡ����������Ϣ
	 */
	@Override
	public void doGetInfosByID(String infosID, AsyncHttpResponseHandler handler) {
		HTRequestObject aRequestObject = new HTRequestObject();
		aRequestObject.setmForamt("json");
		aRequestObject.setmMethod("GetInfosByID");
		aRequestObject.setmToken("");
		aRequestObject.setmTypeName("Infos");
		aRequestObject.setmParms("infosID", infosID);
		excutePost(aRequestObject, handler);
	}
//ע��
	@Override
	public void doRegister(String userCategoryID, String userName,
			String phoneNo, String password,String company, AsyncHttpResponseHandler handler) {
		HTRequestObject aRequestObject = new HTRequestObject();
		aRequestObject.setmForamt("json");
		aRequestObject.setmMethod("Register");
		aRequestObject.setmToken("");
		aRequestObject.setmTypeName("User");
		aRequestObject.setmParms("userCategoryID", userCategoryID);
		aRequestObject.setmParms("userName", userName);
		aRequestObject.setmParms("phoneNo", phoneNo);
		aRequestObject.setmParms("password", password);
		aRequestObject.setmParms("company", company);
		excutePost(aRequestObject, handler);
	}

	// ���汾����
		@Override
		public void checkUpdate(final HTHttpResponseHandler handler) {
			HTRequestObject aRequestObject = new HTRequestObject();
			aRequestObject.setmForamt("json");
			aRequestObject.setmMethod("GetLastVersionInfo");
			aRequestObject.setmToken("");
			aRequestObject.setmTypeName("SystemInfo");
			HTVersionItem version = MyVersion.getVersion(mContext);
//			if (version != null) {
//				aRequestObject.setmParms("lastVersion", version.mVersionCode);
//			}
//			aRequestObject.setmParms("platform", "23");
//			aRequestObject.setmParms("userId", mApp.getUsername());
			HTAsyncNetManager.post(Contst.API_URL, aRequestObject, new AsyncHttpResponseHandler() {
				@Override
				public void onFinish() {
					super.onFinish();
					handler.onFinish();
				}

				@Override
				public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
					handler.onSuccess(statusCode, headers, handlerParseJson(new String(responseBody)));
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
					printResultLog(error);
					handler.onFailure(statusCode, headers, responseBody, error);

				}
			});
		}
		private void printResultLog(Throwable throwable) {
			if (throwable != null) {
				Log.e("yanmei","�汾����--"+"exeption:" + throwable.getMessage());
			}
		}
		// ��½
		@Override
		public void login(String phoneNo, String pwd, String versionStr,
				AsyncHttpResponseHandler handler) {
			HTRequestObject aRequestObject = new HTRequestObject();
			aRequestObject.setmForamt("json");
			aRequestObject.setmMethod("Login");
			aRequestObject.setmToken("");
			aRequestObject.setmTypeName("User");
			aRequestObject.setmParms("phoneNo", phoneNo);
			aRequestObject.setmParms("password", pwd);
			aRequestObject.setmParms("version", versionStr);
			excutePost(aRequestObject, handler);
		}
		
	/*
	 * (non-Javadoc) ��ȡ�û�����
	 */
	@Override
	public void doGetUserCategory(AsyncHttpResponseHandler handler) {
		HTRequestObject aRequestObject = new HTRequestObject();
		aRequestObject.setmForamt("json");
		aRequestObject.setmMethod("GetUserCategory");
		aRequestObject.setmToken("");
		aRequestObject.setmTypeName("User");
		aRequestObject.setmParms("", "");
		excutePost(aRequestObject, handler);
	}

	/* 
	 * ��ȡϵͳ��Ϣ
	 */
	@Override
	public void doGetSystemInfo(String title, AsyncHttpResponseHandler handler) {
		HTRequestObject aRequestObject = new HTRequestObject();
		aRequestObject.setmForamt("json");
		aRequestObject.setmMethod("GetSystemInfo");
		aRequestObject.setmToken("");
		aRequestObject.setmTypeName("SystemInfo");
		aRequestObject.setmParms("title",title);
		excutePost(aRequestObject, handler);
	}

	/* 
	 * ��ȡ�汾��Ϣ
	 */
	@Override
	public void GetLastVersionInfo(AsyncHttpResponseHandler handler) {
		HTRequestObject aRequestObject = new HTRequestObject();
		aRequestObject.setmForamt("json");
		aRequestObject.setmMethod("GetLastVersionInfo");
		aRequestObject.setmToken("");
		aRequestObject.setmTypeName("SystemInfo");
		excutePost(aRequestObject, handler);
	}

}
