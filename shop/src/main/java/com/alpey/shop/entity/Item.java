package com.alpey.shop.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "items")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
	private double amount;
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	private double vat;
	private double totalBeforeTax;
	private double total;

	public Item(Order order, double amount, Product product) {
		this.order = order;
		this.amount = amount;
		this.product = product;
		calculateTotal();
	}

	public void calculateTotal() {
		this.vat = Math.round(this.amount * this.product.getVat() * 100.0 / 100.0);
		this.totalBeforeTax = Math.round(this.amount * this.product.getPriceBeforeTax() * 100.0 / 100.0);
		this.total = Math.round(this.amount * this.product.getPrice() * 100.0 / 100.0);
	}

}
