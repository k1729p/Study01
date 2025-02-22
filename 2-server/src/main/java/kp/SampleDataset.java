package kp;

import kp.company.model.Department;
import kp.company.model.Employee;
import kp.company.model.Title;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static kp.Constants.*;

/**
 * The sample dataset creator.
 * <p>
 * The standard dataset:
 * </p>
 * <ol>
 * <li>Department
 * <ol>
 * <li>Employee
 * <li>Employee
 * <li>Employee
 * </ol>
 * <li>Department
 * <ol>
 * <li>Employee
 * <li>Employee
 * <li>Employee
 * </ol>
 * </ol>
 */
public class SampleDataset {

    private static final Map<Long, Department> departmentMap = new TreeMap<>();
    private static long depIndexUpperBound = DEP_INDEX_UPPER_BOUND;
    private static long empIndexUpperBound = EMP_INDEX_UPPER_BOUND;

    /**
     * Private constructor to prevent instantiation.
     */
    private SampleDataset() {
    }

    /**
     * Loads the sample dataset.
     *
     * @param depIndex the {@link Department}'s index upper bound
     * @param empIndex the {@link Employee}'s index upper bound
     */
    public static void loadDataset(long depIndex, long empIndex) {

        depIndexUpperBound = depIndex;
        empIndexUpperBound = empIndex;
        departmentMap.clear();
        generateDepartments();
    }

    /**
     * Creates the {@link Department} in the sample dataset.
     *
     * @param department the {@link Department}
     * @return the {@link Optional} with the {@link Department}
     */
    public static Optional<Department> putDepartment(Department department) {

        if (Objects.isNull(department) || Objects.isNull(department.getId())
            || Objects.nonNull(departmentMap.get(department.getId()))) {
            return Optional.empty();
        }
        departmentMap.put(department.getId(), department);
        return Optional.of(department);
    }

    /**
     * Updates the {@link Department} in the sample dataset.
     *
     * @param id         the {@link Department}'s id
     * @param department the {@link Department}
     * @return the {@link Optional} with the {@link Department}
     */
    public static Optional<Department> mergeDepartment(Long id, Department department) {

        if (Objects.isNull(id) || Objects.isNull(department)) {
            return Optional.empty();
        }
        department.setId(id);
        final BinaryOperator<Department> mapper = (existingDep, updatedDep) -> {
            Optional.ofNullable(updatedDep.getName()).ifPresent(existingDep::setName);
            if (Objects.isNull(existingDep.getEmployees()) || Objects.isNull(updatedDep.getEmployees())) {
                return existingDep;
            }
            final Map<Long, Employee> existingEmpMap = existingDep.getEmployees().stream()
                    .collect(Collectors.toMap(Employee::getId, Function.identity()));
            final Map<Long, Employee> updatedEmpMap = updatedDep.getEmployees().stream()
                    .collect(Collectors.toMap(Employee::getId, Function.identity()));
            existingEmpMap.keySet().stream().filter(updatedEmpMap::containsKey)
                    .forEach(key -> mergeEmployee(id, key, updatedEmpMap.get(key)));
            return existingDep;
        };
        return Optional.of(departmentMap.merge(id, department, mapper));
    }

    /**
     * Removes the {@link Department} from the sample dataset.
     *
     * @param id the {@link Department}'s id
     * @return the {@link Optional} with the {@link Department}
     */
    public static Optional<Department> removeDepartment(Long id) {
        return Optional.ofNullable(id).map(departmentMap::remove);
    }

    /**
     * Gets the list of {@link Department}s from the sample dataset.
     *
     * @return the list of {@link Department}s
     */
    public static List<Department> getDepartments() {
        return departmentMap.values().stream().toList();
    }

    /**
     * Gets the {@link Department} by id from the sample dataset.
     *
     * @param id the {@link Department}'s id
     * @return the {@link Optional} with the {@link Department}
     */
    public static Optional<Department> getDepartment(Long id) {
        return Optional.ofNullable(id).map(departmentMap::get);
    }

    /**
     * Creates the {@link Employee} in the sample dataset.
     *
     * @param departmentId the {@link Department}'s id
     * @param employee     the {@link Employee}
     * @return the {@link Optional} with the {@link Employee}
     */
    public static Optional<Employee> putEmployee(Long departmentId, Employee employee) {

        if (Objects.isNull(departmentId) || Objects.isNull(employee) || Objects.isNull(employee.getId())
            || Objects.isNull(departmentMap.get(departmentId))) {
            return Optional.empty();
        }
        final Department department = departmentMap.get(departmentId);
        if (Objects.nonNull(department.getEmployees())
            && department.getEmployees().stream().anyMatch(emp -> employee.getId().equals(emp.getId()))) {
            return Optional.empty();
        }
        department.addEmployeesItem(employee);
        departmentMap.put(departmentId, department);
        return Optional.of(employee);
    }

    /**
     * Updates the {@link Employee} in the sample dataset.
     *
     * @param departmentId the {@link Department}'s id
     * @param employeeId   the {@link Employee}'s id
     * @param employee     the {@link Employee}
     * @return the {@link Optional} with the {@link Employee}
     */
    public static Optional<Employee> mergeEmployee(Long departmentId, Long employeeId, Employee employee) {

        if (Objects.isNull(employee)) {
            return Optional.empty();
        }
        final Optional<Employee> existingEmployeeOpt = getEmployee(departmentId, employeeId);
        if (existingEmployeeOpt.isPresent()) {
            final Employee existingEmployee = existingEmployeeOpt.get();
            Optional.ofNullable(employee.getFirstName()).ifPresent(existingEmployee::setFirstName);
            Optional.ofNullable(employee.getLastName()).ifPresent(existingEmployee::setLastName);
            Optional.ofNullable(employee.getTitle()).ifPresent(existingEmployee::setTitle);
        } else {
            employee.setId(employeeId);
            departmentMap.get(departmentId).addEmployeesItem(employee);
        }
        return Optional.of(employee);
    }

    /**
     * Removes the {@link Employee} from the sample dataset.
     *
     * @param departmentId the {@link Department}'s id
     * @param employeeId   the {@link Employee}'s id
     * @return the {@link Optional} with the {@link Employee}
     */
    public static Optional<Employee> removeEmployee(Long departmentId, Long employeeId) {

        final Optional<Employee> existingEmployeeOpt = getEmployee(departmentId, employeeId);
        existingEmployeeOpt.ifPresent(employee -> departmentMap.get(departmentId).getEmployees().remove(employee));
        return existingEmployeeOpt;
    }

    /**
     * Gets the list of {@link Employee} from the sample dataset.
     *
     * @param departmentId the id of the {@link Department}
     * @return the list of {@link Employee}
     */
    public static Optional<List<Employee>> getEmployees(Long departmentId) {
        return Optional.ofNullable(departmentId).map(departmentMap::get).map(Department::getEmployees);
    }

    /**
     * Gets the {@link Employee} by id from the sample dataset.
     *
     * @param departmentId the id of the {@link Department}
     * @param employeeId   the id of the {@link Employee}
     * @return the {@link Optional} with the {@link Employee}
     */
    public static Optional<Employee> getEmployee(Long departmentId, Long employeeId) {

        final Function<List<Employee>, Optional<Employee>> function =
                list -> list.stream().filter(emp -> employeeId.equals(emp.getId())).findFirst();
        return Optional.ofNullable(departmentId).map(departmentMap::get)
                .map(Department::getEmployees).filter(_ -> Objects.nonNull(employeeId)).flatMap(function);
    }

    /**
     * Gets the list of all {@link Employee} from the sample dataset.
     *
     * @return the list of {@link Employee}
     */
    public static List<Employee> getEmployees() {

        return departmentMap.values().stream()
                .map(dep -> Optional.ofNullable(dep.getEmployees()).orElse(List.of()))
                .flatMap(List::stream).toList();
    }

    /**
     * Generates the list of {@link Department}.
     */
    private static void generateDepartments() {
        LongStream.rangeClosed(DEP_INDEX_LOWER_BOUND, depIndexUpperBound).boxed()
                .forEach(SampleDataset::generateDepartment);
    }

    /**
     * Generates the {@link Department}.
     *
     * @param departmentId the id of the {@link Department}
     */
    private static void generateDepartment(long departmentId) {

        final Department department = new Department().id(departmentId).name(DEP_NAME_FUN.apply(departmentId));
        generateEmployees(department);
        departmentMap.put(departmentId, department);
    }

    /**
     * Generates the list of {@link Employee}.
     *
     * @param department the {@link Department}
     */
    private static void generateEmployees(Department department) {

        LongStream.rangeClosed(EMP_INDEX_LOWER_BOUND, empIndexUpperBound).boxed()
                .filter(_ -> Objects.nonNull(department))
                .filter(_ -> Objects.nonNull(department.getId()))
                .map(empIndex -> EMP_INDEX_FUN.applyAsLong(department.getId(), empIndex))
                .forEach(employeeId -> generateEmployee(department, employeeId));
    }

    /**
     * Generates the {@link Employee}.
     *
     * @param department the {@link Department}
     * @param employeeId the id of the {@link Employee}
     */
    private static void generateEmployee(Department department, long employeeId) {

        final Employee employee = new Employee().id(employeeId).firstName(EMP_F_NAME_FUN.apply(employeeId))
                .lastName(EMP_L_NAME_FUN.apply(employeeId)).title(generateTitle(employeeId));
        department.addEmployeesItem(employee);
    }

    /**
     * Generates the {@link Title}.
     *
     * @param employeeId the id of the {@link Employee}
     */
    private static Title generateTitle(long employeeId) {

        final Predicate<Title> predicate = arg -> arg.ordinal() == ((int) employeeId - 1) % 3;
        return Stream.of(Title.values()).filter(predicate).findFirst().orElse(Title.ANALYST);
    }
}