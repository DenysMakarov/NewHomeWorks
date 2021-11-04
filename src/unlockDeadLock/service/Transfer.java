package unlockDeadLock.service;

import unlockDeadLock.model.Account;

import java.util.Random;
import java.util.concurrent.locks.Lock;

public class Transfer implements Runnable {
    private static final int N_TRANSACTION = 1000;
    Account assDonor;
    Account accRecipient;
    int sum;
    Thread thread;

    public Transfer(Account assDonor, Account accRecipient, int sum) {
        this.assDonor = assDonor;
        this.accRecipient = accRecipient;
        this.sum = sum;
        thread = new Thread(this);
        thread.start();
    }

    public Thread getThread() {
        return thread;
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < N_TRANSACTION; i++) {
            int sum = random.nextInt(this.sum);
            try {
                transferMoney(assDonor, accRecipient, sum);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void transferMoney(Account accFrom, Account accTo, int sum) throws InterruptedException {
        Lock lockOfAccFrom = accFrom.getLock();
        Lock lockOfAccTo = accTo.getLock();

        if (lockOfAccFrom.tryLock()) {
            lockOfAccFrom.lock();
            if (lockOfAccTo.tryLock()) {
                lockOfAccTo.lock();
                try {
                    if (accFrom.getBalance() >= sum) {
                        accFrom.debit(sum);
                        accTo.credit(sum);
                    }
                } finally {
                    lockOfAccFrom.unlock();
                    lockOfAccTo.unlock();
                }
            } else {
                lockOfAccFrom.unlock();
            }
        }
    }
}
