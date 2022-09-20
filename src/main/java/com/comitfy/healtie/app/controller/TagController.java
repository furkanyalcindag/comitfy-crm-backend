package com.comitfy.healtie.app.controller;

import com.comitfy.healtie.app.dto.SettingsDTO;
import com.comitfy.healtie.app.dto.TagDTO;
import com.comitfy.healtie.app.dto.requestDTO.SettingsRequestDTO;
import com.comitfy.healtie.app.dto.requestDTO.TagRequestDTO;
import com.comitfy.healtie.app.entity.Tag;
import com.comitfy.healtie.app.mapper.TagMapper;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.app.repository.TagRepository;
import com.comitfy.healtie.app.service.TagService;
import com.comitfy.healtie.app.specification.TagSpecification;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.util.common.BaseCrudController;
import com.comitfy.healtie.util.common.BaseWithMultiLanguageCrudController;
import com.comitfy.healtie.util.common.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("tag")
public class TagController extends BaseWithMultiLanguageCrudController<TagDTO, TagRequestDTO, Tag, TagRepository, TagMapper, TagSpecification, TagService> {

    @Autowired
    TagService tagService;
    @Autowired
    TagMapper tagMapper;

    @Autowired
    HelperService helperService;


    @Override
    protected TagService getService() {
        return tagService;
    }

    @Override
    protected TagMapper getMapper() {
        return tagMapper;
    }

    @PostMapping("/user-api")
    public ResponseEntity<TagRequestDTO> saveByUser(@RequestHeader(value = "accept-language", required = true) String acceptLanguage,
                                                           @RequestBody TagRequestDTO tagRequestDTO) {
        User user = helperService.getUserFromSession();
        if (user != null) {
            {
                tagRequestDTO.setLanguageEnum(LanguageEnum.valueOf(acceptLanguage));
                return new ResponseEntity<>(tagService.saveTagByUser(user.getUuid(), tagRequestDTO), HttpStatus.OK);
            }
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/user-api/{tagId}")
    public ResponseEntity<String> updateTag(@PathVariable UUID tagId, @RequestBody TagRequestDTO dto) {
        User user = helperService.getUserFromSession();
        TagDTO tagDTO=tagService.findByUUID(tagId);

        if (tagDTO == null || user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        } else {
            dto.setLanguageEnum(LanguageEnum.valueOf(tagDTO.getLanguage()));
            tagService.updateTag(tagId, dto, user);
            return new ResponseEntity<>("The object was updated.", HttpStatus.OK);
        }

    }
}
