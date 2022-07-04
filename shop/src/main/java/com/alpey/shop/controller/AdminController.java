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

import com.alpey.shop.request.AdminRequest;
import com.alpey.shop.response.AdminResponse;
import com.alpey.shop.service.AdminService;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;

	@PostMapping
	public AdminResponse create(@RequestBody AdminRequest admin) {
		return adminService.create(admin);
	}

	@PutMapping("/{username}")
	public AdminResponse update(@RequestBody AdminRequest admin, @PathVariable String username) {
		return adminService.update(admin, username);
	}

	@GetMapping
	public List<AdminResponse> findAll() {
		return adminService.findAll();
	}

	@GetMapping("/{username}")
	public AdminResponse findByUsername(@PathVariable String username) {
		return adminService.findByUsername(username);
	}

	@GetMapping("/email/{email}")
	public AdminResponse findByEmail(@PathVariable String email) {
		return adminService.findByUsername(email);
	}

	@DeleteMapping("/{username}")
	public String delete(@PathVariable String username) {
		return adminService.delete(username);
	}

	@GetMapping("/login/{username}/{password}")
	public AdminResponse login(@PathVariable String username, @PathVariable String password, @PathVariable String masterPassword) {
		return adminService.loginValidation(username, password, masterPassword);
	}

}
