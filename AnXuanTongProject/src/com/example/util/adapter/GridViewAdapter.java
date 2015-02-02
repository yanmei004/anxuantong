/**    
 * �ļ���GridViewAdapter.java    
 *    
 * �汾��Ϣ��    
 * ���ڣ�2014-10-3    
 * Copyright ���� Corporation 2014     
 * ��Ȩ����    
 *    
 */
package com.example.util.adapter;

import java.util.List;
import com.example.pojo.GridViewBean;
import com.anxuantong.ym.R;
import com.example.util.Options;
import com.example.util.RemoteImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ��Ŀ��ƣ�AnXuanTongProject ����ƣ�GridViewAdapter �������� �����ˣ�yanmei ����ʱ�䣺2014-10-3
 * ����3:27:19 �޸��ˣ�yanmei �޸�ʱ�䣺2014-10-3 ����3:27:19 �޸ı�ע��
 * @version
 */
public class GridViewAdapter extends BaseAdapter {
	private List<GridViewBean> mData = null;
	private LayoutInflater inflater = null;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	private Context context;

	public GridViewAdapter(Context context, List<GridViewBean> pData) {
		super();
		this.mData = pData;
		if(inflater==null){
//		if(inflater!=null){
			inflater = LayoutInflater.from(context);//modify  by  yanmei  20141124
		}
		context=this.context;
		imageLoader = ImageLoader.getInstance();
		options = Options.getListOptions();
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.griditem, null);//LayoutInflater.from(context)
//			convertView = LayoutInflater.from(context).inflate(R.layout.griditem, null);//LayoutInflater.from(context)
			holder = new Holder();
			holder.tv = (TextView) convertView
					.findViewById(R.id.bbs_gridview_text);
			holder.img = (ImageView) convertView.findViewById(R.id.bbs_gridview_img);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.tv.setText(mData.get(position).getTexts()); // ���ñ���
		if (mData.get(position).getImgs() != null) {
			imageLoader.displayImage(mData.get(position).getImgs(), holder.img,options);
		}
		return convertView;
	}

	private class Holder {
		TextView tv;
		ImageView img;
	}

}
