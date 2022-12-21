package concurrent;

public class CountBarrier {
    private final Object monitor = this;

    private final int total;

    private int count = 0;

    public CountBarrier(final int total) {
        this.total = total;
    }

    public void count() {
        synchronized (monitor) {
                count++;
                monitor.notifyAll();
            }
    }

    public void await() {
        while (count < total) {
            synchronized (monitor) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountBarrier countBarrier = new CountBarrier(5);
        Thread tr1 = new Thread(() ->{
            System.out.println("first thread start");
            for (int i = 0; i < 2; i++) {
                countBarrier.count();
            }
        });

        Thread tr2 = new Thread(() ->{
            System.out.println("second thread start");
            for (int i = 0; i < 3; i++) {
                countBarrier.count();
            }
        });

        Thread tr3 = new Thread(() ->{
            countBarrier.await();
            System.out.println("third thread end");
        });

        tr3.start();
        tr1.start();
        tr2.start();


        tr1.join();
        tr2.join();
        tr3.join();

    }
}
