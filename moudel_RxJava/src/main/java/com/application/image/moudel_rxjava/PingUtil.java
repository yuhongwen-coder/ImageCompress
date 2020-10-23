package com.application.image.moudel_rxjava;

import android.util.Log;

import java.io.IOException;

/**
 * Created by yuhongwen
 * on 2020/10/21
 */
public class PingUtil {
    /**
     * @return
     * @author cat
     * @category 判断是否有外网连接（普通方法不能判断外网的网络是否连接，比如连接上局域网）
     */
    public static boolean ping(String urld) {

        String result = null;
        try {
            String ip = urld;       //需要ping的服务器地址
            Process p = Runtime.getRuntime().exec("ping -c 1 -w 1 " + ip);// ping1次
            // PING的状态
            int status = p.waitFor();
            if (status == 0) {
                result = "successful~";
                return true;
            } else {
                result = "failed~ cannot reach the IP address";
                return false;
            }

        } catch (IOException e) {
            result = "failed~ IOException";
        } catch (InterruptedException e) {
            result = "failed~ InterruptedException";
        } finally {
            Log.i("TTT", "result = " + result);
        }
        return false;
    }
}
