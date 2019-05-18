package com.bridgelabz.fundoNoteApp.user.controller;

import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoNoteApp.user.model.User;
import com.bridgelabz.fundoNoteApp.user.service.UserService;
import com.bridgelabz.fundoNoteApp.util.TokenClass;

@RestController

public class LoginController {

	@Autowired
	private UserService userService;
	@Autowired
	private JavaMailSender sender;
	String userEmail;

	@Autowired
	private TokenClass tokennum;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String geteUserByLogin(@RequestBody User user) {
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

	@RequestMapping(value = "/updateuser", method = RequestMethod.PUT)
	public String updateuser(@RequestBody User user, HttpServletRequest request) {

		// String token=userService.login(user);
		// String token = request.getHeader("token");

		System.out.println("I am  token at update method :" + request.getHeader("token"));
		userService.update(request.getHeader("token"), user);
		return "User Updated successfully....";

	}

	@RequestMapping(value = "/deleteuser", method = RequestMethod.DELETE)
	public String deleteuser(HttpServletRequest request) {

		System.out.println("I am  token at delete method :" + request.getHeader("token"));
		boolean b = userService.delete(request.getHeader("token"));
		System.out.println("-->" + b);

		return "User Deleted successfully....";

	}

	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public String forgotPassword(@RequestBody User user, HttpServletRequest request, HttpServletResponse response) {
		User userDetails = userService.getUserInfoByEmail(user.getEmail());
		if (userDetails == null) {
			return "We didn't find an account for that e-mail address.";
		} else {

			String token = tokennum.jwtToken(user.getId());
			response.setHeader("token", token);

			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			String appUrl = request.getScheme() + "://" + request.getServerName();
			try {
				helper.setTo(userDetails.getEmail());
				helper.setText("To reset your password, click the link below:\n" + appUrl + "/reset?token=" + token);
				helper.setSubject("Password Reset Request");
			} catch (MessagingException e) {
				e.printStackTrace();
				return "Error while sending mail ..";
			}
			sender.send(message);
			return "Mail Sent Success!";
		}
	}

	@RequestMapping(value = "/reset", method = RequestMethod.PUT)
	public String changePassword(HttpServletRequest request, @RequestBody User user) {
		String token = request.getHeader("token");
		int id = tokennum.parseJWT(token);
		if (id >= 0) {
			Optional<User> userList = userService.findById(id);
			userList.get().setPassword(user.getPassword());
			userService.saveUser(userList.get());
			return "Changed";
		} else
			return "Not changed";
	}

	// CODE FOR ACTIVATION OF EMAIL
	@RequestMapping(value = "/mail", method = RequestMethod.POST)
	public String mailForActivation(HttpServletRequest request) {
		String token = request.getHeader("token");
		int userId = tokennum.parseJWT(token);
		Optional<User> list = userService.findById(userId);
		if (list == null) {
			return "We didn't find an account for that e-mail address.";
		} else {
			User userdetails = list.get();

			String appUrl = "http://localhost:8080" + "/active/token=" + token;
			String subject = "To active your status";
			return userService.sendmail(subject, userdetails, appUrl);
		}

	}

	@RequestMapping(value = "/active", method = RequestMethod.PUT)
	public String activeStatus(HttpServletRequest request) {
		String token = request.getHeader("token");

		int id = tokennum.parseJWT(token);
		if (id >= 0) {
			Optional<User> userList = userService.findById(id);
			userList.get().setStatus(1);
			userService.saveUser(userList.get());
			return "Changed";
		} else
			return "Not changed";
	}

}