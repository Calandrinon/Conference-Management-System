package com.imps.cms.repository;

import com.imps.cms.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
