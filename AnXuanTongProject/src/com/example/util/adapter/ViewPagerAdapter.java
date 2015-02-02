package com.example.util.adapter;

import java.util.List;

import android.R.string;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * ViewPager适配器
 * 放图片进去的 
 * 新闻列表顶部
 * @author yanmei
 */
public class ViewPagerAdapter extends PagerAdapter
{
    private static String TAG = "yanmei";
    private List<View> mViews;
    public ViewPagerAdapter(List<View> mViews)
    {
        this.mViews = mViews;
    }
    
    @Override
    public int getCount()
    {
        return Integer.MAX_VALUE;
    }
    
    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        
        return view == object;
    }
    /**
     * 适配器给container容器添加视图
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        Log.v(TAG, "instantiateItem" + position);
        position = position % mViews.size();
        container.addView(mViews.get(position), 0);
        return mViews.get(position);
    }
    
    /**
     * 适配器移除container容器中的视图
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object)
    {
        Log.v(TAG, "destroyItem" + position);
        position = position % mViews.size();
        container.removeView(mViews.get(position));        
    }  

}
