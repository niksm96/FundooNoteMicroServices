package com.bridgelabz.fundoonotes.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bridgelabz.fundoonotes.model.User;

public interface UserService {
	boolean register(User user, HttpServletRequest request);

	String login(User user);

	User update(String token, User user);

	boolean delete(String token);

	User activationStatus(String token);

	boolean forgotPassword(User user, HttpServletRequest request);
	
	User resetPassword(User user,String token);
	
	List<User> retrieveUsers();
	
	User userDetail(String token);
	
}
