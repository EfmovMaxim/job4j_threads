package concurrent.producerconsumer;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.List;
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

    public void offer(T value) throws InterruptedException {
        while (queue.size() >= total) {
            synchronized (this) {
                this.notify();
                this.wait();
            }
        }

        synchronized (this) {
            if (queue.size() < total) {
                queue.offer(value);
                //System.out.println(Thread.currentThread().getName() + " offer " + value + " size: " + queue.size());
            }
        }
    }

    public T poll() throws InterruptedException {
        T rsl;
        while ((rsl = queue.poll()) == null) {
            synchronized (this) {
                this.notify();
                this.wait();
            }
        }

        synchronized (this) {
            this.notify();
        }
        //System.out.println(Thread.currentThread().getName() + "poll: " + rsl + " size: " + queue.size());
        return rsl;
    }

    public int size() {
        return queue.size();
    }


    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> simpleBlockingQueu = new SimpleBlockingQueue(5);

        Thread tr1 = new Thread(() ->{
            System.out.println("first thread start");
            for (int i = 0; i < 20; i++) {
                try {
                    simpleBlockingQueu.poll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread tr2 = new Thread(() ->{
            System.out.println("second thread start");
            for (int i = 0; i < 20; i++) {
                try {
                    simpleBlockingQueu.offer(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        tr1.start();
        tr2.start();


        tr1.join();
        tr2.join();
    }


}

