package com.imps.cms.model.converter;

import com.imps.cms.model.Bid;
import com.imps.cms.model.dto.BidDto;

public class BidConverter {
    public static BidDto convertToDto(Bid bid){
        BidDto bidDto = new BidDto();
        bidDto.setId(bid.getId());
        bidDto.setProposalId(bid.getProposal().getId());
        bidDto.setUserId(bid.getUser().getId());
        bidDto.setBidStatus(bid.getBidStatus());
        return bidDto;
    }
}
