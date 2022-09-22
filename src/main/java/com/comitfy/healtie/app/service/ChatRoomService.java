package com.comitfy.healtie.app.service;

import com.comitfy.healtie.app.dto.ChatRoomDTO;
import com.comitfy.healtie.app.dto.requestDTO.ChatRoomRequestDTO;
import com.comitfy.healtie.app.entity.ChatRoom;
import com.comitfy.healtie.app.mapper.ChatRoomMapper;
import com.comitfy.healtie.app.repository.ChatRoomRepository;
import com.comitfy.healtie.app.specification.ChatRoomSpecification;
import com.comitfy.healtie.commercial.dto.HighlightsDTO;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.userModule.repository.UserRepository;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseWithMultiLanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatRoomService extends BaseWithMultiLanguageService<ChatRoomDTO, ChatRoomRequestDTO, ChatRoom, ChatRoomRepository, ChatRoomMapper, ChatRoomSpecification> {

    @Autowired
    ChatRoomRepository chatRoomRepository;

    @Autowired
    ChatRoomMapper chatRoomMapper;

    @Autowired
    ChatRoomSpecification chatRoomSpecification;

    @Autowired
    UserRepository userRepository;

    @Override
    public ChatRoomRepository getRepository() {
        return chatRoomRepository;
    }

    @Override
    public ChatRoomMapper getMapper() {
        return chatRoomMapper;
    }

    @Override
    public ChatRoomSpecification getSpecification() {
        return chatRoomSpecification;
    }

    public ChatRoomRequestDTO saveChatRoomByUser(UUID id, ChatRoomRequestDTO dto) {
        Optional<User> user = userRepository.findByUuid(id);
        if (user.isPresent()) {
            ChatRoom chatRoom = getMapper().requestDTOToEntity(dto);
            chatRoomRepository.save(chatRoom);
            return dto;
        } else {
            return null;
        }
    }

    public ChatRoomRequestDTO updateChatRoom(UUID id, ChatRoomRequestDTO dto, User user) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findByUuid(id);
        if (chatRoom.isPresent()) {
            ChatRoom chatRoom1 = chatRoomMapper.requestDTOToExistEntity(chatRoom.get(), dto);

            chatRoomRepository.save(chatRoom1);
            return dto;
        } else {
            return null;
        }

    }

    public PageDTO<ChatRoomDTO> getApprovedTrueByUser(int page, int size,User user){

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));

        return getMapper().pageEntityToPageDTO(chatRoomRepository.getApprovedHighlightsOfUser(pageable, user));

    }


}
