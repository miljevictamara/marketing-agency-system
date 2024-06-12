package com.bsep.marketingacency.repository;

import com.bsep.marketingacency.model.KeyStoreAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyStoreAccessRepository extends JpaRepository<KeyStoreAccess, String> {
    KeyStoreAccess findByFileName(String fileName);
}