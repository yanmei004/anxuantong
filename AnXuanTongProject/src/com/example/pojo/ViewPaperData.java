package com.example.pojo;

public class ViewPaperData {
	private String imgUrl;
	private String tvData;
	public ViewPaperData() {
		super();
	}
	public ViewPaperData(String imgUrl, String tvData) {
		super();
		this.imgUrl = imgUrl;
		this.tvData = tvData;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getTvData() {
		return tvData;
	}
	public void setTvData(String tvData) {
		this.tvData = tvData;
	}
	
	@Override
	public String toString() {
		return "ViewPaperData [imgUrl=" + imgUrl + ", tvData=" + tvData + "]";
	}
	

}
