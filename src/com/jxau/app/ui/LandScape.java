package com.jxau.app.ui;



import com.zero.jxauapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;


public class LandScape extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.landscape);
	}
}
