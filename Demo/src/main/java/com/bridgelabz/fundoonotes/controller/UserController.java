package com.bridgelabz.fundoonotes.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.service.UserService;

@Controller
@RequestMapping("/demo")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	@Qualifier("userValidator")
	private Validator userValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(userValidator);
	}

	@PostMapping("/registeruser")
	public ResponseEntity<?> register(@Validated @RequestBody User user, BindingResult bindingResult,
			HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<String>("Add details in proper format", HttpStatus.CONFLICT);
		} else {
			if (userService.register(user, request))
				return new ResponseEntity<Void>(HttpStatus.OK);
			else
				return new ResponseEntity<String>("Registration failed", HttpStatus.CONFLICT);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user, HttpServletResponse response) {
		String token = userService.login(user);
		if (token != null) {
			response.setHeader("token", token);
			logger.info(token);
			if (response.containsHeader(token))
				return new ResponseEntity<Void>(HttpStatus.OK);
			else
				return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		} else
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
	}

	@PostMapping("/updateuser")
	public ResponseEntity<?> updateUser(@RequestHeader String token, @RequestBody User user) {
		User updatedUser = userService.update(token, user);
		if (updatedUser != null) {
			return new ResponseEntity<String>("Updated Successfully", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Couldn't update", HttpStatus.CONFLICT);
	}

	@DeleteMapping(value = "/deleteuser")
	public ResponseEntity<?> deleteUser(@RequestHeader String token) {
		try {
			if (!userService.delete(token)) {
				return new ResponseEntity<String>("Couldn't delete", HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<String>("Deleted successfully", HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Error occured", e);
			return new ResponseEntity<String>("Couldn't delete", HttpStatus.CONFLICT);
		}
	}

	@GetMapping("/activationstatus/{token:.+}")
	public ResponseEntity<?> activateUser(@PathVariable("token") String token, HttpServletResponse response)
			throws IOException {
		User updatedUser = userService.activationStatus(token);
		if (updatedUser != null) {
			response.sendRedirect("http://localhost:4200/login");
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<String>("Couldn't activate", HttpStatus.CONFLICT);
	}

	@PostMapping("/forgotpassword")
	public ResponseEntity<?> forgotPassword(@RequestBody User user, HttpServletRequest request) {
		if (userService.forgotPassword(user, request)) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<String>("Forgot password operation not successful", HttpStatus.CONFLICT);
	}

	@PutMapping(value = "/resetpassword/{token:.+}")
	public ResponseEntity<?> resetPassword(@PathVariable("token") String token, @RequestBody User user) {
		User updatedUser = userService.resetPassword(user, token);
		if (updatedUser != null) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<String>("Your password couldn't be reset", HttpStatus.CONFLICT);
	}

	@GetMapping(value = "/retrieveusers")
	public  ResponseEntity<?>  retrieveUsers() {
		List<User> users = userService.retrieveUsers();
		if (!users.isEmpty())
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		return new ResponseEntity<String>("Users couldn't be fetched", HttpStatus.CONFLICT);
	}
	
	@GetMapping(value = "/userdetails")
	public  ResponseEntity<?>  userDetails(@RequestHeader("token") String token) {
		User user = userService.userDetail(token);
		if (user!=null)
			return new ResponseEntity<User>(user, HttpStatus.OK);
		return new ResponseEntity<String>("Users couldn't be fetched", HttpStatus.CONFLICT);
	}
	
	@PostMapping(value = "/uploadfile")
	public  ResponseEntity<?>  storeFile(@RequestBody MultipartFile file,@RequestHeader("token") String token) throws IOException {
		if (userService.store(file, token))
			return new ResponseEntity<Void>(HttpStatus.OK);
		return new ResponseEntity<String>("Users couldn't be fetched", HttpStatus.CONFLICT);
	}

}
