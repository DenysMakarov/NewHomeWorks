package HomeWork.controller;

import HomeWork.model.ExecutorGroupSum;
import HomeWork.model.GroupSum;
import HomeWork.model.ParallelStreamGroupSum;
import HomeWork.model.ThreadGroupSum;
import HomeWork.tests.GropeSumPerformanceTest;

import java.util.Random;

public class GroupSumApp {
    private static final int N_GROUP = 10_000;
    private static final int NUMBERS_PER_GROUP = 10_000;
    static Random random = new Random();
    static int[][] arr = new int[N_GROUP][NUMBERS_PER_GROUP];

    public static void main(String[] args) {
        fillArray();
        GroupSum executorsGroupSum = new ExecutorGroupSum(arr);
        GroupSum threadGroupSum = new ThreadGroupSum(arr);
        GroupSum parallelStreamGroupSum = new ParallelStreamGroupSum(arr);

        new GropeSumPerformanceTest("ExecutorGroupSum", executorsGroupSum).runTest();
        new GropeSumPerformanceTest("ThreadGroupSum", threadGroupSum).runTest();
        new GropeSumPerformanceTest("ParallelStreamGroupSum", parallelStreamGroupSum).runTest();

    }

    private static void fillArray() {
        for(int i = 0; i < arr.length; i++){
            for(int j = 0; j < arr.length; j++){
                arr[i][j] = random.nextInt();
            }
        }
    }
}
