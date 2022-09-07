package com.comitfy.healtie.app.mapper;

import com.comitfy.healtie.app.dto.CategoryDTO;
import com.comitfy.healtie.app.dto.TagDTO;
import com.comitfy.healtie.app.dto.requestDTO.CategoryRequestDTO;
import com.comitfy.healtie.app.entity.Category;
import com.comitfy.healtie.app.entity.Tag;
import com.comitfy.healtie.app.repository.ArticleRepository;
import com.comitfy.healtie.app.repository.CategoryRepository;
import com.comitfy.healtie.app.service.CategoryService;
import com.comitfy.healtie.userModule.dto.RoleDTO;
import com.comitfy.healtie.userModule.entity.Role;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CategoryMapper implements BaseMapper<CategoryDTO, CategoryRequestDTO, Category> {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ArticleRepository articleRepository;

    @Override
    public CategoryDTO entityToDTO(Category entity) {

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(entity.getName());
        categoryDTO.setUuid(entity.getUuid());
        categoryDTO.setLanguage(entity.getLanguageEnum().name());
        //categoryDTO.setParentList(entity.getParent());
    /*    if (entity.getArticleList() != null) {
            categoryDTO.setArticleCount(entity.getArticleList().size());
        }*/

        Set<CategoryDTO> categoryDTOS = new HashSet<>();
        for (Category category : entity.getParent()) {

            CategoryDTO categoryDTO1 = new CategoryDTO();
            categoryDTO1.setName(category.getName());
            categoryDTO1.setUuid(category.getUuid());
            categoryDTOS.add(categoryDTO1);

        }
        categoryDTO.setParentList(categoryDTOS);


        if (entity.getArticleList() != null) {
            categoryDTO.setArticleCount(articleRepository.getCountOfArticleByCategory(entity.getUuid()));
        }

        return categoryDTO;
    }

    @Override
    public Category dtoToEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setLanguageEnum(dto.getLanguageEnum());
        category.setUuid(dto.getUuid());
        for (CategoryDTO categoryDTO : dto.getParentList()) {
            Category category1 = new Category();
            category1.setName(categoryDTO.getName());
            category1.setLanguageEnum(categoryDTO.getLanguageEnum());
            category.getParent().add(category1);
        }

        return category;
    }

    @Override
    public Category requestDTOToEntity(CategoryRequestDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setLanguageEnum(dto.getLanguageEnum());

        Set<Category> parentList = new HashSet<>();
        category.setParent(parentList);
        for (UUID uuid : dto.getParentList()) {
            Optional<Category> category1 = categoryRepository.findByUuid(uuid);

            category1.ifPresent(value -> category.getParent().add(value));
        }

        return category;
    }

    @Override
    public Category requestDTOToExistEntity(Category category, CategoryRequestDTO dto) {
        category.setName(dto.getName());
        category.setLanguageEnum(dto.getLanguageEnum());
        for (UUID úuid : dto.getParentList()) {
            Optional<Category> category1 = categoryRepository.findByUuid(úuid);

            category1.ifPresent(value -> category.getParent().add(value));
        }

        return category;
    }

    @Override
    public List<Category> dtoListToEntityList(List<CategoryDTO> categoryDTOList) {
        List<Category> categoryList = new ArrayList<>();
        for (CategoryDTO categoryDTO : categoryDTOList) {
            Category category = dtoToEntity(categoryDTO);
            categoryList.add(category);
        }

        return categoryList;
    }


    @Override
    public List<CategoryDTO> entityListToDTOList(List<Category> categories) {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (Category category : categories) {
            CategoryDTO categoryDTO = entityToDTO(category);
            categoryDTOList.add(categoryDTO);
        }

        return categoryDTOList;
    }

    @Override
    public PageDTO<CategoryDTO> pageEntityToPageDTO(Page<Category> pageEntity) {

        PageDTO<CategoryDTO> pageDTO = new PageDTO<CategoryDTO>();
        List<Category> entityList = pageEntity.toList();
        List<CategoryDTO> categoryDTOList = entityListToDTOList(entityList);
        pageDTO.setStart(pageEntity, categoryDTOList);

        return pageDTO;

    }
}
