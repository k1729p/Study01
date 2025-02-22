package kp.company.controller;

import kp.SampleDataset;
import kp.company.api.EmployeesApi;
import kp.company.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * The controller for the {@link Employee}.<br/>
 * The implementation of the {@link EmployeesApi}.
 */
@RestController
@Validated
public class EmployeesController implements EmployeesApi {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<List<Employee>> getEmployees() {

        final ResponseEntity<List<Employee>> responseEntity = Optional.of(SampleDataset.getEmployees())
                .filter(Predicate.not(List::isEmpty)).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        logger.info("getEmployees():");
        return responseEntity;
    }

}
