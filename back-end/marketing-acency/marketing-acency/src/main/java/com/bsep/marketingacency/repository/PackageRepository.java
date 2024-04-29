package com.bsep.marketingacency.repository;

import com.bsep.marketingacency.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<Package, Long> {
}
