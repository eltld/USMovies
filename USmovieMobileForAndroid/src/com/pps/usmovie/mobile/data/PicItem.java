package com.pps.usmovie.mobile.data;

import java.io.Serializable;

/**
 * 图片
 * @author zhangxiaole
 *
 */
public class PicItem implements Serializable{

	private String pic_src;
	private String big_pic_src;
	
	public PicItem() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		String result = "pic_src＝ "+pic_src+", big_pic_src= "+big_pic_src;
		return result;
	}

	public String getPic_src() {
		return pic_src;
	}

	public void setPic_src(String pic_src) {
		this.pic_src = pic_src;
	}

	public String getBig_pic_src() {
		return big_pic_src;
	}

	public void setBig_pic_src(String big_pic_src) {
		this.big_pic_src = big_pic_src;
	}
}
