package com.bsep.marketingacency.service;

import com.bsep.marketingacency.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bsep.marketingacency.repository.EmployeeRepository;

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
            existingEmployee.setUserId(updatedEmployee.getUserId());

            return employeeRepository.save(existingEmployee);
        } else {
            return null;
        }
    }
}
