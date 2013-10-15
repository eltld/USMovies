package com.pps.usmovie.mobile.exception;

import java.io.PrintStream;
import java.io.PrintWriter;



/***
 * 基础异常
 * 根据该类进行数据的异常捕
 *
 */
public class USdramaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String cause;
	private static boolean DEBUG = true;
	
	public USdramaException() {
	}
	
	public USdramaException(String error) {
		this.cause = error;
	}
	
	@Override
	public String getMessage() {
		return super.getMessage();
	}

	@Override
	public void printStackTrace() {
		if(DEBUG){
			super.printStackTrace();
		}
	}
	
	
	public  String getCauseMsg(){
		return cause;
	}

	public void setCause(String error) {
		this.cause = error;
	}
	
	@Override
	public String toString() {
		return " details message:"+this.getMessage();
	}
	@Override
	public void printStackTrace(PrintStream err) {
		if(DEBUG){
			super.printStackTrace(err);
		}
	}
	
	@Override
	public void printStackTrace(PrintWriter err) {
		if(DEBUG){
			super.printStackTrace(err);
		}
	}
//	/**
//	 * 获得堆栈内容
//	 * @param t
//	 * @return 
//	 */
//	public static String getStackTrace(Throwable t)
//	{
//		StringWriter sw = new StringWriter();
//		PrintWriter pw = new PrintWriter(sw, true);
//		t.printStackTrace(pw);
//		pw.flush();
//		sw.flush();
//		return sw.toString();
//	}
}
