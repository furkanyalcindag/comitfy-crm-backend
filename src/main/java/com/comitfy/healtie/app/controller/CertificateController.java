package com.comitfy.healtie.app.controller;

import com.comitfy.healtie.app.dto.AcademicInfoDTO;
import com.comitfy.healtie.app.dto.CertificateDTO;
import com.comitfy.healtie.app.dto.requestDTO.CertificateRequestDTO;
import com.comitfy.healtie.app.entity.Certificate;
import com.comitfy.healtie.app.entity.Doctor;
import com.comitfy.healtie.app.mapper.CertificateMapper;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.app.repository.CertificateRepository;
import com.comitfy.healtie.app.repository.DoctorRepository;
import com.comitfy.healtie.app.service.CertificateService;
import com.comitfy.healtie.app.service.DoctorService;
import com.comitfy.healtie.app.specification.CertificateSpecification;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseCrudController;
import com.comitfy.healtie.util.common.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("certificate")
public class CertificateController extends BaseCrudController<CertificateDTO, CertificateRequestDTO, Certificate, CertificateRepository, CertificateMapper, CertificateSpecification, CertificateService> {

    @Autowired
    CertificateService certificateService;

    @Autowired
    CertificateMapper certificateMapper;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    DoctorService doctorService;
    @Autowired
    HelperService helperService;

    @Override
    protected CertificateService getService() {
        return certificateService;
    }

    @Override
    protected CertificateMapper getMapper() {
        return certificateMapper;
    }

    @PostMapping("/doctor-api")
    public ResponseEntity<CertificateRequestDTO> saveByDoctor(@RequestHeader(value = "accept-language", required = true) String acceptLanguage,
                                                              @RequestBody CertificateRequestDTO certificateRequestDTO) {
        User user = helperService.getUserFromSession();
        if (user != null) {
         //   certificateRequestDTO.setLanguageEnum(LanguageEnum.valueOf(certificateRequestDTO.getLanguage()));
            return new ResponseEntity<>(certificateService.saveCertificateByDoctor(user, certificateRequestDTO), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("doctor/{doctorId}")
    public ResponseEntity<PageDTO<CertificateDTO>> getByDoctorId(@RequestHeader(value = "accept-language", required = true) String acceptLanguage,
                                                                 @PathVariable UUID doctorId, @RequestParam int pageNumber, @RequestParam int pageSize) {
        Optional<Doctor> optional = doctorService.getRepository().findByUuid(doctorId);
        if (optional == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(certificateService.getCertificateByDoctor(doctorId, pageNumber, pageSize), HttpStatus.OK);
        }
    }

    @PutMapping("/user-api/{certificateId}")
    public ResponseEntity<String> updateCertificate(@PathVariable UUID certificateId, @RequestBody CertificateRequestDTO dto) {
        User user = helperService.getUserFromSession();
        CertificateDTO certificateDTO = certificateService.findByUUID(certificateId);
        if (certificateDTO == null || user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        } else {
            certificateService.updateCertificate(certificateId, dto, user);
            return new ResponseEntity<>("The object was updated.", HttpStatus.OK);
        }
    }
    @GetMapping("doctor/user-api")
    public ResponseEntity<PageDTO<CertificateDTO>> getByUser(@RequestHeader(value = "accept-language", required = true) String acceptLanguage,
                                                                  @RequestParam int pageNumber, @RequestParam int pageSize) {
        User user=helperService.getUserFromSession();

        if (user==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(certificateService.getCertificateByUser(user,pageNumber,pageSize), HttpStatus.OK);
        }
    }
}
