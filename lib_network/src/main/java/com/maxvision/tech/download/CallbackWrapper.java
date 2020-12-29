package com.maxvision.tech.download;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;



import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by ml on 2016/9/2.
 */
public class CallbackWrapper implements Callback {

    private static Handler handler = new Handler(Looper.getMainLooper());

    private OnHttpListener listener;

    public CallbackWrapper(OnHttpListener listener) {
        this.listener = listener;
    }

    @Override
    public void onFailure(Call call, IOException e) {
//        this.onHttpFailure(ErrorEvent.Code.REQUEST_FAILURE, e.getMessage());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        int code = response.code();
        String result = response.body().string();

        if (response.isSuccessful()) {
            this.onHttpSuccess(code, result);
        } else {
            this.onHttpFailure(code, result);
        }
    }

    private void onHttpSuccess(final int code, final String result) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d("onHttpSuccess", "code=" + code);
                Log.d("onHttpSuccess", result);
                if (listener != null) {
                    listener.onHttpSuccess(result);
                }
            }
        });
    }

    private void onHttpFailure(final int code, final String error) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d("onHttpFailure", "code=" + code);
                if(error != null) {
                    Log.d("onHttpFailure", error);
                }
                if (listener != null) {
                    listener.onHttpFailure(code, error);
                }
            }
        });
    }

    public interface OnHttpListener {

        void onHttpFailure(int code, String error);

        void onHttpSuccess(String result);
    }
}
