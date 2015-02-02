/**    
 * 文件名：NewsDetail.java    
 *    
 * 版本信息：    
 * 日期：2014-10-24    
 * Copyright 足下 Corporation 2014     
 * 版权所有    
 *    
 */
package com.example.pojo;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.hengtong.library.utils.StringUtils;

/**
 * 
 * 项目名称：AnXuanTongProject 类名称：NewsDetail 类描述： 新闻详细内容 创建人：yanmei 创建时间：2014-10-24
 * 下午2:11:26 修改人：yanmei 修改时间：2014-10-24 下午2:11:26 修改备注：
 * 
 * @version
 * 
 */
public class NewsDetail implements Serializable {
/**
 *  "InfosID": "fc32dfd0-c8f0-48d0-8c65-7acd394a318c",
    "CategoryID": "e495ec74-d6f8-4e47-b64d-8719bb9dd25c",
    "CategoryName": "综合新闻",
    "Status": 1,
    "IsTop": 1,
    "Source": "赶集网",
    "PicUrl": "http://localhost:8081/Upload/Axt.Image/day_141022/201410220802507429.jpg",
    "ThumbnailUrl": null,
    "Introduction":
 */
	private int Status;
	private int IsTop;
	private String InfosID;
	private String CategoryID;
	private String CategoryName;
	private String Source;
	private String PicUrl;
	private String ThumbnailUrl;
	private String Introduction;
	private String InfosTitle;
	private String InfosContent;//新闻内容
	   
	public NewsDetail(JSONObject object) throws JSONException {
		this.Status = object.optInt("Status", 0);
		this.IsTop = object.optInt("IsTop", 0);
		this.CategoryName = SwitchToString(object.optString("CategoryName", ""));
		this.PicUrl = SwitchToString(object.optString("PicUrl", ""));
		this.InfosTitle = SwitchToString(object.optString("InfosTitle", ""));
		this.CategoryID = SwitchToString(object.optString("CategoryID", ""));
		this.ThumbnailUrl = SwitchToString(object.optString("ThumbnailUrl", ""));
		this.Source = SwitchToString(object.optString("Source", ""));
		this.InfosID = SwitchToString(object.optString("InfosID", ""));
		this.Introduction = SwitchToString(object.optString("Introduction", ""));
		this.InfosContent = SwitchToString(object.optString("InfosContent", ""));
	}
	
	   
	/**    
	 * 创建一个新的实例 NewsDetail.    
	 *    
	 * @param status
	 * @param isTop
	 * @param infosID
	 * @param categoryID
	 * @param categoryName
	 * @param source
	 * @param picUrl
	 * @param thumbnailUrl
	 * @param introduction
	 * @param infosTitle
	 * @param infosContent    
	 */
	public NewsDetail(int status, int isTop, String infosID, String categoryID,
			String categoryName, String source, String picUrl,
			String thumbnailUrl, String introduction, String infosTitle,
			String infosContent) {
		super();
		Status = status;
		IsTop = isTop;
		InfosID = infosID;
		CategoryID = categoryID;
		CategoryName = categoryName;
		Source = source;
		PicUrl = picUrl;
		ThumbnailUrl = thumbnailUrl;
		Introduction = introduction;
		InfosTitle = infosTitle;
		InfosContent = infosContent;
	}

	/**    
	 * status    
	 *    
	 * @return  the status    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
	 */
	
	public int getStatus() {
		return Status;
	}

	/**    
	 * @param status the status to set    
	 */
	public void setStatus(int status) {
		Status = status;
	}

	/**    
	 * isTop    
	 *    
	 * @return  the isTop    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
	 */
	
	public int getIsTop() {
		return IsTop;
	}

	/**    
	 * @param isTop the isTop to set    
	 */
	public void setIsTop(int isTop) {
		IsTop = isTop;
	}

	private String SwitchToString(String content) {
		if (StringUtils.isNullOrEmpty(content)) {
			content = "";
		}
		return content;
	}
	/**    
	 * 创建一个新的实例 NewsDetail.    
	 *        
	 */
	public NewsDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	   
	/**    
	 * 创建一个新的实例 NewsDetail.    
	 *    
	 * @param infosID
	 * @param categoryID
	 * @param categoryName
	 * @param source
	 * @param picUrl
	 * @param thumbnailUrl
	 * @param introduction
	 * @param infosTitle
	 * @param infosContent    
	 */
	public NewsDetail(String infosID, String categoryID, String categoryName,
			String source, String picUrl, String thumbnailUrl,
			String introduction, String infosTitle, String infosContent) {
		super();
		InfosID = infosID;
		CategoryID = categoryID;
		CategoryName = categoryName;
		Source = source;
		PicUrl = picUrl;
		ThumbnailUrl = thumbnailUrl;
		Introduction = introduction;
		InfosTitle = infosTitle;
		InfosContent = infosContent;
	}


	/* (non-Javadoc)    
	 * @see java.lang.Object#toString()    
	 */
	@Override
	public String toString() {
		return "NewsDetail [InfosID=" + InfosID + ", CategoryID=" + CategoryID
				+ ", CategoryName=" + CategoryName + ", Source=" + Source
				+ ", PicUrl=" + PicUrl + ", ThumbnailUrl=" + ThumbnailUrl
				+ ", Introduction=" + Introduction + ", InfosTitle="
				+ InfosTitle + ", InfosContent=" + InfosContent + "]";
	}


	/**    
	 * infosID    
	 *    
	 * @return  the infosID    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
	 */
	
	public String getInfosID() {
		return InfosID;
	}


	/**    
	 * @param infosID the infosID to set    
	 */
	public void setInfosID(String infosID) {
		InfosID = infosID;
	}


	/**    
	 * categoryID    
	 *    
	 * @return  the categoryID    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
	 */
	
	public String getCategoryID() {
		return CategoryID;
	}


	/**    
	 * @param categoryID the categoryID to set    
	 */
	public void setCategoryID(String categoryID) {
		CategoryID = categoryID;
	}


	/**    
	 * categoryName    
	 *    
	 * @return  the categoryName    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
	 */
	
	public String getCategoryName() {
		return CategoryName;
	}


	/**    
	 * @param categoryName the categoryName to set    
	 */
	public void setCategoryName(String categoryName) {
		CategoryName = categoryName;
	}


	/**    
	 * source    
	 *    
	 * @return  the source    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
	 */
	
	public String getSource() {
		return Source;
	}


	/**    
	 * @param source the source to set    
	 */
	public void setSource(String source) {
		Source = source;
	}


	/**    
	 * picUrl    
	 *    
	 * @return  the picUrl    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
	 */
	
	public String getPicUrl() {
		return PicUrl;
	}


	/**    
	 * @param picUrl the picUrl to set    
	 */
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}


	/**    
	 * thumbnailUrl    
	 *    
	 * @return  the thumbnailUrl    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
	 */
	
	public String getThumbnailUrl() {
		return ThumbnailUrl;
	}


	/**    
	 * @param thumbnailUrl the thumbnailUrl to set    
	 */
	public void setThumbnailUrl(String thumbnailUrl) {
		ThumbnailUrl = thumbnailUrl;
	}


	/**    
	 * introduction    
	 *    
	 * @return  the introduction    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
	 */
	
	public String getIntroduction() {
		return Introduction;
	}


	/**    
	 * @param introduction the introduction to set    
	 */
	public void setIntroduction(String introduction) {
		Introduction = introduction;
	}


	/**    
	 * infosTitle    
	 *    
	 * @return  the infosTitle    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
	 */
	
	public String getInfosTitle() {
		return InfosTitle;
	}


	/**    
	 * @param infosTitle the infosTitle to set    
	 */
	public void setInfosTitle(String infosTitle) {
		InfosTitle = infosTitle;
	}


	/**    
	 * infosContent    
	 *    
	 * @return  the infosContent    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
	 */
	
	public String getInfosContent() {
		return InfosContent;
	}


	/**    
	 * @param infosContent the infosContent to set    
	 */
	public void setInfosContent(String infosContent) {
		InfosContent = infosContent;
	}
	
	
}
