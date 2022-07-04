package com.alpey.shop.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alpey.shop.entity.Order;
import com.alpey.shop.entity.User;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
	
	public Order findByOrderNumber(String orderNumber);
	public List<Order> findByUser(User user);
	public List<Order> findByOrderDate(LocalDate orderDate);

}
