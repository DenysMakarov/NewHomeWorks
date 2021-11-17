package HomeWork.tools.executor;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThreadPool  implements Executor {

    private Queue<Runnable> workQueue = new ConcurrentLinkedQueue<>();
    private volatile boolean isRunning = true;
    private Thread[] threads;

    public ThreadPool(int nThreads) {
        this.threads = new Thread[nThreads];
        for(int i = 0; i < nThreads; i++){
//            new Thread(new TaskWorker()).start();
            threads[i] = new Thread(new TaskWorker());
            threads[i].start();
        }
    }

    @Override
    public void execute(Runnable tasks) {
        if (isRunning){
            workQueue.add(tasks);
        }
    }

    public void shutDown(){
        isRunning = false;
    }

    private final class TaskWorker implements Runnable{
        @Override
        public void run() {
            while (!workQueue.isEmpty() || isRunning){
                Runnable task = workQueue.poll();
                if (task != null){
                    task.run();
                }
            }
        }
    }

    public void awaitTermination(){
        for(int i = 0; i < threads.length; i++){
            if (threads[i] != null){
                try {
                    threads[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
