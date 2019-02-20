package com.bridgelabz.fundoonotes.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bridgelabz.fundoonotes.model.User;

public interface UserService {
	boolean register(User user, HttpServletRequest request);

	User login(User user, HttpServletRequest request, HttpServletResponse response);

	User update(String token, User user, HttpServletRequest request);

	boolean delete(String token, HttpServletRequest request);

	User activationStatus(String token, HttpServletRequest request);

	boolean forgotPassword(String emailId, HttpServletRequest request);
	
	User resetPassword(User user,String token,HttpServletRequest request);
}
