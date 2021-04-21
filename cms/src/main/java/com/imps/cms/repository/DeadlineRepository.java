package com.imps.cms.repository;

import com.imps.cms.model.Deadline;
import com.imps.cms.model.DeadlineInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(excerptProjection = DeadlineInterface.class)
public interface DeadlineRepository extends JpaRepository<Deadline, Long> {
}
