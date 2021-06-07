package projectPackage;

import java.util.ArrayList;

public interface EmployeeService {

	 public void displayAllEmployees();
	 
	 public double calculateYearlySalary(Employee fred);
	 
	 public Employee findByEmployeeNo(int empNo);
	 
	 public void updateEmployee(Employee fred);
	 
	 public void deleteEmployee(Employee fred);
}
