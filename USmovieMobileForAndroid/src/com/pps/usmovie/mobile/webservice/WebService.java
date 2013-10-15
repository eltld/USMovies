package com.pps.usmovie.mobile.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.pps.usmovie.mobile.exception.ConnectServerException;
import com.pps.usmovie.mobile.util.Log;

public class WebService {

	private static String TAG = "WorkItemWebService";


	public static String doGet(Set<Entry<String, String>> list,
			String sendCode_URL) throws ConnectServerException {
		InputStream is = null;
		BufferedReader br = null;
		String url;
		if(list == null || list.equals(null)){
			 url = sendCode_URL;
		}else{
			 url = getUrl(list, sendCode_URL);
		}
		Log.d(TAG, "------> URL : " + url);

		HttpGet httpGet = new HttpGet(url);
		HttpParams httpParams = httpGet.getParams();
		httpParams.getParameter("true");

		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
				br = new BufferedReader(new InputStreamReader(is));
				String response = "";
				String readLine = null;
				while ((readLine = br.readLine()) != null) {
					response = response + readLine;
				}
				Log.d(TAG, "----->response : " + response);

				httpEntity.consumeContent();
				is.close();
				br.close();
				return response;
			} else {
				Log.w(TAG, "----->getStatusCode != 200");
			}
		} catch(SocketException e){
			throw new ConnectServerException(e.getMessage());
		} catch(ConnectTimeoutException e){
			throw new ConnectServerException(ConnectServerException.TIMEOUT);
		} catch(InterruptedIOException e){
			throw new ConnectServerException(ConnectServerException.INTERRUPUTEDIO );
		} catch (ClientProtocolException e) {
			throw new ConnectServerException(ConnectServerException.CLIENTPROTOCOL);
		} 
		catch (UnsupportedEncodingException e) {
			throw new ConnectServerException(ConnectServerException.UnsupportedEncodingException);
		} catch (IOException e) {
			throw new ConnectServerException(ConnectServerException.IOEXCEPTION);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally {
			try {
				if(is!=null)
				is.close();
				if(br!=null)
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "连接服务器出错,请检查网络连接！";
	}

	public static String connectForGet(Set<Entry<String, String>> list,
			String sendCode_URL) throws ClientProtocolException {
		InputStream is = null;
		BufferedReader br = null;
		String url;
		if(list == null || list.equals(null)){
			 url = sendCode_URL;
		}else{
			 url = getUrl(list, sendCode_URL);
		}
		Log.d(TAG, "------> URL : " + url);

		HttpGet httpGet = new HttpGet(url);
		HttpParams httpParams = httpGet.getParams();
		httpParams.getParameter("true");

		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity httpEntity = httpResponse.getEntity();
				is = httpEntity.getContent();
				br = new BufferedReader(new InputStreamReader(is));
				String response = "";
				String readLine = null;
				while ((readLine = br.readLine()) != null) {

					response = response + readLine;
				}
				Log.d(TAG, "----->response : " + response);

				return response;
			} else {
				Log.w(TAG, "----->getStatusCode != 200");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw e;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			try {
				is.close();
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "连接服务器出错,请检查网络连接！";
	}
	
	/**
	 * 根据参数拼接URL地址
	 * 
	 * @param list
	 *            参数集合
	 * @param sendCode_URL
	 *            url前端地址
	 * @return
	 */
	private static String getUrl(Set<Entry<String, String>> list,
			String sendCode_URL) {
		StringBuilder sb = new StringBuilder(sendCode_URL);
		Iterator<Map.Entry<String, String>> it = list.iterator();

		if (it.hasNext()) {
			sb.append("?");
		}

		while (it.hasNext()) {
			Map.Entry<String, String> map = it.next();
			sb.append(map.getKey() + "=");
			sb.append(map.getValue());
			if (it.hasNext()) {
				sb.append("&");
			}
		}
		String url = sb.toString();
		return url;

	}

	/**
	 * Post方式与后台服务器交互
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doPost(String url, List<NameValuePair> params) {

		/* 建立HTTPPost对象 */
		HttpPost httpRequest = new HttpPost(url);
		HttpClient httpClient = new DefaultHttpClient();   

		String strResult = "doPostError";

		try {
			/* 添加请求参数到请求对象 */
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			/* 发送请求并等待响应 */
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			/* 若状态码为200 ok */
			//int a = httpResponse.getStatusLine().getStatusCode();
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 读返回数据 */
				strResult = EntityUtils.toString(httpResponse.getEntity());
				
				Log.d(TAG, "------> post right result : " + strResult);

			} else if(httpResponse.getStatusLine().getStatusCode() == 403){
				strResult = EntityUtils.toString(httpResponse.getEntity());
			}else {
				strResult = EntityUtils.toString(httpResponse.getEntity());

				Log.d(TAG, "------> post error result : " + strResult);
			}
		} catch (ClientProtocolException e) {
			strResult = e.getMessage().toString();
			
			e.printStackTrace();
		} catch (IOException e) {
			strResult = e.getMessage().toString();
			e.printStackTrace();
		} catch (Exception e) {
			strResult = e.getMessage().toString();
			e.printStackTrace();
		}
		finally {
			httpClient.getConnectionManager().shutdown();
		}
		Log.v("strResult", strResult);

		return strResult;
	}

	/**
	 * 通过POST和服务器交互
	 * signup接口专用（用于参数和图片传递）
	 * @param url
	 * @param params
	 * @return
	 * @throws ConnectServerException 
	 */
	public static String doPostForSignup(String url, MultipartEntity params) throws ConnectServerException {

		/* 建立HTTPPost对象 */
		HttpPost httpRequest = new HttpPost(url);
		HttpClient httpClient = new DefaultHttpClient();  
		String strResult = "doPostError";
	
		try {
			/* 添加请求参数到请求对象 */
			//httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			httpRequest.setEntity(params);
			
			//httpRequest.setEntity(reqEntity);
			
			/* 发送请求并等待响应 */
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			/* 若状态码为200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				
				/* 读返回数据 */
				strResult = EntityUtils.toString(httpResponse.getEntity());
				
				Log.d(TAG, "------> post right result : " + strResult);

			} else {
				strResult = "Error Response: "
						+ httpResponse.getStatusLine().toString();
				Log.d(TAG, "------> post error result : " + strResult +",code:" + httpResponse.getStatusLine().getStatusCode());
			}
		} catch(SocketException e){
			throw new ConnectServerException(e.getMessage());
		} catch(ConnectTimeoutException e){
			throw new ConnectServerException(ConnectServerException.TIMEOUT);
		} catch(InterruptedIOException e){
			throw new ConnectServerException(ConnectServerException.INTERRUPUTEDIO );
		} catch (ClientProtocolException e) {
			throw new ConnectServerException(ConnectServerException.CLIENTPROTOCOL);
		} catch (UnsupportedEncodingException e) {
			throw new ConnectServerException(ConnectServerException.UnsupportedEncodingException);
		} catch (IOException e) {
			throw new ConnectServerException(ConnectServerException.IOEXCEPTION);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		finally {
			httpClient.getConnectionManager().shutdown();
		}
		Log.v("strResult", strResult);
		return strResult;
	}

	/**
	 * 根据URL获取用户头像
	 * @param avatar_path
	 * @return
	 * @throws IOException 
	 */
	public static Bitmap getUserPhotoBitmap(Context context, String avatar_path) throws IOException {
		
		InputStream is = null;
		try {
           Bitmap bitmap = null;
           URL imageUrl = new URL(avatar_path);
           HttpURLConnection conn = (HttpURLConnection) imageUrl
                           .openConnection();
           conn.setConnectTimeout(30000);
           conn.setReadTimeout(30000);
           conn.setInstanceFollowRedirects(true);
           is = conn.getInputStream();           
          
           bitmap = BitmapFactory.decodeStream(is);    
           return bitmap;
   } catch (Exception ex) {
           ex.printStackTrace();
           return null;
   }
   finally{	   
	   is.close();
   }
		
	}

}
