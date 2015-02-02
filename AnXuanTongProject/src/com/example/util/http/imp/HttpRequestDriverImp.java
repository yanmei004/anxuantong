/**    
 * 文件名：HttpRequestDriverImp.java    
 *    
 * 版本信息：    
 * 日期：2014-10-3    
 * Copyright 足下 Corporation 2014     
 * 版权所有    
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
 * 项目名称：AnXuanTongProject 类名称：HttpRequestDriverImp 类描述：imp是抽象类的实现方式 创建人：yanmei
 * 创建时间：2014-10-3 下午4:49:59 修改人：yanmei 修改时间：2014-10-3 下午4:49:59 修改备注：
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
	 * method desc：处理错误信息
	 * 
	 * @return
	 */
	private String handlerError(String aError) {
		if (!StringUtils.isNullOrEmpty(aError)) {
			int start = aError.indexOf(":");
			aError = aError.substring(start + 1);
		} else {
			aError = "操作失败";
		}
		return aError;
	}

	/**
	 * method desc：处理错误信息编码
	 * 
	 * @return
	 */
	private String handlerErrorCode(String aError) {
		if (!StringUtils.isNullOrEmpty(aError)) {
			if (aError.contains("请求对象无法序列化")) {
				return aError;
			}
			int start = aError.indexOf("#");
			int end = aError.indexOf(":");
			aError = aError.substring(start + 1, end);
		}
		return aError;
	}

	// *******************************************具体接口方法********************************
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
				Toast.makeText(mContext, "服务器返回数据为空，请检查服务器！", Toast.LENGTH_LONG)
						.show();
			}
		} catch (JSONException e) {
			Toast.makeText(mContext, "json数据解析异常错误！", Toast.LENGTH_LONG).show();
		}
		return mResponseObj;
	}

	// 得到新闻分类列表
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

	// 得到新闻信息
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
	 * (non-Javadoc) 获取单条新闻信息
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
//注册
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

	// 检查版本更新
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
				Log.e("yanmei","版本更新--"+"exeption:" + throwable.getMessage());
			}
		}
		// 登陆
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
	 * (non-Javadoc) 获取用户类型
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
	 * 获取系统信息
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
	 * 获取版本信息
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
