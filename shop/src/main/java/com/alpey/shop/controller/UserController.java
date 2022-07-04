package com.alpey.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alpey.shop.entity.User;
import com.alpey.shop.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@PostMapping
	public User create(@RequestBody User user) {
		return userService.create(user);
	}
	
	@PutMapping("/{username}")
	public User update(@RequestBody User user, @PathVariable String username) {
		return userService.update(user, username);
	}
	
	@GetMapping
	public List<User> findAll(){
		return userService.findAll();
	}
	
	@GetMapping("/{username}")
	public User findByUsername(@PathVariable String username) {
		return userService.findByUsername(username);
	}
	
	@GetMapping("/email/{email}")
	public User findByEmail(@PathVariable String email) {
		return userService.findByUsername(email);
	}
	
	@GetMapping("/phone/{phone}")
	public User findByPhone(@PathVariable String phone) {
		return userService.findByPhone(phone);
	}
	
	@DeleteMapping("/{username}")
	public String delete(@PathVariable String username) {
		return userService.delete(username);
	}
	
	@GetMapping("/login/{username}/{password}")
	public User login(@PathVariable String username, @PathVariable String password) {
		return userService.loginValidation(username, password);
	}
	
}
