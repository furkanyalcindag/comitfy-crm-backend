package com.comitfy.healtie.app.repository;

import com.comitfy.healtie.app.entity.Article;
import com.comitfy.healtie.app.entity.Comment;
import com.comitfy.healtie.util.common.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommentRepository extends BaseRepository<Comment> {

    Page<Comment> findAllByArticleAndParentIsNull(Pageable pageable, Article article);

    Page<Comment> findCommentByParent(Pageable pageable, Comment parent);

    @Query("SELECT COUNT(likes) FROM Comment comment " +
            "inner join comment.userLikes likes  WHERE comment.uuid=?1")
    long getCountOfCommentLike(UUID commentUUID);

    @Query("SELECT COUNT(likes) FROM  Comment  comment" +
            " inner join comment.userLikes likes WHERE comment.uuid=?1 and likes.uuid=?2")
    long isLikedByUser(UUID commentUUID, UUID userUUID);

    @Query("SELECT COUNT(comments) FROM Comment  comment" +
            " inner join comment.parent comments WHERE comments.uuid=?1")
    long getCountOfCommentByParent(UUID commentUUID);


}
