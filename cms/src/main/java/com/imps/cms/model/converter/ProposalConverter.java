package com.imps.cms.model.converter;

import com.imps.cms.model.Proposal;
import com.imps.cms.model.dto.ProposalDto;

public class ProposalConverter {
    public static ProposalDto convertToDto(Proposal proposal){
        ProposalDto proposalDto = new ProposalDto();
        proposalDto.setId(proposal.getId());
        proposalDto.setStatus(proposal.getStatus());
        proposalDto.setPaperId(proposal.getPaper().getID());
        return proposalDto;
    }
}
