package com.comitfy.healtie.app.controller;

import com.comitfy.healtie.app.dto.ProfessionDTO;
import com.comitfy.healtie.app.dto.requestDTO.ProfessionRequestDTO;
import com.comitfy.healtie.app.entity.Profession;
import com.comitfy.healtie.app.mapper.ProfessionMapper;
import com.comitfy.healtie.app.repository.ProfessionRepository;
import com.comitfy.healtie.app.service.ProfessionService;
import com.comitfy.healtie.app.specification.ProfessionSpecification;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.util.common.BaseCrudController;
import com.comitfy.healtie.util.common.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("profession")
public class ProfessionController extends BaseCrudController<ProfessionDTO, ProfessionRequestDTO, Profession, ProfessionRepository, ProfessionMapper, ProfessionSpecification, ProfessionService> {

    @Autowired
    ProfessionService professionService;

    @Autowired
    ProfessionMapper professionMapper;

    @Autowired
    HelperService helperService;


    @Override
    protected ProfessionService getService() {
        return professionService;
    }

    @Override
    protected ProfessionMapper getMapper() {
        return professionMapper;
    }

    @PostMapping("/user-api")
    public ResponseEntity<ProfessionRequestDTO> saveByUserId(@RequestHeader(value = "accept-language", required = true) String acceptLanguage,
                                                             @RequestBody ProfessionRequestDTO professionRequestDTO) {
        User user = helperService.getUserFromSession();
        if (user != null) {
            {
                return new ResponseEntity<>(professionService.saveProfessionByUser(user.getUuid(), professionRequestDTO), HttpStatus.OK);
            }
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
