package com.example.util.shared;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

/**
 * 
 * @author lin
 *
 */
public class SharedUtil {

	private Activity activity;
	
	
	public SharedUtil(Activity activity){
		
		this.activity = activity;
			
	}
	
	
	
	/**
	 * 查询手机内所有支持分享的应用列表
	 * @author lin
	 */
	public List<ResolveInfo> getShareApps() {	
		List<ResolveInfo> mApps = new ArrayList<ResolveInfo>();
		Intent intent = new Intent(Intent.ACTION_SEND, null);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setType("text/plain");
		PackageManager pManager = activity.getPackageManager();
		mApps = pManager.queryIntentActivities(intent, 
				PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
		return mApps;
	}
	
	
	/**
	 * 获取分享的应用列表的信息，并放入list�?	 * @author lin
	 * @return
	 */
	public List<AppInfo> getShareAppList() {
        
        List<AppInfo> shareAppInfos = new ArrayList<AppInfo>();  
        PackageManager packageManager = activity.getPackageManager();  
        List<ResolveInfo> resolveInfos = getShareApps();  
        if (null == resolveInfos) {  
            return null;  
        } else {  
            for (ResolveInfo resolveInfo : resolveInfos) {  
                AppInfo appInfo = new AppInfo();  
                appInfo.setAppPkgName(resolveInfo.activityInfo.packageName);   
                appInfo.setAppLauncherClassName(resolveInfo.activityInfo.name);  
                appInfo.setAppName(resolveInfo.loadLabel(packageManager).toString());  
                appInfo.setAppIcon(resolveInfo.loadIcon(packageManager));  
                shareAppInfos.add(appInfo);  
            }  
        }         
        return shareAppInfos;  
    }
}
