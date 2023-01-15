package concurrent.producerconsumer;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;


class SimpleBlockingQueueTest {

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList buffer = new CopyOnWriteArrayList();
        final SimpleBlockingQueue queue = new SimpleBlockingQueue(2);

        Thread producer = new Thread(
                () -> {
                    try {
                        for (int i = 0; i < 5; i++) {
                            queue.offer(i);
                        }
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
        );
        producer.start();

        Thread consumer = new Thread(
                () -> {
                    while (queue.size() != 0 || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();

        producer.join();
        consumer.interrupt();
        consumer.join();

        assertThat(buffer).isEqualTo(List.of(0, 1, 2, 3, 4));
    }
}