package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import entity.Customers;
import entity.OrderItems;
import entity.Orders;
import util.DataBaseService;

public class UserServer {
	private DataBaseService dataBaseService = new DataBaseService();
	public boolean isServerclose = false;
	private AcceptRegisterThread acceptRegister = new AcceptRegisterThread(6666);
	private LoginInfoThread loginInfo = new LoginInfoThread(6667);
	private OrdersInfoThread ordersInfo = new OrdersInfoThread(6668);
	private SendProductsThread sendProducts = new SendProductsThread(6669);
	private AcceptOrdersInfoThread acceptOrdersInfo = new AcceptOrdersInfoThread(6670);
	private AcceptUserUpdateThread acceptUserUpdate = new AcceptUserUpdateThread(6671);
	private ClientLogOutThread clientlogout  = new ClientLogOutThread(6672);
	public UserServer() {
		acceptRegister.start();
		loginInfo.start();
		ordersInfo.start();
		sendProducts.start();
		acceptOrdersInfo.start();
		acceptUserUpdate.start();
		clientlogout.start();
	}

	// public void StartServer() {
	//
	// try {
	// if(!acceptRegister.getState().equals( Thread.State.TERMINATED)) {
	// acceptRegister.start();
	// loginInfo.start();
	// ordersInfo.start();
	// sendProducts.start();
	// acceptOrdersInfo.start();
	// }
	// System.out.println(acceptRegister.getState());
	// } catch (Exception e) {
	// System.out.println(acceptRegister.getState());
	// e.printStackTrace();
	// }
	// System.out.println("网络服务器已开启");
	// }
	// public void CloseServer() {
	// try {
	// acceptRegister.stopThisThread();
	//// loginInfo.stop();
	//// ordersInfo.stop();
	//// sendProducts.stop();
	//// sendProducts.stop();
	//// acceptOrdersInfo.stop();
	// isServerclose=true;
	// System.out.println(acceptRegister.getState());
	// }catch(Exception e) {
	// e.printStackTrace();
	// }
	// System.out.println("网络服务器已关闭");
	// }
	// 接收客户端注册信息
	private class AcceptRegisterThread extends Thread {
		private ServerSocket serverSocket;
		private Socket socket;
		private int port;
		private ObjectInputStream ois;
		private ObjectOutputStream oos;

		public AcceptRegisterThread(int port) {
			this.port = port;
		}

		// public void stopThisThread() {
		// this.flag=false;
		//
		// }
		public void run() {
			try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				e.printStackTrace();
			}

			while (this.isAlive()) {

				try {
					// 接受客户端发来的套接字
					socket = serverSocket.accept();
					System.out.println(socket);
					if (socket != null) {
						ois = new ObjectInputStream(socket.getInputStream());
						// 反序列化得到注册的用户信息
						Customers customers = (Customers) ois.readObject();
						// 将接收到的注册信息保存到数据库
						if (dataBaseService.judgeUserExist(customers.get_cname()) == false) {
							dataBaseService.saveCustomers(customers);
							oos = new ObjectOutputStream(socket.getOutputStream());
							oos.writeObject(true);
							oos.flush();
						} else {
							System.out.println(customers.get_cname());
							oos = new ObjectOutputStream(socket.getOutputStream());
							oos.writeObject(false);
							oos.flush();
						}
					}

				} catch (Exception e) {

					e.printStackTrace();
				}

			}
			// 关闭数据流及流式套接字
			try {
				ois.close();
				oos.close();
				socket.close();
			} catch (Exception e) {

				e.printStackTrace();
			}

		}
	}

	// 用户登录处理
	private class LoginInfoThread extends Thread {
		private ServerSocket serverSocket;
		private Socket socket;
		private ObjectInputStream ois;
		private ObjectOutputStream oos;
		private int port;

		public LoginInfoThread(int port) {
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
						String a[] = (String[]) ois.readObject();
						oos = new ObjectOutputStream(socket.getOutputStream());
						oos.writeObject(dataBaseService.findUserBynamepwd(a[0], a[1]));
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
	//用户登出处理
		private class ClientLogOutThread extends Thread {
			private ServerSocket serverSocket;
			private Socket socket;
			private ObjectInputStream ois;
			private int port;

			public ClientLogOutThread(int port) {

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
						if (socket != null) {
							ois = new ObjectInputStream(socket.getInputStream());
							// 反序列化得到的用户ID
							int cno=(int) ois.readObject();
							
							dataBaseService.updateUserOnlineState(cno);

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
	// 向客户端发送订单信息
	private class OrdersInfoThread extends Thread {
		private ServerSocket serverSocket;
		private Socket socket;
		private ObjectInputStream ois;
		private ObjectOutputStream oos;
		private int port;

		public OrdersInfoThread(int port) {
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
						int cno = (int) ois.readObject();
						oos = new ObjectOutputStream(socket.getOutputStream());
						oos.writeObject(dataBaseService.readOrdersResultClient(cno));
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

	// 向客户端发送产品信息
	private class SendProductsThread extends Thread {
		private ServerSocket serverSocket;
		private Socket socket;
		private ObjectInputStream ois;
		private ObjectOutputStream oos;
		private int port;

		public SendProductsThread(int port) {
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
						oos.writeObject(dataBaseService.readProducts());
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

	// 接受用户下单信息
	private class AcceptOrdersInfoThread extends Thread {
		private ServerSocket serverSocket;
		private Socket socket;
		private ObjectInputStream ois;
		private int port;

		public AcceptOrdersInfoThread(int port) {

			this.port = port;
		}

		public void run() {
			try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				e.printStackTrace();
			}
			while (this.isAlive()) {
				try {
					// 接受客户端发来的套接字
					socket = serverSocket.accept();
					System.out.println(socket);
					if (socket != null) {
						ois = new ObjectInputStream(socket.getInputStream());
						// 反序列化得到注册的用户信息
						Orders orders = (Orders) ois.readObject();
						@SuppressWarnings("unchecked")
						ArrayList<OrderItems> orderItems = (ArrayList<OrderItems>) ois.readObject();
						// 将接收到的注册信息保存到数据库
						dataBaseService.placeOrder(orders, orderItems);
						;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			// 关闭数据流及流式套接字
			try {
				ois.close();
				socket.close();
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
	}

	// 接受用户修改信息 数据
	private class AcceptUserUpdateThread extends Thread {
		private ServerSocket serverSocket;
		private Socket socket;
		private int port;
		private ObjectInputStream ois;
		private ObjectOutputStream oos;

		public AcceptUserUpdateThread(int port) {
			this.port = port;
		}

		public void run() {
			try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				e.printStackTrace();
			}

			while (this.isAlive()) {

				try {
					// 接受客户端发来的套接字
					socket = serverSocket.accept();
					System.out.println(socket);
					if (socket != null) {
						ois = new ObjectInputStream(socket.getInputStream());
						// 反序列化得到注册的用户信息
						Customers customers = (Customers) ois.readObject();
						// 将接收到的注册信息保存到数据库
						if (dataBaseService.judgeUserExist(customers.get_cname()) == false) {
							dataBaseService.updateUser(customers);
							oos = new ObjectOutputStream(socket.getOutputStream());
							oos.writeObject(true);
							oos.flush();
						} else {

							oos = new ObjectOutputStream(socket.getOutputStream());
							oos.writeObject(false);
							oos.flush();
						}
					}

				} catch (Exception e) {

					e.printStackTrace();
				}

			}
			// 关闭数据流及流式套接字
			try {
				ois.close();
				oos.close();
				socket.close();
			} catch (Exception e) {

				e.printStackTrace();
			}

		}
	}
	

}
