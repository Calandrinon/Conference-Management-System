package com.imps.cms.model.converter;

import com.imps.cms.model.Proposal;
import com.imps.cms.model.dto.ProposalDto;

import java.util.stream.Collectors;

public class ProposalConverter {
    public static ProposalDto convertToDto(Proposal proposal){
        ProposalDto proposalDto = new ProposalDto();
        proposalDto.setId(proposal.getId());
        proposalDto.setStatus(proposal.getStatus());
        proposalDto.setPaperId(proposal.getPaper().getId());
        proposalDto.setCommentsAllowed(proposal.getCommentsAllowed());
        if(proposal.getComments() != null)
            proposalDto.setComments(proposal.getComments().stream().map(CommentConverter::convertToDto).collect(Collectors.toList()));
        else proposalDto.setComments(null);
        return proposalDto;
    }
}
