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

	// �����˷��Ͳ�Ʒ��Ϣ
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
				// TODO �Զ����ɵ� catch ��
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
					// TODO �Զ����ɵ� catch ��
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

	// �����˷��Ͷ�����Ϣ
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
				// TODO �Զ����ɵ� catch ��
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
					// TODO �Զ����ɵ� catch ��
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
	// �����˷����û���Ϣ
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
				// TODO �Զ����ɵ� catch ��
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
					// TODO �Զ����ɵ� catch ��
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

	// ���չ���˷��͵�����ӵĲ�Ʒ��Ϣ
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

					// ���ܿͻ��˷������׽���
					socket = serverSocket.accept();
					System.out.println(socket);
					if (socket != null) {
						ois = new ObjectInputStream(socket.getInputStream());
						// �����л��õ�ע����û���Ϣ
						Products products = (Products) ois.readObject();
						// �����յ���ע����Ϣ���浽���ݿ�
						dataBaseService.insertProducts(products);
						
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// �ر�����������ʽ�׽���
				try {
					ois.close();
					socket.close();
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}
	}

	// ���չ����ɾ���Ĳ�Ʒ��
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

					// ���ܿͻ��˷������׽���
					socket = serverSocket.accept();
					System.out.println(socket);
					if (socket != null) {
						ois = new ObjectInputStream(socket.getInputStream());
						// �����л��õ�ע����û���Ϣ
						String kinds = (String) ois.readObject();
						// �����յ���ע����Ϣ���浽���ݿ�
						dataBaseService.deleteProducts(kinds);
						
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// �ر�����������ʽ�׽���
				try {
					ois.close();
					socket.close();
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}
	}
	//���չ�����޸ĵĲ�Ʒ��Ϣ
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

					// ���ܿͻ��˷������׽���
					socket = serverSocket.accept();
					System.out.println(socket);
					if (socket != null) {
						ois = new ObjectInputStream(socket.getInputStream());
						// �����л��õ�ע����û���Ϣ
						Products products = (Products) ois.readObject();
						// �����յ���ע����Ϣ���浽���ݿ�
						dataBaseService.updateProducts(products);
						
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// �ر�����������ʽ�׽���
				try {
					ois.close();
					socket.close();
				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}
	}
	//���չ���˷��͵Ĵ�����Ķ�����Ϣ
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

					// ���ܿͻ��˷������׽���
					socket = serverSocket.accept();
					System.out.println(socket);
					if (socket != null) {
						ois = new ObjectInputStream(socket.getInputStream());
						// �����л��õ�ע����û���Ϣ
						Orders orders=(Orders) ois.readObject();
						// �����յ���ע����Ϣ���浽���ݿ�
						dataBaseService.updateOrders(orders);

					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// �ر�����������ʽ�׽���
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
