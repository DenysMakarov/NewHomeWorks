

public class DeadLockApp {
    public static final Object lock1 = new Object();
    public static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(new ThreadLock1());
        Thread thread2 = new Thread(new ThreadLock2());
        thread1.start();
        thread2.start();
    }

}

class ThreadLock1 implements Runnable{
    @Override
    public void run(){
        System.out.println("Thread 1 capture monitor 1");
        synchronized (DeadLockApp.lock1){
            System.out.println("monitor 1 captured");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 1 try to capture monitor 2");
            synchronized (DeadLockApp.lock2){
                System.out.println("monitor 1 and 2 captured");
            }
        }
    }
}

class ThreadLock2 implements Runnable{
    @Override
    public void run(){
        System.out.println("Thread 2 capture monitor 2");
        synchronized (DeadLockApp.lock2){
            System.out.println("monitor 2 captured");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 2 try to capture monitor 1");
            synchronized (DeadLockApp.lock1){
                System.out.println("monitor 2 and 1 captured");
            }
        }
    }
}
