package com.comitfy.healtie.app.service;

import com.comitfy.healtie.app.dto.AcademicInfoDTO;
import com.comitfy.healtie.app.dto.requestDTO.AcademicInfoRequestDTO;
import com.comitfy.healtie.app.entity.AcademicInfo;
import com.comitfy.healtie.app.entity.Doctor;
import com.comitfy.healtie.app.mapper.AcademicInfoMapper;
import com.comitfy.healtie.app.repository.AcademicInfoRepository;
import com.comitfy.healtie.app.repository.DoctorRepository;
import com.comitfy.healtie.app.specification.AcademicInfoSpecification;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.userModule.repository.UserRepository;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.Optional;
import java.util.UUID;

@Service
public class AcademicInfoService extends BaseService<AcademicInfoDTO, AcademicInfoRequestDTO, AcademicInfo, AcademicInfoRepository, AcademicInfoMapper, AcademicInfoSpecification> {

    @Autowired
    AcademicInfoRepository academicInfoRepository;

    @Autowired
    AcademicInfoMapper academicInfoMapper;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AcademicInfoSpecification academicInfoSpecification;

    @Override
    public AcademicInfoRepository getRepository() {
        return academicInfoRepository;
    }

    @Override
    public AcademicInfoMapper getMapper() {
        return academicInfoMapper;
    }

    @Override
    public AcademicInfoSpecification getSpecification() {
        return academicInfoSpecification;
    }

    public PageDTO<AcademicInfoDTO> getAcademicInfoByDoctor(UUID id, int page, int size) {
        Optional<Doctor> doctor = doctorRepository.findByUuid(id);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("graduateYear")));
        if (doctor.isPresent()) {
            return getMapper().pageEntityToPageDTO(academicInfoRepository.findAllByDoctor(pageable, doctor.get()));
        } else {
            return null;
        }
    }


    public AcademicInfoRequestDTO saveAcademicInfoByDoctor(User user, AcademicInfoRequestDTO dto) {
        Optional<Doctor> doctor = doctorRepository.findByUser(user);
        if (doctor.isPresent()) {
            AcademicInfo academicInfo = getMapper().requestDTOToEntity(dto);
            academicInfo.setDoctor(doctor.get());
            academicInfoRepository.save(academicInfo);
            return dto;
        } else {
            return null;
        }
    }

    public AcademicInfoRequestDTO updateAcademicInfo(UUID id, AcademicInfoRequestDTO dto, User user) {
        Optional<AcademicInfo> academicInfo = academicInfoRepository.findByUuid(id);
        if (academicInfo.isPresent()) {
            AcademicInfo academicInfo1 = academicInfoMapper.requestDTOToExistEntity(academicInfo.get(), dto);
            academicInfoRepository.save(academicInfo1);
            return dto;
        } else {
            return null;
        }
    }
    public PageDTO<AcademicInfoDTO> getAcademicInfoByUser(User user, int page, int size) {
        Optional<Doctor> doctor = doctorRepository.findByUser(user);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("graduateYear")));
        if (user!= null) {
            return getMapper().pageEntityToPageDTO(academicInfoRepository.findAllByDoctor(pageable, doctor.get()));
        } else {
            return null;
        }
    }


}

