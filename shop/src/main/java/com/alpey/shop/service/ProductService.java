package com.alpey.shop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpey.shop.entity.Product;
import com.alpey.shop.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	public Product create(Product product) {
		try {
			return productRepository.save(product);
		} catch (EntityExistsException e) {
			return null;
		}
	}

	public Product update(Product product, String oldName) {
		try {
			Product storedProduct = productRepository.findByName(oldName);
			BeanUtils.copyProperties(product, storedProduct, "id");
			return productRepository.save(storedProduct);
		} catch (Exception e) {
			return null;
		}
	}

	public Product findByName(String name) {
		try {
			return productRepository.findByName(name);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	public Product findByBarcode(String barcode) {
		try {
			return productRepository.findByBarcode(barcode);
		} catch (EntityNotFoundException e) {
			return null;
		}
	}

	/*
	 * public List<Product> findByPrice(double price) { return
	 * productRepository.findByPrice(price); }
	 */

	public List<Product> findAll() {
		return (List<Product>) productRepository.findAll();
	}

	public List<Product> findByPrice(char operator, double price) {
		List<Product> allProducts = findAll();
		List<Product> shortlist = new ArrayList<Product>();
		if (operator == '<') {
			shortlist = allProducts.stream().filter(t -> t.getPrice() <= price).collect(Collectors.toList());
		} else if (operator == '>') {
			shortlist = allProducts.stream().filter(t -> t.getPrice() > price).collect(Collectors.toList());
		} else {
			shortlist = productRepository.findByPrice(price);
		}
		return shortlist;
	}

	public String delete(String name) {
		try {
			Product product = productRepository.findByName(name);
			productRepository.delete(product);
			return "Product " + name + " deleted!";
		} catch (EntityNotFoundException e) {
			return "Product " + name + " doesn't exist!";
		}
	}

	/*
	 * public Product copyNotNullProperties(Product src, Product dest) { String
	 * barcode = src.getBarcode(); String name = src.getName(); String unit =
	 * src.getUnit(); double vatPercentage = src.getVatPercentage(); double price =
	 * src.getPrice();
	 * 
	 * if (hasValue(barcode)) dest.setBarcode(barcode); if (hasValue(name))
	 * dest.setName(name); if (hasValue(unit)) dest.setUnit(unit); if
	 * (hasValue(vatPercentage)) dest.setVatPercentage(vatPercentage); if
	 * (hasValue(price)) dest.setPrice(price); dest.calculateVAT(); return dest; }
	 * 
	 * private boolean hasValue(String str) { if (str.equals("") ||
	 * str.equals(null)) { return false; } else { return true; } }
	 * 
	 * private boolean hasValue(double num) { if (num <= 0) { return false; } else {
	 * return true; } }
	 */

}
