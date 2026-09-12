package com.dcl.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.dcl.dto.UserDto;
import com.dcl.entity.User;
import com.dcl.exception.AppException;
import com.dcl.repo.UserRepository;
import com.dcl.request.LoginRequest;
import com.dcl.request.RegisterRequest;
import com.dcl.request.UpdateRequest;
import com.dcl.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository urepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public UserDto register(RegisterRequest request) {
		//validating whether the user is existing user or not!
		User alreadyExists=urepo.findByEmail(request.getEmail()).orElse(null);
		if(alreadyExists!=null) {
			throw new AppException("User already exists!", HttpStatus.CONFLICT);
		}
		
		//transfering the data from request to entity
		User user=mapper.map(request, User.class);
		user=urepo.save(user);
		
		//tranfering the data from entity to dto
		UserDto dto=mapper.map(user, UserDto.class);
		return dto;
	}

	@Override
	public UserDto login(LoginRequest request) {
		//Validating the user with email
		User alreadyExists=urepo.findByEmail(request.getEmail()).orElseThrow(()->new RuntimeException("User Not Found!"));
		//Password validation
		if(!alreadyExists.getPassword().equals(request.getPassword())) {
			throw new AppException("Incorrect Password!", HttpStatus.UNAUTHORIZED);
		}
		UserDto dto=mapper.map(alreadyExists, UserDto.class);
		return dto;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User u=urepo.findById(userId).orElse(null);
		return mapper.map(u, UserDto.class);
	}

	@Override
	public List<UserDto> getAll() {
		List<User> userList=urepo.findAll();
		List<UserDto> dtoList=
		userList.stream()
		        .map(u->mapper.map(u, UserDto.class))
		        .collect(Collectors.toList());
		return dtoList;
	}

	@Override
	public UserDto updateUser(Integer userId, UpdateRequest request) {
		User existingUser=urepo.findById(userId).orElse(null);
		if(existingUser==null) {
			throw new RuntimeException("User not found!");
		}
		mapper.map(request, existingUser);
		existingUser=urepo.save(existingUser);
		return mapper.map(existingUser, UserDto.class);
	}

	@Override
	public void deleteUserById(Integer userId) {
		User existingUser=urepo.findById(userId).orElseThrow(()->new RuntimeException("User Not Found!"));
		urepo.deleteById(userId);
	}

}
