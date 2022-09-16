package com.comitfy.healtie.app.service;

import com.comitfy.healtie.app.dto.CommentDTO;
import com.comitfy.healtie.app.dto.requestDTO.CommentLikeRequestDTO;
import com.comitfy.healtie.app.dto.requestDTO.CommentRequestDTO;
import com.comitfy.healtie.app.entity.Article;
import com.comitfy.healtie.app.entity.Comment;
import com.comitfy.healtie.app.mapper.CommentMapper;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.app.repository.ArticleRepository;
import com.comitfy.healtie.app.repository.CommentRepository;
import com.comitfy.healtie.app.specification.CommentSpecification;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseFilterRequestDTO;
import com.comitfy.healtie.util.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService extends BaseService<CommentDTO, CommentRequestDTO, Comment, CommentRepository, CommentMapper, CommentSpecification> {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    CommentSpecification commentSpecification;

    @Override
    public CommentRepository getRepository() {
        return commentRepository;
    }

    @Override
    public CommentMapper getMapper() {
        return commentMapper;
    }

    @Override
    public CommentSpecification getSpecification() {
        return commentSpecification;
    }


    public CommentRequestDTO saveCommentByArticle(UUID id, CommentRequestDTO dto, Article article, User user) {
        Optional<Article> article1 = articleRepository.findByUuid(id);
        if (article1.isPresent()) {
            Comment comment = getMapper().requestDTOToEntity(dto);
            comment.setArticle(article1.get());
            comment.setUser(user);
            commentRepository.save(comment);
            return dto;
        } else {
            return null;
        }
    }

    public void likeOrDislikeComment(CommentLikeRequestDTO commentLikeRequestDTO, Comment comment, User user) {
        if (commentLikeRequestDTO.isLike()) {
            comment.addLike(user);
        } else {
            comment.removeLike(user);
        }
        commentRepository.save(comment);
    }

    public PageDTO<CommentDTO> getCommentByArticle(UUID id, int page, int size, LanguageEnum languageEnum) {
        Optional<Article> article = articleRepository.findByUuid(id);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));
        if (article.isPresent()) {
            PageDTO<CommentDTO> pageDTO = getMapper().pageEntityToPageDTO(getRepository().findAllByArticleOrderByIdDesc(pageable, article.get()));
            for (int i = 0; i < pageDTO.getData().size(); i++) {
                pageDTO.getData().get(i).setLikeCount(getRepository().getCountOfCommentLike(pageDTO.getData().get(i).getUuid()));
                pageDTO.getData().get(i). setReplyCount(getRepository().getCountOfCommentByParent(pageDTO.getData().get(i).getUuid()));
            }
            return pageDTO;
        } else {
            return null;
        }

    }


    public PageDTO<CommentDTO> getCommentByParent(UUID id, int page, int size) {
        Optional<Comment> parent = commentRepository.findByUuid(id);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));
        if (parent.isPresent()) {
            PageDTO<CommentDTO> pageDTO = getMapper().pageEntityToPageDTO(getRepository().findCommentByParent(pageable, parent.get()));
            for (int i = 0; i < pageDTO.getData().size(); i++) {
                pageDTO.getData().get(i).setLikeCount(getRepository().getCountOfCommentLike(pageDTO.getData().get(i).getUuid()));
                pageDTO.getData().get(i).setReplyCount(getRepository().getCountOfCommentByParent(pageDTO.getData().get(i).getUuid()));
            }
            return pageDTO;

        } else {
            return null;
        }
    }

    public boolean isLikedCommentByUser(UUID commentUUID, UUID userUUID) {

        if (commentRepository.isLikedByUser(commentUUID, userUUID) > 0) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public PageDTO<CommentDTO> findAll(BaseFilterRequestDTO filterRequestDTO, LanguageEnum languageEnum) {
        Pageable pageable = PageRequest.of(filterRequestDTO.getPageNumber(), filterRequestDTO.getPageSize(), Sort.by(Sort.Order.desc("id")));

      /*  if (filterRequestDTO.getLanguage() != null) {
            SearchCriteria sc = new SearchCriteria("languageEnum", "=", "", LanguageEnum.valueOf(filterRequestDTO.getLanguage()));
            filterRequestDTO.getFilters().add(sc);
        } else {
            SearchCriteria sc = new SearchCriteria("languageEnum", "=", "", languageEnum);
            filterRequestDTO.getFilters().add(sc);

        }
*/
        getSpecification().setCriterias(filterRequestDTO.getFilters());
        //return getMapper().pageEntityToPageDTO(getRepository().findAllByLanguageEnum(pageable,languageEnum));

        PageDTO<CommentDTO> pageDTO = getMapper().pageEntityToPageDTO(getRepository().findAll(getSpecification(), pageable));

        if (filterRequestDTO.getRequestUserUUID() != null) {
            for (CommentDTO commentDTO : pageDTO.getData()) {
                commentDTO.setLike(isLikedCommentByUser(commentDTO.getUuid(), filterRequestDTO.getRequestUserUUID()));

            }

        }

        return pageDTO;

    }

}
