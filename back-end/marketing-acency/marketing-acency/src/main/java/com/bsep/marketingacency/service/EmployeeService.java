package com.bsep.marketingacency.service;

import com.bsep.marketingacency.dto.ClientDto;
import com.bsep.marketingacency.dto.EmployeeDto;
import com.bsep.marketingacency.model.Advertisement;
import com.bsep.marketingacency.model.AdvertisementStatus;
import com.bsep.marketingacency.model.Client;
import com.bsep.marketingacency.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bsep.marketingacency.repository.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserService userService;

    public Employee getEmployeeByUserId(Long userId) {
        return employeeRepository.findByUserId(userId);
    }

    public Employee updateEmployee(Employee updatedEmployee) {
        Employee existingEmployee = employeeRepository.findById(updatedEmployee.getId())
                .orElse(null);
        if (existingEmployee != null) {
            existingEmployee.setFirstName(updatedEmployee.getFirstName());
            existingEmployee.setLastName(updatedEmployee.getLastName());
            existingEmployee.setAddress(updatedEmployee.getAddress());
            existingEmployee.setCity(updatedEmployee.getCity());
            existingEmployee.setCountry(updatedEmployee.getCountry());
            existingEmployee.setPhoneNumber(updatedEmployee.getPhoneNumber());
            existingEmployee.setUser(updatedEmployee.getUser());

            return employeeRepository.save(existingEmployee);
        } else {
            return null;
        }
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee saveEmployee(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setAddress(employeeDto.getAddress());
        employee.setCity(employeeDto.getCity());
        employee.setCountry(employeeDto.getCountry());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        employee.setUser(employeeDto.getUser());

        return this.employeeRepository.save(employee);
    }
}
