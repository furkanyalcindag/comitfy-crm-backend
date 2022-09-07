package com.comitfy.healtie.app.service;

import com.comitfy.healtie.app.dto.ArticleDTO;
import com.comitfy.healtie.app.dto.requestDTO.AcademicInfoRequestDTO;
import com.comitfy.healtie.app.dto.requestDTO.ArticleLikeRequestDTO;
import com.comitfy.healtie.app.dto.requestDTO.ArticleRequestDTO;
import com.comitfy.healtie.app.dto.requestDTO.ArticleSaveRequestDTO;
import com.comitfy.healtie.app.entity.AcademicInfo;
import com.comitfy.healtie.app.entity.Article;
import com.comitfy.healtie.app.entity.Category;
import com.comitfy.healtie.app.entity.Doctor;
import com.comitfy.healtie.app.mapper.ArticleMapper;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.app.repository.ArticleRepository;
import com.comitfy.healtie.app.repository.CategoryRepository;
import com.comitfy.healtie.app.repository.CommentRepository;
import com.comitfy.healtie.app.repository.DoctorRepository;
import com.comitfy.healtie.app.specification.ArticleSpecification;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.userModule.repository.UserRepository;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseFilterRequestDTO;
import com.comitfy.healtie.util.common.BaseWithMultiLanguageService;
import com.comitfy.healtie.util.common.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


@Service
public class ArticleService extends BaseWithMultiLanguageService<ArticleDTO, ArticleRequestDTO, Article, ArticleRepository, ArticleMapper, ArticleSpecification> {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ArticleSpecification articleSpecification;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    DoctorService doctorService;


    @Autowired
    UserRepository userRepository;

    @Override
    public ArticleRepository getRepository() {
        return articleRepository;
    }

    @Override
    public ArticleMapper getMapper() {
        return articleMapper;
    }

    @Override
    public ArticleSpecification getSpecification() {
        return articleSpecification;
    }



    public PageDTO<ArticleDTO> getArticleByDoctor(UUID id, int page, int size, LanguageEnum languageEnum) {
        Optional<Doctor> doctor = doctorRepository.findByUuid(id);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));

        if (doctor != null) {

            PageDTO<ArticleDTO> pageDTO = getMapper().pageEntityToPageDTO(getRepository().findAllByUserAndLanguageEnum(pageable, doctor.get().getUser(), languageEnum));
            for (int i = 0; i < pageDTO.getData().size(); i++) {

                pageDTO.getData().get(i).setLikeCount(getRepository().getCountOfArticleLike(pageDTO.getData().get(i).getUuid()));
                pageDTO.getData().get(i).setSaveCount(getRepository().getCountOfArticleSave(pageDTO.getData().get(i).getUuid()));
                pageDTO.getData().get(i).setCommentCount(getRepository().getCountOfComment(pageDTO.getData().get(i).getUuid()));
                pageDTO.getData().get(i).setLike(isLikedArticleByUser( pageDTO.getData().get(i).getUuid(), id));
                pageDTO.getData().get(i).setSave(isSavedArticleByUser( pageDTO.getData().get(i).getUuid(), id));
            }


            return pageDTO;
        } else {
            return null;
        }
    }



    public ArticleRequestDTO saveArticleByUser(UUID id, ArticleRequestDTO dto) {
        Optional<User> user = userRepository.findByUuid(id);
        if (user.isPresent()) {
            Article article = getMapper().requestDTOToEntity(dto);
            article.setUser(user.get());
            articleRepository.save(article);
            return dto;
        } else {
            return null;
        }
    }


    public void likeOrDislikeArticle(ArticleLikeRequestDTO articleLikeRequestDTO, Article article, User user) {

        if (articleLikeRequestDTO.isLike()) {
            article.addLike(user);
        } else {
            article.removeLike(user);
        }
        articleRepository.save(article);
    }

    public void saveOrNotSaveArticle(ArticleSaveRequestDTO articleSaveRequestDTO, Article article, User user) {
        if (articleSaveRequestDTO.isSave()) {
            article.addSave(user);
        } else {
            article.removeSave(user);
        }
        articleRepository.save(article);
    }


    public PageDTO<ArticleDTO> getArticleByCategory(UUID id, int page, int size, LanguageEnum languageEnum) {
        Optional<Category> category = categoryRepository.findByUuid(id);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));
        if (category.isPresent()) {
            Set<Category> categorySet = new HashSet<>();
            categorySet.add(category.get());
            return getMapper().pageEntityToPageDTO(articleRepository.findAllByCategoryListInAndLanguageEnum(pageable, categorySet, languageEnum));
        } else {
            return null;
        }
    }

    public PageDTO<ArticleDTO> getSavedArticleByUser(int page, int size, User user,LanguageEnum languageEnum) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));

        PageDTO<ArticleDTO> pageDTO = getMapper().pageEntityToPageDTO(getRepository().getSavedArticleOfUser(pageable, user.getUuid()));

        for (ArticleDTO articleDTO : pageDTO.getData()) {
            articleDTO.setLike(isLikedArticleByUser(articleDTO.getUuid(), user.getUuid()));
            articleDTO.setSave(isSavedArticleByUser(articleDTO.getUuid(), user.getUuid()));
            articleDTO.setCommentCount(getRepository().getCountOfComment(articleDTO.getUuid()));
        }

        return getMapper().pageEntityToPageDTO(articleRepository.getSavedArticleOfUser(pageable, user.getUuid()));

    }

    public PageDTO<ArticleDTO> getLikedArticleByUser(int page, int size, User user,LanguageEnum languageEnum) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));

      //  PageDTO<ArticleDTO> pageDTO = getMapper().pageEntityToPageDTO(getRepository().findAllByUser(pageable, user,languageEnum));
        PageDTO<ArticleDTO> pageDTO = getMapper().pageEntityToPageDTO(getRepository().getLikedArticleOfUser(pageable, user.getUuid()));

        for (ArticleDTO articleDTO : pageDTO.getData()) {
            articleDTO.setLike(isLikedArticleByUser(articleDTO.getUuid(), user.getUuid()));
            articleDTO.setSave(isSavedArticleByUser(articleDTO.getUuid(), user.getUuid()));
            articleDTO.setCommentCount(getRepository().getCountOfComment(articleDTO.getUuid()));
        }
        return getMapper().pageEntityToPageDTO(articleRepository.getLikedArticleOfUser(pageable, user.getUuid()));

    }


    public boolean isLikedArticleByUser(UUID articleUUID, UUID userUUID) {

        if (articleRepository.isLikedByUser(articleUUID, userUUID) > 0) {
            return true;
        } else {
            return false;
        }

    }


    public boolean isSavedArticleByUser(UUID articleUUID, UUID userUUID) {

        if (articleRepository.isSavedByUser(articleUUID, userUUID) > 0) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public PageDTO<ArticleDTO> findAll(BaseFilterRequestDTO filterRequestDTO, LanguageEnum languageEnum) {
        Pageable pageable = PageRequest.of(filterRequestDTO.getPageNumber(), filterRequestDTO.getPageSize(), Sort.by(Sort.Order.desc("id")));

        if (filterRequestDTO.getLanguage() != null) {
            SearchCriteria sc = new SearchCriteria("languageEnum", "=", "", LanguageEnum.valueOf(filterRequestDTO.getLanguage()));
            filterRequestDTO.getFilters().add(sc);
        } else {
            SearchCriteria sc = new SearchCriteria("languageEnum", "=", "", languageEnum);
            filterRequestDTO.getFilters().add(sc);

        }


        getSpecification().setCriterias(filterRequestDTO.getFilters());
        //return getMapper().pageEntityToPageDTO(getRepository().findAllByLanguageEnum(pageable,languageEnum));

        PageDTO<ArticleDTO> pageDTO = getMapper().pageEntityToPageDTO(getRepository().findAll(getSpecification(), pageable));

        if (filterRequestDTO.getRequestUserUUID() != null) {
            for (ArticleDTO articleDTO : pageDTO.getData()) {

                articleDTO.setLike(isLikedArticleByUser(articleDTO.getUuid(), filterRequestDTO.getRequestUserUUID()));
                articleDTO.setSave(isSavedArticleByUser(articleDTO.getUuid(), filterRequestDTO.getRequestUserUUID()));
                articleDTO.setCommentCount(getRepository().getCountOfComment(articleDTO.getUuid()));

            }

        }

        return pageDTO;

    }


}
