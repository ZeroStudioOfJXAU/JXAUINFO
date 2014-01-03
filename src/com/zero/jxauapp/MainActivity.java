package com.zero.jxauapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.zero.goOut.BusRequestBean;
import com.zero.goOut.CheckBoxAdapter;
import com.zero.goOut.RadioButonListAdapter;
import com.zero.goOut.ViewHolder;

/**
 * Title: MainActivity Description:��ҳ��
 * 
 * @author DaiS
 * @version 1.0
 * @date 2013-12-20
 */
public class MainActivity extends SlidingFragmentActivity implements
		OnClickListener {

	private ImageButton sideMenuExtendBtn;// �������ť
	private ImageButton mapImageBtn;// ��ͼImageButton
	private ImageButton gooutImageBtn;// ����ImageButton
	private ImageButton phoneImageBtn;// ���õ绰ImageButton
	private ImageButton campusLandscapeBtn;// У԰����Btn
	private ImageButton newsImageBtn;
	private TextView functionTextView;
	private Fragment mContent;

	/* popupWindows�Ի����еĿؼ� */
	private PopupWindow mPopupWindow;// ���з��񣬵����˵�
	private Button arrivelBtn;// ���ﰴť
	private Button setOffBtn;// ������ť

	private ListView radioButtonListView;
	private ListView checkboxListView;

	private String[] lineArray;
	private String[] stationArray;

	private ArrayList<String> listStr = null;
	private List<HashMap<String, Object>> list = null;

	private View popupView;// ���з������ͼ�������ҵ����

	private PhoneNumberFragment listFragment;
	private CampusLandscapeFragment campusFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTitle("ũ��App");
		setSlidingActionBarEnabled(true);// �����Ƿ��ܹ�ʹ��ActionBar������
		setContentView(R.layout.activity_main);

		initSlidingMenu(savedInstanceState);// ��ʼ�������˵�
		// Ϊ��ť���ü����������������߲˵���
		popupView = getLayoutInflater().inflate(
				R.layout.bus_track_select_layout, null);
		// popupView.setBackgroundColor(Color.BLUE);
		mPopupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		/* �����е������ǵ���հ״���ʱ��PopupWindow����ʧ */
		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(),
				(Bitmap) null));
		initWidge();
		listFragment = new PhoneNumberFragment();
		campusFragment = new CampusLandscapeFragment();
	}

	/**
	 * ��ʼ�������˵�
	 */
	private void initSlidingMenu(Bundle savedInstanceState) {
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new SideManuFragment()).commit();
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);// ���û�����Ӱ�Ŀ��
		sm.setShadowDrawable(R.drawable.slid_menu_shadow);// ���û�����Ӱ��ͼ����Դ
		sm.setBehindOffsetRes(R.dimen.slid_menu_offset);// ���û����˵���ͼ�Ŀ��
		sm.setFadeDegree(0.35f);// ���ý��뽥��Ч����ֵ
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);// ���ô�����Ļ��ģʽ
	}

	/**
	 * ��ʼ�����˵���Ϊ�����Ӽ���
	 */
	public void initWidge() {

		sideMenuExtendBtn = (ImageButton) findViewById(R.id.side_menu_extend_btn);
		mapImageBtn = (ImageButton) findViewById(R.id.map_imageBtn);
		gooutImageBtn = (ImageButton) findViewById(R.id.goout_imageBtn);
		phoneImageBtn = (ImageButton) findViewById(R.id.phone_imageBtn);
		campusLandscapeBtn = (ImageButton) findViewById(R.id.campus_imageBtn);
		newsImageBtn = (ImageButton) findViewById(R.id.news_and_noticle_imageBtn);
		functionTextView = (TextView) findViewById(R.id.main_function_textView);

		radioButtonListView = (ListView) popupView
				.findViewById(R.id.user_current_location_listView);
		checkboxListView = (ListView) popupView
				.findViewById(R.id.bus_line_listView);
		arrivelBtn = (Button) popupView.findViewById(R.id.arrive_btn);
		setOffBtn = (Button) popupView.findViewById(R.id.set_off_btn);

		mapImageBtn.setOnClickListener(this);
		phoneImageBtn.setOnClickListener(this);
		sideMenuExtendBtn.setOnClickListener(this);
		gooutImageBtn.setOnClickListener(this);
		campusLandscapeBtn.setOnClickListener(this);
		newsImageBtn.setOnClickListener(this);

		arrivelBtn.setOnClickListener(new PopMenuBtnListener());
		setOffBtn.setOnClickListener(new PopMenuBtnListener());

		stationArray = getResources().getStringArray(R.array.bus_station_array);
		lineArray = getResources().getStringArray(R.array.bus_line_array);
	}

	/**
	 * ���з��񵯳��˵�
	 */
	
	public void runPopWindow() {
		List<String> stationList = Arrays.asList(stationArray);
		RadioButonListAdapter radioButtonAdapter = new RadioButonListAdapter(
				this, stationList);

		list = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < lineArray.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("item_tv", lineArray[i]);
			map.put("item_cb", false);
			list.add(map);

			CheckBoxAdapter checkBoxadapter = new CheckBoxAdapter(this, list,
					R.layout.bus_track_checkbox_list_item, new String[] {
							"item_tv", "item_cb" }, new int[] {
							R.id.checkbox_list_textView_item,
							R.id.checkbox_list_checkbox_item });
			
			checkboxListView.setAdapter(checkBoxadapter);
			radioButtonListView.setAdapter(radioButtonAdapter);
			
			listStr = new ArrayList<String>();
			checkboxListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {

					ViewHolder holder = (ViewHolder) view.getTag();
					holder.cb.toggle(); // ��ÿ�λ�ȡ�����itemʱ�ı�checkbox��״̬
					CheckBoxAdapter.isSelected.put(position,
							holder.cb.isChecked());         // ͬʱ�޸�map��ֵ����״̬
					if (holder.cb.isChecked()) {
						listStr.add(lineArray[position]);
					} else {
						listStr.remove(lineArray[position]);
					}
				}
			});
		}
	}

	class PopMenuBtnListener implements OnClickListener {

		String currentStation;// ��ǰλ��
		boolean dir;//����

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int ItemId = v.getId();
			for (int i = 0, j = radioButtonListView.getCount(); i < j; i++) {
				View child = radioButtonListView.getChildAt(i);
				RadioButton rdoBtn = (RadioButton) child
						.findViewById(R.id.radio_btn);
				if (rdoBtn.isChecked())
					currentStation = stationArray[i];
			}
			if (ItemId == R.id.set_off_btn) {
				dir = true;
			} else {
				dir = false;
			}
			if(listStr.size()==0 || currentStation==null){
				Toast.makeText(getApplicationContext(), "��ѡ����·�͵�ǰλ�ã�", Toast.LENGTH_SHORT).show();
				return;
			}
			mPopupWindow.dismiss();
			BusRequestBean info = new BusRequestBean(currentStation, listStr, dir);
			BusTrackFragment busFragment = new BusTrackFragment();
			Bundle busBundle = new Bundle();
			busBundle.putSerializable("BUSTRACKINFO", info);
			/*��BusTrackFragment�������*/
			busFragment.setArguments(busBundle);
			FragmentTransaction fragmentTransaction = getSupportFragmentManager()
					.beginTransaction();
			fragmentTransaction.addToBackStack(null);
			fragmentTransaction.add(android.R.id.content, busFragment).commit();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int ItemId = v.getId();
		switch (ItemId) {
		case R.id.side_menu_extend_btn:
			toggle();
			break;
		case R.id.map_imageBtn:
			startActivity(new Intent().setClass(MainActivity.this,
					jxauMapAct.class));
			break;
		case R.id.goout_imageBtn:
			mPopupWindow.showAsDropDown(v);
//			mPopupWindow.showAtLocation(functionTextView, Gravity.TOP, 0, 0);
			runPopWindow();
			break;
		case R.id.phone_imageBtn:
			// �л������õ绰ҳ��
			FragmentTransaction fragmentTransaction = getSupportFragmentManager()
					.beginTransaction();
			if (listFragment.isAdded()) {
				fragmentTransaction.show(listFragment).commit();
			} else {
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.add(android.R.id.content, listFragment)
						.commit();
			}
			break;
		case R.id.campus_imageBtn:
			FragmentTransaction campusTransaction = getSupportFragmentManager()
					.beginTransaction();
			if (campusFragment.isAdded()) {
				campusTransaction.show(campusFragment).commit();
			} else {
				campusTransaction.addToBackStack(null);
				campusTransaction.add(android.R.id.content, campusFragment)
						.commit();
			}
			break;
		case R.id.news_and_noticle_imageBtn:
			startActivity(new Intent().setClass(MainActivity.this,
					NewsAct.class));
		default:
			break;
		}
	}

	/**
	 * �л�Fragment��Ҳ���л���ͼ�����ݡ� ���Խ�Activity �Ż�ΪFragment...
	 */
	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();
	}

	/**
	 * �˵���ť����¼���ͨ�����ActionBar��Homeͼ�갴ť���򿪻����˵�
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		// ������ؼ��رջ����˵�
		super.onBackPressed();
	}
}
