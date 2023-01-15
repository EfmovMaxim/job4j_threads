package concurrent.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        Integer current, temp;

        do {
            current = count.get();
            temp = (current == null) ? 0 : current.intValue();
            temp++;
        } while (!count.compareAndSet(current, temp));
    }

    public int get() {
        return (count.get() == null) ? 0 : count.get();

    }


    public static void main(String[] args) throws InterruptedException {

        CASCount casCount = new CASCount();

        Thread tr1 = new Thread(() -> {
                        IntStream.range(0, 5).forEach(i -> casCount.increment());
            }
        );

        Thread tr2 = new Thread(() -> {
            IntStream.range(0, 5).forEach(i -> casCount.increment());
            }
        );

        tr1.start();
        tr2.start();

        tr1.join();
        tr2.join();

        System.out.println(casCount.get());
    }

}