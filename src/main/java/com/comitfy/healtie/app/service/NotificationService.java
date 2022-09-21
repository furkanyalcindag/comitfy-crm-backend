package com.comitfy.healtie.app.service;

import com.comitfy.healtie.app.dto.AcademicInfoDTO;
import com.comitfy.healtie.app.dto.NotificationDTO;
import com.comitfy.healtie.app.dto.requestDTO.NotificationRequestDTO;
import com.comitfy.healtie.app.dto.requestDTO.SettingsRequestDTO;
import com.comitfy.healtie.app.entity.Doctor;
import com.comitfy.healtie.app.entity.Notification;
import com.comitfy.healtie.app.entity.Settings;
import com.comitfy.healtie.app.mapper.NotificationMapper;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.app.repository.NotificationRepository;
import com.comitfy.healtie.app.specification.NotificationSpecification;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.userModule.repository.UserRepository;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationService extends BaseService<NotificationDTO, NotificationRequestDTO, Notification, NotificationRepository, NotificationMapper, NotificationSpecification> {

    @Autowired
    NotificationMapper notificationMapper;

    @Autowired
    NotificationSpecification notificationSpecification;

    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public NotificationRepository getRepository() {
        return notificationRepository;
    }

    @Override
    public NotificationMapper getMapper() {
        return notificationMapper;
    }

    @Override
    public NotificationSpecification getSpecification() {
        return notificationSpecification;
    }

    public NotificationRequestDTO saveNotificationByUser(UUID id, NotificationRequestDTO dto) {
        Optional<User> user = userRepository.findByUuid(id);
        if (user.isPresent()) {
            Notification notification = getMapper().requestDTOToEntity(dto);

            notificationRepository.save(notification);
            return dto;
        } else {
            return null;
        }
    }

    public PageDTO<NotificationDTO> getNotificationByUser(int page, int size, User user, LanguageEnum languageEnum) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));


        return getMapper().pageEntityToPageDTO(getRepository().findAll(pageable));

    }

    public NotificationRequestDTO updateNotification(UUID id, NotificationRequestDTO dto, User user) {
        Optional<Notification> notification = notificationRepository.findByUuid(id);
        if (notification.isPresent()) {
            Notification notification1 = notificationMapper.requestDTOToExistEntity(notification.get(), dto);
            notification1.setTitle(dto.getTitle());
            notification1.setMessage(dto.getMessage());
            notification1.setLink(dto.getLink());
            notification1.setBase64(dto.getBase64());
            notification1.setSend(dto.isSend());

            notificationRepository.save(notification1);

            return dto;
        } else {
            return null;
        }

    }


}
