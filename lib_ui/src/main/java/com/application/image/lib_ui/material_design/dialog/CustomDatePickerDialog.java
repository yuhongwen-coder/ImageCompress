package com.application.image.lib_ui.material_design.dialog;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * Created by yuhongwen
 * on 2020/10/23
 *  时间选择器
 */
public class CustomDatePickerDialog extends DatePickerDialog {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public CustomDatePickerDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDatePickerDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public CustomDatePickerDialog(@NonNull Context context, @Nullable OnDateSetListener listener, int year, int month, int dayOfMonth) {
        super(context, listener, year, month, dayOfMonth);
    }
}
