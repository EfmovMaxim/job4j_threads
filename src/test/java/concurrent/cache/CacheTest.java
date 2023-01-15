package concurrent.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


class CacheTest {

    @Test
    public void whenAddAndUpdate() {
        Cache cache = new Cache();
        var b1 = new Base(1, 1);
        var b2 = new Base(2, 1);
        cache.add(b1);
        cache.add(b2);

        Base b1new = new Base(1, 1);

        assertThat(cache.update(b1new)).isTrue();
    }

}