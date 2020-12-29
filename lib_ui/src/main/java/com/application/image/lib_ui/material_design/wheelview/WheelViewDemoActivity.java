package com.application.image.lib_ui.material_design.wheelview;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.application.image.lib_ui.R;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WheelViewDemoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wheel_view_layout);
        findViewById(R.id.open_wheelview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateViewSet();
            }
        });
    }

    private void DateViewSet() {
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = simpleDateFormat.format(date);
            }
        }).setSubmitText("确定")
                .setCancelText("取消")
                .setTextColorCenter(getResources().getColor(R.color.main_color))
                .setDividerColor(getResources().getColor(R.color.main_color))
                .setContentTextSize(20)
                .setSubCalSize(20)
                .setType(new boolean[]{true, true, true, true, true, true})
                .setLabel("年",
                        "月",
                        "日",
                        "时",
                        "分",
                        "秒")
                .build();
        pvTime.show();
    }
}
