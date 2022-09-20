package com.comitfy.healtie.app.service;

import com.comitfy.healtie.app.dto.CategoryDTO;
import com.comitfy.healtie.app.dto.requestDTO.ArticleRequestDTO;
import com.comitfy.healtie.app.dto.requestDTO.CategoryRequestDTO;
import com.comitfy.healtie.app.dto.requestDTO.DoctorBranchRequestDTO;
import com.comitfy.healtie.app.entity.Article;
import com.comitfy.healtie.app.entity.Category;
import com.comitfy.healtie.app.entity.Doctor;
import com.comitfy.healtie.app.mapper.CategoryMapper;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.app.repository.ArticleRepository;
import com.comitfy.healtie.app.repository.CategoryRepository;
import com.comitfy.healtie.app.specification.CategorySpecification;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.userModule.repository.UserRepository;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseWithMultiLanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService extends BaseWithMultiLanguageService<CategoryDTO, CategoryRequestDTO, Category, CategoryRepository, CategoryMapper, CategorySpecification> {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    CategorySpecification categorySpecification;

    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public CategoryRepository getRepository() {
        return categoryRepository;
    }

    @Override
    public CategoryMapper getMapper() {
        return categoryMapper;
    }

    @Override
    public CategorySpecification getSpecification() {
        return categorySpecification;
    }

    public PageDTO<CategoryDTO> getCategoryById(UUID id, int page, int size, LanguageEnum languageEnum) {
        Optional<Category> category = categoryRepository.findByUuid(id);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));
        if (category.isPresent()) {
            PageDTO<CategoryDTO> pageDTO = getMapper().pageEntityToPageDTO(getRepository().findAll(pageable));
            for (int i = 0; i < pageDTO.getData().size(); i++) {
                pageDTO.getData().get(i).setArticleCount(articleRepository.getCountOfArticleByCategory(category.get().getUuid()));
            }
            return pageDTO;
        } else {
            return null;
        }

    }

    public CategoryRequestDTO updateCategory(UUID id, CategoryRequestDTO dto, User user) {
        Optional<Category> category = categoryRepository.findByUuid(id);
        if (category.isPresent()) {
            Category category1 = categoryMapper.requestDTOToExistEntity(category.get(), dto);
         //   category1.setLanguageEnum(dto.getLanguageEnum());
            category1.setName(dto.getName());
            category1.setTop(dto.isTop());
            categoryRepository.save(category1);
            return dto;
        } else {
            return null;
        }

    }

public CategoryDTO saveCategoryByUser(User user, CategoryRequestDTO dto,LanguageEnum languageEnum) {

    if (user!=null) {
        Category category = getMapper().requestDTOToEntity(dto);
        categoryRepository.save(category);
        return getMapper().entityToDTO(category);
    } else {
        return null;
    }
}

}
