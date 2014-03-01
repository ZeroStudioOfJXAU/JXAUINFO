/**
 * 
 */
package com.jxau.app.ui;

import com.zero.jxauapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;

 /**   
 * Title: AdminSystemAct
 * Description:
 * @author DaiS
 * @version 1.0
 * @date 2014-1-9
 */

public class AdminSystem extends Activity{
	private WebView mWebView;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.web);
		mWebView=(WebView) findViewById(R.id.webView1);
		WebSettings webSettings = mWebView.getSettings();       
        mWebView.loadUrl("http://jwgl.jxau.edu.cn/user/login");   
	}
}
