package com.bridgelabz.fundoonotes.utility;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {
	
	private static final String activationUrl = "http://localhost:4200/login";

	@Autowired
	private MailSender mailSender;
	
	public void sendEmail(String toEmail) {
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
			// set message headers
			msg.setFrom("mundargi95@gmail.com");
			msg.setTo("mundargi95@gmail.com");
			msg.setSubject("Verification Email");
			String message = "Note has been shared to you.. login to access the note /n/n "+activationUrl;
			msg.setText(message);
			msg.setSentDate(new Date());
	        mailSender.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
