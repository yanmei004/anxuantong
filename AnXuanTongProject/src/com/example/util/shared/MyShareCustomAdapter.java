package com.example.util.shared;

import java.util.List;

import com.anxuantong.ym.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * 默认的自定义分享列表适配器
 * @author lin
 *
 */
public class MyShareCustomAdapter extends BaseAdapter {

	private Context mContext ;
	private LayoutInflater mInflater;
	private List<AppInfo> list;
	public MyShareCustomAdapter(Context mC){
		mInflater = LayoutInflater.from(mC);
		mContext = mC;
	}
	
	public MyShareCustomAdapter(Context mC,List<AppInfo> list){
		mInflater = LayoutInflater.from(mC);
		mContext = mC;
		this.list = list;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		return list.get(index);
	}

	@Override
	public long getItemId(int index) {
		// TODO Auto-generated method stub
		return index;
	}
	

	@Override
	public View getView(int index, View view, ViewGroup parent) {
		view = mInflater.inflate(R.layout.my_shared_item, null);
		HolderView hv = new HolderView();
		hv.share_item_icon = (ImageView)view.findViewById(R.id.share_item_icon);
		hv.share_item_name = (TextView)view.findViewById(R.id.share_item_name);
		
		AppInfo item = (AppInfo)getItem(index);
		
		hv.share_item_icon.setImageDrawable(item.getAppIcon());
		hv.share_item_name.setText(item.getAppName());
		
		return view;
	}

	class HolderView{
		public ImageView share_item_icon;
		public TextView share_item_name ;

	}
}
