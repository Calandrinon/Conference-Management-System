package com.imps.cms.controller;

import com.imps.cms.model.User;
import com.imps.cms.model.UserType;
import com.imps.cms.repository.UserRepository;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class UserController {
    private final UserRepository userRepository;

    public String sha256hex(String input) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] raw_hash = messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));

        BigInteger hash_number = new BigInteger(1, raw_hash);
        StringBuilder hex_hash = new StringBuilder(hash_number.toString());

        while (hex_hash.length() < 32) {
            hex_hash.insert(0, '0');
        }

        return hex_hash.toString();
    }

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean loginUser(String email, String password) {
        User user = this.userRepository.findByEmail(email).get(0);

        try {
            String hashed_password = sha256hex(user.getSalt() + password);
        }
        catch (NoSuchAlgorithmException e) {
            return false;
        }

        return user.getPassword().equals(password);
    }

    public boolean registerUser(String fullName, UserType userType, String salt, String email, String password) {
        List<User> users = this.userRepository.findByEmail(email);
        if ((long) users.size() != 0) {
            return false;
        }

        User user = new User(fullName, userType, salt, email, password);
        this.userRepository.save(user);

        return true;
    }

}
