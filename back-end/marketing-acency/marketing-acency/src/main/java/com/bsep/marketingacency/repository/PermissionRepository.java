package com.bsep.marketingacency.repository;

import com.bsep.marketingacency.model.Permission;
import com.bsep.marketingacency.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByName(String name);

}
