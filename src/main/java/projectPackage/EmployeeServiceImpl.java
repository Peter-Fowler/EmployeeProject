package projectPackage;

import java.util.Scanner;
import java.util.TreeMap;
import java.util.logging.Logger;
import database.EmployeeDAOImpl;
import creatable.Address;
import creatable.Employee;
import problems.EmployeeNotFound;

public class EmployeeServiceImpl implements EmployeeService {

	EmployeeDAOImpl empData = EmployeeDAOImpl.getInstance();
	
	private static final Logger LOGGER = Logger.getLogger(EmployeeServiceImpl.class.getName());

	/*
	 * TreeMap<Integer, Employee> empAll = new TreeMap<Integer, Employee>() { {
	 * put(001, new Employee(001, "Wright", 10_000.00, new Address("Denver",
	 * "Colorado"))); put(002, new Employee(002, "Bennett", 3_552.00, new
	 * Address("Scottsdale", "Arizone"))); put(003, new Employee(003, "Bailey",
	 * 4_197.00, new Address("Chicago", "Illinois"))); put(004, new Employee(004,
	 * "Brown", 6_810.00, new Address("Santa ana", "California"))); put(005, new
	 * Employee(005, "Watson", 2_701.00, new Address("Sacramento", "California")));
	 * } };
	 */

	public void displayAllEmployees() {
		LOGGER.info("Display All Employees");
		
		empData.getAllEmployees().forEach((emp) -> System.out.println(emp.getNoName()));  //.forEach((k, employee) -> System.out.println(employee.getNoName()));

	}

	public double calculateYearlySalary(Employee fred) {
		LOGGER.info("calculate Yearly Salary for: " + fred.getEmpNo() + " " + fred.getEmpName());
		double salary = fred.getSalary() * 12;
		return salary;
	}

	public Employee findByEmployeeNo(int empNo) throws EmployeeNotFound {
		empNo = (Integer) empNo;

		LOGGER.info("Find By Employee Number Using " + empNo);

		Employee temp = empData.getEmployee(empNo);

		if (temp.getEmpName() == null) {
			LOGGER.warning("Employee with number: \"" + empNo + "\" not found");
			throw new EmployeeNotFound("Employee with number: \"" + empNo + "\" not found");

		}

		Employee fred = new Employee(temp.getEmpNo(), temp.getEmpName(), temp.getSalary(), temp.getAddress());
		LOGGER.info("Employee \"" + fred.getEmpName() + "\" found and will be returned");
		return fred;

	}

	public void updateEmployee(Employee fred) {
		LOGGER.info("Update Employee with ID number: " + fred.getEmpNo());
		
		empData.updateEmployee(fred);
		
		/*
		 * for (int i = 1; i < empAll.size(); i++) { if
		 * (empAll.get(Integer.valueOf(i)).getEmpNo() == fred.getEmpNo()) {
		 * System.out.println("found employee object");
		 * empAll.get(Integer.valueOf(i)).setAddress(fred.getAddress());
		 * empAll.get(Integer.valueOf(i)).setEmpName(fred.getEmpName());
		 * empAll.get(Integer.valueOf(i)).setSalary(fred.getSalary()); break; }
		 */
		
		LOGGER.info("Updated Info: " + fred.toString());
		System.out.println(fred.toString());

	}

	public void deleteEmployee(Employee fred) {
		LOGGER.info("Delete Employee: " + fred.getEmpNo() + " " + fred.getEmpName());

		empData.deleteEmployeeByNo(fred.getEmpNo());
		
		/*
		 * Integer empNo = (Integer) fred.getEmpNo();
		 * 
		 * empAll.remove(empNo);
		 */
	}

	public void changeEmployee(Employee fred, Scanner input) {
		LOGGER.info("Changing Employee: " + fred.getEmpNo() + " " + fred.getEmpName());
		int menu = -1;
		boolean changed = false;
		boolean check = false;

		do {
			System.out.println("What to update: \n" + "1. Employee Name: " + fred.getEmpName() + "\n"
					+ "2. Employee Monthly Salary: " + fred.getSalary() + "\n" + "3. Employee Address: "
					+ fred.getAddress() + "\n" + "4. Save Changes \n5. Exit Without Saving");

			menu = input.nextInt();

			switch (menu) {
			default:
				LOGGER.warning("User input: \"" + menu + "\" not recognized");
				System.out.println("Input Unrecognized");
				break;
			case 1:
				LOGGER.info("Changing Employee Name");
				String trash = input.nextLine();
				System.out.println("Change name \"" + fred.getEmpName() + "\" to: ");
				String newName = input.nextLine();
				check = confirmNameChoice(fred, newName, input);
				if (check) {
					LOGGER.info("Changes to Name Confirmed");
					fred.setEmpName(newName);
					changed = true;
				}
				break;
			case 2:
				LOGGER.info("Changing Employee Salary");
				System.out.println("Change monthly salary \"" + fred.getSalary() + "\" to: ");
				double newSalary = input.nextDouble();
				check = confirmSalaryChoice(fred, newSalary, input);
				if (check) {
					LOGGER.info("Changes to Salary Confirmed");
					fred.setSalary(newSalary);
					changed = true;
				}
				break;
			case 3:
				LOGGER.info("Changing Employee Address");
				System.out.println("Change address \"" + fred.getAddress() + "\" to\nCity: ");
				String newCity = input.next();
				System.out.println("State: ");
				String newState = input.next();
				check = confirmAddressChoice(fred, newCity, newState, input);
				if (check) {
					LOGGER.info("Changes to Address Confirmed");
					changed = true;
					fred.setAddress(new Address(newCity, newState));
				}
				break;
			case 4:
				LOGGER.info("Done Changing Employee");
				menu = -1;
				break;
			case 5:
				LOGGER.info("Leaving \"Update Employee\" without changing the employee");
				menu = -1;
				changed = false;

			}

		} while (menu != -1);

		if (changed) {
			LOGGER.info("About to Commit Changes");
			updateEmployee(fred);
		}
	}

	private boolean confirmAddressChoice(Employee fred, String newCity, String newState, Scanner input) {
		LOGGER.info("Confirming Address Change Choice");
		int yesNo;
		while (true) {
			System.out.println(
					"Change \"" + fred.getAddress() + "\" to \"" + newCity + ", " + newState + "\"?\n" + "1. Yes\t2. No");
			yesNo = input.nextInt();
			if (yesNo == 1)
				return true;
			else if (yesNo == 2)
				return false;
			else
				LOGGER.warning("User input: \"" + yesNo + "\" not recognized");
			System.out.println("Input Unrecognized");
		}
	}

	private boolean confirmSalaryChoice(Employee fred, double newSalary, Scanner input) {
		LOGGER.info("Confirming Salary Change Choice");
		int yesNo;
		while (true) {
			System.out.println("Change \"" + fred.getSalary() + "\" to \"" + newSalary + "?\n" + "1. Yes\t2. No");
			yesNo = input.nextInt();
			if (yesNo == 1)
				return true;
			else if (yesNo == 2)
				return false;
			else
				LOGGER.warning("User input: \"" + yesNo + "\" not recognized");
			System.out.println("Input Unrecognized");
		}
	}

	private boolean confirmNameChoice(Employee fred, String newName, Scanner input) {
		LOGGER.info("Confirming Name Change Choice");
		int yesNo;
		while (true) {
			System.out.println("Change \"" + fred.getEmpName() + "\" to \"" + newName + "\"?\n" + "1. Yes\t2. No");
			yesNo = input.nextInt();
			if (yesNo == 1)
				return true;
			else if (yesNo == 2)
				return false;
			else
				LOGGER.warning("User input: \"" + yesNo + "\" not recognized");
			System.out.println("Input Unrecognized");
		}
	}

	public void addEmployee(Scanner input) {
		LOGGER.info("Creating a new Employee intry");
		boolean done = false;
		String name;
		double salary;
		String city;
		String state;
		
		do {
		System.out.println("What is the new employee's name?");
		
		name = input.next();
		String temp = input.nextLine();
		
		System.out.println("What is " + name + "'s salary?");
		
		salary = input.nextDouble();
		temp = input.nextLine();
		
		System.out.println("In what city does " + name + " live?");
		
		city = input.nextLine();
		
		System.out.println("In what state is " + city + "?");
		
		state = input.nextLine();
		
		System.out.println("Is this information correct?\nName: " + name + "\nSalary: " + salary + "\nAddress: " + city + ", " + state
				+ "\n\n1. Yes\t2. No");
		if(input.nextInt() == 1)
			done = true;
		
		}while(!done);
		
		Employee fred = new Employee((empData.maxEmployeeNo() + 1), name, salary, new Address(city, state));
		
		empData.addEmployee(fred);
	}

}
