package com.example.FriendsRanking.test.utils;

import com.example.FriendsRanking.dto.UserDTO;
import com.example.FriendsRanking.entities.User;

public class UserCreator {
	
	public static User createUser() {
		User user = new User(); 
		user.setName("José");
		user.setEmail("jose@gmail.com");
		user.setPassword("jose123");
		user.setAuthorities("ROLE_USER");
		return user;
	}
	
	public static User createUserAdmin() {
		User user = new User(); 
		user.setName("Marcos");
		user.setEmail("marcos@gmail.com");
		user.setPassword("marcos123");
		user.setAuthorities("ROLE_USER, ROLE_ADMIN");
		return user;
	}
	
	public static UserDTO createUserToBeSaved() {
		UserDTO user = new UserDTO(); 
		user.setName("José");
		user.setEmail("jose@gmail.com");
		user.setPassword("jose123");
		user.setAuthorities("ROLE_USER");
		return user;
	}
}
