package com.alpey.shop.service;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpey.shop.entity.Order;
import com.alpey.shop.entity.User;
import com.alpey.shop.repository.OrderRepository;
import com.alpey.shop.repository.UserRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	UserRepository userRepository;
	
	public Order create(Order order) {
		try {
			return orderRepository.save(order);
		} catch (EntityExistsException e) {
			return null;
		}
	}
	
	public Order update(Order order, long orderNumber) {
		try {
			Order storedOrder = orderRepository.findByOrderNumber(orderNumber);
			BeanUtils.copyProperties(order, storedOrder, "id");
			return orderRepository.save(storedOrder);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
	
	public Order findByNumber(long orderNumber) {
		try {
			return orderRepository.findByOrderNumber(orderNumber);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
	
	public List<Order> findByUser(String username){
		User user = userRepository.findByUsername(username);
		return orderRepository.findByUser(user);
	}
	
	public List<Order> findByDate(LocalDate date){
		return orderRepository.findByOrderDate(date);
	}
	
	public List<Order> findAll(){
		return (List<Order>) orderRepository.findAll();
	}
	
	public String delete(long orderNumber) {
		try {
			Order order = orderRepository.findByOrderNumber(orderNumber);
			orderRepository.delete(order);
			return "Order " + orderNumber + " deleted!";
		} catch (EntityNotFoundException e) {
			return "Order not found!";
		}
	}
	
	
}
