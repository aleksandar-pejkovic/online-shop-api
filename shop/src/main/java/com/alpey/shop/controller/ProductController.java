package com.alpey.shop.controller;

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

import com.alpey.shop.entity.Product;
import com.alpey.shop.service.ProductService;

@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@PostMapping
	public Product create(@RequestBody Product product) {
		return productService.create(product);
	}
	
	@PutMapping("/{name}")
	public Product update(@RequestBody Product product, @PathVariable String name) {
		return productService.update(product, name);
	}
	
	@GetMapping("/{name}")
	public Product findByName(@PathVariable String name) {
		return productService.findByName(name);
	}
	
	@GetMapping("/barcode/{barcode}")
	public Product findByBarcode(@PathVariable String barcode) {
		return productService.findByBarcode(barcode);
	}
	
	@GetMapping("/{operator}/{price}")
	public List<Product> findByPrice(@PathVariable char operator, @PathVariable double price) {
		return productService.findByPrice(operator, price);
	}
	
	@DeleteMapping("/{name}")
	public String delete(@PathVariable String name) {
		return productService.delete(name);
	}

}
