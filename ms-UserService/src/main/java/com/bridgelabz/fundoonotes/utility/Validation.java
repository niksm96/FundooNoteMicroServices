package com.bridgelabz.fundoonotes.utility;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bridgelabz.fundoonotes.model.User;

@Component
public class Validation implements Validator {
	static final String REGEX_NAME = "^[a-zA-Z]{3,20}$";
	static final String REGEX_EMAIL = "^[a-z0-9._%-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";
	static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{3,8}$";
	static final String REGEX_MOBILE_NUMBER = "^[0-9]{10}$";

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;
		if (!user.getName().matches(REGEX_NAME)) {
			errors.rejectValue("name", "symbolsPresent", new Object[] { "'name'" }, "name can't be symbols");
		}
		if (!user.getEmailId().matches(REGEX_EMAIL)) {
			errors.rejectValue("emailId", "symbolsPresent", new Object[] { "'emailId'" }, "emailId can't be symbols");
		}
		if (!user.getPassword().matches(REGEX_PASSWORD)) {
			errors.rejectValue("password", "symbolsPresent", new Object[] { "'password'" },
					"password can't be symbols");
		}
		if (!String.valueOf(user.getMobileNumber()).matches(REGEX_MOBILE_NUMBER)) {
			errors.rejectValue("mobileNumber", "symbolsPresent", new Object[] { "'mobileNumber'" },
					"mobileNumber can't be symbols");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required");
		ValidationUtils.rejectIfEmpty(errors, "mobileNumber", "mobileNumber.required");
		ValidationUtils.rejectIfEmpty(errors, "emailId", "emailId.required");
	}

}
