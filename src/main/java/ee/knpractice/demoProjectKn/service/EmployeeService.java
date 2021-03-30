package ee.knpractice.demoProjectKn.service;

import ee.knpractice.demoProjectKn.dto.EmployeeDto;

import java.util.List;

public interface EmployeeService {
    EmployeeDto saveEmployee(EmployeeDto employeeDto);
    List<EmployeeDto> getAllEmployees();
    EmployeeDto getEmployeeById(long id);
    EmployeeDto updateEmployee(long id, EmployeeDto employeeDto);
    void deleteEmployee(long id);
    void deleteAllEmployees();
    List<EmployeeDto> getAllEmployeesByFirstname(String firstname);
}
