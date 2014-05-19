package com.example.model;

import java.math.BigDecimal;
import java.util.Map;

public class Category {
	private Map<Product, Integer> list;
	private String name;
	
	

	public Category(Map<Product, Integer> list, String name) {
		super();
		this.list = list;
		this.name = name;
	}



	@Override
	public String toString() {
		return "Category [" + list + ", " + name + "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((list == null) ? 0 : list.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Category other = (Category) obj;
		if (list == null) {
			if (other.list != null)
				return false;
		} else if (!list.equals(other.list))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}



	public Map<Product, Integer> getList() {
		return list;
	}



	public void setList(Map<Product, Integer> list) {
		this.list = list;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}
	
	public BigDecimal getTotal(){
		BigDecimal total = new BigDecimal("0.00");
		for(Product pro: list.keySet()){
			total = total.add(pro.getPrice().multiply(new BigDecimal(list.get(pro))));
		}
		return total;
	}

}
