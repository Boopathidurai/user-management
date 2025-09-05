package com.usermanagement.service;

public interface EmailService {
	
	 boolean sendMail(String toEmail, String subject, String body);

}
