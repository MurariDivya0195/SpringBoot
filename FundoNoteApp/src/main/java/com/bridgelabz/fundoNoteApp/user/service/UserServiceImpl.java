package com.bridgelabz.fundoNoteApp.user.service;

import java.security.MessageDigest;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoNoteApp.user.model.User;
import com.bridgelabz.fundoNoteApp.user.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	public UserRepository userRep;

	@Override
	public String login(User user) {

		List<User> usrlst = userRep.findByEmailAndPassword(user.getEmail(), user.getPassword());
		System.out.println("SIZE : " + usrlst.size());

		if (usrlst.size() > 0 && usrlst != null) {
			System.out.println("Sucessful login");

			return "Welcome " + usrlst.get(0).getName();
		} else {
			System.out.println("wrong emailid or password");
			return "wrong emailid or password";
		}
	}

	@Override
	public User userReg(User user) {
		return userRep.save(user);
	}

	@Override
	public String securePassword(String password) {
		
		String generatepassword=null;
		try {
			
				//create MessageDigest instance of MD5
			MessageDigest md= MessageDigest.getInstance("MD5");
			
			//Add password bytes to digest
			md.update(password.getBytes());
			
			//Get the hash's bytes
			
			byte[] bytes=md.digest();
			
			
			
		}
		
		
		
		
		return null;
	}


	

}
