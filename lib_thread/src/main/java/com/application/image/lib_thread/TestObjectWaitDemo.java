package com.application.image.lib_thread;

/**
 * Created by yuhongwen
 * on 2020/9/30
 *   1 : Obj.wait()，与Obj.notify() 和 synchronized 可以实现很好的线程同步模型框架
 *
 *   2：  Obj.wait()，与Obj.notify()必须要与synchronized(Obj)一起使用，也就是wait,与notify是针对已经获取了Obj锁进行操作，
 *   从语法角度来说就是Obj.wait(),Obj.notify必须在 synchronized(Obj){...}语句块内。
 *   从功能上来说wait就是说线程在获取对象锁后，主动释放对象锁，同时本线程休眠。直到有其它线程调用对象的notify()唤醒该线程，才能继续获取对象锁，
 *   并继续执行。
 *   相应的notify()就是对对象锁的唤醒操作。但有一点需要注意的是notify()调用后，
 *   并不是马上就释放对象锁的，而是在相应的synchronized(){}语句块执行结束，
 *   自动释放锁后，JVM会在wait()对象锁的线程中随机选取一线程，赋予其对象锁，唤醒线程，继续执行。这样就提供了在线程间同步、唤醒的操作
 *
 *   3： 应该是三线程打印ABC的问题来解释 这三种用法
 *      建立三个线程，A线程打印10次A，B线程打印10次B,C线程打印10次C，要求线程同时运行，交替打印10次ABC。
 *      这个问题用Object的wait()，notify()就可以很方便的解决
 */
public class TestObjectWaitDemo {

    public static void main(String[] args) throws Exception {
        Object a = new Object();
        Object b = new Object();
        Object c = new Object();
        MyThreadPrinter2 pa = new MyThreadPrinter2("A", c, a);
        MyThreadPrinter2 pb = new MyThreadPrinter2("B", a, b);
        MyThreadPrinter2 pc = new MyThreadPrinter2("C", b, c);
        new Thread(pa).start();
        new Thread(pb).start();
        new Thread(pc).start();
    }

    public static class MyThreadPrinter2 implements Runnable {

        private String name;
        private Object prev;
        private Object self;

        private MyThreadPrinter2(String name, Object prev, Object self) {
            this.name = name;
            this.prev = prev;
            this.self = self;
        }

        @Override
        public void run() {
            int count = 10;
            while (count > 0) {
                synchronized (prev) {
                    synchronized (self) {
                        System.out.print(name);
                        count--;

                        self.notify();
                    }
                    try {
                        prev.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
}
