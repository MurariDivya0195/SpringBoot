package com.bridgelabz.fundoNoteApp.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoNoteApp.user.model.User;
import com.bridgelabz.fundoNoteApp.user.service.UserService;
@RestController
@ComponentScan("com.bl.app.service")
public class RegistrationController {
	@Autowired
	UserService userService;

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public User createStudent(@RequestBody User user) {

		return userService.userReg(user);
	}

}
