package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import creatable.Address;
import creatable.Employee;
import projectPackage.UseEmployee;

public class EmployeeDAOImpl implements EmployeeDAO {

	private static EmployeeDAOImpl instance;
	
	private EmployeeDAOImpl() {}
	
	public static EmployeeDAOImpl getInstance() {
		if (instance == null)
			instance = new EmployeeDAOImpl();
		return instance;
	}
	
	private static final Logger LOGGER = Logger.getLogger(UseEmployee.class.getName());

	@Override
	public List<Employee> getAllEmployees() {
		LOGGER.info("Getting all employees form the database");
		ArrayList<Employee> allEmpList = new ArrayList<Employee>();
		String QUERY = "SELECT * FROM employee ORDER BY empNo";

		try (Connection conn = ConnectionUtil.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(QUERY);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				allEmpList.add(new Employee(rs.getInt("empNo"), rs.getString("empName"), rs.getDouble("salary"),
						(new Address(rs.getString("addCity"), rs.getString("addState")))));
			}
		} catch (SQLException e) {
			System.out.println("Error retriving list of depts");
			e.printStackTrace();
		}
		return allEmpList;
	}

	@Override
	public Employee getEmployee(int no) {
		LOGGER.info("Getting employee with number: " + no + " from the database");
		Employee fred = new Employee(no);
		String QUERY = "SELECT * FROM Employee WHERE empNo =" + no;

		try (Connection conn = ConnectionUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(QUERY);
				ResultSet rs = ps.executeQuery(QUERY)) {
			while (rs.next()) {
				fred.setEmpName(rs.getString("empName"));
				fred.setSalary(rs.getDouble("salary"));
				fred.setAddress(new Address(rs.getString("addCity"), rs.getString("addState")));
			}
		} catch (SQLException e) {
			System.out.println("Error retriving list of depts");
			e.printStackTrace();
		}
		return fred;
	}
	
	@Override
	public void updateEmployee(Employee fred) {
		LOGGER.info("Updating employee with number: " + fred.getEmpNo() + " from the database");
		
		String QUERY = "UPDATE employee SET empName = '" + fred.getEmpName() + "', salary = " + fred.getSalary() 
		+ ", addCity = '" + fred.getAddress().getCity() + "', addState = '" + fred.getAddress().getState() + "' WHERE empNo = " + fred.getEmpNo();
		
		int succeed = -1;	
		
		try (Connection conn = ConnectionUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(QUERY)) {

			succeed = ps.executeUpdate();
			
			if (succeed == 1) {
				String COMMIT = "COMMIT";
				PreparedStatement cps = conn.prepareStatement(COMMIT);
				succeed = cps.executeUpdate();
			}

		} catch (SQLException e) {
			LOGGER.warning("SQL statement failed to update employee: " + fred.getEmpNo());
		}
		if (succeed == 1) {
			LOGGER.info("Employee: " + fred.getEmpNo() + " updated successfully");
			System.out.println("Empolyee updated successfully");
		}

	}

	@Override
	public void deleteEmployeeByNo(int no) {
		LOGGER.info("Deleteing employee with number: " + no + " from the database");
		String QUERY = "DELETE FROM employee WHERE empNo =" + no;
		int succeed = -1;

		try (Connection conn = ConnectionUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(QUERY)) {

			succeed = ps.executeUpdate();
			
			if (succeed == 1) {
				String COMMIT = "COMMIT";
				PreparedStatement cps = conn.prepareStatement(COMMIT);
				succeed = cps.executeUpdate();
			}

		} catch (SQLException e) {
			LOGGER.warning("SQL statement failed to delete employee: " + no);
			System.out.println("Failed to delete Empolyee");
		}
		if (succeed == 1) {
			LOGGER.info("Employee: " + no + " deleted successfully");
			System.out.println("Empolyee deleted successfully");
		}
	}

	@Override
	public void addEmployee(Employee fred) {
		LOGGER.info("Adding employee with number: " + fred.getEmpNo() + " to the database");
		String QUERY = "INSERT INTO employee VALUES(" + fred.getEmpNo() + ", '" + fred.getEmpName() + "', "
				+ fred.getSalary() + ", '" + fred.getAddress().getCity() + "', '" + fred.getAddress().getState() + "')";
		int succeed = -1;

		try (Connection conn = ConnectionUtil.getConnection()) {

			PreparedStatement ps = conn.prepareStatement(QUERY);
			succeed = ps.executeUpdate();
			
			if (succeed == 1) {
				String COMMIT = "COMMIT";
				PreparedStatement cps = conn.prepareStatement(COMMIT);
				succeed = cps.executeUpdate();
			}

		} catch (SQLException e) {
			LOGGER.warning("SQL statement failed to add employee: " + fred.getEmpNo());
			System.out.println("Failed to add Empolyee");
		}
		if (succeed == 1) {
			LOGGER.info("Employee: " + fred.getEmpNo() + " added successfully");
			System.out.println("Empolyee added successfully");
		}

	}

	@Override
	public int maxEmployeeNo() {
		LOGGER.info("Getting max employee number from the database");
		String QUERY = "SELECT MAX(empNo) FROM employee";
		int maxNo = 1;
		
		try (Connection conn = ConnectionUtil.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(QUERY)) {
			while (rs.next()) {
				maxNo = rs.getInt(1);
			}
		
	}catch(SQLException e) {
		e.printStackTrace();
		LOGGER.warning("MaxEmployeeNo failed!");
	}
		return maxNo;
	}
}
