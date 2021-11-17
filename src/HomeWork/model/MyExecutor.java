package HomeWork.model;

import HomeWork.tasks.OneGroupSum;
import HomeWork.tools.executor.ThreadPool;

import java.util.Arrays;

public class MyExecutor extends GroupSum {
    final int CHUNKS = numbersGroup.length;

    public MyExecutor(int[][] numbersGroup) {
        super(numbersGroup);
    }

    @Override
    public int computeSum() {
        OneGroupSum[] tasks = new OneGroupSum[CHUNKS];
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = new OneGroupSum(numbersGroup[i]);
        }

        ThreadPool threadPool = new ThreadPool(5);
        for (int i = 0; i < tasks.length; i++) {
            threadPool.execute(tasks[i]);
        }
        threadPool.shutDown();
        threadPool.awaitTermination();

        return Arrays.stream(tasks)
                .mapToInt(OneGroupSum::getSum)
                .sum();

    }
}
