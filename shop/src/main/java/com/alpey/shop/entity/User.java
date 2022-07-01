package com.alpey.shop.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique = true)
	private String username;
	private String password;
	@Column(unique = true)
	private String email;
	private String firstName;
	private String lastName;
	private String address;
	private String zip;
	private String city;
	@Column(unique = true)
	private String phone;
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Order> orders;

	public List<Order> addOrder(Order order) {
		this.orders.add(order);
		return this.orders;
	}

	public List<Order> removeOrder(Order order) {
		this.orders.remove(order);
		return this.orders;
	}

}
