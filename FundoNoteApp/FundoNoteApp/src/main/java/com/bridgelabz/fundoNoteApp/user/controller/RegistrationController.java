package com.bridgelabz.fundoNoteApp.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoNoteApp.user.model.User;
import com.bridgelabz.fundoNoteApp.user.repository.UserRepository;
import com.bridgelabz.fundoNoteApp.user.service.UserService;

@RestController
@ComponentScan("com.bridgelabz.fundoNoteApp")
public class RegistrationController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public User createStudent(@RequestBody User user) {
		System.out.println("In reg Controller");
		return userService.userReg(user);
	}

	@RequestMapping(value="/fetchdata", method=RequestMethod.GET)
	public List<User> fetchData() {
	
		List<User> userdetails=userRepository.findAll();
		return userdetails;
		
	
		
		
	}
	
	
}
