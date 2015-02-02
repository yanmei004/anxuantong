/**    
 * �ļ���NewsList.java    
 *    
 * �汾��Ϣ��    
 * ���ڣ�2014-10-22    
 * Copyright ���� Corporation 2014     
 * ��Ȩ����    
 *    
 */
package com.example.pojo;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.hengtong.library.utils.StringUtils;

/**
 * 
 * ��Ŀ��ƣ�AnXuanTongProject ����ƣ�NewsList �������� ������� �����ˣ�yanmei ����ʱ�䣺2014-10-22
 * ����8:10:00 �޸��ˣ�yanmei �޸�ʱ�䣺2014-10-22 ����8:10:00 �޸ı�ע��
 * 
 * @version
 * 
 */
public class NewsList implements Serializable{
	private String CategoryName;//
	private boolean IsIsTop;//
	private String InfosTitle;//
	private int IsTop;//
	private String PicUrl;//
	private String ThumbnailUrl;//
	private String CreateDateTime;//
	private String Source;//
	private int Status;//
	private String InfosID;//
	private boolean IsStatus;//
	private String Introduction;
	private String CategoryID;//
	private String UpdateUserID;//
	private String UpdateDateTime;//
	private String StrUpdateDateTime;//
	public NewsList(String categoryName, boolean isIsTop, String infosTitle,
			int isTop, String picUrl, String thumbnailUrl,
			String createDateTime, String source, int status, String infosID,
			boolean isStatus, String introduction, String categoryID,
			String updateUserID, String updateDateTime,
			String strUpdateDateTime, String infosContent, String updateUserName) {
		super();
		CategoryName = categoryName;
		IsIsTop = isIsTop;
		InfosTitle = infosTitle;
		IsTop = isTop;
		PicUrl = picUrl;
		ThumbnailUrl = thumbnailUrl;
		CreateDateTime = createDateTime;
		Source = source;
		Status = status;
		InfosID = infosID;
		IsStatus = isStatus;
		Introduction = introduction;
		CategoryID = categoryID;
		UpdateUserID = updateUserID;
		UpdateDateTime = updateDateTime;
		StrUpdateDateTime = strUpdateDateTime;
		InfosContent = infosContent;
		UpdateUserName = updateUserName;
	}
	public String getStrUpdateDateTime() {
		return StrUpdateDateTime;
	}
	public void setStrUpdateDateTime(String strUpdateDateTime) {
		StrUpdateDateTime = strUpdateDateTime;
	}

	private String InfosContent;//
	private String UpdateUserName;//

	public NewsList(JSONObject object) throws JSONException {
		this.IsStatus = object.optBoolean("IsStatus", false);
		this.IsIsTop = object.optBoolean("IsIsTop", false);
		this.Status = object.optInt("Status", 0);
		this.IsTop = object.optInt("IsTop", 0);
		this.CategoryName = SwitchToString(object.optString("CategoryName", ""));
		this.PicUrl = SwitchToString(object.optString("PicUrl", ""));
		this.InfosTitle = SwitchToString(object.optString("InfosTitle", ""));
		this.CategoryID = SwitchToString(object.optString("CategoryID", ""));
		this.ThumbnailUrl = SwitchToString(object.optString("ThumbnailUrl", ""));
		this.UpdateDateTime = SwitchToString(object.optString("UpdateDateTime",
				""));
		this.StrUpdateDateTime = SwitchToString(object.optString("StrUpdateDateTime",
				""));
		this.UpdateUserName = SwitchToString(object.optString("UpdateUserName",
				""));
		this.CreateDateTime = SwitchToString(object.optString("CreateDateTime",
				""));
		this.Source = SwitchToString(object.optString("Source", ""));
		this.InfosID = SwitchToString(object.optString("InfosID", ""));
		this.Introduction = SwitchToString(object.optString("Introduction", ""));
		this.UpdateUserID = SwitchToString(object.optString("UpdateUserID", ""));
		this.InfosContent = SwitchToString(object.optString("InfosContent", ""));
	}

	private String SwitchToString(String content) {
		if (StringUtils.isNullOrEmpty(content)) {
			content = "";
		}
		return content;
	}

	@Override
	public String toString() {
		return "NewsList [CategoryName=" + CategoryName + ", IsIsTop="
				+ IsIsTop + ", InfosTitle=" + InfosTitle + ", IsTop=" + IsTop
				+ ", PicUrl=" + PicUrl + ", ThumbnailUrl=" + ThumbnailUrl
				+ ", CreateDateTime=" + CreateDateTime + ", Source=" + Source
				+ ", Status=" + Status + ", InfosID=" + InfosID + ", IsStatus="
				+ IsStatus + ", Introduction=" + Introduction + ", CategoryID="
				+ CategoryID + ", UpdateUserID=" + UpdateUserID
				+ ", UpdateDateTime=" + UpdateDateTime + ", InfosContent="
				+ InfosContent + ", UpdateUserName=" + UpdateUserName + "]";
	}

	public NewsList() {
		super();
	}

	public String getCategoryName() {
		return CategoryName;
	}

	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}

	public boolean isIsIsTop() {
		return IsIsTop;
	}

	public void setIsIsTop(boolean isIsTop) {
		IsIsTop = isIsTop;
	}

	public String getInfosTitle() {
		return InfosTitle;
	}

	public void setInfosTitle(String infosTitle) {
		InfosTitle = infosTitle;
	}

	public int getIsTop() {
		return IsTop;
	}

	public void setIsTop(int isTop) {
		IsTop = isTop;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getThumbnailUrl() {
		return ThumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		ThumbnailUrl = thumbnailUrl;
	}

	public String getCreateDateTime() {
		return CreateDateTime;
	}

	public void setCreateDateTime(String createDateTime) {
		CreateDateTime = createDateTime;
	}

	public String getSource() {
		return Source;
	}

	public void setSource(String source) {
		Source = source;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		Status = status;
	}

	public String getInfosID() {
		return InfosID;
	}

	public void setInfosID(String infosID) {
		InfosID = infosID;
	}

	public boolean isIsStatus() {
		return IsStatus;
	}

	public void setIsStatus(boolean isStatus) {
		IsStatus = isStatus;
	}

	public String getIntroduction() {
		return Introduction;
	}

	public void setIntroduction(String introduction) {
		Introduction = introduction;
	}

	public String getCategoryID() {
		return CategoryID;
	}

	public void setCategoryID(String categoryID) {
		CategoryID = categoryID;
	}

	public String getUpdateUserID() {
		return UpdateUserID;
	}

	public void setUpdateUserID(String updateUserID) {
		UpdateUserID = updateUserID;
	}

	public String getUpdateDateTime() {
		return UpdateDateTime;
	}

	public void setUpdateDateTime(String updateDateTime) {
		UpdateDateTime = updateDateTime;
	}

	public String getInfosContent() {
		return InfosContent;
	}

	public void setInfosContent(String infosContent) {
		InfosContent = infosContent;
	}

	public String getUpdateUserName() {
		return UpdateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		UpdateUserName = updateUserName;
	}

}
