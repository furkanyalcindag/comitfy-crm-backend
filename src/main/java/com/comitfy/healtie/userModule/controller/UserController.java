package com.comitfy.healtie.userModule.controller;

import com.comitfy.healtie.app.dto.requestDTO.ArticleRequestDTO;
import com.comitfy.healtie.userModule.dto.RoleDTO;
import com.comitfy.healtie.userModule.dto.UserDTO;
import com.comitfy.healtie.userModule.dto.requestDTO.UserRequestDTO;
import com.comitfy.healtie.userModule.entity.Role;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.userModule.mapper.UserMapper;
import com.comitfy.healtie.userModule.repository.UserRepository;
import com.comitfy.healtie.userModule.service.RoleService;
import com.comitfy.healtie.userModule.service.UserService;
import com.comitfy.healtie.userModule.specification.UserSpecification;
import com.comitfy.healtie.util.common.BaseCrudController;
//import com.comitfy.healtie.util.common.MinioService;
import com.comitfy.healtie.util.common.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

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


/*


    @PostMapping("add-profile-photo")
    public ResponseEntity<String> addProfilePhoto(@RequestParam("photo") MultipartFile file){

        Path path = Path.of(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            minioService.upload(path, file.getInputStream(), file.getContentType());
        } catch (MinioException e) {
            throw new IllegalStateException("The file cannot be upload on the internal storage. Please retry later", e);
        } catch (IOException e) {
            throw new IllegalStateException("The file cannot be read", e);
        }


    }


*/

}
