package com.bsep.marketingacency.service;

import com.bsep.marketingacency.model.Client;
import com.bsep.marketingacency.model.ClientActivationToken;
import com.bsep.marketingacency.model.User;
import com.bsep.marketingacency.repository.ClientActivationTokenRepository;
import lombok.Setter;
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
    public ClientActivationToken save(ClientActivationToken token){
        return clientActivationTokenRepository.save(token);
    }

    public User findUser(UUID tokenId){
        ClientActivationToken token = clientActivationTokenRepository.findById(tokenId).orElseGet(null);
        Client client = clientService.findById(token.getUser().getId());
        if(isExpired(token)){
            //userService.delete(token.getUser());
            //clientService.delete(client);
            return  null;
        }
        User user = token.getUser();
        return user;
    }

    public Boolean checkIfUsed(UUID tokenId){
        ClientActivationToken token = clientActivationTokenRepository.findById(tokenId).orElseGet(null);
        if(!token.getIsUsed()){
            token.setIsUsed(true);
            save(token);
            return false;
        }
        return  true;
    }

    public boolean isExpired(ClientActivationToken token){
        Date currentTime = new Date();
        Date expirationTime = new Date(token.getCreationDate().getTime() + token.getDuration() * 60 * 1000);
        return currentTime.after(expirationTime);
    }

    public void delete(Long userId) {
        clientActivationTokenRepository.deleteAllByUserId(userId);
    }
}
