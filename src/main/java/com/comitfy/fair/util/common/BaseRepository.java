package com.comitfy.fair.util.common;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface BaseRepository<T> extends PagingAndSortingRepository<T,Long>, JpaSpecificationExecutor<T> {

    Optional<T> findByUuid(UUID uuid);


}
