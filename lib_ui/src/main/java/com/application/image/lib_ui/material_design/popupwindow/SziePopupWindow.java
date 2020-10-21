package com.application.image.lib_ui.material_design.popupwindow;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.image.lib_ui.R;

import java.util.List;
import java.util.Map;

/**
 * Created by yuhongwen
 * on 2020/10/20
 */
public class SziePopupWindow extends PopupWindow implements BoxSizeAdapter.ClickListener {
    private Map<String,Integer> sizeMap;
    private RecyclerView sizeRecylerView;
    private BoxSizeAdapter.ClickListener listener;


    public SziePopupWindow(Context context,Map<String,Integer> boxSize) {
        super(context);
        sizeMap = boxSize;
        initView(context);
    }

    public SziePopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SziePopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.box_zise_popupwindow,null);
        sizeRecylerView = view.findViewById(R.id.box_size_list);
        BoxSizeAdapter adapter = new BoxSizeAdapter(context,sizeMap);
        adapter.setClickListener(this);
        sizeRecylerView.setLayoutManager(new LinearLayoutManager(context));
        sizeRecylerView.setAdapter(adapter);
        setContentView(view);
    }

    public void setClickListener(BoxSizeAdapter.ClickListener listener) {
        this.listener = listener;
    }

    public void dismissWindow() {
        dismiss();
    }


    @Override
    public void onClick(int position) {
        listener.onClick(position);
    }
}
