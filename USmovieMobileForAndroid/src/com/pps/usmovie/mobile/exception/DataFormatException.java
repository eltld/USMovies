package com.pps.usmovie.mobile.exception;

//表示服务器返回的数据格式错误;
public class DataFormatException  extends Exception
{
	public DataFormatException(String message)
	{
		super(message);
	}
}
