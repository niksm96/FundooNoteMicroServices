package com.bridgelabz.fundoonotes.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.utility.EmailSender;
import com.bridgelabz.fundoonotes.utility.TokenGenerator;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

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
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		User newUser = userRepository.save(user);
		if (newUser != null) {
			String token = tokenGenerator.generateToken(String.valueOf(newUser.getId()));
			StringBuffer requestUrl = request.getRequestURL();
			String activationUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/"));
			activationUrl = activationUrl + "/activationstatus/" + token;
			emailSender.sendEmail("", "", activationUrl);
			return true;
		}
		return false;
	}

	@Override
	public String login(User user) {
		User existingUser = userRepository.findByEmailId(user.getEmailId());
		logger.debug("user", existingUser);
		if (existingUser != null) {
			if (bcryptEncoder.matches(user.getPassword(), existingUser.getPassword())
					&& existingUser.isActivationStatus())
				return tokenGenerator.generateToken(String.valueOf(existingUser.getId()));
		}
		return null;
	}

	@Override
	public User update(String token, User user) {
		Optional<User> optionalUser = userRepository.findById(tokenGenerator.verifyToken(token));
		return optionalUser
				.map(existingUser -> userRepository
						.save(existingUser.setName(user.getName()).setEmailId(user.getEmailId())
								.setPassword(user.getPassword()).setMobileNumber(user.getMobileNumber())))
				.orElseGet(() -> null);
	}

	@Override
	public boolean delete(String token) {
		Optional<User> optionalUser = userRepository.findById(tokenGenerator.verifyToken(token));
		return optionalUser.map(existingUser -> {
			userRepository.delete(existingUser);
			return true;
		}).orElseGet(() -> false);
	}

	@Override
	public User activationStatus(String token) {
		Optional<User> optionalUser = userRepository.findById(tokenGenerator.verifyToken(token));
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setActivationStatus(true);
			userRepository.save(user);
			return user;
		}
		return null;
	}

	@Override
	public boolean forgotPassword(User user, HttpServletRequest request) {
		User existingUser = userRepository.findByEmailId(user.getEmailId());
		if (existingUser != null) {
			String token = tokenGenerator.generateToken(String.valueOf(existingUser.getId()));
//			StringBuffer requestUrl = request.getRequestURL();
//			String forgotPasswordUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/"));
//			forgotPasswordUrl = forgotPasswordUrl + "/resetpassword/" + token;
			String forgotPasswordUrl = "http://localhost:4200/resetpassword/" + token;
			emailSender.sendEmail("", "", forgotPasswordUrl);
			return true;
		}
		return false;
	}

	@Override
	public User resetPassword(User user, String token) {
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

	@Override
	public User retrieveUserByEmailId(String emailId) {
		List<User> users = userRepository.findAllByEmailId(emailId);
		if(users.size()==1) {
			return users.get(0);
		}
		return null;
	}

	@Override
	public User userDetail(String token) {
		return userRepository.findById(tokenGenerator.verifyToken(token)).get();

	}

	@Override
	public User store(MultipartFile file, String token) throws IOException {
		User user = userRepository.findById(tokenGenerator.verifyToken(token)).get();
		byte[] profilePicture = file.getBytes();
		if (profilePicture.length > 0) {
			user.setProfilePicture(profilePicture);
			userRepository.save(user);
		}
		return user;
	}

	@Override
	public User deleteImage(String token) {
		User user = userRepository.findById(tokenGenerator.verifyToken(token)).get();
		if(user!=null) {
			user.setProfilePicture(null);
			userRepository.save(user);
		}
		return user;
	}
}
