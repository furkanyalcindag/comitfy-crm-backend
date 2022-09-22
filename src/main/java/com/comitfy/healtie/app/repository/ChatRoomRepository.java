package com.comitfy.healtie.app.repository;

import com.comitfy.healtie.app.entity.ChatRoom;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.util.common.BaseWithMultiLanguageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends BaseWithMultiLanguageRepository<ChatRoom> {

    @Query("SELECT c FROM ChatRoom c" +
            " WHERE c.approved = true")
    Page<ChatRoom> getApprovedHighlightsOfUser(Pageable pageable, User user);
}
