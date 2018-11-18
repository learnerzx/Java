package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import entity.Orders;
import entity.Products;
import util.DataBaseService;

public class AdminServer {
	private DataBaseService dataBaseService = new DataBaseService();

	private AdminGoodsInfoThread admingoods = new AdminGoodsInfoThread(7777);
	private AdminOrdersInfoThread adminorders = new AdminOrdersInfoThread(7778);
	private AdminNewGoodsInfoThread newGoods = new AdminNewGoodsInfoThread(7779);
	private AdminDeleteGoodsInfoThread deleteGoods = new AdminDeleteGoodsInfoThread(7780);
	private AdminUpdateGoodsInfoThread updateGoods = new AdminUpdateGoodsInfoThread(7781);
	private AdminUpdateOrdersInfoThread updateOrders = new AdminUpdateOrdersInfoThread(7782);
	private AdminUsersInfoThread usersInfo = new AdminUsersInfoThread(7783);
	public AdminServer() {
		admingoods.start();
		adminorders.start();
		newGoods.start();
		deleteGoods.start();
		updateGoods.start();
		updateOrders.start();
		usersInfo.start();
	}

	// 向管理端发送产品信息
	private class AdminGoodsInfoThread extends Thread {
		private ServerSocket serverSocket;
		private Socket socket;
		private ObjectInputStream ois;
		private ObjectOutputStream oos;
		private int port;

		public AdminGoodsInfoThread(int port) {
			this.port = port;
		}

		public void run() {
			try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			while (this.isAlive()) {
				try {
					socket = serverSocket.accept();

					if (socket != null) {
						ois = new ObjectInputStream(socket.getInputStream());
						ois.readObject();
						oos = new ObjectOutputStream(socket.getOutputStream());
						oos.writeObject(dataBaseService.readProductsResult());
						oos.flush();
					}

				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}

			}
			try {
				ois.close();
				oos.close();
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	// 向管理端发送订单信息
	private class AdminOrdersInfoThread extends Thread {
		private ServerSocket serverSocket;
		private Socket socket;
		private ObjectInputStream ois;
		private ObjectOutputStream oos;
		private int port;

		public AdminOrdersInfoThread(int port) {
			this.port = port;
		}

		public void run() {
			try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			while (this.isAlive()) {
				try {
					socket = serverSocket.accept();

					if (socket != null) {
						ois = new ObjectInputStream(socket.getInputStream());
						ois.readObject();
						oos = new ObjectOutputStream(socket.getOutputStream());
						oos.writeObject(dataBaseService.readOrdersResultServer());
						oos.flush();
					}

				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}

			}
			try {
				ois.close();
				oos.close();
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
	// 向管理端发送用户信息
	private class AdminUsersInfoThread extends Thread {
		private ServerSocket serverSocket;
		private Socket socket;
		private ObjectInputStream ois;
		private ObjectOutputStream oos;
		private int port;

		public AdminUsersInfoThread(int port) {
			this.port = port;
		}

		public void run() {
			try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			while (this.isAlive()) {
				try {
					socket = serverSocket.accept();

					if (socket != null) {
						ois = new ObjectInputStream(socket.getInputStream());
						ois.readObject();
						oos = new ObjectOutputStream(socket.getOutputStream());
						oos.writeObject(dataBaseService.readCustomers());
						oos.flush();
					}

				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}

			}
			try {
				ois.close();
				oos.close();
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	// 接收管理端发送的新添加的产品信息
	private class AdminNewGoodsInfoThread extends Thread {
		private ServerSocket serverSocket;
		private Socket socket;
		private ObjectInputStream ois;
		private int port;

		public AdminNewGoodsInfoThread(int port) {

			this.port = port;
		}

		public void run() {
			try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				while (this.isAlive()) {

					// 接受客户端发来的套接字
					socket = serverSocket.accept();
					System.out.println(socket);
					if (socket != null) {
						ois = new ObjectInputStream(socket.getInputStream());
						// 反序列化得到注册的用户信息
						Products products = (Products) ois.readObject();
						// 将接收到的注册信息保存到数据库
						dataBaseService.insertProducts(products);
						
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 关闭数据流及流式套接字
				try {
					ois.close();
					socket.close();
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}
	}

	// 接收管理端删除的产品名
	private class AdminDeleteGoodsInfoThread extends Thread {
		private ServerSocket serverSocket;
		private Socket socket;
		private ObjectInputStream ois;
		private int port;

		public AdminDeleteGoodsInfoThread(int port) {

			this.port = port;
		}

		public void run() {
			try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				while (this.isAlive()) {

					// 接受客户端发来的套接字
					socket = serverSocket.accept();
					System.out.println(socket);
					if (socket != null) {
						ois = new ObjectInputStream(socket.getInputStream());
						// 反序列化得到注册的用户信息
						String kinds = (String) ois.readObject();
						// 将接收到的注册信息保存到数据库
						dataBaseService.deleteProducts(kinds);
						
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 关闭数据流及流式套接字
				try {
					ois.close();
					socket.close();
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}
	}
	//接收管理端修改的产品信息
	private class AdminUpdateGoodsInfoThread extends Thread {
		private ServerSocket serverSocket;
		private Socket socket;
		private ObjectInputStream ois;
		private int port;

		public AdminUpdateGoodsInfoThread(int port) {

			this.port = port;
		}

		public void run() {
			try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				while (this.isAlive()) {

					// 接受客户端发来的套接字
					socket = serverSocket.accept();
					System.out.println(socket);
					if (socket != null) {
						ois = new ObjectInputStream(socket.getInputStream());
						// 反序列化得到注册的用户信息
						Products products = (Products) ois.readObject();
						// 将接收到的注册信息保存到数据库
						dataBaseService.updateProducts(products);
						
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 关闭数据流及流式套接字
				try {
					ois.close();
					socket.close();
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}
	}
	//接收管理端发送的处理过的订单信息
	private class AdminUpdateOrdersInfoThread extends Thread {
		private ServerSocket serverSocket;
		private Socket socket;
		private ObjectInputStream ois;
		private int port;

		public AdminUpdateOrdersInfoThread(int port) {

			this.port = port;
		}

		public void run() {
			try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				while (this.isAlive()) {

					// 接受客户端发来的套接字
					socket = serverSocket.accept();
					System.out.println(socket);
					if (socket != null) {
						ois = new ObjectInputStream(socket.getInputStream());
						// 反序列化得到注册的用户信息
						Orders orders=(Orders) ois.readObject();
						// 将接收到的注册信息保存到数据库
						dataBaseService.updateOrders(orders);

					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// 关闭数据流及流式套接字
				try {
					ois.close();
					socket.close();
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}
	}
	
}
