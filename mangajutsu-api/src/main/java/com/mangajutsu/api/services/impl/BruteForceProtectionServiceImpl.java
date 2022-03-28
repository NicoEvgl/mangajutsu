package com.mangajutsu.api.services.impl;

import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;

import com.mangajutsu.api.dao.entities.UserEntity;
import com.mangajutsu.api.dao.repositories.UserRepository;
import com.mangajutsu.api.models.FailedLogin;
import com.mangajutsu.api.services.BruteForceProtectionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("bruteForceProtectionService")
public class BruteForceProtectionServiceImpl implements BruteForceProtectionService {

    @Autowired
    UserRepository userRepository;

    @Value("${mangajutsu.security.failedlogin.count}")
    private int maxFailedLogins;
    @Value("${mangajutsu.brute.force.cache.max}")
    private int cacheMaxLimit;

    private final ConcurrentHashMap<String, FailedLogin> cache;

    public BruteForceProtectionServiceImpl() {
        this.cache = new ConcurrentHashMap<>(cacheMaxLimit);
    }

    @Override
    public void registerLoginFailure(String username) {
        UserEntity user = getUser(username);
        if (user != null && !user.isLoginDisabled()) {
            int failedCounter = user.getFailedLoginAttempts();
            if (maxFailedLogins < failedCounter + 1) {
                user.setLoginDisabled(true); // disabling the account
            } else {
                // let's update the counter
                user.setFailedLoginAttempts(failedCounter + 1);
            }
            userRepository.save(user);
        }
    }

    @Override
    public void resetBruteForceCounter(String username) {
        UserEntity user = getUser(username);
        if (user != null) {
            user.setFailedLoginAttempts(0);
            user.setLoginDisabled(false);
            userRepository.save(user);
        }
    }

    @Override
    public boolean isBruteForceAttack(String username) {
        UserEntity user = getUser(username);
        if (user != null) {
            return user.getFailedLoginAttempts() >= maxFailedLogins ? true : false;
        }
        return false;
    }

    protected FailedLogin getFailedLogin(final String username) {
        FailedLogin failedLogin = cache.get(username.toLowerCase());

        if (failedLogin == null) {
            // setup the initial data
            failedLogin = new FailedLogin(0, LocalDateTime.now());
            cache.put(username.toLowerCase(), failedLogin);
            if (cache.size() > cacheMaxLimit) {
                // remove the oldest entry
                cache.remove(cache.keySet().iterator().next());
            }
        }
        return failedLogin;
    }

    private UserEntity getUser(final String username) {
        return userRepository.findByUsername(username);
    }

    public int getMaxFailedLogins() {
        return maxFailedLogins;
    }

    public void setMaxFailedLogins(int maxFailedLogins) {
        this.maxFailedLogins = maxFailedLogins;
    }
}
