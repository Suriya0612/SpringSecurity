package com.user.springsecurity.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.user.springsecurity.dto.UserDto;
import com.user.springsecurity.entity.UserEntity;
import com.user.springsecurity.service.UserService;

@Controller
@RequestMapping
public class AuthController {
	
	private UserService userService;
	
	public AuthController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("hello")
	public String hello(){
        return "hello!";
    }
	
	@GetMapping("/index")
	public String home(){
        return "index";
    }

	   @GetMapping("/register")
	    public String showRegistrationForm(Model model){
	        // create model object to store form data
	        UserDto user = new UserDto();
	       	model.addAttribute("user", user);
	        return "register";
	    }
	   
	   @PostMapping("/register/save")
	    public String registration(@ModelAttribute("user") UserDto userDto,
	                               BindingResult result,
	                               Model model){
	        UserEntity existingUser = userService.findUserByEmail(userDto.getEmail());

	        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
	            result.rejectValue("email", null,
	                    "There is already an account registered with the same email");
	        }

	        if(result.hasErrors()){
	            model.addAttribute("user", userDto);
	            return "/register";
	        }

	        userService.saveUser(userDto);
	        return "redirect:/register?success";
	    }
	   
	   @GetMapping("/login")
	    public String login(){
	        return "login";
	    }
	
	   @GetMapping("/users")
	    public String users(Model model){
	        List<UserDto> users = userService.findAllUsers();
	        model.addAttribute("users", users);
	        return "users";
	    }
}
