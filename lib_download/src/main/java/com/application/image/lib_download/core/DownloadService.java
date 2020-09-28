package com.application.image.lib_download.core;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.application.image.lib_download.DownloadConfig;
import com.application.image.lib_download.db.DBController;
import com.application.image.lib_download.entity.DownloadEntry;
import com.application.image.lib_download.notify.DataChanger;
import com.application.image.lib_download.utils.MyConstant;
import com.application.image.lib_download.utils.MyTraceLog;


/**
 * 
 * @author shuwoom
 * @email 294299195@qq.com
 * @date 2015-9-2
 * @update 2015-9-2
 * @des Service to manager download tasks.
 */
public class DownloadService extends Service{
	public static final int NOTIFY_DOWNLOADING = 1;
    public static final int NOTIFY_UPDATING = 2;
    public static final int NOTIFY_PAUSED_OR_CANCELLED = 3;
    public static final int NOTIFY_COMPLETED = 4;
	public static final int NOTIFY_ERROR = 5;
	public static final int NOTIFY_CONNECTING = 6;
	public static final int NOTIFY_NOT_ENOUGH_SIZE = 7;

	private int i;
	private int j;

	private HashMap<String, DownloadTask> mDownloadingTasks;
	private LinkedBlockingQueue<DownloadEntry> mWaitingQueue;
	
	private ExecutorService mExecutor;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			DownloadEntry entry = (DownloadEntry) msg.obj;
			switch(msg.what){
				case NOTIFY_COMPLETED:
				case NOTIFY_PAUSED_OR_CANCELLED:
				case NOTIFY_ERROR:
					checkNext(entry);
				break;
				
				case NOTIFY_NOT_ENOUGH_SIZE:
					Toast.makeText(getApplicationContext(), "存储卡空间不足，请清理！", Toast.LENGTH_SHORT).show();
					checkNext(entry);
					break;
			}
			DataChanger.getInstance(getApplication()).updateStatus(entry);
		}

	};
	private DataChanger dataChanger;
	private DBController dbController;
	

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mDownloadingTasks = new HashMap<String, DownloadTask>();
		mWaitingQueue = new LinkedBlockingQueue<DownloadEntry>();
		
		mExecutor = Executors.newCachedThreadPool();
		dataChanger = DataChanger.getInstance(getApplicationContext());
		dbController = DBController.getInstance(getApplicationContext());
		intializeDownload();
		Log.e(" service onCreate j = ",String.valueOf((j++)));
		 
	}
	
	//防止App进程被强杀时数据丢失
	private void intializeDownload() {
		ArrayList<DownloadEntry> mDownloadEtries = dbController.queryAll();
        if (mDownloadEtries != null) {
            for (DownloadEntry entry : mDownloadEtries) {
                if (entry.status == DownloadEntry.DownloadStatus.downloading || entry.status == DownloadEntry.DownloadStatus.waiting){
                    entry.status = DownloadEntry.DownloadStatus.pause;
                    if(DownloadConfig.getInstance().isRecoverDownloadWhenStart()){
                    	if(entry.isSupportRange){
                    		entry.status = DownloadEntry.DownloadStatus.pause;
                    	}else{
                    		entry.status = DownloadEntry.DownloadStatus.idle;
                    		entry.reset();
                    	}
                    	addDownload(entry);
                    }else{
                    	if(entry.isSupportRange){
                    		entry.status = DownloadEntry.DownloadStatus.pause;
                    	}else{
                    		entry.status = DownloadEntry.DownloadStatus.idle;
                    		entry.reset();
                    	}
                    	dbController.newOrUpdate(entry);
                    }
                }
                dataChanger.addToOperatedEntryMap(entry.url, entry);
            }
        }
	}

	@SuppressLint("LongLogTag")
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e("service onStartCommand i = ",String.valueOf((i++)));
		if(intent != null){
			int action = intent.getIntExtra(MyConstant.KEY_DOWNLOAD_ACTION, -1);
			DownloadEntry entry = (DownloadEntry) intent.getSerializableExtra(MyConstant.KEY_DOWNLOAD_ENTRY);
			/*****防止App进程被强杀时数据丢失*****/
			if(entry != null && dataChanger.containsDownloadEntry(entry.url)){
				entry = dataChanger.queryDownloadEntryByUrl(entry.url);
			}
			
			switch(action){
				case MyConstant.KEY_DOWNLOAD_ACTION_ADD:
					addDownload(entry);
					break;
					
				case MyConstant.KEY_DOWNLOAD_ACTION_PAUSE:
					pauseDownload(entry);
					break;
					
				case MyConstant.KEY_DOWNLOAD_ACTION_RESUME:
					resumeDownload(entry);
					break;
					
				case MyConstant.KEY_DOWNLOAD_ACTION_CANCEL:
					cancelDownload(entry);
					break;
					
				case MyConstant.KEY_DOWNLOAD_ACTION_PAUSE_ALL:
					pauseAllDownload();
					break;
					
				case MyConstant.KEY_DOWNLOAD_ACTION_RECOVER_ALL:
					recoverAllDownload();
					break;
					
				default:
					break;
			
			}
		}
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	private void recoverAllDownload() {
		 ArrayList<DownloadEntry> mRecoverableEntries = DataChanger.getInstance(getApplication()).queryAllRecoverableEntries();
		if(mRecoverableEntries == null)	return;
		
		for (DownloadEntry entry : mRecoverableEntries) {
			addDownload(entry);
		}
		MyTraceLog.d("DownloadService==>recoverAllDownload" + "***Task Size:" + mDownloadingTasks.size() + "***Waiting Queue:" + mWaitingQueue.size());
	}

	private void pauseAllDownload() {
		while(mWaitingQueue.iterator().hasNext()){
			DownloadEntry entry = mWaitingQueue.poll();
			entry.status = DownloadEntry.DownloadStatus.pause;
			DataChanger.getInstance(getApplication()).updateStatus(entry);
		}
		
		for (Map.Entry<String, DownloadTask> entry : mDownloadingTasks.entrySet()) {
            entry.getValue().pause();
        }
		mDownloadingTasks.clear();
		MyTraceLog.d("DownloadService==>pauseAllDownload");
	}

	private void checkNext(DownloadEntry entry) {
		mDownloadingTasks.remove(entry.url);
		DownloadEntry newEntry = mWaitingQueue.poll();
		if(newEntry != null){
			startDownload(newEntry);
		}
	};

	private void cancelDownload(DownloadEntry entry) {
		DownloadTask task = mDownloadingTasks.remove(entry.url);
		if(task != null){
			task.cancel();
			MyTraceLog.d("DownloadService==>pauseDownload#####cancel downloading task"
				+ "***Task Size:" + mDownloadingTasks.size() 
				+ "***Waiting Queue:" + mWaitingQueue.size());
		}else{
			mWaitingQueue.remove(entry);
			entry.status = DownloadEntry.DownloadStatus.cancel;
			DataChanger.getInstance(getApplication()).updateStatus(entry);
			MyTraceLog.d("DownloadService==>pauseDownload#####cancel waiting queue!"
					+ "***Task Size:" + mDownloadingTasks.size() 
					+ "***Waiting Queue:" + mWaitingQueue.size());
		}
	}

	private void resumeDownload(DownloadEntry entry) {
		addDownload(entry);
		MyTraceLog.d("DownloadService==>resumeDownload"
				+ "***Task Size:" + mDownloadingTasks.size() 
				+ "***Waiting Queue:" + mWaitingQueue.size());
	}

	private void pauseDownload(DownloadEntry entry) {
		DownloadTask task = mDownloadingTasks.remove(entry.url);
		if(task != null){
			MyTraceLog.d("DownloadService==>pauseDownload#####pause downloading task"
					+ "***Task Size:" + mDownloadingTasks.size() 
					+ "***Waiting Queue:" + mWaitingQueue.size());
			task.pause();
		}else{
			mWaitingQueue.remove(entry);
			entry.status = DownloadEntry.DownloadStatus.pause;
			DataChanger.getInstance(getApplication()).updateStatus(entry);
			MyTraceLog.d("DownloadService==>pauseDownload#####pause waiting queue!"
					+ "***Task Size:" + mDownloadingTasks.size() 
					+ "***Waiting Queue:" + mWaitingQueue.size());
		}
		
	}

	private void addDownload(DownloadEntry entry) {
		checkDownloadPath(entry);
		if(isDownloadEntryRepeted(entry)){
			return ;
		}
		if(mDownloadingTasks.size() >= DownloadConfig.getInstance().getMax_download_tasks()){
			mWaitingQueue.offer(entry);
			entry.status = DownloadEntry.DownloadStatus.waiting;
			DataChanger.getInstance(getApplication()).updateStatus(entry);
			MyTraceLog.d("DownloadService==>addDownload#####bigger than max_tasks"
					+ "***Task Size:" + mDownloadingTasks.size()
					+ "***Waiting Queue:" + mWaitingQueue.size());
		}else{
			MyTraceLog.d("DownloadService==>addDownload#####start tasks"
					+ "***Task Size:" + mDownloadingTasks.size() 
					+ "***Waiting Queue:" + mWaitingQueue.size());
			startDownload(entry);
		}
	}
	
	private void startDownload(DownloadEntry entry){
		DownloadTask task =new DownloadTask(entry, mHandler, mExecutor);
		mDownloadingTasks.put(entry.url, task);
		MyTraceLog.d("DownloadService==>startDownload"
				+ "***Task Size:" + mDownloadingTasks.size() 
				+ "***Waiting Queue:" + mWaitingQueue.size());
		task.start();
	}
	
	private void checkDownloadPath(DownloadEntry entry) {
		MyTraceLog.d("DownloadService==>checkDownloadPath()");
		File file = new File(DownloadConfig.DOWNLOAD_PATH + entry.url.substring(entry.url.lastIndexOf("/") + 1));
		if(file != null && !file.exists()){
			entry.reset();
			MyTraceLog.d("DownloadService==>checkDownloadPath()#####" + entry.name + "'s cache is not exist, restart download!");
		}
	}
	
	private boolean isDownloadEntryRepeted(DownloadEntry entry){
		if(mDownloadingTasks.get(entry.url) != null){
			MyTraceLog.d("DownlaodService==>isDownloadEntryRepeted()##### The downloadEntry is in downloading tasks!!");
			return true;
		}
		
		if(mWaitingQueue.contains(entry)){
			MyTraceLog.d("DownlaodService==>isDownloadEntryRepeted()##### The downloadEntry is in waiting queue!!");
			return true;
		}
		return false;
	}

}
