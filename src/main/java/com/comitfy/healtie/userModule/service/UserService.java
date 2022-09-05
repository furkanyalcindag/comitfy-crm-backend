package com.comitfy.healtie.userModule.service;

import com.comitfy.healtie.app.dto.requestDTO.ArticleRequestDTO;
import com.comitfy.healtie.app.entity.Article;
import com.comitfy.healtie.userModule.dto.UserDTO;
import com.comitfy.healtie.userModule.dto.requestDTO.UserRequestDTO;
import com.comitfy.healtie.userModule.entity.Role;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.userModule.mapper.UserMapper;
import com.comitfy.healtie.userModule.repository.RoleRepository;
import com.comitfy.healtie.userModule.repository.UserRepository;
import com.comitfy.healtie.userModule.specification.UserSpecification;
import com.comitfy.healtie.util.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService extends BaseService<UserDTO, UserRequestDTO, User, UserRepository, UserMapper, UserSpecification> {

    @Autowired
    UserSpecification userSpecification;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserRepository getRepository() {
        return userRepository;
    }

    @Override
    public UserMapper getMapper() {
        return userMapper;
    }

    @Override
    public UserSpecification getSpecification() {
        return userSpecification;
    }

    public User getUserByEmail(String email) {

        return getRepository().findByEmail(email).get();
    }
    public UserRequestDTO saveUserByRole(UUID id, UserRequestDTO dto) {
        Optional<Role> role = roleRepository.findByUuid(id);
        if (role.isPresent()) {
            User user = getMapper().requestDTOToEntity(dto);
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            userRepository.save(user);
            return dto;
        } else {
            return null;
        }
    }
}
