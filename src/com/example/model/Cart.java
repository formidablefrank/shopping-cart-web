package com.example.model;

import java.math.BigDecimal;
import java.util.Map;

public class Cart {
	private Map<Product, Integer> list;
	private String user;
	private BigDecimal amount;
	
	public Map<Product, Integer> getList() {
		return list;
	}
	public void setList(Map<Product, Integer> list) {
		this.list = list;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((list == null) ? 0 : list.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Cart other = (Cart) obj;
		if (list == null) {
			if (other.list != null)
				return false;
		} else if (!list.equals(other.list))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Cart [" + list + ", " + user + ", " + "]";
	}
	
	public Cart(Map<Product, Integer> list, String user, BigDecimal amount) {
		super();
		this.list = list;
		this.user = user;
		this.amount = amount;
	}
	
	public BigDecimal getTotal(){
		BigDecimal total = BigDecimal.ZERO;
		for(Product pro: list.keySet()){
			total = total.add(pro.getPrice().multiply(new BigDecimal(list.get(pro))));
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
