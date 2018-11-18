package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import entity.Orders;
import entity.Products;
import util.ServersService;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	ServersService serverService=new ServersService();
	private JPanel contentPane;
	private JTable tableGoods;
	private JTable tableOrders;
	private JTable tableUsers;
	private JScrollPane scrollPane;
	private JPanel panelOrdersTable;
	private DefaultTableModel ordersModel;
	private DefaultTableModel productsModel;
	private DefaultTableModel usersModel;
	
	private int rowCount;
	private int rows[]=new int[10];
	private String kinds[]=new String[10];
	private int i=0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("unchecked")
	public MainFrame() {
		//开启服务器
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1062, 647);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		tabbedPane.addTab("\u5546\u54C1", null, panel, null);
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPaneGoods = new JScrollPane();
		scrollPaneGoods.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.add(scrollPaneGoods);

		// 管理端产品显示
		productsModel = new DefaultTableModel();
		showProductsData(serverService.GoodsInfo());
		rowCount = productsModel.getRowCount();
		tableGoods = new JTable(productsModel) {
			// 重写model的方法设置点击一次即可进入编辑状态
			public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
				super.changeSelection(rowIndex, columnIndex, toggle, extend);
				super.editCellAt(rowIndex, columnIndex, null);
			}

			// 重写model的方法设置原有数据只可更改价格不可更改名字
			public boolean isCellEditable(int row, int column) {
				if (row < rowCount) {
					if (column == 1) {
						return true;
					}
					if (column == 0) {
						return false;
					}
				}

				if (row > rowCount) {
					return true;
				}
				return autoCreateColumnsFromModel;
			}
		};

		scrollPaneGoods.setViewportView(tableGoods);
		tableGoods.setRowHeight(50);
		// 用于保存最后一次更改的表格
		tableGoods.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);


		JPanel panel_3 = new JPanel();
		panel.add(panel_3, BorderLayout.SOUTH);


		// 商品页面"保存修改"按钮
		JButton btnSaveProducts = new JButton("\u4FDD\u5B58\u4FEE\u6539");
		btnSaveProducts.addActionListener(new SaveProductsChangeListener());

		// 商品页面”添加新商品“按钮
		JButton btnAddProducts = new JButton("\u6DFB\u52A0\u5546\u54C1");
		btnAddProducts.addActionListener(new AddProductsListener());
		//商品页面”删除商品“按钮
		JButton btnDeleteProducts = new JButton("\u5220\u9664\u5546\u54C1");
		btnDeleteProducts.addActionListener(new DeleteProductsListener());
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 200, 15));
		panel_3.add(btnDeleteProducts);
		panel_3.add(btnSaveProducts);
		panel_3.add(btnAddProducts);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("\u8BA2\u5355", null, panel_1, null);
		panel_1.setLayout(new BorderLayout(0, 0));

		panelOrdersTable = new JPanel();
		panel_1.add(panelOrdersTable, BorderLayout.CENTER);
		panelOrdersTable.setLayout(new GridLayout(1, 0, 0, 0));

		// 管理端订单显示
		ordersModel = new DefaultTableModel();
		tableOrders = new JTable(ordersModel) {
			public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
				super.changeSelection(rowIndex, columnIndex, toggle, extend);
				super.editCellAt(rowIndex, columnIndex, null);
			}

			public boolean isCellEditable(int row, int column) {
				if (column == 4 || column == 5 || column == 6) {

					return true;

				} else {

					return false;

				}
			}
		};
		tableOrders.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		scrollPane = new JScrollPane(tableOrders);
		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		showOrdersData(serverService.ReceiveOrders());
		tableOrders.setRowHeight(50);
		panelOrdersTable.add(scrollPane);
		

		JPanel panel_5 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_5.getLayout();
		flowLayout.setHgap(400);
		flowLayout.setVgap(30);
		panel_1.add(panel_5, BorderLayout.SOUTH);
		// “刷新”按钮
		JButton btnFlush = new JButton("\u5237    \u65B0");
		btnFlush.addActionListener(new FlushOrdersListener());
		panel_5.add(btnFlush);

		JButton btnSaveOrders = new JButton("\u4FDD\u5B58\u66F4\u6539");
		btnSaveOrders.addActionListener(new SaveOrdersChangeListener());
		panel_5.add(btnSaveOrders);

		// 指定某一列添加下拉框
		TableColumn sportColumn = tableOrders.getColumnModel().getColumn(4);

		@SuppressWarnings("rawtypes")
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("未配送");
		comboBox.addItem("配送中");
		sportColumn.setCellEditor(new DefaultCellEditor(comboBox));

		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("\u7528\u6237\u7BA1\u7406", null, panel_2, null);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_2.add(scrollPane_1, BorderLayout.CENTER);
		//显示用户信息
		usersModel=new DefaultTableModel();
		new FlushUsersThread().start();
		tableUsers = new JTable(usersModel);
		scrollPane_1.setViewportView(tableUsers);
		tableUsers.setRowHeight(50);
	}

	// 切换选项卡
	@SuppressWarnings("unused")
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
		
	}

	// 显示订单表
	void showOrdersData(ResultSet rs) {
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();
			Vector<String> title = new Vector<String>();
			String[] orderArray = { "订单号", "用户ID", "下单时间", "总价", "订单状态", "送货人电话", "送货人" };
			for (int i = 1; i <= colCount; i++) {
				title.add(orderArray[i - 1]);
			}
			Vector<Vector<String>> data = new Vector<Vector<String>>();
			int rowCount = 0;
			while (rs.next()) {
				rowCount++;
				// 行数据
				Vector<String> rowdata = new Vector<String>();
				for (int i = 1; i <= colCount; i++) {
					rowdata.add(rs.getString(i));
				}
				data.add(rowdata);
			}
			if (rowCount == 0) {
				ordersModel.setDataVector(null, title);
			} else {
				ordersModel.setDataVector(data, title);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	// 显示产品表
	void showProductsData(ResultSet rs) {
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();
			Vector<String> title = new Vector<String>();
			String[] orderArray = { "商品名", "价格" };
			for (int i = 1; i <= colCount; i++) {
				title.add(orderArray[i - 1]);
			}
			Vector<Vector<String>> data = new Vector<Vector<String>>();
			int rowCount = 0;
			while (rs.next()) {
				rowCount++;
				// 行数据
				Vector<String> rowdata = new Vector<String>();
				for (int i = 1; i <= colCount; i++) {
					rowdata.add(rs.getString(i));
				}
				data.add(rowdata);
			}
			if (rowCount == 0) {
				productsModel.setDataVector(null, title);
			} else {
			
				productsModel.setDataVector(data, title);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}
	//显示用户表
	void showUsersData(ResultSet rs) {
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();
			Vector<String> title = new Vector<String>();
			String[] orderArray = { "用户ID","用户名","电话","地址" };
			for (int i = 1; i <= colCount; i++) {
				title.add(orderArray[i - 1]);
			}
			Vector<Vector<String>> data = new Vector<Vector<String>>();
			int rowCount = 0;
			while (rs.next()) {
				rowCount++;
				// 行数据
				Vector<String> rowdata = new Vector<String>();
				for (int i = 1; i <= colCount; i++) {
					rowdata.add(rs.getString(i));
				}
				data.add(rowdata);
			}
			if (rowCount == 0) {
				usersModel.setDataVector(null, title);
			} else {
				usersModel.setDataVector(data, title);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}
	// "删除商品"按钮事件
	private class DeleteProductsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			int row=tableGoods.getSelectedRow();
			rows[i]=row;
			kinds[i]=(String) productsModel.getValueAt(row, 0);
			productsModel.removeRow(tableGoods.getSelectedRow());
			i++;
		}
		
	}

	// "添加新商品"按钮事件
	private class AddProductsListener implements ActionListener {

		@SuppressWarnings("rawtypes")
		@Override
		public void actionPerformed(ActionEvent e) {

			productsModel.addRow(new Vector());
		}

	}

	// 商品页面"保存修改"按钮事件
	private class SaveProductsChangeListener implements ActionListener {
		private Products updateProduct = new Products();

		@Override
		public void actionPerformed(ActionEvent e) {
			int k=0;
			try {
				// 若添加新产品（即行数增加）则插入新产品
				if (tableGoods.getRowCount() >= rowCount-k) {
					Products insertProduct = new Products();
					for (int i = rowCount-k; i < tableGoods.getRowCount(); i++) {
						insertProduct.set_kind((String) productsModel.getValueAt(i, 0));
						insertProduct.set_price(Double.parseDouble((String) productsModel.getValueAt(i, 1)));
						serverService.sendNewProducts(insertProduct);
					}
					
				}
				//删除产品
				for (int j = 0; j <= i; j++) {
					
					if (rows[j] <= rowCount) {
						
						serverService.sendDeleteProducts(kinds[j]);
						k++;
					}
				}
					// 更新原有产品
					for (int i = 0; i <= rowCount-k; i++) {

						updateProduct.set_kind((String) productsModel.getValueAt(i, 0));
						updateProduct.set_price(Double.parseDouble((String) productsModel.getValueAt(i, 1)));
						serverService.sendUpdateProducts(updateProduct);

					}


			} catch (Exception ee) {
				ee.printStackTrace();
			}
			rowCount= productsModel.getRowCount();
		}
	}

	// 订单页面“刷新”按钮事件
	private class FlushOrdersListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			showOrdersData(serverService.ReceiveOrders());
		}
	}

	// 订单页面“保存更改”按钮事件
	private class SaveOrdersChangeListener implements ActionListener {
		private Orders order = new Orders();

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				for (int i = 0; i < tableOrders.getRowCount(); i++) {
					order.set_orderNO(Integer.parseInt((String) ordersModel.getValueAt(i, 0)));
					order.set_orderState((String) ordersModel.getValueAt(i, 4));
					order.set_senderPhone((String) ordersModel.getValueAt(i, 5));
					order.set_senderName((String) ordersModel.getValueAt(i, 6));
					serverService.sendUpdateOrders(order);
					//System.out.println(orders.get(i).get_orderNO() + orders.get(i).get_orderState()
						//	+ orders.get(i).get_senderPhone() + orders.get(i).get_senderName());
				}
				
			} catch (Exception ee) {
				ee.printStackTrace();
			}

		}
	}
	private class FlushUsersThread extends Thread{
		void showUsersData(ResultSet rs) {
			try {
				ResultSetMetaData rsmd = rs.getMetaData();
				int colCount = rsmd.getColumnCount();
				Vector<String> title = new Vector<String>();
				String[] orderArray = { "用户ID","用户名","电话","地址" };
				for (int i = 1; i <= colCount; i++) {
					title.add(orderArray[i - 1]);
				}
				Vector<Vector<String>> data = new Vector<Vector<String>>();
				int rowCount = 0;
				while (rs.next()) {
					rowCount++;
					// 行数据
					Vector<String> rowdata = new Vector<String>();
					for (int i = 1; i <= colCount; i++) {
						rowdata.add(rs.getString(i));
					}
					data.add(rowdata);
				}
				if (rowCount == 0) {
					usersModel.setDataVector(null, title);
				} else {
					usersModel.setDataVector(data, title);
				}
			} catch (SQLException e) {

				e.printStackTrace();
			}

		}
		public void run() {
			while(true) {
				showUsersData(serverService.UsersInfo());
				try {
					Thread.sleep(1000*1);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
	}
}
