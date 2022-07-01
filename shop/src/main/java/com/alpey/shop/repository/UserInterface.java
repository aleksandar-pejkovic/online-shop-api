package com.alpey.shop.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alpey.shop.entity.User;

@Repository
public interface UserInterface extends CrudRepository<User, Long> {
	
	public User findByUsername(String username);
	public User findByEmail(String email);
	public List<User> findByCity(String city);

}
