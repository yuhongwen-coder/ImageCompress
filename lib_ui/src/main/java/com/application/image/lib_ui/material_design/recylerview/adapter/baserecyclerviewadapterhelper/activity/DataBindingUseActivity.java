package com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.application.image.lib_ui.R;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.adapter.DataBindingAdapter;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.base.BaseActivity;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.entity.Movie;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.BaseQuickAdapter;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.lib.listener.OnItemClickListener;
import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.utils.Tips;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author: limuyang
 * @date: 2019-12-05
 * @Description:
 */
public class DataBindingUseActivity extends BaseActivity {

    private DataBindingAdapter adapter = new DataBindingAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universal_recycler);

        setTitle("DataBinding Use");
        setBackBtn();

        final RecyclerView mRecyclerView = findViewById(R.id.rv);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);

        //demo 添加的 Header
        //Header 是自行添加进去的 View，所以 Adapter 不管理 Header 的 DataBinding。
        //请在外部自行完成数据的绑定
        View view = getLayoutInflater().inflate(R.layout.head_view, null, false);
        view.findViewById(R.id.iv).setVisibility(View.GONE);
        adapter.addHeaderView(view);

        //item 点击事件
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Tips.show("onItemClick: " + position);
            }
        });

        //设置数据
        adapter.setList(genData());
    }

    private List<Movie> genData() {
        ArrayList<Movie> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            String name = "Chad " + i;
            int price = random.nextInt(10) + 10;
            int len = random.nextInt(80) + 60;
            Movie movie = new Movie(name, len, price, "He was one of Australia's most distinguished artistes");
            list.add(movie);
        }
        return list;
    }
}
