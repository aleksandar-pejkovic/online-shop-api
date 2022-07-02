package com.alpey.shop.service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpey.shop.entity.Order;
import com.alpey.shop.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	public Order create(Order order) {
		try {
			return orderRepository.save(order);
		} catch (EntityExistsException e) {
			return null;
		}
	}
	
	public Order update(Order order) {
		try {
			Order storedOrder = orderRepository.findByOrderNumber(order.getOrderNumber());
			BeanUtils.copyProperties(order, storedOrder, "id");
			return orderRepository.save(storedOrder);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
	
}
