package com.example.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * 封装后的webview工具�? * @author lin
 * 
 */
public class MyWebViewUtil  {

	private Context context ;
	
	public MyWebViewUtil(Context context){
		this.context = context;
	}
	
	
	/**
	 * 封装webview显示的信�?	 * @param myWebView 这里必须传入我们自定义的webview
	 * @param uri �?��访问的网�?	 * @author lin
	 */
	public void showWebInfo(MyWebView myWebView,String url){
		
		//设置webview可以支持浏览器的js
		myWebView.getSettings().setJavaScriptEnabled(true);
		//触摸焦点起作�?		myWebView.requestFocus();
		
		//自�?应大�?		myWebView.getSettings().setUseWideViewPort(true);
		myWebView.getSettings().setLoadWithOverviewMode(true);
		
		//设置webView可缩�?		myWebView.getSettings().setUseWideViewPort(true);
		myWebView.getSettings().setBuiltInZoomControls(true);
		
		myWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		
		
		
		//webview处理传入进来的url地址链接
		myWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                if (url != null && url.startsWith("http://")){
                	Log.e("yanmei", "url="+url);
                	context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
                }
        });
		Log.e("yanmei", "url2="+url);
        //加载url链接地址
		myWebView.loadUrl(url);
		myWebView.refreshDrawableState();
	}
	
	/**
	 * 可以有弹出框的情�?	 * @author linchengliang
	 */
	final class MyWebChromeClient extends WebChromeClient {
		

		@Override
	    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
	        new AlertDialog.Builder(context)
	        .setTitle("信息窗口")
	        .setMessage(message)
	        .setNegativeButton(android.R.string.ok,
	                new DialogInterface.OnClickListener()
	        {
	            public void onClick(DialogInterface dialog, int which)
	            {
	                result.confirm();
	            }
	        })
	        .setPositiveButton(android.R.string.cancel,
	                new DialogInterface.OnClickListener()
	        {
	            public void onClick(DialogInterface dialog, int which)
	            {
	                result.cancel();
	            }
	        })
	        .create()
	        .show();

	        return true;
	    }
	}
	
}
