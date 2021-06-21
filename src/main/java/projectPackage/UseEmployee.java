package projectPackage;

import java.util.Scanner;
import java.util.logging.Logger;
import problems.EmployeeNotFound;

public class UseEmployee {

	private static final Logger LOGGER = Logger.getLogger(UseEmployee.class.getName());

	public static void main(String[] args) {

		LOGGER.info("Logger Name: " + LOGGER.getName());
		EmployeeServiceImpl esi = new EmployeeServiceImpl();
		Scanner input = new Scanner(System.in);
		int menu = 0;

		do {
			System.out.println("Please choose a number:\n" + "1. List all employees.\n"
					+ "2. Display yearly salary of \"Employee\".\n" + "3. Display specific employee details.\n"
					+ "4. Modify an employee's details.\n5. Add an employee.\n6. Delete an employee.\n" 
					+ "7. Exit program.");

			menu = input.nextInt();

			switch (menu) {
			default:
				LOGGER.warning("User input: \"" + menu + "\" not recognized");
				System.out.println("Input Unrecognized");
				break;
			case 1: // List all employees
				LOGGER.info("User Selected Show all employees");
				esi.displayAllEmployees();
				break;
			case 2: // Display yearly salary of Employee
				LOGGER.info("User Selected Display yearly salary of Employee");
				System.out.println("Enter \"Employee Number\" of employee in question (e.g. 001): ");
				menu = input.nextInt();
				try {
					double salary = esi.calculateYearlySalary(esi.findByEmployeeNo(menu));
					System.out.println("The yearly salary for " + esi.findByEmployeeNo(menu).getEmpName() + " is: $"
							+ salary + "\n");
				} catch (EmployeeNotFound e) {
					LOGGER.info("Null Pointer Exception with employee number " + menu);
					System.out.println("The number: " + menu + " does not match any employee numbers.\n");
				}

				break;
			case 3: // Display specific employee details
				LOGGER.info("User Selected Display specific employee details");
				System.out.println("Enter \"Employee Number\" of employee in question (e.g. 001): ");
				menu = input.nextInt();
				try {
					System.out.println(esi.findByEmployeeNo(menu).toString());
				} catch (EmployeeNotFound e) {
					LOGGER.info("Null Pointer Exception with employee number " + menu);
					System.out.println("The number: " + menu + " does not match any employee numbers.\n");
				}
				break;
			case 4: // Modify an employee's details
				LOGGER.info("User Selected Modify an employee's details");
				System.out.println("Enter \"Employee Number\" of employee in question (e.g. 001): ");
				menu = input.nextInt();
				try {
					LOGGER.info("Modify " + esi.findByEmployeeNo(menu).getEmpNo() + " "
							+ esi.findByEmployeeNo(menu).getEmpName() + " details");
					esi.changeEmployee(esi.findByEmployeeNo(menu), input);
				} catch (EmployeeNotFound e) {
					LOGGER.info("Null Pointer Exception with employee number " + menu);
					System.out.println("The number: " + menu + " does not match any employee numbers.\n");
				}
				break;
			case 5: // Add an employee
				LOGGER.info("User Selected Add an employee");
				esi.addEmployee(input);
				break;
			case 6: // Delete an employee
				LOGGER.info("User Selected Delete an employee");
				System.out.println("Enter \"Employee Number\" of employee in question (e.g. 001): ");
				menu = input.nextInt();
				try {
				LOGGER.info("Delete " + esi.findByEmployeeNo(menu).getEmpNo() + " "
						+ esi.findByEmployeeNo(menu).getEmpName());
				esi.deleteEmployee(esi.findByEmployeeNo(menu));
				System.out.println("They will be missed.\n");
				} catch (EmployeeNotFound e) {
					LOGGER.info("Null Pointer Exception with employee number " + menu);
					System.out.println("The number: " + menu + " does not match any employee numbers.\n");
				}
				break;
			case 7: // Exit program
				LOGGER.info("Exit program");
				menu = -1;
			}

		} while (menu != -1);

		input.close();

		System.out.println("Good Bye.");

	}

}
