package com.example.project;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.menu.ResideMenu;
import com.example.menu.ResideMenuItem;
import com.umeng.analytics.MobclickAgent;
import com.anxuantong.ym.R;
/**
 * ��Ŀ���ƣ�GZRSProject �����ƣ�MyMenuActivity ����������ҳ �����ˣ�yanmei ����ʱ�䣺2014-9-17
 * ����2:52:51 �޸��ˣ�yanmei �޸�ʱ�䣺2014-9-17 ����2:52:51 �޸ı�ע��
 * @version
 */
public class MyMenuActivity extends FragmentActivity implements
		View.OnClickListener {
	private final  String mPageName = "MyMenuActivity";
	private MyMenuActivity mContext;
	private ResideMenu resideMenu;
	private ResideMenuItem itemHome;// ���ŷ���
	private ResideMenuItem itemProfile;// �汾����
	private ResideMenuItem itemGuize;// ����
	private ResideMenuItem itemShow;// ��������
	private TextView mTitleTv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mContext = this;
		MobclickAgent.setDebugMode(true);
//      SDK��ͳ��Fragmentʱ����Ҫ�ر�Activity�Դ���ҳ��ͳ�ƣ�
//		Ȼ����ÿ��ҳ�������¼���ҳ��ͳ�ƵĴ���(���������� onResume �� onPause ��Activity)��
		MobclickAgent.openActivityDurationTrack(false);
//		MobclickAgent.setAutoLocation(true);
//		MobclickAgent.setSessionContinueMillis(1000);
		
		MobclickAgent.updateOnlineConfig(this);
		setUpMenu();
		changeFragment(new HomeFragment());
	}

	private void setUpMenu() {
		mTitleTv=(TextView) findViewById(R.id.main_title_tv);
		// attach to current activity;
		resideMenu = new ResideMenu(this);
		resideMenu.setBackground(R.color.backgroundgreen);
		resideMenu.attachToActivity(this);
		resideMenu.setMenuListener(menuListener);

		// create menu items; ��ʼ����Ϣ�ؼ�
//		itemHome = new ResideMenuItem(this, R.drawable.icon_home, "���ŷ���");
		itemHome = new ResideMenuItem(this, R.drawable.icon_home, "����");
		itemProfile = new ResideMenuItem(this, R.drawable.my_baseinfo, "�汾");
		itemGuize = new ResideMenuItem(this, R.drawable.icon_settings, "����");
		itemShow= new ResideMenuItem(this, R.drawable.icon_profile, "����");
		/***
		 * ����¼�
		 */
		itemHome.setOnClickListener(this);
		itemProfile.setOnClickListener(this);
		itemGuize.setOnClickListener(this);
		itemShow.setOnClickListener(this);
		
		resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemProfile, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemGuize, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(itemShow, ResideMenu.DIRECTION_LEFT);
		findViewById(R.id.title_bar_left_menu).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
					}
				});
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		try{// add by yanmei201410109  try
		return resideMenu.onInterceptTouchEvent(ev)
				|| super.dispatchTouchEvent(ev);
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;// add complete by yanmei201410109
	}

	private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
		@Override
		public void openMenu() {
			// Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT)
			// .show();
		}

		@Override
		public void closeMenu() {
			// Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT)
			// .show();
		}
	};

	private void changeFragment(Fragment targetFragment) {
		resideMenu.clearIgnoredViewList();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.main_fragment, targetFragment, "fragment")
				.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
				.commit();
	}

	@Override
	public void onClick(View view) {
		if (view == itemHome) {
			mTitleTv.setText("����");
			changeFragment(new HomeFragment());
			
		} else if (view == itemProfile) {
			mTitleTv.setText("�汾");
			changeFragment(new VersionFragment());
		}
		else if (view == itemGuize) {
			mTitleTv.setText("����");
			changeFragment(new SettingFragment());
		}
		else if (view == itemShow) {
			mTitleTv.setText("����");
			changeFragment(new AboutUsFragment());
		}
		resideMenu.closeMenu();
	}

	// What good method is to access resideMenu��
	public ResideMenu getResideMenu() {
		return resideMenu;
	}
@Override
protected void onResume() {
	super.onResume();
	MobclickAgent.onPageStart( mPageName );
	MobclickAgent.onResume(mContext);
}
@Override
protected void onPause() {
	super.onPause();
	MobclickAgent.onPageStart( mPageName );
	MobclickAgent.onResume(mContext);
}
private long exitTime = 0;  
@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
	if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){  
	       if((System.currentTimeMillis()-exitTime) > 2000){  
	           Toast.makeText(getApplicationContext(), "�ٰ�һ�κ��˼��˳�����", Toast.LENGTH_SHORT).show();  
	           exitTime = System.currentTimeMillis();  
	       } else {  
	           //�˳����� 
	    	   finish();
	       }  
	       return true;  
	   }  
	   return super.onKeyDown(keyCode, event);  
}
}
