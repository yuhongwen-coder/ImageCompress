package com.application.image.lib_ui.material_design.recylerview.multi_adapter.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.image.lib_ui.R;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.LanguageConstants;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.adapter.HeaderRecyclerAndFooterWrapperAdapter;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.adapter.LanguageAdapter;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.adapter.LanguageRvAdapter;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.adapter.ViewHolder;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.bean.BaseIndexPinyinBean;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.bean.InitialSupportLanguageBean;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.bean.LanguageBean;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.bean.LanguageHeaderBean;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.bean.SupportLanguageBean;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.manager.PreferencesHelper;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.view.DividerItemDecoration;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.view.IndexBar;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.view.MarqueeTextView;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.view.RoundedImageView;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.view.SuspensionDecoration;
import com.application.image.lib_ui.utils.JsonUtils;
import com.application.image.lib_ui.utils.ScreenUtils;
import com.google.gson.Gson;




import java.util.ArrayList;
import java.util.List;

import static com.application.image.lib_ui.material_design.recylerview.multi_adapter.LanguageConstants.LANGUAGE_AR;
import static com.application.image.lib_ui.material_design.recylerview.multi_adapter.LanguageConstants.LANGUAGE_EN;


/**
 *  语言选择 DialogFragment
 */
public class SelectLanguageDialog extends DialogFragment implements View.OnClickListener {

    private LanguageBean languageBean;
    private LinearLayout ll_origin;
    private LinearLayout ll_target;
    private RecyclerView rv_contryList;
    private IndexBar mIndexBar;
    private TextView mTvSideBarHint;
    private MarqueeTextView tv_origin;
    private MarqueeTextView tv_target;
    private ArrayList<LanguageHeaderBean> mHeaderDatas;
    private List<InitialSupportLanguageBean> mBodyDatas;
    private LanguageRvAdapter mAdapter;
    private HeaderRecyclerAndFooterWrapperAdapter mHeaderAdapter;
    private LanguageAdapter hotAdapter;
    private boolean isOrigin;
    private ImageView iv_close;
    private DialogDismissEvent dismisDialog;
    private Gson gson = new Gson();
    private Context mContext;
    private RoundedImageView originIcon;
    private RoundedImageView targetIcon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_language, container);
        Bundle arguments = getArguments();
        if (arguments!=null){
            String languages = arguments.getString(LanguageConstants.JSON_LANGUAGE_STR);
            languageBean = JsonUtils.parseJson(languages, LanguageBean.class);
        }else{
            dismiss();
        }
        initViews(view);
        return view;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void initViews(View view) {
        isOrigin = true;
        ll_origin = view.findViewById(R.id.ll_origin);
        ll_target = view.findViewById(R.id.ll_target);
        rv_contryList = view.findViewById(R.id.rv_contryList);
        mIndexBar = view.findViewById(R.id.indexBar);
        mTvSideBarHint = view.findViewById(R.id.tvSideBarHint);
        tv_origin = view.findViewById(R.id.tv_origin);
        tv_target = view.findViewById(R.id.tv_target);
        iv_close = view.findViewById(R.id.iv_close);
        originIcon = view.findViewById(R.id.origin_country_icon);
        targetIcon = view.findViewById(R.id.target_country_icon);
        initRecycler();
        initListener();
    }

    private void initListener() {
        ll_origin.setOnClickListener(this);
        ll_target.setOnClickListener(this);
        iv_close.setOnClickListener(this);
    }

    private boolean orginEqualsTarget(SupportLanguageBean cityBean) {
        SupportLanguageBean orginBean = gson.fromJson(PreferencesHelper.getData(LanguageConstants.LAST_ORIGIN_LANGUAGE),
                SupportLanguageBean.class);
        SupportLanguageBean targetBean = gson.fromJson(PreferencesHelper.getData(LanguageConstants.LAST_TARGET_LANGUAGE),
                SupportLanguageBean.class);
        if (TextUtils.equals(cityBean.getLanguage_number(),
                orginBean.getLanguage_number())) {
            Toast.makeText(mContext,"请不要将识别语言和翻译语言设置成一致的",Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.equals(cityBean.getLanguage_number(),
                targetBean.getLanguage_number())) {
            Toast.makeText(mContext,"请不要将识别语言和翻译语言设置成一致的",Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private void configCountryIcon(ImageView imageView, SupportLanguageBean bean) {
        String imageUrl = bean.getImageUrl();
        if (TextUtils.isEmpty(imageUrl)) {
            if (TextUtils.equals(bean.getEnName(), LANGUAGE_EN)) {
                imageView.setImageResource(getResources().getIdentifier("common_en", "mipmap", mContext.getPackageName()));
            } else if (TextUtils.equals(bean.getEnName(),LANGUAGE_AR)) {
                imageView.setImageResource(getResources().getIdentifier("common_ar", "mipmap", mContext.getPackageName()));
            }
            return;
        }
        imageView.setImageResource(getResources().getIdentifier(imageUrl, "mipmap", mContext.getPackageName()));
    }

    private void initRecycler() {
        tv_target.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tv_target.setMarqueeRepeatLimit(-1);
        tv_origin.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tv_origin.setMarqueeRepeatLimit(-1);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rv_contryList.setLayoutManager(manager);
        rv_contryList.setNestedScrollingEnabled(false);
        ArrayList<BaseIndexPinyinBean> mSourceDatas = new ArrayList<>();
        mHeaderDatas = new ArrayList<>();
        mBodyDatas = languageBean.getLanguage();
        List<SupportLanguageBean> hot_language = languageBean.getHot_language();
        String lastOriginLanguage = PreferencesHelper.getData(LanguageConstants.LAST_ORIGIN_LANGUAGE);
        if (!TextUtils.isEmpty(lastOriginLanguage)) {
            SupportLanguageBean bean = gson.fromJson(lastOriginLanguage, SupportLanguageBean.class);
            tv_origin.setText(bean.getName2());
            configCountryIcon(originIcon,bean);

        }
        String lastTargetLanguage = PreferencesHelper.getData(LanguageConstants.LAST_TARGET_LANGUAGE);
        if (!TextUtils.isEmpty(lastTargetLanguage)) {
            SupportLanguageBean bean = gson.fromJson(lastTargetLanguage, SupportLanguageBean.class);
            tv_target.setText(bean.getName2());
            configCountryIcon(targetIcon,bean);
        }
        mHeaderDatas.add(new LanguageHeaderBean(hot_language, "常用语言", ""));
        mSourceDatas.addAll(mHeaderDatas);
        mIndexBar.getDataHelper().sortSourceDatas(mBodyDatas);
        configInitLanguage(lastOriginLanguage);
        mAdapter = new LanguageRvAdapter(getContext(), R.layout.item_select_language_rv, mBodyDatas);
        //主体 语言点击事件
        mAdapter.setOnClick(new LanguageAdapter.OnClick() {
            @Override
            public void onClick(SupportLanguageBean cityBean, int position, int posInOut) {
                // 排斥识别语言和 合成语音是同一个
                if (SelectLanguageDialog.this.orginEqualsTarget(cityBean)) {
                    return;
                }
                cityBean.setCheck(true);
                if (isOrigin) {
                    tv_origin.setText(cityBean.getName2());
                    SelectLanguageDialog.this.configCountryIcon(originIcon, cityBean);
                } else {
                    tv_target.setText(cityBean.getName2());
                    SelectLanguageDialog.this.configCountryIcon(targetIcon, cityBean);
                }
                if (isOrigin) {
                    PreferencesHelper.saveData(LanguageConstants.LAST_ORIGIN_LANGUAGE, gson.toJson(cityBean));
                } else {
                    PreferencesHelper.saveData(LanguageConstants.LAST_TARGET_LANGUAGE, gson.toJson(cityBean));
                }
                SelectLanguageDialog.this.setCheckedFromSp(isOrigin);
                SelectLanguageDialog.this.clickReSetRvData();

            }
        });
        mHeaderAdapter = new HeaderRecyclerAndFooterWrapperAdapter(mAdapter) {
            @Override
            protected void onBindHeaderHolder(ViewHolder holder, int headerPos, int layoutId, Object o) {
                if (layoutId == R.layout.meituan_item_header) {
                    final LanguageHeaderBean languageHeaderBean = (LanguageHeaderBean) o;
                    //网格
                    RecyclerView recyclerView = holder.getView(R.id.rvCity);
                    hotAdapter = new LanguageAdapter(getContext(), R.layout.item_select_language, languageHeaderBean.getCityList());

                    //常用语言 选择点击事件
                    hotAdapter.setOnClick(new LanguageAdapter.OnClick() {
                        @Override
                        public void onClick(SupportLanguageBean cityBean, int pos, int posInOut) {
                            // 排斥识别语言和 合成语音是同一个
                            if (orginEqualsTarget(cityBean)) {
                                return;
                            }
                            cityBean.setCheck(true);
                            cityBean.setNeedToShow(false);
                            if (isOrigin) {
                                tv_origin.setText(cityBean.getName2());
                                configCountryIcon(originIcon, cityBean);
                                PreferencesHelper.saveData("lastOriginLanguage", gson.toJson(cityBean));
                            } else {
                                tv_target.setText(cityBean.getName2());
                                configCountryIcon(targetIcon, cityBean);
                                PreferencesHelper.saveData("lastTargetLanguage", gson.toJson(cityBean));
                            }
                            setCheckedFromSp(isOrigin);
                            clickReSetRvData();
                        }
                    }, 0);
                    recyclerView.setAdapter(hotAdapter);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
                }
            }
        };
        mHeaderAdapter.setHeaderView(0, R.layout.meituan_item_header, mHeaderDatas.get(0));
        rv_contryList.setAdapter(mHeaderAdapter);
        rv_contryList.addItemDecoration(new SuspensionDecoration(getContext() == null ? mContext:getContext(), mSourceDatas)
                .setmTitleHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics()))
                .setTitleFontSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 32, getResources().getDisplayMetrics()))
                .setHeaderViewCount(mHeaderAdapter.getHeaderViewCount() - mHeaderDatas.size()));
        rv_contryList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(manager)//设置RecyclerView的LayoutManager
                .setHeaderViewCount(mHeaderAdapter.getHeaderViewCount() - mHeaderDatas.size());
        invalidateRv();
        mSourceDatas.addAll(mBodyDatas);
        mIndexBar.setmSourceDatas(mSourceDatas)//设置数据
                .invalidate();
    }

    /**
     * 用于第一次弹框出现时候，语言的选择
     */
    private void configInitLanguage(String lastOriginLanguage) {
        SupportLanguageBean initBean = gson.fromJson(lastOriginLanguage, SupportLanguageBean.class);
        for (int i = 0; i < mBodyDatas.size(); i++) {
            InitialSupportLanguageBean initialSupportLanguageBean = mBodyDatas.get(i);
            List<SupportLanguageBean> supportLanguageBeans = initialSupportLanguageBean.getSupportLanguageBeans();
            if (supportLanguageBeans != null && supportLanguageBeans.size() > 0){
                for (int i1 = 0; i1 < supportLanguageBeans.size(); i1++) {
                    SupportLanguageBean supportLanguageBean = supportLanguageBeans.get(i1);
                    if (supportLanguageBean.getLanguage_number().equals(initBean.getLanguage_number())){
                        supportLanguageBean.setCheck(true);
                        break;
                    } else {
                        supportLanguageBean.setCheck(false);
                    }
                }
            }
        }
        for (LanguageHeaderBean headHotBeans: mHeaderDatas) {
            List<SupportLanguageBean> hotBeans = headHotBeans.getCityList();
            for (SupportLanguageBean hotBean:hotBeans) {
                if (hotBean.getLanguage_number().equals(initBean.getLanguage_number())) {
                    hotBean.setCheck(true);
                    break;
                }else {
                    hotBean.setCheck(false);
                }
            }
        }
    }

    // 重新设置头部常用语言数据源 并刷新界面
    private void clickReSetRvData() {
        invalidateRv();
    }

    private void invalidateRv() {
        if (mAdapter != null) {
            mAdapter.setDatas(mBodyDatas);
            mAdapter.myNotify();
        }
        if (mHeaderAdapter != null) {
            mHeaderAdapter.notifyDataSetChanged();
        }
        if (hotAdapter != null) {
            hotAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialogFragment = getDialog();
        if (dialogFragment == null) {
            return;
        }
        Window win = dialogFragment.getWindow();
        if (win == null) {
            return;
        }
        // 一定要设置Background，如果不设置，window属性设置无效
        win.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparents)));
        DisplayMetrics dm = new DisplayMetrics();
        if (getActivity() == null || getActivity().getWindowManager() == null) {
            return;
        }
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams params = win.getAttributes();
        params.gravity = Gravity.BOTTOM;
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        int screenHeightPix = ScreenUtils.getScreenHeightPix(getContext() == null ? mContext : getContext());
        params.height = screenHeightPix - 50;
        params.windowAnimations = R.style.BottomToTopAnim;
        win.setAttributes(params);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_origin) {
            isOrigin = true;
            ll_origin.setBackgroundResource(R.drawable.bg_bt_left_corner);
            ll_target.setBackgroundResource(R.drawable.bg_bt_right_corner_unselet);
            setCheckedFromSp(isOrigin);
            clickReSetRvData();
        } else if (id == R.id.ll_target) {
            isOrigin = false;
            ll_target.setBackgroundResource(R.drawable.bg_bt_right_corner);
            ll_origin.setBackgroundResource(R.drawable.bg_bt_left_corner_unselect);
            setCheckedFromSp(isOrigin);
            clickReSetRvData();
        } else if (id == R.id.iv_close) {
            if (dismisDialog != null) {
                dismisDialog.pushLangague();
            }
            dismiss();
        }
    }

    private void setCheckedFromSp(boolean isOrigin) {
        SupportLanguageBean spLanguageBean;
        String  lastOriginLanguage = PreferencesHelper.getData(LanguageConstants.LAST_ORIGIN_LANGUAGE);
        String  lastTargetLanguage = PreferencesHelper.getData(LanguageConstants.LAST_TARGET_LANGUAGE);
        if (isOrigin){
             spLanguageBean = gson.fromJson(lastOriginLanguage, SupportLanguageBean.class);
        }else{
             spLanguageBean = gson.fromJson(lastTargetLanguage, SupportLanguageBean.class);
        }
        if (spLanguageBean == null) {
            return;
        }
        for (int i = 0; i < mBodyDatas.size(); i++) {
            InitialSupportLanguageBean initialSupportLanguageBean = mBodyDatas.get(i);
            List<SupportLanguageBean> supportLanguageBeans = initialSupportLanguageBean.getSupportLanguageBeans();
            if (supportLanguageBeans != null && supportLanguageBeans.size() > 0){
                for (int i1 = 0; i1 < supportLanguageBeans.size(); i1++) {
                    SupportLanguageBean supportLanguageBean = supportLanguageBeans.get(i1);
                    if (supportLanguageBean.getLanguage_number().equals(spLanguageBean.getLanguage_number())){
                        supportLanguageBean.setCheck(true);
                    } else {
                        supportLanguageBean.setCheck(false);
                    }
                }
            }
        }
        configHotLanguage(spLanguageBean);
    }

    private void configHotLanguage(SupportLanguageBean bean) {
        for (LanguageHeaderBean headHotBeans: mHeaderDatas) {
            List<SupportLanguageBean> hotBeans = headHotBeans.getCityList();
            for (SupportLanguageBean hotBean:hotBeans) {
                if (hotBean.getLanguage_number().equals(bean.getLanguage_number())) {
                    hotBean.setCheck(true);
                }else {
                    hotBean.setCheck(false);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        onClick(ll_origin);
    }

    public interface DialogDismissEvent {
        void pushLangague();
    }

    public void setDialogListener(DialogDismissEvent listener) {
        dismisDialog = listener;
    }
}
