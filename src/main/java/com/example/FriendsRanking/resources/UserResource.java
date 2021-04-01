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
import com.example.FriendsRanking.services.UserService;

@Controller
@RequestMapping("users")
public class UserResource {
	
	@Autowired
	private UserService userService;
	
	@GetMapping
	public ModelAndView findAllUsers() {
		List<User> users = userService.findAllUsers();
		ModelAndView mv = new ModelAndView("index");
		mv.addObject("users", users);
		return mv;
	}
	
	@PostMapping("/register")
	public String insertUser(User user) {
		userService.insertUser(user);
		return "redirect:/users";
	}
	
	@GetMapping("/edit/{id}")
	public ModelAndView editUser(@PathVariable Long id) {
		User user = userService.findUserById(id);
		ModelAndView mv = new ModelAndView("edit");
		mv.addObject("user", user);
		return mv;
	}
	
	@PutMapping("/update/{id}")
	public String updateUser(@PathVariable Long id, User user) {
		userService.updateUser(id, user);
		return "redirect:/users";
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return "redirect:/users";
	}
}
