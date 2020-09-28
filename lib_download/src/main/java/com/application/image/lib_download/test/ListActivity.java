package com.application.image.lib_download.test;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.application.image.lib_download.DownloadManager;
import com.application.image.lib_download.R;
import com.application.image.lib_download.entity.DownloadEntry;
import com.application.image.lib_download.notify.DataWatcher;


/**
 * 
 * @author shuwoom
 * @email 294299195@qq.com
 * @date 2015-9-2
 * @update 2015-9-2
 * @des Test download list.
 */
public class ListActivity extends Activity implements OnClickListener{
	
	private ArrayList<DownloadEntry> downloadEntries = new ArrayList<DownloadEntry>();
	
	
	private Button pauseAllBtn;
	private Button recoverAllBtn;
	private ListView listView;
    private DownloadAdapter adapter;
	
	private DataWatcher dataWatcher = new DataWatcher() {

		@Override
		public void onDataChanged(DownloadEntry downloadEntry) {
			int index = downloadEntries.indexOf(downloadEntry);
			if(index != -1){
				downloadEntries.remove(index);
				downloadEntries.add(index, downloadEntry);
				adapter.notifyDataSetChanged();
			}
		}
	};
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		
		
		pauseAllBtn = (Button)findViewById(R.id.pause_all_btn);
		recoverAllBtn = (Button)findViewById(R.id.recover_all_btn);
		listView = (ListView) findViewById(R.id.listview);
		
		DownloadEntry entry = new DownloadEntry();
		entry.name = "少年三国志.apk";
		entry.url = "http://gh-game.oss-cn-hangzhou.aliyuncs.com/1435814701749842.apk";
		downloadEntries.add(entry);
		
		entry = new DownloadEntry();
		entry.name = "放开那三国.apk";
		entry.url = "http://gh-game.oss-cn-hangzhou.aliyuncs.com/1437641784268948.apk";
		downloadEntries.add(entry);
		
		entry = new DownloadEntry();
		entry.name = "去吧皮卡丘.apk";
		entry.url = "http://gh-game.oss-cn-hangzhou.aliyuncs.com/1437740158861375.apk";
		downloadEntries.add(entry);
		
		entry = new DownloadEntry();
		entry.name = "X三国.apk";
		entry.url = "http://gh-game.oss-cn-hangzhou.aliyuncs.com/1434794302961350.apk";
		downloadEntries.add(entry);
		
		entry = new DownloadEntry();
		entry.name = "火影忍者-忍者大师.apk";
		entry.url = "http://gh-game.oss-cn-hangzhou.aliyuncs.com/1435242602866100.apk";
		downloadEntries.add(entry);
        
        adapter = new DownloadAdapter();
        listView.setAdapter(adapter);
        
        pauseAllBtn.setOnClickListener(this);
        recoverAllBtn.setOnClickListener(this);
		
	}
	
	@Override
    protected void onResume() {
        super.onResume();
		DownloadManager.getInstance(this).addObserver(dataWatcher);
    }
	
	@Override
    protected void onPause() {
        super.onPause();
        DownloadManager.getInstance(this).removeObserver(dataWatcher);
    }
	
	class DownloadAdapter extends BaseAdapter {

        private ViewHolder holder;

        @Override
        public int getCount() {
            return downloadEntries != null ? downloadEntries.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return downloadEntries.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null || convertView.getTag() == null) {
                convertView = LayoutInflater.from(ListActivity.this).inflate(R.layout.listview_item, null);
                holder = new ViewHolder();
                holder.downloadBtn = (Button) convertView.findViewById(R.id.download_btn);
                holder.statusText = (TextView) convertView.findViewById(R.id.show_txt);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final DownloadEntry entry = downloadEntries.get(position);
            holder.statusText.setText(entry.toString());
            holder.downloadBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                	if(entry.status == DownloadEntry.DownloadStatus.idle){
        				DownloadManager.getInstance(ListActivity.this).add(entry);
        			}else if(entry.status == DownloadEntry.DownloadStatus.downloading
        					||entry.status == DownloadEntry.DownloadStatus.waiting ){
        				DownloadManager.getInstance(ListActivity.this).pause(entry);
        			}else if(entry.status == DownloadEntry.DownloadStatus.pause){
        				DownloadManager.getInstance(ListActivity.this).resume(entry);
        			}else{
        				DownloadManager.getInstance(ListActivity.this).add(entry);
        			}
                }
            });
            return convertView;
        }
        
        class ViewHolder {
        	Button downloadBtn;
            TextView statusText;
        }
    }

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.pause_all_btn) {
			DownloadManager.getInstance(ListActivity.this).pauseAll();
		} else if (id == R.id.recover_all_btn) {
			DownloadManager.getInstance(ListActivity.this).recoverAll();
		}
	}

}
