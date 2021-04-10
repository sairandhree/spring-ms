package com.ms.boot.productms.model;

import java.io.Serializable;

public class DiscountRequest implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5093689614568882151L;
	private ProductCategory category;
	private double mrp;
	
	
	public DiscountRequest(ProductCategory category, double mrp) {
		super();
		this.category = category;
		this.mrp = mrp;
	}
	public DiscountRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "DiscountRequest [category=" + category + ", mrp=" + mrp + "]";
	}
	public ProductCategory getCategory() {
		return category;
	}
	public void setCategory(ProductCategory category) {
		this.category = category;
	}
	public double getMrp() {
		return mrp;
	}
	public void setMrp(double mrp) {
		this.mrp = mrp;
	}
	
	

}
