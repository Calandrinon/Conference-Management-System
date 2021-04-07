package com.imps.cms.repository;

import com.imps.cms.model.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ConferenceRepository extends JpaRepository<Conference, Long> {
}
