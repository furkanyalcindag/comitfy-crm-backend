package com.comitfy.healtie.app.mapper;

import com.comitfy.healtie.app.dto.SettingsDTO;
import com.comitfy.healtie.app.dto.TagDTO;
import com.comitfy.healtie.app.dto.requestDTO.SettingsRequestDTO;
import com.comitfy.healtie.app.dto.requestDTO.TagRequestDTO;
import com.comitfy.healtie.app.entity.Settings;
import com.comitfy.healtie.app.entity.Tag;
import com.comitfy.healtie.app.repository.ArticleRepository;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagMapper implements BaseMapper<TagDTO, TagRequestDTO, Tag> {

    @Autowired
    ArticleRepository articleRepository;

    @Override
    public TagDTO entityToDTO(Tag entity) {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setName(entity.getName());
        tagDTO.setUuid(entity.getUuid());
        tagDTO.setLanguage(entity.getLanguageEnum().name());
        if (entity.getArticleList() != null) {
            tagDTO.setArticleCount(articleRepository.getCountOfArticleByTag(entity.getUuid()));
        }
        return tagDTO;
    }

    @Override
    public Tag dtoToEntity(TagDTO dto) {
        Tag tag = new Tag();
        tag.setName(dto.getName());
        tag.setUuid(dto.getUuid());
        tag.setLanguageEnum(dto.getLanguageEnum());

        return tag;
    }

    @Override
    public Tag requestDTOToEntity(TagRequestDTO dto) {
        Tag tag = new Tag();
        tag.setName(dto.getName());
        tag.setLanguageEnum(dto.getLanguageEnum());
        return tag;

    }

    @Override
    public Tag requestDTOToExistEntity(Tag entity, TagRequestDTO dto) {
        entity.setName(dto.getName());
        entity.setLanguageEnum(dto.getLanguageEnum());
        return entity;
    }


    @Override
    public List<Tag> dtoListToEntityList(List<TagDTO> tagDTOS) {
        List<Tag> tagList = new ArrayList<>();
        for (TagDTO tagDTO : tagDTOS) {
            Tag tag = dtoToEntity(tagDTO);
            tagList.add(tag);
        }
        return tagList;
    }

    @Override
    public List<TagDTO> entityListToDTOList(List<Tag> tagList) {
        List<TagDTO> tagDTOList = new ArrayList<>();
        for (Tag tag : tagList) {
            TagDTO tagDTO = entityToDTO(tag);
            tagDTOList.add(tagDTO);
        }
        return tagDTOList;
    }

    @Override
    public PageDTO<TagDTO> pageEntityToPageDTO(Page<Tag> pageEntity) {
        PageDTO<TagDTO> pageDTO = new PageDTO<TagDTO>();
        List<Tag> entityList = pageEntity.toList();
        List<TagDTO> tagDTOList = entityListToDTOList(entityList);
        pageDTO.setStart(pageEntity, tagDTOList);
        return pageDTO;
    }
}
