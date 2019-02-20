package com.bridgelabz.fundoonotes.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

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
		System.out.println(token);
		if (token != null) {
			response.setHeader("token", token);
			return new ResponseEntity<Void>(HttpStatus.OK);
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
		boolean result = userService.delete(token);
		if (result) {
			return new ResponseEntity<String>("Deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Couldn't delete", HttpStatus.CONFLICT);
	}

	@GetMapping("/activationstatus/{token:.+}")
	public ResponseEntity<?> activateUser(@PathVariable("token") String token) {
		User updatedUser = userService.activationStatus(token);
		if (updatedUser != null) {
			return new ResponseEntity<String>("Activated Successfully", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Couldn't activate", HttpStatus.CONFLICT);
	}

	@GetMapping("/forgotpassword")
	public ResponseEntity<?> forgotPassword(@RequestParam("emailId") String emailId, HttpServletRequest request) {
		if (userService.forgotPassword(emailId, request)) {
			return new ResponseEntity<String>("Forgot password operation successful", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Forgot password operation not successful", HttpStatus.CONFLICT);
	}

	@PutMapping(value = "/resetpassword/{token:.+}")
	public ResponseEntity<?> resetPassword(@PathVariable("token") String token, @RequestBody User user) {
		User updatedUser = userService.resetPassword(user, token);
		if (updatedUser != null) {
			return new ResponseEntity<String>("Your password has been reset", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Your password couldn't be reset", HttpStatus.CONFLICT);
	}

}
