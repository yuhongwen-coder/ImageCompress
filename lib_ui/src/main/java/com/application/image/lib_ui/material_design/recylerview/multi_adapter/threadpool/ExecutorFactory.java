package com.application.image.lib_ui.material_design.recylerview.multi_adapter.threadpool;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * 线程执行任务核心 有bug
 */
public class ExecutorFactory extends ThreadPoolExecutor {
    private static volatile ExecutorFactory instance;

    private ExecutorFactory(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    /**
     * java.lang.ClassCastException: com.maxvision.tech.robot.ui.translate.ChatMessageActivity$3 cannot be cast to java.lang.Comparable
     * 	at java.util.concurrent.PriorityBlockingQueue.siftUpComparable(PriorityBlockingQueue.java:356)
     * 	at java.util.concurrent.PriorityBlockingQueue.offer(PriorityBlockingQueue.java:488)
     * 	at java.util.concurrent.ThreadPoolExecutor.execute(ThreadPoolExecutor.java:1386)
     * 	at com.maxvision.tech.robot.ui.translate.threadpool.SimpaleThreadPoolMaager.execute(SimpaleThreadPoolMaager.java:43)
     * 	at com.maxvision.tech.robot.ui.translate.ChatMessageActivity.syntheticLanguage(ChatMessageActivity.java:374)
     * 	at com.maxvision.tech.robot.ui.translate.ChatMessageActivity.access$700(ChatMessageActivity.java:54)
     * 	at com.maxvision.tech.robot.ui.translate.ChatMessageActivity$2$1$1.result(ChatMessageActivity.java:333)
     * 	at com.cl.voice.Voice$5.onResponse(Voice.java:286)
     * 	at retrofit2.DefaultCallAdapterFactory$ExecutorCallbackCall$1$1.run(DefaultCallAdapterFactory.java:83)
     * 	at android.os.Handler.handleCallback(Handler.java:900)
     * 	at android.os.Handler.dispatchMessage(Handler.java:103)
     * 	at android.os.Looper.loop(Looper.java:219)
     * 	at android.app.ActivityThread.main(ActivityThread.java:8349)
     * 	at java.lang.reflect.Method.invoke(Native Method)
     * 	at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:513)
     * 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1055)
     * @return
     */
    public static ExecutorFactory getExecutor() {
        if (instance == null) {
            synchronized (ExecutorFactory.class) {
                instance = new ExecutorFactory(ThreadPoolConst.IO_CORE_POOL_SIZE,ThreadPoolConst.IO_MAXIMUM_POOL_SIZE,
                        ThreadPoolConst.IO_KEEP_ALIVE_TIME,TimeUnit.SECONDS,
                        new PriorityBlockingQueue<Runnable>(ThreadPoolConst.IO_POOL_QUEUE_SIZE));
            }
        }
        return instance;
    }

}
