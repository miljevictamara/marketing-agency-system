package com.bsep.marketingacency.repository;

import com.bsep.marketingacency.model.ResetPasswordToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Long> {
    ResetPasswordToken findByToken(String token);

    @Transactional
    void deleteByUserId (Long userId);
}
