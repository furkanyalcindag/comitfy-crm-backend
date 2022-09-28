package com.comitfy.healtie.app.repository;

import com.comitfy.healtie.app.entity.UserApplyChatRoom;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.userModule.entity.Contract;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.util.common.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserApplyChatRoomRepository extends BaseRepository<UserApplyChatRoom> {

    Page<UserApplyChatRoom> findAllByChatRoomUuid(Pageable pageable, UUID chatRoomUuid);

    Page<User> findAllByChatRoomUuidIn(Pageable pageable, Set<UUID> chatRoomSet);

    Page<UserApplyChatRoom> findAllByApproved(Pageable pageable, boolean approved);








}
