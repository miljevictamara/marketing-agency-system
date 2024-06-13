package com.bsep.marketingacency.service;

import com.bsep.marketingacency.dto.AdministratorDto;
import com.bsep.marketingacency.model.Administrator;
import com.bsep.marketingacency.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class AdministratorService {
    @Autowired
    private AdministratorRepository administratorRepository;

    public Administrator getAdministratorByUserId(Long userId) {
        return administratorRepository.findByUserId(userId);
    }

    public Administrator updateAdministrator(Administrator updatedAdministrator, SecretKey secretKey) {
        Administrator existingAdministrator = administratorRepository.findById(updatedAdministrator.getId())
                .orElse(null);
        if (existingAdministrator != null) {
            existingAdministrator.setFirstName(updatedAdministrator.getFirstName());
            existingAdministrator.setLastName(updatedAdministrator.getLastName());
            existingAdministrator.setAddress(updatedAdministrator.getAddress(), secretKey);
            existingAdministrator.setCity(updatedAdministrator.getCity());
            existingAdministrator.setCountry(updatedAdministrator.getCountry());
            existingAdministrator.setPhoneNumber(updatedAdministrator.getPhoneNumber(), secretKey);
            existingAdministrator.setUser(updatedAdministrator.getUser());

            return administratorRepository.save(existingAdministrator);
        }
        else {
            return null;
        }
    }

    public Administrator saveAdministrator(AdministratorDto administratorDto, SecretKey secretKey) {
        Administrator administrator = new Administrator();
        administrator.setFirstName(administratorDto.getFirstName());
        administrator.setLastName(administratorDto.getLastName());
        administrator.setAddress(administratorDto.getAddress(), secretKey);
        administrator.setCity(administratorDto.getCity());
        administrator.setCountry(administratorDto.getCountry());
        administrator.setPhoneNumber(administratorDto.getPhoneNumber(), secretKey);
        administrator.setUser(administratorDto.getUser());

        return this.administratorRepository.save(administrator);
    }
}
