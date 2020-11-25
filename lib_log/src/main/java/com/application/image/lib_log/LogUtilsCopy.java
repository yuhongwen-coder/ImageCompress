package com.application.image.lib_log;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by yuhongwen
 * on 2020/11/24
 * 语音库日志
 * 1: 日志格式
 *     13:32:00.073 D/TAG
 *     args[0] = 000
 *     args[1] = 1111
 * 2: 默认 newSingleThreadExecutor 线程池打印
 *
 * 3： 文件名称
 *
 * 4： 文件大小超过 多少KB 就在新建文件 (TODO)
 */
public class LogUtilsCopy {
    public static final String D = "debug";
    public static final int E = Log.ERROR;
    private static final String LINE_SEP = System.getProperty("line.separator");
    @SuppressLint("SimpleDateFormat")
    private static final Format FORMAT = new SimpleDateFormat("MM-dd HH:mm:ss.SSS ");
    private static final String NOTHING = "log nothing";
    private static final String NULL = "null";
    private static final String ARGS = "args";
    public static final ConfigDir CONFIG_DIR = new ConfigDir();
    private static ExecutorService sExecutor;

    public static void d(String tag, Object... contents) {
        log(D, tag, contents);
    }

    public static void log(String type, String tag, Object... contents) {
        String body = processBody(contents);
        print2File(type, tag, body);
    }

    private static String processBody(Object... contents) {
        String body = "";
        if (contents != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < contents.length; ++i) {
                Object content = contents[i];
                sb.append(ARGS)
                        .append("[")
                        .append(i == 0 ? "source":"result")
                        .append("]")
                        .append(" = ")
                        .append(content == null ? NULL : content.toString())
                        .append(LINE_SEP);
            }
            body = sb.toString();
        }
        return body.length() == 0 ? NOTHING : body;
    }

    private static void print2File(String type, final String tag, final String msg) {
        Date now = new Date(System.currentTimeMillis());
        String format = FORMAT.format(now);
        String fullPath;
        if (TextUtils.equals(tag,CONFIG_DIR.MINGANCI)) {
            fullPath = CONFIG_DIR.minGanFullPath;
        }else if (TextUtils.equals(tag,CONFIG_DIR.XIANLIAO)) {
            fullPath = CONFIG_DIR.xiaoliaoFullPath;
        } else if (TextUtils.equals(tag,CONFIG_DIR.YITUKU)) {
            fullPath = CONFIG_DIR.intentFullPath;
        } else if (TextUtils.equals(tag,CONFIG_DIR.YULIAOKU)) {
            fullPath = CONFIG_DIR.yuLiaoFullPath;
        } else {
            fullPath = CONFIG_DIR.defaultFullPath;
        }
        if (!createOrExistsFile(fullPath)) {
            Log.e("LogUtils", "create " + fullPath + " failed!");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(format)
                .append(type)
                .append("/")
                .append("IdentifyControl")
                .append(LINE_SEP)
                .append(msg)
                .append(LINE_SEP);
        final String content = sb.toString();
        input2File(content, fullPath);
    }

    private static boolean createOrExistsFile(final String filePath) {
        File file = new File(filePath);
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            boolean isCreate = file.createNewFile();
            if (isCreate) printDeviceInfo(filePath);
            return isCreate;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void printDeviceInfo(String filePath) {
        final String head = "************* Log Head ****************" +
                "\nType of Log        : " + filePath +
                "\nDevice Manufacturer: " + Build.MANUFACTURER +
                "\nDevice Model       : " + Build.MODEL +
                "\nAndroid Version    : " + Build.VERSION.RELEASE +
                "\nAndroid SDK        : " + Build.VERSION.SDK_INT +
                "\nApp VersionName    : " + BuildConfig.VERSION_NAME+
                "\nApp VersionCode    : " + BuildConfig.VERSION_CODE +
                "\nApp BuildeType    : " + BuildConfig.BUILD_TYPE +
//                "\nApp BuildeFLAVOR    : " + BuildConfig.FLAVOR +
//                "\nApp Device_OCR    : " + BuildConfig.DEVICE_OCR +
//                "\nApp Device_PRI    : " + BuildConfig.DEVICE_PRINTER +
//                "\nApp Device_SCAN    : " + BuildConfig.DEVICE_SCAN +
//                "\nApp APP_Install_Time    : " + BuildConfig.INSTALL_TIME +
                "\n************* Log Head ****************\n\n";
        input2File(head, filePath);
    }

    private static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    private static void input2File(final String input, final String filePath) {
        if (sExecutor == null) {
            sExecutor = Executors.newSingleThreadExecutor();
        }
        Future<Boolean> submit = sExecutor.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                BufferedWriter bw = null;
                try {
                    bw = new BufferedWriter(new FileWriter(filePath, true));
                    bw.write(input);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    try {
                        if (bw != null) {
                            bw.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        try {
            if (submit.get()) return;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        Log.e("LogUtils", "log to " + filePath + " failed!");
    }

    /**
     * 配置文件路径
     *
     */
    public static class ConfigDir {
        public String xiaoliaoFullPath;
        public String yuLiaoFullPath;
        public String intentFullPath;
        public String minGanFullPath;
        public String defaultFullPath;
        public String mDefaultDir;
        public final String MINGANCI = "minGanCi";
        public final String XIANLIAO = "xianliao";
        public final String YULIAOKU = "yuliao";
        public final String YITUKU = "yiyu";
        public final String DEFAULT = "default";

        private ConfigDir() {
//            mDefaultDir = FileConstant.getPathSpreadFile();
            minGanFullPath = mDefaultDir + MINGANCI + ".txt";
            xiaoliaoFullPath = mDefaultDir + XIANLIAO + ".txt";
            yuLiaoFullPath = mDefaultDir + YULIAOKU + ".txt";
            intentFullPath = mDefaultDir + YITUKU + ".txt";
            defaultFullPath = mDefaultDir + DEFAULT + ".txt";

        }
    }
}
