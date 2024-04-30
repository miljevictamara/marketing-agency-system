package com.bsep.marketingacency.service;

import com.bsep.marketingacency.model.Client;
import com.bsep.marketingacency.model.ClientActivationToken;
import org.apache.commons.codec.digest.HmacUtils;
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
import java.util.Base64;
import java.util.UUID;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ClientService clientService;

    @Autowired
    private Environment env;

    @Value("${hmac.secret}")
    private String hmacSecret;

    @Async
    public void sendRegistrationApprovalAsync(Client client, ClientActivationToken token) throws MailException, InterruptedException {
        System.out.println("Async metoda se izvrsava u drugom Threadu u odnosu na prihvaceni zahtev. Thread id: " + Thread.currentThread().getId());
        Thread.sleep(10000);
        System.out.println("Slanje emaila...");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(client.getUser().getMail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Account activation");

        UUID tokenId = token.getId();

        String activationLink = "https://localhost:4200/activation/" + tokenId;
        String hmac = generateHmac(activationLink, hmacSecret);
        String activationLinkWithHmac = activationLink + "?hmac=" + hmac;



        String clientName = client.getFirstName() != null ? client.getFirstName() : client.getCompanyName();
        String message = "Welcome " + clientName + ",\n\nThank you for choosing Marketing Agency System." +
                "To activate your account, please click the following link:\n\n " + activationLinkWithHmac +
                "\n\nBest regards,\nThe MarketingSupport Team";



        mail.setText(message );

        javaMailSender.send(mail);

        System.out.println("Email sent!");

    }

    @Async
    public void sendRegistrationRejectionAsync(Client client, String reason) throws MailException, InterruptedException {
        System.out.println("Async metoda se izvrsava u drugom Threadu u odnosu na prihvaceni zahtev. Thread id: " + Thread.currentThread().getId());
        Thread.sleep(10000);
        System.out.println("Slanje emaila...");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(client.getUser().getMail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Account activation");

        String clientName = client.getFirstName() != null ? client.getFirstName() : client.getCompanyName();
        String message = "Welcome " + clientName + ",\n\nThank you for choosing Marketing Agency System." +
                "Your registration request has been rejected for the following reason: \n\n" +
                "\""+ reason +"\""+
                "\n\nBest regards,\nThe MarketingSupport Team";


        mail.setText(message);

        javaMailSender.send(mail);

        System.out.println("Email sent!");
    }

//    private String generateHmac(String message) {
//        try {
//            Mac mac = Mac.getInstance("HmacSHA256");
//            SecretKeySpec secretKey = new SecretKeySpec(hmacSecret.getBytes(), "HmacSHA256");
//            mac.init(secretKey);
//            byte[] hmacBytes = mac.doFinal(message.getBytes());
//            return Base64.getEncoder().encodeToString(hmacBytes);
//        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public String generateHmac(String data, String secretKey) {
        try {
            Mac hmacSha256 = Mac.getInstance("HmacSHA256");

            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");

            hmacSha256.init(secretKeySpec);
            String hash = Base64.getEncoder().encodeToString(hmacSha256.doFinal(data.getBytes()));
            return hash;

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean verifyHmac(String data, String hmac) {
        try {
            String recalculatedHmac = generateHmac(data, hmacSecret);

            return hmac.equals(recalculatedHmac);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
