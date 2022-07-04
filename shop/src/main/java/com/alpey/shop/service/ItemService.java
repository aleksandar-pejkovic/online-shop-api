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

	public ItemResponse create(ItemRequest itemRequest) {
		try {
			Item item = parseItem(itemRequest);
			Item storedItem = itemRepository.save(item);
			return parseItemResponse(storedItem);
		} catch (EntityExistsException | NullPointerException | IllegalArgumentException e) {
			return new ItemResponse();
		}
	}

	/*
	 * public Item update(Item item) { try { Item storedItem =
	 * itemRepository.findById(item.getId()).get(); return
	 * itemRepository.save(storedItem); } catch (EntityNotFoundException |
	 * NullPointerException e) { return null; } }
	 */

	public List<ItemResponse> findByOrder(String orderNumber) {
		try {
			Order order = orderRepository.findByOrderNumber(orderNumber);
			List<Item> items = itemRepository.findByOrder(order);
			return parseItemResponse(items);
		} catch (EntityNotFoundException | NullPointerException | IllegalArgumentException e) {
			return new ArrayList<ItemResponse>();
		}
	}
	
	public String delete(long id) {
		try {
			Item item = itemRepository.findById(id).get();
			Order order = orderRepository.findByOrderNumber(item.getOrder().getOrderNumber());
			order.removeItem(item);
			itemRepository.delete(item);
			return "Item deleted!";
		} catch (EntityNotFoundException | NullPointerException | IllegalArgumentException e) {
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

	private Item parseItem(ItemRequest itemRequest) {
		Item item = new Item();
		BeanUtils.copyProperties(itemRequest, item);
		Order order = orderRepository.findByOrderNumber(itemRequest.getOrderNumber());
		Product product = productRepository.findByName(itemRequest.getProductName());
		item.setOrder(order);
		item.setProduct(product);
		item.calculateTotal();
		order.addItem(item);
		return item;
	}

	private ItemResponse parseItemResponse(Item item) {
		ItemResponse itemResponse = new ItemResponse();
		BeanUtils.copyProperties(item, itemResponse);
		itemResponse.setProductName(item.getProduct().getName());
		itemResponse.setPrice(item.getProduct().getPrice());
		return itemResponse;
	}

	private List<ItemResponse> parseItemResponse(List<Item> items) {
		List<ItemResponse> itemResponse = new ArrayList<ItemResponse>();
		items.stream().forEach(item -> itemResponse.add(parseItemResponse(item)));
		return itemResponse;
	}

}
