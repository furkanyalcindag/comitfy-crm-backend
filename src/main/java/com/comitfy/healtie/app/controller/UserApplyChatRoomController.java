package com.comitfy.healtie.app.controller;

import com.comitfy.healtie.app.dto.UserApplyChatRoomDTO;
import com.comitfy.healtie.app.dto.requestDTO.UserApplyChatRoomRequestDTO;
import com.comitfy.healtie.app.entity.ChatRoom;
import com.comitfy.healtie.app.entity.UserApplyChatRoom;
import com.comitfy.healtie.app.mapper.UserApplyChatRoomMapper;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.app.repository.UserApplyChatRoomRepository;
import com.comitfy.healtie.app.service.ChatRoomService;
import com.comitfy.healtie.app.service.UserApplyChatRoomService;
import com.comitfy.healtie.app.specification.UserApplyChatRoomSpecification;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseCrudController;
import com.comitfy.healtie.util.common.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("user-apply-chat-room")
public class UserApplyChatRoomController extends BaseCrudController<UserApplyChatRoomDTO, UserApplyChatRoomRequestDTO, UserApplyChatRoom, UserApplyChatRoomRepository, UserApplyChatRoomMapper, UserApplyChatRoomSpecification, UserApplyChatRoomService> {
    @Autowired
    UserApplyChatRoomService userApplyChatRoomService;

    @Autowired
    UserApplyChatRoomMapper userApplyChatRoomMapper;

    @Autowired
    HelperService helperService;

    @Autowired
    ChatRoomService chatRoomService;

    @Override
    protected UserApplyChatRoomService getService() {
        return userApplyChatRoomService;
    }

    @Override
    protected UserApplyChatRoomMapper getMapper() {
        return userApplyChatRoomMapper;
    }

    @PostMapping("/{chatRoomId}")
    public ResponseEntity<UserApplyChatRoomDTO> saveHighlightsByProduct(@PathVariable UUID chatRoomId, @RequestBody UserApplyChatRoomRequestDTO dto) {

        User user = helperService.getUserFromSession();
        ChatRoom chatRoom = chatRoomService.findEntityByUUID(chatRoomId);
        if (chatRoom != null) {
            return new ResponseEntity<>(userApplyChatRoomService.saveUserApplyByChatRoom(chatRoomId, dto, user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("get-all-by-chat-room/{chatRoomId}")
    public ResponseEntity<PageDTO<UserApplyChatRoomDTO>> getByChatRoomId(@RequestHeader(value = "accept-language", required = true) String language,
                                                                         @PathVariable UUID chatRoomId, @RequestParam int pageNumber, @RequestParam int pageSize) {
        PageDTO<UserApplyChatRoomDTO> pageDTO = userApplyChatRoomService.getUserByChatRoom(chatRoomId, pageNumber, pageSize, LanguageEnum.valueOf(language));

        return new ResponseEntity<>(pageDTO, HttpStatus.OK);
    }
}
