package com.dcl.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserDto {
	
	private Integer userId;
	
	private String name;
	
	private String phone;
	
	private String email;
	
	private LocalDateTime createdAt;
}
