package com.alpey.shop.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpey.shop.entity.Item;
import com.alpey.shop.entity.Order;
import com.alpey.shop.entity.Product;
import com.alpey.shop.repository.ItemRepository;
import com.alpey.shop.repository.OrderRepository;
import com.alpey.shop.repository.ProductRepository;
import com.alpey.shop.request.ItemRequest;
import com.alpey.shop.response.ItemResponse;

@Service
public class ItemService {

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	ProductRepository productRepository;

	public Item create(ItemRequest itemRequest) {
		try {
			Item item = new Item();
			Product product = productRepository.findByName(itemRequest.getProductName());
			Order order = orderRepository.findByOrderNumber(itemRequest.getOrderNumber());
			BeanUtils.copyProperties(itemRequest, item);
			item.setOrder(order);
			item.setProduct(product);
			item.calculateTotal();
			order.addItem(item);
			return itemRepository.save(item);
		} catch (EntityExistsException e) {
			return null;
		}
	}

	public Item update(Item item) {
		try {
			Item storedItem = itemRepository.findById(item.getId()).get();
			return itemRepository.save(storedItem);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	public List<ItemResponse> findByOrder(long orderNumber) {
		try {
			Order order = orderRepository.findByOrderNumber(orderNumber);
			List<Item> items = itemRepository.findByOrder(order);
			return convertToResponse(items);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	public String delete(long id) {
		try {
			Item item = itemRepository.findById(id).get();
			itemRepository.delete(item);
			return "Item deleted!";
		} catch (EntityNotFoundException e) {
			return "Item not found!";
		}
	}

	/*
	 * 
	 * public Item copyNotNullProperties(Item src, Item dest) { Order order =
	 * src.getOrder(); double amount = src.getAmount(); Product product =
	 * src.getProduct();
	 * 
	 * if (order != null) dest.setOrder(order); if (product != null)
	 * dest.setProduct(product); if(hasValue(amount)) dest.setAmount(amount);
	 * 
	 * dest.calculateTotal(); return dest; }
	 * 
	 * private boolean hasValue(double num) { if (num <= 0) { return false; } else {
	 * return true; } }
	 */
	
	private ItemResponse convertToResponse(Item item) {
		ItemResponse response = new ItemResponse();
		BeanUtils.copyProperties(item, response);
		response.setProductName(item.getProduct().getName());
		response.setPrice(item.getProduct().getPrice());
		return response;
	}
	
	private List<ItemResponse> convertToResponse(List<Item> items) {
		List<ItemResponse> response = new ArrayList<ItemResponse>();
		items.stream().forEach(item -> response.add(convertToResponse(item)));
		return response;
	}

}
