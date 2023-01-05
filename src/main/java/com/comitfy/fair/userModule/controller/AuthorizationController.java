package com.comitfy.fair.userModule.controller;


import com.comitfy.fair.userModule.entity.Role;
import com.comitfy.fair.userModule.entity.User;
import com.comitfy.fair.userModule.model.requestModel.auth.LoginRequest;
import com.comitfy.fair.userModule.model.requestModel.auth.RegisterRequest;
import com.comitfy.fair.userModule.service.UserService;
import com.comitfy.fair.userModule.service.interfaces.IAuthService;
import com.comitfy.fair.util.common.HelperService;
import com.comitfy.fair.util.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("auth")
public class AuthorizationController {

    @Autowired
    IAuthService authService;


    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    HelperService helperService;

    @Autowired
    UserService userService;


    @PostMapping("/register")
    public ResponseEntity<String> registerHandler(@RequestBody RegisterRequest user) {


        authService.registerUser(user);

        return new ResponseEntity<String>("Kullanıcı Eklendi", HttpStatus.OK);

    }


    @PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody LoginRequest body) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword());

        authManager.authenticate(authInputToken);

        String token = jwtUtil.generateToken(body.getEmail());

        StringBuilder roles = new StringBuilder();
        User user = userService.getUserByEmail(body.getEmail());
        //List<Role> roleList = authService.getRolesByUser(user);
        for (Role role : user.getRoles()) {

            if (roles.toString().equals("")) {
                roles.append(role.getName());
            } else {
                roles.append(",").append(role.getName());
            }
        }

        Map<String, Object> authorizationMap = new HashMap<>();
        authorizationMap.put("roles", roles);
        authorizationMap.put("jwt-token", token);
        authorizationMap.put("gender", user.getGenderEnum() != null ? user.getGenderEnum().name() : "");
        //Collections.singletonMap("jwt-token", token);

        return authorizationMap;
    }

    /*@GetMapping("required-contract")
    public ResponseEntity<PageDTO<ContractDTO>> getRequiredContract(@RequestHeader(value = "accept-language", required = true) String language,
                                                                    @RequestParam int pageNumber, @RequestParam int pageSize) {
        PageDTO<ContractDTO> dto = authServiceforContract.getRequiredContract(pageNumber, pageSize, LanguageEnum.valueOf(language));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }*/

}
