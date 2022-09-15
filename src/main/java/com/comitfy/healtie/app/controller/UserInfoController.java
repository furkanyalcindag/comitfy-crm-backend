package com.comitfy.healtie.app.controller;

import com.comitfy.healtie.app.dto.requestDTO.DoctorProfileImageRequestDTO;
import com.comitfy.healtie.app.service.UserInfoService;
import com.comitfy.healtie.userModule.dto.UserDTO;
import com.comitfy.healtie.userModule.dto.requestDTO.UserAgeRangeRequestDTO;
import com.comitfy.healtie.userModule.dto.requestDTO.UserGenderRequestDTO;
import com.comitfy.healtie.userModule.dto.requestDTO.UserNameRequestDTO;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.util.common.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user-profile")
public class UserInfoController {

    @Autowired
    HelperService helperService;

    @Autowired
    UserInfoService userService;


    @GetMapping("info")
    public ResponseEntity<UserDTO> getUserInfoFromSession() {

        User user = helperService.getUserFromSession();

        UserDTO userDTO = userService.findByUUID(user.getUuid());

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PutMapping("/gender-update/{id}")
    public ResponseEntity<String> updateGender(@RequestBody UserGenderRequestDTO dto) {
        User user = helperService.getUserFromSession();
        UserDTO userDTO = userService.findByUUID(user.getUuid());
        if (userDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        } else {

            userService.updateGender(user.getUuid(), dto);
            return new ResponseEntity<>("Object with the id " + user.getUuid() + " was updated.", HttpStatus.OK);


        }
    }

    @PutMapping("/age-range-update/{id}")
    public ResponseEntity<String> updateAgeRange(@RequestBody UserAgeRangeRequestDTO dto) {
        User user = helperService.getUserFromSession();
        UserDTO userDTO = userService.findByUUID(user.getUuid());
        if (userDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        } else {

            userService.updateAgeRange(user.getUuid(), dto);
            return new ResponseEntity<>("Object with the id " + user.getUuid() + " was updated.", HttpStatus.OK);


        }
    }

    @PutMapping("/name-update")
    public ResponseEntity<String> updateName(@RequestBody UserNameRequestDTO dto) {
        User user = helperService.getUserFromSession();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        } else {

            userService.updateName(dto,user);
            return new ResponseEntity<>("Object was updated.", HttpStatus.OK);

        }
    }


}
