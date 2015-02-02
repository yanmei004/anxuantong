package com.example.update;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.example.pojo.Version;
import com.hengtong.library.manager.UpdateManager;
import com.hengtong.library.utils.LogControl;

public class ClassUpdate {
	private final static String TAG = "Class_Update";
	private Context mContext = null;
	private UpdateManager updateManager;
	private boolean isAutoUpdate;

	public ClassUpdate(Context mContext) {
		this.mContext = mContext;
	}

	/**
	 * 手动�?��是否有新版本更新
	 * @param loadDialog
	 */
	public void manualUpdate(String aTaskFlag) {
		this.isAutoUpdate = false;
		this._checkUpdate(aTaskFlag);

	}

	/**
	 * 自动�?��是否有新版本更新
	 */
	public void autoUpdate(String aTaskFlag) {
		this.isAutoUpdate = true;
		this._checkUpdate(aTaskFlag);
	}

	public void initUpdate(Version version) {
		if (null == this.updateManager)
			this.updateManager = new UpdateManager(this.mContext);
		int mCurrentVersionCode = getAppInfo().versionCode;
		String mCurrentVersionName = getAppInfo().versionName;
		LogControl.i(TAG, "====mCurrentVersionCode===" + mCurrentVersionCode + "=====VERSION_ITEM====" + version);
		if (version != null) {
			boolean isNowVersion = mCurrentVersionCode >= version.mVersionCode ? true : false;
			// int updateType = version.mForce ? 2 : (isNowVersion ? 0 : 1);
			int updateType = isNowVersion ? 0 : version.mForce ? 2 : 1;
			this.updateManager.setVersionInfo(updateType, mCurrentVersionName, version.mVersionName, version.mAppDownUrl, version.info, version.Size);
			this.updateManager.Run(this.isAutoUpdate);
		}
	}

	/**
	 * 版本管理
	 * @return PackageInfo
	 */
	public PackageInfo getAppInfo() {
		PackageManager pm = mContext.getPackageManager();
		PackageInfo info = null;
		try {
			info = pm.getPackageInfo(mContext.getPackageName(), 0);
			// this.appVersion = info.versionName; // 版本名，versionCode同理
		} catch (NameNotFoundException e) {
			info = new PackageInfo();
			info.versionCode = 1;
			info.versionName = "2.0.0";
		}
		return info;
	}

	/**
	 * 获取自动更新数据
	 */
	private void _checkUpdate(String aTaskFlag) {
		// 网络请求...
		// NetManager.doUpdateApp(aTaskFlag);
	}

	public void setAutoUpdate(boolean isAutoUpdate) {
		this.isAutoUpdate = isAutoUpdate;
	}
}
