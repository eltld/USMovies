package com.pps.usmovie.mobile.data;

public class TextLayoutItem {

	private String title;
	
	private String leftText;
	private String leftSubText;
	private String rightText;
	private boolean isArrow = false;
	
	public TextLayoutItem() {
		// TODO Auto-generated constructor stub
	}
	public TextLayoutItem(String leftText,String leftSubText,String rightText,boolean isArrow) {
		this.leftText= leftText;
		this.leftSubText = leftSubText;
		this.rightText = rightText;
		this.isArrow = isArrow;
	}
	
	public TextLayoutItem(String leftText ,String rightText,boolean isArrow) {
		this.leftText= leftText;
		this.rightText = rightText;
		this.isArrow = isArrow;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLeftText() {
		return leftText;
	}

	public void setLeftText(String leftText) {
		this.leftText = leftText;
	}

	public String getLeftSubText() {
		return leftSubText;
	}

	public void setLeftSubText(String leftSubText) {
		this.leftSubText = leftSubText;
	}

	public String getRightText() {
		return rightText;
	}

	public void setRightText(String rightText) {
		this.rightText = rightText;
	}

	public boolean isArrow() {
		return isArrow;
	}

	public void setArrow(boolean isArrow) {
		this.isArrow = isArrow;
	}
}
