package concurrent.singlelocklist;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    @GuardedBy("this")
    private final List<T> list;

    public SingleLockList(List<T> list) {
        this.list = copy(list);
    }

    public void add(T value) {
        synchronized (list) {
            list.add(value);
        }
    }

    public T get(int index) {
        synchronized (list) {
            return list.get(index);
        }
    }

    @Override
    public synchronized Iterator<T> iterator() {
        synchronized (list) {
            return copy(list).iterator(); //fail-safe
        }
    }

    private List<T> copy(List<T> origin) {
            return new ArrayList<T>(origin);
    }
}

