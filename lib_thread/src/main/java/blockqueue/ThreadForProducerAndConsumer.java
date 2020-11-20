package blockqueue;

/**
 * 生产者与消费者模型
 */
public class ThreadForProducerAndConsumer {
    private static BoxForConsumerAndProducter box;
    private static BoxForConsumerAndProducter box2;
    public static void main(String[] args) {
        box = BoxForConsumerAndProducter.getInstance();
        Consumer consumer = new Consumer(box);
        Producter producter = new Producter(box);
        Thread consumerThread = new Thread(consumer);
        Thread producterThread = new Thread(producter);
        producterThread.start();
        consumerThread.start();
    }
}
