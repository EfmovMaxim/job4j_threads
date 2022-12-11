package concurrent;

import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class UserCache {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public void add(User user) {
        users.put(id.incrementAndGet(), User.of(user.getName()));
    }

    public User findById(int id) {
        return User.of(users.get(id).getName());
    }

    public List<User> findAll() {
        return users.values().stream().map(u -> User.of(u.getName())).toList();
    }

    public static void main(String[] args) {
        UserCache userCache = new UserCache();
        userCache.add(User.of("first"));
        userCache.add(User.of("second"));
        System.out.println(userCache.users.toString());

        System.out.println(userCache.findAll());
    }
}