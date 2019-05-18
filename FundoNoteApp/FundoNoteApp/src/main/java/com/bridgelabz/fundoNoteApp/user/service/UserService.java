package com.bridgelabz.fundoNoteApp.user.service;

import java.util.Optional;

import com.bridgelabz.fundoNoteApp.user.model.User;

public interface UserService {
	public String login(User user);

	public User userReg(User user);

	//public String generateToken(int id);

	//public int verifyToken(String secretKey);

	public String securePassword(String password);

	public User saveUser(User user);

	public boolean delete(String token);

	public User update(String token, User user);
	
public User getUserInfoByEmail(String email);
	
	

	public Optional<User> findById(int id);
	
	public String sendmail(String subject, User userdetails,String appUrl); 

}