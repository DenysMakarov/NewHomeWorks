package HomeWork.model;

import java.util.Arrays;

public class ParallelStreamGroupSum extends GroupSum{

    public ParallelStreamGroupSum(int[][] numbersGroup) {
        super(numbersGroup);
    }

    @Override
    public int computeSum() {
        return Arrays.stream(numbersGroup)
                .parallel() // создает потоки
                .mapToInt(a -> Arrays.stream(a).sum())
                .sum();
    }
}
