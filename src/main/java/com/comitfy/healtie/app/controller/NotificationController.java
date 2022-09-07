package com.comitfy.healtie.app.controller;

import com.comitfy.healtie.app.dto.NotificationDTO;
import com.comitfy.healtie.app.dto.requestDTO.NotificationRequestDTO;
import com.comitfy.healtie.app.entity.Notification;
import com.comitfy.healtie.app.mapper.NotificationMapper;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.app.repository.NotificationRepository;
import com.comitfy.healtie.app.service.NotificationService;
import com.comitfy.healtie.app.specification.NotificationSpecification;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseCrudController;
import com.comitfy.healtie.util.common.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("notification")
public class NotificationController extends BaseCrudController<NotificationDTO, NotificationRequestDTO, Notification, NotificationRepository, NotificationMapper, NotificationSpecification, NotificationService> {

    @Autowired
    NotificationService notificationService;

    @Autowired
    NotificationMapper notificationMapper;
    @Autowired
    HelperService helperService;

    @Override
    protected NotificationService getService() {
        return notificationService;
    }

    @Override
    protected NotificationMapper getMapper() {
        return notificationMapper;
    }

    @PostMapping("/user-api")
    public ResponseEntity<NotificationRequestDTO> saveByUserId(@RequestHeader(value = "accept-language", required = true) String acceptLanguage,
                                                               @RequestBody NotificationRequestDTO notificationRequestDTO) {
        User user = helperService.getUserFromSession();
        if (user != null) {
            {
                return new ResponseEntity<>(notificationService.saveNotificationByUser(user.getUuid(), notificationRequestDTO), HttpStatus.OK);
            }
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("user-api")
    public ResponseEntity<PageDTO<NotificationDTO>> getNotificationByUser(@RequestHeader(value = "accept-language", required = true) String language,
                                                                          @RequestParam int pageNumber, @RequestParam int pageSize) {

        User user = helperService.getUserFromSession();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(notificationService.getNotificationByUser(pageNumber, pageSize, user, LanguageEnum.valueOf(language)), HttpStatus.OK);
        }
    }

}
