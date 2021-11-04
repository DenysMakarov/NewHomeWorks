package unlockDeadLock.model;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    int accNumber;
    int balance;
    Lock lock = new ReentrantLock();

    public Lock getLock() {
        return lock;
    }

    public Account(int accNumber) {
        this.accNumber = accNumber;
    }


    public int getAccNumber() {
        return accNumber;
    }

    public int getBalance() {
        return balance;
    }

    public void debit(int sum) {
        this.balance -= sum;
    }
    public void credit(int sum) {
        this.balance += sum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accNumber == account.accNumber && balance == account.balance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accNumber, balance);
    }
}
