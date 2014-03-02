package com.jxau.app.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.StaggeredGridView.LayoutParams;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.jxau.app.adapter.CheckBoxAdapter;
import com.jxau.app.adapter.RadioButonListAdapter;
import com.jxau.app.bean.GoOutBean;
import com.jxau.app.bean.RadioListViewHolder;
import com.jxau.app.bean.ViewHolder;
import com.jxau.app.common.PictureUnit;
import com.zero.jxauapp.R;

/**
 * Title: MainActivity Description:��ҳ��
 * 
 * @author DaiS
 * @version 1.0
 * @date 2013-12-20
 */
public class Main extends SlidingFragmentActivity implements
		OnClickListener {

	private ImageButton sideMenuExtendBtn;// �������ť
	private ImageView mapImageBtn;  // ��ͼImageButton
	private ImageView gooutImageBtn;// ����ImageButton
	private ImageView phoneImageBtn;// ���õ绰ImageButton
	private ImageView campusLandscapeBtn;// У԰����Btn
	private ImageView newsImageBtn;
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

	
	private LinearLayout intentDelayLayout;
	
//	private StaggeredGridView mSGV;
//	private SGVAdapter mAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTitle("ũ��App");
		setSlidingActionBarEnabled(true);// �����Ƿ��ܹ�ʹ��ActionBar������
		setContentView(R.layout.main);

		initSlidingMenu(savedInstanceState);// ��ʼ�������˵�
		// Ϊ��ť���ü����������������߲˵���
		popupView = getLayoutInflater().inflate(
				R.layout.bus_select, null);
		// popupView.setBackgroundColor(Color.BLUE);
		mPopupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, true);
		mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
		mPopupWindow.update(); 
		/* �����е������ǵ���հ״���ʱ��PopupWindow����ʧ */
		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(),
				(Bitmap) null));
		initWidge();
		new Thread(getDataRunable).start();
	}
	ArrayList<String> plist = new ArrayList<String>();
	 private SharedPreferences sharedPreferences;  
	 private SharedPreferences.Editor editor; 
	 private Runnable getDataRunable = new Runnable() {
	    	@Override
	    	public void run() {
	    		// TODO Auto-generated method stub
	    		getPictureUrls();
	    		handler.sendEmptyMessage(0);
	    	}
	    };
	    private Handler handler = new Handler() {  
	        public void handleMessage(Message msg) {  
	            switch (msg.what) {  
	            case 0:
//	            	Toast.makeText(getApplicationContext(),  plist.size()+" ", Toast.LENGTH_SHORT).show();
//	            	Toast.makeText(getApplicationContext(),  sharedPreferences.getString("20", "0"), Toast.LENGTH_SHORT).show();
	                break;  
	            }  
	        };  
	    };  
	public void getPictureUrls(){
		 sharedPreferences = this.getSharedPreferences("test",Context.MODE_PRIVATE);  
	     editor = sharedPreferences.edit();
	     
	     plist=PictureUnit.catchUrls("http://blog.csdn.net/u013677570/article/details/19290151");
	     if(sharedPreferences.getInt("size",0) != plist.size()){
	    	 int i=1;
		     for(String s:plist){
		    	 editor.putString(""+i, s); 
		    	 i++;
		     }
		     editor.putInt("size", plist.size());
		     editor.commit();
	     }
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
				.replace(R.id.menu_frame, new SideManu()).commit();
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
		mapImageBtn = (ImageView) findViewById(R.id.map_imageBtn);
		gooutImageBtn = (ImageView) findViewById(R.id.goout_imageBtn);
		phoneImageBtn = (ImageView) findViewById(R.id.phone_imageBtn);
		campusLandscapeBtn = (ImageView) findViewById(R.id.campus_imageBtn);
		newsImageBtn = (ImageView) findViewById(R.id.news_and_noticle_imageBtn);
		//functionTextView = (TextView) findViewById(R.id.main_function_textView);
		intentDelayLayout=(LinearLayout) findViewById(R.id.main_bus_wait);
		
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
					R.layout.bus_checkbox_list_item, new String[] {
							"item_tv", "item_cb" }, new int[] {
							R.id.checkbox_list_textView_item,
							R.id.checkbox_list_checkbox_item });
			
			checkboxListView.setAdapter(checkBoxadapter);
			radioButtonListView.setAdapter(radioButtonAdapter);
			
			
			listStr = new ArrayList<String>();
			
			radioButtonListView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					for (int i = 0, j = radioButtonListView.getCount(); i < j; i++) {
						View child = radioButtonListView.getChildAt(i);
						RadioButton rdoBtn = (RadioButton) child
								.findViewById(R.id.radio_btn);
						if(i!=position){
							rdoBtn.setChecked(false);
						}else{
							rdoBtn.setChecked(true);
						}
					}
					RadioListViewHolder holder = (RadioListViewHolder) view.getTag();
					holder.rb.toggle(); 
				}
			});
			
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
				Toast.makeText(getApplicationContext(), "��ѡ��ǰλ�ú���·��", Toast.LENGTH_SHORT).show();
				return;
			}
			//intentDelayLayout.setVisibility(View.VISIBLE);
//			mPopupWindow.dismiss();
			GoOutBean info = new GoOutBean(currentStation, listStr, dir);
//			BusTrackFragment busFragment = new BusTrackFragment();
			Bundle busBundle = new Bundle();
			busBundle.putSerializable("BUSTRACKINFO", info);
			Intent intent=new Intent();
			intent.putExtras(busBundle);
			intent.setClass(Main.this,Bus.class);
			startActivity(intent);
			mPopupWindow.dismiss();
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
			startActivity(new Intent().setClass(Main.this,
					MapActivity.class));
			break;
		case R.id.goout_imageBtn:
			mPopupWindow.showAsDropDown(mapImageBtn);
			runPopWindow();
			break;
		case R.id.phone_imageBtn:
			// �л������õ绰ҳ��
			startActivity(new Intent().setClass(Main.this,
					PhoneNumber.class));
			break;
		case R.id.campus_imageBtn:
			startActivity(new Intent().setClass(Main.this,
					LandScape.class));
			break;
		case R.id.news_and_noticle_imageBtn:
			startActivity(new Intent().setClass(Main.this,
					News.class));
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
