package com.application.image.lib_ui.material_design.custom;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.application.image.lib_ui.R;

/**
 * Created by yuhongwen
 * on 2020/10/31
 */
public class CustomViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_view_activity);
        ImageView voicePlay = findViewById(R.id.voice_play);
        playAudioAnimation(voicePlay);
    }

    private void playAudioAnimation(ImageView voicePlay) {

    }
}
