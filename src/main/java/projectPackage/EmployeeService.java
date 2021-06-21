package projectPackage;

import java.util.ArrayList;

import creatable.Employee;
import problems.EmployeeNotFound;

public interface EmployeeService {

	 public void displayAllEmployees();
	 
	 public double calculateYearlySalary(Employee fred);
	 
	 public Employee findByEmployeeNo(int empNo) throws EmployeeNotFound;
	 
	 public void updateEmployee(Employee fred);
	 
	 public void deleteEmployee(Employee fred);
}
