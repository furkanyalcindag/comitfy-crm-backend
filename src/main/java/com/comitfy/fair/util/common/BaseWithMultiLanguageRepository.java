package com.comitfy.fair.util.common;

import com.comitfy.fair.app.model.enums.LanguageEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface BaseWithMultiLanguageRepository<T> extends PagingAndSortingRepository<T,Long> , JpaSpecificationExecutor<T> {

    Optional<T> findByUuid(UUID uuid);
    Page<T> findAllByLanguageEnum(Pageable pageable,LanguageEnum languageEnum);

}
