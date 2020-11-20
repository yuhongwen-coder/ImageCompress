package blockqueue;

import java.util.concurrent.BlockingQueue;

public class ProducterQueue implements Runnable {
    private BlockingQueue prodecterQueue;
    public ProducterQueue(BlockingQueue queue) {
        this.prodecterQueue = queue;
    }

    @Override
    public void run() {
        for (int i=0;i<10;i++) {
            System.out.println("生成者生成事件 i= " + i);
            try {
                prodecterQueue.put(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
