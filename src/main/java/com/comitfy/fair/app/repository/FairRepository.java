package com.comitfy.fair.app.repository;

import com.comitfy.fair.app.entity.Fair;
import com.comitfy.fair.util.common.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FairRepository extends BaseRepository<Fair> {

    Fair findFirstByIsActiveOrderByIdDesc(boolean isActive);

}
