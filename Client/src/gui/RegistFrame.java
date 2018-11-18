package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;

import entity.Customers;
import util.ClientService;

@SuppressWarnings("serial")
public class RegistFrame extends JFrame {
	private JPanel contentPane;
	private JTextField Text_userName;
	private JPasswordField Text_userPwd;
	private JPasswordField Text_userPwdSure;
	private JTextField Text_userPhone;
	private JTextArea Text_Address;
	private Customers customer=new Customers();


	/**
	 * Create the frame.
	 */
	public RegistFrame() {
		setTitle("\u7528\u6237\u6CE8\u518C");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 489, 580);
		contentPane = new JPanel();
		contentPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		contentPane.setBackground(UIManager.getColor("ToolTip.backgroundInactive"));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUserName = new JLabel("\u7528 \u6237 \u540D\uFF1A");
		lblUserName.setFont(new Font("黑体", Font.PLAIN, 18));
		lblUserName.setBounds(14, 27, 102, 29);
		contentPane.add(lblUserName);
		
		Text_userName = new JTextField();
		Text_userName.setFont(new Font("黑体", Font.PLAIN, 18));
		Text_userName.setBackground(Color.WHITE);
		Text_userName.setBounds(103, 28, 336, 29);
		contentPane.add(Text_userName);
		Text_userName.setColumns(10);
		
		JLabel lblUserPwd = new JLabel("\u5BC6    \u7801\uFF1A");
		lblUserPwd.setFont(new Font("黑体", Font.PLAIN, 18));
		lblUserPwd.setBounds(14, 174, 102, 29);
		contentPane.add(lblUserPwd);
		
		Text_userPwd = new JPasswordField();
		Text_userPwd.setFont(new Font("SansSerif", Font.PLAIN, 18));
		Text_userPwd.setBounds(103, 175, 336, 29);
		contentPane.add(Text_userPwd);
		
		JLabel lblSurePwd = new JLabel("\u786E\u8BA4\u5BC6\u7801\uFF1A");
		lblSurePwd.setFont(new Font("黑体", Font.PLAIN, 18));
		lblSurePwd.setBounds(14, 248, 102, 29);
		contentPane.add(lblSurePwd);
		
		Text_userPwdSure = new JPasswordField();
		Text_userPwdSure.setFont(new Font("SansSerif", Font.PLAIN, 18));
		Text_userPwdSure.setBounds(103, 249, 336, 29);
		contentPane.add(Text_userPwdSure);
		
		JLabel lblPhone = new JLabel("\u624B \u673A \u53F7\uFF1A");
		lblPhone.setFont(new Font("黑体", Font.PLAIN, 18));
		lblPhone.setBounds(14, 100, 102, 29);
		contentPane.add(lblPhone);
		
		Text_userPhone = new JTextField();
		Text_userPhone.setFont(new Font("黑体", Font.PLAIN, 18));
		Text_userPhone.setColumns(11);
		Text_userPhone.setBounds(103, 100, 336, 29);
		contentPane.add(Text_userPhone);
		
		JButton button_Back = new JButton("\u8FD4 \u56DE");
		button_Back.addActionListener(new BackListener());
		button_Back.setFont(new Font("黑体", Font.PLAIN, 18));
		button_Back.setBounds(56, 463, 128, 45);
		contentPane.add(button_Back);
		
		JButton button_Register = new JButton("\u6CE8 \u518C");
		button_Register.addActionListener(new RegisterListener());
		button_Register.setFont(new Font("黑体", Font.PLAIN, 18));
		button_Register.setBounds(281, 463, 128, 45);
		contentPane.add(button_Register);
		
		JLabel lblAddress = new JLabel("\u5730    \u5740\uFF1A");
		lblAddress.setFont(new Font("黑体", Font.PLAIN, 18));
		lblAddress.setBounds(14, 315, 102, 29);
		contentPane.add(lblAddress);
		
		Text_Address = new JTextArea();
		Text_Address.setFont(new Font("黑体", Font.PLAIN, 18));
		Text_Address.setLineWrap(true);
		Text_Address.setBounds(104, 310, 334, 84);
		contentPane.add(Text_Address);
		
		this.setVisible(true);
		setWindowXY();
	}
	void  setWindowXY() {
		Dimension dem=Toolkit.getDefaultToolkit().getScreenSize();

		  int sHeight=dem.height;

		  int sWidth=dem.width;

		  int fHeight=this.getHeight();

		  int fWidth=this.getWidth();

		  this.setLocation((sWidth-fWidth)/2, (sHeight-fHeight)/2);
		}
	public class RegisterListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
			String strName=Text_userName.getText();
			String strPwd=new String(Text_userPwd.getPassword());
			String strPhone=Text_userPhone.getText();
			String strPwdS=new String(Text_userPwdSure.getPassword());
			String strAdr=Text_Address.getText();;
			String PHONE_NUMBER_REG = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";
			Pattern pattern = Pattern.compile(PHONE_NUMBER_REG);
			 Matcher matcher = pattern.matcher(strPhone);
			//纠错显示
			if(strName==null||strName.equals("")) {
				JOptionPane.showMessageDialog(rootPane,"用户名不能为空","", JOptionPane. ERROR_MESSAGE );
				return;
			}
			if(strPhone==null||strPhone.equals("")){
				JOptionPane.showMessageDialog(rootPane,"手机号码不能为空","", JOptionPane. ERROR_MESSAGE );
				return;
			}
			if(strPwd==null||strPwd.equals("")) {
				JOptionPane.showMessageDialog(rootPane,"密码不能为空","", JOptionPane. ERROR_MESSAGE );
				return;
			}
			if(strPwdS==null||strPwdS.equals("")) {
				JOptionPane.showMessageDialog(rootPane,"请输入确认密码","", JOptionPane. ERROR_MESSAGE );
				return;
			}
			if(strAdr==null||strAdr.equals("")) {
				JOptionPane.showMessageDialog(rootPane,"请输入地址","", JOptionPane. ERROR_MESSAGE );
				return;
			}
			if(!strPwd.equals(strPwdS)) {
				JOptionPane.showMessageDialog(rootPane,"两次密码不一致","", JOptionPane. ERROR_MESSAGE );
				return;
			}
			if(!matcher.find()) {
				JOptionPane.showMessageDialog(rootPane,"电话格式不正确","", JOptionPane. ERROR_MESSAGE );
				return;
			}
			ClientService clientService=new ClientService();

			//保存信息
			customer.set_cname(strName);
			customer.set_password(strPwd);
			customer.set_phone(strPhone);
			customer.set_address(strAdr);
			try {
				if(clientService.sendRegisterCustomers(customer)==false) {
					JOptionPane.showMessageDialog(rootPane,"用户名重复无法注册","", JOptionPane. ERROR_MESSAGE );
					return;
				}
				else {
					JOptionPane.showMessageDialog(rootPane,"注册成功","", JOptionPane. INFORMATION_MESSAGE );
				}
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(rootPane,"请检查网络或联系管理员","连接服务器失败", JOptionPane. ERROR_MESSAGE );
				e1.printStackTrace();
				return;
			}

		}
	}
	public  class BackListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			RegistFrame.this.dispose();
					new LoginFrame();
				}
		}
}
