package HomeWork.model;

import HomeWork.tasks.OneGroupSum;

import java.util.Arrays;

public class ThreadGroupSum extends GroupSum {
    final int CHUNKS = numbersGroup.length;

    public ThreadGroupSum(int[][] numbersGroup) {
        super(numbersGroup);
    }

    @Override
    public int computeSum(){
        OneGroupSum[] tasks = new OneGroupSum[CHUNKS];

        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = new OneGroupSum(numbersGroup[i]);
        }

        Thread[] threads = new Thread[tasks.length];

        for(int i = 0; i < tasks.length; i++){
            threads[i] = new Thread(tasks[i]);
            threads[i].start();
        }

        for(int i = 0; i < threads.length; i++){
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return Arrays.stream(tasks)
                .mapToInt(OneGroupSum::getSum)
                .sum();
    }
}
