package com.imps.cms.controller;

import com.imps.cms.model.dto.UserRoleDto;
import com.imps.cms.service.ActivationTokenService;
import com.imps.cms.service.PermissionService;
import com.imps.cms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private ActivationTokenService activationTokenService;


    @GetMapping(value = "/isVerified/{userID}")
    public ResponseEntity<Boolean> isAccountVerified(@PathVariable Long userID){
        return new ResponseEntity<>(this.permissionService.isAccountActivated(userID), HttpStatus.OK);
    }

    @GetMapping(value = "/getVerified/{verificationToken}/{userID}")
    public ResponseEntity<Boolean> getAccountVerified(@PathVariable String verificationToken, @PathVariable Long userID){
        return new ResponseEntity<>(this.activationTokenService.activateToken(verificationToken, userID), HttpStatus.OK);
    }

    @GetMapping(value = "/isAdmin/{userID}")
    public ResponseEntity<Boolean> isAccountAdmin(@PathVariable Long userID){
        return new ResponseEntity<>(this.permissionService.isAccountAdmin(userID), HttpStatus.OK);
    }

    @GetMapping(value = "/isListener/{conferenceID}/{userID}")
    public ResponseEntity<Boolean> isAccountListener(@PathVariable Long conferenceID, @PathVariable Long userID){
        return new ResponseEntity<>(this.permissionService.isAccountListener(conferenceID, userID), HttpStatus.OK);
    }

    @GetMapping(value = "/isAuthor/{conferenceID}/{userID}")
    public ResponseEntity<Boolean> isAccountAuthor(@PathVariable Long conferenceID, @PathVariable Long userID){
        return new ResponseEntity<>(this.permissionService.isAccountAuthor(conferenceID, userID), HttpStatus.OK);
    }

    @GetMapping(value = "/isChair/{conferenceID}/{userID}")
    public ResponseEntity<Boolean> isAccountChair(@PathVariable Long conferenceID, @PathVariable Long userID){
        return new ResponseEntity<>(this.permissionService.isAccountChair(conferenceID, userID), HttpStatus.OK);
    }

    @GetMapping(value = "/isPCMember/{conferenceID}/{userID}")
    public ResponseEntity<Boolean> isAccountPCMember(@PathVariable Long conferenceID, @PathVariable Long userID){
        return new ResponseEntity<>(this.permissionService.isAccountPCMember(conferenceID, userID), HttpStatus.OK);
    }

}
