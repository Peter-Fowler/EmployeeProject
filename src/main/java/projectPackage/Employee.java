package projectPackage;


public class Employee {

	int empNo;
	String empName;
	double salary;
	Address address;
	
	public Employee(int empNo, String empName, double salary, Address address) {
		super();
		this.empNo = empNo;
		this.empName = empName;
		this.salary = salary;
		this.address = address;
	}

	public int getEmpNo() {
		return empNo;
	}

	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getNoName() {
		return this.getEmpNo() + " " + this.getEmpName();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ("Employee Number: " + this.getEmpNo() + ", Name: " + this.getEmpName() + ", Salary: $" 
				+ this.getSalary() + ", Address: " + this.getAddress().toString());
	}
	
	
}
