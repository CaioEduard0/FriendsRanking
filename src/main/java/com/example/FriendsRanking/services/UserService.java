package com.example.FriendsRanking.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.FriendsRanking.entities.User;
import com.example.FriendsRanking.repositories.UserRepository;
import com.example.FriendsRanking.services.exceptions.RepeatedEmailException;

@Service
public class UserService implements UserDetailsService {
	
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User findUserByEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		return user.orElseThrow(() -> new UsernameNotFoundException("User with e-mail " + email + " not found!"));
	}
	
	public void insertUser(User user) {
		Optional<User> email = userRepository.findByEmail(user.getEmail());
		if (email.isPresent()) {
			throw new RepeatedEmailException(user.getEmail());
		}
		userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByEmail(email);
		return user.orElseThrow(() -> new UsernameNotFoundException("User with e-mail " + email + " not found!"));
	}
}
