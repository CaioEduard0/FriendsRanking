package com.example.FriendsRanking.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.FriendsRanking.entities.Friend;

public class FriendDTO {
	
	@NotBlank(message = "{name.not.blank}")
	@Size(message = "{name.size}")
	private String name;
	
	@Min(value = 0, message = "{points.size}")
	@Max(value = 100, message = "{points.size}")
	@NotNull(message = "{points.not.null}")
	private int points;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPoints() {
		return points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	
	public Friend dtoToFriend(FriendDTO friendDto) {
		Friend friend = new Friend();
		friend.setName(friendDto.getName());
		friend.setPoints(friendDto.getPoints());
		return friend;
	}
}
