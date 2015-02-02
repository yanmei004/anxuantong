/**    
 * 文件名：CoalType.java    
 *    
 * 版本信息：    
 * 日期：2014-10-27    
 * Copyright 足下 Corporation 2014     
 * 版权所有    
 *    
 */
package com.example.pojo;

import org.json.JSONException;
import org.json.JSONObject;

import com.hengtong.library.utils.StringUtils;

/**
 * 
 * 项目名称：AnXuanTongProject 类名称：CoalType 类描述： 创建人：yanmei 创建时间：2014-10-27 下午2:23:05
 * 修改人：yanmei 修改时间：2014-10-27 下午2:23:05 修改备注：
 * 
 * @version
 * 
 */
public class CoalType {
	/**
	 * "Status": 1,// "IsStatus": false,// "UserCategoryName": "金沙煤矿",//
	 * "Contact": null, "Address": "金沙", "UpdateUserID": "123456",
	 * "UpdateDateTime": "2014-10-27T14:18:08.21", "UserCategoryID":
	 * "60e6bbcc-21bf-48bd-b4ce-ca238d11d543", "UpdateUserName": "yanmei",
	 * "PhoneNo": null
	 */
	private int Status;// ###//
	private boolean IsStatus;// ###//
	private String UserCategoryName;//
	private String Contact;//
	private String Address;//
	private String UpdateUserID;//
	private String UpdateDateTime;//
	private String UserCategoryID;//
	private String UpdateUserName;//
	private String PhoneNo;//

	public CoalType(JSONObject object) throws JSONException {
		this.IsStatus = object.optBoolean("IsStatus", false);
		this.Status = object.optInt("Status", 0);
		this.UserCategoryName = SwitchToString(object.optString(
				"UserCategoryName", ""));
		this.Contact = SwitchToString(object.optString("Contact", ""));
		this.Address = SwitchToString(object.optString("Address", ""));
		this.UpdateDateTime = SwitchToString(object.optString("UpdateDateTime",
				""));
		this.UpdateUserName = SwitchToString(object.optString("UpdateUserName",
				""));
		this.PhoneNo = SwitchToString(object.optString("PhoneNo", ""));
		this.UpdateUserID = SwitchToString(object.optString("UpdateUserID", ""));
		this.UserCategoryID = SwitchToString(object.optString("UserCategoryID",
				""));
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
	 * isStatus    
	 *    
	 * @return  the isStatus    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
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
	 * userCategoryName    
	 *    
	 * @return  the userCategoryName    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
	 */
	
	public String getUserCategoryName() {
		return UserCategoryName;
	}

	/**    
	 * @param userCategoryName the userCategoryName to set    
	 */
	public void setUserCategoryName(String userCategoryName) {
		UserCategoryName = userCategoryName;
	}

	/**    
	 * contact    
	 *    
	 * @return  the contact    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
	 */
	
	public String getContact() {
		return Contact;
	}

	/**    
	 * @param contact the contact to set    
	 */
	public void setContact(String contact) {
		Contact = contact;
	}

	/**    
	 * address    
	 *    
	 * @return  the address    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
	 */
	
	public String getAddress() {
		return Address;
	}

	/**    
	 * @param address the address to set    
	 */
	public void setAddress(String address) {
		Address = address;
	}

	/**    
	 * updateUserID    
	 *    
	 * @return  the updateUserID    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
	 */
	
	public String getUpdateUserID() {
		return UpdateUserID;
	}

	/**    
	 * @param updateUserID the updateUserID to set    
	 */
	public void setUpdateUserID(String updateUserID) {
		UpdateUserID = updateUserID;
	}

	/**    
	 * updateDateTime    
	 *    
	 * @return  the updateDateTime    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
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
	 * userCategoryID    
	 *    
	 * @return  the userCategoryID    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
	 */
	
	public String getUserCategoryID() {
		return UserCategoryID;
	}

	/**    
	 * @param userCategoryID the userCategoryID to set    
	 */
	public void setUserCategoryID(String userCategoryID) {
		UserCategoryID = userCategoryID;
	}

	/**    
	 * updateUserName    
	 *    
	 * @return  the updateUserName    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
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

	/**    
	 * phoneNo    
	 *    
	 * @return  the phoneNo    
	 * @since   CodingExample Ver(编码范例查看) 1.0    
	 */
	
	public String getPhoneNo() {
		return PhoneNo;
	}

	/**    
	 * @param phoneNo the phoneNo to set    
	 */
	public void setPhoneNo(String phoneNo) {
		PhoneNo = phoneNo;
	}

	   
	/**    
	 * 创建一个新的实例 CoalType.    
	 *        
	 */
	public CoalType() {
		super();
		// TODO Auto-generated constructor stub
	}

	   
	/**    
	 * 创建一个新的实例 CoalType.    
	 *    
	 * @param status
	 * @param isStatus
	 * @param userCategoryName
	 * @param contact
	 * @param address
	 * @param updateUserID
	 * @param updateDateTime
	 * @param userCategoryID
	 * @param updateUserName
	 * @param phoneNo    
	 */
	public CoalType(int status, boolean isStatus, String userCategoryName,
			String contact, String address, String updateUserID,
			String updateDateTime, String userCategoryID,
			String updateUserName, String phoneNo) {
		super();
		Status = status;
		IsStatus = isStatus;
		UserCategoryName = userCategoryName;
		Contact = contact;
		Address = address;
		UpdateUserID = updateUserID;
		UpdateDateTime = updateDateTime;
		UserCategoryID = userCategoryID;
		UpdateUserName = updateUserName;
		PhoneNo = phoneNo;
	}

	/* (non-Javadoc)    
	 * @see java.lang.Object#toString()    
	 */
	@Override
	public String toString() {
		return "CoalType [Status=" + Status + ", IsStatus=" + IsStatus
				+ ", UserCategoryName=" + UserCategoryName + ", Contact="
				+ Contact + ", Address=" + Address + ", UpdateUserID="
				+ UpdateUserID + ", UpdateDateTime=" + UpdateDateTime
				+ ", UserCategoryID=" + UserCategoryID + ", UpdateUserName="
				+ UpdateUserName + ", PhoneNo=" + PhoneNo + "]";
	}
	
}
