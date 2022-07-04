package com.alpey.shop.service;

import java.time.LocalDate;
import java.util.ArrayList;
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
import com.alpey.shop.request.OrderRequest;
import com.alpey.shop.response.OrderResponse;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;

	@Autowired
	UserRepository userRepository;

	public OrderResponse create(OrderRequest orderRequest) {
		try {
			Order order = parseOrder(orderRequest);
			Order storedOrder = orderRepository.save(order);
			return parseOrderResponse(storedOrder);
		} catch (EntityExistsException | NullPointerException e) {
			return new OrderResponse();
		}
	}

	public OrderResponse update(OrderRequest orderRequest, String orderNumber) {
		try {
			Order storedOrder = parseOrder(orderRequest, orderNumber);
			storedOrder = orderRepository.save(storedOrder);
			return parseOrderResponse(storedOrder);
		} catch (EntityNotFoundException | NullPointerException e) {
			return new OrderResponse();
		}
	}

	public OrderResponse findByNumber(String orderNumber) {
		try {
			Order order = orderRepository.findByOrderNumber(orderNumber);
			return parseOrderResponse(order);
		} catch (EntityNotFoundException | NullPointerException e) {
			return new OrderResponse();
		}
	}

	public List<OrderResponse> findByUser(String username) {
		try {
			User user = userRepository.findByUsername(username);
			List<Order> orders = orderRepository.findByUser(user);
			return parseOrderResponse(orders);
		} catch (EntityNotFoundException | NullPointerException e) {
			return new ArrayList<OrderResponse>();
		}
	}

	public List<OrderResponse> findByDate(LocalDate date) {
		try {
			List<Order> orders = orderRepository.findByOrderDate(date);
			return parseOrderResponse(orders);
		} catch (EntityNotFoundException | NullPointerException e) {
			return new ArrayList<OrderResponse>();
		}
	}

	public List<OrderResponse> findAll() {
		List<Order> orders = (List<Order>) orderRepository.findAll();
		return parseOrderResponse(orders);
	}

	public String delete(String orderNumber) {
		try {
			Order order = orderRepository.findByOrderNumber(orderNumber);
			orderRepository.delete(order);
			return "Order " + orderNumber + " deleted!";
		} catch (EntityNotFoundException | NullPointerException e) {
			return "Order not found!";
		}
	}

	private OrderResponse parseOrderResponse(Order order) {
		OrderResponse orderResponse = new OrderResponse();
		BeanUtils.copyProperties(order, orderResponse);
		orderResponse.setFirstName(order.getUser().getFirstName());
		orderResponse.setLastName(order.getUser().getLastName());
		return orderResponse;
	}

	private List<OrderResponse> parseOrderResponse(List<Order> orders) {
		List<OrderResponse> orderResponses = new ArrayList<OrderResponse>();
		orders.stream().forEach(order -> orderResponses.add(parseOrderResponse(order)));
		return orderResponses;
	}

	private Order parseOrder(OrderRequest orderRequest) {
		Order order = new Order();
		BeanUtils.copyProperties(orderRequest, order);
		order.setUser(userRepository.findByUsername(orderRequest.getUsername()));
		return order;
	}

	private Order parseOrder(OrderRequest orderRequest, String orderNumber) {
		Order order = orderRepository.findByOrderNumber(orderNumber);
		BeanUtils.copyProperties(orderRequest, order);
		order.setUser(userRepository.findByUsername(orderRequest.getUsername()));
		return order;
	}
}
