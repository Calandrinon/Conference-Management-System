package com.imps.cms.controller;

import com.imps.cms.model.User;
import com.imps.cms.model.UserType;
import com.imps.cms.model.dto.LoginDto;
import com.imps.cms.model.dto.UserDto;
import com.imps.cms.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
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

    @PostMapping("/login")
    public ResponseEntity<Boolean> loginUser(@RequestBody LoginDto loginDto) {

        User user = this.userRepository.findByEmail(loginDto.getEmail()).get(0);

        try {
            String hashed_password = sha256hex(user.getSalt() + loginDto.getPassword());
        }
        catch (NoSuchAlgorithmException e) {
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(user.getPassword().equals(loginDto.getPassword()));
    }

    @PostMapping("/registerUser")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserDto userDto) throws URISyntaxException {
        List<User> users = this.userRepository.findByEmail(userDto.getEmail());
        if ((long) users.size() != 0) {
            return ResponseEntity.of(Optional.empty());
        }
        User user = User.builder()
                .fullName(userDto.getFullName())
                .salt(userDto.getSalt())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();


        this.userRepository.save(user);
        return ResponseEntity.created(new URI("api/registerUser/" + user.getId())).body(user);
    }
}
