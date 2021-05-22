package com.imps.cms.repository;

import com.imps.cms.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(excerptProjection = InvitationInterface.class)
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    List<Invitation> findByReceiverId(Long userId);

    List<Invitation> findByConferenceAndReceiverAndUserType(Conference conference, User receiver, UserType chair);
}
