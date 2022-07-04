package com.alpey.shop.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRequest {
	
	private String username;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private String street;
	private String zip;
	private String city;
	private String phone;

}
