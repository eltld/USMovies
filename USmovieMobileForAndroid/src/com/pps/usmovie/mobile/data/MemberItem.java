package com.pps.usmovie.mobile.data;

import java.io.Serializable;

/**
 * 所有成员项[导演,演员,编辑,作者...]
 * @author zhangxiaole
 *
 */
public class MemberItem implements Serializable{

	private String id="";
	private String zh_name;
	private String en_name;
	private String pic_src;
	
	/**
	 * 用于判断是否是唯一成员
	 */
	private boolean isSingle = true;
	/**类别-用于显示导演\编辑\制片等*/
	private String classify;
	
	public MemberItem() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		String result = "id="+id+",zh_name＝ "+zh_name+", en_name= "+en_name+", pic_src= "+pic_src;
		return result;
	}

	public String getZh_name() {
		return zh_name;
	}

	public void setZh_name(String zh_name) {
		this.zh_name = zh_name;
	}

	public String getEn_name() {
		return en_name;
	}

	public void setEn_name(String en_name) {
		this.en_name = en_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPic_src() {
		return pic_src;
	}

	public void setPic_src(String pic_src) {
		this.pic_src = pic_src;
	}
	
	public void setSingle(boolean isSingle) {
		this.isSingle = isSingle;
	}
	
	public boolean getSingle(){
		return isSingle;
	}
	
	public String getClassify() {
		return classify;
	}
	
	public void setClassify(String classify) {
		this.classify = classify;
	}
}
