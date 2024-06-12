package com.bsep.marketingacency.service;

import com.bsep.marketingacency.TokenRefreshException;
import com.bsep.marketingacency.controller.UserController;
import com.bsep.marketingacency.model.RefreshToken;
import com.bsep.marketingacency.repository.RefreshTokenRepository;
import com.bsep.marketingacency.repository.UserRepository;
import com.bsep.marketingacency.util.HashUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("86400000")
    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    private final static Logger logger = LogManager.getLogger(RefreshTokenService.class);

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

//    @Transactional
//    public int deleteByUserId(Long userId) {
//        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
//    }

    @Transactional
    public int deleteByUserId(Long userId) {
        try {
            int deletedTokens = refreshTokenRepository.deleteByUser(userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId)));
            //logger.info("Deleted refresh tokens for user with userId {}", HashUtil.hash(userId.toString()));
            return deletedTokens;
        } catch (IllegalArgumentException e) {
            logger.warn("User not found with id: {}", HashUtil.hash(userId.toString()));
            throw e;
        } catch (Exception e) {
            logger.error("Error while deleting refresh tokens for user with userId {}", HashUtil.hash(userId.toString()), e);
            throw new RuntimeException("Failed to delete refresh tokens", e);
        }
    }
}
