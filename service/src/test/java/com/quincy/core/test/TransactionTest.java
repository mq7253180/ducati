package com.quincy.core.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionTest {
	public void opt1() throws Exception {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = this.createConnection();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
			stat = conn.prepareStatement("SELECT * FROM t_core_i18n_value WHERE id=?");
			stat.setLong(1, 101);
			ResultSet rs = stat.executeQuery();
			if(rs.next()) {
				System.out.println("----------------"+rs.getString("value"));
			}

			rs = stat.executeQuery();
			if(rs.next()) {
				System.out.println("----------------"+rs.getString("value"));
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			if(stat!=null) {
				stat.close();
			}
			if(conn!=null) {
				conn.close();
			}
		}
	}

	private Connection createConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/jlcedu", "jlcedu", "xxx");
	}

	public static void main(String[] args) throws Exception {
		new TransactionTest().opt1();
	}
}