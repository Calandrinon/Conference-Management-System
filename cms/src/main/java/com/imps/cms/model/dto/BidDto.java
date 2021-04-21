package com.imps.cms.model.dto;

import com.imps.cms.model.BidStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BidDto implements Serializable {
    private Long id;
    private Long proposalId;
    private Long userId;
    private BidStatus bidStatus;
}
