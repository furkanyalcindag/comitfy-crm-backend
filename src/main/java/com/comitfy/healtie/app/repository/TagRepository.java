package com.comitfy.healtie.app.repository;

import com.comitfy.healtie.app.entity.Article;
import com.comitfy.healtie.app.entity.Comment;
import com.comitfy.healtie.app.entity.Tag;
import com.comitfy.healtie.util.common.BaseRepository;
import com.comitfy.healtie.util.common.BaseWithMultiLanguageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TagRepository extends BaseWithMultiLanguageRepository<Tag> {

    Optional<Tag> findByNameEquals(String name);


}
