package com.alpey.shop.request;

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
public class OrderRequest {

	private long orderNumber;
	private String username;
	@JsonFormat(pattern = "dd.MM.yyyy.")
	private LocalDate orderDate;
	
}
