package com.application.image.lib_ui.material_design.recylerview.multi_adapter.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.application.image.lib_ui.R;

import com.application.image.lib_ui.material_design.recylerview.multi_adapter.bean.SupportLanguageBean;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.view.MarqueeTextView;

import java.util.List;

import static com.application.image.lib_ui.material_design.recylerview.multi_adapter.LanguageConstants.LANGUAGE_AR;
import static com.application.image.lib_ui.material_design.recylerview.multi_adapter.LanguageConstants.LANGUAGE_EN;


/**
 * Created by      Android studio
 *
 * @author :chenzheming (from Center Of Wuhan)
 * 创建时间 :2020/9/28$
 * 更新时间 :
 * Version :1.0
 * 功能描述 :
 **/
public class LanguageAdapter extends CommonAdapter<SupportLanguageBean>{
    private OnClick onClick;
    private int posInOut;
    public LanguageAdapter(Context context, int layoutId, List<SupportLanguageBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(final ViewHolder holder, final SupportLanguageBean cityBean) {
        holder.setText(R.id.tv_name,cityBean.getZhFullName()+ "  "+cityBean.getName2());
        holder.setText(R.id.tv_languga_code,cityBean.getIcao_code());
        holder.getView(R.id.ll_root)
                .setBackgroundResource(cityBean.isCheck()? R.drawable.bg_language_corner:R.drawable.bg_language_corner_unselect);
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClick != null) {
                    // 排斥一个语言重复选择
                    if (cityBean.isCheck()) {
                        return;
                    }
                    onClick.onClick(cityBean, LanguageAdapter.this.getPosition(holder), posInOut);
                }
            }
        });
        if (cityBean.isCheck()){
            MarqueeTextView tvName = holder.getView(R.id.tv_name);
            tvName.setMovementMethod(LinkMovementMethod.getInstance());
            tvName.setEllipsize(TextUtils.TruncateAt.valueOf("MARQUEE"));  // 添加跑马灯功能
            tvName.setMarqueeRepeatLimit(Integer.MAX_VALUE);
        }
        configCountryIcon((ImageView) holder.getView(R.id.iv_country),cityBean);
    }

    private void configCountryIcon(ImageView imageView, SupportLanguageBean bean) {
        String imageUrl = bean.getImageUrl();
        if (TextUtils.isEmpty(imageUrl)) {
            if (TextUtils.equals(bean.getEnName(), LANGUAGE_EN)) {
                imageView.setImageResource(mContext.getResources().getIdentifier("common_en", "mipmap", mContext.getPackageName()));
            } else if (TextUtils.equals(bean.getEnName(), LANGUAGE_AR)) {
                imageView.setImageResource(mContext.getResources().getIdentifier("common_ar", "mipmap", mContext.getPackageName()));
            } else {

            }
            return;
        }
        imageView.setImageResource(mContext.getResources().getIdentifier(imageUrl, "mipmap", mContext.getPackageName()));
    }

    public interface  OnClick{
        void onClick(SupportLanguageBean cityBean, int pos, int posInOut);
    }

    public void setOnClick(OnClick onClick,int pos){
        this.onClick =onClick;
        this.posInOut =pos;
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
//        MarqueeView tvName= holder.getView(R.id.tv_name);
//        if (tvName != null) {
//            tvName.stopFlipping();
//        }
    }

}
