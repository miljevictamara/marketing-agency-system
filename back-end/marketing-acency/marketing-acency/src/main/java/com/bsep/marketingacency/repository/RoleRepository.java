package com.bsep.marketingacency.repository;

import com.bsep.marketingacency.model.RejectionNote;
import com.bsep.marketingacency.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
