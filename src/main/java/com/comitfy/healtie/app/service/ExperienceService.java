package com.comitfy.healtie.app.service;

import com.comitfy.healtie.app.dto.CertificateDTO;
import com.comitfy.healtie.app.dto.ExperienceDTO;
import com.comitfy.healtie.app.dto.requestDTO.ExperienceRequestDTO;
import com.comitfy.healtie.app.entity.Certificate;
import com.comitfy.healtie.app.entity.Doctor;
import com.comitfy.healtie.app.entity.Experience;
import com.comitfy.healtie.app.mapper.ExperienceMapper;
import com.comitfy.healtie.app.repository.DoctorRepository;
import com.comitfy.healtie.app.repository.ExperienceRepository;
import com.comitfy.healtie.app.specification.ExperienceSpecification;
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
public class ExperienceService extends BaseService<ExperienceDTO, ExperienceRequestDTO, Experience, ExperienceRepository, ExperienceMapper, ExperienceSpecification> {

    @Autowired
    ExperienceRepository experienceRepository;

    @Autowired
    ExperienceMapper experienceMapper;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    ExperienceSpecification experienceSpecification;

    @Override
    public ExperienceRepository getRepository() {
        return experienceRepository;
    }

    @Override
    public ExperienceMapper getMapper() {
        return experienceMapper;
    }

    @Override
    public ExperienceSpecification getSpecification() {
        return experienceSpecification;
    }

    public PageDTO<ExperienceDTO> getExperienceByDoctor(UUID id, int page, int size) {
        Optional<Doctor> doctor = doctorRepository.findByUuid(id);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("endDate")));
        if (doctor.isPresent()) {
            return getMapper().pageEntityToPageDTO(experienceRepository.findAllByDoctor(pageable, doctor.get()));
        } else {
            return null;
        }
    }

    public ExperienceRequestDTO saveExperienceByDoctor(User user, ExperienceRequestDTO dto) {
        Optional<Doctor> doctor = doctorRepository.findByUser(user);
        if (doctor.isPresent()) {
            Experience experience = getMapper().requestDTOToEntity(dto);
            experience.setDoctor(doctor.get());
            experienceRepository.save(experience);
            return dto;
        } else {
            return null;
        }

    }

    public ExperienceRequestDTO updateExperience(UUID id, ExperienceRequestDTO dto, User user) {
        Optional<Experience> experience = experienceRepository.findByUuid(id);
        if (experience.isPresent()) {
            Experience experience1 = experienceMapper.requestDTOToExistEntity(experience.get(), dto);
            experience1.setDescription(dto.getDescription());
            experience1.setStartDate(dto.getStartDate());
            experience1.setEndDate(dto.getEndDate());
            experience1.setTitle(dto.getTitle());
            experience1.setWorkedPlace(dto.getWorkedPlace());
            experienceRepository.save(experience1);

            return dto;
        } else {
            return null;
        }
    }

    public PageDTO<ExperienceDTO> getExperienceByUser(User user, int page, int size) {
        Optional<Doctor> doctor = doctorRepository.findByUser(user);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("takenDate")));
        if (user!= null) {
            return getMapper().pageEntityToPageDTO(experienceRepository.findAllByDoctor(pageable, doctor.get()));
        } else {
            return null;
        }
    }
}
