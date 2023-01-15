package concurrent.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(),
                (k, v) -> {
                    Base stored = memory.get(model.getId());
                    if (stored.getVersion() != model.getVersion()) {
                        throw new OptimisticException("Versions are not equal");
                    }
                    var update = new Base(model.getId(), model.getVersion() + 1);
                    update.setName(model.getName());

                    return update;
                }) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }
}