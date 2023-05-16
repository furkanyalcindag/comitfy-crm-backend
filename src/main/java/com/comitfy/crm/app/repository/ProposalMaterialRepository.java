package com.comitfy.crm.app.repository;

import com.comitfy.crm.app.entity.ProposalMaterial;
import com.comitfy.crm.util.common.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProposalMaterialRepository extends BaseRepository<ProposalMaterial> {

    List<ProposalMaterial> findAllByVersionAndProposalId(Integer version, Long proposalId);
}
