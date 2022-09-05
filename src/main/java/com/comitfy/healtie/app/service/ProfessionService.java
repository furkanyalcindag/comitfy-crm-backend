package com.comitfy.healtie.app.service;

import com.comitfy.healtie.app.dto.ProfessionDTO;
import com.comitfy.healtie.app.dto.requestDTO.NotificationRequestDTO;
import com.comitfy.healtie.app.dto.requestDTO.ProfessionRequestDTO;
import com.comitfy.healtie.app.entity.Notification;
import com.comitfy.healtie.app.entity.Profession;
import com.comitfy.healtie.app.mapper.ProfessionMapper;
import com.comitfy.healtie.app.repository.ProfessionRepository;
import com.comitfy.healtie.app.specification.ProfessionSpecification;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.userModule.repository.UserRepository;
import com.comitfy.healtie.util.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProfessionService extends BaseService<ProfessionDTO, ProfessionRequestDTO, Profession, ProfessionRepository, ProfessionMapper, ProfessionSpecification> {

    @Autowired
    ProfessionRepository professionRepository;

    @Autowired
    ProfessionMapper professionMapper;

    @Autowired
    ProfessionSpecification professionSpecification;

    @Autowired
    UserRepository userRepository;

    @Override
    public ProfessionRepository getRepository() {
        return professionRepository;
    }

    @Override
    public ProfessionMapper getMapper() {
        return professionMapper;
    }

    @Override
    public ProfessionSpecification getSpecification() {
        return professionSpecification;
    }
    public ProfessionRequestDTO saveProfessionByUser(UUID id, ProfessionRequestDTO dto) {
        Optional<User> user = userRepository.findByUuid(id);
        if (user.isPresent()) {
            Profession profession = getMapper().requestDTOToEntity(dto);

            professionRepository.save(profession);
            return dto;
        } else {
            return null;
        }
    }

}
