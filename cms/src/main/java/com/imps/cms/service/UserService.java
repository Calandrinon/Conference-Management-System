package com.imps.cms.service;

import com.imps.cms.model.User;
import com.imps.cms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("No user with this id"));
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }
}
