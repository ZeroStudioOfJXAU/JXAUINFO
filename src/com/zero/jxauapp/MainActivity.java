package com.zero.jxauapp;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RadioButton;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.zero.goOut.GoOutInfo;

 /**   
 * Title: MainActivity
 * Description:��ҳ��
 * @author DaiS
 * @version 1.0
 * @date 2013-12-20
 */   
public class MainActivity extends SlidingFragmentActivity implements
		OnClickListener {
	
	private ImageButton sideMenuExtendBtn;// �������ť
	private ImageButton mapImageBtn;// ��ͼImageButton
	private ImageButton gooutImageBtn;// ����ImageButton
	private ImageButton phoneImageBtn;//���õ绰ImageButton
	private ImageButton CampusLandscapeBtn;//У԰����Btn
	private Fragment mContent;
	
	private PopupWindow mPopupWindow;// ���з��񣬵����˵�
	// popupWindows�Ի����еĿؼ�
	private RadioButton nongDaRadioBtn;//ũ�󣬵�ѡ��ť
	private RadioButton caiDaRadioBtn;//�ƴ󣬵�ѡ��ť
	private RadioButton xiaLuoRadioBtn;//���ޣ���ѡ��ť
	private CheckBox line240CheckBox;//240��ѡ��
	private CheckBox line704CheckBox;//704��ѡ��
	private Button arrivelBtn;//���ﰴť
	private Button setOffBtn;//������ť
	private View popupView;//���з������ͼ�������ҵ����
	PhoneNumberFragment listFragment;
	CampusLandscapeFragment campusFragment;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ���ñ������ı���
		setTitle("ũ��App");
		// �����Ƿ��ܹ�ʹ��ActionBar������
		setSlidingActionBarEnabled(true);
		// �����Ƿ���ʾHomeͼ�갴ť
		// getActionBar().setDisplayHomeAsUpEnabled(true);
		// ������������ͼ
		setContentView(R.layout.activity_main);
		// ��ʼ�������˵�
		initSlidingMenu(savedInstanceState);
		// Ϊ��ť���ü����������������߲˵���
		initBindListenerWidge();

		popupView = getLayoutInflater().inflate(R.layout.pop_menu, null);
		// popupView.setBackgroundColor(Color.BLUE);
		mPopupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, true);
		// �����е������ǵ���հ״���ʱ��PopupWindow����ʧ
		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(),
				(Bitmap) null));
		listFragment= new PhoneNumberFragment();
		campusFragment=new CampusLandscapeFragment();
	}
	/**
	 * ��ʼ�������˵�
	 */
	private void initSlidingMenu(Bundle savedInstanceState) {
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		// ���û����˵�����ͼ
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new SideManuFragment()).commit();
		// ʵ���������˵�����
		SlidingMenu sm = getSlidingMenu();
		// ���û�����Ӱ�Ŀ��
		sm.setShadowWidthRes(R.dimen.shadow_width);
		// ���û�����Ӱ��ͼ����Դ
		sm.setShadowDrawable(R.drawable.slid_menu_shadow);
		// ���û����˵���ͼ�Ŀ��
		sm.setBehindOffsetRes(R.dimen.slid_menu_offset);
		// ���ý��뽥��Ч����ֵ
		sm.setFadeDegree(0.35f);
		// ���ô�����Ļ��ģʽ
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
	}
	
	/**
	 * Ϊ�������е������Ӽ���
	 */
	public void initBindListenerWidge() {
		sideMenuExtendBtn = (ImageButton) findViewById(R.id.side_menu_extend_btn);
		mapImageBtn = (ImageButton) findViewById(R.id.map_imageBtn);
		gooutImageBtn = (ImageButton) findViewById(R.id.goout_imageBtn);
		phoneImageBtn=(ImageButton) findViewById(R.id.phone_imageBtn);
		CampusLandscapeBtn=(ImageButton) findViewById(R.id.campus_imageBtn);
		mapImageBtn.setOnClickListener(this);
		phoneImageBtn.setOnClickListener(this);
		sideMenuExtendBtn.setOnClickListener(this);
		gooutImageBtn.setOnClickListener(this);
		CampusLandscapeBtn.setOnClickListener(this);
	}
	
	/**
	 * ���з��񵯳��˵�
	 */
	public void runPopWindow() {
		nongDaRadioBtn = (RadioButton) popupView
				.findViewById(R.id.current_location_radioBtn1);
		caiDaRadioBtn = (RadioButton) popupView
				.findViewById(R.id.current_location_radioBtn2);
		xiaLuoRadioBtn = (RadioButton) popupView
				.findViewById(R.id.current_location_radioBtn3);
		line240CheckBox = (CheckBox) popupView
				.findViewById(R.id.line240_checkBox);
		line704CheckBox = (CheckBox) popupView
				.findViewById(R.id.line704_checkBox);
		arrivelBtn = (Button) popupView.findViewById(R.id.arrive_btn);
		setOffBtn = (Button) popupView.findViewById(R.id.set_off_btn);
		
		arrivelBtn.setOnClickListener(new PopMenuBtnListener());
		setOffBtn.setOnClickListener(new PopMenuBtnListener());
	}
	
	 /**   
	 * Description:���з��񣬵����˵��еİ�ť������
	 */   
	class PopMenuBtnListener implements OnClickListener{
		//��·
		String line;
		//��ǰλ��
		String currentLocation;
		//����
		boolean dir;
		/* (non-Javadoc)
		 * @see android.view.View.OnClickListener#onClick(android.view.View)
		 */
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int ItemId = v.getId();
			
			if (line240CheckBox.isChecked() && line704CheckBox.isChecked()) {
				line="240 704";
			}else if (line704CheckBox.isChecked()) {
				line="704";
			}else{
				line="240";
			}
			
			if (caiDaRadioBtn.isChecked()) {
				currentLocation="�ƴ�";
			} else if (xiaLuoRadioBtn.isChecked()) {
				currentLocation="����";
			}else{
				currentLocation="ũ��";
			}
			
			if(ItemId==R.id.set_off_btn){
				dir=true;
			}else if(ItemId==R.id.arrive_btn){
				dir=false;
			}
			  
			mPopupWindow.dismiss();
			GoOutInfo info=new GoOutInfo(currentLocation,line,dir);
			BusTrackFragment busFragment=new BusTrackFragment();  
	        Bundle busBundle=new Bundle();  
	        busBundle.putSerializable("BUSTRACKINFO", info);  
	        //��detailFragment�������  
	        busFragment.setArguments(busBundle);
	        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
	        fragmentTransaction.addToBackStack(null);
			fragmentTransaction.add(android.R.id.content, busFragment).commit(); 
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int ItemId = v.getId();// ��ȡ�����idֵ
		switch (ItemId) {
		case R.id.side_menu_extend_btn:
			toggle();
			break;
		case R.id.map_imageBtn:
			startActivity(new Intent().setClass(MainActivity.this,
					jxauMapAct.class));
			break;
		case R.id.goout_imageBtn:
			// ����PopWindows
			 mPopupWindow.showAsDropDown(v);
			 runPopWindow();
			break;
		case R.id.phone_imageBtn:
			//�л������õ绰ҳ��
			FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
			if(listFragment.isAdded()){
				fragmentTransaction.show(listFragment).commit();
			}else{
				fragmentTransaction.addToBackStack(null);
				fragmentTransaction.add(android.R.id.content, listFragment).commit(); 
			}
	         break;
		case R.id.campus_imageBtn:
			FragmentTransaction campusTransaction=getSupportFragmentManager().beginTransaction();
			if(campusFragment.isAdded()){
				campusTransaction.show(campusFragment).commit();
			}else{
				campusTransaction.addToBackStack(null);
				campusTransaction.add(android.R.id.content, campusFragment).commit(); 
			}
		default:
			break;
		}
	}
	
	/**
	 * �л�Fragment��Ҳ���л���ͼ�����ݡ�
	 * ���Խ�Activity �Ż�ΪFragment...
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
