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

    Page<Comment> findAllByArticle(Pageable pageable, Article article);

    @Query("SELECT COUNT(likes) FROM Comment comment " +
            "inner join comment.userLikes likes  WHERE comment.uuid=?1")
    long getCountOfCommentLike(UUID commentUUID);

    @Query("SELECT COUNT(likes) FROM  Comment  comment" +
            " inner join comment.userLikes likes WHERE comment.uuid=?1 and likes.uuid=?2")
    long isLikedByUser(UUID commentUUID, UUID userUUID);


}
