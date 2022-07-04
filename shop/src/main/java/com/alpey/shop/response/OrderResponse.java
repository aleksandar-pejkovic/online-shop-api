package com.alpey.shop.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderResponse {

	private String orderNumber;
	private String firstName;
	private String lastName;
	@JsonFormat(pattern = "dd.MM.yyyy.")
	private LocalDate orderDate;
	private double vat;
	private double totalBeforeTax;
	private double total;
	
}
