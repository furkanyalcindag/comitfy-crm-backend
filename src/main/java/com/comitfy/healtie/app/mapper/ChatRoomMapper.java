package com.comitfy.healtie.app.mapper;

import com.comitfy.healtie.app.dto.ChatRoomDTO;
import com.comitfy.healtie.app.dto.requestDTO.ChatRoomRequestDTO;
import com.comitfy.healtie.app.entity.ChatRoom;
import com.comitfy.healtie.userModule.entity.Role;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ChatRoomMapper implements BaseMapper<ChatRoomDTO, ChatRoomRequestDTO, ChatRoom> {
    @Override
    public ChatRoomDTO entityToDTO(ChatRoom entity) {
        ChatRoomDTO chatRoomDTO = new ChatRoomDTO();
        chatRoomDTO.setName(entity.getName());
        chatRoomDTO.setUserLimit(entity.getUserLimit());
        chatRoomDTO.setColorCode(entity.getColorCode());
       // chatRoomDTO.setApproved(entity.getApproved());
        chatRoomDTO.setStartTime(entity.getStartTime());
        chatRoomDTO.setEndTime(entity.getEndTime());
        chatRoomDTO.setLanguage(entity.getLanguageEnum().name());
        chatRoomDTO.setChatRoomStatusEnum(entity.getChatRoomStatusEnum());
        chatRoomDTO.setDoctorUUID(entity.getDoctorUUID());
        chatRoomDTO.setUuid(entity.getUuid());

        if (entity.getUser() != null) {
            chatRoomDTO.setUserCount(entity.getUser().stream().count());
        }

        return chatRoomDTO;
    }

    @Override
    public ChatRoom dtoToEntity(ChatRoomDTO dto) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(dto.getName());
        chatRoom.setUserLimit(dto.getUserLimit());
        chatRoom.setColorCode(dto.getColorCode());
      //  chatRoom.setApproved(dto.getApproved());
        chatRoom.setStartTime(dto.getStartTime());
        chatRoom.setEndTime(dto.getEndTime());
        chatRoom.setLanguageEnum(dto.getLanguageEnum());
        chatRoom.setChatRoomStatusEnum(dto.getChatRoomStatusEnum());
        chatRoom.setDoctorUUID(dto.getDoctorUUID());
        chatRoom.setUuid(dto.getUuid());
        return chatRoom;
    }

    @Override
    public ChatRoom requestDTOToEntity(ChatRoomRequestDTO dto) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(dto.getName());
        chatRoom.setUserLimit(dto.getUserLimit());
        chatRoom.setColorCode(dto.getColorCode());
       // chatRoom.setApproved(dto.getApproved());
        chatRoom.setStartTime(dto.getStartTime());
        chatRoom.setEndTime(dto.getEndTime());
        chatRoom.setLanguageEnum(dto.getLanguageEnum());
        chatRoom.setChatRoomStatusEnum(dto.getChatRoomStatusEnum());
        chatRoom.setDoctorUUID(dto.getDoctorUUID());
        return chatRoom;
    }

    @Override
    public ChatRoom requestDTOToExistEntity(ChatRoom chatRoom, ChatRoomRequestDTO dto) {
        chatRoom.setName(dto.getName());
        chatRoom.setUserLimit(dto.getUserLimit());
        chatRoom.setColorCode(dto.getColorCode());
       // chatRoom.setApproved(dto.getApproved());
        chatRoom.setStartTime(dto.getStartTime());
        chatRoom.setEndTime(dto.getEndTime());
        chatRoom.setLanguageEnum(dto.getLanguageEnum());
        chatRoom.setChatRoomStatusEnum(dto.getChatRoomStatusEnum());
        chatRoom.setDoctorUUID(dto.getDoctorUUID());
        return chatRoom;
    }

    @Override
    public List<ChatRoom> dtoListToEntityList(List<ChatRoomDTO> chatRoomDTOS) {
        List<ChatRoom> chatRoomList = new ArrayList<>();
        for (ChatRoomDTO chatRoomDTO : chatRoomDTOS) {
            ChatRoom chatRoom = dtoToEntity(chatRoomDTO);
            chatRoomList.add(chatRoom);
        }
        return chatRoomList;
    }

    @Override
    public List<ChatRoomDTO> entityListToDTOList(List<ChatRoom> chatRooms) {
        List<ChatRoomDTO> chatRoomDTOList = new ArrayList<>();
        for (ChatRoom chatRoom : chatRooms) {
            ChatRoomDTO chatRoomDTO = entityToDTO(chatRoom);
            chatRoomDTOList.add(chatRoomDTO);
        }
        return chatRoomDTOList;
    }

    @Override
    public PageDTO<ChatRoomDTO> pageEntityToPageDTO(Page<ChatRoom> pageEntity) {
        PageDTO<ChatRoomDTO> pageDTO = new PageDTO<ChatRoomDTO>();
        List<ChatRoom> entityList = pageEntity.toList();
        List<ChatRoomDTO> chatRoomDTOList = entityListToDTOList(entityList);
        pageDTO.setStart(pageEntity, chatRoomDTOList);
        return pageDTO;
    }
}
