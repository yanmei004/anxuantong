/**    
 * �ļ���MyApplication.java    
 *    
 * �汾��Ϣ��    
 * ���ڣ�2014-10-5    
 * Copyright ���� Corporation 2014     
 * ��Ȩ����    
 *    
 */
package com.example.project.applican;

import java.io.File;

import android.content.Context;

import com.anxuantong.ym.R;
import com.hengtong.library.HTBaseApplication;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
/**
 * 
 * ��Ŀ��ƣ�AnXuanTongProject ����ƣ�MyApplication �������� �����ˣ�yanmei ����ʱ�䣺2014-10-5
 * ����4:17:40 �޸��ˣ�yanmei �޸�ʱ�䣺2014-10-5 ����4:17:40 �޸ı�ע��
 * 
 * @version
 * 
 */
public class MyApplication extends HTBaseApplication {
	private static MyApplication mApp;
	@Override
	public void onCreate() {
		super.onCreate();
		// 初始化ImageLoader
		@SuppressWarnings("deprecation")
		DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.ic_stub) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.ic_error) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.build(); // 创建配置过得DisplayImageOption对象

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).defaultDisplayImageOptions(options)
				.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
//		mApp = (MyApplication) getApplicationContext();
//		initImageLoader(getApplicationContext());
	}
	
	public static MyApplication getSelf() {
		return mApp;
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
	}
	
	/** ��ʼ��ImageLoader */
	public static void initImageLoader(Context context) {
		File cacheDir = StorageUtils.getOwnCacheDirectory(context, "anxuantong/Cache");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration
				.Builder(context)
				.threadPoolSize(3)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCache(new UnlimitedDiscCache(cacheDir))
				.writeDebugLogs() // Remove for release app
				.build();
		ImageLoader.getInstance().init(config);
	}
}
