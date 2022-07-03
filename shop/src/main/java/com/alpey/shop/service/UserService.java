package com.alpey.shop.service;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpey.shop.entity.User;
import com.alpey.shop.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public User create(User user) {
		try {
			return userRepository.save(user);
		} catch (EntityExistsException e) {
			return null;
		}
	}

	public User update(User user, String oldName) {
		try {
			User storedUser = userRepository.findByUsername(oldName);
			BeanUtils.copyProperties(user, storedUser, "id");
			return userRepository.save(storedUser);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}

	public User findByUsername(String username) {
		try {
			return userRepository.findByUsername(username);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	public User findByEmail(String email) {
		try {
			return userRepository.findByEmail(email);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	public String delete(String username) {
		try {
			User user = userRepository.findByUsername(username);
			userRepository.delete(user);
			return "User " + username + " deleted!";
		} catch (EntityNotFoundException e) {
			return "User " + username + " doesn't exist!";
		}
	}

	public User loginValidation(String username, String password) {
		
		try {
			User user = findByUsername(username);
			if (user.getPassword().equals(password)) {
				return user;
			} else {
				return null;
			}
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
	
	/*
	public User copyNotNullProperties(User src, User dest) {
		String username = src.getUsername();
		String password = src.getPassword();
		String email = src.getEmail();
		
		if(hasValue(username))
			dest.setUsername(username);
		if(hasValue(password))
			dest.setPassword(password);
		if(hasValue(email))
			dest.setEmail(email);
		
		return dest;
	}
	
	public boolean hasValue(String str) {
		if(str.equals("") || str.equals(null)) {
			return false;
		} else {
			return true;
		}
	}
	*/
	
}
