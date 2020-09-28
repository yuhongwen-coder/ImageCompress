package com.application.image.lib_download.core;

import com.application.image.lib_download.utils.MyConstant;
import com.application.image.lib_download.utils.MyTraceLog;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
 * @author shuwoom
 * @email 294299195@qq.com
 * @date 2015-9-2
 * @update 2015-9-2
 * @des Connecting network to get content length and whether is's supporting ranges operation.
 */
public class ConnectThread implements Runnable{
	private final ConnectListener listener;
	private final String url;
	private volatile boolean isRunning;
	
	public ConnectThread(String url, ConnectListener listener){
		this.url = url;
		this.listener = listener;
	}
	
	@Override
	public void run() {
		isRunning = true;
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection)new URL(url).openConnection();
			connection.setRequestMethod("GET");
//			connection.setRequestProperty("Range","bytes=0-");
			connection.setConnectTimeout(MyConstant.CONNECT_TIME);
			connection.setReadTimeout(MyConstant.READ_TIME);
			int responseCode = connection.getResponseCode();
			int contentLength = connection.getContentLength();
			boolean isSupportRange = false;
			if(responseCode == HttpURLConnection.HTTP_OK){
				String ranges = connection.getHeaderField("Accept-Ranges");
                if ("bytes".equals(ranges)){
                    isSupportRange = true;
                }
                listener.onConnectSuccess(isSupportRange, contentLength);
                MyTraceLog.d("ConnectThread==>run()#####" + url + "*****connect success " + responseCode);
			}else{
				listener.onConnectFaile("server error:" + responseCode);
				MyTraceLog.d("ConnectThread==>run()#####" + url + "*****server error: " + responseCode);
			}
			isRunning = false;
		}  catch (Exception e) {
			MyTraceLog.d("ConnectThread==>run()#####" + url + "*****connect exception***" + e.getMessage());
			isRunning = false;
			listener.onConnectFaile(e.getMessage());
		} finally{
			if (connection != null){
				MyTraceLog.d("ConnectThread==>run()#####" + url + "*****close connection");
                connection.disconnect();
            }
		}
	}
	
	public boolean isRunning() {
        return isRunning;
    }

    public void cancel() {
        Thread.currentThread().interrupt();
    }
	
	interface ConnectListener{
		public void onConnectSuccess(boolean isSupportRange, int totalLength);
		public void onConnectFaile(String message);
	}

}
