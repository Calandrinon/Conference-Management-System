package com.imps.cms.repository;

import com.imps.cms.model.Conference;
import com.imps.cms.model.Section;
import com.imps.cms.model.SectionInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(excerptProjection = SectionInterface.class)
public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findByConference(Conference conference);
}
