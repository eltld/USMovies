package com.pps.usmovie.mobile.data;

import java.io.Serializable;

/**
 * 出品公司
 * @author zhangxiaole
 *
 */
public class IssuItem implements Serializable{

	private String issuing;
	
	public IssuItem() {
		// TODO Auto-generated constructor stub
	}
	
	public String getIssuing() {
		return issuing;
	}
	
	public void setIssuing(String issuing) {
		this.issuing = issuing;
	}
}
