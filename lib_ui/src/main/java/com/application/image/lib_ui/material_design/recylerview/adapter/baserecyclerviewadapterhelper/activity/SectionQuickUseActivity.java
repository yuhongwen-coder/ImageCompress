package com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.application.image.lib_ui.R;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.adapter.SectionQuickAdapter;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.base.BaseActivity;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.data.DataServer;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.decoration.GridSectionAverageGapItemDecoration;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.entity.MySection;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.entity.Video;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.BaseQuickAdapter;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.listener.OnItemChildClickListener;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.listener.OnItemClickListener;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.utils.Tips;



import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class SectionQuickUseActivity extends BaseActivity {
    private RecyclerView    mRecyclerView;
    private List<MySection> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_uer);

        setBackBtn();
        setTitle("Quick Section Use");

        mRecyclerView = findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.addItemDecoration(new GridSectionAverageGapItemDecoration(10, 10, 20, 15));

        mData = DataServer.getSectionData();
        SectionQuickAdapter adapter = new SectionQuickAdapter(R.layout.item_section_content, R.layout.def_section_head, mData);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                MySection mySection = mData.get(position);
                if (mySection.isHeader()) {
                    Tips.show((String) mySection.getObject());
                } else {
                    Video video = (Video) mySection.getObject();
                    Tips.show(video.getName());
                }
            }
        });
        adapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                Tips.show("onItemChildClick: " + position);
            }
        });
        mRecyclerView.setAdapter(adapter);
    }


}
