package com.example.FriendsRanking.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.FriendsRanking.entities.User;

public class UserDTO {
	
	@NotBlank(message = "{name.not.blank}")
	@Size(min = 2, max = 50, message = "{name.size}")
	private String name;
	
	@NotBlank(message = "{email.not.blank}")
	@Email(message = "{email.not.valid}")
	private String email;
	
	@NotBlank(message = "{password.not.blank}")
	@Size(min = 6, max = 50, message = "{password.size}")
	private String password;
	
	private String authorities;
	
	private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getAuthorities() {
		return authorities;
	}
	
	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}
	
	public User dtoToUser(UserDTO userDto) {
		User user = new User();
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setAuthorities(userDto.getAuthorities());
		return user;
	}
}
