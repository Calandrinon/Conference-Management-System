package com.imps.cms.controller;

import com.imps.cms.repository.ConferenceRepository;
import com.imps.cms.repository.DeadlineRepository;
import com.imps.cms.repository.UserRepository;
import org.springframework.stereotype.Controller;

@Controller
public class AdminController {
    private final ConferenceRepository conferenceRepository;
    private final UserRepository userRepository;
    private final DeadlineRepository deadlineRepository;


}
