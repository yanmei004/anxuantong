/**    
 * 文件名：DetailInfoActivity.java    
 *    
 * 版本信息：    
 * 日期：2014-9-29    
 * Copyright 足下 Corporation 2014     
 * 版权所有    
 *    
 */
package com.example.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cmcc.gz.lib.controls.pulltorefresh.PullToRefreshBase;
import cmcc.gz.lib.controls.pulltorefresh.PullToRefreshBase.Mode;
import cmcc.gz.lib.controls.pulltorefresh.PullToRefreshBase.OnLastItemVisibleListener;
import cmcc.gz.lib.controls.pulltorefresh.PullToRefreshBase.OnRefreshListener2;
import cmcc.gz.lib.controls.pulltorefresh.PullToRefreshListView;

import com.example.pojo.NewsList;
import com.example.pojo.ViewPaperData;
import com.example.util.ImageCycleView;
import com.example.util.ImageCycleView.ImageCycleViewListener;
import com.example.util.MyProgressDialog;
import com.example.util.Options;
import com.example.util.http.IHttpRequestDriver;
import com.example.util.http.imp.HttpRequestDriverImp;
import com.hengtong.library.async.AsyncHttpResponseHandler;
import com.hengtong.library.enty.HTResponseObject;
import com.hengtong.library.utils.HTNetTool;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.anxuantong.ym.R;
/**
 * 项目名称：GZRSProject 类名称：DetailInfoActivity 类描述： 创建人：yanmei 创建时间：2014-9-29
 * 上午10:45:02 修改人：yanmei 修改时间：2014-9-29 上午10:45:02 修改备注：
 * @version
 */
public class NewsListActivity extends Activity {
	public static  Handler mHandler;//接收消息
	private ImageCycleView mAdView;
	private ArrayList<String> mImageUrl = null;
	private TextView showTv;
	private List<View> mViews;
	private int currentIndex;
	private boolean isContinue = true;
	private Timer timer;
	private static boolean isSleep = true;
	private static final int initPositon = 50000;
	private static int currentPosition = initPositon;
	private int newsListsInt = 0;
	private int newsShufflingListsInt = 1;
	private int pageIndex = 1;
	private int pageSize = 10;
	private String categoryID;
	private String infosTitle = "";
	private List<NewsList> newsLists=new ArrayList<NewsList>() ;//新闻
	private List<NewsList> newsShufflingLists = new ArrayList<NewsList>();
	private MyAdapter adapter;
	private PullToRefreshListView mListView;
	private TextView titleTv, backTv;
	private String strTitle;
	private RelativeLayout mEmptyRelativeLayout;
	private MyProgressDialog progressDialog;
	private LinearLayout mFaileLayout;
	// // 判断是否下拉
	private final int ROWS = 10;
	private final int FIRSTPAGE = 1;
	private int page = FIRSTPAGE;
	/** 加载数据类别 0是刷新加载 1是更多加载 */
	private int l_type = 0;
	private TextView mTitleTv;
	private String mImgUrl,mTvTitleData;
	private Boolean IsIsTop;
	private TextView leftTv, centerTv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_layout);
		init();
		initClick();
		initView();
	}

	private void initClick() {
		GetInfos(0, false);//得到列表
		GetViewPaperInfos(0,false);
	}

	private void init() {
		showTv=(TextView) findViewById(R.id.article_title);
		mImageUrl = new ArrayList<String>();
		mAdView = (ImageCycleView) findViewById(R.id.ad_view);
		mEmptyRelativeLayout = (RelativeLayout) findViewById(R.id.list_head_relative);
		mEmptyRelativeLayout.setVisibility(View.GONE);
		progressDialog = new MyProgressDialog(NewsListActivity.this);
//		progressDialog.showProgessDialog("", "正在加载", true);
		mTitleTv = (TextView) findViewById(R.id.list_head_tv);
		leftTv = (TextView) findViewById(R.id.leftImage);
		leftTv.setVisibility(View.VISIBLE);
		centerTv = (TextView) findViewById(R.id.centerTitle);
		centerTv.setText("新闻列表");
		mFaileLayout = (LinearLayout) findViewById(R.id.act_faile_layout);
		mFaileLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GetInfos(0, false);
			}
		});
		backTv = (TextView) findViewById(R.id.leftImage);
		backTv.setVisibility(View.VISIBLE);
		titleTv = (TextView) findViewById(R.id.centerTitle);
		mListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		mListView.setScrollingWhileRefreshingEnabled(true);// 刷新数据时不允许滚动屏幕
		mListView.setPressed(true);
		mListView.setMode(Mode.PULL_UP_TO_REFRESH);// 只能上拉刷新
		mListView.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				new AddDataTask().execute();
				
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				new AppendDataTask().execute();
			}
		});
		mListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
			}
		});
		ListView actualListView = mListView.getRefreshableView();
		registerForContextMenu(actualListView);
		categoryID = getIntent().getStringExtra("categoryID");
		infosTitle = getIntent().getStringExtra("infosTitle");
		titleTv.setText("" + infosTitle);
		backTv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	/**
	 * initView(下来刷新)
	 */
	private void initView() {
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(NewsListActivity.this,
						NewsDetailActivity.class);
				intent.putExtra("infosID", newsLists.get(arg2 - 1).getInfosID());// moify
				intent.putExtra("StrUpdateDateTime", newsLists.get(arg2 - 1)
						.getStrUpdateDateTime());
				startActivity(intent);
			}
		});
	}
	
	/**
	 * GetInfosCategory(得到viewpaper新闻分类列表)
	 */
	private void GetViewPaperInfos(int pType, final Boolean isShowDialog) {
		final IHttpRequestDriver request = HttpRequestDriverImp.getSingleInstance(this);
		int mPage;
		if (pType == 0) {
			mPage = 1;
		} else {
			mPage = page;
		}
		l_type = pType;
       //得到列表轮播新闻
		request.doGetInfos(categoryID, newsShufflingListsInt, "", mPage, ROWS,
				new AsyncHttpResponseHandler() {
					@Override
					public void onStart() {
						super.onStart();
						if (isShowDialog) {
							progressDialog.showProgessDialog("", "正在加载", true);
						}
					}

					@Override
					public void onFinish() {
						super.onFinish();
						if (isShowDialog) {
							progressDialog.dismissProgessDialog();
						}
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						mListView.onRefreshComplete();
						progressDialog.dismissProgessDialog();
						HTResponseObject htResponseObject = request.handlerParseJson(new String(responseBody).toString());
						if (htResponseObject.ismSuccess()) {// 成功
							if (htResponseObject.getmResult() == null)
								return;
							String result = htResponseObject.getmResult().toString();
							parseJsonViewPaper(result,newsShufflingLists);
							mAdView.setImageResources(mImageUrl, mAdCycleViewListener);
							if(newsShufflingLists.size()==0||newsShufflingLists.isEmpty()){
								mAdView.setVisibility(View.GONE);
								showTv.setVisibility(View.GONE);
							}else {
								mAdView.setVisibility(View.VISIBLE);
								showTv.setVisibility(View.VISIBLE);
								showTv.setText(""+newsShufflingLists.get(0).getInfosTitle());
							}
							mHandler=new Handler(){
								public void handleMessage(android.os.Message msg) {
									try {
										showTv.setText(""+newsShufflingLists.get(msg.what).getInfosTitle());
									}
									catch (Exception e) {
										e.printStackTrace();
									}
									
								};
							};
//							newsLists=new ArrayList<NewsList>();//add 2015 01 20
//							adapter = new MyAdapter(NewsListActivity.this,newsLists);
//							mListView.setAdapter(adapter);
//							if (newsLists.size() == 0 || newsLists.equals("")) {
//								mEmptyRelativeLayout.setVisibility(View.VISIBLE);
//								mListView.setVisibility(View.GONE);
//								mFaileLayout.setVisibility(View.GONE);
//							}
//							adapter.notifyDataSetChanged();
//							mListView.invalidate();
//							mListView.setSelected(false);
//							mListView.onRefreshComplete();
						} else {
							Toast.makeText(NewsListActivity.this,"错误信息=" + htResponseObject.getmError(),Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						if (!HTNetTool.checkNetState(NewsListActivity.this)) {
							mListView.setVisibility(View.GONE);
							mEmptyRelativeLayout.setVisibility(View.GONE);
							mFaileLayout.setVisibility(View.VISIBLE);
							return;
						}
						progressDialog.dismissProgessDialog();
					}
				});
	}
	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {

		@Override
		public void onImageClick(int position, View imageView) {
			
			Intent intent = new Intent(NewsListActivity.this,NewsDetailActivity.class);
			intent.putExtra("infosID", newsShufflingLists.get(position).getInfosID());// moify
			intent.putExtra("StrUpdateDateTime", newsShufflingLists.get(position).getStrUpdateDateTime());
			startActivity(intent);
		}

		@Override
		public void displayImage(String imageURL, ImageView imageView) {
			ImageLoader.getInstance().displayImage(imageURL, imageView);
		}
	};
	
	/**
	 * GetInfosCategory(得到新闻分类列表)
	 */
	private void GetInfos(int pType, final Boolean isShowDialog) {
		final IHttpRequestDriver request = HttpRequestDriverImp.getSingleInstance(this);
		int mPage;
		if (pType == 0) {
			mPage = 1;
		} else {
			mPage = page;
		}
		l_type = pType;
        //得到列表新闻
		request.doGetInfos(categoryID, newsListsInt, "", mPage, ROWS,
				new AsyncHttpResponseHandler() {
					@Override
					public void onStart() {
						super.onStart();
//						if (isShowDialog) {
							progressDialog.showProgessDialog("", "", true);
//						}
					}

					@Override
					public void onFinish() {
						super.onFinish();
//						if (isShowDialog) {
							progressDialog.dismissProgessDialog();
//						}
					}

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						mListView.onRefreshComplete();
						progressDialog.dismissProgessDialog();
						HTResponseObject htResponseObject = request
								.handlerParseJson(new String(responseBody)
										.toString());
						if (htResponseObject.ismSuccess()) {// 成功
							if (htResponseObject.getmResult() == null)
								return;
							String result = htResponseObject.getmResult().toString();
//							newsLists=new ArrayList<NewsList>();
							newsLists=parseJson(result,newsLists);
							titleTv.setText("" + infosTitle);
							
							if (newsLists.size() == 0 || newsLists.equals("")) {
								mEmptyRelativeLayout.setVisibility(View.VISIBLE);
								mListView.setVisibility(View.GONE);
								mFaileLayout.setVisibility(View.GONE);
							}else {
								mListView.setVisibility(View.VISIBLE);
								mFaileLayout.setVisibility(View.GONE);
								mEmptyRelativeLayout.setVisibility(View.GONE);
								
								adapter = new MyAdapter(NewsListActivity.this,newsLists);
								mListView.setAdapter(adapter);	
								adapter.notifyDataSetChanged();
								mListView.invalidate();
								mListView.setSelected(false);
								mListView.onRefreshComplete();
							}
						} else {
							Toast.makeText(NewsListActivity.this,"错误信息=" + htResponseObject.getmError(),Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						if (!HTNetTool.checkNetState(NewsListActivity.this)) {
							mListView.setVisibility(View.GONE);
							mEmptyRelativeLayout.setVisibility(View.GONE);
							mFaileLayout.setVisibility(View.VISIBLE);
							return;
						}
						progressDialog.dismissProgessDialog();
					}
				});
	}
	// 解析新闻列表
	private List<NewsList> parseJson(String result,List<NewsList> lists) {
		try {
			int refreshnum = 0; // 比如刷新时，有刷新出两条，那么先第一条放在前面，第二条放在后面
			NewsList bean;
			JSONArray newsJson = new JSONArray(result);
			if (newsJson != null) {
				for (int i = 0; i < newsJson.length(); i++) {// 订车
					JSONObject json = (JSONObject) newsJson.opt(i);
					bean = new NewsList(json);
					IsIsTop = bean.isIsIsTop();
					mImgUrl = bean.getPicUrl();
//					if (IsIsTop) {
//						addHeadView();
//					}
					if (l_type == 0) {// 刷新
						lists.add(refreshnum, bean);
						refreshnum++;
					} else {
						lists.add(bean);
					}

				}
			}
//			titleTv.setText("" + infosTitle);
//			adapter = new MyAdapter(NewsListActivity.this,lists);
//			mListView.setAdapter(adapter);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return lists;
	}
	
//	// 解析新闻列表
//	private void parseJson(String result,List<NewsList> lists) {
//		try {
//			int refreshnum = 0; // 比如刷新时，有刷新出两条，那么先第一条放在前面，第二条放在后面
//			NewsList bean;
//			JSONArray newsJson = new JSONArray(result);
//			if (newsJson != null) {
//				for (int i = 0; i < newsJson.length(); i++) {// 订车
//					JSONObject json = (JSONObject) newsJson.opt(i);
//					bean = new NewsList(json);
//					IsIsTop = bean.isIsIsTop();
//					mImgUrl = bean.getPicUrl();
////					if (IsIsTop) {
////						addHeadView();
////					}
//					if (l_type == 0) {// 刷新
//						lists.add(refreshnum, bean);
//						refreshnum++;
//					} else {
//						lists.add(bean);
//					}
//
//				}
//			}
////			titleTv.setText("" + infosTitle);
////			adapter = new MyAdapter(NewsListActivity.this,lists);
////			mListView.setAdapter(adapter);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//	}

	//解析viewpaper
	// 解析新闻列表
		private void parseJsonViewPaper(String result,List<NewsList> lists) {
			try {
				int refreshnum = 0; // 比如刷新时，有刷新出两条，那么先第一条放在前面，第二条放在后面
				NewsList bean;
				JSONArray newsJson = new JSONArray(result);
				if (newsJson != null) {
					for (int i = 0; i < newsJson.length(); i++) {// 订车
						JSONObject json = (JSONObject) newsJson.opt(i);
						bean = new NewsList(json);
						IsIsTop = bean.isIsIsTop();
						mImgUrl = bean.getPicUrl();
						mTvTitleData=bean.getCategoryName();////modify
						mImageUrl.add(mImgUrl);//modify
//						mImageUrl.add(new ViewPaperData(mImgUrl, mTvTitleData));////modify
						if (IsIsTop) {
//							addHeadView();
						}
						if (l_type == 0) {// 刷新
							lists.add(refreshnum, bean);
							refreshnum++;
						} else {
							lists.add(bean);
						}

					}
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	private class MyAdapter extends BaseAdapter {
		private Context mContext;
		private List<NewsList> lists;
		private ImageLoader imageLoader;
		private DisplayImageOptions options;

		public void setLists(List<NewsList> list) {
			this.lists = list;
		}

		public Context getmContext() {
			return mContext;
		}

		public void setmContext(Context mContext) {
			this.mContext = mContext;
		}

		public List<NewsList> getLists() {
			return lists;
		}

		public MyAdapter(Context mContext, List<NewsList> list) {
			super();
			this.mContext = mContext;
			this.lists = list;
			imageLoader = ImageLoader.getInstance();
			options = Options.getListOptions();
		}

		@Override
		public int getCount() {
			if (lists == null) {
				lists = new ArrayList<NewsList>();
			}
			return lists.size();
		}

		@Override
		public Object getItem(int arg0) {
			return lists.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				LayoutInflater inflater = (LayoutInflater) mContext
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.news_listitem, null);
				viewHolder.titleTv = (TextView) convertView
						.findViewById(R.id.title);
				viewHolder.introductionTv = (TextView) convertView
						.findViewById(R.id.introduction);
				viewHolder.sourceTv = (TextView) convertView
						.findViewById(R.id.source);
				viewHolder.createTimeTv = (TextView) convertView
						.findViewById(R.id.create_time);
				viewHolder.wapThumbImg = (ImageView) convertView
						.findViewById(R.id.wap_thumb);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			NewsList newsList = lists.get(position);
			String wap_thumb = newsList.getPicUrl();
			viewHolder.titleTv.setText(newsList.getInfosTitle());
			viewHolder.introductionTv.setText(newsList.getIntroduction());
			viewHolder.sourceTv.setText(newsList.getSource());

			viewHolder.createTimeTv.setText(newsList.getStrUpdateDateTime()
					.substring(0, 10));
			if (newsList.getPicUrl() != null
					|| !"".equals(newsList.getPicUrl())) { // 头像URL不为空，则加载头像
				DisplayImageOptions options = new DisplayImageOptions.Builder()
						.cacheInMemory(true).cacheOnDisc(true)
						.showStubImage(R.drawable.bbs_default_picbg)
						.showImageForEmptyUri(R.drawable.bbs_default_picbg)
						.showImageOnFail(R.drawable.bbs_default_picbg).build();
				ImageLoader.getInstance().displayImage(newsList.getPicUrl(),
						viewHolder.wapThumbImg, options);
			}
			return convertView;
		}
	}

	public static class ViewHolder {
		TextView titleTv, introductionTv, sourceTv, createTimeTv;
		ImageView wapThumbImg;
	}

	private class AddDataTask extends AsyncTask<Void, Void, String[]> {
		@Override
		protected String[] doInBackground(Void... params) {
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {// 刷新
			page = FIRSTPAGE;
			if (page != 1) {
				page--;
			}
			GetInfos(0, false);
			super.onPostExecute(result);
		}
	}

	private class AppendDataTask extends AsyncTask<Void, Void, String[]> {
		@Override
		protected String[] doInBackground(Void... params) {
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {// 加载
			page++;
			GetInfos(1, false);
			super.onPostExecute(result);
		}
	}

	/**
	 * 设置线程间隔
	 */
	private void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * viewpager页面变化的监听器
	 * 
	 * @author user
	 * 
	 */
	class MyPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {

		}

		@Override
		public void onPageSelected(int position) {
			currentPosition = position;
		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}

	}

	/**
	 * 监听手势监听器
	 * 
	 * @author user
	 * 
	 */
	class MyTouchListener implements OnTouchListener {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
			case MotionEvent.ACTION_MOVE:
				isContinue = false;
				break;
			case MotionEvent.ACTION_UP:
			default:
				isContinue = true;
				isSleep = true;
				break;
			}
			return false;
		}

	}
}
