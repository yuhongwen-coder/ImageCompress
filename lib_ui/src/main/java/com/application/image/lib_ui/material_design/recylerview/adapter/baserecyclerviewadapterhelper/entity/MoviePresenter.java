package com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.entity;

import android.view.View;

import com.application.image.lib_ui.material_design.recylerview.adapter.baserecyclerviewadapterhelper.utils.Tips;

/**
 * Created by luoxiongwen on 16/10/24.
 */

public class MoviePresenter {
    public void buyTicket(View view, Movie movie) {
        Tips.show("buy ticket: " + movie.name);
    }
}
