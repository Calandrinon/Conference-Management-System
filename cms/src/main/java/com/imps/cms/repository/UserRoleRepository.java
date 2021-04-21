package com.imps.cms.repository;

import com.imps.cms.model.UserRole;
import com.imps.cms.model.UserRoleDtoInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = UserRoleDtoInterface.class)
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
