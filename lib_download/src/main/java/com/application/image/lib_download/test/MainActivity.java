package com.application.image.lib_download.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
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
 * @des Test one download task.
 */
public class MainActivity extends Activity implements OnClickListener{
	
	private final String url = "http://gh-game.oss-cn-hangzhou.aliyuncs.com/1434794302961350.apk";
	private DownloadEntry entry = new DownloadEntry(url);
	
	private Button addBtn;
	private Button cancelBtn;
	private Button pauseBtn;
	private Button resumeBtn;
	
	private TextView showText;
	
	private DataWatcher dataWatcher = new DataWatcher() {

		@Override
		public void onDataChanged(DownloadEntry data) {
			entry = data;
			showText.setText(entry.toString());
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		entry.name = "x三国.apk";
		
		showText = (TextView)findViewById(R.id.show_text);
		addBtn = (Button)findViewById(R.id.add_btn);
		cancelBtn = (Button)findViewById(R.id.cancel_btn);
		pauseBtn = (Button)findViewById(R.id.pause_btn);
		resumeBtn = (Button)findViewById(R.id.resume_btn);
		
		addBtn.setOnClickListener(this);
		cancelBtn.setOnClickListener(this);
		pauseBtn.setOnClickListener(this);
		resumeBtn.setOnClickListener(this);
		
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

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.add_btn) {
			DownloadManager.getInstance(MainActivity.this).add(entry);
		} else if (id == R.id.pause_btn) {
			DownloadManager.getInstance(MainActivity.this).pause(entry);
		} else if (id == R.id.resume_btn) {
			DownloadManager.getInstance(MainActivity.this).resume(entry);
		} else if (id == R.id.cancel_btn) {
			DownloadManager.getInstance(MainActivity.this).cancel(entry);
		}
	}

}
