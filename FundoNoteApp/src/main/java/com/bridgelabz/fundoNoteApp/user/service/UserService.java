package com.bridgelabz.fundoNoteApp.user.service;

import com.bridgelabz.fundoNoteApp.user.model.User;

public interface UserService {
    public String login(User user);

    public User userReg(User user);

   public String securePassword(String password);

}