package com.alpey.shop.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ItemResponse {
	
	private double amount;
	private String productName;
	private double price;
	private double vat;
	private double totalBeforeTax;
	private double total;

}
