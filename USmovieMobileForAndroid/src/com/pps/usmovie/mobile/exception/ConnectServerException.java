package com.pps.usmovie.mobile.exception;

import org.apache.http.HttpStatus;


/**
 * 网络连接异常处理�?有关网络异常的类放到此处处理
 * 
 */
public class ConnectServerException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6928908300774537820L;
	public static final int TIMEOUT 		=	-1;
	public static final int INTERRUPUTEDIO 	=	-2;
	public static final int CLIENTPROTOCOL 	= 	-3;
	public static final int UNKNOWN 		= 	-4;
	public static final int JSONEXCEPTION 	=  	-5;
	public static final int NONET 			= 	-6;
	public static final int IOEXCEPTION		= 	-7;
	public static final int UnsupportedEncodingException		= 	-8;

	public String cause;
	 
	public ConnectServerException(String message) {
		//		SSMLogger.info(message);
		if( message.contains("Broken pipe") ){
			cause = message;
		}else if( message.contains("timed out")){
			cause= "请求超时";
		}else if( message.contains("closed")){
			cause= "连接已关闭，请重试";
		}else if( message.contains("refused") ){
			cause= "连接被拒绝！";
		}else if( message.contains("reset by peer")|| message.contains("reset")){
			cause= "发送数据时但连接已关闭";
		}else if( message.contains("Address already in use") ){
			cause= "端口被占用";
		}else if( message.contains("No route to host") ){
			cause= "找不到路由";
		}else if( message.contains("UnknownHostException") ){
			cause= "无法找到主机地址";
		}else if(message.contains("Network unreachable")){
			cause= "请检查网络连接！";
		}else{
			cause= "连接错误！";
		}
	}
	
	public ConnectServerException(int code) {
		System.out.println("错误代码:"+code);
		switch (code) {
		case NONET:
			cause = "没有可用的网络连接";
			break;
		case TIMEOUT:
			cause = "请求超时!";
			break;
		case INTERRUPUTEDIO:
			cause = "网络中断!";
			break;
		case CLIENTPROTOCOL:
			cause = "请求协议错误！";
			break;
		case HttpStatus.SC_INTERNAL_SERVER_ERROR:
			cause = "服务器遇到了意料不到的情况!";
			break;
		case HttpStatus.SC_NOT_IMPLEMENTED:
			cause = "服务器处理出错!";
			break;
		case HttpStatus.SC_BAD_GATEWAY:
			cause = "服务器用作网关或代理服务器时收到了无效响应!";
			break;
		case HttpStatus.SC_SERVICE_UNAVAILABLE:
			cause = "服务不可用!";
			break;
		case HttpStatus.SC_GATEWAY_TIMEOUT:
			cause ="服务器无应答!";
			break;
		case HttpStatus.SC_HTTP_VERSION_NOT_SUPPORTED:
			cause = "请求错误！";
			break;
		case HttpStatus.SC_UNAUTHORIZED:
			cause = "拒绝访问!";
			break;
		case HttpStatus.SC_NOT_FOUND:
			cause = "无法找到指定位置的资源!";
			break;
		case HttpStatus.SC_EXPECTATION_FAILED:
			cause = "请求失败";
			break;
		case JSONEXCEPTION:
			cause = "服务器响应错误！";//JSON异常
			break;
		case IOEXCEPTION:
			cause = "请检查网络连接！";//O读写错误！
			break;
		case UnsupportedEncodingException:
			cause = "不支持的编码类型！";//JSON异常
			break;
		case  HttpStatus.SC_FORBIDDEN:
			cause = "403 Forbidden ";//JSON异常,错误代码:
			break;
		default:
			cause ="错误响应或服务器无响应";
			break;
		}
	}
	
	public String getErrorMessage() {
		return cause;
	}


}
