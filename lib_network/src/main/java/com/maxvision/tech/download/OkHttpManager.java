package com.maxvision.tech.download;

import android.util.Log;


import androidx.appcompat.view.SupportActionModeWrapper;

import com.application.image.compress.BaseApplication;
import com.maxvision.tech.cookie.CookieJarImpl;
import com.maxvision.tech.cookie.CookieStore;
import com.maxvision.tech.cookie.PersistentCookieStore;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * <p>Name：OKHttpManager</p>
 * <p>okhttp 管理类 </p>
 */

public class OkHttpManager {
    private static MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");
    //创建okHttpClient对象
    private static OkHttpClient mOkHttpClient;

    private static OkHttpManager mInstance;

    private CookieStore cookieStore;

    public static OkHttpManager getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpManager(null);
                }
            }
        }
        return mInstance;
    }

    private OkHttpManager(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
//            LogUtils.i("okHttpClient");
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            okHttpClientBuilder.cookieJar(new CookieJarImpl(getCookieStore()));
            okHttpClientBuilder.connectTimeout(90, TimeUnit.SECONDS);
            okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            mOkHttpClient = okHttpClientBuilder.build();
        } else {
            mOkHttpClient = okHttpClient;
        }
    }

    public CookieStore getCookieStore() {
        if (cookieStore == null) {
            cookieStore = new PersistentCookieStore(BaseApplication.getInstance());
        }
        return cookieStore;
    }

    public boolean hasCookie(){
        List<Cookie> cookies = new PersistentCookieStore(BaseApplication.getInstance()).getCookies();
        if(cookies != null && cookies.size() > 0){
            return true;
        }
        return false;
    }

    public void deleteCookie() {
        if (cookieStore != null) {
            cookieStore.removeAll();
        }
    }

    public void cancelCallsWithTag(Object tag) {
        if (tag == null) {
            return;
        }

        synchronized (mOkHttpClient.dispatcher().getClass()) {
            for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
                if (tag.equals(call.request().tag())) call.cancel();
            }

            for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
                if (tag.equals(call.request().tag())) call.cancel();
            }
        }
    }

    public OkHttpClient getHttpClient() {
        return mOkHttpClient;
    }

    /**
     * 同步访问网络
     *
     * @param request 网络请求
     * @return 返回网络响应
     * @throws IOException
     */
    public Response execute(Request request) throws IOException {
        return getInstance().mOkHttpClient.newCall(request).execute();
    }

    /**
     * 开启异步线程访问网络
     *
     * @param request  网络请求
     * @param listener 网络回调
     */
    public void enqueue(Request request, CallbackWrapper.OnHttpListener listener) {
        getInstance().mOkHttpClient.newCall(request).enqueue(new CallbackWrapper(listener));
    }

    /**
     * 同步访问网络
     *
     * @param url 访问地址
     * @return 返回网络响应
     * @throws IOException
     */
    public String get(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = execute(request);
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * @param url      访问地址
     * @param listener 网络回调
     */
    public void get(String url, CallbackWrapper.OnHttpListener listener) {
        get(null, url, listener);
    }

    /**
     * @param tag      标签
     * @param url      访问地址
     * @param listener 网络回调
     */
    public void get(Object tag, String url, CallbackWrapper.OnHttpListener listener) {
        Log.d("http", url);
        Request request = new Request.Builder().tag(tag).url(url).get().build();
        enqueue(request, listener);
    }

    /**
     * 同步Post请求
     *
     * @param url  访问地址
     * @param body
     * @return 返回网络响应
     * @throws IOException
     */
    public String post(String url, RequestBody body) throws IOException {
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = execute(request);
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * @param url      访问地址
     * @param listener 网络回调
     */
    public void post(String url, CallbackWrapper.OnHttpListener listener) {
        post(null, url, null, listener);
    }

    /**
     * @param url      访问地址
     * @param body     参数
     * @param listener 网络回调
     */
    public void post(String url, RequestBody body, CallbackWrapper.OnHttpListener listener) {
        post(null, url, body, listener);
    }

    /**
     * @param tag      标签
     * @param url      访问地址
     * @param body     body
     * @param listener 网络回调
     */
    public void post(Object tag, String url, RequestBody body, CallbackWrapper.OnHttpListener listener) {
        Log.d("http", url+body.toString());
        Request request = new Request.Builder().tag(tag).url(url).post(body).build();
        enqueue(request, listener);
    }

    public OkDownloadRequest download(String tag, String url, String filePath, OkDownloadEnqueueListener listener) {
        OkDownloadRequest request = new OkDownloadRequest.Builder()
                .url(url)
                .filePath(filePath)
                .build();
        OkDownloadManager.getInstance(BaseApplication.getInstance()).enqueue(request, listener);
        return request;
    }


}
