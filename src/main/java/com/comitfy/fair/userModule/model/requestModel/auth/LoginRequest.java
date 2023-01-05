package com.comitfy.fair.userModule.model.requestModel.auth;

import lombok.Data;

@Data
public class LoginRequest {
	private String email;
	private String password;
}
