package com.application.image.lib_ui.material_design.viewpager.adapter.fragmentpageradapter.b;

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
 * name: wy
 * date: 2020/9/25
 * desc:
 */
public class FaceListAdapter extends RecyclerView.Adapter<FaceListAdapter.ViewHolder> {

    private Context mContext;
    private List<FACE_LIST_DATA_> faceDatas;
    private LayoutInflater inflate;

    private OnClickListener mOnClickListener;

    public FaceListAdapter(Context context, List<FACE_LIST_DATA_> list) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final FACE_LIST_DATA_ data = faceDatas.get(position);
        // 引入图片压缩算法
        Glide.with(mContext).load(data.getImagePath()).apply(new RequestOptions().placeholder(R.drawable.kzjc_ic_placeholder).diskCacheStrategy(DiskCacheStrategy.NONE)).into(holder.ivFacelibPic);
        holder.tvFacelibName.setText(data.getName());
        holder.ivUpload.setVisibility(data.getUploadState() == 0 ? View.VISIBLE : View.GONE);

        //删除
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnClickListener != null) {
                    mOnClickListener.onDelete(position);
                }
            }
        });
        //上传
        holder.ivUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    mOnClickListener.onUpload(position);
                }
            }
        });

        holder.ivFacelibPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    mOnClickListener.onBigPhotoView(data.getImagePath());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return faceDatas.size();
    }

    public interface OnClickListener {
        void onDelete(int position);

        void onUpload(int position);

        void onBigPhotoView(String imageUrl);
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