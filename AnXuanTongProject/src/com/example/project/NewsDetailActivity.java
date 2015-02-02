/**    
 * �ļ���NewsDetailActivity.java    
 *    
 * �汾��Ϣ��    
 * ���ڣ�2014-10-23    
 * Copyright ���� Corporation 2014     
 * ��Ȩ����    
 *    
 */
package com.example.project;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pojo.NewsDetail;
import com.example.util.MyProgressDialog;
import com.example.util.MySharedUtil;
import com.example.util.Options;
import com.example.util.StringUtils;
import com.example.util.http.IHttpRequestDriver;
import com.example.util.http.imp.HttpRequestDriverImp;
import com.example.util.view.HtmlTextView;
import com.hengtong.library.async.AsyncHttpResponseHandler;
import com.hengtong.library.enty.HTResponseObject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgentJSInterface;
import com.anxuantong.ym.R;
/**
 * 
 * ��Ŀ��ƣ�AnXuanTongProject ����ƣ�NewsDetailActivity �������� �����ˣ�yanmei ����ʱ�䣺2014-10-23
 * ����5:02:23 �޸��ˣ�yanmei �޸�ʱ�䣺2014-10-23 ����5:02:23 �޸ı�ע��
 * @version
 */
public class NewsDetailActivity extends Activity {
	private HtmlTextView webView;
	
	private final String mPageName = "NewsDetailActivity";
	private MyProgressDialog progressDialog;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	private String infosID;
//	private MyWebView myWebView;
//	private WebView myWebView;
	private NewsDetail newsDetail;
	private String infosTitle;
	private TextView titleTv, backTv, moreTv;
	private String infoTitleDetail,PicUrl;
	private TextView titleDetailTv;
	private TextView timeDetailTv;
	private String StrUpdateDateTime;
	private ImageView newdetailImg;
	
	/**
	 * 设置字体颜色（WebView中对返回的Html 文本进行拼接）
	 */
	public static String START_TAGS = "<html>\n" + "<head>\n"
			+ "<style type=\"text/css\"> \n" + "body{\n" + "font-size: 8.0F;\n"
			+ " font-family: Arial;\n " + "color: #000;\n"
			+ "text-align:justify;\n" + "}\n" + "</style> \n" + "</head>\n"
			+ "<body>";
	/**
	 * 结束标签
	 */
	public static String END_TAGS = "\n</body>\n" + "</html>";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_newsdetail_activity);
		progressDialog = new MyProgressDialog(this);
		webView=(HtmlTextView) findViewById(R.id.wb_details);
		imageLoader = ImageLoader.getInstance();
		options = Options.getListOptions();
		newdetailImg=(ImageView) findViewById(R.id.newdetail_img);
		backTv = (TextView) findViewById(R.id.leftImage);
		backTv.setVisibility(View.VISIBLE);
		titleTv = (TextView) findViewById(R.id.centerTitle);
		timeDetailTv = (TextView) findViewById(R.id.newdetaicenterTime);
		moreTv = (TextView) findViewById(R.id.rightImage);
//		moreTv.setVisibility(View.VISIBLE);
		moreTv.setVisibility(View.GONE);
		infosTitle = "";
		infoTitleDetail = "";
		PicUrl="";
		titleDetailTv = (TextView) findViewById(R.id.newdetaicenterTitle);

		backTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		infosID = getIntent().getStringExtra("infosID");
		StrUpdateDateTime = getIntent().getStringExtra("StrUpdateDateTime");
//		myWebView = (WebView) findViewById(R.id.detail_content_webview);
//		myWebView.setVerticalScrollBarEnabled(false); //滚动条垂直不显示
		//设置：
//		myWebView.getSettings().setLoadWithOverviewMode(true);
//		myWebView.getSettings().setJavaScriptEnabled(true);
//		myWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
////		myWebView.setVerticalScrollBarEnabled(false);
//		myWebView.setVerticalScrollbarOverlay(false);
//		myWebView.setHorizontalScrollBarEnabled(false);
//		myWebView.setHorizontalScrollbarOverlay(false);
		
		newsDetail = new NewsDetail();
		getSingleNewsDetail(infosID);
		moreTv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				MySharedUtil sharedUtil = new MySharedUtil(
						NewsDetailActivity.this);
				sharedUtil.show(moreTv, "分享内容：" + newsDetail.getInfosContent());

			}
		});
//		new MobclickAgentJSInterface(this, myWebView, new WebChromeClient());
	}

	/**    
	*/
	private void showContent() {
		String content=newsDetail.getInfosContent().trim();
		  content = content.replace("<!--VIDEO#1--></p><p>", "");
	        content = content.replace("<!--VIDEO#2--></p><p>", "");
	        content = content.replace("<!--VIDEO#3--></p><p>", "");
	        content = content.replace("<!--VIDEO#4--></p><p>", "");
	        content = content.replace("<!--REWARD#0--></p><p>", "");
	        webView.setHtmlFromString(content, false);
//	        myWebView.setHtmlFromString(content, false);
//		Log.e("yanmei", "详细内容=--"+newsDetail.getInfosContent());
////		myWebView.setScrollBarStyle(0);// 滚动条风格，为0就是不给滚动条留空间，滚动条覆盖在网页上
//		StringBuffer sb = new StringBuffer();
//		sb.append(START_TAGS);
//		sb.append(newsDetail.getInfosContent().trim());
//		sb.append(END_TAGS);
////		myWebView.setBackgroundColor(0);
//		// 加载HTML字符串进行显示
//		myWebView.loadDataWithBaseURL(null, sb.toString().trim(), "text/html", "utf-8",null);
////		myWebView.loadDataWithBaseURL(null, newsDetail.getInfosContent(),"text/html", "UTF-8", null);
	}

	// ���������б�
	private void parseJson(String result) {
		try {
			JSONObject json = new JSONObject(result);
			newsDetail = new NewsDetail(json);
			infosTitle = newsDetail.getCategoryName();
			infoTitleDetail = newsDetail.getInfosTitle();
			PicUrl=newsDetail.getPicUrl();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * getSingleNewsDetail(������һ�仰�����������������)
	 */
	private void getSingleNewsDetail(final String infosid) {
		progressDialog.showProgessDialog("", "", true);
		final IHttpRequestDriver request = HttpRequestDriverImp.getSingleInstance(this);
		request.doGetInfosByID(infosid, new AsyncHttpResponseHandler() {
			@Override
			public void onStart() {
				super.onStart();
				progressDialog.showProgessDialog("", "", true);
			}
			@Override
			public void onFinish() {
				super.onFinish();
				progressDialog.dismissProgessDialog();
			}
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					byte[] responseBody) {
				progressDialog.dismissProgessDialog();
				HTResponseObject htResponseObject = request
						.handlerParseJson(new String(responseBody).toString());
				if (htResponseObject.ismSuccess()) {// �ɹ�
					if (htResponseObject.getmResult() == null)
						return;
					String result = htResponseObject.getmResult().toString();
					parseJson(result);
					titleTv.setText("" + infosTitle);
					titleDetailTv.setText("" + infoTitleDetail);
					timeDetailTv.setText("" + newsDetail.getSource() + "   "
							+ StrUpdateDateTime);
					if(!StringUtils.isEmpty(PicUrl)){
						imageLoader.displayImage(PicUrl,newdetailImg,options);
					}
//					imageLoader.displayImage(PicUrl,newdetailImg,options);
					showContent();
				} else {
					Toast.makeText(NewsDetailActivity.this,
							"错误信息=" + htResponseObject.getmError(),
							Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				progressDialog.dismissProgessDialog();
			}
		});
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		//SDK已经禁用了基于Activity 的页面统计，所以需要再次重新统计页面
		MobclickAgent.onPageEnd(mPageName);
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		//SDK已经禁用了基于Activity 的页面统计，所以需要再次重新统计页面
		MobclickAgent.onPageStart(mPageName);
		MobclickAgent.onResume(this);
	}
}
