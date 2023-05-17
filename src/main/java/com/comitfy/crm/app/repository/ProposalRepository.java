package com.comitfy.crm.app.repository;

import com.comitfy.crm.app.entity.Proposal;
import com.comitfy.crm.util.common.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProposalRepository extends BaseRepository<Proposal> {


    @Query("SELECT p.proposalStatus, COUNT(p.proposalStatus) FROM Proposal AS p where p.isDeleted=false GROUP BY p.proposalStatus")
    List<Object[]> countTotalProposalStatus();


}
