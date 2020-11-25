package com.application.image.lib_ui.material_design.viewpager.adapter.fragmentpageradapter.b;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.application.image.lib_ui.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;




/**
 * name: wy
 * date: 2020/9/25
 * desc: 人脸列表(黑名单、白名单、员工名单)
 */
public class FaceListFragment extends Fragment implements FaceListAdapter.OnClickListener, XRecyclerView.LoadingListener {
    private int page = 1;
    private static final int PAGE_SIZE = 20;
    private String fragmetType;
    private XRecyclerView recyclerView;
    private List<FACE_LIST_DATA_> faceDatas = new ArrayList<>();
    private int clickPosition;
    private DialogOnSureListener mDialogOnSureListener;
    private FaceListAdapter adapter;
    private static final String TAG = "FaceListFragment";
    private int clickUploadPosition;
    private static final int FACE_HAS_UPLOAD = 1;
    private Context mContext;

    public static FaceListFragment newInstance(String type) {
        FaceListFragment fragment = new FaceListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e(TAG,"onCreate enter");
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) return;
        this.fragmetType = bundle.getString("type");
        initData();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onResume() {
        Log.e(TAG,"onResume enter");
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(TAG,"onCreateView enter");
        return inflater.inflate(R.layout.fragment_face_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.e(TAG,"onViewCreated enter");
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new FaceListAdapter(getContext(), faceDatas);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 5, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        adapter.setOperationListener(this);
        recyclerView.setPullRefreshEnabled(true);
        recyclerView.setLoadingMoreEnabled(true);
        recyclerView.setLoadingListener(this);
    }

    private void initData() {
        page = 1;
        getFaceData();
    }

    /**
     * 上拉刷新  存在bug  源码不能回调
     */
    @Override
    public void onRefresh() {
        page = 1;
        Log.e(TAG,"onRefresh enter");
        getFaceData();
    }

    /**
     * 下拉加载
     */
    @Override
    public void onLoadMore() {
        page ++;
        Log.e(TAG,"onLoadMore enter");
        getFaceData();
    }

    private void delDbFace() {
//        DbControllerFactory.getInstance().getFaceListDataController().deleteByFaceId(faceDatas.get(clickPosition).getFaceId());
//        if (getContext() == null) return;
//        ((Activity)getContext()).runOnUiThread(() -> {
//            faceDatas.remove(clickPosition);
//            adapter.notifyDataSetChanged();
//            CustomToast.toastLong(TIP_SUCCESS, getString(R.string.delete_success));
//            if (mDialogOnSureListener != null) {
//                mDialogOnSureListener.diss();
//            }
//        });
    }

    /**
     * 删除
     */
    @Override
    public void onDelete(int position) {
        clickPosition = position;
        if (mDialogOnSureListener != null) {
            mDialogOnSureListener.showDialog(2);
        }
//        RosSendMessage.getInstance().deleteFace(faceDatas.get(position).getFaceId(), new ObserverHandler<FaceUploadOrDelData>() {
//            @Override
//            public void onSuccess(FaceUploadOrDelData data) {
//                delDbFace();
//            }
//
//            @Override
//            public void onError(String code) {
//                CustomToast.toastLong(TIP_SUCCESS, getString(R.string.delete_fail));
//            }
//        });
    }

    private void updateFaceView() {
//        if(getContext() == null) return;
//        ((Activity)getContext()).runOnUiThread(() -> {
//            for (int i =0;i<faceDatas.size();i++) {
//                if (i == clickUploadPosition) {
//                    // 更新上传后的图标
//                    faceDatas.get(i).setUploadState(FACE_HAS_UPLOAD);
////                    DbControllerFactory.getInstance().getFaceListDataController()
////                            .updataByFaceId(faceDatas.get(i).getFaceId(),FACE_HAS_UPLOAD);
//                    adapter.notifyDataSetChanged();
//                    break;
//                }
//            }
//        });
    }

    /**
     * 上传
     */
    @Override
    public void onUpload(int position) {
        Log.e("yhw","onUpload begin = " + position);
        clickUploadPosition = position;
        if (mDialogOnSureListener != null) {
            mDialogOnSureListener.showDialog(5);
        }
//        RosSendMessage.getInstance().uploadFace(faceDatas.get(position).getFaceId(), faceDatas.get(position).getImagePath(),
//                new ObserverHandler<FaceUploadOrDelData>() {
//
//            @Override
//            public void onSuccess(FaceUploadOrDelData data) {
//                Log.e("yhw","onUpload success");
//                updateFaceView();
//            }
//
//            @Override
//            public void onError(String code) {
//                Log.e("yhw","onUpload fail");
//                CustomToast.toastLong(TIP_SUCCESS, getString(R.string.text_upload_fail));
//            }
//        });
    }

    /**
     * 大图预览
     *   /storage/emulated/0/Robot2_new/white_list/w1605088868210.png
     * @param imageUrl 原图url路径地址
     */
    @Override
    public void onBigPhotoView(String imageUrl) {
        if (mContext != null) {
            ArrayList<String> imageList = new ArrayList<>();
            imageList.add(imageUrl);
//            PhotoViewerActivity.startPhotoViewerActivity(mContext, imageList, 0, IMAGETYPE_LOCAL);
        }
    }

    private void getFaceData() {
//        DbControllerFactory.getInstance().getFaceListDataController().queryPageData(page, PAGE_SIZE, fragmetType, (state, object) -> {
//            if (page == 1) {
//                faceDatas.clear();
//            }
//            faceDatas.addAll(object);
//            if(object.size() == 0){
//                recyclerView.setLoadingMoreEnabled(false);
//            }
//            adapter.notifyDataSetChanged();
//            initialization();
//        });
    }

    private void initialization(){
        recyclerView.loadMoreComplete();
        recyclerView.refreshComplete();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void setDialogOnSureListener(DialogOnSureListener dialogOnSureListener) {
        mDialogOnSureListener = dialogOnSureListener;
    }

    public interface DialogOnSureListener {

        /**
         * 弹出对话框类型
         * @param type 1：清除所有  2：白名单   3：黑名单
         */
        void showDialog(int type);

        void diss();
    }

    public void delFaceAll(){
//        if (getContext() == null) return;
//        ((Activity)getContext()).runOnUiThread(() -> {
//            faceDatas.clear();
//            adapter.notifyDataSetChanged();
//        });
    }
}