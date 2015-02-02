package com.example.util;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * 带进度条的WebView
 * @author lin
 */
public class MyWebView extends WebView {

    private MyProgressBarUtil progressbar;

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        progressbar = new MyProgressBarUtil(context);
        setWebChromeClient(new WebChromeClient());
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
            	progressbar.dismissProgessBarDialog();
            } else {
            	progressbar.showProgessDialog("", "", true);
            }
            super.onProgressChanged(view, newProgress);
        }

    }
}
