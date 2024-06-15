package com.bsep.marketingacency.service;

import com.bsep.marketingacency.controller.EmployeeController;
import com.bsep.marketingacency.dto.ClientDto;
import com.bsep.marketingacency.dto.EmployeeDto;
import com.bsep.marketingacency.keystores.KeyStoreReader;
import com.bsep.marketingacency.model.Advertisement;
import com.bsep.marketingacency.model.AdvertisementStatus;
import com.bsep.marketingacency.model.Client;
import com.bsep.marketingacency.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bsep.marketingacency.repository.EmployeeRepository;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserService userService;

    private Logger logger =  LoggerFactory.getLogger(EmailService.class);

    public Employee getEmployeeByUserId(Long userId) {
        return employeeRepository.findByUserId(userId);
    }

    public Employee updateEmployee(Employee updatedEmployee, SecretKey secretKey) {
        Employee existingEmployee = employeeRepository.findById(updatedEmployee.getId())
                .orElse(null);
        if (existingEmployee != null) {
            existingEmployee.setFirstName(updatedEmployee.getFirstName());
            existingEmployee.setLastName(updatedEmployee.getLastName());
            existingEmployee.setAddress(updatedEmployee.getAddress(), secretKey);
            existingEmployee.setCity(updatedEmployee.getCity());
            existingEmployee.setCountry(updatedEmployee.getCountry());
            existingEmployee.setPhoneNumber(updatedEmployee.getPhoneNumber(), secretKey);
            existingEmployee.setUser(updatedEmployee.getUser());

            return employeeRepository.save(existingEmployee);
        } else {
            return null;
        }
    }

    public List<Employee> getAllEmployees() throws IllegalBlockSizeException, BadPaddingException {

        List<Employee> employees = employeeRepository.findAll();
        KeyStoreReader keyStoreReader = new KeyStoreReader();

        for (Employee employee : employees) {
            String alias = employee.getUser().getMail();
            SecretKey secretKey = keyStoreReader.readSecretKey(
                    alias + ".jks",
                    alias,
                    "marketing-agency".toCharArray(),
                    "marketing-agency".toCharArray()
            );

            employee.setPhoneNumber(employee.getPhoneNumber(secretKey));
            employee.setAddress(employee.getAddress(secretKey));
        }

        if(employees.isEmpty()){
            logger.info("No employees found.");
        }else {
            logger.info("All employees successfully retrieved.");
        }

        return employees;
    }

    public Employee saveEmployee(EmployeeDto employeeDto, SecretKey secretKey) {
        Employee employee = new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setAddress(employeeDto.getAddress(), secretKey);
        employee.setCity(employeeDto.getCity());
        employee.setCountry(employeeDto.getCountry());
        employee.setPhoneNumber(employeeDto.getPhoneNumber(), secretKey);
        employee.setUser(employeeDto.getUser());

        return this.employeeRepository.save(employee);
    }
}
