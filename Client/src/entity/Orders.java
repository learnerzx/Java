package entity;

import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@SuppressWarnings("serial")
public class Orders implements Serializable{
	private int orderNO;
	private int cno;
	private double totalMoney;
	public Date orderTime;
	private String orderState;
	private String senderPhone;
	private String senderName;
	
	public Orders(int orderNO, int cno,  Date orderTime,double totalMoney, String orderState,String senderPhone,String senderName) {
		this.orderNO=orderNO;
		this.cno=cno;
		this.orderTime=orderTime;
		this.totalMoney=totalMoney;
		this.orderState=orderState;
		this.senderPhone=senderPhone;
		this.senderName=senderName;
	}
	public Orders() {
	}
	public int get_orderNO() {
		return orderNO;
	}
	public void set_orderNO(int orderNO) {
		this.orderNO = orderNO;
	}
	public void set_cno(int cno) {
		this.cno=cno;
	}
	public int get_cno() {
		return cno;
	}
	public void set_totalMoney(double totalMoney) {
		this.totalMoney=totalMoney;
	}
	public double get_totalMoney() {
		return totalMoney;
	}
	public java.util.Date get_orderTime() throws ParseException {
		SimpleDateFormat simpleformat = new SimpleDateFormat("yy-MM-dd hh:mm:ss.SSS");
		Date currentDate =new Date(System.currentTimeMillis());
		simpleformat.format(currentDate);
		this.orderTime=currentDate;
		return simpleformat.parse(simpleformat.format(currentDate));
	}
	public void set_orderState(String orderState) {
		this.orderState = orderState;
	}
	public String get_orderState() {
		return orderState;
	}
	public void set_senderPhone(String senderPhone) {
		this.senderPhone = senderPhone;
	}
	public String get_senderPhone() {
		return senderPhone;
	}
	public void set_senderName(String senderName) {
		this.senderName = senderName;
	}
	public String get_senderName() {
		return senderName;
	}

	
}
