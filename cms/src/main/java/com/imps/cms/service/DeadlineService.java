package com.imps.cms.service;

import com.imps.cms.model.Deadline;
import com.imps.cms.repository.DeadlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeadlineService {
    @Autowired
    private DeadlineRepository deadlineRepository;

    public void addDeadline(Deadline deadline) {
        deadlineRepository.save(deadline);
    }
}
