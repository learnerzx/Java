package entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Customers implements Serializable{
	private int cno;
	private String cname;
	private String password;
	private String address;
	private String phone;
	private String online;
	public Customers(int cno,String cname,String password,String address,String phone) {
		this.cno=cno;
		this.cname=cname;
		this.password=password;
		this.address=address;
		this.phone=phone;
	}
	public Customers(String cname,int cno,String phone,String address,String online) {
		this.cname=cname;
		this.cno=cno;
		this.phone=phone;
		this.address=address;
		this.online=online;
	}
	public Customers() {}
	public void set_cno(int cno) {
		this.cno=cno;
	}
	public int get_cno() {
		return cno;
	}
	public void set_cname(String cname) {
		this.cname=cname;
	}
	public String get_cname() {
		return cname;
	}
	public void set_phone(String phone) {
		this.phone=phone;
	}
	public String get_phone() {
		return phone;
	}
	public void set_address(String address) {
		this.address=address;
	}
	public String get_address() {
		return address;
	}
	public void set_password(String password) {
		this.password=password;
	}
	public String get_password() {
		return password;
	}
	public String get_Online() {
		return online;
	}
	public void set_Online(String online) {
		this.online = online;
	}

}
