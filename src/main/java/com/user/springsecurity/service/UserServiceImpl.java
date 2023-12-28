package com.user.springsecurity.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.springsecurity.dto.UserDto;
import com.user.springsecurity.entity.RoleEntity;
import com.user.springsecurity.entity.UserEntity;
import com.user.springsecurity.repository.*;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
   private PasswordEncoder passwordEncoder;
    
    public UserServiceImpl(UserRepository userRepository,
            RoleRepository roleRepository, //) {
            PasswordEncoder passwordEncoder) {
    	this.userRepository = userRepository;
    	this.roleRepository = roleRepository;
    	this.passwordEncoder = passwordEncoder;
    }
    
	 @Override
	    public void saveUser(UserDto userDto) {
	       UserEntity user = new UserEntity();
	        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
	        user.setEmail(userDto.getEmail());
	        // encrypt the password using spring security
	        //user.setPassword(userDto.getPassword());
	        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

	        RoleEntity role = roleRepository.findByName("ROLE_ADMIN");
	        if(role == null){
	            role = checkRoleExist();
	        }
	        user.setRoles(Arrays.asList(role));
	        userRepository.save(user);
	    }
	 
	 //method is used to find existing emailid
	 @Override
	    public UserEntity findUserByEmail(String email) {
	        return userRepository.findByEmail(email);
	    }
	 
	 @Override
	    public List<UserDto> findAllUsers() {
	        List<UserEntity> users = userRepository.findAll();
	        return users.stream()
	                .map((user) -> mapToUserDto(user))
	                .collect(Collectors.toList());
	    }
	 
	 private UserDto mapToUserDto(UserEntity user){
	        UserDto userDto = new UserDto();
	        String[] str = user.getName().split(" ");
	        userDto.setFirstName(str[0]);
	        userDto.setLastName(str[1]);
	        userDto.setEmail(user.getEmail());
	        return userDto;
	    }
	 
	    private RoleEntity checkRoleExist(){
	        RoleEntity role = new RoleEntity();
	        role.setName("ROLE_ADMIN");
	        return roleRepository.save(role);
	    }
}
