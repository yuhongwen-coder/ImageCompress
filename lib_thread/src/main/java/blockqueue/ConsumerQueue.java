package blockqueue;

import java.util.concurrent.BlockingQueue;

public class ConsumerQueue implements Runnable{
    private BlockingQueue consumeQueue;

    public ConsumerQueue(BlockingQueue queue) {
        this.consumeQueue = queue;
    }

    @Override
    public void run() {
        for (int i = 0;i<10;i++) {
            try {
                System.out.println("消费者消费的编号 i=" + consumeQueue.take());
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
        }
    }
}
