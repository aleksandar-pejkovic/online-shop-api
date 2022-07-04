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

import com.alpey.shop.request.ProductRequest;
import com.alpey.shop.response.ProductResponse;
import com.alpey.shop.service.ProductService;

@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@PostMapping
	public ProductResponse create(@RequestBody ProductRequest product) {
		return productService.create(product);
	}
	
	@PutMapping("/{name}")
	public ProductResponse update(@RequestBody ProductRequest product, @PathVariable String name) {
		return productService.update(product, name);
	}
	
	@GetMapping
	public List<ProductResponse> findAll() {
		return productService.findAll();
	}
	
	@GetMapping("/{name}")
	public ProductResponse findByName(@PathVariable String name) {
		return productService.findByName(name);
	}
	
	@GetMapping("/barcode/{barcode}")
	public ProductResponse findByBarcode(@PathVariable String barcode) {
		return productService.findByBarcode(barcode);
	}
	
	@GetMapping("/{operator}/{price}")
	public List<ProductResponse> findByPrice(@PathVariable char operator, @PathVariable double price) {
		return productService.findByPrice(operator, price);
	}
	
	@DeleteMapping("/{name}")
	public String delete(@PathVariable String name) {
		return productService.delete(name);
	}

}
