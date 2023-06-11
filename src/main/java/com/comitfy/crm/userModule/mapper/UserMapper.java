package com.comitfy.crm.userModule.mapper;

import com.comitfy.crm.app.dto.AutoCompleteDTO;
import com.comitfy.crm.userModule.dto.UserDTO;
import com.comitfy.crm.userModule.dto.requestDTO.UserAgeRangeRequestDTO;
import com.comitfy.crm.userModule.dto.requestDTO.UserGenderRequestDTO;
import com.comitfy.crm.userModule.dto.requestDTO.UserNameRequestDTO;
import com.comitfy.crm.userModule.dto.requestDTO.UserRequestDTO;
import com.comitfy.crm.userModule.entity.Role;
import com.comitfy.crm.userModule.entity.User;
import com.comitfy.crm.userModule.repository.RoleRepository;
import com.comitfy.crm.util.PageDTO;
import com.comitfy.crm.util.common.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.*;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<UserDTO, UserRequestDTO, User> {

    /*@Override
    public UserDTO entityToDTO(User entity) {

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(entity.getEmail());
        userDTO.setFirstName(entity.getFirstName());
        userDTO.setLastName(entity.getLastName());
        userDTO.setEmail(entity.getEmail());
        userDTO.setPhotoLink(entity.getPhotoLink());
        userDTO.setUuid(entity.getUuid());

        userDTO.setGenderEnum(entity.getGenderEnum());
        userDTO.setAgeRangeEnum(entity.getAgeRangeEnum());


     /*   Set<RoleDTO> roleDTOS = new HashSet<>();
        for (Role role : entity.getRoles()) {

            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setName(role.getName());
            roleDTO.setUuid(role.getUuid());
            roleDTOS.add(roleDTO);
        }


        return userDTO;
    }

    @Override
    public User dtoToEntity(UserDTO dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPhotoLink(dto.getPhotoLink());
        return user;

    }

    @Override
    public User requestDTOToEntity(UserRequestDTO dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setAgeRangeEnum(dto.getAgeRangeEnum());
        user.setGenderEnum(dto.getGenderEnum());
        user.setUsername(dto.getEmail());
        //user.setPhotoLink(dto.getPhotoLink());
        user.setRoles(new HashSet<>());
    //    user.setChatRoomSet(new HashSet<>());

        return user;
    }


    @Override
    public User requestDTOToExistEntity(User user, UserRequestDTO dto) {

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setGenderEnum(dto.getGenderEnum());
        user.setAgeRangeEnum(dto.getAgeRangeEnum());
        //user.setPhotoLink(dto.getPhotoLink());
        return user;
    }

    public User requestDTOToExistEntityforGender(User user, UserGenderRequestDTO dto) {
        user.setGenderEnum(dto.getGenderEnum());

        return user;
    }

    public User requestDTOToExistEntityforAgeRange(User user, UserAgeRangeRequestDTO dto) {
        user.setAgeRangeEnum(dto.getAgeRangeEnum());

        return user;
    }

    public User requestDTOToExistEntityforName(User user, UserNameRequestDTO dto) {

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());

        return user;
    }


    @Override
    public List<User> dtoListToEntityList(List<UserDTO> userDTOS) {
        List<User> userList = new ArrayList<>();
        for (UserDTO userDTO : userDTOS) {
            User user = dtoToEntity(userDTO);
            userList.add(user);
        }
        return userList;
    }

    @Override
    public List<UserDTO> entityListToDTOList(List<User> users) {
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = entityToDTO(user);
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    @Override
    public PageDTO<UserDTO> pageEntityToPageDTO(Page<User> pageEntity) {
        PageDTO<UserDTO> pageDTO = new PageDTO<UserDTO>();
        List<User> entityList = pageEntity.toList();
        List<UserDTO> userDTOList = entityListToDTOList(entityList);
        pageDTO.setStart(pageEntity, userDTOList);

        return pageDTO;
    }*/

    @Mappings({@Mapping(source = "uuid", target = "value"),
            @Mapping(source = "username", target = "label")

    })
    AutoCompleteDTO entityToAutoCompleteDTO(User entity);
}
