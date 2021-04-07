package com.imps.cms.repository;

import com.imps.cms.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface SectionRepository extends JpaRepository<Section, Long> {
}
