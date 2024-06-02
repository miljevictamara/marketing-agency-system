package com.bsep.marketingacency.service;

import com.bsep.marketingacency.model.Client;
import com.bsep.marketingacency.model.ClientActivationToken;
import com.bsep.marketingacency.model.LoginToken;
import com.bsep.marketingacency.model.User;
import com.bsep.marketingacency.repository.LoginTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class LoginTokenService {
    @Autowired
    private LoginTokenRepository loginTokenRepository;

    @Autowired
    private ClientService clientService;
    public LoginToken save(LoginToken token){
        return loginTokenRepository.save(token);
    }

    public User findUser(UUID tokenId){
        LoginToken token = loginTokenRepository.findById(tokenId).orElseGet(null);
        if(isExpired(token)){
            return  null;
        }
        User user = token.getUser();
        return user;
    }

    public boolean isExpired(LoginToken token){
        Date currentTime = new Date();
        Date expirationTime = new Date(token.getCreationDate().getTime() + token.getDuration() * 60 * 1000);
        return currentTime.after(expirationTime);
    }

    public Boolean checkIfUsed(UUID tokenId){
        LoginToken token = loginTokenRepository.findById(tokenId).orElseGet(null);
        if(!token.getIsUsed()){
            token.setIsUsed(true);
            save(token);
            return false;
        }
        return  true;
    }

    public void delete(Long userId) {
        loginTokenRepository.deleteAllByUserId(userId);
    }

}
