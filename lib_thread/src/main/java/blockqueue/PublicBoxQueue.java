package blockqueue;



import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 主程序，一端通过生成者向容器添加数据，一端通过消费者从容器取数据
 *  通过阻塞式队列实现生产者和消费者模型非常简单，它提供阻塞的方法put()和take()
 *  开发者不需要实现wait_notify代码来实现通信。
 *  在java5中提供了不同的实现方式，比如 ArrayBlockingQueue和 LinkedBlockingQueue
 *  两者都是先进先出的顺序，而ArrayLinkedQueue 是自然有界的
 *  LinkedBlockingQueue可选的边界
 */
public class PublicBoxQueue {
    public static void main(String[] args) {
        BlockingQueue publicQueue = new LinkedBlockingQueue();
        ConsumerQueue consumerQueue = new ConsumerQueue(publicQueue);
        ProducterQueue producterQueue = new ProducterQueue(publicQueue);
        Thread consumeThread = new Thread(consumerQueue);
        Thread producterThread = new Thread(producterQueue);
        consumeThread.start();
        producterThread.start();
    }
}
