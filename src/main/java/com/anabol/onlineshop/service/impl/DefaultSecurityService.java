package com.anabol.onlineshop.service.impl;

import com.anabol.onlineshop.entity.User;
import com.anabol.onlineshop.entity.UserRole;
import com.anabol.onlineshop.service.SecurityService;
import com.anabol.onlineshop.service.UserService;
import com.anabol.onlineshop.web.auth.Session;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;

public class DefaultSecurityService implements SecurityService {
    private static final int SESSION_LENGTH_HOURS = 12;
    private static final String ENCRYPTION_ALGORITHM = "SHA-1";

    private List<Session> sessions = Collections.synchronizedList(new ArrayList<>());
    private UserService userService;

    @Override
    public Session login(String login, String password) {
        User user = userService.getByName(login);
        if (user != null) {
            byte[] salt = org.apache.commons.codec.binary.Base64.decodeBase64(user.getSalt());
            byte[] hash = getHash(password, salt);
            byte[] dbHash = org.apache.commons.codec.binary.Base64.decodeBase64(user.getPassword());

            if (MessageDigest.isEqual(dbHash, hash)) {  // Authentication
                Session session = new Session();
                String token = UUID.randomUUID().toString();
                session.setToken(token);
                session.setUser(user);
                session.setUserRole(UserRole.getByName(user.getRole()));
                session.setExpireDate(LocalDateTime.now().plusHours(SESSION_LENGTH_HOURS));
                sessions.add(session);
                return session;
            }
        }
        return null;
    }

    @Override
    public boolean register(String login, String password) {
        User user = userService.getByName(login);
        if (user == null) {
            User newUser = new User();
            newUser.setName(login);
            byte[] salt = getNextSalt();
            newUser.setSalt(org.apache.commons.codec.binary.Base64.encodeBase64String(salt));
            byte[] hash = getHash(password, salt);
            newUser.setPassword(org.apache.commons.codec.binary.Base64.encodeBase64String(hash));
            newUser.setRole(UserRole.USER.getName());
            userService.insert(newUser);
            return true;
        }
        return false;
    }

    @Override
    public Session findByToken(String token) {
        Iterator<Session> iterator = sessions.iterator();
        while (iterator.hasNext()) {
            Session session = iterator.next();
            if (session.getToken().equals(token)) {
                if (session.getExpireDate().isBefore(LocalDateTime.now())) {
                    iterator.remove();
                    return null;
                }
                return session;
            }
        }
        return null;
    }

    @Override
    public void removeByToken(String token) {
        Session session = findByToken(token);
        if (session != null) {
            sessions.remove(session);
        }
    }

    byte[] getNextSalt() {
        try {
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            return salt;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Can't encrypt password", e);
        }
    }

    byte[] getHash(String password, byte[] salt) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(ENCRYPTION_ALGORITHM);
            messageDigest.update(salt);
            return messageDigest.digest(password.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Can't encrypt password", e);
        }
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserService getUserService() {
        return userService;
    }
}
