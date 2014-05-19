package com.example.model;

import java.math.BigDecimal;
import java.util.List;


public class Inventory {
	private List<Category> categories;
	private BigDecimal amount;

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((categories == null) ? 0 : categories.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inventory other = (Inventory) obj;
		if (categories == null) {
			if (other.categories != null)
				return false;
		} else if (!categories.equals(other.categories))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Inventory [" + categories + "]";
	}

	public Inventory(List<Category> categories) {
		super();
		this.categories = categories;
	}
	
	public BigDecimal getTotal(){
		BigDecimal total = new BigDecimal("0.00");
		for(Category cat: categories){
			total = total.add(cat.getTotal());
		}
		amount = total;
		return total;
	}

	public BigDecimal getAmount() {
		getTotal();
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
