package com.imps.cms.service;

import com.imps.cms.model.Bid;
import com.imps.cms.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidService {
    @Autowired
    private BidRepository bidRepository;

    public List<Bid> getAll(){
        return bidRepository.findAll();
    }

    public void addBid(Bid bid){
        bidRepository.save(bid);
    }
}
