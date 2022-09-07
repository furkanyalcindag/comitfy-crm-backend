package com.comitfy.healtie.app.controller;

import com.comitfy.healtie.app.dto.ExperienceDTO;
import com.comitfy.healtie.app.dto.requestDTO.ExperienceRequestDTO;
import com.comitfy.healtie.app.entity.Doctor;
import com.comitfy.healtie.app.entity.Experience;
import com.comitfy.healtie.app.mapper.ExperienceMapper;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.app.repository.ExperienceRepository;
import com.comitfy.healtie.app.service.DoctorService;
import com.comitfy.healtie.app.service.ExperienceService;
import com.comitfy.healtie.app.specification.ExperienceSpecification;
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
@RequestMapping("experience")
public class ExperienceController extends BaseCrudController<ExperienceDTO, ExperienceRequestDTO, Experience, ExperienceRepository, ExperienceMapper, ExperienceSpecification, ExperienceService> {

    @Autowired
    ExperienceService experienceService;

    @Autowired
    ExperienceMapper experienceMapper;

    @Autowired
    DoctorService doctorService;

    @Autowired
    HelperService helperService;

    @Override
    protected ExperienceService getService() {
        return experienceService;
    }

    @Override
    protected ExperienceMapper getMapper() {
        return experienceMapper;
    }

    @PostMapping("/doctor-api")
    public ResponseEntity<ExperienceRequestDTO> saveByDoctor(@RequestHeader(value = "accept-language", required = true) String acceptLanguage,
                                                             @RequestBody ExperienceRequestDTO experienceRequestDTO) {
        User user = helperService.getUserFromSession();
        if (user != null) {
            experienceRequestDTO.setLanguageEnum(LanguageEnum.valueOf(experienceRequestDTO.getLanguage()));
            return new ResponseEntity<>(experienceService.saveExperienceByDoctor(user, experienceRequestDTO), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("doctor/{doctorId}")
    public ResponseEntity<PageDTO<ExperienceDTO>> getByDoctorId(@RequestHeader(value = "accept-language", required = true) String acceptLanguage,
                                                                @PathVariable UUID doctorId, @RequestParam int pageNumber, @RequestParam int pageSize) {
        Optional<Doctor> optional = doctorService.getRepository().findByUuid(doctorId);
        if (optional == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(experienceService.getExperienceByDoctor(doctorId, pageNumber, pageSize), HttpStatus.OK);
        }
    }

    @PutMapping("/user-api/{experienceId}")
    public ResponseEntity<String> updateExperience(@PathVariable UUID experienceId, @RequestBody ExperienceRequestDTO dto) {
        User user = helperService.getUserFromSession();
        ExperienceDTO experienceDTO = experienceService.findByUUID(experienceId);
        if (experienceDTO == null || user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        } else {
            dto.setLanguageEnum(LanguageEnum.valueOf(dto.getLanguage()));
            experienceService.updateExperience(experienceId, dto, user);
            return new ResponseEntity<>("The object was updated.", HttpStatus.OK);
        }
    }
}
