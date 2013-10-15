package com.pps.usmovie.mobile.layout;

import android.view.View;

public interface IDetailsLayout {

	/**
	 * 生成每一个Item;
	 * @param size 共显示多少条[0:表示全部显示,则不显示"更多"条目]
	 */
	void setWidget(int size);
	/**
	 * 从服务器获取列表数据
	 * @param params(params[0]==videoId)
	 */
	void setListData(String... params);
	void setTitle(String title);
	View getLayout();
}
