package com.imps.cms.repository;

import com.imps.cms.model.Paper;
import com.imps.cms.model.PaperInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(excerptProjection = PaperInterface.class)
public interface PaperRepository extends JpaRepository<Paper, Long> {
}
