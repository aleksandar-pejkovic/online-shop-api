package com.alpey.shop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "admins")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique = true)
	private String username;
	private String password;
	@Column(unique = true)
	private String email;
	private static String masterPassword = "adminIAm";

	public static String changeMasterPassword(String oldMasterPassword, String newMasterPassword) {
		if (masterPassword.equals(oldMasterPassword)) {
			masterPassword = newMasterPassword;
			String msg = "Master password has been changed!";
			System.out.println(msg);
			return msg;
		} else {
			return "Old password is incorrect!";
		}
	}

}
