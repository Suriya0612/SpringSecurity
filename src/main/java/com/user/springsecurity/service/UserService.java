package com.user.springsecurity.service;

import java.util.List;
import com.user.springsecurity.dto.UserDto;
import com.user.springsecurity.entity.UserEntity;

public interface UserService {

	void saveUser(UserDto userDto);
	
	UserEntity findUserByEmail(String email);
	
	List<UserDto> findAllUsers();
}
