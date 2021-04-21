package com.imps.cms.service;


import com.imps.cms.model.UserRole;
import com.imps.cms.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;

    public void addUserRole(UserRole userRole){
        userRoleRepository.save(userRole);
    }
}
