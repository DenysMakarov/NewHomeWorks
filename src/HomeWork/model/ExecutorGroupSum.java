package HomeWork.model;

import HomeWork.tasks.OneGroupSum;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorGroupSum extends GroupSum{

    final int CHUNKS = numbersGroup.length;

    public ExecutorGroupSum(int[][] numbersGroup) {
        super(numbersGroup);
    }

    @Override
    public int computeSum() {
        OneGroupSum[] tasks = new OneGroupSum[CHUNKS];
        for(int i = 0; i < tasks.length; i++){
            tasks[i] = new OneGroupSum(numbersGroup[i]);
        }

        ExecutorService executorService = Executors.newWorkStealingPool();
        for(int i = 0; i < tasks.length; i++){
            executorService.execute(tasks[i]);
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return Arrays.stream(tasks)
                .mapToInt(OneGroupSum::getSum)
                .sum();
    }
}
