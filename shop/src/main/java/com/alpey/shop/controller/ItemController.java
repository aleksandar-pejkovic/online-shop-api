package com.alpey.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alpey.shop.entity.Item;
import com.alpey.shop.request.ItemRequest;
import com.alpey.shop.response.ItemResponse;
import com.alpey.shop.service.ItemService;

@CrossOrigin
@RestController
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	ItemService itemService;

	@PostMapping
	public Item create(@RequestBody ItemRequest item) {
		return itemService.create(item);
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable long id) {
		return itemService.delete(id);
	}
	
	@GetMapping("/{orderNumber}")
	public List<ItemResponse> findByOrder(@PathVariable long orderNumber) {
		return itemService.findByOrder(orderNumber);
	}
	
	
	
}
