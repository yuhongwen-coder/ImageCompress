package com.application.image.lib_ui.material_design.recylerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.application.image.lib_ui.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Created by yuhongwen
 * on 2020/10/12
 */
public class FaceListAdapter extends RecyclerView.Adapter<FaceListAdapter.ViewHolder>{
    private Context mContext;
    private List<BaseFaceListTest> faceDatas;
    private LayoutInflater inflate;

    private OnClickListener mOnClickListener;

    public FaceListAdapter(Context context, List<BaseFaceListTest> list) {
        this.mContext = context;
        this.faceDatas = list;
        inflate = LayoutInflater.from(mContext);
    }

    public void setOperationListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflate.inflate(R.layout.item_face_list, parent, false);
        return new FaceListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaseFaceListTest data = faceDatas.get(position);

        // 引入图片压缩算法
        Glide.with(mContext).load(data.getImagePath()).apply(new RequestOptions().placeholder(R.drawable.ic_placeholder).diskCacheStrategy(DiskCacheStrategy.NONE)).into(holder.ivFacelibPic);
        holder.tvFacelibName.setText(data.getName());
        holder.ivUpload.setVisibility(data.getUploadState() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return faceDatas.size();
    }

    public interface OnClickListener {
        void onDelete(int position);

        void onUpload(int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivFacelibPic;
        TextView tvFacelibName;
        ImageView ivDelete;
        ImageView ivUpload;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFacelibPic = itemView.findViewById(R.id.facelib_pic);
            tvFacelibName = itemView.findViewById(R.id.facelib_name);
            ivDelete = itemView.findViewById(R.id.iv_delete);
            ivUpload = itemView.findViewById(R.id.iv_upload);
        }
    }
}
