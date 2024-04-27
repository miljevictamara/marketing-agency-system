package com.bsep.marketingacency.repository;

import com.bsep.marketingacency.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByMail(String mail);
}
