package util;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;

import dbc.Dbc;
import entity.Customers;
import entity.OrderItems;
import entity.Orders;
import entity.Products;

public class DataBaseService {
	private Dbc dbc = new Dbc();
	private Connection con = null;

	public DataBaseService() {

		try {
			this.con = dbc.getConnection();
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}

	public void saveCustomers(Customers customers) {
		try {
			String Insertsql = "INSERT INTO customers(cname,password,address,phone) VALUES(?,?,?,?)";
			Object[] param = new Object[] { customers.get_cname(), customers.get_password(), customers.get_address(),
					customers.get_phone() };

			dbc.executeUpdate(Insertsql, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveOrderItems(OrderItems orderItems) throws SQLException {

		String Insertsql = "INSERT INTO OrderItems(orderNO,kind,price,qty,subTotal) VALUES(?,?,?,?,?)";
		dbc.executeUpdate(Insertsql, new Object[] { orderItems.get_orderNO(), orderItems.get_kind(),
				orderItems.get_price(), orderItems.get_qty(), orderItems.get_subTotal() });
	}

	public void saveOrders(Orders orders) throws SQLException {

		String Insertsql = "INSERT INTO Orders(cno,orderTime,totalMoney,orderState,senderPhone,senderName) VALUES(?,?,?,?,?,?) ";
		try {
			dbc.executeUpdate(Insertsql,
					new Object[] { orders.get_cno(), orders.get_orderTime(), orders.get_totalMoney(),
							orders.get_orderState(), orders.get_senderPhone(), orders.get_senderName() });
		} catch (ParseException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}

	}
	//�����ݿ��ȡ�ͻ�������Ϣ
	public CachedRowSet readCustomers() {
		try {
			String Selectsql = "SELECT cno,cname,phone,address FROM Customers WHERE online=?";
			ResultSet rs = dbc.executeQuery(Selectsql, new Object[] {"true"});
			CachedRowSetImpl crs = new CachedRowSetImpl();
			crs.populate(rs);
			return crs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

//	public List<Customers> readCustomers() {
//		List<Customers> customers = new ArrayList<Customers>();
//
//		try {
//			String Selectsql = "SELECT cno,cname,phone,address,online FROM Customers";
//			ResultSet rs = dbc.executeQuery(Selectsql, null);
//			while (rs.next()) {
//				Customers customer = new Customers(rs.getString(2), rs.getInt(1), rs.getString(3), rs.getString(4),
//						rs.getString(5));
//				customers.add(customer);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return customers;
//	}

//
//	public void saveProducts(Products products) {
//
//		try {
//
//			String Insertsql = "INSERT INTO Procucts(kind,price) VALUES(?,?)";
//			dbc.executeUpdate(Insertsql, new Object[] { products.get_kind(), products.get_price() });
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}


//	public ArrayList<OrderItems> readOrderItems() {
//		ArrayList<OrderItems> orderitems = new ArrayList<OrderItems>();
//
//		try {
//
//			String Selectsql = "SELECT * FROM OrderItems";
//			ResultSet rs = dbc.executeQuery(Selectsql, null);
//			while (rs.next()) {
//				OrderItems orderitem = new OrderItems(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getInt(4),
//						rs.getDouble(5));
//				orderitems.add(orderitem);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return orderitems;
//	}

//	public ArrayList<Orders> readOrders() {
//		ArrayList<Orders> orders = new ArrayList<Orders>();
//
//		try {
//			String Selectsql = "SELECT * FROM Orders";
//			ResultSet rs = dbc.executeQuery(Selectsql, null);
//			while (rs.next()) {
//				Orders order = new Orders(rs.getInt(1), rs.getInt(2), rs.getDate(3), rs.getDouble(4), rs.getString(5),
//						rs.getString(6), rs.getString(7));
//				orders.add(order);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return orders;
//	}
	
	
	//�����ݿ������Ʒ��������ڷ��Ϳͻ���
	public ArrayList<Products> readProducts() {
		ArrayList<Products> products = new ArrayList<Products>();

		try {

			String Selectsql = "SELECT * FROM Products";
			ResultSet rs = dbc.executeQuery(Selectsql, null);
			while (rs.next()) {
				Products product = new Products(rs.getString(1), rs.getDouble(2));
				products.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return products;
	}
	//�����ݿ������Ʒ��������ڷ��͹����
	public CachedRowSet readProductsResult() {
		String Selectsql = "SELECT * FROM Products";
		ResultSet rs = dbc.executeQuery(Selectsql,null);
		try {
			CachedRowSetImpl crs = new CachedRowSetImpl();
			crs.populate(rs);
			return crs;
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			return null;
		}
	}
	// �������û��µ�
	public void placeOrder(Orders orders, ArrayList<OrderItems> orderItems) throws SQLException {

		try {
			if (con.getAutoCommit()) {
				con.setAutoCommit(false);
			}
			// ���涩��
			saveOrders(orders);
			String Selectsql = "SELECT OrderNO FROM Orders WHERE cno=? AND totalMoney=? ORDER BY OrderNO DESC";
			ResultSet rs = dbc.executeQuery(Selectsql,
					new Object[] { orders.get_cno(),orders.get_totalMoney() });
			rs.next();

			// ���涩����
			for (int i = 0; i < orderItems.size(); i++) {
				OrderItems orderitem = new OrderItems();
				orderitem = orderItems.get(i);
				orderitem.set_orderNO(rs.getInt(1));
				saveOrderItems(orderitem);
			}
			con.commit();
			con.setAutoCommit(true);

		} catch (Exception e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (Exception se) {
					se.printStackTrace();
				}
			}
			e.printStackTrace();
		}
	}

	// �����ˢ�¶�����Ϣ��
	public CachedRowSet readOrdersResultServer() {
		
		String Selectsql = "SELECT * FROM Orders";
		ResultSet rs = dbc.executeQuery(Selectsql,null);
		
		try {
			CachedRowSetImpl crs = new CachedRowSetImpl();
			crs.populate(rs);
			return crs;
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			return null;
		}
	}
	//�ͻ���ˢ�¶���
	public CachedRowSet readOrdersResultClient(int cno) {

		String Selectsql = "SELECT * FROM Orders WHERE cno=?";
		ResultSet rs = dbc.executeQuery(Selectsql, new Object[] { cno });
		
		try {
			CachedRowSetImpl crs = new CachedRowSetImpl();
			crs.populate(rs);
			return crs;
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			return null;
		}
		
	}
	//ע��ʱ�ж��û����Ƿ��ظ�
	public boolean judgeUserExist(String name) {
		String SelectSql="SELECT * FROM Customers WHERE cname=?";
		ResultSet rs=dbc.executeQuery(SelectSql, new Object[] {name});
		try {
			if(rs.next()) {
				return true;
			}
			else {
				System.out.println(rs.next());
				return  false;
			}
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			return false;
		}
		
	}
	//��¼ʱ��֤�û�������
	public Customers findUserBynamepwd(String name,String password) {
		
		String SelectSql="SELECT * FROM Customers WHERE cname=? AND password=?";
		String UpdateSql="UPDATE Customers SET online=? WHERE cno=?";
		ResultSet rs=dbc.executeQuery(SelectSql, new Object[] {name,password});
		try {
			rs.next();
			Customers customer=new Customers(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
					rs.getString(5));
			dbc.executeUpdate(UpdateSql, new Object[] {"true",customer.get_cno()});
			return customer;
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			return null;
		}
	}
	//�رմ���ʱע���˺�
	public void updateUserOnlineState(int cno) {
		String UpdateSql="UPDATE Customers SET online=? WHERE cno=?";
		try {
			System.out.println(cno);
			dbc.executeUpdate(UpdateSql, new Object[] {"false",cno});
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
	}
	//����˸��²�Ʒ��Ϣ��ֻ���޸ļ۸�
	public void updateProducts(Products product) {
		String UpdateSql="UPDATE Products SET price=? WHERE kind=?";
		
		try {
			dbc.executeUpdate(UpdateSql, new Object[] { product.get_price(),product.get_kind()});
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
	}
	//����˲����²�Ʒ��Ϣ
	public void insertProducts(Products product) {
		String InsertSql="Insert INTO Products(kind,price) VALUES(?,?)";
		
		try {
			dbc.executeUpdate(InsertSql, new Object[] {product.get_kind(),product.get_price()});
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	//�����ɾ�����ݿ��в�Ʒ
	public void deleteProducts(String kind) {
		String DeteleSql="DELETE FROM Products WHERE kind=?";
		try {
			System.out.println(kind);
			dbc.executeUpdate(DeteleSql, new Object[] {kind});
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//����˸��¶�����Ϣ
	public void updateOrders(Orders order) {
		String UpdateSql="UPDATE Orders SET orderState=?,senderPhone=?,senderName=? WHERE orderNO=?";
		try {
		
			dbc.executeUpdate(UpdateSql, new Object[] {order.get_orderState(),order.get_senderPhone(),order.get_senderName(),order.get_orderNO() });

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//�ͻ����û����¸�����Ϣ
	public void updateUser(Customers customer) {
		String UpdateSql="UPDATE Customers SET cname=?,phone=?,address=? WHERE cno=?";
		try {
			System.out.println(customer.get_cno());
			dbc.executeUpdate(UpdateSql, new Object[] {customer.get_cname(),customer.get_phone(),customer.get_address(),customer.get_cno()});

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}