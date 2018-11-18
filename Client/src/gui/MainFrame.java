package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import entity.Customers;
import entity.OrderItems;
import entity.Orders;
import util.ClientService;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JPanel panel_4;
	private JLabel lblGoodsName[];
	private JSpinner spiGoodsQuantity[];
	private JLabel lblGoodsPrice;
	private JPanel panelOrdersTable;
	private JTable table;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private DefaultTableModel tableModel;
	private Orders orders = new Orders();
	private OrderItems orderItem = new OrderItems();
	private ClientService clientService = new ClientService();
	
	private Customers customers;
	private JTextField textFieldCno;
	private JTextField textFieldName;
	private JTextField textFieldPhone;
	private JTextArea textAreaAddress;

	/**
	 * Create the frame.
	 */
	public MainFrame(Customers customers) {
		setResizable(false);
		this.customers = customers;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 606, 779);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		// ���˵�����
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setBounds(10, 1, 1118, 1000);
		getContentPane().setLayout(new BorderLayout());
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		// �˵�ҳ1��Ʒ���
		JPanel panel_Shop = new JPanel();
		tabbedPane.addTab("\u5546\u54C1\u4FE1\u606F", null, panel_Shop, null);
		panel_Shop.setLayout(null);
		// ��ť����
		JPanel panelButton = new JPanel();
		panelButton.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panelButton.setBounds(0, 614, 583, 87);
		panel_Shop.add(panelButton);
		// �����á���ť
		JButton btnReset = new JButton("\u91CD  \u7F6E");
		btnReset.setBounds(47, 31, 93, 33);
		btnReset.setBorder(UIManager.getBorder("Button.border"));
		btnReset.setBackground(new Color(255, 255, 255));
		btnReset.setForeground(Color.BLACK);
		btnReset.addActionListener(new ResetListener());
		panelButton.setLayout(null);
		btnReset.setHorizontalAlignment(SwingConstants.LEFT);
		btnReset.setFont(new Font("����", Font.PLAIN, 20));
		panelButton.add(btnReset);
		// ���µ�����ť
		JButton btnMakeOrder = new JButton("\u4E0B  \u5355");
		btnMakeOrder.setBounds(441, 31, 93, 33);
		btnMakeOrder.setBackground(new Color(255, 255, 255));
		btnMakeOrder.addActionListener(new MakeOrderListener());
		btnMakeOrder.setHorizontalAlignment(SwingConstants.RIGHT);
		btnMakeOrder.setFont(new Font("����", Font.PLAIN, 20));
		panelButton.add(btnMakeOrder);
		//"ˢ��"��ť
		JButton btnNewButton = new JButton("\u5237  \u65B0");
		btnNewButton.addActionListener(new flushGoodsListener());
		btnNewButton.setFont(new Font("����", Font.PLAIN, 20));
		btnNewButton.setBounds(248, 31, 93, 33);
		panelButton.add(btnNewButton);
		// ����������ʾ��Ʒ�б�
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 0, 583, 613);
		panel_Shop.add(scrollPane_1);
		scrollPane_1.setToolTipText("");
		scrollPane_1.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		showGoodsData();
	
		// �ҵĶ���������
		JPanel panel_MyOrder = new JPanel();
		tabbedPane.addTab("\u6211\u7684\u8BA2\u5355", null, panel_MyOrder, null);
		panel_MyOrder.setLayout(null);
		// �ҵĶ�������������ű��
		panelOrdersTable = new JPanel();
		panelOrdersTable.setBounds(0, 0, 595, 619);
		panel_MyOrder.add(panelOrdersTable);
		panelOrdersTable.setLayout(new GridLayout(1, 0, 0, 0));
		// ˢ�°�ť
		JButton btnFlash = new JButton("\u5237   \u65B0");
		btnFlash.addActionListener(new FlushOrderListener());
		btnFlash.setFont(new Font("����", Font.PLAIN, 20));
		btnFlash.setForeground(Color.BLACK);
		btnFlash.setBackground(Color.WHITE);
		btnFlash.setBounds(222, 643, 149, 45);
		panel_MyOrder.add(btnFlash);
		
		tableModel  = new DefaultTableModel();
		table = new JTable(tableModel);
		showOrdersData();
		scrollPane = new JScrollPane(table);
		table.setRowHeight(50);
		table.getColumnModel().getColumn(2).setPreferredWidth(195);
		panelOrdersTable.add(scrollPane);
		
		
		// ������ʱˢ�¶����߳�
		// new UpdateTableThread().start();
		new Timer(1000 * 10, new FlushOrderListener()).start();

		JPanel panel_MyInfo = new JPanel();
		tabbedPane.addTab("\u4E2A\u4EBA\u4FE1\u606F", null, panel_MyInfo, null);
		panel_MyInfo.setLayout(null);

		JLabel lblHeadImage = new JLabel("\u56FE\u7247");
		lblHeadImage.setBounds(24, 41, 100, 67);
		panel_MyInfo.add(lblHeadImage);

		JLabel lblNewLabel_1 = new JLabel("\u8D26   \u53F7\uFF1A");
		lblNewLabel_1.setFont(new Font("����", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(226, 44, 100, 35);
		panel_MyInfo.add(lblNewLabel_1);

		textFieldCno = new JTextField();
		textFieldCno.setBounds(335, 41, 180, 38);
		panel_MyInfo.add(textFieldCno);
		textFieldCno.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("\u59D3   \u540D\uFF1A");
		lblNewLabel_2.setFont(new Font("����", Font.PLAIN, 20));
		lblNewLabel_2.setBounds(226, 103, 100, 35);
		panel_MyInfo.add(lblNewLabel_2);

		textFieldName = new JTextField();
		textFieldName.setColumns(10);
		textFieldName.setBounds(335, 103, 180, 38);
		panel_MyInfo.add(textFieldName);

		JLabel label = new JLabel("\u7535   \u8BDD\uFF1A");
		label.setFont(new Font("����", Font.PLAIN, 20));
		label.setBounds(226, 164, 100, 35);
		panel_MyInfo.add(label);

		textFieldPhone = new JTextField();
		textFieldPhone.setColumns(10);
		textFieldPhone.setBounds(335, 164, 180, 38);
		panel_MyInfo.add(textFieldPhone);

		textAreaAddress = new JTextArea();
		textAreaAddress.setBounds(74, 384, 441, 222);
		panel_MyInfo.add(textAreaAddress);

		JLabel label_1 = new JLabel("\u5730   \u5740\uFF1A");
		label_1.setFont(new Font("����", Font.PLAIN, 20));
		label_1.setBounds(74, 313, 100, 35);
		panel_MyInfo.add(label_1);

		showMyInfo();

		JButton btnChange = new JButton("\u4FEE  \u6539");
		btnChange.addActionListener(new ChangeListener());
		btnChange.setFont(new Font("����", Font.PLAIN, 18));
		btnChange.setBounds(76, 640, 113, 35);
		panel_MyInfo.add(btnChange);

		JButton btnSaveChange = new JButton("\u4FDD  \u5B58");
		btnSaveChange.addActionListener(new SaveChangeListener());
		btnSaveChange.setFont(new Font("����", Font.PLAIN, 18));
		btnSaveChange.setBounds(401, 640, 113, 35);
		panel_MyInfo.add(btnSaveChange);
		scrollPane_1.getVerticalScrollBar().setUnitIncrement(20);
		setWindowXY();
		this.setVisible(true);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
		
		try {
			clientService.LogoutInfo(customers.get_cno());
		} catch (Exception e1) {
			e1.printStackTrace();

		}
		System.exit(0);
		}
		}); 
	}

	// ���ھ���
	void setWindowXY() {
		Dimension dem = Toolkit.getDefaultToolkit().getScreenSize();

		int sHeight = dem.height;

		int sWidth = dem.width;

		int fHeight = this.getHeight();

		int fWidth = this.getWidth();

		this.setLocation((sWidth - fWidth) / 2, (sHeight - fHeight) / 2);
	}
	//��ʾ��Ʒ
	void showGoodsData() {
		int i = 0, lenth = clientService.ReceiveProducts().size();
		lblGoodsName = new JLabel[lenth];
		spiGoodsQuantity = new JSpinner[lenth];
		panel_4 = new JPanel();
		scrollPane_1.setViewportView(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));
		for (i = 0; i < lenth; i++) {
			
			// ������Ʒ����panel
			JPanel paneGoods = new JPanel();
			paneGoods.setBounds(0, 0, 556, 166);
			//paneGoods.setPreferredSize(new Dimension(556,166),da);
			paneGoods.setAlignmentY(0.5f + i);
			panel_4.add(paneGoods);
			paneGoods.setLayout(null);
			// ��Ʒͼ
			JLabel lblGoodsImg = new JLabel("ͼƬ");
			lblGoodsImg.setBounds(14, 23, 118, 99);
			paneGoods.add(lblGoodsImg);
			// ��Ʒ��
			lblGoodsName[i] = new JLabel(clientService.ReceiveProducts().get(i).get_kind());
			lblGoodsName[i].setFont(new Font("�����Ӳ������", Font.PLAIN, 20));
			lblGoodsName[i].setBounds(141, 39, 107, 72);
			paneGoods.add(lblGoodsName[i]);
			// ��Ʒ���� (���Ϊ0 ���Ϊ���)
			spiGoodsQuantity[i] = new JSpinner();
			spiGoodsQuantity[i].setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
			spiGoodsQuantity[i].setBounds(364, 55, 72, 34);
			paneGoods.add(spiGoodsQuantity[i]);
			// �ֽ���
			JSeparator sepGoods = new JSeparator();
			sepGoods.setBounds(14, 120, 528, 2);
			paneGoods.add(sepGoods);
			// ��Ʒ�۸�  
			//lblGoodsPrice = new JLabel(new String(String.valueOf(service.ReceiveProducts().get(i).get_price())));
			lblGoodsPrice = new JLabel(NumberFormat.getCurrencyInstance(Locale.CHINA).format(clientService.ReceiveProducts().get(i).get_price()));
			lblGoodsPrice.setFont(new Font("�����ݽ����", Font.PLAIN, 30));
			lblGoodsPrice.setBounds(262, 55, 107, 34);
			paneGoods.add(lblGoodsPrice);
			// ��Ͱ����
			JLabel lblGoodstong = new JLabel("\u6876");
			lblGoodstong.setFont(new Font("����", Font.PLAIN, 18));
			lblGoodstong.setBounds(445, 54, 46, 32);
			paneGoods.add(lblGoodstong);
			// y1+=166;y2+=166;y3+=166;y4+=166;y5+=166;y6+=166;y7+=166;


		}
		panel_4.setPreferredSize(new Dimension(280, i * 135));
	}
	// ��ʾ����
//	void flushOrderTable() {
//		panelOrdersTable.removeAll();
//		MatchedTableModel ordermodel = new MatchedTableModel(service.ReceiveOrders(customers.get_cno()));
//		table = new JTable(ordermodel);
//		scrollPane = new JScrollPane(table);
//		table.setRowHeight(50);
//		table.getColumnModel().getColumn(2).setPreferredWidth(195);
//		panelOrdersTable.add(scrollPane);
//	}
	// ��ʾ����
	void showOrdersData() {
		ResultSet rs=clientService.ReceiveOrders(customers.get_cno());
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();
			Vector<String> title = new Vector<String>();
			String[] orderArray = { "������", "�û�ID", "�µ�ʱ��", "�ܼ�", "����״̬", "�ͻ��˵绰", "�ͻ���" };
			for (int i = 1; i <= colCount; i++) {
				title.add(orderArray[i - 1]);
			}
			Vector<Vector<String>> data = new Vector<Vector<String>>();
			int rowCount = 0;
			while (rs.next()) {
				rowCount++;
				// ������
				Vector<String> rowdata = new Vector<String>();
				for (int i = 1; i <= colCount; i++) {
					rowdata.add(rs.getString(i));
				}
				data.add(rowdata);
			}
			if (rowCount == 0) {
				tableModel.setDataVector(null, title);
			} else {
				tableModel.setDataVector(data, title);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}
	// ��ʾ������Ϣ
	void showMyInfo() {
		textFieldCno.setText(Integer.toString(customers.get_cno()));
		textFieldName.setText(customers.get_cname());
		textFieldPhone.setText(customers.get_phone());
		textAreaAddress.setText(customers.get_address());
		textAreaAddress.setFont(new Font("����", Font.BOLD, 16));

		textFieldCno.setEditable(false);
		textFieldName.setEditable(false);
		textFieldPhone.setEditable(false);
		textAreaAddress.setEditable(false);
	}

	// �л�ѡ�
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

	// �µ���ť�¼�
	private class MakeOrderListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ArrayList<OrderItems> orderItems = new ArrayList<OrderItems>();
			double totalMoney = 0;
			String str = "�û�����{0}\n�µ�ʱ�䣺{1}\n�ܼۣ�{2}" ;
			String msg=null;
			// ѭ�����涩������Ϣ��������
			for (int i = 0; i < spiGoodsQuantity.length; i++) {
				if ((int) spiGoodsQuantity[i].getValue() > 0) {
					String strKind = lblGoodsName[i].getText();
					int quantity = (int) spiGoodsQuantity[i].getValue();
					double price = clientService.ReceiveProducts().get(i).get_price();
					double subTotal = quantity * price;
					totalMoney += subTotal;
					orderItem = new OrderItems(strKind, price, quantity, subTotal);
					orderItems.add(orderItem);
				}
			}
			//������0Ԫ��
			if(totalMoney>0) {
			orders.set_totalMoney(totalMoney);
			orders.set_cno(customers.get_cno());

			try {
				clientService.sendOrdersInfo(orders, orderItems);
				msg = MessageFormat.format(str, new Object[] {customers.get_cname(),orders.get_orderTime(),orders.get_totalMoney()});
			} catch (Exception e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}
			for (JSpinner i : spiGoodsQuantity) {
				i.setValue(0);
			}
			JOptionPane.showMessageDialog(rootPane, msg, "�µ��ɹ�", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(rootPane, "����ѡ����Ʒ", "", JOptionPane.ERROR_MESSAGE);
			}
			
			
		}
	}

	// ���ð�ť�¼�
	private class ResetListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for (JSpinner i : spiGoodsQuantity)
				i.setValue(0);
		}
	}
	//��Ʒ��ʾҳˢ�°�ť�¼�
	private class flushGoodsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			showGoodsData();
			
		}
		
	}
	// ����ҳˢ�°�ť�¼�
	private class FlushOrderListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			showOrdersData();
		}
	}

	// �޸İ�ť�¼�
	private class ChangeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(textFieldName.isEditable()==false)
			{
			textFieldName.setEditable(true);
			textFieldPhone.setEditable(true);
			textAreaAddress.setEditable(true);
			}
			else {
				textFieldName.setEditable(false);
				textFieldPhone.setEditable(false);
				textAreaAddress.setEditable(false);
			}
		}

	}
	//������Ϣҳ�����޸İ�ť�¼�
	private class SaveChangeListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			Customers customer = new Customers();
			customer.set_cno(Integer.parseInt(textFieldCno.getText()));
			customer.set_cname(textFieldName.getText());
			customer.set_phone(textFieldPhone.getText());
			customer.set_address(textAreaAddress.getText());
			try {
				if (clientService.sendUserUpdateInfo(customer) == false) {
					JOptionPane.showMessageDialog(rootPane, "�û����ظ�", "", JOptionPane.ERROR_MESSAGE);
					return;
				} else {
					JOptionPane.showMessageDialog(rootPane, "�޸ĳɹ�", "", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(rootPane, "�����������ϵ����Ա", "���ӷ�����ʧ��", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
				return;
			}
		}

	}
}
