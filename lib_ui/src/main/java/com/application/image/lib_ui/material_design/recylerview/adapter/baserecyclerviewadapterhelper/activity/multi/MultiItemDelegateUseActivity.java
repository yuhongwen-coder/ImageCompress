package com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.activity.multi;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.application.image.lib_ui.R;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.adapter.multi.DelegateMultiAdapter;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.base.BaseActivity;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.data.DataServer;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.entity.DelegateMultiEntity;

import java.util.List;

public class MultiItemDelegateUseActivity extends BaseActivity {

    private DelegateMultiAdapter adapter = new DelegateMultiAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_item_use);

        setTitle("BaseMultiItemQuickAdapter");
        setBackBtn();

        initRv();
    }

    private void initRv() {
        RecyclerView mRecyclerView = findViewById(R.id.rv_list);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final List<DelegateMultiEntity> data = DataServer.getDelegateMultiItemData();
        adapter.setList(data);
    }
}
