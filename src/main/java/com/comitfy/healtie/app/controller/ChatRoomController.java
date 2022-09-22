package com.comitfy.healtie.app.controller;

import com.comitfy.healtie.app.dto.ChatRoomDTO;
import com.comitfy.healtie.app.dto.requestDTO.ChatRoomRequestDTO;
import com.comitfy.healtie.app.entity.ChatRoom;
import com.comitfy.healtie.app.mapper.ChatRoomMapper;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.app.repository.ChatRoomRepository;
import com.comitfy.healtie.app.service.ChatRoomService;
import com.comitfy.healtie.app.specification.ChatRoomSpecification;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseWithMultiLanguageCrudController;
import com.comitfy.healtie.util.common.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("chat-room")
public class ChatRoomController extends BaseWithMultiLanguageCrudController<ChatRoomDTO, ChatRoomRequestDTO, ChatRoom, ChatRoomRepository, ChatRoomMapper, ChatRoomSpecification, ChatRoomService> {
    @Autowired
    ChatRoomService chatRoomService;

    @Autowired
    ChatRoomMapper chatRoomMapper;

    @Autowired
    HelperService helperService;

    @Override
    protected ChatRoomService getService() {
        return chatRoomService;
    }

    @Override
    protected ChatRoomMapper getMapper() {
        return chatRoomMapper;
    }

    @PostMapping("/user-api")
    public ResponseEntity<ChatRoomRequestDTO> saveByUser(@RequestHeader(value = "accept-language", required = true) String acceptLanguage,
                                                         @RequestBody ChatRoomRequestDTO chatRoomRequestDTO) {
        User user = helperService.getUserFromSession();
        if (user != null) {
            {
                chatRoomRequestDTO.setLanguageEnum(LanguageEnum.valueOf(acceptLanguage));
                return new ResponseEntity<>(chatRoomService.saveChatRoomByUser(user.getUuid(), chatRoomRequestDTO), HttpStatus.OK);
            }
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/user-api/{chatRoomId}")
    public ResponseEntity<String> updateTag(@PathVariable UUID chatRoomId, @RequestBody ChatRoomRequestDTO dto) {
        User user = helperService.getUserFromSession();
        ChatRoomDTO chatRoomDTO = chatRoomService.findByUUID(chatRoomId);

        if (chatRoomDTO == null || user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        } else {
            dto.setLanguageEnum(LanguageEnum.valueOf(chatRoomDTO.getLanguage()));
            chatRoomService.updateChatRoom(chatRoomId, dto, user);
            return new ResponseEntity<>("The object was updated.", HttpStatus.OK);
        }

    }

    @GetMapping("/approved-user-api/")
    public ResponseEntity<PageDTO<ChatRoomDTO>> getApprovedTrueByUser(@RequestParam int pageNumber, @RequestParam int pageSize) {
        User user = helperService.getUserFromSession();
        if (user != null) {
            PageDTO<ChatRoomDTO> dtoList = chatRoomService.getApprovedTrueByUser(pageNumber, pageSize, user);
            return new ResponseEntity<>(dtoList, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
