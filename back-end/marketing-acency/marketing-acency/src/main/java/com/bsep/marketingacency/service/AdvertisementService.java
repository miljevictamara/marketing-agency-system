package com.bsep.marketingacency.service;

import com.bsep.marketingacency.dto.AdvertisementDto;
import com.bsep.marketingacency.dto.EmployeeDto;
import com.bsep.marketingacency.model.Advertisement;
import com.bsep.marketingacency.model.AdvertisementStatus;
import com.bsep.marketingacency.model.Client;
import com.bsep.marketingacency.model.Employee;
import com.bsep.marketingacency.repository.AdvertisementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    @Autowired
    private ClientService clientService;

    public AdvertisementService(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    public List<Advertisement> getPendingAdvertisements() {
        return advertisementRepository.findByStatus(AdvertisementStatus.PENDING);
    }

    public List<Advertisement> getAcceptedAdvertisements() {
        return advertisementRepository.findByStatus(AdvertisementStatus.ACCEPTED);
    }

    public Advertisement updateAdvertisement(AdvertisementDto updatedAdvertisement) {
        Advertisement existingAdvertisement = advertisementRepository.findById(updatedAdvertisement.getId())
                .orElse(null);
        Long id = updatedAdvertisement.getClientId();
        Client client = clientService.findById(id);

        if (existingAdvertisement != null) {
            existingAdvertisement.setClient(client);
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
        Long id = advertisementDto.getClientId();
        Client client = clientService.findById(id);

        Advertisement advertisement = new Advertisement();
        advertisement.setClient(client);
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

    public Long getClientIdByAdvertismentId(Long advertismentId){
      Advertisement a =  advertisementRepository.getById(advertismentId);
      return a.getClient().getId();
    }
}
