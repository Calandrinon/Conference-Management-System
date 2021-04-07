package com.imps.cms.repository;

import com.imps.cms.model.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
}
