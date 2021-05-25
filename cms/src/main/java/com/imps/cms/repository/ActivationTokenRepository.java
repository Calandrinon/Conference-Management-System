package com.imps.cms.repository;

import com.imps.cms.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(excerptProjection = InvitationInterface.class)
public interface ActivationTokenRepository extends JpaRepository<ActivationToken, Long> {
    List<ActivationToken> findByReceiverId(Long userId);

    List<ActivationToken> findByTokenAndReceiver(String token, User receiver);
}
