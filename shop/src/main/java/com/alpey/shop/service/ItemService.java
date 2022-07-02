package com.alpey.shop.service;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpey.shop.entity.Item;
import com.alpey.shop.entity.Order;
import com.alpey.shop.entity.Product;
import com.alpey.shop.repository.ItemRepository;
import com.alpey.shop.repository.OrderRepository;

@Service
public class ItemService {

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	OrderRepository orderRepository;

	public Item create(Item item) {
		try {
			return itemRepository.save(item);
		} catch (EntityExistsException e) {
			return null;
		}
	}

	public Item update(Item item) {
		try {
			Item storedItem = itemRepository.findById(item.getId()).get();
			copyNotNullProperties(item, storedItem);
			return itemRepository.save(storedItem);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	public List<Item> findByOrder(long orderNumber) {
		try {
			Order order = orderRepository.findByOrderNumber(orderNumber);
			return itemRepository.findByOrder(order);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}
	
	public String delete(Item item) {
		try {
			itemRepository.delete(item);
			return "Item deleted!";
		} catch (EntityNotFoundException e) {
			return "Item not found!";
		}
	}

	public Item copyNotNullProperties(Item src, Item dest) {
		Order order = src.getOrder();
		double amount = src.getAmount();
		Product product = src.getProduct();

		if (order != null)
			dest.setOrder(order);
		if (product != null)
			dest.setProduct(product);
		if(hasValue(amount))
			dest.setAmount(amount);

		dest.calculateTotal();
		return dest;
	}

	private boolean hasValue(double num) {
		if (num <= 0) {
			return false;
		} else {
			return true;
		}
	}

}
