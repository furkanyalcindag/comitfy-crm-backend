package com.comitfy.crm.app.controller;

import com.comitfy.crm.app.dto.SettingsDTO;
import com.comitfy.crm.app.dto.requestDTO.ReceiptRequestDTO;
import com.comitfy.crm.app.dto.requestDTO.SettingsRequestDTO;
import com.comitfy.crm.app.entity.Settings;
import com.comitfy.crm.app.mapper.SettingsMapper;
import com.comitfy.crm.app.repository.SettingsRepository;
import com.comitfy.crm.app.service.SettingsService;
import com.comitfy.crm.app.specification.SettingsSpecification;
import com.comitfy.crm.userModule.entity.User;
import com.comitfy.crm.util.common.BaseCrudController;
import com.comitfy.crm.util.common.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("settings")
public class SettingsController extends BaseCrudController<SettingsDTO, SettingsRequestDTO, Settings, SettingsRepository, SettingsMapper, SettingsSpecification, SettingsService> {

    @Autowired
    SettingsMapper settingsMapper;

    @Autowired
    SettingsService settingsService;

    @Autowired
    HelperService helperService;

    @Override
    protected SettingsService getService() {
        return settingsService;
    }

    @Override
    protected SettingsMapper getMapper() {
        return settingsMapper;
    }


    @PutMapping("/update-by-key/")
    public ResponseEntity<SettingsDTO> updateSettingsByKey(@RequestBody SettingsRequestDTO dto) {
        SettingsDTO settingsDTO = settingsService.updateByKey(dto);
        return new ResponseEntity<>(settingsDTO, HttpStatus.OK);
    }


    @GetMapping("/get-by-key/{key}")
    public ResponseEntity<SettingsDTO> getByKey(@PathVariable String key) {
        SettingsDTO settingsDTO = settingsService.getByKey(key);
        return new ResponseEntity<>(settingsDTO, HttpStatus.OK);
    }

    @PutMapping("/save-default-feature/")
    public ResponseEntity<SettingsDTO> saveDefaultFeauture(@RequestBody ReceiptRequestDTO dto) {
        SettingsDTO settingsDTO = settingsService.saveDefaultFeature(dto);
        return new ResponseEntity<>(settingsDTO, HttpStatus.OK);
    }


}
