package com.alpey.shop.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpey.shop.entity.User;
import com.alpey.shop.repository.UserRepository;
import com.alpey.shop.request.UserRequest;
import com.alpey.shop.response.UserResponse;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;

	public UserResponse create(UserRequest userRequest) {
		try {
			User user = parseUser(userRequest);
			User storedUser = userRepository.save(user);
			return parseUserResponse(storedUser);
		} catch (EntityExistsException | NullPointerException | IllegalArgumentException e) {
			return new UserResponse();
		}
	}

	public UserResponse update(UserRequest user, String oldName) {
		try {
			User storedUser = parseUser(user, oldName);
			storedUser = userRepository.save(storedUser);
			return parseUserResponse(storedUser);
		} catch (EntityNotFoundException | NullPointerException | IllegalArgumentException e) {
			return new UserResponse();
		}
	}

	public List<UserResponse> findAll() {
		List<User> users = (List<User>) userRepository.findAll();
		return parseUserResponse(users);
	}

	public UserResponse findByUsername(String username) {
		try {
			User user = userRepository.findByUsername(username);
			return parseUserResponse(user);
		} catch (EntityNotFoundException | NullPointerException | IllegalArgumentException e) {
			return new UserResponse();
		}
	}

	public UserResponse findByEmail(String email) {
		try {
			User user = userRepository.findByEmail(email);
			return parseUserResponse(user);
		} catch (EntityNotFoundException | NullPointerException | IllegalArgumentException e) {
			return new UserResponse();
		}
	}

	public List<UserResponse> findByCity(String city) {
		try {
			List<User> users = userRepository.findByCity(city);
			return parseUserResponse(users);
		} catch (EntityNotFoundException | NullPointerException | IllegalArgumentException e) {
			return new ArrayList<UserResponse>();
		}
	}

	public UserResponse findByPhone(String phone) {
		try {
			User user = userRepository.findByPhone(phone);
			return parseUserResponse(user);
		} catch (EntityNotFoundException | NullPointerException | IllegalArgumentException e) {
			return new UserResponse();
		}
	}

	public String delete(String username) {
		try {
			User user = userRepository.findByUsername(username);
			userRepository.delete(user);
			return "User " + username + " deleted!";
		} catch (EntityNotFoundException | NullPointerException | IllegalArgumentException e) {
			return "User " + username + " doesn't exist!";
		}
	}

	public UserResponse loginValidation(String username, String password) {

		try {
			User user = userRepository.findByUsername(username);
			if (user.getPassword().equals(password)) {
				return parseUserResponse(user);
			} else {
				return new UserResponse();
			}
		} catch (EntityNotFoundException | NullPointerException | IllegalArgumentException e) {
			return new UserResponse();
		}
	}

	/*
	 * public User copyNotNullProperties(User src, User dest) { String username =
	 * src.getUsername(); String password = src.getPassword(); String email =
	 * src.getEmail();
	 * 
	 * if(hasValue(username)) dest.setUsername(username); if(hasValue(password))
	 * dest.setPassword(password); if(hasValue(email)) dest.setEmail(email);
	 * 
	 * return dest; }
	 * 
	 * public boolean hasValue(String str) { if(str.equals("") || str.equals(null))
	 * { return false; } else { return true; } }
	 */

	private User parseUser(UserRequest userRequest) {
		User user = new User();
		BeanUtils.copyProperties(userRequest, user);
		return user;
	}

	private User parseUser(UserRequest userRequest, String oldUsername) {
		User storedUser = userRepository.findByUsername(oldUsername);
		BeanUtils.copyProperties(userRequest, storedUser);
		return storedUser;
	}

	private UserResponse parseUserResponse(User user) {
		UserResponse userResponse = new UserResponse();
		BeanUtils.copyProperties(user, userResponse);
		return userResponse;
	}

	private List<UserResponse> parseUserResponse(List<User> users) {
		List<UserResponse> userResponses = new ArrayList<UserResponse>();
		users.stream().forEach(user -> userResponses.add(parseUserResponse(user)));
		return userResponses;
	}

}
