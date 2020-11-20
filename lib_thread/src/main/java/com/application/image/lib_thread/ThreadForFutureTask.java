package com.application.image.lib_thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 线程实现方式---》通过 callable 来用于接收返回结果
 */
public class ThreadForFutureTask {
    public static void main(String[] args) {
        testCallable();
    }
    private static void testCallable() {
        CallableThreadDemo callable = new CallableThreadDemo();
        // callable 实现方式需要 FutureTask 实现类来支持，用于接收运算结果
        FutureTask<Integer> task = new FutureTask(callable);
        new Thread(task).start();
        Integer sum = null;
        // 接收线程执行完毕的结果
        try {
            sum = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(sum);
    }
    public static class CallableThreadDemo implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            int sum = 0;
            for (int i = 0; i<100;i++) {
                sum += i;
            }
            return sum;
        }
    }
}
