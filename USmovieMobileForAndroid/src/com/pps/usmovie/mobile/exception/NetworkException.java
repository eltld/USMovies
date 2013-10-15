package com.pps.usmovie.mobile.exception;

//表示网络系统存在故障;
public class NetworkException extends Exception 
{
	public NetworkException(String message)
	{
		super(message);
	}
}
