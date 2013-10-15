package com.pps.usmovie.mobile.data;

import java.io.Serializable;

public class ClassItem implements Serializable {
	
	private String classId;
	private String className;
	
	public ClassItem() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		String result = "classId : " + classId+" , className : " + className;
		
		return result;
	}
	
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}
