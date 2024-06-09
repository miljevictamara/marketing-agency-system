package com.bsep.marketingacency.service;

import com.bsep.marketingacency.model.Client;
import com.bsep.marketingacency.model.ClientActivationToken;
import com.bsep.marketingacency.model.LoginToken;
import com.bsep.marketingacency.model.User;
import com.bsep.marketingacency.util.HashUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ClientService clientService;

    @Autowired
    private  UserService userService;

    @Autowired
    private LoginTokenService loginTokenService;

    @Autowired
    private Environment env;

    @Value("${hmac.secret}")
    private String hmacSecret;

    private final static Logger logger = LogManager.getLogger(EmailService.class);

    @Async
    public void sendRegistrationApprovalAsync(Client client, ClientActivationToken token) throws MailException, InterruptedException {
        logger.info("Async method is being executed in a different thread. Thread id: {}", Thread.currentThread().getId());
        Thread.sleep(10000);
        logger.info("Preparing to send email for client with ID {}", HashUtil.hash(String.valueOf(client.getId())));

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(client.getUser().getMail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Account activation");

        UUID tokenId = token.getId();

        String activationLink = "https://localhost:4200/activation/" + tokenId;
        String hmac = generateHmac(activationLink, hmacSecret);
        //String activationLinkWithHmac = activationLink + "?hmac=" + hmac;
        String activationLinkWithHmac = activationLink + "/" + hmac;

        String clientName = client.getFirstName() != null ? client.getFirstName() : client.getCompanyName();
        String message = "Welcome " + clientName + ",\n\nThank you for choosing Marketing Agency System." +
                "To activate your account, please click the following link:\n\n " + activationLinkWithHmac +
                "\n\nBest regards,\nThe MarketingSupport Team";

        mail.setText(message);

        javaMailSender.send(mail);
        logger.info("Activation email sent successfully to client {}", HashUtil.hash(client.getUser().getMail()));
    }


//    @Async
//    public void sendRegistrationRejectionAsync(Client client, String reason) throws MailException, InterruptedException {
//        System.out.println("Async metoda se izvrsava u drugom Threadu u odnosu na prihvaceni zahtev. Thread id: " + Thread.currentThread().getId());
//        Thread.sleep(10000);
//        System.out.println("Slanje emaila...");
//
//        SimpleMailMessage mail = new SimpleMailMessage();
//        mail.setTo(client.getUser().getMail());
//        mail.setFrom(env.getProperty("spring.mail.username"));
//        mail.setSubject("Rejected registration");
//
//        String clientName = client.getFirstName() != null ? client.getFirstName() : client.getCompanyName();
//        String message = "Welcome " + clientName + ",\n\nThank you for choosing Marketing Agency System." +
//                "Your registration request has been rejected for the following reason: \n\n" +
//                "\""+ reason +"\""+
//                "\n\nBest regards,\nThe MarketingSupport Team";
//
//
//        mail.setText(message);
//
//        javaMailSender.send(mail);
//
//        System.out.println("Email sent!");
//        //javaMailSender.send(mail);
//
//        //System.out.println("Email sent!");
//    }

    @Async
    public void sendLogMessageToAdmins(String line) throws MailException {
        System.out.println("Sending log message to admins...");

        List<User> adminUsers = userService.findAllByRolesName("ROLE_ADMIN");

        for (User adminUser : adminUsers) {
            try {
                SimpleMailMessage mail = new SimpleMailMessage();
                mail.setTo(adminUser.getMail());
                mail.setFrom("noreply@example.com");
                mail.setSubject("Log Message");
                mail.setText(line);

                javaMailSender.send(mail);
                System.out.println("Email sent to admin: " + adminUser.getMail());
            } catch (Exception e) {
                System.err.println("Error sending email to admin: " + adminUser.getMail());
                e.printStackTrace();
            }
        }
    }

    @Async
    public void sendRegistrationRejectionAsync(Client client, String reason) throws MailException, InterruptedException {
        logger.info("Async method is being executed in a different thread. Thread id: {}", Thread.currentThread().getId());
        Thread.sleep(10000);
        logger.info("Preparing to send email for client with ID {}", HashUtil.hash(String.valueOf(client.getId())));

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(client.getUser().getMail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Rejected registration");

        String clientName = client.getFirstName() != null ? client.getFirstName() : client.getCompanyName();
        String message = "Welcome " + clientName + ",\n\nThank you for choosing Marketing Agency System." +
                "Your registration request has been rejected for the following reason: \n\n" +
                "\"" + reason + "\"" +
                "\n\nBest regards,\nThe MarketingSupport Team";


        mail.setText(message);

        try {
            javaMailSender.send(mail);
            logger.info("Rejection email sent successfully to client {}", HashUtil.hash(client.getUser().getMail()));
        } catch (MailException e) {
            logger.error("Error sending rejection email to client {}: {}", HashUtil.hash(client.getUser().getMail()), e.getMessage());
            throw e;
        }
    }



    public String generateHmac(String data, String secretKey) {
        try {
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");

            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");

            hmacSha256.init(secretKeySpec);
            byte[] hashBytes = hmacSha256.doFinal(data.getBytes());

            // Konvertujte byte niz u heksadecimalni format
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }


//    public Boolean verifyHmac(String data, String hmac) {
//        try {
//            String recalculatedHmac = generateHmac(data, hmacSecret);
//
//            return hmac.equals(recalculatedHmac);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    public Boolean verifyHmac(String data, String hmac) {
        try {
            String recalculatedHmac = generateHmac(data, hmacSecret);
            return hmac.equals(recalculatedHmac);
        } catch (Exception e) {
            logger.error("Error verifying HMAC: {}", e.getMessage());
            return false;
        }
    }


//    @Async
//    public void sendLoginToken(String mail) throws MailException, InterruptedException {
//        System.out.println("Async metoda se izvrsava u drugom Threadu u odnosu na prihvaceni zahtev. Thread id: " + Thread.currentThread().getId());
//        Thread.sleep(10000);
//        System.out.println("Slanje emaila...");
//
//        User user = userService.findByMail(mail);
//        Client client = clientService.findByUserId(user.getId());
//
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(client.getUser().getMail());
//        message.setFrom(env.getProperty("spring.mail.username"));
//        message.setSubject("Passwordless Login");
//
//        LoginToken loginToken = new LoginToken();
//        loginToken.setUser(user);
//        loginToken.setCreationDate(new Date());
//        loginToken.setDuration(10);
//        loginToken.setIsUsed(false);
//        loginTokenService.save(loginToken);
//
//        String hashedToken = HashUtil.hash(String.valueOf(loginToken.getId()));
//        logger.info("Generated login token for user with email {}. Hashed token: {}", mail, hashedToken);
//
//        String loginLink = "https://localhost:4200/passwordless-login-link/" + loginToken.getId();
//        //String hmac = generateHmac(loginLink, hmacSecret);
//        //String loginLinkWithHmac = loginLink + "?hmac=" + hmac;
//
//
//
//        String clientName = client.getFirstName() != null ? client.getFirstName() : client.getCompanyName();
//        String text = "Hello " + clientName + ",\n\nThank you for choosing Marketing Agency System." +
//                "To login without password, please click the following link:\n\n " + loginLink +
//                "\n\nBest regards,\nThe MarketingSupport Team";
//
//
//
//        message.setText(text);
//
//        javaMailSender.send(message);
//
//        System.out.println("Email sent!");
//
//    }

    @Async
    public void sendLoginToken(String mail) throws MailException, InterruptedException {
        //logger.info("Async metoda se izvrsava u drugom Threadu u odnosu na prihvaceni zahtev. Thread id: {}", Thread.currentThread().getId());

        Thread.sleep(10000);
        logger.info("Preparing to send passwordless login email...");

        try {
            User user = userService.findByMail(mail);
            Client client = clientService.findByUserId(user.getId());

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(client.getUser().getMail());
            message.setFrom(env.getProperty("spring.mail.username"));
            message.setSubject("Passwordless Login");

            LoginToken loginToken = new LoginToken();
            loginToken.setUser(user);
            loginToken.setCreationDate(new Date());
            loginToken.setDuration(10);
            loginToken.setIsUsed(false);
            loginTokenService.save(loginToken);

            String hashedToken = HashUtil.hash(String.valueOf(loginToken.getId()));
            logger.info("Generated passwordless login token for user with email {}. Token: {}", mail, hashedToken);

            String loginLink = "https://localhost:4200/passwordless-login-link/" + loginToken.getId();
            //String hmac = generateHmac(loginLink, hmacSecret);
            //String loginLinkWithHmac = loginLink + "?hmac=" + hmac;

            String clientName = client.getFirstName() != null ? client.getFirstName() : client.getCompanyName();
            String text = "Hello " + clientName + ",\n\nThank you for choosing Marketing Agency System." +
                    "To login without password, please click the following link:\n\n " + loginLink +
                    "\n\nBest regards,\nThe MarketingSupport Team";

            message.setText(text);

            javaMailSender.send(message);
            logger.info("Email for passwordless login sent to {}.", client.getUser().getMail());

        } catch (MailException ex) {
            logger.error("Failed to send passwordless login email to {} due to a MailException.", mail, ex);
            throw ex;
        } catch (Exception ex) {
            logger.error("An unexpected error occurred while sending passwordless login email to {}.", mail, ex);
            throw ex;
        }
    }



}
