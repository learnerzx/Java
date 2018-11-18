package util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import entity.Customers;
import entity.OrderItems;
import entity.Orders;
import entity.Products;

public class ClientService {
	private String serverIP;
	private int sendRegisterPort;
	private int LoginInfoPort;
	private int ReceiveOrdersPort;
	private int ReceiveProductsPort;
	private int sendOrdersPort;
	private int sendUserUpdatePort;

	public ClientService() {
		serverIP=Config.getValue("serverIP");
		sendRegisterPort=Integer.parseInt(Config.getValue("sendRegisterPort"));
		LoginInfoPort=Integer.parseInt(Config.getValue("LoginInfoPort"));
		ReceiveOrdersPort=Integer.parseInt(Config.getValue("ReceiveOrdersPort"));
		ReceiveProductsPort=Integer.parseInt(Config.getValue("ReceiveProductsPort"));
		sendOrdersPort=Integer.parseInt(Config.getValue("sendOrdersPort"));
		sendUserUpdatePort=Integer.parseInt(Config.getValue("sendUserUpdatePort"));
	}

	
	//发送用户注册信息，接收用户名是否重复布尔值 
	public boolean sendRegisterCustomers(Customers customers) throws UnknownHostException, IOException {
		Socket socket = null;
		socket = new Socket(serverIP, sendRegisterPort);
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		boolean state = true;
		try {
			oos= new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(customers);
			oos.flush();
			ois=new ObjectInputStream(socket.getInputStream());
			state=(boolean) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
				oos.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return state;

	}
	
	
	//用户登陆信息处理
	public Customers LoginInfo(String cname,String password) throws UnknownHostException, IOException  {
		String a[]= {cname,password};
		Customers customers = null ;
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		Socket socket;
	
			socket=new Socket(serverIP,LoginInfoPort);
		try {
			oos=new ObjectOutputStream(socket.getOutputStream()); 
			oos.writeObject(a);
			oos.flush();
			ois=new ObjectInputStream(socket.getInputStream());
			customers=(Customers) ois.readObject();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null && ois != null) {
					oos.close();
					ois.close();
				}
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return customers;
	}
		
	public void LogoutInfo(int cno)   {
		ObjectOutputStream oos = null;
		Socket socket = null;
		try {
			socket=new Socket(serverIP,6672);
			oos=new ObjectOutputStream(socket.getOutputStream()); 
			oos.writeObject(cno);
			oos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null) {
					oos.close();
				}
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//接收用户订单信息
	public ResultSet ReceiveOrders(int cno){
		CachedRowSet crs;
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		Socket socket = null;
		try {
			socket=new Socket(serverIP,ReceiveOrdersPort);
			oos=new ObjectOutputStream(socket.getOutputStream()); 
			oos.writeObject(cno);
			oos.flush();
			ois=new ObjectInputStream(socket.getInputStream());
			crs= (CachedRowSet) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			}finally {
		
		try {
			oos.close();
			ois.close();
			socket.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
			}
		return crs;
	}
	
	//接收商品信息
	@SuppressWarnings("unchecked")
	public ArrayList<Products> ReceiveProducts(){
		ArrayList<Products> products=new ArrayList<Products>();
		ObjectOutputStream oos;
		ObjectInputStream ois;
		Socket socket;
		try {
			socket=new Socket(serverIP,ReceiveProductsPort);
			oos=new ObjectOutputStream(socket.getOutputStream()); 
			oos.writeObject(1);
			oos.flush();
			
			ois=new ObjectInputStream(socket.getInputStream());
			products=  (ArrayList<Products>) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
			}
		
		try {
			oos.close();
			ois.close();
			socket.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return products;
	}
	
	//发送下单信息
	public void sendOrdersInfo(Orders orders,ArrayList<OrderItems> orderItems) {
		Socket socket = null;
		ObjectOutputStream oos=null;
		try {
			socket=new Socket(serverIP,sendOrdersPort);
			oos=new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(orders);
			oos.writeObject(orderItems);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				oos.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	//发送用户修改的个人信息，接收数据库是否有重名的布尔值
	public boolean sendUserUpdateInfo(Customers customers) throws UnknownHostException, IOException {
		Socket socket =new Socket(serverIP, sendUserUpdatePort);
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		boolean state = true;
		try {
			oos= new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(customers);
			oos.flush();
			ois=new ObjectInputStream(socket.getInputStream());
			state=(boolean) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
				oos.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return state;
	}
}