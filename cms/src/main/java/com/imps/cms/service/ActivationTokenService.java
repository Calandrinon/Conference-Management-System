package com.imps.cms.service;

import antlr.Token;
import com.imps.cms.model.ActivationToken;
import com.imps.cms.model.Invitation;
import com.imps.cms.model.User;
import com.imps.cms.repository.ActivationTokenRepository;
import com.imps.cms.repository.ConferenceRepository;
import com.imps.cms.repository.UserRepository;
import com.imps.cms.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class ActivationTokenService {
    @Autowired
    private ActivationTokenRepository activationTokenRepository;
    @Autowired
    private UserRepository userRepository;


    public ActivationToken addActivationToken(ActivationToken activationToken){
        return this.activationTokenRepository.save(activationToken);
    }

    public boolean activateToken(String token, Long userID) {
        User user = this.userRepository.findById(userID).get();
        ActivationToken found_token;
        try {
            found_token = this.activationTokenRepository.findByTokenAndReceiver(token, user).get(0);
        }
        catch (RuntimeException e) {
            return false;
        }

        if (found_token.getStatus() != "NOT_ACTIVATED") {
            return false;
        }
        found_token.setStatus("ACTIVATED");
        user.setActivated(true);
        this.activationTokenRepository.save(found_token);
        this.userRepository.save(user);
        return true;
    }
}
