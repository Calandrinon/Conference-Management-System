package com.imps.cms.repository;

import com.imps.cms.model.User;
import com.imps.cms.model.UserDtoInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(excerptProjection = UserDtoInterface.class)
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmail(String email);
}
