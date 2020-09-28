package com.application.image.lib_log;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.orhanobut.logger.Logger;

public class LogUtilsActivity extends AppCompatActivity {

    private static final String TAG = "LogUtilsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("onCreate enter");
        setContentView(R.layout.activity_log_utils);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
