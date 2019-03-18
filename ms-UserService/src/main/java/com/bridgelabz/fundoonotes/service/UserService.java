package com.bridgelabz.fundoonotes.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

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

	User store(MultipartFile file, String token) throws IOException;
	
	User deleteImage(String token);
	
	User verifyUser(String emailId);
	
	User getUserById(int user);
	
}
