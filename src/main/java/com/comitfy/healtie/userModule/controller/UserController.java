package com.comitfy.healtie.userModule.controller;

import com.comitfy.healtie.app.dto.requestDTO.ArticleRequestDTO;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.userModule.dto.UserDTO;
import com.comitfy.healtie.userModule.dto.requestDTO.UserRequestDTO;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.userModule.mapper.UserMapper;
import com.comitfy.healtie.userModule.repository.UserRepository;
import com.comitfy.healtie.userModule.service.RoleService;
import com.comitfy.healtie.userModule.service.UserService;
import com.comitfy.healtie.userModule.specification.UserSpecification;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseCrudController;
import com.comitfy.healtie.util.common.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("user-api")
public class UserController extends BaseCrudController<UserDTO, UserRequestDTO, User, UserRepository, UserMapper, UserSpecification, UserService> {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleService roleService;

    @Autowired
    HelperService helperService;
   /* @Autowired
    private MinioService minioService;*/

    @Override
    protected UserService getService() {
        return userService;
    }

    @Override
    protected UserMapper getMapper() {
        return userMapper;
    }


    @PostMapping("/{roleId}")
    public ResponseEntity<UserRequestDTO> saveByRole(@RequestHeader(value = "accept-language", required = true) String acceptLanguage,
                                                     @RequestBody UserRequestDTO userRequestDTO, @PathVariable UUID roleId) {
        User user = helperService.getUserFromSession();
        if (user != null) {
            {
                return new ResponseEntity<>(userService.saveUserByRole(roleId, userRequestDTO), HttpStatus.OK);
            }
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @GetMapping("get-all-by-role/{roleId}")
    public ResponseEntity<PageDTO<UserDTO>> getByRoleId(@RequestHeader(value = "accept-language", required = true) String language,
                                                        @PathVariable UUID roleId, @RequestParam int pageNumber, @RequestParam int pageSize) {
        PageDTO<UserDTO> pageDTO = userService.getUserByRole(roleId, pageNumber, pageSize, LanguageEnum.valueOf(language));

        return new ResponseEntity<>(pageDTO, HttpStatus.OK);
    }
    @PostMapping("/user-api")
    public ResponseEntity<UserRequestDTO> saveByUser(@RequestHeader(value = "accept-language", required = true) String acceptLanguage,
                                                          @RequestBody UserRequestDTO userRequestDTO) {
        //UserDTO optional = userService.findByUUID(userId);
        User user = helperService.getUserFromSession();
        if (user != null) {
            {
                userRequestDTO.setLanguageEnum(LanguageEnum.valueOf(userRequestDTO.getLanguage()));
                return new ResponseEntity<>(userService.saveUserByUser(user.getUuid(), userRequestDTO), HttpStatus.OK);
            }
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
