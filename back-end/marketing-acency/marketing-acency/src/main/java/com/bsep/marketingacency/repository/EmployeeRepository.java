package com.bsep.marketingacency.repository;

import com.bsep.marketingacency.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByUserId(Long userId);
}
