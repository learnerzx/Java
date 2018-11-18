package entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Products implements Serializable{
	private String kind;
	private double price;
	
	public Products(String kind, double price) {
		this.kind=kind;
		this.price=price;
	}
	public Products() {
	}
	public String get_kind() {
		return kind;
	}
	public void set_kind(String kind) {
		this.kind = kind;
	}
	public double get_price() {
		return price;
	}
	public void set_price(double price) {
		this.price = price;
	}
}
