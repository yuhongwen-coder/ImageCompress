package com.application.image.lib_ui.material_design.popupwindow;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.application.image.lib_ui.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PopupWindowDemoActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, BoxSizeAdapter.ClickListener {

    private static final String TYPE_RESET = "type_reset";
    private static final String TYPE_ROBOT = "type_robot";
    private static final String TYPE_STOP = "type_stop";
    private static final String START_SPORT = "start_sport";
    private String mBaseUrl = "http://192.168.51.66:8080";
    private static final String BOX_IS_EMPTY = "box_is_empty";
    private static final String BOX_SIZE = "box_size";
    private static final String INIT_ACTION = "init_action";
    private static final String START_ACTION = "start_action";

    private static final String SELECT_BOX_EMPTY = "true";
    private static final String SELECT_BOX_NOT_EMPTY = "false";
    private OkHttpClient mOkHttpClient;


    private Button selectBoxSizeBtn;


    public static final String POPUP_WNDOW_TYPE_ISEMPTY = "isEmpty";
    public static final String POPUP_WNDOW_TYPE_BOXSIZE = "boxSize";
    private RadioGroup radioGroup;


    private BoxModel boxModel;
    private HashMap<String, Integer> boxSize;
    private SziePopupWindow popupWindow;
    private ArrayList<String> boxSizeList;
    private RadioButton emptyRadioBtn;
    private TextView requestText;
    private TextView responseText;
    private TextView exceptionText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_popupwindow);
        selectBoxSizeBtn = findViewById(R.id.box_size_select);
        emptyRadioBtn = findViewById(R.id.rbtn_empty_box);
        requestText = findViewById(R.id.request_text);
        responseText = findViewById(R.id.response_text);
        exceptionText = findViewById(R.id.exception_text);

        findViewById(R.id.reset).setOnClickListener(this);
        findViewById(R.id.restart).setOnClickListener(this);
        findViewById(R.id.stop).setOnClickListener(this);
        findViewById(R.id.start).setOnClickListener(this);

        findViewById(R.id.box_size_select).setOnClickListener(this);
        radioGroup = findViewById(R.id.box_radio_group);
        radioGroup.setOnCheckedChangeListener(this);
        configData();
    }

    private void configData() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        mOkHttpClient = builder.build();
        boxModel = new BoxModel();
        boxSize = new HashMap<String, Integer>();
        boxSize.put("20尺", 2000);
        boxSize.put("20尺单前", 2010);
        boxSize.put("20尺单后", 2001);
        boxSize.put("20尺双前", 2011);
        boxSize.put("20尺双后", 2022);
        boxSize.put("40尺", 4000);
        boxSize.put("40尺高", 4001);
        boxSize.put("45尺", 5005);

        boxSizeList = new ArrayList<String>();
        Set<String> sizeList = boxSize.keySet();
        Iterator<String> keyList = sizeList.iterator();
        while (keyList.hasNext()) {
            boxSizeList.add(keyList.next());
        }

        popupWindow = new SziePopupWindow(this, boxSize);
        popupWindow.setClickListener(this);
        onClick(0);

        emptyRadioBtn.setChecked(true);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.reset) {
            postBoxException(TYPE_RESET);
        } else if (id == R.id.restart) {
            postBoxException(TYPE_ROBOT);
        } else if (id == R.id.stop) {
            postBoxException(TYPE_STOP);
        } else if (id == R.id.start) {
            postSport(START_SPORT);
        } else if (id == R.id.box_is_empty) {
            // 是否空箱
//            showPopupWindow(POPUP_WNDOW_TYPE_ISEMPTY);
        } else if (id == R.id.box_size_select) {
            // 箱体尺寸
            showPopupWindow(POPUP_WNDOW_TYPE_BOXSIZE);
        }
    }

    private void showPopupWindow(String type) {
        if (!popupWindow.isShowing()) {
            popupWindow.showAsDropDown(selectBoxSizeBtn);
        }
    }

    /**
     * 其中正常运行的正文内容中除了b0命令字, startSport
     * 还有后面两个字节,
     * 分别代表的是集装箱尺寸 boxSize
     * 和是否空箱. isEmpty
     *
     * @param startSport
     */
    private void postSport(String startSport) {
//        String url = mBaseUrl + "device_sport";
//        String url = mBaseUrl;
        String url = mBaseUrl + "/test";
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonString = new JSONObject();
        try {
            jsonString.put(START_ACTION, startSport);
            jsonString.put(BOX_SIZE, boxModel.getBoxSize());
            jsonString.put(BOX_IS_EMPTY, boxModel.getBoxIsEmpty());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(type, String.valueOf(jsonString));
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        requestText.setText(request.body().toString()  + "\n jsonString = " + jsonString + "\n url = " + url);
        try {
            Response response = mOkHttpClient.newCall(request).execute();
            responseText.setText(response.body().toString());
        } catch (IOException e) {
            exceptionText.setText(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 异常模式  0xb1	0xb2	0xb3	0xb3
     * 暂停	复位	重启	停止
     *
     * @param typeStop
     */
    private void postBoxException(String typeStop) {
//        String url = mBaseUrl + "device_init";
        String url = mBaseUrl;
        MediaType type = MediaType.parse("application/json; charset=utf-8");
        JSONObject jsonString = new JSONObject();
        try {
            jsonString.put(INIT_ACTION, typeStop);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(type, String.valueOf(jsonString));
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        try {
            Response response = mOkHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rbtn_empty_box) {
            boxModel.setBoxIsEmpty(SELECT_BOX_EMPTY);
        } else if (checkedId == R.id.rbtn_full_box) {
            boxModel.setBoxIsEmpty(SELECT_BOX_NOT_EMPTY);
        }
    }

    @Override
    public void onClick(int position) {
        selectBoxSizeBtn.setText(boxSizeList.get(position));
        boxModel.setBoxSize(boxSize.get(boxSizeList.get(position)));
        popupWindow.dismiss();
    }
}