package com.comitfy.healtie.app.repository;

import com.comitfy.healtie.app.entity.ArticleClick;
import com.comitfy.healtie.util.common.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.UUID;

@Repository
public interface ArticleClickRepository extends BaseRepository<ArticleClick> {

    @Query("SELECT COUNT(click) FROM ArticleClick click" +
            " WHERE click.articleUuid=?1 and click.creationDate between ?2 and ?3")
    long getCountOfArticleClickByDate(UUID articleUUID, Date startDate, Date endDate);

}
