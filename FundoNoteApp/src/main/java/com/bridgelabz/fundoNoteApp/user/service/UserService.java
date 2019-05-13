package com.bridgelabz.fundoNoteApp.user.service;

import com.bridgelabz.fundoNoteApp.user.model.User;

public interface UserService {
	public String login(User user);

	public User userReg(User user);

	public User changeUserPassword(User user);

	public String generateToken(int id);

	public int verifyToken(String secretKey);

	public String securePassword(User user);

	public User saveUser(User user);

	public boolean delete(String token);

	public User update(String token, User user);

}