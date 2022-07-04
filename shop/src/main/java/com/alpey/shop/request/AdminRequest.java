package com.alpey.shop.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AdminRequest {
	
	private String username;
	private String password;
	private String email;
	private String masterPassword;

}
