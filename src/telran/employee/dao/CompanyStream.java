package telran.employee.dao;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import telran.employee.interfaces.ICompany;
import telran.employee.model.Employee;
import telran.employee.model.SalesManager;

public class CompanyStream implements ICompany {
    private Set<Employee> employees;
    private int capacity;
    ReadWriteLock rwLock = new ReentrantReadWriteLock();
    Lock read = rwLock.readLock();
    Lock write = rwLock.writeLock();

    public CompanyStream(int capacity) {
        this.capacity = capacity;
        employees = new HashSet<>();
    }

    @Override
    public boolean addEmployee(Employee employee) {
        write.lock();
        try {
            if (employee == null || employees.size() == capacity) {
                return false;
            }
            return employees.add(employee);
        } finally {
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
            return employees.stream()
                    .filter(e -> e.getId() == id)
                    .findFirst()
                    .orElse(null);
        } finally {
            read.unlock();
        }

    }

    @Override
    public double totalSalary() {
        read.lock();
        try {
            return employees.stream()
                    .map(e -> e.calcSalary())
                    .reduce(0.0, (a, b) -> a + b);
        } finally {
            read.unlock();
        }

    }

    @Override
    public int quantity() {
        read.lock();
        try {
            return employees.size();
        } finally {
            read.unlock();
        }
    }

    @Override
    public double totalSales() {
        read.lock();
        try {
            return employees.stream()
                    .filter(e -> e instanceof SalesManager)
                    .map(e -> (SalesManager) e)
                    .map(s -> s.getSalesValue())
                    .reduce(0.0, (a, b) -> a + b);
        } finally {
            read.unlock();
        }
    }

    @Override
    public void printEmployees() {
        read.lock();
        try {
            employees.forEach(e -> System.out.println(e));

        } finally {
            read.unlock();
        }
    }
}
