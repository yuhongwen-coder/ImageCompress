package com.application.image.lib_thread;

public class ThreadForMyThread {
    public static void main(String[] args) {
        testMyThread();
    }

    private static void testMyThread() {
        MyThread myThread = new MyThread();
        myThread.start();
    }

    public static class MyThread extends Thread {
        @Override
        public void run() {
            int sum = 0;
            for (int i = 0;i<100;i++) {
                sum += i;
            }
            System.out.println("test MyThread sum = " + sum);
        }
    }
}
