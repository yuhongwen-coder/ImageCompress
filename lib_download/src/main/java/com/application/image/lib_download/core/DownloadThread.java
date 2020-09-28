package com.application.image.lib_download.core;

import com.application.image.lib_download.DownloadConfig;
import com.application.image.lib_download.entity.DownloadEntry;
import com.application.image.lib_download.utils.MyConstant;
import com.application.image.lib_download.utils.MyTraceLog;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
 * @author shuwoom
 * @email 294299195@qq.com
 * @date 2015-9-2
 * @update 2015-9-2
 * @des Download thread.
 */
public class DownloadThread implements Runnable{
	private String url;
	private int index;
	private int startPos;
	private int endPos;
	private String path;
	
	private DownloadListener listener;
	private volatile boolean isPaused ;
	private volatile boolean isCanceled;
	private volatile boolean isError;
	private boolean isSingleDownload;
	
	private DownloadEntry.DownloadStatus status;
	
	public DownloadThread(String url, int index, int startPos, int endPos, DownloadListener listener) {
		this.url = url;
		this.index = index;
		this.startPos = startPos;
		this.endPos = endPos;
		this.path = DownloadConfig.DOWNLOAD_PATH + url.substring(url.lastIndexOf("/") + 1);
		this.listener = listener;
		if (startPos == 0 && endPos == 0) {
            isSingleDownload = true;
        } else {
            isSingleDownload = false;
        }
	}

	@Override
	public void run() {
		status = DownloadEntry.DownloadStatus.downloading;
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection)new URL(url).openConnection();
			connection.setRequestMethod("GET");
			if(!isSingleDownload){
				connection.setRequestProperty("Range","bytes=" + startPos + "-" + endPos);
			}
			connection.setConnectTimeout(MyConstant.CONNECT_TIME);
			connection.setReadTimeout(MyConstant.READ_TIME);
			
			int responseCode = connection.getResponseCode();
			int contentLength = connection.getContentLength();
			File file = new File(path);
			RandomAccessFile raf = null;
			FileOutputStream fos = null;
			InputStream is = null;
			
			if(responseCode == HttpURLConnection.HTTP_PARTIAL){
				//支持断点下载
				byte[]buffer = new byte[2048];
				int len = -1;
				
				if(DownloadConfig.getInstance().getMax_download_threads() > 1){
					//子线程数>1时，使用RandomAccessFile
					MyTraceLog.d("DownloadThread==>" + "run()#####使用RandomAccessFile. Support ranges. Index:" + index + "==" + url + "***" + startPos + "-" + endPos + "**" + contentLength);
					raf = new RandomAccessFile(file, "rw");
					raf.seek(startPos);
					is = connection.getInputStream();
					
					while( (len = is.read(buffer)) != -1){
						if(isPaused || isCanceled || isError){
							break;
						}
						raf.write(buffer, 0, len);
						listener.onProgressChanged(index, len);
					}
					
					raf.close();
					is.close();
				}else{
					//子线程数为1时，使用FileOutputStream提高速度
					MyTraceLog.d("DownloadThread==>" + "run()#####使用FileOutputStream. Support ranges. Index:" + index + "==" + url + "***" + startPos + "-" + endPos + "**" + contentLength);
			        BufferedInputStream bis = null;
			        BufferedOutputStream bos = null;
			        
					if (!file.exists()) {
			            File dir = file.getParentFile();
			            if (dir.exists() || dir.mkdirs()) {
			            	file.createNewFile();
			            }
			        }
					fos = new FileOutputStream(path, true);
					bis = new BufferedInputStream(connection.getInputStream());
			        bos = new BufferedOutputStream(fos);
					
			        while( (len = bis.read(buffer)) != -1){
						if(isPaused || isCanceled || isError){
							break;
						}
						bos.write(buffer, 0, len);
						listener.onProgressChanged(index, len);
					}
			        bos.flush();
			        bis.close();
		            bos.close();
				}
				
			}else if(responseCode == HttpURLConnection.HTTP_OK){
				//不支持断点下载
				MyTraceLog.d("DownloadThread==>" + "run()#####not support ranges. Index:" + index + "==" + url + "***" + startPos + "-" + endPos + "**" + contentLength);
				fos = new FileOutputStream(file);
				is = connection.getInputStream();
				byte[]buffer = new byte[2048];
				int len = -1;
				while( (len = is.read(buffer)) != -1){
					if(isPaused || isCanceled || isError){
						break;
					}
					fos.write(buffer, 0, len);
					listener.onProgressChanged(index, len);
				}
				
				fos.close();
				is.close();
			}else{
				MyTraceLog.d("DownloadThread==>index:" + index + " run()#####server error");
				status = DownloadEntry.DownloadStatus.error;
                listener.onDownloadError(index, "server error:" + responseCode);
                return;
			}
			
			if(isPaused){
				MyTraceLog.d("DownloadThread==>index:" + index + " run()#####pause");
				status = DownloadEntry.DownloadStatus.pause;
				listener.onDownloadPaused(index);
			}else if(isCanceled){
				MyTraceLog.d("DownloadThread==>index:" + index + " run()#####cancel");
				status = DownloadEntry.DownloadStatus.cancel;
				listener.onDownloadCanceled(index);
			}else if(isError){
				MyTraceLog.d("DownloadThread==>index:" + index + " run()#####error");
				status = DownloadEntry.DownloadStatus.error;
				listener.onDownloadError(index, "cancel manually by error");
			}else{
				MyTraceLog.d("DownloadThread==>index:" + index + " run()#####done");
				status = DownloadEntry.DownloadStatus.done;
				listener.onDownloadCompleted(index);
			}
		} catch (IOException e) {
			e.printStackTrace();
			if(isPaused){
				MyTraceLog.d("DownloadThread==>" + " run()#####exception and pause");
				status = DownloadEntry.DownloadStatus.pause;
				listener.onDownloadPaused(index);
			}else if(isCanceled){
				MyTraceLog.d("DownloadThread==>index:" + index + " run()#####exception and cancel");
				status = DownloadEntry.DownloadStatus.cancel;
				listener.onDownloadCanceled(index);
			}else{
				MyTraceLog.d("DownloadThread==>index:" + index + " run()#####error");
				status = DownloadEntry.DownloadStatus.error;
				listener.onDownloadError(index, e.getMessage());
			}
			
		} finally {
			if(connection != null){
				connection.disconnect();
				MyTraceLog.d("DownloadThread==>run()#####index:" + index + "***" + url + "*****close connection");
			}
		}
	}
	
	interface DownloadListener{
		void onProgressChanged(int index, int progress);
		
		void onDownloadPaused(int index);
		
		void onDownloadCanceled(int index);

		void onDownloadCompleted(int index);
		
		void onDownloadError(int index, String message);
	}

	public void pause() {
		MyTraceLog.d("DownloadThread==>pause()#####index:" + index);
		isPaused = true;
		Thread.currentThread().interrupt();
	}
	
	public void cancel(){
		MyTraceLog.d("DownloadThread==>index:" + index + " cancel()");
		isCanceled = true;
		Thread.currentThread().interrupt();
	}

	public boolean isRunning() {
		return status == DownloadEntry.DownloadStatus.downloading;
	}

	public void cancelByError() {
		MyTraceLog.d("DownloadThread==>index:" + index + " cancelByError()");
		isError  = true;
		Thread.currentThread().interrupt();
	}

}
