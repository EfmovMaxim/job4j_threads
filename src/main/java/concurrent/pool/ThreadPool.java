package concurrent.pool;

import concurrent.producerconsumer.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {

    private static final int AVAILABLEPROCESSORS =  Runtime.getRuntime().availableProcessors();
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(AVAILABLEPROCESSORS);


    public ThreadPool() {
        for (int i = 0; i < AVAILABLEPROCESSORS; i++) {
            Thread thread = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        Runnable task = tasks.poll();
                        if (task != null) {
                            task.run();
                        }
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            threads.add(thread);
            thread.start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        var pool = new ThreadPool();

        for (int i = 0; i < 20; i++) {
            int j = i;
            try {
                pool.work(() -> {
                    try {
                        Thread.sleep(300);
                        System.out.println("Work" + j + " " + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            } catch (InterruptedException e) {
                
            }
        }

        Thread.sleep(8000);
        pool.shutdown();

    }
}