/**
 * 
 */
package com.zero.jxauapp;

import java.util.List;

import com.zero.goOut.BusInfo;
import com.zero.goOut.GoOutInfo;
import com.zero.goOut.getDataAsyncTask;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

/**
 * Title: GoOutAct Description:����ҳ��
 * 
 * @date 2013-12-19
 */

public class GoOutAct extends Activity {

	private TextView goOutTextView;
	// Update data in main thread
	public Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			List<BusInfo> list = (List<BusInfo>) msg.obj;
			BusInfo in = list.get(0);
			if (in.getDirection())
				goOutTextView.append("������" + '\n');
			else
				goOutTextView.append("������" + '\n');
			for (int i = 1; i < list.size(); i++) {
				BusInfo info = list.get(i);
				goOutTextView.append(info.number + info.station + info.flag
						+ '\n');
			}
		};
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.go_out_layout);
		goOutTextView = (TextView) findViewById(R.id.go_out_textView);
		dealWithDataFromPopWindow();
	}

	public void dealWithDataFromPopWindow() {
		GoOutInfo info = (GoOutInfo) getIntent().getSerializableExtra(
				"GoOutInfo");
		String location;

		if (info.getCurrentLocation().endsWith("ũ��")) {
			location = "ũ��������վ";
		} else if (info.getCurrentLocation().endsWith("����")) {
			location = "����վ";
		} else {
			location = "�ƴ�վ";
		}

		// new getDataAsyncTask(location,line,dire, handler).execute();
		// 1��ʾ���� 2��ʾ������

		if (info.getDirection()) {
			if (info.getLine().equals("240 704")) {
				new getDataAsyncTask(location, "240", 1, handler).execute();
				new getDataAsyncTask(location, "704", 1, handler).execute();
			} else {
				new getDataAsyncTask(location, info.getLine(), 1, handler)
						.execute();
			}
		} else {
			if (info.getLine().equals("240 704")) {
				new getDataAsyncTask(location, "240", 0, handler).execute();
				new getDataAsyncTask(location, "704", 0, handler).execute();
			} else {
				new getDataAsyncTask(location, info.getLine(), 0, handler)
						.execute();
			}
		}
	}
}
