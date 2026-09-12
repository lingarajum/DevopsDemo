package com.dcl.request;

import lombok.Data;

@Data
public class RegisterRequest {

	private String name;
	
	private String phone;
	
	private String email;
	
	private String password;
	
}
