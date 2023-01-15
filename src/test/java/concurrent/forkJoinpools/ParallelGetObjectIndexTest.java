package concurrent.forkJoinpools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ParallelGetObjectIndexTest {

    @Test
    void indexOfInArrayWithDifferentTypes() {
        Object[] array = new Object[25];
        for (int i = 0; i < array.length; i++) {
            array[i] = Integer.valueOf(i + 2);
        }
        array[15] = "result";
        var rsl = ParallelGetObjectIndex.indexOf(new String("result"), array);
        assertThat(rsl).isEqualTo(15);
    }

    @Test
    void indexOfObjectNotFound() {
        Object[] array = new Object[25];
        for (int i = 0; i < array.length; i++) {
            array[i] = Integer.valueOf(i + 2);
        }
        var rsl = ParallelGetObjectIndex.indexOf(Integer.valueOf(44), array);
        assertThat(rsl).isEqualTo(-1);
    }

    @Test
    void indexOfInSmallArray() {
        Object[] array = new Object[9];
        for (int i = 0; i < array.length; i++) {
            array[i] = Integer.valueOf(i + 2);
        }
        array[4] = "result";
        var rsl = ParallelGetObjectIndex.indexOf(new String("result"), array);
        assertThat(rsl).isEqualTo(4);
    }
}