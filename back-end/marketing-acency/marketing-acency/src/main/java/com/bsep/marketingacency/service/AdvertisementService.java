package com.bsep.marketingacency.service;

import com.bsep.marketingacency.dto.AdvertisementDto;
import com.bsep.marketingacency.dto.EmployeeDto;
import com.bsep.marketingacency.model.Advertisement;
import com.bsep.marketingacency.model.AdvertisementStatus;
import com.bsep.marketingacency.model.Employee;
import com.bsep.marketingacency.repository.AdvertisementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;

    public AdvertisementService(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    public List<Advertisement> getPendingAdvertisements() {
        return advertisementRepository.findByStatus(AdvertisementStatus.PENDING);
    }

    public List<Advertisement> getAcceptedAdvertisements() {
        return advertisementRepository.findByStatus(AdvertisementStatus.ACCEPTED);
    }

    public Advertisement updateAdvertisement(Advertisement updatedAdvertisement) {
        Advertisement existingAdvertisement = advertisementRepository.findById(updatedAdvertisement.getId())
                .orElse(null);
        if (existingAdvertisement != null) {
            existingAdvertisement.setClient(updatedAdvertisement.getClient());
            existingAdvertisement.setSlogan(updatedAdvertisement.getSlogan());
            existingAdvertisement.setDuration(updatedAdvertisement.getDuration());
            existingAdvertisement.setDescription(updatedAdvertisement.getDescription());
            existingAdvertisement.setDeadline(updatedAdvertisement.getDeadline());
            existingAdvertisement.setActive_from(updatedAdvertisement.getActive_from());
            existingAdvertisement.setActive_to(updatedAdvertisement.getActive_to());
            existingAdvertisement.setRequest_description(updatedAdvertisement.getRequest_description());
            existingAdvertisement.setStatus(updatedAdvertisement.getStatus());

            return advertisementRepository.save(existingAdvertisement);
        } else {
            return null;
        }
    }

    public List<Advertisement> getAdvertisementsByClientUserId(Long clientUserId) {
        return advertisementRepository.findByClientUserId(clientUserId);
    }

    public Advertisement saveAdvertisement(AdvertisementDto advertisementDto) {
        Advertisement advertisement = new Advertisement();
        advertisement.setClient(advertisementDto.getClient());
        advertisement.setSlogan(advertisementDto.getSlogan());
        advertisement.setDuration(advertisementDto.getDuration());
        advertisement.setDescription(advertisementDto.getDescription());
        advertisement.setDeadline(advertisementDto.getDeadline());
        advertisement.setActive_from(advertisementDto.getActive_from());
        advertisement.setActive_to(advertisementDto.getActive_to());
        advertisement.setRequest_description(advertisementDto.getRequest_description());
        advertisement.setStatus(advertisementDto.getStatus());

        return this.advertisementRepository.save(advertisement);
    }
}
