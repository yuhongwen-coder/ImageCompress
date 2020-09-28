package com.application.image.lib_imagecompress;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yuhongwen
 * on 2020/9/28
 */
public interface InputStreamProvider {

    InputStream open() throws IOException;

    String getPath();
}
