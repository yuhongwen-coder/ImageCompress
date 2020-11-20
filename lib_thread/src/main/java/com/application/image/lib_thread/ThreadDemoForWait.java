package com.application.image.lib_thread;


/**
 * 首先要明确，只能在synchronized同步方法或者同步代码块中使用这些。在执行wait方法后，
 * 当前线程释放锁(这点与sleep, yield不同)。调用了wait函数的线程会一直等待，
 * 直到有其他线程调用了同一个对象的notify或notifyAll方法。需要注意的是，
 * 被唤醒并不代表立刻获得对象的锁，要等待执行notify方法的线程执行完，
 * 也即退出synchronized代码块后，当前线程才会释放锁，进而wait状态的线程才可以获得该对象锁
 */
public class ThreadDemoForWait {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("main thread name  enter = " + Thread.currentThread().getName());
        MyThread thread = new MyThread( "waitThread");
        synchronized (thread) {
            // 启动 waitThread 线程
            thread.start();
            // 阻塞主线程，等待其他线程唤醒
            System.out.println("main thread name is waited");
            thread.wait();
            for (int j = 0; j < 3; j++) {
                System.out.println(" main thread notifyed = " +
                        Thread.currentThread().getName() + "--j = " + j);
            }
        }
    }

    private static class MyThread extends Thread {
        public MyThread(String threadName) {
            super(threadName);
        }
        @Override
        public void run() {
            synchronized (this) {
                System.out.println("waitThread name enter = " + Thread.currentThread().getName());
                for (int j = 0; j < 3; j++) {
                    System.out.println("thread name  = " + Thread.currentThread().getName() + "--j = " + j);
                }
                System.out.println("waitThread start sleep  = " +
                        DateFormatUtils.formatDate(System.currentTimeMillis()));
                try {
                    // 当前线程休眠2s
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 休眠时间结束，唤醒执行wait()函数的线程（主线程）
                System.out.println("waitThread sleep ended = " +
                        DateFormatUtils.formatDate(System.currentTimeMillis()));
                notify();
            }

        }
    }
}
