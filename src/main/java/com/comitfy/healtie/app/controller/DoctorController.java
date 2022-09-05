package com.comitfy.healtie.app.controller;

import com.comitfy.healtie.app.dto.DoctorDTO;
import com.comitfy.healtie.app.dto.requestDTO.*;
import com.comitfy.healtie.app.entity.Doctor;
import com.comitfy.healtie.app.mapper.DoctorMapper;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.app.repository.DoctorRepository;
import com.comitfy.healtie.app.service.DoctorProfileClickService;
import com.comitfy.healtie.app.service.DoctorService;
import com.comitfy.healtie.app.specification.DoctorSpecification;
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
@RequestMapping("doctor")
public class DoctorController extends BaseCrudController<DoctorDTO, DoctorRequestDTO, Doctor, DoctorRepository, DoctorMapper, DoctorSpecification, DoctorService> {

    @Autowired
    DoctorMapper doctorMapper;

    @Autowired
    DoctorService doctorService;

    @Autowired
    HelperService helperService;

    @Autowired
    DoctorProfileClickService doctorProfileClickService;


    @Override
    protected DoctorService getService() {
        return doctorService;
    }

    @Override
    protected DoctorMapper getMapper() {
        return doctorMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getById(@RequestHeader(value = "accept-language", required = true) String acceptLanguage, @PathVariable UUID id) {
        DoctorDTO optionalT = getService().findByUUID(id);
        if (optionalT == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            User user = helperService.getUserFromSession();
            DoctorProfileClickRequestDTO doctorProfileClickRequestDTO = new DoctorProfileClickRequestDTO();
            doctorProfileClickRequestDTO.setDoctorUUID(optionalT.getUuid());
            doctorProfileClickRequestDTO.setUserUUID(user != null ? user.getUuid() : null);
            doctorProfileClickService.save(doctorProfileClickRequestDTO);
            return new ResponseEntity<>(optionalT, HttpStatus.OK);
        }
    }

    @PutMapping("/title-update/")
    public ResponseEntity<String> updateDoctorTitle(@RequestBody DoctorTitleRequestDTO dto) {
        User user = helperService.getUserFromSession();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);

        } else {
            doctorService.updateTitle(dto, user);
            return new ResponseEntity<>("The object was updated", HttpStatus.OK);
        }
    }

    @PutMapping("/diploma-no-update/")
    public ResponseEntity<String> updateDoctorDiplomaNo(@RequestBody DoctorDiplomaNoRequestDTO dto) {
        User user = helperService.getUserFromSession();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);

        } else {
            doctorService.updateDiplomaNo(dto, user);
            return new ResponseEntity<>("The object was updated", HttpStatus.OK);
        }
    }

    @PutMapping("/address-update/")
    public ResponseEntity<String> updateDoctorAddress(@RequestBody DoctorAddressRequestDTO dto) {
        User user = helperService.getUserFromSession();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);

        } else {
            doctorService.updateAddress(dto, user);
            return new ResponseEntity<>("The object was updated", HttpStatus.OK);
        }
    }

    @PutMapping("/phone-update/")
    public ResponseEntity<String> updateDoctorPhone(@RequestBody DoctorPhoneRequestDTO dto) {
        User user = helperService.getUserFromSession();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);

        } else {
            doctorService.updatePhone(dto, user);
            return new ResponseEntity<>("The object was updated", HttpStatus.OK);
        }
    }

    @PutMapping("/clinic-name-update/")
    public ResponseEntity<String> updateDoctorClinicName(@RequestBody DoctorClinicNameRequestDTO dto) {
        User user = helperService.getUserFromSession();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);

        } else {
            doctorService.updateClinicName(dto, user);
            return new ResponseEntity<>("The object was updated", HttpStatus.OK);
        }
    }

    @PutMapping("/about-update/")
    public ResponseEntity<String> updateDoctorAbout(@RequestBody DoctorAboutRequestDTO dto) {
        User user = helperService.getUserFromSession();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);

        } else {
            doctorService.updateAbout(dto, user);
            return new ResponseEntity<>("The object was updated", HttpStatus.OK);
        }
    }

    @PutMapping("/branch-update/")
    public ResponseEntity<String> updateDoctorBranch(@RequestBody DoctorBranchRequestDTO dto) {
        User user = helperService.getUserFromSession();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);

        } else {
            doctorService.updateBranch(dto, user);
            return new ResponseEntity<>("The object was updated", HttpStatus.OK);
        }
    }
    @PutMapping("/profile-image-update/")
    public ResponseEntity<String> updateDoctorProfileImage(@RequestBody DoctorProfileImageRequestDTO dto) {
        User user = helperService.getUserFromSession();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);

        } else {
            doctorService.updateProfileImage(dto, user);
            return new ResponseEntity<>("The object was updated", HttpStatus.OK);
        }
    }

}
