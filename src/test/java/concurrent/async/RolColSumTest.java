package concurrent.async;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;


class RolColSumTest {

    @Test
    void calcSumSync() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        var expected = new RolColSum.Sums[]{
                new RolColSum.Sums(6,12),
                new RolColSum.Sums(15, 15),
                new RolColSum.Sums(24, 18)
        };

        assertThat(RolColSum.sum(matrix)).isEqualTo(expected);
    }

    @Test
    void calcSumAsync() throws ExecutionException, InterruptedException {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        var expected = new RolColSum.Sums[]{
                new RolColSum.Sums(6,12),
                new RolColSum.Sums(15, 15),
                new RolColSum.Sums(24, 18)
        };
        var actual = RolColSum.asyncSum(matrix);
        assertThat(actual).isEqualTo(expected);
    }
}