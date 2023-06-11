package com.comitfy.crm.userModule.controller;

import com.comitfy.crm.app.model.enums.LanguageEnum;
import com.comitfy.crm.userModule.dto.UserDTO;
import com.comitfy.crm.userModule.dto.requestDTO.UserRequestDTO;
import com.comitfy.crm.userModule.entity.User;
import com.comitfy.crm.userModule.mapper.UserMapper;
import com.comitfy.crm.userModule.repository.UserRepository;
import com.comitfy.crm.userModule.service.RoleService;
import com.comitfy.crm.userModule.service.UserService;
import com.comitfy.crm.userModule.specification.UserSpecification;
import com.comitfy.crm.util.PageDTO;
import com.comitfy.crm.util.common.BaseCrudController;
import com.comitfy.crm.util.common.HelperService;
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
    public ResponseEntity<UserRequestDTO> saveByRole(@RequestBody UserRequestDTO userRequestDTO, @PathVariable UUID roleId) {
        return new ResponseEntity<>(userService.saveUserByRole(roleId, userRequestDTO), HttpStatus.OK);
    }


    @GetMapping("get-all-by-role/{roleId}")
    public ResponseEntity<PageDTO<UserDTO>> getByRoleId(
            @PathVariable UUID roleId, @RequestParam int pageNumber, @RequestParam int pageSize) {
        PageDTO<UserDTO> pageDTO = userService.getUserByRole(roleId, pageNumber, pageSize);

        return new ResponseEntity<>(pageDTO, HttpStatus.OK);
    }

    @PostMapping("/user-api")
    public ResponseEntity<UserRequestDTO> saveByUser(@RequestHeader(value = "accept-language", required = true) String acceptLanguage,
                                                     @RequestBody UserRequestDTO userRequestDTO) {
        //UserDTO optional = userService.findByUUID(userId);
        User user = helperService.getUserFromSession();
        if (user != null) {
            {

                return new ResponseEntity<>(userService.saveUserByUser(user.getUuid(), userRequestDTO), HttpStatus.OK);
            }
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
