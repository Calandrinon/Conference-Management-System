package com.imps.cms.controller;

import com.imps.cms.model.*;
import com.imps.cms.model.converter.UserConverter;
import com.imps.cms.model.dto.LoginDto;
import com.imps.cms.model.dto.UserDto;
import com.imps.cms.repository.UserRepository;
import com.imps.cms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class UserController {
    private final UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ConferenceService conferenceService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private MailService mailService;
    @Autowired
    private ActivationTokenService activationTokenService;

    public static String sha256hex(String input) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] raw_hash = messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));

        BigInteger hash_number = new BigInteger(1, raw_hash);
        StringBuilder hex_hash = new StringBuilder(hash_number.toString());

        while (hex_hash.length() < 32) {
            hex_hash.insert(0, '0');
        }

        return hex_hash.toString();
    }

    public String generateString(int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        return generatedString;
    }

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/updateAccount/{userEmail}/{newPassword}")
    public ResponseEntity<UserDto> updateAccount(@PathVariable String userEmail, @PathVariable String newPassword) {

        try
        {
            User user = this.userRepository.findByEmail(userEmail).get(0);
            user.setPassword(UserController.sha256hex(user.getSalt() + newPassword));
            this.userService.updateUser(user);
        } catch (Exception e)
        {
            System.out.println(e);
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody LoginDto loginDto) {

        User user = this.userRepository.findByEmail(loginDto.getEmail()).get(0);
        try {
            if(user.getPassword().equals(sha256hex(user.getSalt() + loginDto.getPassword()))){
                return new ResponseEntity<>(UserConverter.convertToDto(user), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
        } catch (NoSuchAlgorithmException e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @GetMapping("/users/all")
    public ResponseEntity<List<UserDto>> getUsers(){
        List<UserDto> userDtoList = this.userService.getAll().stream().map(UserConverter::convertToDto).collect(Collectors.toList());
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @PostMapping("/registerUser")
    public ResponseEntity<User> registerUser(@Valid @RequestBody UserDto userDto) throws URISyntaxException {
        List<User> users = this.userRepository.findByEmail(userDto.getEmail());
        if ((long) users.size() != 0) {
            return ResponseEntity.of(Optional.empty());
        }

        String hashed_password;
        String salt = this.generateString(8);
        try {
            hashed_password = sha256hex(salt + userDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            return ResponseEntity.of(Optional.empty());
        }

        User user = User.builder()
                .fullName(userDto.getFullName())
                .salt(salt)
                .email(userDto.getEmail())
                .password(hashed_password)
                .activated(false)
                .build();


        String token = this.generateString(30);
        ActivationToken activationToken = ActivationToken.builder()
                .token(token)
                .status("NOT_ACTIVATED")
                .receiver(user)
                .build();

        user = this.userRepository.save(user);
        for(Conference conference: conferenceService.findAll()){
            userRoleService.setEmptyUserRole(conference, user);
        }

        mailService.sendEmail("hello there", "Get verified here: http://localhost:8080/api/permissions/" + token + "/" + user.getId(), user.getEmail());
        return ResponseEntity.created(new URI("api/registerUser/" + user.getId())).body(user);
    }
}
