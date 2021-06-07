package projectPackage;

import java.util.Scanner;
import java.util.logging.Logger;

public class UseEmployee {
	
	private static final Logger LOGGER = Logger.getLogger(UseEmployee.class.getName());

	public static void main(String[] args) {
		
		LOGGER.info("Logger Name: " + LOGGER.getName());
		EmployeeServiceImpl esi = new EmployeeServiceImpl();
		Scanner input = new Scanner(System.in);		
		int menu = 0;
		
		do {
			System.out.println("Please choose a number:\n"
					+ "1. List all employees.\n"
					+ "2. Display yearly salary of \"Employee\".\n"
					+ "3. Display specific employee details.\n"
					+ "4. Modify an employee's details.\n"
					+ "5. Delete an employee.\n"
					+ "6. Exit program.");
			
			menu = input.nextInt();
			
			switch(menu) {
			default:
				LOGGER.warning("User input: \"" + menu + "\" not recognized");
				System.out.println("Input Unrecognized");
				break;
			case 1: //List all employees
				LOGGER.info("Show all employees");
				esi.displayAllEmployees();
				break;
			case 2: //Display yearly salary of Employee
				LOGGER.info("Display yearly salary of Employee");
				System.out.println("Enter \"Employee Number\" of employee in question (e.g. 001): ");
				menu = input.nextInt();
				double salary = esi.calculateYearlySalary(esi.findByEmployeeNo(menu));
				System.out.println("The yearly salary for " + esi.findByEmployeeNo(menu).getEmpName() + " is: $" + salary + "\n");
				break;
			case 3: //Display specific employee details
				LOGGER.info("Display specific employee details");
				System.out.println("Enter \"Employee Number\" of employee in question (e.g. 001): ");
				menu = input.nextInt();
				System.out.println(esi.findByEmployeeNo(menu).toString());
				break;
			case 4: //Modify an employee's details
				LOGGER.info("Modify an employee's details");
				System.out.println("Enter \"Employee Number\" of employee in question (e.g. 001): ");
				menu = input.nextInt();
				LOGGER.info("Modify " + esi.findByEmployeeNo(menu).getEmpNo() + " " + esi.findByEmployeeNo(menu).getEmpName() + " details");
				esi.changeEmployee(esi.findByEmployeeNo(menu), input);
				break;
			case 5: //Delete an employee
				LOGGER.info("Delete an employee");
				System.out.println("Enter \"Employee Number\" of employee in question (e.g. 001): ");
				menu = input.nextInt();
				LOGGER.info("Delete " + esi.findByEmployeeNo(menu).getEmpNo() + " " + esi.findByEmployeeNo(menu).getEmpName());
				esi.deleteEmployee(esi.findByEmployeeNo(menu));
				System.out.println("They will be missed.\n");
				break;
			case 6: //Exit program
				LOGGER.info("Exit program");
			menu = -1;
			}
			
		}while(menu != -1);
		
		input.close();
		
		System.out.println("Good Bye.");

	}

}
