package com.bridgelabz.fundoonotes.utility;

public interface TokenGenerator {
	String generateToken(String id);

	int verifyToken(String token);

}
