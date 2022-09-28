package com.comitfy.healtie.userModule.repository;


import com.comitfy.healtie.app.entity.Article;
import com.comitfy.healtie.app.entity.Category;
import com.comitfy.healtie.app.entity.Comment;
import com.comitfy.healtie.app.entity.UserApplyChatRoom;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.userModule.entity.Role;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.util.common.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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


    @Query(value = "SELECT usr.* " +
            " FROM users usr " +
            " INNER JOIN user_apply_chat_room applyChatRoom ON applyChatRoom.user_uuid = usr.uuid" +
            " WHERE applyChatRoom.chat_room_uuid = :roomUUID",

            countQuery = "SELECT COUNT(*) FROM user_apply_chat_room applyChatRoom " +
                    "WHERE applyChatRoom.chat_room_uuid = :roomUUID",
            nativeQuery = true)
    Page<User> getUsersByChatRoom(@Param("roomUUID") String roomUUID, Pageable pageable);

    @Query(value = "SELECT usr.* " +
            " FROM users usr " +
            " INNER JOIN user_apply_chat_room applyChatRoom ON applyChatRoom.user_uuid = usr.uuid" +
            " WHERE applyChatRoom.chat_room_uuid = :roomUUID AND applyChatRoom.approved= true",

            countQuery = "SELECT COUNT(*) FROM user_apply_chat_room applyChatRoom " +
                    "WHERE applyChatRoom.chat_room_uuid = :roomUUID AND applyChatRoom.approved= true",
            nativeQuery = true)
    Page<User> getApprovedUsersByChatRoom(@Param("roomUUID") String roomUUID, Pageable pageable);

    @Query(value = "SELECT usr.* " +
            " FROM users usr " +
            " INNER JOIN user_apply_chat_room applyChatRoom ON applyChatRoom.user_uuid = usr.uuid" +
            " WHERE applyChatRoom.chat_room_uuid = :roomUUID AND applyChatRoom.approved= false",

            countQuery = "SELECT COUNT(*) FROM user_apply_chat_room applyChatRoom " +
                    "WHERE applyChatRoom.chat_room_uuid = :roomUUID AND applyChatRoom.approved= false",
            nativeQuery = true)
    Page<User> getNonApprovedUsersByChatRoom(@Param("roomUUID") String roomUUID, Pageable pageable);



}
