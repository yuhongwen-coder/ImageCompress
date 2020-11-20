package com.application.image.lib_thread;


import static java.lang.Thread.sleep;

public class ThreadDemoForSleep {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("thread name  = " + Thread.currentThread().getName());
        Thread thread = new Thread(new MyRunnable(), "thread1");
        thread.start();
        for (int i = 0; i < 10; i++) {
            System.out.println("thread name  = " + Thread.currentThread().getName() +
                    "--i = " + i + ", system time = " +
                    DateFormatUtils.formatDate(System.currentTimeMillis()));
            if (i == 2) {
                sleep(5000); // 休眠20s (主线程阻塞20s后自动唤醒,阻塞期间不释放cpu)
            }
        }
    }
    private static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("thread name  = " + Thread.currentThread().getName() +
                    ", system time = " + DateFormatUtils.formatDate(System.currentTimeMillis()));
            for (int j = 0; j < 3; j++) {
                System.out.println("thread name  = " + Thread.currentThread().getName() + "--j = " + j);
            }
        }
    }
}
