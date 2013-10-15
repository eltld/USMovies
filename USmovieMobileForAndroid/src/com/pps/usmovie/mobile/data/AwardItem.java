package com.pps.usmovie.mobile.data;

import java.io.Serializable;

public class AwardItem implements Serializable {

	private String id;
	private String wikiId;
	private String name;
	private String award;
	private String remark;

	public AwardItem() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		String result = "id=" + id + ", wikiId = " + wikiId + ", name = "
				+ name + ", award = " + award + ", remark = " + remark;
		return result;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWikiId() {
		return wikiId;
	}

	public void setWikiId(String wikiId) {
		this.wikiId = wikiId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
