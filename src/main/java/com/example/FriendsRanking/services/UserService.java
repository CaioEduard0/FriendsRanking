package com.example.FriendsRanking.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FriendsRanking.entities.User;
import com.example.FriendsRanking.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}
	
	public User findUserById(Long id) {
		Optional<User> user = userRepository.findById(id);
		return user.get();
	}
	
	public void insertUser(User user) {
		userRepository.save(user);
	}
	
	public void updateUser(Long id, User user) {
		User updatedUser = findUserById(id);
		updatedUser.setName(user.getName());
		updatedUser.setEmail(user.getEmail());
		userRepository.save(updatedUser);
	}
	
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
}
