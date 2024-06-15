package com.bsep.marketingacency.repository;

import com.bsep.marketingacency.model.Client;
import com.bsep.marketingacency.model.ClientActivationToken;
import com.bsep.marketingacency.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ClientActivationTokenRepository extends JpaRepository<ClientActivationToken, UUID> {
    //void deleteByUserId(Long userId);

    @Transactional
    @Modifying
    void deleteByUser(User user);
}
