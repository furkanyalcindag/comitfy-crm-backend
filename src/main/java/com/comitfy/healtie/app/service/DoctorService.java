package com.comitfy.healtie.app.service;

import com.comitfy.healtie.app.dto.AcademicInfoDTO;
import com.comitfy.healtie.app.dto.DoctorDTO;
import com.comitfy.healtie.app.dto.requestDTO.*;
import com.comitfy.healtie.app.entity.Doctor;
import com.comitfy.healtie.app.mapper.DoctorMapper;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.app.repository.ArticleRepository;
import com.comitfy.healtie.app.repository.DoctorRepository;
import com.comitfy.healtie.app.specification.DoctorSpecification;
import com.comitfy.healtie.userModule.dto.UserDTO;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DoctorService extends BaseService<DoctorDTO, DoctorRequestDTO, Doctor, DoctorRepository, DoctorMapper, DoctorSpecification> {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    DoctorMapper doctorMapper;

    @Autowired
    DoctorSpecification doctorSpecification;

    @Autowired
    ArticleRepository articleRepository;

    @Override
    public DoctorRepository getRepository() {
        return doctorRepository;
    }

    @Override
    public DoctorMapper getMapper() {
        return doctorMapper;
    }

    @Override
    public DoctorSpecification getSpecification() {
        return doctorSpecification;
    }

    public DoctorTitleRequestDTO updateTitle(DoctorTitleRequestDTO dto, User user) {
        Optional<Doctor> doctor = doctorRepository.findByUser(user);
        if (doctor.isPresent()) {
            Doctor doctor1 = doctorMapper.requestDTOToExistEntityForTitle(doctor.get(), dto);
            doctor1.setTitle(dto.getTitle());
            doctorRepository.save(doctor1);
            return dto;

        } else {

            return null;
        }
    }

    public DoctorDiplomaNoRequestDTO updateDiplomaNo(DoctorDiplomaNoRequestDTO dto, User user) {
        Optional<Doctor> doctor = doctorRepository.findByUser(user);
        if (doctor.isPresent()) {
            Doctor doctor1 = doctorMapper.requestDTOToExistEntityForDiplomaNo(doctor.get(), dto);
            doctor1.setDiplomaNo(dto.getDiplomaNo());
            doctorRepository.save(doctor1);
            return dto;

        } else {

            return null;
        }
    }

    public DoctorAddressRequestDTO updateAddress(DoctorAddressRequestDTO dto, User user) {
        Optional<Doctor> doctor = doctorRepository.findByUser(user);
        if (doctor.isPresent()) {
            Doctor doctor1 = doctorMapper.requestDTOToExistEntityForAddress(doctor.get(), dto);
          //  doctor1.setAddress(dto.getAddress());
            doctorRepository.save(doctor1);
            return dto;

        } else {

            return null;
        }
    }

    public DoctorPhoneRequestDTO updatePhone(DoctorPhoneRequestDTO dto, User user) {
        Optional<Doctor> doctor = doctorRepository.findByUser(user);
        if (doctor.isPresent()) {
            Doctor doctor1 = doctorMapper.requestDTOToExistEntityForPhone(doctor.get(), dto);
         //  doctor1.setPhone(dto.getPhone());
            doctorRepository.save(doctor1);
            return dto;

        } else {

            return null;
        }
    }

    public DoctorClinicNameRequestDTO updateClinicName(DoctorClinicNameRequestDTO dto, User user) {
        Optional<Doctor> doctor = doctorRepository.findByUser(user);
        if (doctor.isPresent()) {
            Doctor doctor1 = doctorMapper.requestDTOToExistEntityForClinicName(doctor.get(), dto);
            doctor1.setClinicName(dto.getClinicName());
            doctorRepository.save(doctor1);
            return dto;

        } else {

            return null;
        }
    }

    public DoctorAboutRequestDTO updateAbout(DoctorAboutRequestDTO dto, User user) {
        Optional<Doctor> doctor = doctorRepository.findByUser(user);
        if (doctor.isPresent()) {
            Doctor doctor1 = doctorMapper.requestDTOToExistEntityForAbout(doctor.get(), dto);
            doctor1.setAbout(dto.getAbout());
            doctorRepository.save(doctor1);
            return dto;

        } else {

            return null;
        }
    }

    public DoctorBranchRequestDTO updateBranch(DoctorBranchRequestDTO dto, User user) {
        Optional<Doctor> doctor = doctorRepository.findByUser(user);
        if (doctor.isPresent()) {
            Doctor doctor1 = doctorMapper.requestDTOToExistEntityForBranch(doctor.get(), dto);
            doctor1.setBranch(dto.getBranch());
            doctorRepository.save(doctor1);
            return dto;

        } else {

            return null;
        }
    }
    public DoctorProfileImageRequestDTO updateProfileImage(DoctorProfileImageRequestDTO dto, User user) {
        Optional<Doctor> doctor = doctorRepository.findByUser(user);
        if (doctor.isPresent()) {
            Doctor doctor1 = doctorMapper.requestDTOToExistEntityForProfileImage(doctor.get(), dto);
            doctor1.setProfileImage(dto.getProfileImage());
            doctorRepository.save(doctor1);
            return dto;

        } else {

            return null;
        }
    }
    public DoctorDTO getDoctorByUser(User user) {
        Optional<Doctor> doctor = doctorRepository.findByUser(user);
        if (doctor.isPresent()) {
            return getMapper().entityToDTO(doctor.get());
        } else {
            return null;
        }
    }

}

