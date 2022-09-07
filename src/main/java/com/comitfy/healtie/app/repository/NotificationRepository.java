package com.comitfy.healtie.app.repository;

import com.comitfy.healtie.app.entity.Notification;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.util.common.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends BaseRepository<Notification> {
   // Page<Notification> findAllByUserAndLanguageEnum(Pageable pageable, User user, LanguageEnum languageEnum);
}
