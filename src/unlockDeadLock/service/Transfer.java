package unlockDeadLock.service;

import unlockDeadLock.model.Account;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Transfer implements Runnable {
    private static final int N_TRANSACTION = 10_000;
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
                transferMoneyFirstSolution(assDonor, accRecipient, sum);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    // FIRST SOLUTION
    private void transferMoneyFirstSolution(Account accFrom, Account accTo, int sum) throws InterruptedException {
        Account from;
        Account to;

        if (accFrom.getAccNumber() < accTo.getAccNumber()) {
            from = accFrom;
            to = accTo;
        } else {
            from = accTo;
            to = accFrom;
        }

        try {
            from.getLock().lock();
            try {
                to.getLock().lock();
                if (accFrom.getBalance() >= sum) {
                    accFrom.debit(sum);
                    accTo.credit(sum);
                }
            } finally {
                to.getLock().unlock();
            }
        } finally {
            from.getLock().unlock();
        }

    }


    // SECOND SOLUTION
    private void transferMoneySecondSolution(Account accFrom, Account accTo, int sum) throws InterruptedException {
        Lock lockOfAccFrom = accFrom.getLock();
        Lock lockOfAccTo = accTo.getLock();

        System.out.println("Acc " + accFrom.getAccNumber() + "  " + accFrom.getBalance());
//        System.out.println(" Acc " + accTo.getBalance());

        if (lockOfAccFrom.tryLock()) {
            if (lockOfAccTo.tryLock()) {
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
