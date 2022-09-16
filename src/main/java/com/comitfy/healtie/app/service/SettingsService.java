package com.comitfy.healtie.app.service;

import com.comitfy.healtie.app.dto.SettingsDTO;
import com.comitfy.healtie.app.dto.requestDTO.CategoryRequestDTO;
import com.comitfy.healtie.app.dto.requestDTO.NotificationRequestDTO;
import com.comitfy.healtie.app.dto.requestDTO.SettingsRequestDTO;
import com.comitfy.healtie.app.entity.Category;
import com.comitfy.healtie.app.entity.Notification;
import com.comitfy.healtie.app.entity.Settings;
import com.comitfy.healtie.app.mapper.SettingsMapper;
import com.comitfy.healtie.app.repository.SettingsRepository;
import com.comitfy.healtie.app.specification.SettingsSpecification;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.userModule.repository.UserRepository;
import com.comitfy.healtie.util.common.BaseService;
import com.comitfy.healtie.util.common.BaseSpecification;
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
            Settings settings1 = settingsMapper.requestDTOToExistEntity(settings.get(), dto);
            settings1.setKey(dto.getKey());
            settings1.setValue(dto.getValue());
            settings1.setCurrent(dto.isCurrent());

            settingsRepository.save(settings1);
            return dto;
        } else {
            return null;
        }

    }
}
