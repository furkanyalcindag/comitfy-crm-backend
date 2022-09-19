package com.comitfy.healtie.app.mapper;

import com.comitfy.healtie.app.dto.CategoryDTO;
import com.comitfy.healtie.app.dto.CategoryDTOForArticle;
import com.comitfy.healtie.app.dto.CommentDTO;
import com.comitfy.healtie.app.dto.TagDTO;
import com.comitfy.healtie.app.dto.requestDTO.CommentRequestDTO;
import com.comitfy.healtie.app.entity.Category;
import com.comitfy.healtie.app.entity.Comment;
import com.comitfy.healtie.app.entity.Tag;
import com.comitfy.healtie.app.repository.CommentRepository;
import com.comitfy.healtie.app.service.CommentService;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CommentMapper implements BaseMapper<CommentDTO, CommentRequestDTO, Comment> {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentService commentService;

    @Override
    public CommentDTO entityToDTO(Comment entity) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent(entity.getContent());
        commentDTO.setUuid(entity.getUuid());
        commentDTO.setCreationDateTime(entity.getCreationDate());

        


        if (entity.getUserLikes() != null) {
            commentDTO.setLikeCount(entity.getUserLikes().size());
        }
        if (entity.getUser() != null) {
            commentDTO.setUserName(entity.getUser().getFirstName() + " " + entity.getUser().getLastName());
        }
   /*     if (entity.getParent() == null) {
            commentDTO.setReplyCount(entity.getArticle().getCommentList().size()-1);
        }*/
        return commentDTO;
    }

    @Override
    public Comment dtoToEntity(CommentDTO dto) {
        Comment comment = new Comment();
        comment.setContent(dto.getContent());

        return comment;
    }

    @Override
    public Comment requestDTOToEntity(CommentRequestDTO dto) {

        Comment comment = new Comment();
        comment.setContent(dto.getContent());

        Comment comment1 = commentService.findEntityByUUID(dto.getParentUuid());
        comment.setParent(comment1);

        return comment;
    }

    @Override
    public Comment requestDTOToExistEntity(Comment comment, CommentRequestDTO dto) {
        comment.setContent(dto.getContent());

        Comment comment1 = commentService.findEntityByUUID(dto.getParentUuid());
        comment.setParent(comment1);

        return comment;
    }


    @Override
    public List<Comment> dtoListToEntityList(List<CommentDTO> commentDTOList) {
        List<Comment> commentList = new ArrayList<>();
        for (CommentDTO commentDTO : commentDTOList) {
            Comment comment = dtoToEntity(commentDTO);
            commentList.add(comment);
        }
        return commentList;
    }

    @Override
    public List<CommentDTO> entityListToDTOList(List<Comment> comments) {
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDTO commentDTO = entityToDTO(comment);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

    @Override
    public PageDTO<CommentDTO> pageEntityToPageDTO(Page<Comment> pageEntity) {
        PageDTO<CommentDTO> pageDTO = new PageDTO<CommentDTO>();
        List<Comment> entityList = pageEntity.toList();
        List<CommentDTO> commentDTOList = entityListToDTOList(entityList);
        pageDTO.setStart(pageEntity, commentDTOList);

        return pageDTO;
    }
}
