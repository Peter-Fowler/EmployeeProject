package testProjectPackage;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import creatable.Address;
import creatable.Employee;
import database.ConnectionUtil;
import problems.EmployeeNotFound;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import projectPackage.EmployeeServiceImpl;

public class TestEmployeeServicesImpl {

	private EmployeeServiceImpl esi;

	@BeforeEach
	void initEach() {
		esi = new EmployeeServiceImpl();
	}

	@Test
	void testFindByEmployeeNo() {
		//esi.displayAllEmployees();
		Employee fred = new Employee(003, "Bailey", 4197.00, (new Address("Chicago", "Illinois")));
		try {
			assertEquals(fred.toString(), esi.findByEmployeeNo(003).toString());
		} catch (EmployeeNotFound e) {
			e.getMessage();
		}
	}

	@Test
	void testCapculateYearlySalary() {
	//	esi.displayAllEmployees();
		try {
			assertEquals(50_364.0, esi.calculateYearlySalary(esi.findByEmployeeNo(003)), 0);
		} catch (EmployeeNotFound e) {
			e.getMessage();
		}
	}

	@Test
	void testUpdateEmployeeName() { 
	//	esi.displayAllEmployees();
		try {
			Employee fred = esi.findByEmployeeNo(001);
			fred.setEmpName("Fowler");
			System.out.println(fred.getEmpName() + " " + esi.findByEmployeeNo(001).getEmpName());
			assertFalse(fred.getEmpName().equalsIgnoreCase(esi.findByEmployeeNo(001).getEmpName()));
			esi.updateEmployee(fred);
			assertTrue(fred.getEmpName().equalsIgnoreCase(esi.findByEmployeeNo(001).getEmpName()));
		} catch (EmployeeNotFound e) {
			e.getMessage();
		}
	}

	@ParameterizedTest
	@ValueSource(doubles = { 5000.00, 7500.00, 20003.00 })
	void testUpdateEmployeeSalary(double salary) {
	//	esi.displayAllEmployees();
		try {
			Employee fred = esi.findByEmployeeNo(001);
			fred.setSalary(salary);
			System.out.println(fred.getSalary() + " " + esi.findByEmployeeNo(001).getSalary());
			
			assertFalse(fred.getSalary() == esi.findByEmployeeNo(001).getSalary());
			
			esi.updateEmployee(fred);
			
			System.out.println(fred.getSalary() + " in test update salary");
			System.out.println(esi.findByEmployeeNo(001).getSalary() + " in test update salary");
			
			assertTrue(fred.getSalary() == esi.findByEmployeeNo(001).getSalary());
			
		} catch (EmployeeNotFound e) {
			e.getMessage();
		}
	}

	@Test
	void testUpdateEmployeeAddress() {
	//	esi.displayAllEmployees();
		try {
			Employee fred = esi.findByEmployeeNo(001);
			Address newHome = new Address("Atlanta", "Georgia");
			fred.setAddress(newHome);
			assertFalse(fred.getAddress().getCity().equalsIgnoreCase(esi.findByEmployeeNo(001).getAddress().getCity()));
			assertFalse(fred.getAddress().getState().equalsIgnoreCase(esi.findByEmployeeNo(001).getAddress().getState()));
			esi.updateEmployee(fred);
			assertTrue(fred.getAddress().getCity().equalsIgnoreCase(esi.findByEmployeeNo(001).getAddress().getCity()));
			assertTrue(fred.getAddress().getState().equalsIgnoreCase(esi.findByEmployeeNo(001).getAddress().getState()));
		} catch (EmployeeNotFound e) {
			e.getMessage();
		}
	}

	
	  @ParameterizedTest
	  
	  @ValueSource(ints = { 2, 4, 5, }) void testDeleteEmployee(int empNo) { //
	  esi.displayAllEmployees(); try { Employee fred = esi.findByEmployeeNo(empNo);
	  assertTrue(esi.findByEmployeeNo(empNo) != null); esi.deleteEmployee(fred);
	  assertTrue(esi.findByEmployeeNo(empNo) == null); } catch (EmployeeNotFound e)
	  { e.getMessage(); } }
	 
	  @AfterAll
	  public static void restoreDatabase() {

		  System.out.println("Before all");
		  
			String QUERY1 = "INSERT INTO employee VALUES(005, 'Watson', 2701.00, 'Sacramento','California')";
			 String QUERY2 = "INSERT INTO employee VALUES(002, 'Bennett', 3552.00, 'Scottsdale', 'Arizone')";
			String QUERY3 = "INSERT INTO employee VALUES(004,'Brown', 6810.00, 'Santa ana', 'California')";
			String QUERY4 = "UPDATE employee SET empName = 'Wright', salary = 10000.00, addCity = 'Denver', addState = 'Colorado' WHERE empNo = 001";
			
			System.out.println("Query1 made");
			
			int succeed = 0;
			
			try (Connection conn = ConnectionUtil.getConnection()) {

				Statement ps1 = conn.createStatement();
				succeed = ps1.executeUpdate(QUERY1);
				System.out.println("Query1 to database");
				
				Statement ps2 = conn.createStatement();
				succeed += ps2.executeUpdate(QUERY2);
				System.out.println("Query2 to database");
				
				Statement ps3 = conn.createStatement();
				succeed += ps3.executeUpdate(QUERY3);
				System.out.println("Query3 to database");
				
				Statement ps4 = conn.createStatement();
				succeed += ps4.executeUpdate(QUERY4);
				System.out.println("Query4 to database");
				

			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (succeed == 3) {
				
				System.out.println("All is back as it would be.");
			}else 
				System.out.println(succeed);
	  }
	  
	  
}
