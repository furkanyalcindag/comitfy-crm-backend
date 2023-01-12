package com.comitfy.fair.app.repository;

import com.comitfy.fair.app.entity.Fair;
import com.comitfy.fair.app.entity.FairParticipant;
import com.comitfy.fair.util.common.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FairParticipantRepository extends BaseRepository<FairParticipant> {

    List<FairParticipant> findAllByFairOrderByIdDesc(Fair fair);
}
