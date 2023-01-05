package com.comitfy.fair.userModule.service.interfaces;


import com.comitfy.fair.userModule.model.requestModel.auth.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public interface IAuthService {


    boolean registerUser(RegisterRequest request);

}
