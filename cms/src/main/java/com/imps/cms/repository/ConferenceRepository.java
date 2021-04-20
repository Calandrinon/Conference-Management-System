package com.imps.cms.repository;

import com.imps.cms.model.Conference;
import com.imps.cms.model.ConferenceInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(excerptProjection = ConferenceInterface.class)
public interface ConferenceRepository extends JpaRepository<Conference, Long> {
}
