package com.example.FriendsRanking.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.FriendsRanking.entities.Friend;
import com.example.FriendsRanking.entities.User;
import com.example.FriendsRanking.services.FriendService;
import com.example.FriendsRanking.services.UserService;

@Controller
@RequestMapping("users")
public class FriendResource {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FriendService friendService;
	
	@GetMapping("/{id}/friends")
	public ModelAndView findAllFriends(@PathVariable Long id) {
		User user = userService.findUserById(id);
		List<Friend> friends = friendService.findAllFriends(id);
		ModelAndView mv = new ModelAndView("friends");
		mv.addObject("user", user);
		mv.addObject("friends", friends);
		return mv;
	}
	
	@PostMapping("/new-friend")
	public String insertFriend(String id, Friend friend) {
		friendService.insertFriend(id, friend);
		return "redirect:/users/"+id+"/friends";
	}
	
	@PutMapping("/update-points/{id}")
	public String updatePoints(@PathVariable Long id, Integer points) {
		long userId = friendService.updatePoints(id, points);
		return "redirect:/users/"+userId+"/friends";
	}
	
	@DeleteMapping("/delete-friend/{id}")
	public String deleteFriend(@PathVariable Long id) {
		long userId = friendService.deleteFriend(id);
		return "redirect:/users/"+userId+"/friends";
	}
}
