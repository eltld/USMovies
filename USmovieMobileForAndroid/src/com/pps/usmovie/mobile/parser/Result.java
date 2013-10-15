package com.pps.usmovie.mobile.parser;

import java.util.ArrayList;

public class Result<T> {

	private int ret;

	private String message = "";
	private String total_count;
	private String per_page;
	private int curr_page;
	private int total_page;
	private int nearer_page;
	private String tag;

	private T _t;

	private ArrayList<T> data = new ArrayList<T>();

	public Result() {
	}

	public int getRet() {
		return ret;
	}
	
	public void setRet(int ret) {
		this.ret = ret;
	}
	
	public String getMessage() {
	/**　Json字符串
		　　形如 {"ret":203}
		　　200 非获取列表操作动作成功
		　　201 失败
		　　202 传递参数有误
		　　203 加密不对
		　　204 其他错误*/
		if(ret == 201){
			setMessage("获取失败失败");
		}else if(ret == 202){
			setMessage("传递参数有误");
		}else if(ret == 203){
			setMessage("参数加密不对");
		}else if(ret == 204){
			setMessage("其他错误");
		}
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

	public void addList(T _t) {
		data.add(_t);
	}

	public ArrayList<T> getList() {
		return data;
	}

	public T get() {
		return _t;
	}

	public void set(T _t) {
		this._t = _t;
	}

	public String getTotal_count() {
		return total_count;
	}

	public void setTotal_count(String total_count) {
		this.total_count = total_count;
	}

	public String getPer_page() {
		return per_page;
	}

	public void setPer_page(String per_page) {
		this.per_page = per_page;
	}

	public int getCurr_page() {
		return curr_page;
	}

	public void setCurr_page(int curr_page) {
		this.curr_page = curr_page;
	}

	public int getTotal_page() {
		return total_page;
	}

	public void setTotal_page(int total_page) {
		this.total_page = total_page;
	}

	public int getNearer_page() {
		return nearer_page;
	}

	public void setNearer_page(int nearer_page) {
		this.nearer_page = nearer_page;
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
	
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
}
