package com.bsep.marketingacency.repository;

import com.bsep.marketingacency.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByMail(String mail);

    User findUserById(Long id);
    List<User> findAllByRolesName(String roleName);
}
