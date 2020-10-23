package com.application.image.lib_ui.material_design.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * Created by yuhongwen
 * on 2020/10/15
 *
 *    https://blog.csdn.net/lmj623565791/article/details/37815413/
 *
 *    1、 概述
 *      DialogFragment在android 3.0时被引入。是一种特殊的Fragment，用于在Activity的内容之上展示一个模态的对话框。典型的用于：展示警告框，输入框，确认框等等。
 *      在DialogFragment产生之前，我们创建对话框：一般采用AlertDialog和Dialog。注：官方不推荐直接使用Dialog创建对话框。
 *   2、 好处与用法
 *     使用DialogFragment来管理对话框，当旋转屏幕和按下后退键时可以更好的管理其声明周期，
 *     它和Fragment有着基本一致的声明周期。且DialogFragment也允许开发者把Dialog作为内嵌的组件进行重用，
 *     类似Fragment（可以在大屏幕和小屏幕显示出不同的效果）。上面会通过例子展示这些好处~
 *     使用DialogFragment至少需要实现onCreateView或者onCreateDIalog方法。
 *     onCreateView即使用定义的xml布局文件展示Dialog。onCreateDialog即利用AlertDialog或者Dialog创建出Dialog。
 *
 */
public class DialogFragmentActivity extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}
