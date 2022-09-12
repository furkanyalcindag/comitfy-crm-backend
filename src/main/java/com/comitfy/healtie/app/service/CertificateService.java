package com.comitfy.healtie.app.service;

import com.comitfy.healtie.app.dto.AcademicInfoDTO;
import com.comitfy.healtie.app.dto.CertificateDTO;
import com.comitfy.healtie.app.dto.requestDTO.CertificateRequestDTO;
import com.comitfy.healtie.app.entity.Certificate;
import com.comitfy.healtie.app.entity.Doctor;
import com.comitfy.healtie.app.mapper.CertificateMapper;
import com.comitfy.healtie.app.repository.CertificateRepository;
import com.comitfy.healtie.app.repository.DoctorRepository;
import com.comitfy.healtie.app.specification.CertificateSpecification;
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
public class CertificateService extends BaseService<CertificateDTO, CertificateRequestDTO, Certificate, CertificateRepository, CertificateMapper, CertificateSpecification> {

    @Autowired
    CertificateRepository certificateRepository;

    @Autowired
    CertificateMapper certificateMapper;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    CertificateSpecification certificateSpecification;

    @Override
    public CertificateRepository getRepository() {
        return certificateRepository;
    }

    @Override
    public CertificateMapper getMapper() {
        return certificateMapper;
    }

    @Override
    public CertificateSpecification getSpecification() {
        return certificateSpecification;
    }

    public PageDTO<CertificateDTO> getCertificateByDoctor(UUID id, int page, int size) {
        Optional<Doctor> doctor = doctorRepository.findByUuid(id);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("takenDate")));
        if (doctor.isPresent()) {
            return getMapper().pageEntityToPageDTO(certificateRepository.findAllByDoctor(pageable, doctor.get()));
        } else {
            return null;
        }
    }

    public CertificateRequestDTO saveCertificateByDoctor(User user, CertificateRequestDTO dto) {
        Optional<Doctor> doctor = doctorRepository.findByUser(user);
        if (doctor.isPresent()) {
            Certificate certificate = getMapper().requestDTOToEntity(dto);
            certificate.setDoctor(doctor.get());
            certificateRepository.save(certificate);
            return dto;
        } else {
            return null;
        }
    }

    public CertificateRequestDTO updateCertificate(UUID id, CertificateRequestDTO dto, User user) {
        Optional<Certificate> certificate = certificateRepository.findByUuid(id);
        if (certificate.isPresent()) {
            Certificate certificate1 = certificateMapper.requestDTOToExistEntity(certificate.get(), dto);
            certificate1.setCertificateNo(dto.getCertificateNo());
            certificate1.setTakenDate(dto.getTakenDate());
            certificate1.setTakenFrom(dto.getTakenFrom());
            certificate1.setName(dto.getName());
            certificateRepository.save(certificate1);

            return dto;
        } else {
            return null;
        }
    }
    public PageDTO<CertificateDTO> getCertificateByUser(User user, int page, int size) {
        Optional<Doctor> doctor = doctorRepository.findByUser(user);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("takenDate")));
        if (user!= null) {
            return getMapper().pageEntityToPageDTO(certificateRepository.findAllByDoctor(pageable, doctor.get()));
        } else {
            return null;
        }
    }

}
