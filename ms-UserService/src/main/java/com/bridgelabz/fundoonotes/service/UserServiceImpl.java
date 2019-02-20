package com.bridgelabz.fundoonotes.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.utility.EmailSender;
import com.bridgelabz.fundoonotes.utility.TokenGenerator;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenGenerator tokenGenerator;

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	private EmailSender emailSender;

	@Override
	public boolean register(User user, HttpServletRequest request) {
		String token;
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		User newUser=userRepository.save(user);
		if (newUser!=null) {
			token = tokenGenerator.generateToken(String.valueOf(newUser.getId()));
			StringBuffer requestUrl = request.getRequestURL();
			String activationUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/"));
			activationUrl = activationUrl + "/activationstatus/" + token;
			emailSender.sendEmail("", "", activationUrl);
			return true;
		}
		return false;
	}

	@Override
	public User login(User user, HttpServletRequest request, HttpServletResponse response) {
		User existingUser = userRepository.findByEmailId(user.getEmailId());
		String token = null;
		boolean isMatch;
		if (existingUser != null) {
			isMatch = bcryptEncoder.matches(user.getPassword(), existingUser.getPassword());
			if (isMatch && user.isActivationStatus()) {
				token = tokenGenerator.generateToken(String.valueOf(existingUser.getId()));
				response.setHeader("token", token);
			}
		}
		return existingUser;
	}

	@Override
	public User update(String token, User user, HttpServletRequest request) {
		int id = tokenGenerator.verifyToken(token);
		Optional<User> optionalUser = userRepository.findById(id);
		User newUser = optionalUser.get();
		if (newUser != null) {
			newUser.setEmailId(user.getEmailId());
			newUser.setName(user.getName());
			newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
			newUser.setMobileNumber(user.getMobileNumber());
			userRepository.save(newUser);
		}
		return newUser;
	}

	@Override
	public boolean delete(String token, HttpServletRequest request) {
		int id = tokenGenerator.verifyToken(token);
		Optional<User> optionalUser = userRepository.findById(id);
		User existingUser = optionalUser.get();
		if(existingUser != null) {
			userRepository.delete(existingUser);
			return true;
		}
		return false;
	}

	@Override
	public User activationStatus(String token, HttpServletRequest request) {
		int id = tokenGenerator.verifyToken(token);
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setActivationStatus(true);
			userRepository.save(user);
			return user;
		}
		return null;
	}

	@Override
	public boolean forgotPassword(String emailId, HttpServletRequest request) {
		User existingUser = userRepository.findByEmailId(emailId);
		if (existingUser != null) {
			String token = tokenGenerator.generateToken(String.valueOf(existingUser.getId()));
			StringBuffer requestUrl = request.getRequestURL();
			String forgotPasswordUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/"));
			forgotPasswordUrl = forgotPasswordUrl + "/resetpassword/" + token;
			emailSender.sendEmail("", "", forgotPasswordUrl);
			return true;
		}
		return false;
	}

	@Override
	public User resetPassword(User user, String token, HttpServletRequest request) {
		int id = tokenGenerator.verifyToken(token);
		Optional<User> optionalUser = userRepository.findById(id);
		User existingUser = optionalUser.get();
		if (existingUser != null) {
			existingUser.setPassword(bcryptEncoder.encode(user.getPassword()));
			userRepository.save(existingUser);
			return existingUser;
		}
		return null;
	}

}
