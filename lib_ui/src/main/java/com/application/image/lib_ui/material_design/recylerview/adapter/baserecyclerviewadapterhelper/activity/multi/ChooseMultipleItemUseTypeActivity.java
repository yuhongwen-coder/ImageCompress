package com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.activity.multi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.base.BaseActivity;


/**
 * https://github.com/chaychan
 *
 * @author ChayChan
 * @description: ChooseMultipleItemUseType
 * @date 2018/3/30  10:14
 */

public class ChooseMultipleItemUseTypeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityChooseMultipleItemUseTypeBinding binding = ActivityChooseMultipleItemUseTypeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("MultipleItem Use");
        setBackBtn();

        binding.cardView0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseMultipleItemUseTypeActivity.this, BinderUseActivity.class));
            }
        });

        binding.cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseMultipleItemUseTypeActivity.this, MultiItemQuickUseActivity.class));
            }
        });

        binding.cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseMultipleItemUseTypeActivity.this, MultiItemDelegateUseActivity.class));
            }
        });

        binding.cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseMultipleItemUseTypeActivity.this, MultiItemProviderUseActivity.class));
            }
        });
    }
}
