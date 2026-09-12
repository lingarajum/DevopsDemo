package com.dcl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dcl.dto.UserDto;
import com.dcl.request.LoginRequest;
import com.dcl.request.RegisterRequest;
import com.dcl.request.UpdateRequest;
import com.dcl.response.ApiResponse;
import com.dcl.service.UserService;

@RestController  //@Controller + @ResponseBody
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService uservice;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request){
		UserDto dto=uservice.register(request);
		ApiResponse response=new ApiResponse<>("User created successfully!",dto , HttpStatus.OK);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request){
		UserDto dto=uservice.login(request);
		ApiResponse response=new ApiResponse<>("Login successful!",dto , HttpStatus.OK);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/get/{userId}")
	public ResponseEntity<?> getUserById(@PathVariable Integer userId){
		UserDto dto=uservice.getUserById(userId);
		ApiResponse response=new ApiResponse<>("User Info!",dto , HttpStatus.OK);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/get")
	public ResponseEntity<?> getAllUser(){
		List<UserDto> dtoList=uservice.getAll();
		ApiResponse response=new ApiResponse<>("Users Data!",dtoList , HttpStatus.OK);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<?> deleteById(@PathVariable Integer userId){
		uservice.deleteUserById(userId);
		ApiResponse response=new ApiResponse<>("User created successfully!",null, HttpStatus.OK);
		return ResponseEntity.ok(response);
		
	}
	
	@PutMapping("/update/{userId}")
	public ResponseEntity<?> updateUser(@PathVariable Integer userId,@RequestBody UpdateRequest request){
		UserDto dto=uservice.updateUser(userId, request);
		ApiResponse response=new ApiResponse<>("User updated successfully!",dto , HttpStatus.OK);
		return ResponseEntity.ok(response);
	}
}
