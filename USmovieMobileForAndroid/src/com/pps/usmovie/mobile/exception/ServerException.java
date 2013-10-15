package com.pps.usmovie.mobile.exception;

//表示服务器无法处理上传的数据而出现的各种异常;
public class ServerException extends Exception 
{
	public ServerException(String message)
	{
		super(message);
	}
}