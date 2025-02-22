package kp.company.controller;

import kp.SampleDataset;
import kp.company.model.Department;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static kp.TestConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * The {@link DepartmentsController} tests using the {@link MockMvc}.
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(DepartmentsController.class)
class DepartmentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private static final boolean VERBOSE = false;

    /**
     * Should get the {@link Department}s.
     * <p>
     * Tests a <b>GET</b> request.
     * </p>
     *
     * @throws Exception the {@link Exception}
     */
    @Test
    void shouldGetDepartments() throws Exception {
        // GIVEN
        SampleDataset.loadDataset(DEP_TEST_INDEX_UPPER_BOUND, EMP_TEST_INDEX_UPPER_BOUND);
        final MockHttpServletRequestBuilder requestBuilder = get(DEPARTMENTS_PATH)
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
        resultActions.andExpect(jsonPath("$", Matchers.hasSize(DEP_TEST_SIZE)));
        checkDepartment(resultActions, "$[0].");
        checkEmployee(resultActions, "$[0].employees[0].");
    }

    /**
     * Should create the {@link Department}.
     * <p>
     * Tests a <b>POST</b> request.
     * </p>
     *
     * @throws Exception the {@link Exception}
     */
    @Test
    void shouldCreateDepartment() throws Exception {
        // GIVEN
        final MockHttpServletRequestBuilder requestBuilder = post(DEPARTMENTS_PATH)
                .accept(MediaType.APPLICATION_JSON_VALUE).content(CREATED_DEPARTMENT_CONTENT)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        // WHEN
        final ResultActions resultActions = mockMvc.perform(requestBuilder);
        // THEN
        if (VERBOSE) {
            resultActions.andDo(print());
        }
        resultActions.andExpect(status().isCreated());
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
        resultActions.andExpect(jsonPath("$.id").value(Long.toString(CREATED_DEPARTMENT_ID)));
        resultActions.andExpect(jsonPath("$.name").value(CREATED_DEPARTMENT_NAME));
        resultActions.andExpect(jsonPath("$.employees", Matchers.hasSize(EMP_CREATED_SIZE)));
        resultActions.andExpect(jsonPath("$.employees[0].id").value(CREATED_EMPLOYEE_ID));
        resultActions.andExpect(jsonPath("$.employees[0].firstName").value(CREATED_EMPLOYEE_F_NAME));
        resultActions.andExpect(jsonPath("$.employees[0].lastName").value(CREATED_EMPLOYEE_L_NAME));
        resultActions.andExpect(jsonPath("$.employees[0].title").value(CREATED_EMPLOYEE_TITLE));
    }

    /**
     * Should fail validation and get '400 Bad Request' error response when creating
     * department with invalid department data.
     * <p>
     * Tests a <b>POST</b> request.
     * </p>
     *
     * @throws Exception the {@link Exception}
     */
    @Test
    void shouldFailValidationWhenCreatingDepartmentWithInvalidDepartment() throws Exception {
        // GIVEN
        final MockHttpServletRequestBuilder requestBuilder = post(DEPARTMENTS_PATH)
                .accept(MediaType.APPLICATION_JSON_VALUE).content(CREATED_DEPARTMENT_INVALID_DEP_CONTENT)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        // WHEN
        final ResultActions resultActions = mockMvc.perform(requestBuilder);
        // THEN
        if (VERBOSE) {
            resultActions.andDo(print());
        }
        resultActions.andExpect(status().isBadRequest());
        for (String msg : VALIDATION_FAILED_DEP_MSG_LIST) {
            resultActions.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(msg)));
        }
    }

    /**
     * Should fail validation and get '400 Bad Request' error response when creating
     * department with invalid employee data.
     * <p>
     * Tests a <b>POST</b> request.
     * </p>
     *
     * @throws Exception the {@link Exception}
     */
    @Test
    void shouldFailValidationWhenCreatingDepartmentWithInvalidEmployee() throws Exception {
        // GIVEN
        final MockHttpServletRequestBuilder requestBuilder = post(DEPARTMENTS_PATH)
                .accept(MediaType.APPLICATION_JSON_VALUE).content(CREATED_DEPARTMENT_INVALID_EMP_CONTENT)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        // WHEN
        final ResultActions resultActions = mockMvc.perform(requestBuilder);
        // THEN
        if (VERBOSE) {
            resultActions.andDo(print());
        }
        resultActions.andExpect(status().isBadRequest());
        for (String msg : VALIDATION_FAILED_EMP_MSG_LIST) {
            resultActions.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(msg)));
        }
    }

    /**
     * Should get the {@link Department} by id.
     * <p>
     * Tests a <b>GET</b> request.
     * </p>
     *
     * @throws Exception the {@link Exception}
     */
    @Test
    void shouldGetDepartmentById() throws Exception {
        // GIVEN
        SampleDataset.loadDataset(DEP_TEST_INDEX_UPPER_BOUND, EMP_TEST_INDEX_UPPER_BOUND);
        final MockHttpServletRequestBuilder requestBuilder = get(DEPARTMENT_BY_ID_PATH)
                .accept(MediaType.APPLICATION_JSON_VALUE);
        // WHEN
        final ResultActions resultActions = mockMvc.perform(requestBuilder);
        // THEN
        if (VERBOSE) {
            resultActions.andDo(print());
        }
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
        checkDepartment(resultActions, "$.");
        checkEmployee(resultActions, "$.employees[0].");
    }

    /**
     * Should get '404 Not Found' error response when getting the {@link Department}
     * by absent id.
     * <p>
     * Tests a <b>GET</b> request.
     * </p>
     *
     * @throws Exception the {@link Exception}
     */
    @Test
    void shouldGetNotFoundErrorWhenGettingDepartmentByAbsentId() throws Exception {
        // GIVEN
        SampleDataset.loadDataset(DEP_TEST_INDEX_UPPER_BOUND, EMP_TEST_INDEX_UPPER_BOUND);
        final MockHttpServletRequestBuilder requestBuilder = get(DEPARTMENT_BY_ABSENT_ID_PATH)
                .accept(MediaType.APPLICATION_JSON_VALUE);
        // WHEN
        final ResultActions resultActions = mockMvc.perform(requestBuilder);
        // THEN
        if (VERBOSE) {
            resultActions.andDo(print());
        }
        resultActions.andExpect(status().isNotFound());
    }

    /**
     * Should get '400 Bad Request' error response when getting the
     * {@link Department} by bad id.
     * <p>
     * Tests a <b>GET</b> request.
     * </p>
     *
     * @throws Exception the {@link Exception}
     */
    @Test
    void shouldGetBadRequestErrorWhenGettingDepartmentByBadId() throws Exception {
        // GIVEN
        SampleDataset.loadDataset(DEP_TEST_INDEX_UPPER_BOUND, EMP_TEST_INDEX_UPPER_BOUND);
        final MockHttpServletRequestBuilder requestBuilder = get(DEPARTMENT_BY_BAD_ID_PATH)
                .accept(MediaType.APPLICATION_JSON_VALUE);
        // WHEN
        final ResultActions resultActions = mockMvc.perform(requestBuilder);
        // THEN
        if (VERBOSE) {
            resultActions.andDo(print());
        }
        resultActions.andExpect(status().isBadRequest());
    }

    /**
     * Should fail validation and get '400 Bad Request' error response when getting
     * the {@link Department} by invalid id.
     * <p>
     * Tests a <b>GET</b> request.
     * </p>
     *
     * @throws Exception the {@link Exception}
     */
    @Test
    void shouldFailValidationWhenGettingDepartmentByInvalidId() throws Exception {
        // GIVEN
        SampleDataset.loadDataset(DEP_TEST_INDEX_UPPER_BOUND, EMP_TEST_INDEX_UPPER_BOUND);
        final MockHttpServletRequestBuilder requestBuilder = get(DEPARTMENT_BY_INVALID_ID_PATH)
                .accept(MediaType.APPLICATION_JSON_VALUE);
        // WHEN
        final ResultActions resultActions = mockMvc.perform(requestBuilder);
        // THEN
        if (VERBOSE) {
            resultActions.andDo(print());
        }
        resultActions.andExpect(status().isBadRequest());
        resultActions
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(VALIDATION_FAILED_DEP_MSG)));
    }

    /**
     * Should delete the {@link Department}.
     * <p>
     * Tests a <b>DELETE</b> request.
     * </p>
     *
     * @throws Exception the {@link Exception}
     */
    @Test
    void shouldDeleteDepartment() throws Exception {
        // GIVEN
        SampleDataset.loadDataset(DEP_TEST_INDEX_UPPER_BOUND, EMP_TEST_INDEX_UPPER_BOUND);
        final MockHttpServletRequestBuilder requestBuilder1 = delete(DEPARTMENT_BY_ID_PATH)
                .accept(MediaType.APPLICATION_JSON_VALUE);
        // WHEN
        final ResultActions resultActions1 = mockMvc.perform(requestBuilder1);
        // THEN
        if (VERBOSE) {
            resultActions1.andDo(print());
        }
        resultActions1.andExpect(status().isNoContent());

        final MockHttpServletRequestBuilder requestBuilder2 = get(DEPARTMENT_BY_ID_PATH)
                .accept(MediaType.APPLICATION_JSON_VALUE);
        final ResultActions resultActions2 = mockMvc.perform(requestBuilder2);
        if (VERBOSE) {
            resultActions2.andDo(print());
        }
        resultActions2.andExpect(status().isNotFound());
    }

    /**
     * Should update the {@link Department}.
     * <p>
     * Tests a <b>PATCH</b> request.
     * </p>
     *
     * @throws Exception the {@link Exception}
     */
    @Test
    void shouldUpdateDepartment() throws Exception {
        // GIVEN
        SampleDataset.loadDataset(DEP_TEST_INDEX_UPPER_BOUND, EMP_TEST_INDEX_UPPER_BOUND);
        final MockHttpServletRequestBuilder requestBuilder1 = patch(DEPARTMENT_BY_ID_PATH)
                .accept(MediaType.APPLICATION_JSON_VALUE).content(UPDATED_DEPARTMENT_CONTENT)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        // WHEN
        final ResultActions resultActions1 = mockMvc.perform(requestBuilder1);
        // THEN
        if (VERBOSE) {
            resultActions1.andDo(print());
        }
        resultActions1.andExpect(status().isNoContent());

        final MockHttpServletRequestBuilder requestBuilder2 = get(DEPARTMENT_BY_ID_PATH)
                .accept(MediaType.APPLICATION_JSON_VALUE);
        final ResultActions resultActions2 = mockMvc.perform(requestBuilder2);
        if (VERBOSE) {
            resultActions2.andDo(print());
        }
        resultActions2.andExpect(status().isOk());
        resultActions2.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
        resultActions2.andExpect(jsonPath("$.id").value(Long.toString(EXPECTED_DEPARTMENT_ID)));
        resultActions2.andExpect(jsonPath("$.name").value(UPDATED_DEPARTMENT_NAME));
        resultActions2.andExpect(jsonPath("$.employees", Matchers.hasSize(EMP_TEST_SIZE)));
        resultActions2.andExpect(jsonPath("$.employees[0].id").value(EXPECTED_EMPLOYEE_ID));
        resultActions2.andExpect(jsonPath("$.employees[0].firstName").value(UPDATED_EMPLOYEE_F_NAME));
        resultActions2.andExpect(jsonPath("$.employees[0].lastName").value(UPDATED_EMPLOYEE_L_NAME));
        resultActions2.andExpect(jsonPath("$.employees[0].title").value(UPDATED_EMPLOYEE_TITLE));
    }

    /**
     * Should get the {@link Employee}s in the {@link Department}.
     * <p>
     * Tests a <b>GET</b> request.
     * </p>
     *
     * @throws Exception the {@link Exception}
     */
    @Test
    void shouldGetEmployeesInDepartment() throws Exception {
        // GIVEN
        SampleDataset.loadDataset(DEP_TEST_INDEX_UPPER_BOUND, EMP_TEST_INDEX_UPPER_BOUND);
        final MockHttpServletRequestBuilder requestBuilder = get(EMPLOYEES_IN_DEPARTMENT_PATH)
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
        resultActions.andExpect(jsonPath("$", Matchers.hasSize(EMP_TEST_SIZE)));
        checkEmployee(resultActions, "$[0].");
    }

    /**
     * Should create the {@link Employee} in the {@link Department}.
     * <p>
     * Tests a <b>POST</b> request.
     * </p>
     *
     * @throws Exception the {@link Exception}
     */
    @Test
    void shouldCreateEmployeeInDepartment() throws Exception {
        // GIVEN
        SampleDataset.loadDataset(DEP_TEST_INDEX_UPPER_BOUND, EMP_TEST_INDEX_UPPER_BOUND);
        final MockHttpServletRequestBuilder requestBuilder = post(EMPLOYEES_IN_DEPARTMENT_PATH)
                .accept(MediaType.APPLICATION_JSON_VALUE).content(CREATED_EMPLOYEE_CONTENT)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        // WHEN
        final ResultActions resultActions = mockMvc.perform(requestBuilder);
        // THEN
        if (VERBOSE) {
            resultActions.andDo(print());
        }
        resultActions.andExpect(status().isCreated());
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
        resultActions.andExpect(jsonPath("$.id").value(CREATED_EMPLOYEE_ID));
        resultActions.andExpect(jsonPath("$.firstName").value(CREATED_EMPLOYEE_F_NAME));
        resultActions.andExpect(jsonPath("$.lastName").value(CREATED_EMPLOYEE_L_NAME));
        resultActions.andExpect(jsonPath("$.title").value(CREATED_EMPLOYEE_TITLE));
    }

    /**
     * Should get the {@link Employee} in the {@link Department} by id.
     * <p>
     * Tests a <b>GET</b> request.
     * </p>
     *
     * @throws Exception the {@link Exception}
     */
    @Test
    void shouldGetEmployeeInDepartmentById() throws Exception {
        // GIVEN
        SampleDataset.loadDataset(DEP_TEST_INDEX_UPPER_BOUND, EMP_TEST_INDEX_UPPER_BOUND);
        final MockHttpServletRequestBuilder requestBuilder = get(EMPLOYEE_IN_DEPARTMENT_BY_ID_PATH)
                .accept(MediaType.APPLICATION_JSON_VALUE);
        // WHEN
        final ResultActions resultActions = mockMvc.perform(requestBuilder);
        // THEN
        if (VERBOSE) {
            resultActions.andDo(print());
        }
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
        checkEmployee(resultActions, "$.");
    }

    /**
     * Should get '404 Not Found' error response when getting the {@link Employee}
     * by absent id.
     * <p>
     * Tests a <b>GET</b> request.
     * </p>
     *
     * @throws Exception the {@link Exception}
     */
    @Test
    void shouldGetNotFoundErrorWhenGettingEmployeeInDepartmentByAbsentId() throws Exception {
        // GIVEN
        SampleDataset.loadDataset(DEP_TEST_INDEX_UPPER_BOUND, EMP_TEST_INDEX_UPPER_BOUND);
        final MockHttpServletRequestBuilder requestBuilder = get(EMPLOYEE_IN_DEPARTMENT_BY_ABSENT_ID_PATH)
                .accept(MediaType.APPLICATION_JSON_VALUE);
        // WHEN
        final ResultActions resultActions = mockMvc.perform(requestBuilder);
        // THEN
        if (VERBOSE) {
            resultActions.andDo(print());
        }
        resultActions.andExpect(status().isNotFound());
    }

    /**
     * Should get '400 Bad Request' error response when getting the {@link Employee}
     * by bad id.
     * <p>
     * Tests a <b>GET</b> request.
     * </p>
     *
     * @throws Exception the {@link Exception}
     */
    @Test
    void shouldGetBadRequestErrorWhenGettingEmployeeInDepartmentByBadId() throws Exception {
        // GIVEN
        SampleDataset.loadDataset(DEP_TEST_INDEX_UPPER_BOUND, EMP_TEST_INDEX_UPPER_BOUND);
        final MockHttpServletRequestBuilder requestBuilder = get(EMPLOYEE_IN_DEPARTMENT_BY_BAD_ID_PATH)
                .accept(MediaType.APPLICATION_JSON_VALUE);
        // WHEN
        final ResultActions resultActions = mockMvc.perform(requestBuilder);
        // THEN
        if (VERBOSE) {
            resultActions.andDo(print());
        }
        resultActions.andExpect(status().isBadRequest());
    }

    /**
     * Should fail validation and get '400 Bad Request' error response when getting
     * the {@link Employee} by invalid id.
     * <p>
     * Tests a <b>GET</b> request.
     * </p>
     *
     * @throws Exception the {@link Exception}
     */
    @Test
    void shouldFailValidationWhenGettingEmployeeInDepartmentByInvalidId() throws Exception {
        // GIVEN
        SampleDataset.loadDataset(DEP_TEST_INDEX_UPPER_BOUND, EMP_TEST_INDEX_UPPER_BOUND);
        final MockHttpServletRequestBuilder requestBuilder = get(EMPLOYEE_IN_DEPARTMENT_BY_INVALID_ID_PATH)
                .accept(MediaType.APPLICATION_JSON_VALUE);
        // WHEN
        final ResultActions resultActions = mockMvc.perform(requestBuilder);
        // THEN
        if (VERBOSE) {
            resultActions.andDo(print());
        }
        resultActions.andExpect(status().isBadRequest());
        resultActions
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString(VALIDATION_FAILED_EMP_MSG)));
    }

    /**
     * Should delete the {@link Employee} in the {@link Department}.
     * <p>
     * Tests a <b>DELETE</b> request.
     * </p>
     *
     * @throws Exception the {@link Exception}
     */
    @Test
    void shouldDeleteEmployeeInDepartment() throws Exception {
        // GIVEN
        SampleDataset.loadDataset(DEP_TEST_INDEX_UPPER_BOUND, EMP_TEST_INDEX_UPPER_BOUND);
        final MockHttpServletRequestBuilder requestBuilder1 = delete(EMPLOYEE_IN_DEPARTMENT_BY_ID_PATH)
                .accept(MediaType.APPLICATION_JSON_VALUE);
        // WHEN
        final ResultActions resultActions1 = mockMvc.perform(requestBuilder1);
        // THEN
        if (VERBOSE) {
            resultActions1.andDo(print());
        }
        resultActions1.andExpect(status().isNoContent());

        final MockHttpServletRequestBuilder requestBuilder2 = get(EMPLOYEE_IN_DEPARTMENT_BY_ID_PATH)
                .accept(MediaType.APPLICATION_JSON_VALUE);
        final ResultActions resultActions2 = mockMvc.perform(requestBuilder2);
        if (VERBOSE) {
            resultActions2.andDo(print());
        }
        resultActions2.andExpect(status().isNotFound());
    }

    /**
     * Should update the {@link Employee} in the {@link Department}.
     * <p>
     * Tests a <b>PATCH</b> request.
     * </p>
     *
     * @throws Exception the {@link Exception}
     */
    @Test
    void shouldUpdateEmployeeInDepartment() throws Exception {
        // GIVEN
        SampleDataset.loadDataset(DEP_TEST_INDEX_UPPER_BOUND, EMP_TEST_INDEX_UPPER_BOUND);
        final MockHttpServletRequestBuilder requestBuilder1 = patch(EMPLOYEE_IN_DEPARTMENT_BY_ID_PATH)
                .accept(MediaType.APPLICATION_JSON_VALUE).content(UPDATED_EMPLOYEE_CONTENT)
                .contentType(MediaType.APPLICATION_JSON_VALUE);
        // WHEN
        final ResultActions resultActions1 = mockMvc.perform(requestBuilder1);
        // THEN
        if (VERBOSE) {
            resultActions1.andDo(print());
        }
        resultActions1.andExpect(status().isNoContent());

        final MockHttpServletRequestBuilder requestBuilder2 = get(EMPLOYEE_IN_DEPARTMENT_BY_ID_PATH)
                .accept(MediaType.APPLICATION_JSON_VALUE);
        final ResultActions resultActions2 = mockMvc.perform(requestBuilder2);
        if (VERBOSE) {
            resultActions2.andDo(print());
        }
        resultActions2.andExpect(status().isOk());
        resultActions2.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
        resultActions2.andExpect(jsonPath("$.id").value(EXPECTED_EMPLOYEE_ID));
        resultActions2.andExpect(jsonPath("$.firstName").value(UPDATED_EMPLOYEE_F_NAME));
        resultActions2.andExpect(jsonPath("$.lastName").value(UPDATED_EMPLOYEE_L_NAME));
        resultActions2.andExpect(jsonPath("$.title").value(UPDATED_EMPLOYEE_TITLE));
    }

    /**
     * Checks the {@link Department}
     *
     * @param resultActions the {@link ResultActions}
     * @param prefix        the JSON path prefix
     * @throws Exception the {@link Exception}
     */
    private void checkDepartment(ResultActions resultActions, String prefix) throws Exception {

        resultActions.andExpect(jsonPath(prefix + "id").value(Long.toString(EXPECTED_DEPARTMENT_ID)));
        resultActions.andExpect(jsonPath(prefix + "name").value(EXPECTED_DEPARTMENT_NAME));
        resultActions.andExpect(jsonPath(prefix + "employees", Matchers.hasSize(EMP_TEST_SIZE)));
    }

    /**
     * Checks the {@link Employee}
     *
     * @param resultActions the {@link ResultActions}
     * @param prefix        the JSON path prefix
     * @throws Exception the {@link Exception}
     */
    private void checkEmployee(ResultActions resultActions, String prefix) throws Exception {

        resultActions.andExpect(jsonPath(prefix + "id").value(EXPECTED_EMPLOYEE_ID));
        resultActions.andExpect(jsonPath(prefix + "firstName").value(EXPECTED_EMPLOYEE_F_NAME));
        resultActions.andExpect(jsonPath(prefix + "lastName").value(EXPECTED_EMPLOYEE_L_NAME));
        resultActions.andExpect(jsonPath(prefix + "title").value(EXPECTED_EMPLOYEE_TITLE));
    }

}
