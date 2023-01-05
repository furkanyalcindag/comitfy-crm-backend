package com.comitfy.fair.userModule.repository;


import com.comitfy.fair.userModule.entity.User;
import com.comitfy.fair.util.common.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends BaseRepository<User> {

    Optional<User> findByEmail(String email);





    @Query("SELECT user FROM User user" +
            " inner join user.roles role WHERE role.uuid=?1")
    Page<User> getUserByRole(Pageable pageable, UUID roleUUID);








}
