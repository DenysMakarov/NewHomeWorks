package telran.mediation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlkQueue<T> implements IBlkQueue<T> {
	List<T> msg;
	int maxSize;

	Lock mutex = new ReentrantLock();
	Condition senderCondition = mutex.newCondition();
	Condition receiveCondition = mutex.newCondition();

	public BlkQueue(int maxSize) {
		this.msg =  new ArrayList<>();
		this.maxSize = maxSize;
	}

	@Override
	public int getSize() {
		return msg.size();
	}

//	@Override
//	public synchronized void push(T message) {
//		while (msg.size() >= maxSize){
//			try {
//				wait();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//		msg.add(message);
//		notifyAll();
//	}

	@Override
	public void push(T message) {
		mutex.lock();
		try {
			while (msg.size() >= maxSize){
				try {
					senderCondition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			msg.add(message);
			receiveCondition.signal();
		}finally {
			mutex.unlock();
		}

	}

//	@Override
//	public synchronized T pop() {
//		while (msg.size() < 1){
//			try {
//				wait();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
//		T message = msg.get(0);
//		msg.remove(0);
//		notifyAll();
//		return message;
//	}

	@Override
	public T pop() {
		mutex.lock();
		try {
			while (msg.size() < 1){
				try {
					receiveCondition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			T message = msg.get(0);
			msg.remove(0);
			senderCondition.signal();
			return message;
		}finally {
			mutex.unlock();
		}
	}

}