/**    
 * �ļ���MyVersion.java    
 *    
 * �汾��Ϣ��    
 * ���ڣ�2014-11-12    
 * Copyright ���� Corporation 2014     
 * ��Ȩ����    
 *    
 */
package com.example.util;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.hengtong.library.enty.HTVersionItem;

/**
 * 
 * ��Ŀ��ƣ�AnXuanTongProject ����ƣ�MyVersion �������� �����ˣ�yanmei ����ʱ�䣺2014-11-12
 * ����3:32:38 �޸��ˣ�yanmei �޸�ʱ�䣺2014-11-12 ����3:32:38 �޸ı�ע��
 * 
 * @version
 * 
 */
public class MyVersion {
	private Context context;

	public static HTVersionItem getVersion(Context context) {
		HTVersionItem version = null;
		try {
			// ��ȡpackagemanager��ʵ��
			PackageManager packageManager = context.getPackageManager();
			// getPackageName()���㵱ǰ��İ���0����ǻ�ȡ�汾��Ϣ
			PackageInfo packInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			version = new HTVersionItem();
			version.mVersionName = packInfo.versionName;
			version.mVersionCode = packInfo.versionCode;
		} catch (Exception e) {
			// LogControl.e("exception", "get packinfo faile! cause:" +
			// e.getMessage());
		}
		return version;
	}
}
