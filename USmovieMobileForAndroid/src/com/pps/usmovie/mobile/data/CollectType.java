package com.pps.usmovie.mobile.data;

import java.util.ArrayList;

import com.pps.usmovie.mobile.common.Constants;

public class CollectType<T> {

	private int typeCode=0;
	private String message;
	private String type;
	
	private T _t;
	private ArrayList<T> data = new ArrayList<T>();
	
	public CollectType() {
		// TODO Auto-generated constructor stub
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getTypeCode() {
		return typeCode;
	}
	
	public void setTypeCode(int typeCode) {
		this.typeCode = typeCode;
		
		if(typeCode == Constants.COLLECT_SUCCESS_CODE){
			setMessage("视频收藏成功!");
		}else if(typeCode == Constants.COLLECT_EXISTENCE_CODE){
			setMessage("该视频已经存在!");
		}else{
			setMessage("视频收藏失败!");
		}
	}
	
	public T get_t() {
		return _t;
	}
	
	public void set_t(T _t) {
		this._t = _t;
	}
	
	public ArrayList<T> getData() {
		return data;
	}
	
	public void setData(ArrayList<T> data) {
		this.data = data;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
}
