package com.yash.model;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class PizzaOrder {
	
	private String orderName;
	
	private Date orderDate;
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	@Override
	public String toString() {
		return "PizzaOrder [orderName=" + orderName + ", orderDate=" + orderDate + "]";
	}
	public PizzaOrder(String orderName, Date orderDate) {
		
		this.orderName = orderName;
		this.orderDate = orderDate;
	}
	public PizzaOrder() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
