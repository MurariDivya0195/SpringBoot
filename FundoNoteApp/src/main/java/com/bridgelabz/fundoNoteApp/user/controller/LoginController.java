package com.bridgelabz.fundoNoteApp.user.controller;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoNoteApp.user.model.User;
import com.bridgelabz.fundoNoteApp.user.service.UserService;

@RestController
@ComponentScan("com.bl.app.user.service")
public class LoginController {

	@Autowired
	UserService userService;
	@Autowired
	private JavaMailSender sender;
	String userEmail;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String geteUserByLogin(@RequestBody User user) {
		userEmail = user.getEmail();
		return userService.login(user);
	}

	@RequestMapping(value = "/sendMail", method = RequestMethod.POST)
	public String sendMail(@RequestBody User user) {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {
			helper.setTo(user.getEmail());
			helper.setText("Greetings :)");
			helper.setSubject("Mail From Spring Boot");
		} catch (MessagingException e) {
			e.printStackTrace();
			return "Error while sending mail ..";
		}
		sender.send(message);
		return "Mail Sent Success!";
	}
}