package com.imps.cms.repository;

import com.imps.cms.model.Paper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PaperRepository extends JpaRepository<Paper, Long> {
}
