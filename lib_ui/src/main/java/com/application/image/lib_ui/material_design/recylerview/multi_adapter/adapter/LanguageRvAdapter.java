package com.application.image.lib_ui.material_design.recylerview.multi_adapter.adapter;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.application.image.lib_ui.R;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.bean.InitialSupportLanguageBean;


import java.util.List;

/**
 * Created by      Android studio
 *
 * @author :chenzheming (from Center Of Wuhan)
 * 创建时间 :2020/9/29$
 * 更新时间 :
 * Version :1.0
 * 功能描述 :
 **/
public class LanguageRvAdapter extends CommonAdapter<InitialSupportLanguageBean>{
    private LanguageAdapter.OnClick onClick;
    private LanguageAdapter adapter;

    public LanguageRvAdapter(Context context, int layoutId, List<InitialSupportLanguageBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, final InitialSupportLanguageBean cityBean) {
        RecyclerView recyclerView = holder.getView(R.id.rvCity);
        recyclerView.setNestedScrollingEnabled(false);
        adapter = new LanguageAdapter(mContext, R.layout.item_select_language, cityBean.getSupportLanguageBeans());
        recyclerView.setAdapter(adapter);
        adapter.setOnClick(onClick,getPosition(holder));
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 5));
    }

    public void setOnClick(LanguageAdapter.OnClick onClick){
        this.onClick =onClick;
    }


    public void myNotify() {
        notifyDataSetChanged();
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }
}
