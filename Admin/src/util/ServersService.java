package util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;

import javax.sql.rowset.CachedRowSet;

import entity.Orders;
import entity.Products;

public class ServersService {
	private String serverIP;


	public ServersService() {
		serverIP=Config.getValue("serverIP");
	}

	
	
	
	//接收服务器产品信息
	public ResultSet GoodsInfo() {
		CachedRowSet crs;
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		Socket socket = null;
			
		try {
			socket=new Socket(serverIP,7777);
			oos=new ObjectOutputStream(socket.getOutputStream()); 
			oos.writeObject(1);
			oos.flush();
			ois=new ObjectInputStream(socket.getInputStream());
			crs=(CachedRowSet) ois.readObject();
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
					oos.close();
					ois.close();
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return crs;
	}
		
	//从服务器接收数据库中的所有订单信息
	public ResultSet ReceiveOrders(){
		CachedRowSet crs;
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		Socket socket = null;
		try {
			socket=new Socket(serverIP,7778);
			oos=new ObjectOutputStream(socket.getOutputStream()); 
			oos.writeObject(1);
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
	
	//向服务器发送添加的新产品信息
	public void sendNewProducts(Products newProducts) {
		Socket socket = null;
		ObjectOutputStream oos=null;
		try {
			socket=new Socket(serverIP,7779);
			oos=new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(newProducts);
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
	
	//向服务器发送删除的产品名
	public void	sendDeleteProducts(String kinds) {
		Socket socket = null;
		ObjectOutputStream oos=null;
		try {
			socket=new Socket(serverIP,7780);
			oos=new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(kinds);
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
	//向服务器发送修改的产品信息	
	public void sendUpdateProducts(Products products) {
		Socket socket = null;
		ObjectOutputStream oos=null;
		try {
			socket=new Socket(serverIP,7781);
			oos=new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(products);
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
	//向服务器发送处理过的订单信息
	public void sendUpdateOrders(Orders orders) {
		Socket socket = null;
		ObjectOutputStream oos=null;
		try {
			socket=new Socket(serverIP,7782);
			oos=new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(orders);
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
	//接收用户信息
		public ResultSet UsersInfo() {
			CachedRowSet crs;
			ObjectOutputStream oos = null;
			ObjectInputStream ois = null;
			Socket socket = null;
				
			try {
				socket=new Socket(serverIP,7783);
				oos=new ObjectOutputStream(socket.getOutputStream()); 
				oos.writeObject(1);
				oos.flush();
				ois=new ObjectInputStream(socket.getInputStream());
				crs=(CachedRowSet) ois.readObject();
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} finally {
				try {
						oos.close();
						ois.close();
					socket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return crs;
		}
		
}