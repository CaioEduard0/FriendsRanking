package com.example.FriendsRanking.controllers;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.FriendsRanking.dto.UserDTO;
import com.example.FriendsRanking.entities.Friend;
import com.example.FriendsRanking.entities.User;
import com.example.FriendsRanking.services.UserService;

@Controller
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	@GetMapping("/sign-up")
	public String signUpPage() {
		return "signup";
	}
	
	@PostMapping("/sign-up")
	public ModelAndView createAccount(@Valid UserDTO user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ModelAndView mv = new ModelAndView("signup");
			mv.addObject("errors", bindingResult.getAllErrors());
			return mv;
		}
		userService.insertUser(user.dtoToUser(user));
		return new ModelAndView("login");
	}
	
	@GetMapping("/")
	public ModelAndView indexPage(@AuthenticationPrincipal UserDetails userDetails) {
		User user = userService.findUserByEmail(userDetails.getUsername());
		List<Friend> friends = user.getFriends();
		Collections.sort(friends);
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("user", user);
		mv.addObject("friends", friends);
		return mv;
	}
}
