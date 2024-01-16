package kp.company.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import kp.SampleDataset;
import kp.company.api.EmployeesApi;
import kp.company.model.Employee;

/**
 * The controller for the {@link Employee}.<br/>
 * The implementation of the {@link EmployeesApi}.
 * 
 */
@RestController
@Validated
public class EmployeesController implements EmployeesApi {
	private static final Log logger = LogFactory.getLog(MethodHandles.lookup().lookupClass().getName());

	/**
	 * The constructor.
	 * 
	 */
	public EmployeesController() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseEntity<List<Employee>> getEmployees() {

		final ResponseEntity<List<Employee>> responseEntity = Optional.of(SampleDataset.getEmployees())
				.filter(list -> !list.isEmpty()).map(ResponseEntity::ok)//
				.orElse(ResponseEntity.notFound().build());
		logger.info("getEmployees():");
		return responseEntity;
	}

}
