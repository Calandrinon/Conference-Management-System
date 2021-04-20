package com.imps.cms.repository;

import com.imps.cms.model.Invitation;
import com.imps.cms.model.InvitationInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = InvitationInterface.class)
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
}
