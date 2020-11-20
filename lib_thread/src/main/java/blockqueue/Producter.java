package blockqueue;

/**
 * 生产者
 */
public class Producter implements Runnable {
    private BoxForConsumerAndProducter box;

    public Producter(BoxForConsumerAndProducter box) {
        this.box = box;
    }
    @Override
    public void run() {
        for(int i = 0; i < 10;i++) {
            System.out.println("Producter count = " + i);
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            box.increace();
        }
    }
}
