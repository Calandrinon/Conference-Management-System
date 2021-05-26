package com.imps.cms.repository;

import com.imps.cms.model.Conference;
import com.imps.cms.model.Paper;
import com.imps.cms.model.PaperInterface;
import com.imps.cms.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;
import java.util.List;


@RepositoryRestResource(excerptProjection = PaperInterface.class)
public interface PaperRepository extends JpaRepository<Paper, Long> {
    List<Paper> findByConference(Conference conference);

    Paper findBySection(Section section);
}
