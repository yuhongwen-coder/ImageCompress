package com.application.image.lib_ui.material_design.viewpager.adapter.fragmentpageradapter.a;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.application.image.lib_ui.R;

/**
 * author: glc.
 * Date: 2019/12/26 11:09
 * Description:
 */
public class FaceCaptureAdapter extends PagedListAdapter<FaceAlarm,FaceCaptureAdapter.FaceViewHolder> {

    private Context mContext;

    public FaceCaptureAdapter(Context context) {
        super(diff);
        this.mContext = context;
    }

    private static final DiffUtil.ItemCallback<FaceAlarm> diff = new DiffUtil.ItemCallback<FaceAlarm>() {
        @Override
        public boolean areItemsTheSame(@NonNull FaceAlarm oldItem, @NonNull FaceAlarm newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull FaceAlarm oldItem, @NonNull FaceAlarm newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public FaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_alarm_face_capture,parent,false);
        return new FaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaceViewHolder holder, int position) {
        FaceAlarm item = getItem(position);
//        holder.tvFaceName.setText(item.getAlarmName());
//        holder.tvFaceSexy.setText(item.getAlarmSex());
//        holder.tvFaceAge.setText(item.getAlarmAge()+"岁");
//        holder.tvFaceLevel.setText(item.getAlarmLevel());
//        holder.tvFaceSimilar.setText(item.getScore()+"%");
//        holder.tvFaceCertNo.setText(item.getAlarmCardId());
//        holder.tvFacePassTime.setText(item.getAlarmPassTime());
//        ImageViewr.showImage(mContext,item.getCardPhotoUrl(),holder.ivFace);
//        ImageViewr.showImage(mContext,item.getCapturePhotoUrl(),holder.ivCapture);
    }

    public class FaceViewHolder extends RecyclerView.ViewHolder {

//        @BindView(R.id.iv_face)
//        ImageView ivFace;
//        @BindView(R.id.iv_capture)
//        ImageView ivCapture;
//        @BindView(R.id.tv_face_name)
//        TextView tvFaceName;
//        @BindView(R.id.tv_face_sexy)
//        TextView tvFaceSexy;
//        @BindView(R.id.tv_face_age)
//        TextView tvFaceAge;
//        @BindView(R.id.tv_face_level)
//        TextView tvFaceLevel;
//        @BindView(R.id.tv_face_similar)
//        TextView tvFaceSimilar;
//        @BindView(R.id.tv_face_cert_no)
//        TextView tvFaceCertNo;
//        @BindView(R.id.tv_face_pass_time)
//        TextView tvFacePassTime;

        public FaceViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
