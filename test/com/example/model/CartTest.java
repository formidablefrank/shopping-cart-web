package com.example.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class CartTest {

	@Test
	public void testGetTotal() {
		Map<Product, Integer> actualCart = new LinkedHashMap<>();
		actualCart.put(new Product(null, null, new BigDecimal("1.00"), null), 5);
		actualCart.put(new Product(null, null, new BigDecimal("2.50"), null), 4);
		
		BigDecimal expectedTotal = new BigDecimal("15.00");
		assertEquals(expectedTotal, new Cart(actualCart, "", null).getTotal());
	}

}
