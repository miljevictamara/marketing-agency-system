package com.bsep.marketingacency.service;

import com.bsep.marketingacency.model.Client;
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
    public void sendRegistrationVerificationAsync(Client client) throws MailException, InterruptedException {
        System.out.println("Async metoda se izvrsava u drugom Threadu u odnosu na prihvaceni zahtev. Thread id: " + Thread.currentThread().getId());
        Thread.sleep(10000);
        System.out.println("Slanje emaila...");

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(client.getUser().getMail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Account activation");

        String clientName = client.getFirstName() != null ? client.getFirstName() : client.getCompanyName();
        String message = "Welcome " + clientName + ",\n\nThank you for choosing Marketing Agency System.\n\nBest regards,\nThe MarketingSupport Team";

        String hmac = generateHmac(message);

        mail.setText(message + "\n\nHMAC: " + hmac);

        javaMailSender.send(mail);

        System.out.println("Email sent!");
        javaMailSender.send(mail);

        System.out.println("Email sent!");
    }

    private String generateHmac(String message) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(hmacSecret.getBytes(), "HmacSHA256");
            mac.init(secretKey);
            byte[] hmacBytes = mac.doFinal(message.getBytes());
            return Base64.getEncoder().encodeToString(hmacBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }

}
