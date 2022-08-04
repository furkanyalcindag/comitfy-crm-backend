package com.comitfy.healtie.femaleModules.repository;

import com.comitfy.healtie.femaleModules.entity.OvulationCycle;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.util.common.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OvulationCycleRepository extends BaseRepository<OvulationCycle> {


    boolean existsByUserAndActivated(User user, boolean isActive);


    OvulationCycle findByActivatedAndUser(boolean isActive, User user);

}
