package com.bsep.marketingacency.repository;

import com.bsep.marketingacency.enumerations.ClientType;
import com.bsep.marketingacency.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByUserId(Long userId);
    List<Client> findByType(ClientType type);
}
