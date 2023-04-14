package com.comitfy.crm.app.repository;

import com.comitfy.crm.app.entity.Fair;
import com.comitfy.crm.util.common.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FairRepository extends BaseRepository<Fair> {

    Fair findFirstByIsActiveOrderByIdDesc(boolean isActive);

}
