package com.alpey.shop.controller;

import java.time.LocalDate;
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

import com.alpey.shop.entity.Order;
import com.alpey.shop.request.OrderRequest;
import com.alpey.shop.service.OrderService;

@CrossOrigin
@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@PostMapping
	public Order create(@RequestBody OrderRequest order) {
		return orderService.create(order);
	}
	
	@PutMapping("/{orderNumber}")
	public Order update(@RequestBody Order order, @PathVariable long orderNumber) {
		return orderService.update(order, orderNumber);
	}
	
	@GetMapping("/{orderNumber}")
	public Order findByNumber(@PathVariable long orderNumber) {
		return orderService.findByNumber(orderNumber);
	}
	
	@GetMapping("/user/{username}")
	public List<Order> findByUser(@PathVariable String username){
		return orderService.findByUser(username);
	}
	
	@GetMapping("/date/{date}")
	public List<Order> findByDate(@RequestBody LocalDate date){
		return orderService.findByDate(date);
	}
	
	@GetMapping
	public List<Order> findAll(){
		return orderService.findAll();
	}
	
	@DeleteMapping("/{orderNumber}")
	public String delete(long orderNumber) {
		return orderService.delete(orderNumber);
	}
	
}
