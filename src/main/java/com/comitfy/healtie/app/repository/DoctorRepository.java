package com.comitfy.healtie.app.repository;

import com.comitfy.healtie.app.entity.Doctor;
import com.comitfy.healtie.commercial.model.enums.PaymentStatusEnum;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.util.common.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoctorRepository extends BaseRepository<Doctor> {


    Optional<Doctor> findByUser(User user);

    @Query("SELECT COUNT(likes) from Article article" +
            " inner join article.userLikes likes  inner join article.user user where user.uuid=?1 ")
    long getCountOfArticleLikes(UUID userUUID);


    @Query("SELECT COUNT(likes) from Article article" +
            " inner join article.userLikes likes  inner join article.user user where user.uuid=?1 and article.creationDate between ?2 and ?3")
    long getCountOfArticleLikesByDate(UUID userUUID, Date startDate, Date endDate);


    @Query("SELECT COUNT(saves) from Article article" +
            " inner join article.userSaves saves inner join article.user user where user.uuid=?1 and article.creationDate between ?2 and ?3")
    long getCountOfArticleSavesByDate(UUID userUUID, Date startDate, Date endDate);



}
