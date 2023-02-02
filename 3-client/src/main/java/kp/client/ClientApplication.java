package kp.client;

import java.util.List;

import kp.company.api.DepartmentsApi;
import kp.company.model.Department;
import kp.company.model.Employee;

/**
 * The client application.
 *
 */
public class ClientApplication {

	private static final String BASE_PATH = "http://localhost:8080";
	private static final long DEPARTMENT_ID = 1L;
	private static final long EMPLOYEE_ID = 101L;

	/**
	 * The constructor.
	 */
	public ClientApplication() {
		super();
	}

	/**
	 * The entry point for the application.
	 * 
	 * @param args the command-line arguments
	 */
	public static void main(String[] args) {

		final ApiClient defaultClient = Configuration.getDefaultApiClient();
		defaultClient.setBasePath(BASE_PATH);
		final DepartmentsApi departmentsApi = new DepartmentsApi(defaultClient);
		try {
			callEndpoints(departmentsApi);
		} catch (ApiException e) {
			System.out.printf("ApiException[%s]%n", e.getMessage());
		} catch (Exception e) {
			System.out.printf("Exception[%s]%n", e.getMessage());
		}
	}

	/**
	 * Calls some endpoints on departments API.
	 * 
	 * @param departmentsApi the departments API
	 * @throws ApiException the {@link ApiException}
	 */
	private static void callEndpoints(DepartmentsApi departmentsApi) throws ApiException {

		System.out.println("*** The result from 'getDepartments()' ***");
		final List<Department> departmentList = departmentsApi.getDepartments();
		departmentList.forEach(ClientApplication::showDepartment);
		System.out.println("*** The result from 'getDepartmentById()' ***");
		final Department department = departmentsApi.getDepartmentById(DEPARTMENT_ID);
		showDepartment(department);
		System.out.println("*** The result from 'getEmployeesInDepartment()' ***");
		final List<Employee> employeeList = departmentsApi.getEmployeesInDepartment(DEPARTMENT_ID);
		employeeList.forEach(ClientApplication::showEmployee);
		System.out.println("*** The result from 'getEmployeeInDepartmentByIds()' ***");
		final Employee employee = departmentsApi.getEmployeeInDepartmentByIds(DEPARTMENT_ID, EMPLOYEE_ID);
		showEmployee(employee);
	}

	/**
	 * Shows the {@link Department}
	 * 
	 * @param department the {@link Department}
	 */
	private static void showDepartment(Department department) {
		System.out.printf("\t department: id[%d], name[%s], number of employees[%d]%n", department.getId(),
				department.getName(), department.getEmployees().size());
	}

	/**
	 * Shows the {@link Employee}
	 * 
	 * @param employee the {@link Employee}
	 */
	private static void showEmployee(Employee employee) {
		System.out.printf("\t employee: id[%d], first name[%s], last name[%s], title[%s]%n", employee.getId(),
				employee.getFirstName(), employee.getLastName(), employee.getTitle());
	}

}
