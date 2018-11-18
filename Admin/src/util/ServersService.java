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

	
	
	
	//���շ�������Ʒ��Ϣ
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
		
	//�ӷ������������ݿ��е����ж�����Ϣ
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
	
	//�������������ӵ��²�Ʒ��Ϣ
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
	
	//�����������ɾ���Ĳ�Ʒ��
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
	//������������޸ĵĲ�Ʒ��Ϣ	
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
	//����������ʹ�����Ķ�����Ϣ
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
	//�����û���Ϣ
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