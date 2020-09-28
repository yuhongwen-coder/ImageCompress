package com.application.image.lib_download.notify;

import com.application.image.lib_download.entity.DownloadEntry;

import java.util.Observable;
import java.util.Observer;


/**
 * 
 * @author shuwoom
 * @email 294299195@qq.com
 * @date 2015-9-2
 * @update 2015-9-2
 * @des Observer
 */
public abstract class DataWatcher implements Observer{

	@Override
	public void update(Observable observable, Object data) {
		if(data instanceof DownloadEntry){
			onDataChanged((DownloadEntry) data);
		}
	}
	
	public abstract void onDataChanged(DownloadEntry downloadEntry);

}
