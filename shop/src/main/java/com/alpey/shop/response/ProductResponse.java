package com.alpey.shop.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductResponse {

	private String barcode;
	private String name;
	private String unit;
	private double vatPercentage;
	private double vat; // value added tax
	private double priceBeforeTax;
	private double price;

}
