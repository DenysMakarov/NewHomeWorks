package telran.employee.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import telran.employee.interfaces.ICompany;
import telran.employee.model.Employee;
import telran.employee.model.Manager;
import telran.employee.model.SalesManager;

public class CompanyList implements ICompany {
	private List<Employee> employees;
	private int capacity;
	ReadWriteLock rwLock = new ReentrantReadWriteLock();
	Lock read = rwLock.readLock();
	Lock write = rwLock.writeLock();
	
	public CompanyList(int capacity) {
		this.capacity = capacity;
		employees = new ArrayList<>();
	}

	@Override
	public boolean addEmployee(Employee employee) {
		write.lock();
		try {
			if (employees.size() == capacity || employees.contains(employee)) {
				return false;
			}
			employees.add(employee);
			return true;
		}finally {
			write.unlock();
		}
	}

	@Override
	public Employee removeEmployee(int id) {
		write.lock();
		try {
			Employee victim = findEmployee(id);
			employees.remove(victim);
			return victim;
		} finally {
			write.unlock();
		}

	}

	@Override
	public Employee findEmployee(int id) {
		read.lock();
		try {
			for (Employee employee : employees) {
				if(employee.getId() == id) {
					return employee;
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
		try {
			for (Employee employee : employees) {
				sum += employee.calcSalary();
			}
			return sum;
		} finally {
			read.unlock();
		}
	}

	@Override
	public int quantity() {
		read.lock();
		try {
			return employees.size();
		}finally {
			read.unlock();
		}
	}

	@Override
	public double totalSales() {
		double sum = 0;
		read.lock();
		try {
			for (Employee employee : employees) {
				if(employee instanceof SalesManager) {
					SalesManager sm = (SalesManager) employee;
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
			employees.forEach(e -> System.out.println(e));
		}finally {
			read.unlock();
		}

	}

}
