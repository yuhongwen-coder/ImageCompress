package com.application.image.lib_ui.material_design.recylerview.multi_adapter.threadpool;


import com.application.image.lib_ui.material_design.recylerview.multi_adapter.threadpool.ExecutorFactory;
import com.application.image.lib_ui.material_design.recylerview.multi_adapter.threadpool.TaskRunnable;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 语音翻译开线程，不然可能影响动画的即时性
 * 后面根据实际场景可以在优化 参数的配置和 拒绝策略
 *  目前实现
 *  1 ： 线程池管理类
 *  2： 获取线程核心执行器
 *  3： 定义线程任务执行 Runnable
 */
public class SimpaleThreadPoolMaager {

    private SimpaleThreadPoolMaager(){

    }

    public static SimpaleThreadPoolMaager getInstance(){
        return InstanceHolder.mInstance;
    }

    private static class InstanceHolder{
        private static final SimpaleThreadPoolMaager mInstance = new SimpaleThreadPoolMaager();
    }

    public void execute(TaskRunnable runnable){
        if(null == runnable){
            throw new NullPointerException("Runnable should not be null");
        }
        ExecutorService executor = ExecutorFactory.getExecutor();
        if(null == executor){
            return ;
        }
        executor.execute(runnable);
    }
}
