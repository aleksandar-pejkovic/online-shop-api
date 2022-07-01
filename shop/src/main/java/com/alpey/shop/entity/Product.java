package com.alpey.shop.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique = true)
	private String barcode;
	@Column(unique = true)
	private String name;
	private String unit;
	private double vatPercentage;
	private double vat;	//value added tax
	private double priceBeforeTax;
	private double price;
	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<Item> items;

	public double calculateVAT() {
		double recalculatedPercentage = (this.vatPercentage * 100) / (this.vatPercentage + 100) / 100; //a coefficient used for calculating VAT in full price
		this.vat = Math.round((this.price * recalculatedPercentage) * 100.0 / 100.0);
		this.priceBeforeTax = this.price - this.vat;
		return this.vat;
	}

}
