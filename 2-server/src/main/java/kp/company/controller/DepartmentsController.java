package kp.company.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import kp.SampleDataset;
import kp.company.api.DepartmentsApi;
import kp.company.model.Department;
import kp.company.model.Employee;

/**
 * The controller for the {@link Department}.<br/>
 * The implementation of the {@link DepartmentsApi}. The order of methods here
 * is equals to the order of endpoints on the Swagger page.
 * 
 */
@RestController
@Validated
public class DepartmentsController implements DepartmentsApi {
	private static final Log logger = LogFactory.getLog(MethodHandles.lookup().lookupClass().getName());

	/**
	 * The constructor.
	 * 
	 */
	public DepartmentsController() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<List<Department>> getDepartments() {

		final ResponseEntity<List<Department>> responseEntity = Optional.of(SampleDataset.getDepartments())
				.filter(list -> !list.isEmpty()).map(ResponseEntity::ok)//
				.orElse(ResponseEntity.notFound().build());
		logger.info("getDepartments():");
		return responseEntity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<Department> createDepartment(@Valid @RequestBody Department department) {

		final ResponseEntity<Department> responseEntity = SampleDataset.putDepartment(department)
				.map(dep -> new ResponseEntity<>(dep, HttpStatus.CREATED)).orElse(ResponseEntity.badRequest().build());
		logger.info(String.format("createDepartment(): department id[%d]", department.getId()));
		return responseEntity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<Department> getDepartmentById(@Min(1L) @PathVariable("depId") Long depId) {

		final ResponseEntity<Department> responseEntity = SampleDataset.getDepartment(depId).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
		logger.info(String.format("getDepartmentById(): department id[%d]", depId));
		return responseEntity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<Void> deleteDepartment(@Min(1L) @PathVariable("depId") Long depId) {

		final ResponseEntity<Void> responseEntity = SampleDataset.removeDepartment(depId)
				.map(dep -> ResponseEntity.noContent().<Void>build()).orElse(ResponseEntity.notFound().build());
		logger.info(String.format("deleteDepartment(): department id[%d]", depId));
		return responseEntity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<Void> updateDepartment(@Min(1L) @PathVariable("depId") Long depId,
			@Valid @RequestBody Department department) {

		final ResponseEntity<Void> responseEntity = SampleDataset.mergeDepartment(depId, department)
				.map(dep -> ResponseEntity.noContent().<Void>build()).orElse(ResponseEntity.notFound().build());
		logger.info(String.format("updateDepartment(): department id[%d]", depId));
		return responseEntity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<List<Employee>> getEmployeesInDepartment(@Min(1L) @PathVariable("depId") Long depId) {

		final ResponseEntity<List<Employee>> responseEntity = SampleDataset.getEmployees(depId).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
		logger.info(String.format("getEmployeesInDepartment(): department id[%d]", depId));
		return responseEntity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<Employee> createEmployeeInDepartment(@Min(1L) @PathVariable("depId") Long depId,
			@Valid @RequestBody Employee employee) {

		final ResponseEntity<Employee> responseEntity = SampleDataset.putEmployee(depId, employee)
				.map(dep -> new ResponseEntity<>(dep, HttpStatus.CREATED)).orElse(ResponseEntity.badRequest().build());
		logger.info(String.format("createEmployeeInDepartment(): department id[%d], employee id[%d]", depId,
				employee.getId()));
		return responseEntity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<Employee> getEmployeeInDepartmentByIds(@Min(1L) @PathVariable("depId") Long depId,
			@Min(1L) @PathVariable("empId") Long empId) {

		final ResponseEntity<Employee> responseEntity = SampleDataset.getEmployee(depId, empId).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
		logger.info(String.format("getEmployeeInDepartmentByIds(): department id[%d], employee id[%d]", depId, empId));
		return responseEntity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<Void> deleteEmployeeInDepartment(@Min(1L) @PathVariable("depId") Long depId,
			@Min(1L) @PathVariable("empId") Long empId) {

		final ResponseEntity<Void> responseEntity = SampleDataset.removeEmployee(depId, empId)
				.map(dep -> ResponseEntity.noContent().<Void>build()).orElse(ResponseEntity.notFound().build());
		logger.info(String.format("deleteEmployeeInDepartment(): department id[%d], employee id[%d]", depId, empId));
		return responseEntity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<Void> updateEmployeeInDepartment(@Min(1L) @PathVariable("depId") Long depId,
			@Min(1L) @PathVariable("empId") Long empId, @Valid @RequestBody Employee employee) {

		final ResponseEntity<Void> responseEntity = SampleDataset.mergeEmployee(depId, empId, employee)
				.map(dep -> ResponseEntity.noContent().<Void>build()).orElse(ResponseEntity.notFound().build());
		logger.info(String.format("updateEmployeeInDepartment(): department id[%d], employee id[%d]", depId, empId));
		return responseEntity;
	}

}
