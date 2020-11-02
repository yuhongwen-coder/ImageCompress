package com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.image.lib_ui.R;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.adapter.EmptyViewAdapter;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.base.BaseActivity;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.data.DataServer;


public class EmptyViewUseActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private EmptyViewAdapter mAdapter = new EmptyViewAdapter();
    private boolean mError = true;
    private boolean mNoData = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_view_use);

        setBackBtn();
        setTitle("EmptyView Use");

        findViewById(R.id.btn_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        mRecyclerView = findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        onRefresh();
    }

    private void reset() {
        mError = true;
        mNoData = true;
        mAdapter.setList(null);
        onRefresh();
    }

    private View getEmptyDataView() {
        View notDataView = getLayoutInflater().inflate(R.layout.empty_view, mRecyclerView, false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
        return notDataView;
    }

    private View getErrorView() {
        View errorView = getLayoutInflater().inflate(R.layout.error_view, mRecyclerView, false);
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRefresh();
            }
        });
        return errorView;
    }

    private void onRefresh() {
        // 方式一：直接传入 layout id
        mAdapter.setEmptyView(R.layout.loading_view);
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mError) {
                    // 方式二：传入View
                    mAdapter.setEmptyView(getErrorView());
                    mError = false;
                } else {
                    if (mNoData) {
                        mAdapter.setEmptyView(getEmptyDataView());
                        mNoData = false;
                    } else {
                        mAdapter.setList(DataServer.getSampleData(10));
                    }
                }
            }
        }, 1000);
    }
}
