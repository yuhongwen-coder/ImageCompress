package com.application.image.lib_thread;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by yuhongwen
 * on 2020/9/28
 *
 *   1: 线程安全 阻塞队列 BlockQueue
 *      1-1 同一时刻，出队或者入队 只能有一个线程在操作，这样保证了 出对和 入队的线程安全性
 *      1-2 但是同一时刻，可以有 分别执行出队和人对的操作的两个线程在操作，
 *          但是为了保证 当前队列的元素个数，使用 AtomicInterger 来确保底层的队列是线程安全的
 *
 *   2： 主线程和 子线程之间的交互问题，保证数据的及时性
 *      2-2  CountDownLatch
 *      2-3 intentService
 *      2-4 handler + Thread (join wait )函数
 *
 *  3：
 *     比如有线程A和B，在A执行完成后B再开始执行
 *
 *    在线程A run方法最后启动线程B - -
 *    共享机制： 一个volatile boolean类型的标识变量，B一直检查该变量的值，而A则在执行完成后改变A的值
 *    中断机制： 和上面的方式差不多，B一直检查Thread.currentThread().isInterrupted() ，而A则在执行完成过后中断B threadB.interrupt()
 *        1： 使用Object.wait()和notify()方法，共享一个对象obj，线程B 首先obj.wait()，线程A执行完成后obj.notify()，唤醒线程B
 *        2：使用join()方法，线程B一开始调用threadA.join()，等待线程A执行完成之后此方法才会返回，然后开始执行。
 *        3：共享一个CountDownLatch, CountDownLatch c = new CountDownLatch(1) B 开始调用 c.await(),A在执行完成后调用c.countDown(),当c中计数器为0时，await()方法不再阻塞。
 *          使用栅栏，这个不太直观，就是用一个CyclicBarrier(1,new B())，当A执行完成后，调用barrier.await()。
 *        4：通过线程池执行一个Callable或Thread执行一个FutureTask，拿到一个Future，调用future的get()方法，此方法会阻塞，直到返回线程执行完毕，具体用法见 http://blog.csdn.net/joenqc/article/details/76333935
 *    使用Executors.newSingleThreadExecutor，顺序提交两个任务
 */
public class TestCountDownLatchDemo {
    private static final int MAX_INTEGER = 10000;
    private static CountDownLatch countDownLatch = new CountDownLatch(MAX_INTEGER);
    private static BlockingDeque<Integer> bo = new LinkedBlockingDeque<>();
    public static void main(String[] args) {
        BlockingDeque<Runnable> runnables = new LinkedBlockingDeque<>();
        ThreadPoolExecutor.CallerRunsPolicy callerRunsPolicy = new ThreadPoolExecutor.CallerRunsPolicy();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,10,3,
                TimeUnit.SECONDS,runnables,
                Executors.defaultThreadFactory(),callerRunsPolicy);
        for (int i = 0;i<MAX_INTEGER;i++) {
            threadPoolExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    bo.push(1);
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
            System.out.println("bo is size = " + bo.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
