package com.example.FriendsRanking.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.FriendsRanking.entities.Friend;
import com.example.FriendsRanking.entities.User;
import com.example.FriendsRanking.repositories.FriendRepository;
import com.example.FriendsRanking.repositories.UserRepository;

@Service
public class FriendService {
	
	private FriendRepository friendRepository;
	private UserRepository userRepository;
	
	public FriendService(FriendRepository friendRepository, UserRepository userRepository) {
		this.friendRepository = friendRepository;
		this.userRepository = userRepository;
	}
	
	public void insertFriend(String email, Friend friend) {
		Optional<User> user = userRepository.findByEmail(email);
		user.get().getFriends().add(friend);
		friend.setUser(user.get());
		friendRepository.save(friend);
	}
	
	public void updatePoints(Long id, int points) {
		Optional<Friend> friend = friendRepository.findById(id);
		friend.get().setPoints(points);
		friendRepository.save(friend.get());
	}
	
	public void deleteFriend(Long id) {
		friendRepository.deleteById(id);
	}
}
