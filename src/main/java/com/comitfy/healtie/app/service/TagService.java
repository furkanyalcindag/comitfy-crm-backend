package com.comitfy.healtie.app.service;

import com.comitfy.healtie.app.dto.CommentDTO;
import com.comitfy.healtie.app.dto.TagDTO;
import com.comitfy.healtie.app.dto.requestDTO.ArticleRequestDTO;
import com.comitfy.healtie.app.dto.requestDTO.TagRequestDTO;
import com.comitfy.healtie.app.entity.Article;
import com.comitfy.healtie.app.entity.Tag;
import com.comitfy.healtie.app.mapper.TagMapper;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.app.repository.ArticleRepository;
import com.comitfy.healtie.app.repository.TagRepository;
import com.comitfy.healtie.app.specification.TagSpecification;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.userModule.repository.UserRepository;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TagService extends BaseService<TagDTO, TagRequestDTO, Tag, TagRepository, TagMapper, TagSpecification> {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    TagMapper tagMapper;

    @Autowired
    TagSpecification tagSpecification;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Override
    public TagRepository getRepository() {
        return tagRepository;
    }

    @Override
    public TagMapper getMapper() {
        return tagMapper;
    }

    @Override
    public TagSpecification getSpecification() {
        return tagSpecification;
    }

    public TagRequestDTO saveTagByUser(UUID id, TagRequestDTO dto) {
        Optional<User> user = userRepository.findByUuid(id);
        if (user.isPresent()) {
            Tag tag = getMapper().requestDTOToEntity(dto);

            tagRepository.save(tag);
            return dto;
        } else {
            return null;
        }
    }

    public TagRequestDTO updateTag(UUID id, TagRequestDTO dto, User user) {
        Optional<Tag> tag = tagRepository.findByUuid(id);
        if (tag.isPresent()) {
            Tag tag1 = tagMapper.requestDTOToExistEntity(tag.get(), dto);
            tag1.setName(dto.getName());
            tagRepository.save(tag1);

            return dto;
        } else {
            return null;
        }

    }

}
