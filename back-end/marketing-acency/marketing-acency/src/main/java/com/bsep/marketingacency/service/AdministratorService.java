package com.bsep.marketingacency.service;

import com.bsep.marketingacency.model.Administrator;
import com.bsep.marketingacency.model.Employee;
import com.bsep.marketingacency.repository.AdministratorRepository;
import com.bsep.marketingacency.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorService {
    @Autowired
    private AdministratorRepository administratorRepository;

    public Administrator getAdministratorByUserId(Long userId) {
        return administratorRepository.findByUserId(userId);
    }

    public Administrator updateAdministrator(Administrator updatedAdministrator) {
        Administrator existingAdministrator = administratorRepository.findById(updatedAdministrator.getId())
                .orElse(null);
        if (existingAdministrator != null) {
            existingAdministrator.setFirstName(updatedAdministrator.getFirstName());
            existingAdministrator.setLastName(updatedAdministrator.getLastName());
            existingAdministrator.setAddress(updatedAdministrator.getAddress());
            existingAdministrator.setCity(updatedAdministrator.getCity());
            existingAdministrator.setCountry(updatedAdministrator.getCountry());
            existingAdministrator.setPhoneNumber(updatedAdministrator.getPhoneNumber());
            existingAdministrator.setUserId(updatedAdministrator.getUserId());

            return administratorRepository.save(existingAdministrator);
        }
        else {
            return null;
        }
    }
}
