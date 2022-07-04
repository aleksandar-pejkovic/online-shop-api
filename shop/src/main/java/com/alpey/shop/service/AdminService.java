package com.alpey.shop.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpey.shop.entity.Admin;
import com.alpey.shop.repository.AdminRepository;
import com.alpey.shop.request.AdminRequest;
import com.alpey.shop.response.AdminResponse;

@Service
public class AdminService {

	@Autowired
	AdminRepository adminRepository;

	public AdminResponse create(AdminRequest adminRequest) {
		try {
			Admin admin = parseAdmin(adminRequest);
			Admin storedAdmin = adminRepository.save(admin);
			return parseAdminResponse(storedAdmin);
		} catch (EntityExistsException | NullPointerException e) {
			return new AdminResponse();
		}
	}

	public AdminResponse update(AdminRequest adminRequest, String oldUsername) {
		try {
			Admin storedAdmin = parseAdmin(adminRequest, oldUsername);
			Admin updatedAdmin = adminRepository.save(storedAdmin);
			return parseAdminResponse(updatedAdmin);
		} catch (EntityNotFoundException | NullPointerException e) {
			return new AdminResponse();
		}
	}

	public List<AdminResponse> findAll() {
		List<Admin> admins = (List<Admin>) adminRepository.findAll();
		return parseAdminResponse(admins);
	}

	public AdminResponse findByUsername(String username) {
		try {
			Admin admin = adminRepository.findByUsername(username);
			return parseAdminResponse(admin);
		} catch (EntityNotFoundException | NullPointerException e) {
			return new AdminResponse();
		}
	}

	public AdminResponse findByEmail(String email) {
		try {
			Admin admin = adminRepository.findByEmail(email);
			return parseAdminResponse(admin);
		} catch (EntityNotFoundException | NullPointerException e) {
			return new AdminResponse();
		}
	}

	public String delete(String username) {
		try {
			Admin admin = adminRepository.findByUsername(username);
			adminRepository.delete(admin);
			return "Admin " + username + " deleted!";
		} catch (EntityNotFoundException | NullPointerException e) {
			return "Admin " + username + " doesn't exist!";
		}
	}

	public String changeMasterPassword(String oldMasterPassword, String newMasterPassword) {
		try {
			return Admin.changeMasterPassword(oldMasterPassword, newMasterPassword);
		} catch (NullPointerException e) {
			return "Input can't be null!";
		}
	}

	public AdminResponse loginValidation(String username, String password, String masterPassword) {

		try {
			Admin admin = adminRepository.findByUsername(username);
			if (admin.getPassword().equals(password) && Admin.masterPassword.equals(masterPassword)) {
				return parseAdminResponse(admin);
			} else {
				return new AdminResponse();
			}
		} catch (EntityNotFoundException | NullPointerException e) {
			return new AdminResponse();
		}
	}

	/*
	 * public Admin copyNotNullProperties(Admin src, Admin dest) { String username =
	 * src.getUsername(); String password = src.getPassword(); String email =
	 * src.getEmail();
	 * 
	 * if (hasValue(username)) dest.setUsername(username); if (hasValue(password))
	 * dest.setPassword(password); if (hasValue(email)) dest.setEmail(email);
	 * 
	 * return dest; }
	 * 
	 * public boolean hasValue(String str) { if (str.equals("") || str.equals(null))
	 * { return false; } else { return true; } }
	 */

	private Admin parseAdmin(AdminRequest adminRequest, String oldUsername) {
		Admin admin = adminRepository.findByUsername(oldUsername);
		BeanUtils.copyProperties(adminRequest, admin);
		return admin;
	}

	private Admin parseAdmin(AdminRequest adminRequest) {
		Admin admin = new Admin();
		BeanUtils.copyProperties(adminRequest, admin);
		return admin;
	}

	private AdminResponse parseAdminResponse(Admin admin) {
		AdminResponse adminResponse = new AdminResponse();
		BeanUtils.copyProperties(admin, adminResponse);
		return adminResponse;
	}

	private List<AdminResponse> parseAdminResponse(List<Admin> admins) {
		List<AdminResponse> adminResponses = new ArrayList<AdminResponse>();
		admins.stream().forEach(admin -> adminResponses.add(parseAdminResponse(admin)));
		return adminResponses;
	}

}
