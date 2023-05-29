package com.comitfy.crm.app.repository;

import com.comitfy.crm.app.entity.ProposalProduct;
import com.comitfy.crm.util.common.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProposalProductRepository extends BaseRepository<ProposalProduct> {

    // List<ProposalMaterial> findAllByVersionAndProposalId(Integer version, Long proposalId);
    List<ProposalProduct> findAllByProposalIdAndVersion(Long propsalId, Integer version);

}
