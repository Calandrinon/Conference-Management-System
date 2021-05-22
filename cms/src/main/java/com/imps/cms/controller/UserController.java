package com.imps.cms.controller;

import com.imps.cms.model.Conference;
import com.imps.cms.model.User;
import com.imps.cms.model.UserRole;
import com.imps.cms.model.UserType;
import com.imps.cms.model.converter.UserConverter;
import com.imps.cms.model.dto.LoginDto;
import com.imps.cms.model.dto.UserDto;
import com.imps.cms.repository.UserRepository;
import com.imps.cms.service.ConferenceService;
import com.imps.cms.service.MailService;
import com.imps.cms.service.UserRoleService;
import com.imps.cms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<UserDto> loginUser(@RequestBody LoginDto loginDto) {

        User user = this.userRepository.findByEmail(loginDto.getEmail()).get(0);
        if(user.getPassword().equals(loginDto.getPassword())){
            return new ResponseEntity<>(UserConverter.convertToDto(user), HttpStatus.OK);
        }
        else {
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
        User user = User.builder()
                .fullName(userDto.getFullName())
                .salt(userDto.getSalt())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();


        user = this.userRepository.save(user);
        for(Conference conference: conferenceService.findAll()){
            userRoleService.setEmptyUserRole(conference, user);
        }

        mailService.SendEmail("hello there", "get verified here", user.getEmail());
        return ResponseEntity.created(new URI("api/registerUser/" + user.getId())).body(user);
    }
}
