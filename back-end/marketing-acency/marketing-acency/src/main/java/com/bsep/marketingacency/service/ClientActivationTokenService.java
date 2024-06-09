package com.bsep.marketingacency.service;

import com.bsep.marketingacency.controller.ClientActivationTokenController;
import com.bsep.marketingacency.model.Client;
import com.bsep.marketingacency.model.ClientActivationToken;
import com.bsep.marketingacency.model.User;
import com.bsep.marketingacency.repository.ClientActivationTokenRepository;
import com.bsep.marketingacency.util.HashUtil;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;


@Service
public class ClientActivationTokenService {
    @Autowired
    private ClientActivationTokenRepository clientActivationTokenRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private  ClientService clientService;
    private Logger logger =  LoggerFactory.getLogger(ClientActivationTokenService.class);

    public ClientActivationToken save(ClientActivationToken token){
        return clientActivationTokenRepository.save(token);
    }

//    public User findUser(UUID tokenId){
//        ClientActivationToken token = clientActivationTokenRepository.findById(tokenId).orElseGet(null);
//        Client client = clientService.findById(token.getUser().getId());
//        if(isExpired(token)){
//            //userService.delete(token.getUser());
//            //clientService.delete(client);
//            return  null;
//        }
//        User user = token.getUser();
//        return user;
//    }

    public User findUser(UUID tokenId){
        try {
            ClientActivationToken token = clientActivationTokenRepository.findById(tokenId).orElse(null);
            if (token == null) {
                logger.error("Client activation token with ID {} not found.", HashUtil.hash(tokenId.toString()));
                return null;
            }

            if (isExpired(token)) {
                logger.warn("Client activation token with ID {} has expired.", HashUtil.hash(tokenId.toString()));
                return null;
            }

            Client client = clientService.findById(token.getUser().getId());
            if (client == null) {
                logger.error("Client not found for token with ID {}.", HashUtil.hash(tokenId.toString()));
                return null;
            }

            User user = token.getUser();
            return user;
        } catch (Exception e) {
            logger.error("Error finding user with token ID {}: {}", HashUtil.hash(tokenId.toString()), e.getMessage());
            return null;
        }
    }


//    public Boolean checkIfUsed(UUID tokenId){
//        ClientActivationToken token = clientActivationTokenRepository.findById(tokenId).orElseGet(null);
//        if(!token.getIsUsed()){
//            token.setIsUsed(true);
//            save(token);
//            return false;
//        }
//        return  true;
//    }

    public Boolean checkIfUsed(UUID tokenId){
        try {
            ClientActivationToken token = clientActivationTokenRepository.findById(tokenId).orElse(null);
            if (token == null) {
                logger.error("Client activation token with ID {} not found.", HashUtil.hash(tokenId.toString()));
                return null;
            }

            if (!token.getIsUsed()) {
                token.setIsUsed(true);
                save(token);
                return false;
            }

            return true;
        } catch (Exception e) {
            logger.error("Error checking if token with ID {} is used: {}", HashUtil.hash(tokenId.toString()), e.getMessage());
            return null;
        }
    }


    public boolean isExpired(ClientActivationToken token){
        Date currentTime = new Date();
        Date expirationTime = new Date(token.getCreationDate().getTime() + token.getDuration() * 60 * 1000);
        return currentTime.after(expirationTime);
    }
}
