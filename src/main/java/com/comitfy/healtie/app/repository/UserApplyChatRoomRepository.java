package com.comitfy.healtie.app.repository;

import com.comitfy.healtie.app.dto.UserApplyChatRoomDTO;
import com.comitfy.healtie.app.entity.UserApplyChatRoom;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.util.common.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserApplyChatRoomRepository extends BaseRepository<UserApplyChatRoom> {

    Page<UserApplyChatRoom> findAllByChatRoomUuid(Pageable pageable, UUID chatRoomUuid);
}
