package com.comitfy.healtie.app.service;

import com.comitfy.healtie.app.dto.ArticleDTO;
import com.comitfy.healtie.app.dto.ContractActiveDTO;
import com.comitfy.healtie.app.dto.UserApplyChatRoomDTO;
import com.comitfy.healtie.app.dto.requestDTO.UserApplyChatRoomRequestDTO;
import com.comitfy.healtie.app.entity.ChatRoom;
import com.comitfy.healtie.app.entity.UserApplyChatRoom;
import com.comitfy.healtie.app.mapper.UserApplyChatRoomMapper;
import com.comitfy.healtie.app.model.enums.ChatRoomStatusEnum;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.app.repository.ChatRoomRepository;
import com.comitfy.healtie.app.repository.UserApplyChatRoomRepository;
import com.comitfy.healtie.app.specification.UserApplyChatRoomSpecification;
import com.comitfy.healtie.userModule.dto.UserDTO;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.userModule.mapper.UserMapper;
import com.comitfy.healtie.userModule.repository.UserRepository;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseService;
import com.comitfy.healtie.util.common.HelperService;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserApplyChatRoomService extends BaseService<UserApplyChatRoomDTO, UserApplyChatRoomRequestDTO, UserApplyChatRoom, UserApplyChatRoomRepository, UserApplyChatRoomMapper, UserApplyChatRoomSpecification> {

    @Autowired
    UserApplyChatRoomRepository userApplyChatRoomRepository;

    @Autowired
    UserApplyChatRoomMapper userApplyChatRoomMapper;

    @Autowired
    UserApplyChatRoomSpecification userApplyChatRoomSpecification;

    @Autowired
    ChatRoomRepository chatRoomRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;

    @Autowired
    HelperService helperService;

    @Override
    public UserApplyChatRoomRepository getRepository() {
        return userApplyChatRoomRepository;
    }

    @Override
    public UserApplyChatRoomMapper getMapper() {
        return userApplyChatRoomMapper;
    }

    @Override
    public UserApplyChatRoomSpecification getSpecification() {
        return userApplyChatRoomSpecification;
    }

    public UserApplyChatRoomDTO saveUserApplyByChatRoom(UUID chatRoomUUID, UserApplyChatRoomRequestDTO dto, User user) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findByUuid(chatRoomUUID);
        if (chatRoom.isPresent()) {
            if (chatRoom.get().getChatRoomStatusEnum() == ChatRoomStatusEnum.PUBLIC) {
                UserApplyChatRoom userApplyChatRoom = getMapper().requestDTOToEntity(dto);
                userApplyChatRoom.setUserUuid(user.getUuid());
                userApplyChatRoom.setChatRoomUuid(chatRoomUUID);
                userApplyChatRoom.setApproved(Boolean.TRUE);
                userApplyChatRoomRepository.save(userApplyChatRoom);
                return getMapper().entityToDTO(userApplyChatRoom);
            } else{
                UserApplyChatRoom userApplyChatRoom = getMapper().requestDTOToEntity(dto);
                userApplyChatRoom.setUserUuid(user.getUuid());
                userApplyChatRoom.setChatRoomUuid(chatRoomUUID);
                userApplyChatRoom.setApproved(Boolean.FALSE);
                userApplyChatRoomRepository.save(userApplyChatRoom);
                return getMapper().entityToDTO(userApplyChatRoom);
            }
        }
        else return null;
    }

    public PageDTO<UserApplyChatRoomDTO> getAllByChatRoom(UUID id, int page, int size, LanguageEnum languageEnum) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findByUuid(id);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));


        if (chatRoom.isPresent()) {
        // userMapper.pageEntityToPageDTO(userRepository.getUsersByChatRoom(id.toString(),pageable));
           return getMapper().pageEntityToPageDTO(userApplyChatRoomRepository.findAllByChatRoomUuid(pageable, id));
        } else {
            return null;
        }

    }

    public PageDTO<UserApplyChatRoomDTO> getApprovedApply(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));

        PageDTO<UserApplyChatRoomDTO> pageDTO = getMapper().pageEntityToPageDTO(userApplyChatRoomRepository.findAllByApproved(pageable, true));

        return pageDTO;

    }

}