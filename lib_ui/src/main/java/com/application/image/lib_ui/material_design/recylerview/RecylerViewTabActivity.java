package com.application.image.lib_ui.material_design.recylerview;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.application.image.lib_ui.R;
import com.application.image.lib_ui.utils.AssetsSourceUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuhongwen
 * on 2020/9/29
 */
public class RecylerViewTabActivity extends AppCompatActivity implements FaceListAdapter.OnClickListener, XRecyclerView.LoadingListener{
    private static final String TAG = "RecylerViewTabActivity";
    private XRecyclerView recyclerView;
    private FaceListAdapter adapter;
    private final String face_white_list = "white_face_list.json";
    private ArrayList<BaseFaceListTest> faceDatas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xrecylerview_demo);
        initData();
        configXRecylerView();
    }

    private void initData() {
        String whiteFace = AssetsSourceUtils.getJsonString(face_white_list);
        String srcImagePath = Environment.getExternalStorageDirectory() + "/" + "Robot2"+"/" + "alarm_blacklist";
        List<String> pathList = getPicturesTest(srcImagePath);
        Gson gson = new Gson();
        List<FACE_LIST_DATA_Test> dataList = gson.fromJson(whiteFace,  new TypeToken<List<FACE_LIST_DATA_Test>>(){
        }.getType());
        for (int i = 0; i<dataList.size();i++) {
            dataList.get(i).setImagePath(pathList.get(i));
        }
        faceDatas.addAll(dataList);
    }

    private void configXRecylerView() {
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new FaceListAdapter(this, faceDatas);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        adapter.setOperationListener(this);
        recyclerView.setPullRefreshEnabled(true);
        recyclerView.setLoadingMoreEnabled(true);
        recyclerView.setLoadingListener(this);
    }

    /**
     *  测试函数
     * @param strPath
     * @return
     */
    private List<String> getPicturesTest(final String strPath) {
        List<String> list = new ArrayList<String>();
        File file = new File(strPath);
        File[] allfiles = file.listFiles();
        if (allfiles == null) {
            return null;
        }
        for(int i = 0; i < allfiles.length; i++) {
            final File fi = allfiles[i];
            if(fi.isFile()) {
                int idx = fi.getPath().lastIndexOf(".");
                if (idx <= 0) {
                    continue;
                }
                String suffix = fi.getPath().substring(idx);
                if (suffix.toLowerCase().equals(".jpg") ||
                        suffix.toLowerCase().equals(".jpeg") ||
                        suffix.toLowerCase().equals(".bmp") ||
                        suffix.toLowerCase().equals(".png") ||
                        suffix.toLowerCase().equals(".gif") ) {
                    list.add(fi.getPath());
                }
            }
        }
        return list;
    }

    @Override
    public void onDelete(int position) {

    }

    @Override
    public void onUpload(int position) {

    }

    @Override
    public void onRefresh() {
        Log.e(TAG,"onRefresh enter");
    }

    @Override
    public void onLoadMore() {
        Log.e(TAG,"onLoadMore enter");
    }
}
