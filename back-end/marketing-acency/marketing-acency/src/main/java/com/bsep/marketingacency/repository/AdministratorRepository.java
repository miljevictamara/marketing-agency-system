package com.bsep.marketingacency.repository;

import com.bsep.marketingacency.model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
    Administrator findByUserId(Long userId);
}
