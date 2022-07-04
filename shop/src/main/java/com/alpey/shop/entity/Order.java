package com.alpey.shop.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique = true)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long orderNumber;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@JsonFormat(pattern = "dd.MM.yyyy.")
	private LocalDate orderDate;
	@JsonIgnore
	@OneToMany(mappedBy = "order")
	private List<Item> items;
	private double vat;
	private double totalBeforeTax;
	private double total;
	
	public List<Item> addItem(Item item){
		this.items.add(item);
		this.total += item.getTotal();
		this.vat += item.getVat();
		this.totalBeforeTax += item.getTotalBeforeTax();
		return this.items;
	}
	
	public List<Item> removeItem(Item item){
		this.items.remove(item);
		return this.items;
	}

}
