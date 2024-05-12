package com.bsep.marketingacency.service;

import com.bsep.marketingacency.dto.ClientDto;
import com.bsep.marketingacency.dto.UserDto;
import com.bsep.marketingacency.enumerations.RegistrationRequestStatus;
import com.bsep.marketingacency.model.Client;
import com.bsep.marketingacency.model.Package;
import com.bsep.marketingacency.model.Role;
import com.bsep.marketingacency.model.User;
import com.bsep.marketingacency.model.*;
import com.bsep.marketingacency.repository.ClientRepository;
import com.bsep.marketingacency.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RejectionNoteService rejectionNoteService;

    @Autowired
    private ClientActivationTokenService clientActivationTokenService;

    public Client save(ClientDto clientDto) {
        Client client = new Client();
        client.setUser(clientDto.getUser());
        client.setType(clientDto.getType());
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setCompanyName(clientDto.getCompanyName());
        client.setPib(clientDto.getPib());
        client.setClientPackage(clientDto.getClientPackage());
        client.setPhoneNumber(clientDto.getPhoneNumber());
        client.setAddress(clientDto.getAddress());
        client.setCity(clientDto.getCity());
        client.setCountry(clientDto.getCountry());
        client.setIsApproved(RegistrationRequestStatus.PENDING);

        return this.clientRepository.save(client);
    }

    public void delete(Client client){
        this.clientRepository.delete(client);
    }

    public ClientActivationToken approveRegistrationRequest(Long id){
        Client client = clientRepository.getById(id);
        client.setIsApproved(RegistrationRequestStatus.APPROVED);
        this.clientRepository.save(client);
        ClientActivationToken token = new ClientActivationToken();
        token.setDuration(10);
        token.setUser(client.getUser());
        token.setCreationDate(new Date());
        token.setIsUsed(false);
        clientActivationTokenService.save(token);

        return token;
    }

    public void rejectRegistrationRequest(Long id, String reason){
        Client client = clientRepository.getById(id);
        client.setIsApproved(RegistrationRequestStatus.REJECTED);
        RejectionNote rejectionNote = new RejectionNote();
        rejectionNote.setEmail(client.getUser().getMail());
        rejectionNote.setRejectionDate(new Date());
        rejectionNote.setReason(reason);
        this.rejectionNoteService.save(rejectionNote);
    }

    public Client findById(Long id){
        return this.clientRepository.findById(id).orElse(null);
    }

    public Client findByUserId(Long id){
        return this.clientRepository.findByUserId(id);
    }

    public Boolean checkIfClientCanLoginWithoutPassword(String mail){
        User user = userService.findByMail(mail);
        Client client = clientRepository.findByUserId(user.getId());
        if (user == null || client == null || !user.getIsActivated()) {
            return false;
        }

        Package clientPackage = client.getClientPackage();

        if (clientPackage != null) {
            String packageName = clientPackage.getName();

            if ("GOLD".equals(packageName) || "STANDARD".equals(packageName)) {

                return true;
            }
        }
        return false;
    }
  
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

}
