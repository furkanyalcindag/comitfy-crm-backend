package com.comitfy.healtie.userModule.repository;


import com.comitfy.healtie.app.entity.Article;
import com.comitfy.healtie.app.entity.Category;
import com.comitfy.healtie.app.entity.Comment;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.userModule.entity.Role;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.util.common.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRepository extends BaseRepository<User> {

    Optional<User> findByEmail(String email);


    @Query("SELECT COUNT(article.uuid) from Article article" +
            " inner join article.userLikes likes  where likes.uuid=?1")
    long getLikeCountByUser(UUID userUUID);

    @Query("SELECT COUNT(article.uuid) from Article article" +
            " inner join article.userSaves saves  where saves.uuid=?1")
    long getSaveCountByUser(UUID userUUID);


    @Query("SELECT user FROM User user" +
            " inner join user.roles role WHERE role.uuid=?1")
    Page<User> getUserByRole(Pageable pageable, UUID roleUUID);

/*    @Query("SELECT userApply.userUuid FROM UserApplyChatRoom userApply " +
            " WHERE  userApply.chatRoomUuid =?1")
    Page<User> getUserByChatRoom(Pageable pageable, UUID userApplyUUID);*/






/*    @Query("SELECT userApply.uuid FROM UserApplyChatRoom userApply " +
            " WHERE userApply.chatRoomUuid=?1")
    Page<User> getUserByChatRoom(Pageable pageable, UUID userApplyUUID);*/




}
