package kp.company.controller;

import kp.SampleDataset;
import kp.company.model.Employee;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static kp.TestConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * The {@link EmployeesController} tests using the {@link MockMvc}.
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeesController.class)
class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private static final boolean VERBOSE = false;

    /**
     * Should get the {@link Employee}s.
     * <p>
     * Tests a <b>GET</b> request.
     * </p>
     *
     * @throws Exception the {@link Exception}
     */
    @Test
    void shouldGetEmployees() throws Exception {
        // GIVEN
        SampleDataset.loadDataset(DEP_TEST_INDEX_UPPER_BOUND, EMP_TEST_INDEX_UPPER_BOUND);
        final MockHttpServletRequestBuilder requestBuilder = get(EMPLOYEES_PATH)
                .accept(MediaType.APPLICATION_JSON_VALUE);
        // WHEN
        final ResultActions resultActions = mockMvc.perform(requestBuilder);
        // THEN
        if (VERBOSE) {
            resultActions.andDo(print());
        }
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
        resultActions.andExpect(jsonPath("$").isArray());
        resultActions.andExpect(jsonPath("$", Matchers.hasSize(DEP_TEST_SIZE * EMP_TEST_SIZE)));
        resultActions.andExpect(jsonPath("$[0].id").value(EXPECTED_EMPLOYEE_ID));
        resultActions.andExpect(jsonPath("$[0].firstName").value(EXPECTED_EMPLOYEE_F_NAME));
        resultActions.andExpect(jsonPath("$[0].lastName").value(EXPECTED_EMPLOYEE_L_NAME));
        resultActions.andExpect(jsonPath("$[0].title").value(EXPECTED_EMPLOYEE_TITLE));
    }

}
