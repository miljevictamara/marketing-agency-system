package com.bsep.marketingacency.service;

import com.bsep.marketingacency.controller.UserController;
import com.bsep.marketingacency.dto.ClientDto;
import com.bsep.marketingacency.dto.UserDto;
import com.bsep.marketingacency.enumerations.ClientType;
import com.bsep.marketingacency.model.Client;
import com.bsep.marketingacency.model.Role;
import com.bsep.marketingacency.model.User;
import com.bsep.marketingacency.repository.ClientRepository;
import com.bsep.marketingacency.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private TOTPManager totpManager;

    private final static Logger logger = LogManager.getLogger(UserService.class);
    public User findByMail(String email) {
        User user = userRepository.findByMail(email);
        if (user == null) {
            logger.warn("User with email {} does not exist.", email);
        }
        return user;
    }


    public User findUserById(Long id) { return userRepository.findUserById(id); }

    public User save(UserDto userDto) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setMail(userDto.getMail());
        Role role = roleService.findByName("ROLE_CLIENT");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        user.setIsActivated(false);
        user.setIsBlocked(false);
        user.setMfa(userDto.getMfa());
        user.setSecret(userDto.getSecret());

        return this.userRepository.save(user);
    }

    public User saveEmployeeUser(UserDto userDto) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setMail(userDto.getMail());
        Role role = roleService.findByName("ROLE_EMPLOYEE");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        user.setIsActivated(true);
        user.setIsBlocked(false);

        return this.userRepository.save(user);
    }

    public User saveAdminUser(UserDto userDto) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setMail(userDto.getMail());
        Role role = roleService.findByName("ROLE_ADMIN");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        user.setIsActivated(true);
        user.setIsBlocked(false);
        return this.userRepository.save(user);
    }

    public void delete(User user){
        userRepository.delete(user);
    }

    public User updateIsActivated(Long id) {
        User existingUser = userRepository.findById(id).orElseGet(null);

        existingUser.setIsActivated(true);

        return userRepository.save(existingUser);
    }

    public List<Client> getAllIndividuals() {
        List<Client> individualClients = clientRepository.findByType(ClientType.INDIVIDUAL);
        return individualClients;
    }

    public List<Client> getAllLegalEntities() {
        List<Client> legalEntityClients = clientRepository.findByType(ClientType.LEGAL_ENTITY);
        return legalEntityClients;
    }

    public Boolean verify(String mail, String code) {
        User user = userRepository.findByMail(mail);

        if (user == null) {
            return false;
        }

        if (!totpManager.verifyCode(code, user.getSecret())) {
            return false;
        }

        return true;
    }




}
