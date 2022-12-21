package concurrent.producerconsumer;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {
    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private int total;
    private int count;

    public SimpleBlockingQueue(int total) {
        this.total = total;
    }

    public void offer(T value) {
        while (queue.size() >= total) {
            synchronized (this) {
                try {
                    this.notify();
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        synchronized (this) {
            if (queue.size() < total) {
                queue.offer(value);
                System.out.println(Thread.currentThread().getName() + " offer " + value + " size: " + queue.size());
            }
        }
    }

    public T poll() {
        T rsl;
        while ((rsl = queue.poll()) == null) {
            synchronized (this) {
                try {
                    this.notify();
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        synchronized (this) {
            this.notify();
        }
        System.out.println(Thread.currentThread().getName() + "poll: " + rsl + " size: " + queue.size());
        return rsl;
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueu = new SimpleBlockingQueue(5);

        Thread tr1 = new Thread(() ->{
            System.out.println("first thread start");
            for (int i = 0; i < 20; i++) {
                simpleBlockingQueu.poll();
            }
        });

        Thread tr2 = new Thread(() ->{
            System.out.println("second thread start");
            for (int i = 0; i < 20; i++) {
                simpleBlockingQueu.offer(i);
            }
        });

        tr1.start();
        tr2.start();


        tr1.join();
        tr2.join();
    }

}

