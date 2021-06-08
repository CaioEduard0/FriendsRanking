package com.example.FriendsRanking.controllers;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.FriendsRanking.dto.FriendDTO;
import com.example.FriendsRanking.dto.PointsDTO;
import com.example.FriendsRanking.entities.Friend;
import com.example.FriendsRanking.entities.User;
import com.example.FriendsRanking.services.FriendService;
import com.example.FriendsRanking.services.UserService;

@Controller
public class FriendController {
	
	private FriendService friendService;
	private UserService userService;
	
	public FriendController(FriendService friendService, UserService userService) {
		this.friendService = friendService;
		this.userService = userService;
	}
	
	@PostMapping("/new-friend")
	public ModelAndView insertFriend(@AuthenticationPrincipal UserDetails userDetails, @Valid FriendDTO friend, BindingResult bindingResult) {
		ModelAndView mv = new ModelAndView("index");
		User user = userService.findUserByEmail(userDetails.getUsername());
		List<Friend> friends = user.getFriends();
		Collections.sort(friends);
		mv.addObject("user", user);
		mv.addObject("friends", friends);
		if (bindingResult.hasErrors()) {		
			mv.addObject("errors", bindingResult.getAllErrors());
			return mv;
		}
		friendService.insertFriend(userDetails.getUsername(), friend.dtoToFriend(friend));
		Collections.sort(friends);
		mv.addObject("friends", friends);
		return mv;
	}
	
	@PutMapping("/update/{id}")
	public ModelAndView updatePoints(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, @Valid PointsDTO points, BindingResult bindingResult) {
		ModelAndView mv = new ModelAndView("index");
		User user = userService.findUserByEmail(userDetails.getUsername());
		List<Friend> friends = user.getFriends();
		Collections.sort(friends);
		mv.addObject("user", user);
		mv.addObject("friends", friends);
		if (bindingResult.hasErrors()) {
			mv.addObject("updateError", bindingResult.getAllErrors());
			return mv;
		}
		friendService.updatePoints(id, points.getPoints());
		Collections.sort(friends);
		mv.addObject("friends", friends);
		return mv;
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteFriend(@PathVariable Long id) {
		friendService.deleteFriend(id);
		return "redirect:/";
	}
}
