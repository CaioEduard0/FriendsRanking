package com.example.FriendsRanking.services.exceptions;

public class RepeatedEmailException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public RepeatedEmailException(String email) {
		super("The e-mail " + email + " is already registered!");
	}
}
