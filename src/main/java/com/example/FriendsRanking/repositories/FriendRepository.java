package com.example.FriendsRanking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.FriendsRanking.entities.Friend;

public interface FriendRepository extends JpaRepository<Friend, Long> {
	
}