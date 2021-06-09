package com.example.FriendsRanking.controllers;

import java.util.ArrayList;
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
	
	@SuppressWarnings("unchecked")
	@PostMapping("/new-friend")
	public ModelAndView insertFriend(@AuthenticationPrincipal UserDetails userDetails, @Valid FriendDTO friend, BindingResult bindingResult) {
		ModelAndView mv = getIndexPage(userDetails.getUsername());
		if (bindingResult.hasErrors()) {
			
			List<String> messages = new ArrayList<>();
			bindingResult.getFieldErrors().forEach((f) -> {
				int r = f.getField().compareTo("points");
				if (r == 0) {
					messages.add("The points are mandatory and must be between 0 and 100!");
				} else {
					messages.add(f.getDefaultMessage());
				}
			});
			mv.addObject("errors", messages);
			return mv;  
		}
		friendService.insertFriend(userDetails.getUsername(), friend.dtoToFriend(friend));
		Collections.sort((List<Friend>) mv.getModelMap().getAttribute("friends"));
		return mv;
	}
	
	@SuppressWarnings("unchecked")
	@PutMapping("/update/{id}")
	public ModelAndView updatePoints(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, @Valid PointsDTO points, BindingResult bindingResult) {
		ModelAndView mv = getIndexPage(userDetails.getUsername());
		if (bindingResult.hasErrors()) {
			
			List<String> messages = new ArrayList<>();
			bindingResult.getFieldErrors().forEach((f) -> {
				int r = f.getField().compareTo("points");
				if (r == 0) {
					messages.add("The points must be between 0 and 100!");
				} else {
					messages.add(f.getDefaultMessage());
				}
			});
			mv.addObject("updateError", messages);
			return mv;  
		}
		friendService.updatePoints(id, points.getPoints());
		Collections.sort((List<Friend>) mv.getModelMap().getAttribute("friends"));
		return mv;
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteFriend(@PathVariable Long id) {
		friendService.deleteFriend(id);
		return "redirect:/";
	}
	
	private ModelAndView getIndexPage(String email) {
		ModelAndView mv = new ModelAndView("index");
		User user = userService.findUserByEmail(email);
		List<Friend> friends = user.getFriends();
		Collections.sort(friends);
		mv.addObject("user", user);
		mv.addObject("friends", friends);
		return mv;
	}
}
