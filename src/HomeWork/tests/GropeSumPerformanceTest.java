package HomeWork.tests;

import HomeWork.model.GroupSum;

public class GropeSumPerformanceTest {
    String name;
    GroupSum groupSum;

    public GropeSumPerformanceTest(String name, GroupSum groupSum) {
        this.name = name;
        this.groupSum = groupSum;
    }

    public void runTest(){
        long t1 = System.currentTimeMillis();
        int sum = groupSum.computeSum();
        long t2 = System.currentTimeMillis();
        System.out.println("test name : " + name +
                " time: " + (t2 - t1) +
                " sum = " + sum);
    }
}
