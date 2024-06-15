package com.bsep.marketingacency.repository;

import com.bsep.marketingacency.model.Advertisement;
import com.bsep.marketingacency.model.AdvertisementStatus;
import com.bsep.marketingacency.model.Client;
import com.bsep.marketingacency.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    List<Advertisement> findByStatus(AdvertisementStatus status);

    List<Advertisement> findByClientUserId(Long clientUserId);

    List<Advertisement> findByClient(Client client);

}
