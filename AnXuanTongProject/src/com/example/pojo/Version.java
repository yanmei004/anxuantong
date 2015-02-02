package com.example.pojo;

import org.json.JSONException;
import org.json.JSONObject;

import com.hengtong.library.enty.HTVersionItem;

/**
 * 功能：版本实
 * @ahthor：yanmei
 * @date:2013-12-12
 * @version::V1.0
 */
public class Version extends HTVersionItem {
	private static final long serialVersionUID = 1L;

	public String info;// 版本描述
	public double Size;// 版本大小

	public Version(String jsons) {
		try {
			JSONObject jsonObject = new JSONObject(jsons);
			this.mAppDownUrl = jsonObject.optString("Url", "");
			this.mVersionName = jsonObject.optString("Version", "");
			this.mVersionCode = jsonObject.optInt("BuildNo", 0);
			this.info = jsonObject.optString("Info", "");
			this.Size = jsonObject.optDouble("Size", 0);
			int temp = jsonObject.optInt("Forced", 0);
			this.mForce = (temp == 1 ? true : false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
