package blockqueue;

/**
 * 生产者和消费者公用这个容器
 */
public class BoxForConsumerAndProducter {
    private int boxApple = 0;
    private static volatile BoxForConsumerAndProducter box;
    private static Object lock = new Object();

    private BoxForConsumerAndProducter() {
    }

    public static BoxForConsumerAndProducter getInstance() {
        if (box == null) {
            synchronized (lock) {
                if (box == null) {
                    box = new BoxForConsumerAndProducter();
                }
            }
        }
        return box;
    }

    /**
     * 向容器中生产增加数据
     */
    public void increace() {
        synchronized (box) {
            while (boxApple == 10) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            boxApple++;
            box.notify();
            System.out.println("decreace 生产事件成功 boxApple = " + boxApple);
        }
    }

    /**
     * 向容器中消费较少数据
     */
    public void decreace() {
        synchronized (box) {
            while (boxApple ==0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            boxApple--;
            box.notify();
            System.out.println("decreace 消费事件成功 boxApple = " + boxApple);
        }
    }
}
