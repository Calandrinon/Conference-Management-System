package com.imps.cms.repository;

import com.imps.cms.model.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
}
