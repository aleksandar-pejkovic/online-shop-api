package com.alpey.shop.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductRequest {

	private String barcode;
	private String name;
	private String unit;
	private double vatPercentage;
	private double price;

}
