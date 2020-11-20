package blockqueue;

/**
 * 消费者模型
 *    https://blog.csdn.net/qq_41247433/article/details/79434202
 */
public class Consumer implements Runnable {
    private BoxForConsumerAndProducter box;

    public Consumer(BoxForConsumerAndProducter box) {
        this.box = box;
    }
    @Override
    public void run() {
        for(int i = 0; i < 10;i++) {
            System.out.println("concumer count = " + i);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            box.decreace();
        }
    }
}
