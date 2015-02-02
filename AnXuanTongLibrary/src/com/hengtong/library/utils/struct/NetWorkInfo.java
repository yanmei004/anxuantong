package com.hengtong.library.utils.struct;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkInfo 
{
	private int _type;
	private String _extraInfo;
	public int get_type() {
		return _type;
	}
	public void set_type(int _type) {
		this._type = _type;
	}
	public String get_extraInfo() {
		return _extraInfo;
	}
	public void set_extraInfo(String _extraInfo) {
		this._extraInfo = _extraInfo;
	}
	/**
	 * 检测是否已经连接网络。
	 * 
	 * @param context
	 * @return 当且仅当连上网络时返回true,否则返回false。
	 */
	public static boolean isConnectingToInternet(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager == null) {
			return false;
		}
		NetworkInfo info = connectivityManager.getActiveNetworkInfo();
		return (info != null) && info.isAvailable();
	}
}
