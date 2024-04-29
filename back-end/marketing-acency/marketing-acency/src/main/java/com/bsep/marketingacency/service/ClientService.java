package com.bsep.marketingacency.service;

import com.bsep.marketingacency.repository.ClientRepository;
import com.bsep.marketingacency.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
}
