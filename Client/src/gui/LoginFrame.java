package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;/*** 在屏幕中间显示Shell* @param shell 要显示的Shell对象*/
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import entity.Customers;
import util.ClientService;
@SuppressWarnings("serial")
public class LoginFrame extends JFrame {
	private JTextField userName;
	private JPasswordField userPwd;
	private ClientService clientService=new ClientService();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch(Exception exe){
			exe.printStackTrace();
			}
		new LoginFrame();
	}

	/**
	 * Create the frame.
	 */
	public LoginFrame() {

		setResizable(false);
		setTitle("\u5BA2\u6237\u7AEF\u767B\u5F55");
		setAlwaysOnTop(true);
		setBounds(100, 100, 530, 416);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		userName = new JTextField();
		userName.setBounds(126, 193, 282, 41);
		getContentPane().add(userName);
		userName.setColumns(10);
		
		userPwd = new JPasswordField();
		userPwd.setBounds(126, 233, 282, 41);
		getContentPane().add(userPwd);
		userPwd.setColumns(20);

		JLabel lblUserName = new JLabel("\u7528\u6237\u540D\uFF1A");
		lblUserName.setFont(new Font("黑体", Font.PLAIN, 18));
		lblUserName.setBounds(44, 195, 80, 30);
		getContentPane().add(lblUserName);
		
		JLabel lblUserPwd = new JLabel("\u5BC6  \u7801\uFF1A");
		lblUserPwd.setFont(new Font("黑体", Font.PLAIN, 18));
		lblUserPwd.setBounds(44, 229, 80, 30);
		getContentPane().add(lblUserPwd);
		
		JButton btnLogin = new JButton("\u767B\u5F55");
		btnLogin.addActionListener(new LoginListener());
		btnLogin.setBounds(286, 298, 122, 35);
		getContentPane().add(btnLogin);
		
		JButton btnRegister = new JButton("\u6CE8\u518C");
		btnRegister.addActionListener(new RegisterListener());
		btnRegister.setBounds(122, 298, 122, 35);
		getContentPane().add(btnRegister);
		this.setVisible(true);
		setWindowXY();
	}
	//居中显示窗口
	void  setWindowXY() {
		Dimension dem=Toolkit.getDefaultToolkit().getScreenSize();

		  int sHeight=dem.height;

		  int sWidth=dem.width;

		  int fHeight=this.getHeight();

		  int fWidth=this.getWidth();

		  this.setLocation((sWidth-fWidth)/2, (sHeight-fHeight)/2);
		}
	//登录按钮事件
	private class  LoginListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {	
					String strName=userName.getText();
					String strPwd=new String(userPwd.getPassword());
					Customers customer = null;
					if(strName==null||strName.equals("")) {
						JOptionPane.showMessageDialog(rootPane,"用户名不能为空","", JOptionPane. ERROR_MESSAGE );
						return;
					}
				    if(strPwd==null||strPwd.equals("")) {
						JOptionPane.showMessageDialog(rootPane,"密码不能为空","", JOptionPane. ERROR_MESSAGE );
						return;
					}
					try {
						customer=clientService.LoginInfo(strName, strPwd);
					} catch (UnknownHostException e1) {
						e1.printStackTrace();
						return;
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(rootPane,"请检查网络或联系管理员","连接服务器失败", JOptionPane. ERROR_MESSAGE );
						e1.printStackTrace();
						return;
					}

				    if(customer!=null) {
						LoginFrame.this.setVisible(false);
						new MainFrame(customer);
					}
					else if(customer==null){
						JOptionPane.showMessageDialog(rootPane,"用户名或密码错误","", JOptionPane. ERROR_MESSAGE );
					}   
				}
			}
	
	//注册按钮事件
	public class  RegisterListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			LoginFrame.this.dispose();
			 new RegistFrame();
		}
	}
}

