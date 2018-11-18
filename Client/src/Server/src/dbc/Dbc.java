package dbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dbc {
	public Connection conn = null;
    PreparedStatement pstmt = null ;
    ResultSet rs = null;
	
	public Connection getConnection() throws Exception {
		String url = Config.getValue("url");
		String user = Config.getValue("user");
		String password = Config.getValue("password");
		String driver = Config.getValue("driver");
		try {
			Class.forName(driver) ;
			this.conn = DriverManager.getConnection(url,user,password) ;
		} catch (Exception e) {
			throw new SQLException("�������������ʧ��");
		}
		System.out.println("�������ݿ�ɹ�");
		return this.conn;
	}

	public void close() throws Exception {
		if (this.conn != null) {
			try {
				this.conn.close();
			} catch (Exception e) {
				throw e;
			}
		}
	}
	public ResultSet executeQuery(String preparedSql, Object[] param) {
		// ����SQL,ִ��SQL
		try {
			// �õ�PreparedStatement����
			pstmt = conn.prepareStatement(preparedSql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			if (param != null) {
				for (int i = 0; i < param.length; i++) {
					// ΪԤ����sql���ò���
					pstmt.setObject(i + 1, param[i]);
				}
			}
			// ִ��SQL���
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			// ����SQLException�쳣
			e.printStackTrace();
		}
		return rs;
	}
	public int executeUpdate(String preparedSql, Object[] param) throws SQLException {
			int num = 0;
			// ����SQL,ִ��SQL
				// �õ�PreparedStatement����
				pstmt = conn.prepareStatement(preparedSql);
				if (param != null) {
					for (int i = 0; i < param.length; i++) {
						// ΪԤ����sql���ò���
						pstmt.setObject(i + 1, param[i]);
					}
				}
				// ִ��SQL���
				num = pstmt.executeUpdate();

			return num;
		}
	}


