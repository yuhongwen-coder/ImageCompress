package com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.application.image.lib_ui.R;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.adapter.HeaderAndFooterAdapter;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.base.BaseActivity;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.BaseQuickAdapter;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.listener.OnItemClickListener;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.utils.Tips;



/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class HeaderAndFooterUseActivity extends BaseActivity {

    private              RecyclerView           mRecyclerView;
    private HeaderAndFooterAdapter adapter;
    private static final int                    PAGE_SIZE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universal_recycler);

        setBackBtn();
        setTitle("HeaderAndFooter Use");

        mRecyclerView = findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();

        View headerView = getHeaderView(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addHeaderView(getHeaderView(1, getRemoveHeaderListener()), 0);
            }
        });
        adapter.addHeaderView(headerView);

        View footerView = getFooterView(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addFooterView(getFooterView(1, getRemoveFooterListener()), 0);
            }
        });
        adapter.addFooterView(footerView, 0);

        mRecyclerView.setAdapter(adapter);

    }


    private View getHeaderView(int type, View.OnClickListener listener) {
        View view = getLayoutInflater().inflate(R.layout.head_view, mRecyclerView, false);
        if (type == 1) {
            ImageView imageView = view.findViewById(R.id.iv);
            imageView.setImageResource(R.mipmap.rm_icon);
        }
        view.setOnClickListener(listener);
        return view;
    }

    private View getFooterView(int type, View.OnClickListener listener) {
        View view = getLayoutInflater().inflate(R.layout.footer_view, mRecyclerView, false);
        if (type == 1) {
            ImageView imageView = view.findViewById(R.id.iv);
            imageView.setImageResource(R.mipmap.rm_icon);
        }
        view.setOnClickListener(listener);
        return view;
    }

    private View.OnClickListener getRemoveHeaderListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.removeHeaderView(v);
            }
        };
    }


    private View.OnClickListener getRemoveFooterListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.removeFooterView(v);
            }
        };
    }

    private void initAdapter() {
        adapter = new HeaderAndFooterAdapter(PAGE_SIZE);
        adapter.setAnimationEnable(true);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                Tips.show(String.valueOf(position));
            }

        });

    }

}
