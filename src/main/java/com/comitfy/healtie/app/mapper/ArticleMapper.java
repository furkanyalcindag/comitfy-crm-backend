package com.comitfy.healtie.app.mapper;

import com.comitfy.healtie.app.dto.ArticleDTO;
import com.comitfy.healtie.app.dto.CategoryDTO;
import com.comitfy.healtie.app.dto.CategoryDTOForArticle;
import com.comitfy.healtie.app.dto.TagDTO;
import com.comitfy.healtie.app.dto.requestDTO.ArticleRequestDTO;
import com.comitfy.healtie.app.entity.Article;
import com.comitfy.healtie.app.entity.Category;
import com.comitfy.healtie.app.entity.Tag;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.app.repository.*;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@Transactional
public class ArticleMapper implements BaseMapper<ArticleDTO, ArticleRequestDTO, Article> {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    TagRepository tagRepository;

    @Override
    public ArticleDTO entityToDTO(Article entity) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setDescription(entity.getDescription());
        articleDTO.setTitle(entity.getTitle());
        articleDTO.setPublishedDate(entity.getPublishedDate());

        ;
        // articleDTO.setAuthor(entity.getAuthor());
        if (entity.getUserSaves() != null) {
            articleDTO.setSaveCount(entity.getUserSaves().size());
        }

        if (entity.getUserLikes() != null) {
            articleDTO.setLikeCount(entity.getUserLikes().size());
        }

        if (entity.getUser() != null) {
            articleDTO.setAuthor(entity.getUser().getFirstName() + " " + entity.getUser().getLastName());
        }
        if (entity.getCommentList() != null) {
            articleDTO.setCommentCount(articleRepository.getCountOfComment(entity.getUuid()));
        }


        Set<TagDTO> tagDTOS = new HashSet<>();
        for (Tag tag : entity.getTags()) {

            TagDTO tagDTO = new TagDTO();
            tagDTO.setName(tag.getName());
            tagDTO.setUuid(tag.getUuid());
            tagDTOS.add(tagDTO);
        }

        articleDTO.setTags(tagDTOS);

        Set<CategoryDTOForArticle> categoryDTOS=new HashSet<>();
        for (Category category:entity.getCategoryList())
        {
            CategoryDTOForArticle categoryDTO=new CategoryDTOForArticle();
            categoryDTO.setName(category.getName());
            categoryDTO.setUuid(category.getUuid());
            categoryDTOS.add(categoryDTO);
        }
        articleDTO.setCategoryListForArticle(categoryDTOS);

        articleDTO.setUuid(entity.getUuid());
        articleDTO.setLanguage(entity.getLanguageEnum().name());
        return articleDTO;
    }


    @Override
    public Article dtoToEntity(ArticleDTO dto) {
        Article article = new Article();
        article.setDescription(dto.getDescription());
        article.setTitle(dto.getTitle());
        article.setPublishedDate(dto.getPublishedDate());
        article.setLanguageEnum(LanguageEnum.valueOf(dto.getLanguage()));
        article.setLanguageEnum(dto.getLanguageEnum());
        Set<Tag> tags = new HashSet<>();
        for (TagDTO tagDTO : dto.getTags()) {

            Tag tag = new Tag();
            tag.setName(tagDTO.getName());
            tags.add(tag);
        }
        article.setTags(tags);

        return article;
    }

    @Override
    public Article requestDTOToEntity(ArticleRequestDTO dto) {
        Article article = new Article();
        article.setDescription(dto.getDescription());
        article.setTitle(dto.getTitle());
        article.setPublishedDate(dto.getPublishedDate());
        //article.setLanguageEnum(dto.getLanguageEnum());
        article.setLanguageEnum(LanguageEnum.valueOf(dto.getLanguage()));

        Set<Tag> tags = new HashSet<>();
        for (TagDTO tagDTO : dto.getTags()) {

            Optional<Tag> optional = tagRepository.findByNameEquals(tagDTO.getName());
            Tag tag;
            if (optional.isPresent()) {
                tag = optional.get();
            } else {
                tag = new Tag();
                tag.setName(tagDTO.getName());
                tagRepository.save(tag);
            }
            tags.add(tag);
        }

        article.setTags(tags);
        article.setCategoryList(new HashSet<>());
        for (UUID uuid : dto.getCategoryList()) {
            Optional<Category> category1 = categoryRepository.findByUuid(uuid);

            category1.ifPresent(value -> article.getCategoryList().add(value));
        }

        return article;
    }

    @Override
    public Article requestDTOToExistEntity(Article article, ArticleRequestDTO dto) {
        article.setDescription(dto.getDescription());
        article.setTitle(dto.getTitle());
        article.setPublishedDate(dto.getPublishedDate());
       // article.setLanguageEnum(dto.getLanguageEnum());
        article.setLanguageEnum(LanguageEnum.valueOf(dto.getLanguage()));
        Set<Tag> tags = new HashSet<>();
        for (TagDTO tagDTO : dto.getTags()) {

            Optional<Tag> optional = tagRepository.findByNameEquals(tagDTO.getName());
            Tag tag;
            if (optional.isPresent()) {
                tag = optional.get();
            } else {
                tag = new Tag();
                tag.setName(tagDTO.getName());
            }
            tags.add(tag);
        }

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
