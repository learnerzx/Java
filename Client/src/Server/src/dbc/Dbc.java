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
			throw new SQLException("驱动错误或连接失败");
		}
		System.out.println("连接数据库成功");
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
		// 处理SQL,执行SQL
		try {
			// 得到PreparedStatement对象
			pstmt = conn.prepareStatement(preparedSql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			if (param != null) {
				for (int i = 0; i < param.length; i++) {
					// 为预编译sql设置参数
					pstmt.setObject(i + 1, param[i]);
				}
			}
			// 执行SQL语句
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			// 处理SQLException异常
			e.printStackTrace();
		}
		return rs;
	}
	public int executeUpdate(String preparedSql, Object[] param) throws SQLException {
			int num = 0;
			// 处理SQL,执行SQL
				// 得到PreparedStatement对象
				pstmt = conn.prepareStatement(preparedSql);
				if (param != null) {
					for (int i = 0; i < param.length; i++) {
						// 为预编译sql设置参数
						pstmt.setObject(i + 1, param[i]);
					}
				}
				// 执行SQL语句
				num = pstmt.executeUpdate();

			return num;
		}
	}


