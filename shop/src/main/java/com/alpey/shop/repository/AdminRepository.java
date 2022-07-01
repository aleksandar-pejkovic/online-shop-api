package com.alpey.shop.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alpey.shop.entity.Admin;

@Repository
public interface AdminRepository extends CrudRepository<Admin, Long> {
	
	public Admin findByUsername(String username);
	public Admin findByEmail(String email);

}
