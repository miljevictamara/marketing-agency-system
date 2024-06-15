package com.bsep.marketingacency.service;

import com.bsep.marketingacency.controller.UserController;
import com.bsep.marketingacency.dto.ClientDto;
import com.bsep.marketingacency.dto.UserDto;
import com.bsep.marketingacency.enumerations.ClientType;
import com.bsep.marketingacency.keystores.KeyStoreReader;
import com.bsep.marketingacency.model.Client;
import com.bsep.marketingacency.model.ResetPasswordToken;
import com.bsep.marketingacency.model.Role;
import com.bsep.marketingacency.model.User;
import com.bsep.marketingacency.repository.ClientRepository;
import com.bsep.marketingacency.repository.ResetPasswordTokenRepository;
import com.bsep.marketingacency.repository.UserRepository;
import com.bsep.marketingacency.util.HashUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResetPasswordTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private TOTPManager totpManager;

    @Autowired
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    private final static Logger logger = LogManager.getLogger(UserService.class);
    public User findByMail(String email) {
        User user = userRepository.findByMail(email);
        if (user == null) {
            logger.warn("User {} does not exist.", email);
        }
        return user;
    }


    public User findUserById(Long id) {
        User user = userRepository.findUserById(id);
        if (user != null) {
            //logger.info("User found for ID: {}.", HashUtil.hash(id.toString()));
        } else {
            logger.warn("User not found by ID: {}.", HashUtil.hash(id.toString()));
        }
        return user;
    }


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


//    public User updateIsActivated(Long id) {
//        User existingUser = userRepository.findById(id).orElseGet(null);
//
//        existingUser.setIsActivated(true);
//
//        return userRepository.save(existingUser);
//    }

    public User updateIsActivated(Long id) {
        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser == null) {
            logger.warn("User with ID {} not found.", HashUtil.hash(id.toString()));
            return null;
        }

        existingUser.setIsActivated(true);
        User updatedUser = userRepository.save(existingUser);

        logger.info("User with ID {} successfully activated.", HashUtil.hash(id.toString()));

        return updatedUser;
    }


    public List<Client> getAllIndividuals() throws IllegalBlockSizeException, BadPaddingException {
        List<Client> clients = clientRepository.findByType(ClientType.INDIVIDUAL);

        KeyStoreReader keyStoreReader = new KeyStoreReader();

        for (Client client : clients) {
            String alias = client.getUser().getMail();
            SecretKey secretKey = keyStoreReader.readSecretKey(
                    alias + ".jks",
                    alias,
                    "marketing-agency".toCharArray(),
                    "marketing-agency".toCharArray()
            );

            if (secretKey != null) {
                if (client.getFirstName() != null) {
                    client.setFirstName(client.getFirstName(secretKey));
                }
                if (client.getLastName() != null){
                    client.setLastName(client.getLastName(secretKey));
                }

                client.setPhoneNumber(client.getPhoneNumber(secretKey));
                client.setAddress(client.getAddress(secretKey));
                if (client.getPib() != null) {
                    client.setPib(client.getPib(), secretKey);
                }
            } else {
                System.out.println("Secret key is null for client: " + client.getUser().getMail());
            }
        }

        return clients;

    }


    public List<Client> getAllLegalEntities() throws IllegalBlockSizeException, BadPaddingException {
        List<Client> clients = clientRepository.findByType(ClientType.LEGAL_ENTITY);

        KeyStoreReader keyStoreReader = new KeyStoreReader();

        for (Client client : clients) {
            String alias = client.getUser().getMail();
            SecretKey secretKey = keyStoreReader.readSecretKey(
                    alias + ".jks",
                    alias,
                    "marketing-agency".toCharArray(),
                    "marketing-agency".toCharArray()
            );

            if (secretKey != null) {
                if (client.getFirstName() != null) {
                    client.setFirstName(client.getFirstName(secretKey));
                }
                if (client.getLastName() != null){
                    client.setLastName(client.getLastName(secretKey));
                }

                client.setPhoneNumber(client.getPhoneNumber(secretKey));
                client.setAddress(client.getAddress(secretKey));
                if (client.getPib() != null) {
                    client.setPib(client.getPib(), secretKey);
                }
            } else {
                System.out.println("Secret key is null for client: " + client.getUser().getMail());
            }
        }

        return clients;
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

//    public void deleteUser(Long userId) {
//        userRepository.deleteById(userId);
//    }

    public void deleteUser(Long userId) {
        try {
            resetPasswordTokenRepository.deleteByUserId(userId);
            userRepository.deleteById(userId);
            //logger.info("Deleted user {}.", HashUtil.hash(userId.toString()));
        } catch (EmptyResultDataAccessException e) {
            logger.warn("User {} not found.", HashUtil.hash(userId.toString()));
            throw e;
        } catch (Exception e) {
            logger.error("Error while deleting user {}.", HashUtil.hash(userId.toString()));
            throw new RuntimeException("Failed to delete user", e);
        }
    }
    
    public List<User> findAllByRolesName(String roleName) {
        return userRepository.findAllByRolesName(roleName);
    }


    public User updateIsBlocked(Long id) {
        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser == null) {
            logger.warn("User {} not found.", HashUtil.hash(id.toString()));
            return null;
        }

        existingUser.setIsBlocked(true);
        User updatedUser = userRepository.save(existingUser);

        logger.info("User {} successfully blocked.", HashUtil.hash(id.toString()));

        return updatedUser;
    }












//    public void sendPasswordResetLink(String email) {
//        User user = userRepository.findByMail(email);
//        if (user == null) {
//            throw new RuntimeException("User not found");
//        }
//
//        String token = UUID.randomUUID().toString();
//        ResetPasswordToken resetToken = new ResetPasswordToken();
//        resetToken.setToken(token);
//        resetToken.setUserId(user.getId());
//        resetToken.setExpiryDate(LocalDateTime.now().plusHours(24)); // 24-hour expiry
//        tokenRepository.save(resetToken);
//
//        String resetLink = "http://localhost:4200/reset-password?token=" + token;
//        sendEmail(user.getMail(), resetLink);
//    }

    public void sendPasswordResetLink(String email) {
        try {
            User user = userRepository.findByMail(email);
            if (user == null) {
                logger.warn("User {},who request a password reset, not found.", email);
                throw new RuntimeException("User {} not found");
            }


            String token = UUID.randomUUID().toString();
            ResetPasswordToken resetToken = new ResetPasswordToken();
            resetToken.setToken(token);
            resetToken.setUserId(user.getId());
            resetToken.setExpiryDate(LocalDateTime.now().plusHours(24)); // 24-hour expiry
            tokenRepository.save(resetToken);

            logger.info("Reset token {} generated for {}.", HashUtil.hash(token), email);

            String resetLink = "http://localhost:4200/reset-password?token=" + token;
            sendEmail(user.getMail(), resetLink);


        } catch (Exception ex) {
            logger.error("Error while generating reset token for {}.", email);
            throw new RuntimeException("Error while sending password reset link", ex);
        }
    }


//    private void sendEmail(String email, String resetLink) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(email);
//        message.setSubject("Password Reset Request");
//        message.setText("To reset your password, click the link below:\n" + resetLink);
//        mailSender.send(message);
//    }

    private void sendEmail(String email, String resetLink) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Password Reset Request");
            message.setText("To reset your password, click the link below:\n" + resetLink);
            mailSender.send(message);

            logger.info("Password reset email sent to {}.", email);
        } catch (Exception ex) {
            logger.error("Error while sending password reset email to {}.", email);
            throw new RuntimeException("Error while sending email", ex);
        }
    }


//    public void changePassword(String token, String newPassword) {
//        ResetPasswordToken resetToken = tokenRepository.findByToken(token);
//        if (resetToken == null || resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
//            throw new RuntimeException("Invalid or expired token");
//        }
//
//        User user = userRepository.findById(resetToken.getUserId())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        // Hash the new password before saving
//        String encodedPassword = passwordEncoder.encode(newPassword);
//        user.setPassword(encodedPassword);
//        userRepository.save(user);
//
//        tokenRepository.delete(resetToken);
//    }

    public void changePassword(String token, String newPassword) {
        try {
            logger.info("Trying to check reset token {} validity.", HashUtil.hash(token));
            ResetPasswordToken resetToken = tokenRepository.findByToken(token);
            if (resetToken == null || resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
                logger.warn("Reset token {} is not valid.", HashUtil.hash(token));
                throw new RuntimeException("Invalid or expired token");
            }

            logger.info("Reset token {} is valid.", HashUtil.hash(token));

            User user = userRepository.findById(resetToken.getUserId())
                    .orElseThrow(() -> {
                        logger.error("User {} not found for resetting password.", HashUtil.hash(resetToken.getUserId().toString()));
                        return new RuntimeException("User not found");
                    });

            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
            userRepository.save(user);

            tokenRepository.delete(resetToken);

            logger.info("Password changed successfully for user {}.", HashUtil.hash(resetToken.getUserId().toString()));
        } catch (Exception ex) {
            logger.error("Error while changing password for token {}.", HashUtil.hash(token));
            throw new RuntimeException("Error while changing password", ex);
        }
    }



}
