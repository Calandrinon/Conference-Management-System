package com.imps.cms.model;

import org.springframework.data.rest.core.config.Projection;

@Projection(
        name = "bidDto"
        , types = { Bid.class }
)
public interface BidDto {
    Long getId();
    UserDto getUser();
    BidStatus getBidStatus();
}
