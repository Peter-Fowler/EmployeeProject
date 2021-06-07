package testProjectPackage;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import projectPackage.Address;
import projectPackage.Employee;
import projectPackage.EmployeeServiceImpl;

public class TestEmployeeServicesImpl {

	private EmployeeServiceImpl esi;

	@BeforeEach
	void initEach() {
		esi = new EmployeeServiceImpl();
	}

	@Test
	void testFindByEmployeeNo() {
		assertEquals(new Employee(001, "Wright", 10_000.00, new Address("Denver", "Colorado")).toString(),
				esi.findByEmployeeNo(001).toString());
	}

	@Test
	void testCapculateYearlySalary() {
		assertEquals(120_000.0, esi.calculateYearlySalary(esi.findByEmployeeNo(001)), 0);
	}

	@Test
	void testUpdateEmployeeName() {
		Employee fred = esi.findByEmployeeNo(001);
		fred.setEmpName("Fowler");
		System.out.println(fred.getEmpName() + " " + esi.findByEmployeeNo(001).getEmpName());
		assertFalse(fred.getEmpName().equalsIgnoreCase(esi.findByEmployeeNo(001).getEmpName()));
		esi.updateEmployee(fred);
		assertTrue(fred.getEmpName().equalsIgnoreCase(esi.findByEmployeeNo(001).getEmpName()));
	}

	@ParameterizedTest
	@ValueSource(doubles = { 5000.00, 7500.00, 20003.00 })
	void testUpdateEmployeeSalary(double salary) {
		Employee fred = esi.findByEmployeeNo(001);
		fred.setSalary(salary);
		System.out.println(fred.getSalary() + " " + esi.findByEmployeeNo(001).getSalary());
		assertFalse(fred.getSalary() == esi.findByEmployeeNo(001).getSalary());
		esi.updateEmployee(fred);
		assertTrue(fred.getSalary() == esi.findByEmployeeNo(001).getSalary());
	}

	@Test
	void testUpdateEmployeeAddress() {
		Employee fred = esi.findByEmployeeNo(001);
		Address newHome = new Address("Atlanta", "Georgia");
		fred.setAddress(newHome);
		assertFalse(fred.getAddress().getCity().equalsIgnoreCase(esi.findByEmployeeNo(001).getAddress().getCity()));
		assertFalse(fred.getAddress().getState().equalsIgnoreCase(esi.findByEmployeeNo(001).getAddress().getState()));
		esi.updateEmployee(fred);
		assertTrue(fred.getAddress().getCity().equalsIgnoreCase(esi.findByEmployeeNo(001).getAddress().getCity()));
		assertTrue(fred.getAddress().getState().equalsIgnoreCase(esi.findByEmployeeNo(001).getAddress().getState()));
	}

	@ParameterizedTest
	@ValueSource(ints = {1, 2, 4,})
	void testDeleteEmployee(int empNo) {
		Employee fred = esi.findByEmployeeNo(empNo);
		assertTrue(esi.findByEmployeeNo(empNo) != null);
		esi.deleteEmployee(fred);
		assertFalse(esi.findByEmployeeNo(empNo) != null);
	}
}
