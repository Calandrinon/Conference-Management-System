package com.imps.cms.model.converter;

import com.imps.cms.model.Comment;
import com.imps.cms.model.dto.CommentDto;

public class CommentConverter {
    public static CommentDto convertToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setProposalId(comment.getProposal().getId());
        commentDto.setUserName(comment.getUser().getFullName());
        commentDto.setContent(comment.getContent());
        return commentDto;
    }
}
