package database;

import java.util.List;

import creatable.Employee;

public interface EmployeeDAO {

	public List<Employee> getAllEmployees();
	
	public Employee getEmployee(int no);
	  
	public void updateEmployee(Employee fred);
	
	public void deleteEmployeeByNo(int no);
	
	public void addEmployee(Employee fred);
	
	public int maxEmployeeNo();
}
