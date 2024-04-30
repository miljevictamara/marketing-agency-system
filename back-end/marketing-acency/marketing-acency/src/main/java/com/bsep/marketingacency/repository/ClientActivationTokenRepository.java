package com.bsep.marketingacency.repository;

import com.bsep.marketingacency.model.Client;
import com.bsep.marketingacency.model.ClientActivationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ClientActivationTokenRepository extends JpaRepository<ClientActivationToken, UUID> {
}
