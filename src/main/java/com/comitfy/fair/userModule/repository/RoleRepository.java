package com.comitfy.fair.userModule.repository;

import com.comitfy.fair.userModule.entity.Role;
import com.comitfy.fair.util.common.BaseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends BaseRepository<Role> {

    Optional<Role> findByName(String name);

    //List<Role> findAllByUsers_Email(String email);

    List<Role> findAllByNameContains(String name, Pageable pageable);

}
