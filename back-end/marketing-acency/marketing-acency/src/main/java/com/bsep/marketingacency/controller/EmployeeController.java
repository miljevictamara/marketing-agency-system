package com.bsep.marketingacency.controller;

import com.bsep.marketingacency.dto.ClientDto;
import com.bsep.marketingacency.dto.EmployeeDto;
import com.bsep.marketingacency.model.Client;
import com.bsep.marketingacency.model.Employee;
import com.bsep.marketingacency.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping("/employee")
public class EmployeeController {
    private Logger logger =  LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private EmployeeService employeeService;

    // pristup: Employee
    //@PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
    @GetMapping("/byUserId/{userId}")
    @PreAuthorize("hasAuthority('GET_EMPLOYEE_BYUSERID')")
    public ResponseEntity<EmployeeDto> getEmployeeByUserId(@PathVariable Long userId) {
        Employee employee = employeeService.getEmployeeByUserId(userId);
        if (employee != null) {
            EmployeeDto employeeDto = new EmployeeDto(
                    employee.getId(),
                    employee.getFirstName(),
                    employee.getLastName(),
                    employee.getAddress(),
                    employee.getCity(),
                    employee.getCountry(),
                    employee.getPhoneNumber(),
                    employee.getUser()
            );
            return new ResponseEntity<>(employeeDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // pristup: Employee
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('UPDATE_EMPLOYEE')")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto) {
        Employee updatedEmployee = new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getAddress(),
                employeeDto.getCity(),
                employeeDto.getCountry(),
                employeeDto.getPhoneNumber(),
                employeeDto.getUser()
        );

        Employee updated = employeeService.updateEmployee(updatedEmployee);

        if (updated != null) {
            return new ResponseEntity<>(employeeDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // pristup: Administrator
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('GET_ALL_EMPLOYEES')")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    // pristup: Administrator

    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('CREATE_EMPLOYEE')")
    public ResponseEntity<String> createEmployee(@RequestBody EmployeeDto employeeDto) {

        Employee savedEmployee = employeeService.saveEmployee(employeeDto);

        return new ResponseEntity<>("Employee created.",HttpStatus.CREATED);
    }
}
