package kp;

import kp.company.model.Title;

import java.util.List;

@SuppressWarnings("doclint:missing")
public class TestConstants {

    public static final long DEP_TEST_INDEX_LOWER_BOUND = 1;
    public static final long DEP_TEST_INDEX_UPPER_BOUND = 2;
    public static final long EMP_TEST_INDEX_LOWER_BOUND = 1;
    public static final long EMP_TEST_INDEX_UPPER_BOUND = 2;
    public static final int DEP_TEST_SIZE = Long.valueOf(DEP_TEST_INDEX_UPPER_BOUND - DEP_TEST_INDEX_LOWER_BOUND + 1)
            .intValue();
    public static final int EMP_TEST_SIZE = Long.valueOf(EMP_TEST_INDEX_UPPER_BOUND - EMP_TEST_INDEX_LOWER_BOUND + 1)
            .intValue();

    private static final String ROOT = "/";
    public static final String DEPARTMENTS_PATH = ROOT + "departments";
    public static final String DEPARTMENT_BY_ID_PATH = ROOT + "departments/1";
    public static final String EMPLOYEES_IN_DEPARTMENT_PATH = ROOT + "departments/1/employees";
    public static final String EMPLOYEE_IN_DEPARTMENT_BY_ID_PATH = ROOT + "departments/1/employees/101";
    public static final String EMPLOYEES_PATH = ROOT + "employees";
    public static final String TITLES_PATH = ROOT + "titles";

    public static final String DEPARTMENT_BY_ABSENT_ID_PATH = ROOT + "departments/99999";
    public static final String DEPARTMENT_BY_BAD_ID_PATH = ROOT + "departments/ABC";
    public static final String DEPARTMENT_BY_INVALID_ID_PATH = ROOT + "departments/0";
    public static final String EMPLOYEE_IN_DEPARTMENT_BY_ABSENT_ID_PATH = ROOT + "departments/1/employees/99999";
    public static final String EMPLOYEE_IN_DEPARTMENT_BY_BAD_ID_PATH = ROOT + "departments/1/employees/ABC";
    public static final String EMPLOYEE_IN_DEPARTMENT_BY_INVALID_ID_PATH = ROOT + "departments/1/employees/0";

    public static final long EXPECTED_DEPARTMENT_ID = DEP_TEST_INDEX_LOWER_BOUND;
    public static final String EXPECTED_DEPARTMENT_NAME = Constants.DEP_NAME_FUN.apply(DEP_TEST_INDEX_LOWER_BOUND);

    public static final long EXPECTED_EMPLOYEE_ID = Constants.EMP_INDEX_FUN.applyAsLong(DEP_TEST_INDEX_LOWER_BOUND,
            EMP_TEST_INDEX_LOWER_BOUND);
    public static final String EXPECTED_EMPLOYEE_F_NAME = Constants.EMP_F_NAME_FUN
            .apply(Constants.EMP_INDEX_FUN.applyAsLong(DEP_TEST_INDEX_LOWER_BOUND, EMP_TEST_INDEX_LOWER_BOUND));
    public static final String EXPECTED_EMPLOYEE_L_NAME = Constants.EMP_L_NAME_FUN
            .apply(Constants.EMP_INDEX_FUN.applyAsLong(DEP_TEST_INDEX_LOWER_BOUND, EMP_TEST_INDEX_LOWER_BOUND));
    public static final String EXPECTED_EMPLOYEE_TITLE = Title.DEVELOPER.name().toLowerCase();

    public static final String VALIDATION_FAILED_DEP_MSG = """
            getDepartmentById.depId: must be greater than or equal to 1""";
    public static final String VALIDATION_FAILED_EMP_MSG = """
            getEmployeeInDepartmentByIds.empId: must be greater than or equal to 1""";

    public static final long CREATED_DEPARTMENT_ID = 12345;
    public static final String CREATED_DEPARTMENT_NAME = Constants.DEP_NAME_FUN.apply(CREATED_DEPARTMENT_ID);

    public static final int EMP_CREATED_SIZE = 1;
    public static final long CREATED_EMPLOYEE_ID = 67890;
    public static final String CREATED_EMPLOYEE_F_NAME = Constants.EMP_F_NAME_FUN.apply(CREATED_EMPLOYEE_ID);
    public static final String CREATED_EMPLOYEE_L_NAME = Constants.EMP_L_NAME_FUN.apply(CREATED_EMPLOYEE_ID);
    public static final String CREATED_EMPLOYEE_TITLE = Title.ANALYST.name().toLowerCase();

    public static final String CREATED_EMPLOYEE_CONTENT =
            "{\"id\":%d,\"firstName\":\"%s\",\"lastName\":\"%s\",\"title\":\"%s\"}".formatted(
                    CREATED_EMPLOYEE_ID, CREATED_EMPLOYEE_F_NAME, CREATED_EMPLOYEE_L_NAME, CREATED_EMPLOYEE_TITLE);
    public static final String CREATED_DEPARTMENT_CONTENT = "{\"id\":%d,\"name\":\"%s\",\"employees\":[%s]}".formatted(
            CREATED_DEPARTMENT_ID, CREATED_DEPARTMENT_NAME, CREATED_EMPLOYEE_CONTENT);
    public static final String CREATED_DEPARTMENT_INVALID_DEP_CONTENT =
            "{\"id\":0,\"name\":\"~\",\"employees\":[%s]}".formatted(CREATED_EMPLOYEE_CONTENT);
    public static final String CREATED_DEPARTMENT_INVALID_EMP_CONTENT =
            "{\"id\":%d,\"name\":\"%s\",\"employees\":[{\"id\":0,\"firstName\":\"~\",\"lastName\":\"~\",\"title\":\"%s\"}]}"
                    .formatted(
                            CREATED_DEPARTMENT_ID, CREATED_DEPARTMENT_NAME, CREATED_EMPLOYEE_TITLE);
    public static final List<String> VALIDATION_FAILED_DEP_MSG_LIST = List.of("with 3 errors",
            "Field error in object 'department' on field 'name': rejected value [~]",
            "Field error in object 'department' on field 'id': rejected value [0]", "default message [must match",
            "default message [size must be between 2 and 25]", "default message [must be greater than or equal to 1]");
    public static final List<String> VALIDATION_FAILED_EMP_MSG_LIST = List.of("with 5 errors",
            "Field error in object 'department' on field 'employees[0].firstName': rejected value [~]",
            "Field error in object 'department' on field 'employees[0].lastName': rejected value [~]",
            "Field error in object 'department' on field 'employees[0].id': rejected value [0]",
            "default message [must match", "default message [size must be between 2 and 25]",
            "default message [must be greater than or equal to 1]");

    public static final String UPDATED_DEPARTMENT_NAME = "D-Name-UPDATED";
    public static final String UPDATED_EMPLOYEE_F_NAME = "EF-Name-UPDATED";
    public static final String UPDATED_EMPLOYEE_L_NAME = "EL-Name-UPDATED";
    public static final String UPDATED_EMPLOYEE_TITLE = Title.MANAGER.name().toLowerCase();

    public static final String UPDATED_DEPARTMENT_CONTENT =
            "{\"name\":\"%s\",\"employees\":[{\"id\":%d,\"firstName\":\"%s\",\"lastName\":\"%s\",\"title\":\"%s\"}]}"
                    .formatted(
                            UPDATED_DEPARTMENT_NAME, EXPECTED_EMPLOYEE_ID, UPDATED_EMPLOYEE_F_NAME, UPDATED_EMPLOYEE_L_NAME,
                            UPDATED_EMPLOYEE_TITLE);
    public static final String UPDATED_EMPLOYEE_CONTENT =
            "{\"firstName\":\"%s\",\"lastName\":\"%s\",\"title\":\"%s\"}".formatted(
                    UPDATED_EMPLOYEE_F_NAME, UPDATED_EMPLOYEE_L_NAME, UPDATED_EMPLOYEE_TITLE);

    private TestConstants() {
        throw new IllegalStateException("Utility class");
    }
}
