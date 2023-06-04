package com.comitfy.crm.app.service;

import com.comitfy.crm.app.dto.SettingsDTO;
import com.comitfy.crm.app.dto.requestDTO.ReceiptRequestDTO;
import com.comitfy.crm.app.dto.requestDTO.SettingsRequestDTO;
import com.comitfy.crm.app.entity.Product;
import com.comitfy.crm.app.entity.Settings;
import com.comitfy.crm.app.mapper.SettingsMapper;
import com.comitfy.crm.app.repository.SettingsRepository;
import com.comitfy.crm.app.specification.SettingsSpecification;
import com.comitfy.crm.userModule.entity.User;
import com.comitfy.crm.userModule.repository.UserRepository;
import com.comitfy.crm.util.common.BaseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SettingsService extends BaseService<SettingsDTO, SettingsRequestDTO, Settings, SettingsRepository, SettingsMapper, SettingsSpecification> {

    @Autowired
    SettingsRepository settingsRepository;

    @Autowired
    SettingsMapper settingsMapper;

    @Autowired
    SettingsSpecification settingsSpecification;

    @Autowired
    UserRepository userRepository;

    @Override
    public SettingsRepository getRepository() {
        return settingsRepository;
    }

    @Override
    public SettingsMapper getMapper() {
        return settingsMapper;
    }

    @Override
    public SettingsSpecification getSpecification() {
        return settingsSpecification;
    }

    public SettingsRequestDTO saveSettingsByUser(UUID id, SettingsRequestDTO dto) {
        Optional<User> user = userRepository.findByUuid(id);
        if (user.isPresent()) {
            Settings settings = getMapper().requestDTOToEntity(dto);

            settingsRepository.save(settings);
            return dto;
        } else {
            return null;
        }
    }

    public SettingsRequestDTO updateSettings(UUID id, SettingsRequestDTO dto, User user) {
        Optional<Settings> settings = settingsRepository.findByUuid(id);
        if (settings.isPresent()) {
            //Settings settings1 = settingsMapper.requestDTOToExistEntity(settings.get(), dto);
            Settings settings1 = new Settings();
            settings1.setKey(dto.getKey());
            settings1.setValue(dto.getValue());
            settings1.setCurrent(dto.isCurrent());

            settingsRepository.save(settings1);
            return dto;
        } else {
            return null;
        }

    }


    public SettingsDTO saveDefaultFeature(ReceiptRequestDTO dto) {
        try {
            Settings settings = settingsRepository.findByKey("defaultFeatures");

            ObjectMapper mapper = new ObjectMapper();

            settings.setValue(mapper.writeValueAsString(dto.getReceipts()));

            settingsRepository.save(settings);

            return getMapper().entityToDTO(settings);
        } catch (Exception e) {
            return null;
        }

    }

    public SettingsDTO updateByKey(SettingsRequestDTO dto) {

        Settings settings = settingsRepository.findByKey(dto.getKey());

        settings.setValue(dto.getValue());

        settingsRepository.save(settings);

        return getMapper().entityToDTO(settings);

    }

    public SettingsDTO getByKey(String key) {

        Settings settings = settingsRepository.findByKey(key);


        return getMapper().entityToDTO(settings);

    }
}
