package com.example.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import com.anxuantong.ym.R;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.menu.ResideMenu;
import com.example.pojo.GridViewBean;
import com.example.util.MyProgressDialog;
import com.example.util.StringUtils;
import com.example.util.adapter.GridViewAdapter;
import com.example.util.http.IHttpRequestDriver;
import com.example.util.http.imp.HttpRequestDriverImp;
import com.hengtong.library.async.AsyncHttpResponseHandler;
import com.hengtong.library.enty.HTResponseObject;

/**
 * User: yanmei update 20140917 Mail: 1095553746@qq.com
 */
@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment {
	private MyProgressDialog progressDialog;
	private int pageIndex = 1;
	private int pageSize = 10;
	private String text = null;
	private GridView mainGv;
	private GridViewAdapter adapter;
	private List<GridViewBean> mBbsGridViewBean = new ArrayList<GridViewBean>(); // С����̳����б�
	private LinearLayout loadingLayout;
	private LinearLayout failLoadingLayout;
	private LinearLayout Linear_above_toHome;
	private String reFreshTime = "";
	private View parentView;
	private ResideMenu resideMenu;
	private TextView mEmptyTv;
	public HomeFragment() {
		super();
		this.text = "welcome";
	}

	public HomeFragment(String text) {
		this.text = text;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	@Override
	public void onResume() {
		super.onResume();
		initWight();
	}

	private void initWight() {
		MyMenuActivity parentActivity = (MyMenuActivity) getActivity();
		parentView = getView();
		resideMenu = parentActivity.getResideMenu();
		mEmptyTv=(TextView) parentView.findViewById(R.id.detail_ctivity_tv);
		mainGv = (GridView) parentView.findViewById(R.id.main_gridView);
		mainGv.setSelector(new ColorDrawable(Color.TRANSPARENT));
		loadingLayout = (LinearLayout) parentView.findViewById(R.id.view_loading);
		failLoadingLayout = (LinearLayout) parentView.findViewById(R.id.view_load_fail);
		loadingLayout.setVisibility(View.GONE);
		failLoadingLayout.setVisibility(View.GONE);
		initView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		parentView = inflater.inflate(R.layout.homeactivity, container, false);
		progressDialog = new MyProgressDialog(getActivity());
		return parentView;
	}

	private void initData() {
		GetInfosCategory();
	}

	/**
	 * GetInfosCategory(�õ����ŷ����б�)
	 */
	private void GetInfosCategory() {
		final IHttpRequestDriver request = HttpRequestDriverImp.getSingleInstance(getActivity());
		request.doGetInfosCategory(pageIndex, pageSize,
				new AsyncHttpResponseHandler() {
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
						HTResponseObject htResponseObject=request.handlerParseJson(new String(responseBody).toString());
                        if (htResponseObject.ismSuccess()) {// �ɹ�
                        	if (htResponseObject.getmResult() == null)
        						return;
                        		String result=htResponseObject.getmResult().toString();
                        		parseJson(result);//������
                        		if(!mBbsGridViewBean.equals("")||mBbsGridViewBean!=null){
                        			mainGv.setAdapter(new GridViewAdapter(getActivity(), mBbsGridViewBean));
                        		}
                        		mainGv.setAdapter(new GridViewAdapter(getActivity(), mBbsGridViewBean));
                        		if(mBbsGridViewBean.size()==0||mBbsGridViewBean==null){
            						mEmptyTv.setVisibility(View.VISIBLE);
            					}
                        	}else {
								Toast.makeText(getActivity(), ""+htResponseObject.getmError(), Toast.LENGTH_SHORT).show();
							}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
					}
				});
	}

	// ���������б�
	private void parseJson(String result) {
		try {
			JSONArray newsJson = new JSONArray(result);
			if (newsJson != null) {
				for (int i = 0; i < newsJson.length(); i++) {// ����
					JSONObject json = (JSONObject) newsJson.opt(i);
					GridViewBean bean = new GridViewBean(json);
					mBbsGridViewBean.add(bean);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void setUpViewsNew() {
		MyMenuActivity parentActivity = (MyMenuActivity) getActivity();
		parentView = getView();
		resideMenu = parentActivity.getResideMenu();
		mEmptyTv=(TextView) parentView.findViewById(R.id.detail_ctivity_tv);
		mainGv = (GridView) parentView.findViewById(R.id.main_gridView);
		mainGv.setSelector(new ColorDrawable(Color.TRANSPARENT));
		initData();
		loadingLayout = (LinearLayout) parentView
				.findViewById(R.id.view_loading);
		failLoadingLayout = (LinearLayout) parentView
				.findViewById(R.id.view_load_fail);
		loadingLayout.setVisibility(View.GONE);
		failLoadingLayout.setVisibility(View.GONE);
		initView();
	}

	/**
	 * initView(��ʼ���ؼ�)
	 */
	private void initView() {
		failLoadingLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mainGv.setVisibility(View.GONE);
				loadingLayout.setVisibility(View.VISIBLE);
				failLoadingLayout.setVisibility(View.GONE);
			}
		});
		reFreshTime = StringUtils.getStringDate(String.valueOf(System
				.currentTimeMillis()));
		mainGv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				Intent intent = new Intent(getActivity(),NewsListActivity.class);
//				Intent intent = new Intent(getActivity(),NewsListTestActivity.class);//viewpaper测试类
				intent.putExtra("categoryID", mBbsGridViewBean.get(position).getCategoryID());
				intent.putExtra("infosTitle", mBbsGridViewBean.get(position).getTexts());
				startActivity(intent);
			}
		});
	}

	private void setUpViews() {
		MyMenuActivity parentActivity = (MyMenuActivity) getActivity();
		resideMenu = parentActivity.getResideMenu();
		parentView.findViewById(R.id.btn_open_menu).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
					}
				});
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setUpViewsNew();
	}

	private void stopRequestData() {
		// mListView.
		// mListView.stopRefresh();
		// mListView.stopLoadMore();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
	
    public boolean dispatchKeyEvent(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK&& event.getAction() == KeyEvent.ACTION_DOWN) {
            }
    		return false;
    }
	
}
