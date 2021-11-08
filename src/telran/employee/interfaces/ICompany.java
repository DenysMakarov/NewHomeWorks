package telran.employee.interfaces;

import telran.employee.model.Employee;

public interface ICompany {
	String TITLE = "Apple";
	
	boolean addEmployee(Employee employee);

	Employee removeEmployee(int id);

	Employee findEmployee(int id);

	double totalSalary();

	int quantity();

	default double avgSalary() {
		return totalSalary() / quantity();
	}

	double totalSales();
	
	void printEmployees();
}
