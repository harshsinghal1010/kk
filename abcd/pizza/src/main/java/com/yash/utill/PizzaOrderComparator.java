package com.yash.utill;

import java.util.Comparator;

import com.yash.model.PizzaOrder;

public class PizzaOrderComparator implements Comparator<PizzaOrder>{

	@Override
	public int compare(PizzaOrder o1, PizzaOrder o2) {
		// TODO Auto-generated method stub
		
		int sort_order = o1.getOrderName().compareTo(o2.getOrderName());
		
		// if same order name then sort by order date
		if(sort_order==0)
			return o1.getOrderDate().compareTo(o2.getOrderDate());
		
		
		return sort_order;
	}

}
