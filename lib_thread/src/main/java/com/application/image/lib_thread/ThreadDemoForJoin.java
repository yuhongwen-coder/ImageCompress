package com.application.image.lib_thread;

public class ThreadDemoForJoin {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("thread name  = " + Thread.currentThread().getName());
        Thread thread = new Thread(new MyRunnable(), "thread1");
        thread.start();
        for(int i = 0; i< 5 ; i++) {
            System.out.println("thread name  = " + Thread.currentThread().getName() + "--i = " + i);
            if (i == 2) {
                thread.join();
            }
        }
    }

    private static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("thread name  = " + Thread.currentThread().getName());
            for (int j = 0 ; j<3;j++) {
                System.out.println("thread name  = " + Thread.currentThread().getName() + "--j = " + j);
            }
        }
    }
}
