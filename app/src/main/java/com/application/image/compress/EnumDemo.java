package com.application.image.compress;

import android.text.TextUtils;

/**
 * Created by yuhongwen
 * on 2020/11/26
 */
public class EnumDemo {
    private enum FavlorType {
        ALPHA("alpha"),
        TJFYHG("tjfyhg"),
        TJXYHG("TJXYHG");

        private final String mValue;

        FavlorType(String value) {
            this.mValue = value;
        }

        public static FavlorType valueof(String value) {
            FavlorType type;
            if (TextUtils.equals(value,"alpha")) {
                type = ALPHA;
            } else if (TextUtils.equals(value,"tjfyhg")) {
                type = TJFYHG;
            } else if (TextUtils.equals(value,"TJXYHG")) {
                type = TJXYHG;
            } else {
                type = ALPHA;
            }
            return type;
        }
    }
}
