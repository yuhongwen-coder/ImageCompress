package com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.activity.node;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.image.lib_ui.R;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.adapter.node.section.NodeSectionAdapter;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.base.BaseActivity;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.entity.node.section.ItemNode;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.entity.node.section.RootNode;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.entity.node.BaseNode;


import java.util.ArrayList;
import java.util.List;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class NodeSectionUseActivity extends BaseActivity {
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_section);

        setBackBtn();
        setTitle("Node Use (Section)");

        mRecyclerView = findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
//        mRecyclerView.addItemDecoration(new GridSectionAverageGapItemDecoration(10, 10, 20, 15));

        final NodeSectionAdapter nodeAdapter = new NodeSectionAdapter();

        // 顶部header
        View view = getLayoutInflater().inflate(R.layout.head_view, mRecyclerView, false);
        view.findViewById(R.id.iv).setVisibility(View.GONE);
        nodeAdapter.addHeaderView(view);

        mRecyclerView.setAdapter(nodeAdapter);

        nodeAdapter.setList(getEntity());
        mRecyclerView.scheduleLayoutAnimation();
    }

    private List<BaseNode> getEntity() {
        List<BaseNode> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {

            //Item Node
            ItemNode itemEntity1 = new ItemNode(R.mipmap.click_head_img_0, "Root " + i + " - SecondNode 0");
            ItemNode itemEntity2 = new ItemNode(R.mipmap.click_head_img_0, "Root " + i + " - SecondNode 1");
            ItemNode itemEntity3 = new ItemNode(R.mipmap.click_head_img_0, "Root " + i + " - SecondNode 2");
            ItemNode itemEntity4 = new ItemNode(R.mipmap.click_head_img_0, "Root " + i + " - SecondNode 3");
            ItemNode itemEntity5 = new ItemNode(R.mipmap.click_head_img_0, "Root " + i + " - SecondNode 4");
            List<BaseNode> items = new ArrayList<>();
            items.add(itemEntity1);
            items.add(itemEntity2);
            items.add(itemEntity3);
            items.add(itemEntity4);
            items.add(itemEntity5);

            // Root Node
            RootNode entity = new RootNode(items, "Root Node " + i);

            if (i == 1) {
                // 第1号数据默认不展开
                entity.setExpanded(false);
            }

            list.add(entity);
        }
        return list;
    }
}
