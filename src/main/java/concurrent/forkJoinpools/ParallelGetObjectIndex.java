package concurrent.forkJoinpools;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ParallelGetObjectIndex extends RecursiveTask<Integer> {

    private final Object[] array;
    private final Object object;
    private final int from;
    private final int to;

    public ParallelGetObjectIndex(Object[] array, Object object, int from, int to) {
        this.array = array;
        this.object = object;
        this.from = from;
        this.to = to;
    }

    public ParallelGetObjectIndex(Object[] array, Object object) {
        this.array = array;
        this.object = object;
        this.from = 0;
        this.to = array.length;
    }

    @Override
    protected Integer compute() {
        if (from - to <= 10) {
            return searchIndex();
        }

        int mid = (from - to) / 2;
        var taskLeft = new ParallelGetObjectIndex(array, object, from, from + mid);
        var taskRight = new ParallelGetObjectIndex(array, object, from + mid, to);

        taskLeft.fork();
        taskRight.fork();

        return Integer.max(taskLeft.join(), taskRight.join());
    }

    private int searchIndex() {
        for (int i = from; i < to; i++) {
            if (array[i].equals(object)) {
                return i;
            }
        }
        return -1;
    }

    public static Integer indexOf(Object object, Object[] array) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelGetObjectIndex(array, object));
    }
}
