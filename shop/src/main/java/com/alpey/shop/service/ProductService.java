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
import com.alpey.shop.request.ProductRequest;
import com.alpey.shop.response.ProductResponse;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	public ProductResponse create(ProductRequest productRequest) {
		try {
			Product storedProduct = productRepository.save(parseProduct(productRequest));
			return parseProductResponse(storedProduct);
		} catch (EntityExistsException | IllegalArgumentException | NullPointerException e) {
			return null;
		}
	}

	public ProductResponse update(ProductRequest productRequest, String oldName) {
		try {
			Product storedProduct = parseProduct(productRequest, oldName);
			BeanUtils.copyProperties(productRequest, storedProduct);
			storedProduct = productRepository.save(storedProduct);
			return parseProductResponse(storedProduct);
		} catch (EntityNotFoundException | NullPointerException | IllegalArgumentException e) {
			return new ProductResponse();
		}
	}

	public ProductResponse findByName(String name) {
		try {
			Product product = productRepository.findByName(name);
			return parseProductResponse(product);
		} catch (EntityNotFoundException | NullPointerException | IllegalArgumentException e) {
			return new ProductResponse();
		}
	}

	public ProductResponse findByBarcode(String barcode) {
		try {
			Product product = productRepository.findByBarcode(barcode);
			return parseProductResponse(product);
		} catch (EntityNotFoundException | NullPointerException | IllegalArgumentException e) {
			return new ProductResponse();
		}
	}

	/*
	 * public List<Product> findByPrice(double price) { return
	 * productRepository.findByPrice(price); }
	 */

	public List<ProductResponse> findAll() {
		List<Product> products = (List<Product>) productRepository.findAll();
		return parseProductResponse(products);
	}

	public List<ProductResponse> findByPrice(char operator, double price) {
		try {
			List<Product> allProducts = (List<Product>) productRepository.findAll();
			List<Product> shortlist = new ArrayList<Product>();
			if (operator == '<') {
				shortlist = allProducts.stream().filter(t -> t.getPrice() <= price).collect(Collectors.toList());
			} else if (operator == '>') {
				shortlist = allProducts.stream().filter(t -> t.getPrice() > price).collect(Collectors.toList());
			} else {
				shortlist = productRepository.findByPrice(price);
			}
			return parseProductResponse(shortlist);
		} catch (EntityNotFoundException | NullPointerException | IllegalArgumentException e) {
			return new ArrayList<ProductResponse>();
		}
	}

	public String delete(String name) {
		try {
			Product product = productRepository.findByName(name);
			productRepository.delete(product);
			return "Product " + name + " deleted!";
		} catch (EntityNotFoundException | NullPointerException | IllegalArgumentException e) {
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
	
	private Product parseProduct(ProductRequest productRequest) {
		Product product = new Product();
		BeanUtils.copyProperties(productRequest, product);
		product.calculateVAT();
		return product;
	}
	private Product parseProduct(ProductRequest productRequest, String oldName) {
		Product product = productRepository.findByName(oldName);
		BeanUtils.copyProperties(productRequest, product);
		product.calculateVAT();
		return product;
	}
	
	private ProductResponse parseProductResponse(Product product) {
		ProductResponse productResponse = new ProductResponse();
		BeanUtils.copyProperties(product, productResponse);
		return productResponse;
	}
	
	private List<ProductResponse> parseProductResponse(List<Product> products) {
		List<ProductResponse> productResponses = new ArrayList<ProductResponse>();
		products.stream().forEach(product -> productResponses.add(parseProductResponse(product)));
		return productResponses;
	}

}
