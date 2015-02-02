/**    
 * �ļ���IHttpRequestDriver.java    
 *    
 * �汾��Ϣ��    
 * ���ڣ�2014-10-3    
 * Copyright ���� Corporation 2014     
 * ��Ȩ����    
 *    
 */
package com.example.util.http;

import com.example.util.http.imp.HTHttpResponseHandler;
import com.hengtong.library.async.AsyncHttpResponseHandler;
import com.hengtong.library.enty.HTResponseObject;

/**
 * 
 * ��Ŀ��ƣ�AnXuanTongProject ����ƣ�IHttpRequestDriver �������� �����ˣ�yanmei ����ʱ�䣺2014-10-3
 * ����4:42:59 �޸��ˣ�yanmei �޸�ʱ�䣺2014-10-3 ����4:42:59 �޸ı�ע��
 * 
 * @version
 * 
 */
public interface IHttpRequestDriver {

	// ������������ݴ���
	public HTResponseObject handlerParseJson(String json);

	// ����
	public void checkUpdate(HTHttpResponseHandler handler);

	// 1.��ȡ���Ű��
	public void doGetInfosCategory(int pageIndex, int pageSize,
			AsyncHttpResponseHandler handler);

	// 2.获取新闻列表
	public void doGetInfos(String categoryID,int isTop, String infosTitle, int pageIndex,
			int pageSize, AsyncHttpResponseHandler handler);

	// 3.��ȡ��������
	public void doGetInfosByID(String infosID, AsyncHttpResponseHandler handler);

	// 4.�û�ע��
//	public void doRegister(String userCategoryID, String userName,
//			String phoneNo, String password, AsyncHttpResponseHandler handler);
	public void doRegister(String userCategoryID, String userName,
			String phoneNo, String password, String company,AsyncHttpResponseHandler handler);

	// 5.��½
	public void login(String phoneNo, String password, String version,
			AsyncHttpResponseHandler handler);

	// 6.��ȡ�û���Ϣ

	// 7.��ȡϵͳ��Ϣ
	public void doGetSystemInfo(String title, AsyncHttpResponseHandler handler);

	// 8.��ȡ�汾GetLastVersionInfo
	public void GetLastVersionInfo(AsyncHttpResponseHandler handler);
	
	// 9.��ȡ�û�����GetUserCategory
	public void doGetUserCategory(AsyncHttpResponseHandler handler);

}
