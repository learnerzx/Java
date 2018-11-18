package entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class OrderItems implements Serializable{
	private int orderNO;
	private String kind;
	private double price;
	private int qty;
	private double subTotal;
	
	public OrderItems( int orderNO,String kind, double price, int qty, double subTotal) {
		this.orderNO=orderNO;
		this.kind=kind;
		this.price=price;
		this.qty=qty;
		this.subTotal=subTotal;
	}
	public OrderItems( String kind, double price, int qty, double subTotal) {

		this.kind=kind;
		this.price=price;
		this.qty=qty;
		this.subTotal=subTotal;
	}
	public OrderItems() {
		
	}
	public void set_orderNO(int orderNO) {
		this.orderNO=orderNO;
	}
	public int get_orderNO() {
		return orderNO;
	}
	public void set_kind(String kind) {
		this.kind=kind;
	}
	public String get_kind() {
		return kind;
	}
	public void set_price(double price) {
		this.price=price;
	}
	public double get_price() {
		return price;
	}
	public void set_qty(int qty) {
		this.qty=qty;
	}
	public int get_qty() {
		return qty;
	}
	public void set_subTotal(double subTotal) {
		this.subTotal=subTotal;
	}
	public double get_subTotal() {
		return subTotal;
	}
	
}
