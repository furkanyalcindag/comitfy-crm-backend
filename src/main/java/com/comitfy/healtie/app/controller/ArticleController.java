package com.comitfy.healtie.app.controller;

import com.comitfy.healtie.app.dto.ArticleClickDTO;
import com.comitfy.healtie.app.dto.ArticleDTO;
import com.comitfy.healtie.app.dto.DoctorDTO;
import com.comitfy.healtie.app.dto.requestDTO.ArticleLikeRequestDTO;
import com.comitfy.healtie.app.dto.requestDTO.ArticleRequestDTO;
import com.comitfy.healtie.app.dto.requestDTO.ArticleSaveRequestDTO;
import com.comitfy.healtie.app.entity.Article;
import com.comitfy.healtie.app.entity.Doctor;
import com.comitfy.healtie.app.mapper.ArticleMapper;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.app.repository.ArticleRepository;
import com.comitfy.healtie.app.repository.CategoryRepository;
import com.comitfy.healtie.app.repository.DoctorRepository;
import com.comitfy.healtie.app.service.ArticleClickService;
import com.comitfy.healtie.app.service.ArticleService;
import com.comitfy.healtie.app.service.DoctorService;
import com.comitfy.healtie.app.specification.ArticleSpecification;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.userModule.repository.UserRepository;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseWithMultiLanguageCrudController;
import com.comitfy.healtie.util.common.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("article")
public class ArticleController extends BaseWithMultiLanguageCrudController<ArticleDTO, ArticleRequestDTO, Article, ArticleRepository, ArticleMapper, ArticleSpecification, ArticleService> {

    @Autowired
    ArticleService articleService;

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    ArticleClickService articleClickService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    HelperService helperService;

    @Autowired
    UserRepository userRepository;

    @Override
    protected ArticleService getService() {
        return articleService;
    }

    @Override
    protected ArticleMapper getMapper() {
        return articleMapper;
    }

    @GetMapping("doctor/{doctorId}")
    public ResponseEntity<PageDTO<ArticleDTO>> getByDoctorId(@RequestHeader(value = "accept-language", required = true) String language,
                                                             @PathVariable UUID doctorId, @RequestParam int pageNumber, @RequestParam int pageSize) {
        Optional<Doctor> optional = doctorService.getRepository().findByUuid(doctorId);
        if (optional == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(articleService.getArticleByDoctor(doctorId, pageNumber, pageSize, LanguageEnum.valueOf(language)), HttpStatus.OK);
        }
    }

    @GetMapping("get-all-by-category/{categoryId}")
    public ResponseEntity<PageDTO<ArticleDTO>> getByCategoryId(@RequestHeader(value = "accept-language", required = true) String language,
                                                               @PathVariable UUID categoryId, @RequestParam int pageNumber, @RequestParam int pageSize) {
        PageDTO<ArticleDTO> pageDTO = articleService.getArticleByCategory(categoryId, pageNumber, pageSize, LanguageEnum.valueOf(language));

        return new ResponseEntity<>(pageDTO, HttpStatus.OK);

    }


    @PostMapping("/{doctorId}")
    public ResponseEntity<ArticleRequestDTO> saveByDoctorId(@RequestHeader(value = "accept-language", required = true) String acceptLanguage,
                                                            @PathVariable UUID doctorId, @RequestBody ArticleRequestDTO articleRequestDTO) {
        DoctorDTO optional = doctorService.findByUUID(doctorId);
        if (optional == null) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(articleService.saveArticleByDoctor(doctorId, articleRequestDTO), HttpStatus.OK);
        }
    }


    @PostMapping("user-api/like-article/{articleId}")
    public ResponseEntity<String> likeOrDislikeArticle(@RequestHeader(value = "accept-language", required = true) String acceptLanguage,
                                                       @PathVariable UUID articleId, @RequestBody ArticleLikeRequestDTO articleLikeRequestDTO) {
        Article article = articleService.findEntityByUUID(articleId);
        User user = helperService.getUserFromSession();
        articleService.likeOrDislikeArticle(articleLikeRequestDTO, article, user);
        return new ResponseEntity<>("Başarılı", HttpStatus.OK);
    }

    @PostMapping("user-api/save-article/{articleId}")
    public ResponseEntity<String> saveOrNotSaveArticle(@RequestHeader(value = "accept-language", required = true) String acceptLanguage,
                                                       @PathVariable UUID articleId, @RequestBody ArticleSaveRequestDTO articleSaveRequestDTO) {
        Article article = articleService.findEntityByUUID(articleId);
        User user = helperService.getUserFromSession();
        articleService.saveOrNotSaveArticle(articleSaveRequestDTO, article, user);
        return new ResponseEntity<>("Başarılı", HttpStatus.OK);
    }

    @GetMapping("user-api/saved-articles/")
    public ResponseEntity<PageDTO<ArticleDTO>> getSavesByUser(@RequestHeader(value = "accept-language", required = true) String language,
                                                              @RequestParam int pageNumber, @RequestParam int pageSize) {

        User user = helperService.getUserFromSession();
        PageDTO<ArticleDTO> dto = articleService.getSavedArticleByUser(pageNumber, pageSize, user);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("user-api/liked-articles/")
    public ResponseEntity<PageDTO<ArticleDTO>> getLikesByUser(@RequestHeader(value = "accept-language", required = true) String language,
                                                              @RequestParam int pageNumber, @RequestParam int pageSize) {
        User user = helperService.getUserFromSession();
        PageDTO<ArticleDTO> dto = articleService.getLikedArticleByUser(pageNumber, pageSize, user);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

   /* @GetMapping("user-api/clicked-article/")
    public ResponseEntity<ArticleClickDTO> getClickedByUser(@RequestHeader(value = "accept-language", required = true) String language){
        User user=helperService.getUserFromSession();
        if(user==null){

        }
        ArticleClickDTO dto=articleClickService.



               if (entity.getUserSaves() != null) {
            articleDTO.setSaveCount(entity.getUserSaves().size());
        }
    }*/






    /*    @GetMapping("/")
    public ResponseEntity<PageDTO<DTO>> getAll(@RequestHeader(value = "accept-language", required = true) String language, @RequestParam int pageNumber, @RequestParam int pageSize) {

        PageDTO<DTO> dtoList = getService().findAll(pageNumber, pageSize, LanguageEnum.valueOf(language));

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }*/

    /*    @GetMapping("doctor/{doctorId}")
    public ResponseEntity<PageDTO<ArticleDTO>> getByDoctorId(@RequestHeader(value = "accept-language", required = true) String language,
                                                             @PathVariable UUID doctorId, @RequestParam int pageNumber, @RequestParam int pageSize) {
        Optional<Doctor> optional = doctorService.getRepository().findByUuid(doctorId);
        if (optional == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(articleService.getArticleByDoctor(doctorId, pageNumber, pageSize, LanguageEnum.valueOf(language)), HttpStatus.OK);
        }
    }*/
}
