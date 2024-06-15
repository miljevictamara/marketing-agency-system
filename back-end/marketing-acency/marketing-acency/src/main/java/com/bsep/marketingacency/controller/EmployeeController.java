package com.bsep.marketingacency.controller;

import com.bsep.marketingacency.dto.ClientDto;
import com.bsep.marketingacency.dto.EmployeeDto;
import com.bsep.marketingacency.keystores.KeyStoreReader;
import com.bsep.marketingacency.keystores.KeyStoreWriter;
import com.bsep.marketingacency.model.Client;
import com.bsep.marketingacency.model.Employee;
import com.bsep.marketingacency.service.EmployeeService;
import com.bsep.marketingacency.util.HashUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping("/employee")
public class EmployeeController {
    private Logger logger =  LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    // pristup: Employee
    //@PreAuthorize("hasAuthority('ROLE_EMPLOYEE')")
//    @GetMapping("/byUserId/{userId}")
//    @PreAuthorize("hasAuthority('GET_EMPLOYEE_BYUSERID')")
//    public ResponseEntity<EmployeeDto> getEmployeeByUserId(@PathVariable Long userId) {
//        Employee employee = employeeService.getEmployeeByUserId(userId);
//        if (employee != null) {
//            EmployeeDto employeeDto = new EmployeeDto(
//                    employee.getId(),
//                    employee.getFirstName(),
//                    employee.getLastName(),
//                    employee.getAddress(),
//                    employee.getCity(),
//                    employee.getCountry(),
//                    employee.getPhoneNumber(),
//                    employee.getUser()
//            );
//            return new ResponseEntity<>(employeeDto, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @GetMapping("/byUserId/{userId}")
    @PreAuthorize("hasAuthority('GET_EMPLOYEE_BYUSERID')")
    public ResponseEntity<EmployeeDto> getEmployeeByUserId(@PathVariable Long userId) {
        try {
            KeyStoreReader keyStoreReader = new KeyStoreReader();

            Employee employee = employeeService.getEmployeeByUserId(userId);
            String alias = employee.getUser().getMail();

            SecretKey secretKey = keyStoreReader.readSecretKey(employee.getUser().getMail()+".jks", alias, "marketing-agency".toCharArray(), "marketing-agency".toCharArray());

            logger.info("Trying to retrieve employee {} information.", employee.getUser().getMail());
            if (employee != null) {
                EmployeeDto employeeDto = new EmployeeDto(
                        employee.getId(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getAddress(secretKey),
                        employee.getCity(),
                        employee.getCountry(),
                        employee.getPhoneNumber(secretKey),
                        employee.getUser()
                );
                logger.info("Employee {} information successfully retrieved.", employee.getUser().getMail());
                return new ResponseEntity<>(employeeDto, HttpStatus.OK);
            } else {
                logger.warn("Employee {} not found.", employee.getUser().getMail());
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error fetching employee with user ID {}.", HashUtil.hash(userId.toString()));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // pristup: Employee
//    @PutMapping("/update")
//    @PreAuthorize("hasAuthority('UPDATE_EMPLOYEE')")
//    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto) {
//        Employee updatedEmployee = new Employee(
//                employeeDto.getId(),
//                employeeDto.getFirstName(),
//                employeeDto.getLastName(),
//                employeeDto.getAddress(),
//                employeeDto.getCity(),
//                employeeDto.getCountry(),
//                employeeDto.getPhoneNumber(),
//                employeeDto.getUser()
//        );
//
//        Employee updated = employeeService.updateEmployee(updatedEmployee);
//
//        if (updated != null) {
//            return new ResponseEntity<>(employeeDto, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('UPDATE_EMPLOYEE')")
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto) {
        try {
            KeyStoreReader keyStoreReader = new KeyStoreReader();
            String alias = employeeDto.getUser().getMail();
            SecretKey secretKey = keyStoreReader.readSecretKey(employeeDto.getUser().getMail() + ".jks", alias, "marketing-agency".toCharArray(), "marketing-agency".toCharArray());

            logger.info("Trying to update employee {}.", employeeDto.getUser().getMail());
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

            Employee updated = employeeService.updateEmployee(updatedEmployee, secretKey);

            if (updated != null) {
                logger.info("Employee {} successfully updated.", employeeDto.getUser().getMail());
                return new ResponseEntity<>(employeeDto, HttpStatus.OK);
            } else {
                logger.warn("Employee {} not found for updating.", employeeDto.getUser().getMail());
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error updating employee {}.", employeeDto.getUser().getMail());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // pristup: Administrator
//    @GetMapping("/all")
//    @PreAuthorize("hasAuthority('GET_ALL_EMPLOYEES')")
//    public List<Employee> getAllEmployees() {
//        return employeeService.getAllEmployees();
//    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('GET_ALL_EMPLOYEES')")
    public List<Employee> getAllEmployees() throws IllegalBlockSizeException, BadPaddingException {
        try {
            logger.info("Trying to retrieve all employees.");
            return employeeService.getAllEmployees();
        } catch (Exception e) {
            logger.error("Error fetching all employees.");
            throw e;
        }
    }


    // pristup: Administrator
//    @PostMapping(value = "/create")
//    @PreAuthorize("hasAuthority('CREATE_EMPLOYEE')")
//    public ResponseEntity<Map<String, String>> createEmployee(@RequestBody EmployeeDto employeeDto) {
//        Employee savedEmployee = employeeService.saveEmployee(employeeDto);
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "Employee created.");
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('CREATE_EMPLOYEE')")
    public ResponseEntity<Map<String, String>> createEmployee(@RequestBody EmployeeDto employeeDto) {
        try {
            SecretKey secretKey = KeyStoreWriter.generateSecretKey("AES", 256);
            KeyStoreWriter keyStoreWriter = new KeyStoreWriter();
            keyStoreWriter.loadKeyStore(null, "marketing-agency".toCharArray());
            keyStoreWriter.writeSecretKey(employeeDto.getUser().getMail(), secretKey, "marketing-agency".toCharArray());
            keyStoreWriter.saveKeyStore(employeeDto.getUser().getMail() + ".jks", "marketing-agency".toCharArray());

            Employee savedEmployee = employeeService.saveEmployee(employeeDto, secretKey);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Employee created.");
            logger.info("Employee {} successfully created.", savedEmployee.getUser().getMail());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating employee: {}", e.getMessage());
            throw e;
        }
    }


}
