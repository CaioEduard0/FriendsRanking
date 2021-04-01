package com.example.FriendsRanking.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.FriendsRanking.entities.Friend;
import com.example.FriendsRanking.entities.User;
import com.example.FriendsRanking.repositories.FriendRepository;

@Service
public class FriendService {
	
	@Autowired
	private FriendRepository friendRepository;
	
	@Autowired
	private UserService userService;
	
	public List<Friend> findAllFriends(Long id) {
		User user = userService.findUserById(id);
		List<Friend> friends = user.getFriends();
		Collections.sort(friends);
		return friends;
	}
	
	public void insertFriend(String id, Friend friend) {
		User user = userService.findUserById(Long.parseLong(id));
		user.getFriends().add(friend);
		friend.setUser(user);
		friendRepository.save(friend);
	}
	
	public long updatePoints(Long id, Integer points) {
		Optional<Friend> friend = friendRepository.findById(id);
		friend.get().setPoints(points);
		friendRepository.save(friend.get());
		return friend.get().getUser().getId();
	}
	
	public long deleteFriend(Long id) {
		Optional<Friend> friend = friendRepository.findById(id);
		long userId = friend.get().getUser().getId();
		friendRepository.deleteById(id);
		return userId;
	}
}
