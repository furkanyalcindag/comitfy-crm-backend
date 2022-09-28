package com.comitfy.healtie.app.mapper;

import com.comitfy.healtie.app.dto.UserApplyChatRoomDTO;
import com.comitfy.healtie.app.dto.requestDTO.UserApplyChatRoomRequestDTO;
import com.comitfy.healtie.app.entity.UserApplyChatRoom;
import com.comitfy.healtie.userModule.repository.UserRepository;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseMapper;
import com.comitfy.healtie.util.common.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserApplyChatRoomMapper implements BaseMapper<UserApplyChatRoomDTO, UserApplyChatRoomRequestDTO, UserApplyChatRoom> {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HelperService helperService;

    @Override
    public UserApplyChatRoomDTO entityToDTO(UserApplyChatRoom entity) {
        UserApplyChatRoomDTO userApplyChatRoomDTO = new UserApplyChatRoomDTO();
        userApplyChatRoomDTO.setApproved(entity.getApproved());
        userApplyChatRoomDTO.setChatRoomUUID(entity.getChatRoomUuid());
        userApplyChatRoomDTO.setUserUUID(entity.getUserUuid());
        userApplyChatRoomDTO.setUuid(entity.getUuid());
       // userApplyChatRoomDTO.setUserName(userRepository.findByUuid(entity.getUserUuid()).get().getFirstName() + " " + userRepository.findByUuid(entity.getUserUuid()).get().getLastName());

        return userApplyChatRoomDTO;
    }


    @Override
    public UserApplyChatRoom dtoToEntity(UserApplyChatRoomDTO dto) {
        UserApplyChatRoom userApplyChatRoom = new UserApplyChatRoom();
        userApplyChatRoom.setApproved(dto.isApproved());
        userApplyChatRoom.setChatRoomUuid(dto.getChatRoomUUID());
        userApplyChatRoom.setUserUuid(dto.getUserUUID());
        return userApplyChatRoom;
    }

    @Override
    public UserApplyChatRoom requestDTOToEntity(UserApplyChatRoomRequestDTO dto) {
        UserApplyChatRoom userApplyChatRoom = new UserApplyChatRoom();
        return userApplyChatRoom;
    }

    @Override
    public UserApplyChatRoom requestDTOToExistEntity(UserApplyChatRoom userApplyChatRoom, UserApplyChatRoomRequestDTO dto) {
        return userApplyChatRoom;
    }

    @Override
    public List<UserApplyChatRoom> dtoListToEntityList(List<UserApplyChatRoomDTO> userApplyChatRoomDTOS) {
        List<UserApplyChatRoom> userApplyChatRoomList = new ArrayList<>();
        for (UserApplyChatRoomDTO userApplyChatRoomDTO : userApplyChatRoomDTOS) {
            UserApplyChatRoom userApplyChatRoom = dtoToEntity(userApplyChatRoomDTO);
            userApplyChatRoomList.add(userApplyChatRoom);
        }
        return userApplyChatRoomList;
    }

    @Override
    public List<UserApplyChatRoomDTO> entityListToDTOList(List<UserApplyChatRoom> userApplyChatRooms) {
        List<UserApplyChatRoomDTO> userApplyChatRoomDTOList = new ArrayList<>();
        for (UserApplyChatRoom userApplyChatRoom : userApplyChatRooms) {
            UserApplyChatRoomDTO userApplyChatRoomDTO = entityToDTO(userApplyChatRoom);
            userApplyChatRoomDTOList.add(userApplyChatRoomDTO);
        }
        return userApplyChatRoomDTOList;
    }

    @Override
    public PageDTO<UserApplyChatRoomDTO> pageEntityToPageDTO(Page<UserApplyChatRoom> pageEntity) {
        PageDTO<UserApplyChatRoomDTO> pageDTO = new PageDTO<UserApplyChatRoomDTO>();
        List<UserApplyChatRoom> entityList = pageEntity.toList();
        List<UserApplyChatRoomDTO> userApplyChatRoomDTOList = entityListToDTOList(entityList);
        pageDTO.setStart(pageEntity, userApplyChatRoomDTOList);
        return pageDTO;
    }

}
