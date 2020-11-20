package com.application.image.lib_ui.material_design.recylerview.multi_adapter;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.application.image.lib_ui.R;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.adapter.TranslationChatAdapter;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.bean.SupportLanguageBean;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.bean.TranslationMsgBean;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.dialog.SelectLanguageDialog;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.manager.PreferencesHelper;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.view.RippleView;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.view.RobotVoicePlayAnimation;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.view.RoundedImageView;
import com.application.image.lib_ui.utils.AssetsSourceUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static com.application.image.lib_ui.material_design.recylerview.multi_adapter.LanguageConstants.LANGUAGE_AR;
import static com.application.image.lib_ui.material_design.recylerview.multi_adapter.LanguageConstants.LANGUAGE_EN;
import static com.application.image.lib_ui.material_design.recylerview.multi_adapter.LanguageConstants.LANGUAGE_JSON;
import static com.application.image.lib_ui.material_design.recylerview.multi_adapter.LanguageConstants.LAST_ORIGIN_LANGUAGE;
import static com.application.image.lib_ui.material_design.recylerview.multi_adapter.LanguageConstants.LAST_TARGET_LANGUAGE;
import static com.application.image.lib_ui.material_design.recylerview.multi_adapter.LanguageConstants.TARGET_CONTENT;


public class ChatMessageActivityCopy extends AppCompatActivity implements SelectLanguageDialog.DialogDismissEvent {
    private SelectLanguageDialog selectLanguageDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message_copy);
        configDialogFragment();
        findViewById(R.id.iv_iv_up_arrow_parent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                Bundle bundle = new Bundle();
                selectLanguageDialog.setArguments(bundle);
                // bug fix Fragment already added: SelectLanguageDialog
                if (!selectLanguageDialog.isAdded()) {
                    selectLanguageDialog.show(fm, "");
                }
            }
        });

    }

    private void configDialogFragment() {
        selectLanguageDialog = new SelectLanguageDialog();
        selectLanguageDialog.setDialogListener(this);
    }

    @Override
    public void pushLangague() {

    }
}