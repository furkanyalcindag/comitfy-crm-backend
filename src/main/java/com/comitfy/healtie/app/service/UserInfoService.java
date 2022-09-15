package com.comitfy.healtie.app.service;

import com.comitfy.healtie.app.dto.requestDTO.DoctorProfileImageRequestDTO;
import com.comitfy.healtie.app.entity.Doctor;
import com.comitfy.healtie.app.repository.DoctorRepository;
import com.comitfy.healtie.userModule.dto.UserDTO;
import com.comitfy.healtie.userModule.dto.requestDTO.UserAgeRangeRequestDTO;
import com.comitfy.healtie.userModule.dto.requestDTO.UserGenderRequestDTO;
import com.comitfy.healtie.userModule.dto.requestDTO.UserNameRequestDTO;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.userModule.mapper.UserMapper;
import com.comitfy.healtie.userModule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserInfoService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    DoctorRepository doctorRepository;

    public long getLikeCountByUser(UUID userUUID) {

        return userRepository.getLikeCountByUser(userUUID);
    }


    public long getSaveCountByUser(UUID userUUID) {

        return userRepository.getSaveCountByUser(userUUID);
    }

    public long getLikeCountOfArticleByUser(UUID userUUID) {
        return doctorRepository.getCountOfArticleLikes(userUUID);
    }


    public UserDTO findByUUID(UUID uuid) {
        Optional<User> optionalEntity = userRepository.findByUuid(uuid);
        return optionalEntity.map(entity -> userMapper.entityToDTO(entity)).orElse(null);

    }

    public UserGenderRequestDTO updateGender(UUID id, UserGenderRequestDTO dto) {
        Optional<User> user = userRepository.findByUuid(id);
        if (user.isPresent()) {
            User user1 = userMapper.requestDTOToExistEntityforGender(user.get(), dto);
            user1.setGenderEnum(dto.getGenderEnum());

            userRepository.save(user1);

            return dto;
        } else {
            return null;
        }
    }

    public UserAgeRangeRequestDTO updateAgeRange(UUID id, UserAgeRangeRequestDTO dto) {
        Optional<User> user = userRepository.findByUuid(id);
        if (user.isPresent()) {
            User user1 = userMapper.requestDTOToExistEntityforAgeRange(user.get(), dto);
            user1.setAgeRangeEnum(dto.getAgeRangeEnum());

            userRepository.save(user1);

            return dto;
        } else {
            return null;
        }
    }

    public UserNameRequestDTO updateName(UserNameRequestDTO dto,User user) {

        if (user!=null) {
            User user1 = userMapper.requestDTOToExistEntityforName(user, dto);
            user1.setFirstName(dto.getFirstName());
            user1.setLastName(dto.getLastName());

            userRepository.save(user1);

            return dto;
        } else {
            return null;
        }
    }

}
