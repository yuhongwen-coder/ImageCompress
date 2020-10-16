package com.application.image.lib_ui.material_design.viewpager.face;

import android.os.Bundle;
import android.os.Environment;
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
import com.application.image.lib_ui.material_design.recylerview.FACE_LIST_DATA_Test;
import com.application.image.lib_ui.utils.AssetsSourceUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

/**
 * Created by yuhongwen
 * on 2020/10/15
 */
public class FaceListFragment extends Fragment implements XRecyclerView.LoadingListener{

    private String fragmetType;
    private XRecyclerView recyclerView;
    private FaceListAdapter adapter;

    private final String face_white_list = "white_face_list.json";
    private List<FACE_LIST_DATA_Test> faceDatas;

    /**
     * 关键方法 可以通过这个方式存入 Fragment 参数 这样在 Fragment 运行生命周期 onCreate() 时候，可以从 Arguments
     *  中 获取参数，并根据参数来区分 不同的 Fragment
     * @return
     */
    public static FaceListFragment newInstance(String fragmentType) {
        Bundle args = new Bundle();
        args.putString("type", fragmentType);
        FaceListFragment fragment = new FaceListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("yhw","FaceListFragment onCreate");
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle == null) return;
        this.fragmetType = bundle.getString("type");
        initData();
    }

    private void initData() {
        String whiteFace = AssetsSourceUtils.getJsonString(face_white_list);
        String srcImagePath = Environment.getExternalStorageDirectory() + "/" + "Robot2"+"/" + "alarm_blacklist";
        List<String> pathList = AssetsSourceUtils.getPicturesTest(srcImagePath);
        Gson gson = new Gson();
        List<FACE_LIST_DATA_Test> dataList = gson.fromJson(whiteFace,  new TypeToken<List<FACE_LIST_DATA_Test>>(){
        }.getType());
        for (int i = 0; i<dataList.size();i++) {
            dataList.get(i).setImagePath(pathList.get(i));
        }
        faceDatas.addAll(dataList);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("yhw","FaceListFragment onCreateView");
        return inflater.inflate(R.layout.fragment_face_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.e("yhw","FaceListFragment onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);

        adapter = new FaceListAdapter(getContext(), faceDatas);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 5, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.setPullRefreshEnabled(true);
        recyclerView.setLoadingMoreEnabled(true);
        recyclerView.setLoadingListener(this);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
