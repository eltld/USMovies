package com.pps.usmovie.mobile.data;

public class VideoMenuItem {

	private String id;
	private String name;
	private String status;
	
	public VideoMenuItem() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		String result = "id="+id+ ", name=" + name + ", status = "+ status;
		return result;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
