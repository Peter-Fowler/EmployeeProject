package projectPackage;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class EmployeeServiceImpl implements EmployeeService {

	private static final Logger LOGGER = Logger.getLogger(EmployeeServiceImpl.class.getName());

	ArrayList<Employee> empAll = new ArrayList<Employee>() {
		{
			add(new Employee(001, "Wright", 10_000.00, new Address("Denver", "Colorado")));
			add(new Employee(002, "Bennett", 3_552.00, new Address("Scottsdale", "Arizone")));
			add(new Employee(003, "Bailey", 4_197.00, new Address("Chicago", "Illinois")));
			add(new Employee(004, "Brown", 6_810.00, new Address("Santa ana", "California")));
			add(new Employee(005, "Watson", 2_701.00, new Address("Sacramento", "California")));
		}
	};

	public void displayAllEmployees() {
		LOGGER.info("Display All Employees");
		empAll.forEach(employee -> System.out.println(employee.getNoName()));

	}

	public double calculateYearlySalary(Employee fred) {
		LOGGER.info("calculate Yearly Salary for: " + fred.getEmpNo() + " " + fred.getEmpName());
		double salary = fred.getSalary() * 12;
		return salary;
	}

	public Employee findByEmployeeNo(int empNo) {
		LOGGER.info("Find By Employee Number");
		ArrayList<Employee> empObj = (ArrayList<Employee>) empAll.stream()
				.filter(employee -> employee.getEmpNo() == empNo).collect(Collectors.toList());
		if (empObj.isEmpty()) {
			LOGGER.info("Employee with number: \"" + empNo + "\" not found");
			return null;
		} else {
			Employee fred = new Employee(empObj.get(0).getEmpNo(), empObj.get(0).getEmpName(),
					empObj.get(0).getSalary(), empObj.get(0).getAddress());
			LOGGER.info("Employee \"" + fred.getEmpName() + "\" found and will be returned");
			return fred;
		}
	}

	public void updateEmployee(Employee fred) {
		LOGGER.info("Update Employee with number: " + fred.getEmpNo());
		for (int i = 0; i < empAll.size(); i++) {
			if (empAll.get(i).getEmpNo() == fred.getEmpNo()) {
				empAll.get(i).setAddress(fred.getAddress());
				empAll.get(i).setEmpName(fred.getEmpName());
				empAll.get(i).setSalary(fred.getSalary());
				break;
			}
		}
		LOGGER.info("Updated Info: " + fred.toString());
		System.out.println(fred.toString());

	}

	public void deleteEmployee(Employee fred) {
		boolean deleted = false;
		LOGGER.info("Delete Employee: " + fred.getEmpNo() + " " + fred.getEmpName());
		for (int i = 0; i < empAll.size(); i++) {
			if (fred.getEmpNo() == empAll.get(i).getEmpNo()) {
				empAll.remove(empAll.get(i));
				LOGGER.info("Deleting Successful" + fred.toString() + " has been removed.");
				System.out.println(fred.toString() + " has been removed.");
				deleted = true;
				break;
			}
		}
		if (!deleted) {
			LOGGER.warning("Delete Employee Failed");
			System.out.println("Error removing " + fred.getEmpName() + ".");
		}

	}

	public void changeEmployee(Employee fred, Scanner input) {
		LOGGER.info("Changing Employee: " + fred.getEmpNo() + " " + fred.getEmpName());
		int menu = -1;
		boolean changed = false;
		boolean check = false;

		do {
			System.out.println("What to update: \n" + "1. Employee Name: " + fred.getEmpName() + "\n"
					+ "2. Employee Monthly Salary: " + fred.getSalary() + "\n" + "3. Employee Address: "
					+ fred.getAddress() + "\n" + "4. Exit");

			menu = input.nextInt();

			switch (menu) {
			default:
				LOGGER.warning("User input: \"" + menu + "\" not recognized");
				System.out.println("Input Unrecognized");
				break;
			case 1:
				LOGGER.info("Changing Employee Name");
				System.out.println("Change name \"" + fred.getEmpName() + "\" to: ");
				String newName = input.next();
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
					"Change \"" + fred.getAddress() + "\" to \"" + newCity + ", " + newState + "?\n" + "1. Yes\t2. No");
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

}
