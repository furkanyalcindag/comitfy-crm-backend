package com.comitfy.healtie.app.repository;

import com.comitfy.healtie.app.entity.AcademicInfo;
import com.comitfy.healtie.app.entity.Doctor;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.util.common.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcademicInfoRepository extends BaseRepository<AcademicInfo> {

    Page<AcademicInfo> findAllByDoctor(Pageable pageable, Doctor doctor);




}
