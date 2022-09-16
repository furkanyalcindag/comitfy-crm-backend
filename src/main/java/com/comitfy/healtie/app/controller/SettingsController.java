package com.comitfy.healtie.app.controller;

import com.comitfy.healtie.app.dto.CategoryDTO;
import com.comitfy.healtie.app.dto.SettingsDTO;
import com.comitfy.healtie.app.dto.requestDTO.CategoryRequestDTO;
import com.comitfy.healtie.app.dto.requestDTO.NotificationRequestDTO;
import com.comitfy.healtie.app.dto.requestDTO.SettingsRequestDTO;
import com.comitfy.healtie.app.entity.Settings;
import com.comitfy.healtie.app.mapper.SettingsMapper;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.app.repository.SettingsRepository;
import com.comitfy.healtie.app.service.SettingsService;
import com.comitfy.healtie.app.specification.SettingsSpecification;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.util.common.BaseCrudController;
import com.comitfy.healtie.util.common.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/user-api")
    public ResponseEntity<SettingsRequestDTO> saveByUserId(@RequestHeader(value = "accept-language", required = true) String acceptLanguage,
                                                               @RequestBody SettingsRequestDTO settingsRequestDTO) {
        User user = helperService.getUserFromSession();
        if (user != null) {
            {
                return new ResponseEntity<>(settingsService.saveSettingsByUser(user.getUuid(), settingsRequestDTO), HttpStatus.OK);
            }
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/user-api/{settingsId}")
    public ResponseEntity<String> updateSettings(@PathVariable UUID settingsId, @RequestBody SettingsRequestDTO dto) {
        User user = helperService.getUserFromSession();
        SettingsDTO settingsDTO=settingsService.findByUUID(settingsId);

        if (settingsDTO == null || user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        } else {
            settingsService.updateSettings(settingsId, dto, user);
         //   dto.setLanguageEnum(LanguageEnum.valueOf(categoryDTO.getLanguage()));
            return new ResponseEntity<>("The object was updated.", HttpStatus.OK);
        }

    }
}
