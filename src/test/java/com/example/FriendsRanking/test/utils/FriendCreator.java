package com.example.FriendsRanking.test.utils;

import static com.example.FriendsRanking.test.utils.UserCreator.createUser;
import com.example.FriendsRanking.entities.Friend;

public class FriendCreator {
	
	public static Friend createFriend() {
		Friend friend = new Friend();
		friend.setName("Mario");
		friend.setPoints(50);
		friend.setUser(createUser());
		return friend;
	}
}
