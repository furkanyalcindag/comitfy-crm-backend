package com.comitfy.crm.userModule.service.interfaces;


import com.comitfy.crm.userModule.model.requestModel.auth.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public interface IAuthService {


    boolean registerUser(RegisterRequest request);

}
