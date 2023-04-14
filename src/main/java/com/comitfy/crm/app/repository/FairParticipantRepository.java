package com.comitfy.crm.app.repository;

import com.comitfy.crm.app.entity.Fair;
import com.comitfy.crm.app.entity.FairParticipant;
import com.comitfy.crm.util.common.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FairParticipantRepository extends BaseRepository<FairParticipant> {

    List<FairParticipant> findAllByFairOrderByIdDesc(Fair fair);

    List<FairParticipant> findAllByFairAndIsParticipatedOrderByParticipationDateDesc(Fair fair,Boolean isParticipated);
}
