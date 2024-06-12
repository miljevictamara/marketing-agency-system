package com.bsep.marketingacency.service;

import com.bsep.marketingacency.controller.UserController;
import com.bsep.marketingacency.dto.ClientDto;
import com.bsep.marketingacency.dto.UserDto;
import com.bsep.marketingacency.enumerations.RegistrationRequestStatus;
import com.bsep.marketingacency.model.Client;
import com.bsep.marketingacency.model.Package;
import com.bsep.marketingacency.model.Role;
import com.bsep.marketingacency.model.User;
import com.bsep.marketingacency.model.*;
import com.bsep.marketingacency.repository.AdvertisementRepository;
import com.bsep.marketingacency.repository.ClientRepository;
import com.bsep.marketingacency.repository.UserRepository;
import com.bsep.marketingacency.util.HashUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RejectionNoteService rejectionNoteService;

    @Autowired
    private ClientActivationTokenService clientActivationTokenService;

    @Autowired
    private PackageService packageService;

    private final static Logger logger = LogManager.getLogger(ClientService.class);

//    public Client save(ClientDto clientDto) {
//        String mail = clientDto.getUser();
//        User user = userService.findByMail(mail);
//
//        String packageName = clientDto.getClientPackage();
//        Package pack = packageService.findByName(packageName);
//
//        Client client = new Client();
//        client.setUser(user);
//        client.setType(clientDto.getType());
//        client.setFirstName(clientDto.getFirstName());
//        client.setLastName(clientDto.getLastName());
//        client.setCompanyName(clientDto.getCompanyName());
//        client.setPib(clientDto.getPib());
//        client.setClientPackage(pack);
//        client.setPhoneNumber(clientDto.getPhoneNumber());
//        client.setAddress(clientDto.getAddress());
//        client.setCity(clientDto.getCity());
//        client.setCountry(clientDto.getCountry());
//        client.setIsApproved(RegistrationRequestStatus.PENDING);
//
//        return this.clientRepository.save(client);
//    }
public User findUser(UUID tokenId){
    ClientActivationToken token = clientActivationTokenService.findById(tokenId);
    Optional<Client> client = clientRepository.findById(token.getUser().getId());
    if(isExpired(token)){
        //userService.delete(token.getUser());
        //clientService.delete(client);
        return  null;
    }
    User user = token.getUser();
    return user;
}

    public boolean isExpired(ClientActivationToken token){
        Date currentTime = new Date();
        Date expirationTime = new Date(token.getCreationDate().getTime() + token.getDuration() * 60 * 1000);
        return currentTime.after(expirationTime);
    }
    public Client save(ClientDto clientDto) {
        String mail = clientDto.getUser();
        User user = userService.findByMail(mail);
        if (user == null) {
            throw new IllegalArgumentException("User not found.");
        }

        String packageName = clientDto.getClientPackage();
        Package pack = packageService.findByName(packageName);
        if (pack == null) {
            logger.warn("Package with name {} not found.", packageName);
            throw new IllegalArgumentException("Package not found.");
        }

        Client client = new Client();
        client.setUser(user);
        client.setType(clientDto.getType());
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setCompanyName(clientDto.getCompanyName());
        client.setPib(clientDto.getPib());
        client.setClientPackage(pack);
        client.setPhoneNumber(clientDto.getPhoneNumber());
        client.setAddress(clientDto.getAddress());
        client.setCity(clientDto.getCity());
        client.setCountry(clientDto.getCountry());
        client.setIsApproved(RegistrationRequestStatus.PENDING);

        Client savedClient = this.clientRepository.save(client);

        return savedClient;
    }


    public void delete(Client client){
        this.clientRepository.delete(client);
    }

//    public ClientActivationToken approveRegistrationRequest(Long id){
//        Client client = clientRepository.getById(id);
//        client.setIsApproved(RegistrationRequestStatus.APPROVED);
//        this.clientRepository.save(client);
//        ClientActivationToken token = new ClientActivationToken();
//        token.setDuration(10);
//        token.setUser(client.getUser());
//        token.setCreationDate(new Date());
//        token.setIsUsed(false);
//        clientActivationTokenService.save(token);
//
//        return token;
//    }

    public ClientActivationToken approveRegistrationRequest(Long id) {
        Client client = clientRepository.getById(id);
        client.setIsApproved(RegistrationRequestStatus.APPROVED);
        this.clientRepository.save(client);
        logger.info("Client with ID {} has been approved.", HashUtil.hash(id.toString()));

        ClientActivationToken token = new ClientActivationToken();
        token.setDuration(10);
        token.setUser(client.getUser());
        token.setCreationDate(new Date());
        token.setIsUsed(false);
        clientActivationTokenService.save(token);

        logger.info("Activation token generated for client with ID {}. Token ID: {}", HashUtil.hash(id.toString()), HashUtil.hash(String.valueOf(token.getId())));

        return token;
    }


    public void rejectRegistrationRequest(Long id, String reason) {
        Client client = clientRepository.getById(id);
        client.setIsApproved(RegistrationRequestStatus.REJECTED);
        RejectionNote rejectionNote = new RejectionNote();
        rejectionNote.setEmail(client.getUser().getMail());
        rejectionNote.setRejectionDate(new Date());
        rejectionNote.setReason(reason);
        this.rejectionNoteService.save(rejectionNote);

        logger.info("Registration request rejected for client {}.", client.getUser().getMail());
    }


    public Client findById(Long id) {
        return this.clientRepository.findById(id).orElse(null);
    }


//    public Client findByUserId(Long id){
//        return this.clientRepository.findByUserId(id);
//    }

    public Client findByUserId(Long userId) {
        Client client = clientRepository.findByUserId(userId);
        if (client == null) {
            logger.warn("Client with user ID {} does not exist.", HashUtil.hash(String.valueOf(userId)));
            return null;
        }
        return client;
    }

//    public Boolean checkIfClientCanLoginWithoutPassword(String mail){
//        User user = userService.findByMail(mail);
//        Client client = clientRepository.findByUserId(user.getId());
//        if (user == null || client == null || !user.getIsActivated()) {
//            return false;
//        }
//
//        Package clientPackage = client.getClientPackage();
//
//        if (clientPackage != null) {
//            String packageName = clientPackage.getName();
//
//            if ("GOLD".equals(packageName) || "STANDARD".equals(packageName)) {
//
//                return true;
//            }
//        }
//        return false;
//    }

    public Boolean checkIfClientCanLoginWithoutPassword(String mail) {
        logger.info("Checking if client with email {} can log in without password.", mail);

        try {
            User user = userService.findByMail(mail);
            if (user == null) {
                return false;
            }

            Client client = clientRepository.findByUserId(user.getId());
            if (client == null) {
                String hashedUserId = HashUtil.hashUserId(String.valueOf(user.getId()));
                logger.warn("Client with user ID {} does not exist.", hashedUserId);
                return false;
            }

            if (!user.getIsActivated()) {
                logger.warn("User with email {} is not activated.", mail);
                return false;
            }

            Package clientPackage = client.getClientPackage();
            if (clientPackage != null) {
                String packageName = clientPackage.getName();

                if ("GOLD".equals(packageName) || "STANDARD".equals(packageName)) {
                    logger.info("Client with email {} can log in without password.", mail);
                    return true;
                }
            } else {
                logger.warn("Client with email {} does not have a package assigned.", mail);
            }

            logger.info("Client with email {} cannot log in without password.", mail);
            return false;

        } catch (Exception ex) {
            logger.error("Error while checking if client with email {} can log in without password.", mail, ex);
            return false;
        }
    }


    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

//    public Client getClientByUserId(Long userId) { return clientRepository.findByUserId(userId); }

    public Client getClientByUserId(Long userId) {
        Client client = clientRepository.findByUserId(userId);
        if (client == null) {
            logger.warn("Client with user ID {} not found.", HashUtil.hash(userId.toString()));
        }
        return client;
    }


    public Client updateClient(Client updatedClient) {
        Client existingClient = clientRepository.findById(updatedClient.getId())
                .orElse(null);
        if(existingClient != null) {
            existingClient.setFirstName(updatedClient.getFirstName());
            existingClient.setLastName(updatedClient.getLastName());
            existingClient.setAddress(updatedClient.getAddress());
            existingClient.setCity(updatedClient.getCity());
            existingClient.setCountry(updatedClient.getCountry());
            existingClient.setPhoneNumber(updatedClient.getPhoneNumber());

            return clientRepository.save(existingClient);
        } else {
            return null;
        }
    }

//    public void deleteClient(Long userId) {
//        Client client = clientRepository.findByUserId(userId);
//
//        List<Advertisement> clientAdvertisements = advertisementRepository.findByClient(client);
//        if (clientAdvertisements != null){
//            advertisementRepository.deleteAll(clientAdvertisements);
//        }
//
//        if (client != null) {
//            clientRepository.delete(client);
//        }
//    }

    public void deleteClient(Long userId) {
        try {
            Client client = clientRepository.findByUserId(userId);
            if (client == null) {
                logger.warn("Client with userId {} not found", HashUtil.hash(userId.toString()));
                return;
            }

            try {
                List<Advertisement> clientAdvertisements = advertisementRepository.findByClient(client);
                if (clientAdvertisements != null && !clientAdvertisements.isEmpty()) {
                    advertisementRepository.deleteAll(clientAdvertisements);
                    logger.info("Deleted {} advertisements for client with userId {}", clientAdvertisements.size(), HashUtil.hash(userId.toString()));
                }
            } catch (Exception e) {
                logger.error("Error while deleting advertisements for client with userId {}", HashUtil.hash(userId.toString()), e);
            }

            try {
                clientRepository.delete(client);
                logger.info("Deleted client with userId {}", HashUtil.hash(userId.toString()));
            } catch (Exception e) {
                logger.error("Error while deleting client with userId {}", HashUtil.hash(userId.toString()), e);
            }

        } catch (Exception e) {
            logger.error("Error while retrieving client with userId {}", HashUtil.hash(userId.toString()), e);
        }
    }
}
