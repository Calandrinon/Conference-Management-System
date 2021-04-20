package com.imps.cms.model;

import org.springframework.data.rest.core.config.Projection;

@Projection(
        name = "bidDto"
        , types = { Bid.class }
)
public interface BidInterface {
    Long getId();
    UserDtoInterface getUser();
    BidStatus getBidStatus();
}
