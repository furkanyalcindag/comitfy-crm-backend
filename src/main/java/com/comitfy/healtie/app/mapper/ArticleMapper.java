package com.comitfy.healtie.app.mapper;

import com.comitfy.healtie.app.dto.ArticleDTO;
import com.comitfy.healtie.app.dto.requestDTO.ArticleRequestDTO;
import com.comitfy.healtie.app.entity.Article;
import com.comitfy.healtie.app.repository.ArticleRepository;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ArticleMapper implements BaseMapper<ArticleDTO, ArticleRequestDTO, Article> {

    @Autowired
    ArticleRepository articleRepository;

    @Override
    public ArticleDTO entityToDTO(Article entity) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setName(entity.getName());
        articleDTO.setTitle(entity.getTitle());
        articleDTO.setTag(entity.getTag());
        return articleDTO;
    }

    @Override
    public Article dtoToEntity(ArticleDTO dto) {
        Article article = new Article();
        article.setName(dto.getName());
        article.setTitle(dto.getTitle());
        article.setLanguageEnum(dto.getLanguageEnum());
        article.setTag(dto.getTag());
        return article;
    }

    @Override
    public Article requestDTOToEntity(ArticleRequestDTO dto) {
        Article article = new Article();
        article.setName(dto.getName());
        article.setTitle(dto.getTitle());
        article.setLanguageEnum(dto.getLanguageEnum());
        article.setTag(dto.getTag());
        return article;

    }
    @Override
    public Article requestDTOToExistEntity(Article article, ArticleRequestDTO dto) {
        article.setName(dto.getName());
        article.setTitle(dto.getTitle());
        article.setLanguageEnum(dto.getLanguageEnum());
        article.setTag(dto.getTag());
        return article;
    }



    @Override
    public List<Article> dtoListToEntityList(List<ArticleDTO> articleDTOS) {
        List<Article> articleList = new ArrayList<>();
        for (ArticleDTO articleDTO : articleDTOS) {
            Article article = dtoToEntity(articleDTO);
            articleList.add(article);
        }
        return articleList;
    }

    @Override
    public List<ArticleDTO> entityListToDTOList(List<Article> articles) {
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        for (Article article : articles) {
            ArticleDTO articleDTO = entityToDTO(article);
            articleDTOList.add(articleDTO);
        }
        return articleDTOList;
    }

    @Override
    public PageDTO<ArticleDTO> pageEntityToPageDTO(Page<Article> pageEntity) {
        PageDTO<ArticleDTO> pageDTO = new PageDTO<ArticleDTO>();
        List<Article> entityList = pageEntity.toList();
        List<ArticleDTO> articleDTOList = entityListToDTOList(entityList);
        pageDTO.setStart(pageEntity, articleDTOList);

        return pageDTO;
    }
}