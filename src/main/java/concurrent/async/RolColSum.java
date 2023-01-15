package concurrent.async;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int rowSum;
        private int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    public static Sums[] sum(int[][] matrix) {
        int rowSum, colSum;
        var rsl = new Sums[matrix.length];

        for (int i = 0; i < rsl.length; i++) {
            rowSum = 0;
            colSum = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }

            rsl[i] = new Sums(rowSum, colSum);
        }

        return rsl;
    }

    public static CompletableFuture<Integer> calcArray(int[] array) {
        return CompletableFuture.supplyAsync(
                () -> {
                    return Arrays.stream(array).reduce(0, Integer::sum);
                });
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        var rsl = new Sums[matrix.length];
        var colMatrix = new int[matrix.length];
        for (int i = 0; i < rsl.length; i++) {

            for (int j = 0; j < matrix[i].length; j++) {
                colMatrix[j] = matrix[j][i];
            }
            rsl[i] = new Sums(calcArray(matrix[i]).get(),
                    calcArray(colMatrix).get());
        }

        return rsl;
    }

}
