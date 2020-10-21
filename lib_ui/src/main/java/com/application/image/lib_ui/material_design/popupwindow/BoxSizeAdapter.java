package com.application.image.lib_ui.material_design.popupwindow;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.image.lib_ui.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yuhongwen
 * on 2020/10/20
 */
public class BoxSizeAdapter extends RecyclerView.Adapter<BoxSizeAdapter.SizeViewHolder> {
    private Context context;
    private ClickListener clickListener;
    private ArrayList<String> boxSizeList;


    public BoxSizeAdapter(Context context, Map<String,Integer> boxSizeMap) {
        Log.e("yhw","BoxSizeAdapter enter");
        this.context = context;
        parseKeyValue(boxSizeMap);
    }

    private void parseKeyValue(Map<String,Integer> boxSizeMap) {
        boxSizeList = new ArrayList<String>();
        Set<String> sizeList = boxSizeMap.keySet();
        Iterator<String> keyList = sizeList.iterator();
        while (keyList.hasNext()) {
            boxSizeList.add(keyList.next());
        }
    }

    @NonNull
    @Override
    public SizeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e("yhw","onCreateViewHolder enter");
        View view = LayoutInflater.from(context).inflate(R.layout.item_size_list, parent, false);
        return new SizeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SizeViewHolder holder, final int position) {
        Log.e("yhw","onBindViewHolder enter");
        holder.sizeItemText.setText(boxSizeList.get(position));
        holder.sizeItemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        Log.e("yhw","getItemCount enter");
        return boxSizeList.size();
    }

    protected static class SizeViewHolder extends RecyclerView.ViewHolder{

        private View sizeItemLine;
        private TextView sizeItemText;

        public SizeViewHolder(@NonNull View itemView) {
            super(itemView);
            sizeItemText = itemView.findViewById(R.id.size_item);
            sizeItemLine = itemView.findViewById(R.id.size_item_line);
        }
    }

    public void setClickListener(ClickListener listener) {
        clickListener = listener;
    }
    public interface ClickListener {
        void onClick(int position);
    }
}
