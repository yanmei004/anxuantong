package com.example.util;

import android.graphics.Bitmap;

import com.anxuantong.ym.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class Options {
	/** �����б����õ���ͼƬ�������� */
	public static DisplayImageOptions getListOptions() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
//				// ����ͼƬ�������ڼ���ʾ��ͼƬ
				.showImageOnLoading(R.drawable.bbs_default_picbg)
//				// ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
				.showImageForEmptyUri(R.drawable.bbs_default_picbg)
//				// ����ͼƬ����/�������д���ʱ����ʾ��ͼƬ
				.showImageOnFail(R.drawable.bg_error)
				//.cacheInMemory(true)
				// �������ص�ͼƬ�Ƿ񻺴����ڴ���
				.cacheOnDisc(true)
				// �������ص�ͼƬ�Ƿ񻺴���SD����
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)// ����ͼƬ����εı��뷽ʽ��ʾ
				.bitmapConfig(Bitmap.Config.RGB_565)// ����ͼƬ�Ľ�������
				// .decodingOptions(android.graphics.BitmapFactory.Options
				// decodingOptions)//����ͼƬ�Ľ�������
				.considerExifParams(true)
				// ����ͼƬ����ǰ���ӳ�
				// .delayBeforeLoading(int delayInMillis)//int
				// delayInMillisΪ�����õ��ӳ�ʱ��
				// ����ͼƬ���뻺��ǰ����bitmap��������
				// ��preProcessor(BitmapProcessor preProcessor)
				.resetViewBeforeLoading(true)// ����ͼƬ������ǰ�Ƿ����ã���λ
				// .displayer(new RoundedBitmapDisplayer(20))//�Ƿ�����ΪԲ�ǣ�����Ϊ����
				.displayer(new FadeInBitmapDisplayer(100))// ����
				.build();
		 
		return options;
	}
}
