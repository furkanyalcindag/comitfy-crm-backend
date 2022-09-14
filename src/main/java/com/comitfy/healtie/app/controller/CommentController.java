package com.comitfy.healtie.app.controller;

import com.comitfy.healtie.app.dto.CommentDTO;
import com.comitfy.healtie.app.dto.requestDTO.CommentLikeRequestDTO;
import com.comitfy.healtie.app.dto.requestDTO.CommentRequestDTO;
import com.comitfy.healtie.app.entity.Article;
import com.comitfy.healtie.app.entity.Comment;
import com.comitfy.healtie.app.mapper.CommentMapper;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.app.repository.CommentRepository;
import com.comitfy.healtie.app.service.ArticleService;
import com.comitfy.healtie.app.service.CommentService;
import com.comitfy.healtie.app.specification.CommentSpecification;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseCrudController;
import com.comitfy.healtie.util.common.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("comment")
public class CommentController extends BaseCrudController<CommentDTO, CommentRequestDTO, Comment, CommentRepository, CommentMapper, CommentSpecification, CommentService> {

    @Autowired
    ArticleService articleService;
    @Autowired
    CommentMapper commentMapper;

    @Autowired
    CommentService commentService;

    @Autowired
    HelperService helperService;


    @Override
    protected CommentService getService() {
        return commentService;
    }

    @Override
    protected CommentMapper getMapper() {
        return commentMapper;
    }

    @PostMapping("/{articleId}")
    public ResponseEntity<CommentRequestDTO> save(@RequestHeader(value = "accept-language", required = true) String acceptLanguage,
                                                  @PathVariable UUID articleId, @RequestBody CommentRequestDTO commentRequestDTO) {

        Article article = articleService.findEntityByUUID(articleId);
        User user = helperService.getUserFromSession();
        if (user != null) {
            commentRequestDTO.setLanguageEnum(LanguageEnum.valueOf(commentRequestDTO.getLanguage()));
            return new ResponseEntity<>(commentService.saveCommentByArticle(articleId, commentRequestDTO, article, user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("user-api/like-comment/{commentId}")
    public ResponseEntity<String> likeOrDislikeComment(@RequestHeader(value = "accept-language", required = true) String acceptLanguage,
                                                       @PathVariable UUID commentId, @RequestBody CommentLikeRequestDTO commentLikeRequestDTO) {
        Comment comment = commentService.findEntityByUUID(commentId);
        User user = helperService.getUserFromSession();
        commentLikeRequestDTO.setLanguageEnum(LanguageEnum.valueOf(commentLikeRequestDTO.getLanguage()));
        commentService.likeOrDislikeComment(commentLikeRequestDTO, comment, user);
        return new ResponseEntity<>("Başarılı", HttpStatus.OK);
    }

    @GetMapping("article/{articleId}")
    public ResponseEntity<PageDTO<CommentDTO>> getByArticleId(@RequestHeader(value = "accept-language", required = true) String language,
                                                              @PathVariable UUID articleId, @RequestParam int pageNumber, @RequestParam int pageSize) {
        Optional<Article> optional = articleService.getRepository().findByUuid(articleId);
        if (optional == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(commentService.getCommentByArticle(articleId, pageNumber, pageSize, LanguageEnum.valueOf(language)), HttpStatus.OK);
        }
    }

    @GetMapping("/comment/{commentId}")
    public ResponseEntity<PageDTO<CommentDTO>> getByCommentId(@RequestHeader(value = "accept-language", required = true) String language,
                                                             @PathVariable UUID commentId, @RequestParam int pageNumber, @RequestParam int pageSize) {
        Optional<Comment> optional = commentService.getRepository().findByUuid(commentId);
        if (optional == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(commentService.getCommentByParent(commentId, pageNumber, pageSize), HttpStatus.OK);
        }
    }


}
