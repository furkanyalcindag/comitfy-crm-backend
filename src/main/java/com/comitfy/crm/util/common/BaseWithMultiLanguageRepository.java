package com.comitfy.crm.util.common;

import com.comitfy.crm.app.model.enums.LanguageEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface BaseWithMultiLanguageRepository<T> extends JpaRepository<T,Long>, JpaSpecificationExecutor<T> {

    Optional<T> findByUuid(UUID uuid);
    Page<T> findAllByLanguageEnum(Pageable pageable,LanguageEnum languageEnum);

}
