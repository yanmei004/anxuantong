package com.example.util.shared;

import android.graphics.drawable.Drawable;

/**
 * 系统中应用信息的实体类别
 * @author lin
 *
 */
public class AppInfo {

	private String appPkgName;
	
	private String appLauncherClassName;
	
	private String appName;
	
	private Drawable appIcon;

	public String getAppPkgName() {
		return appPkgName;
	}

	public void setAppPkgName(String appPkgName) {
		this.appPkgName = appPkgName;
	}

	public String getAppLauncherClassName() {
		return appLauncherClassName;
	}

	public void setAppLauncherClassName(String appLauncherClassName) {
		this.appLauncherClassName = appLauncherClassName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Drawable getAppIcon() {
		return appIcon;
	}

	public void setAppIcon(Drawable appIcon) {
		this.appIcon = appIcon;
	}

}
