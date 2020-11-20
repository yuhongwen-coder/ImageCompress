package com.application.image.lib_thread;

public class ThreadForRunnable {
    public static void main(String[] args) {
        testThreadRunnable();
    }

    private static void testThreadRunnable() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for(int i = 0;i<100;i++) {
                    sum +=i;
                }
                System.out.println("testThreadRunnable sum = " + sum);
            }
        });
        thread.start();
    }
}
