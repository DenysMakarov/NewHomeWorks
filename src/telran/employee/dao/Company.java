package telran.employee.dao;

import telran.employee.interfaces.ICompany;
import telran.employee.model.Employee;
import telran.employee.model.SalesManager;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Company implements ICompany {
	private Employee[] employees;
	private int size;
	ReadWriteLock rwLock = new ReentrantReadWriteLock();
	Lock read = rwLock.readLock();
	Lock write = rwLock.writeLock();

	public Company(int capacity) {
		employees = new Employee[capacity];
	}

	@Override
	public boolean addEmployee(Employee employee) {
		write.lock();
		try {
			if(size == employees.length
					|| findEmployee(employee.getId()) != null) {
				return false;
			}
			employees[size] = employee;
			size++;
			return true;
		}finally {
			write.unlock();
		}
	}

	@Override
	public Employee removeEmployee(int id) {
		write.lock();
		try {
			for (int i = 0; i < size; i++) {
				if(employees[i].getId() == id) {
					Employee victim = employees[i];
					employees[i] = employees[size - 1];
					size--;
					employees[size] = null;
					return victim;
				}
			}
			return null;
		}finally {
			write.unlock();
		}
	}

	@Override
	public Employee findEmployee(int id) {
		read.lock();
		try {
			for (int i = 0; i < size; i++) {
				if(employees[i].getId() == id) {
					return employees[i];
				}
			}
			return null;
		}finally {
			read.unlock();
		}

	}

	@Override
	public double totalSalary() {
		double sum = 0;
		read.lock();
		try{
			for (int i = 0; i < size; i++) {
				sum += employees[i].calcSalary();
			}
			return sum;
		}finally {
			read.unlock();
		}
	}

	@Override
	public int quantity() {
		read.lock();
		try {
			return size;
		}finally {
			read.unlock();
		}
	}

	@Override
	public double totalSales() {
		double sum = 0;
		read.lock();
		try {
			for (int i = 0; i < size; i++) {
				if(employees[i] instanceof SalesManager) {
					SalesManager sm = (SalesManager) employees[i];
					sum += sm.getSalesValue();
				}
			}
			return sum;
		}finally {
			read.unlock();
		}
	}

	@Override
	public void printEmployees() {
		read.lock();
		try {
			for (int i = 0; i < size; i++) {
				System.out.println(employees[i]);
			}
		}finally {
			read.unlock();
		}
	}

}
