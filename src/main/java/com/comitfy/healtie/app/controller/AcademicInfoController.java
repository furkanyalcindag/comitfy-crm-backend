package com.comitfy.healtie.app.controller;

import com.comitfy.healtie.app.dto.AcademicInfoDTO;
import com.comitfy.healtie.app.dto.requestDTO.AcademicInfoRequestDTO;
import com.comitfy.healtie.app.entity.AcademicInfo;
import com.comitfy.healtie.app.entity.Doctor;
import com.comitfy.healtie.app.mapper.AcademicInfoMapper;
import com.comitfy.healtie.app.repository.AcademicInfoRepository;
import com.comitfy.healtie.app.repository.DoctorRepository;
import com.comitfy.healtie.app.service.AcademicInfoService;
import com.comitfy.healtie.app.service.DoctorService;
import com.comitfy.healtie.app.specification.AcademicInfoSpecification;
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
@RequestMapping("academicInfo")
public class AcademicInfoController extends BaseCrudController<AcademicInfoDTO, AcademicInfoRequestDTO, AcademicInfo, AcademicInfoRepository, AcademicInfoMapper, AcademicInfoSpecification, AcademicInfoService> {

    @Autowired
    AcademicInfoService academicInfoService;

    @Autowired
    AcademicInfoMapper academicInfoMapper;

    @Autowired
    AcademicInfoRepository academicInfoRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    HelperService helperService;

    @Autowired
    DoctorService doctorService;

    @Override
    protected AcademicInfoService getService() {
        return academicInfoService;
    }

    @Override
    protected AcademicInfoMapper getMapper() {
        return academicInfoMapper;
    }

    @PostMapping("/doctor-api")
    public ResponseEntity<AcademicInfoRequestDTO> saveByDoctor(@RequestHeader(value = "accept-language", required = true) String acceptLanguage,
                                                               @RequestBody AcademicInfoRequestDTO academicInfoRequestDTO) {
        User user = helperService.getUserFromSession();
        if (user != null) {
            return new ResponseEntity<>(academicInfoService.saveAcademicInfoByDoctor(user, academicInfoRequestDTO), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("doctor/{doctorId}")
    public ResponseEntity<PageDTO<AcademicInfoDTO>> getByDoctorId(@RequestHeader(value = "accept-language", required = true) String acceptLanguage,
                                                                  @PathVariable UUID doctorId, @RequestParam int pageNumber, @RequestParam int pageSize) {
        Optional<Doctor> optional = doctorService.getRepository().findByUuid(doctorId);
        if (optional == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(academicInfoService.getAcademicInfoByDoctor(doctorId, pageNumber, pageSize), HttpStatus.OK);
        }
    }


    @PutMapping("/user-api/{academicInfoId}")
    public ResponseEntity<String> updateAcademicInfo(@PathVariable UUID academicInfoId, @RequestBody AcademicInfoRequestDTO dto) {
        User user = helperService.getUserFromSession();
        AcademicInfoDTO academicInfoDTO = academicInfoService.findByUUID(academicInfoId);
        if (academicInfoDTO == null || user==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        } else {
            academicInfoService.updateAcademicInfo(academicInfoId, dto, user);
            return new ResponseEntity<>("The object was updated.", HttpStatus.OK);
        }
    }

    @GetMapping("doctor/user-api")
    public ResponseEntity<PageDTO<AcademicInfoDTO>> getByUser(@RequestHeader(value = "accept-language", required = true) String acceptLanguage,
                                                                  @RequestParam int pageNumber, @RequestParam int pageSize) {
        User user=helperService.getUserFromSession();

        if (user==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(academicInfoService.getAcademicInfoByUser(user,pageNumber,pageSize), HttpStatus.OK);
        }
    }

}





