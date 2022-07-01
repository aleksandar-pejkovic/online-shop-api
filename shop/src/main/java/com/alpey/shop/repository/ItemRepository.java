package com.alpey.shop.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alpey.shop.entity.Item;
import com.alpey.shop.entity.Order;
import com.alpey.shop.entity.Product;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {

	public List<Item> findByOrder(Order order);
	public List<Item> findByProduct(Product product);
	
}
