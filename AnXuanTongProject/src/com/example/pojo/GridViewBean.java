/**    
 * �ļ�����GridViewBean.java    
 *    
 * �汾��Ϣ��    
 * ���ڣ�2014-10-3    
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
 * ��Ŀ���ƣ�AnXuanTongProject �����ƣ�GridViewBean �����������Ű��    �����ˣ�yanmei ����ʱ�䣺2014-10-3
 * ����3:23:40 �޸��ˣ�yanmei �޸�ʱ�䣺2014-10-3 ����3:23:40 �޸ı�ע��
 * 
 * @version
 * 
 */
public class GridViewBean implements Serializable{
	@Override
	public boolean equals(Object o) {
		if (this.ids.equals(((GridViewBean) o).ids)) {
			return true;
		} else {
			return false;
		}
	}

	   
	/**    
	 * ����һ���µ�ʵ�� GridViewBean.    
	 *        
	 */
	public GridViewBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)    
	 * @see java.lang.Object#toString()    
	 */
	@Override
	public String toString() {
		return "GridViewBean [Status=" + Status + ", IsStatus=" + IsStatus
				+ ", texts=" + texts + ", imgs=" + imgs + ", ids=" + ids
				+ ", codes=" + codes + ", CategorySort=" + CategorySort
				+ ", UpdateDateTime=" + UpdateDateTime + ", UpdateUserName="
				+ UpdateUserName + "]";
	}

	@Override
	public int hashCode() {
		return ids.hashCode();
	}
/**
 *  "Status": 1,
        "IsStatus": false,
        "CategoryName": "�ۺ�����",
        "CategoryID": "e495ec74-d6f8-4e47-b64d-8719bb9dd25c",
        "CategoryIco": null,
        "UpdateUserID": "123456",
        "CategorySort": 0,
        "UpdateDateTime": "2014-10-03T20:34:52.53",
        "UpdateUserName": "yanmei"
 */
	private int Status ;//״̬
	private boolean IsStatus;//�ж�״̬
	public String texts;//�б�����
	public String imgs;//ͼ��
	public String CategoryID;//idֵ
	public String ids;//idֵ
	public String codes;//
    private int CategorySort;//����
    public String UpdateDateTime;//����ʱ��
	public String UpdateUserName;//������
	
	public GridViewBean(JSONObject object)throws JSONException{
	this.IsStatus=object.optBoolean("IsStatus",false);
	this.Status=object.optInt("Status",0);
	this.CategorySort=object.optInt("CategorySort",0);
	this.texts=SwitchToString(object.optString("CategoryName", ""));
	this.imgs=SwitchToString(object.optString("CategoryIco", ""));
	this.ids=SwitchToString(object.optString("UpdateUserID", ""));
	this.CategoryID=SwitchToString(object.optString("CategoryID", ""));
	this.codes=SwitchToString(object.optString("", ""));
	this.UpdateDateTime=SwitchToString(object.optString("UpdateDateTime", ""));
	this.UpdateUserName=SwitchToString(object.optString("UpdateUserName", ""));
	}
	private String SwitchToString(String content) {
		if (StringUtils.isNullOrEmpty(content)) {
			content = "";
		}
		return content;
	}
	
	/**    
	 * status    
	 *    
	 * @return  the status    
	 * @since   CodingExample Ver(���뷶���鿴) 1.0    
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
	 * categoryID    
	 *    
	 * @return  the categoryID    
	 * @since   CodingExample Ver(���뷶���鿴) 1.0    
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
	 * isStatus    
	 *    
	 * @return  the isStatus    
	 * @since   CodingExample Ver(���뷶���鿴) 1.0    
	 */
	
	public boolean isIsStatus() {
		return IsStatus;
	}

	/**    
	 * @param isStatus the isStatus to set    
	 */
	public void setIsStatus(boolean isStatus) {
		IsStatus = isStatus;
	}

	/**    
	 * categorySort    
	 *    
	 * @return  the categorySort    
	 * @since   CodingExample Ver(���뷶���鿴) 1.0    
	 */
	
	public int getCategorySort() {
		return CategorySort;
	}

	/**    
	 * @param categorySort the categorySort to set    
	 */
	public void setCategorySort(int categorySort) {
		CategorySort = categorySort;
	}

	/**    
	 * updateDateTime    
	 *    
	 * @return  the updateDateTime    
	 * @since   CodingExample Ver(���뷶���鿴) 1.0    
	 */
	
	public String getUpdateDateTime() {
		return UpdateDateTime;
	}

	/**    
	 * @param updateDateTime the updateDateTime to set    
	 */
	public void setUpdateDateTime(String updateDateTime) {
		UpdateDateTime = updateDateTime;
	}

	/**    
	 * updateUserName    
	 *    
	 * @return  the updateUserName    
	 * @since   CodingExample Ver(���뷶���鿴) 1.0    
	 */
	
	public String getUpdateUserName() {
		return UpdateUserName;
	}

	/**    
	 * @param updateUserName the updateUserName to set    
	 */
	public void setUpdateUserName(String updateUserName) {
		UpdateUserName = updateUserName;
	}

	public String getTexts() {
		return texts;
	}

	public void setTexts(String texts) {
		this.texts = texts;
	}

	public String getImgs() {
		return imgs;
	}

	public void setImgs(String imgs) {
		this.imgs = imgs;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getCodes() {
		return codes;
	}

	public void setCodes(String codes) {
		this.codes = codes;
	}

}
