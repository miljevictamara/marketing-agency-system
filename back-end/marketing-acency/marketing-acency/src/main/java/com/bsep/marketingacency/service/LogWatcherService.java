package com.bsep.marketingacency.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.mail.javamail.JavaMailSender;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogWatcherService {

    private static final String LOG_FILE_PATH = "app.log";
    private final SimpMessagingTemplate messagingTemplate;
    private int lastReadIndex = 0;

    @Autowired
    private EmailService emailService;
    private List<String> warningLines = new ArrayList<>();

    @Autowired
    public LogWatcherService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(fixedDelay = 5000) // Check every 5 seconds
    public void watchLogFile() {
        try {
            Path logFilePath = Paths.get(LOG_FILE_PATH);
            List<String> lines = Files.readAllLines(logFilePath);

            // Prođi kroz sve linije od posljednjeg indeksa do kraja
            for (int i = lastReadIndex; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.contains("WARN") || line.contains("ERROR")) {
                    messagingTemplate.convertAndSend("/topic/logs", line);
                    warningLines.add(line);
                }
            }

            lastReadIndex = lines.size();
            if (!warningLines.isEmpty()) {
                String emailMessage = String.join("\n", warningLines);
                emailService.sendLogMessageToAdmins(emailMessage);
                warningLines.clear();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
