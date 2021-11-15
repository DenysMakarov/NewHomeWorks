package HomeWork.model;
// посчитать сумму двумерного массива

public abstract class GroupSum {
    int[][] numbersGroup;

    public GroupSum(int[][] numbersGroup) {
        this.numbersGroup = numbersGroup;
    }

    public abstract int computeSum();
}
