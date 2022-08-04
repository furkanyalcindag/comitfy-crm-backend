package com.comitfy.healtie.app.repository;

import com.comitfy.healtie.app.entity.DoctorProfileClick;
import com.comitfy.healtie.util.common.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

@Repository
public interface DoctorProfileClickRepository extends BaseRepository<DoctorProfileClick> {

    @Query("SELECT COUNT(click) FROM DoctorProfileClick click" +
            " WHERE click.doctorUuid=?1 AND click.creationDate between ?2 and ?3")
    long getCountOfDoctorProfileCLickByDate(UUID doctorUUID,Date startDate, Date endDate);


}
