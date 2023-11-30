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

	public void opt2() throws Exception {
		Connection conn = null;
		PreparedStatement stat = null;
		PreparedStatement stat2 = null;
		try {
			conn = this.createConnection();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
			stat = conn.prepareStatement("SELECT * FROM b_region WHERE id=?");
			stat.setLong(1, 243);
			ResultSet rs = stat.executeQuery();
			if(rs.next()) {
				System.out.println("----------------"+rs.getString("en_name"));
			}
			rs.close();

			stat2 = conn.prepareStatement("UPDATE b_region SET en_name=CONCAT(en_name, '_xxx') WHERE id=?");
//			stat2 = conn.prepareStatement("DELETE FROM b_region WHERE id=?");
			stat2.setLong(1, 243);
			int r = stat2.executeUpdate();
			System.out.println("R----------------"+r);

			rs = stat.executeQuery();
			if(rs.next()) {
				System.out.println("----------------"+rs.getString("en_name"));
			}

			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			if(stat!=null) {
				stat.close();
			}
			if(stat2!=null) {
				stat2.close();
			}
			if(conn!=null) {
				conn.close();
			}
		}
	}

	public void opt3() throws Exception {
		Connection conn = null;
		PreparedStatement stat = null;
		PreparedStatement stat2 = null;
		PreparedStatement stat3 = null;
		ResultSet rs = null;
		try {
			conn = this.createConnection();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

//			stat = conn.prepareStatement("SELECT * FROM b_region WHERE id=?");
//			stat.setLong(1, 243);
			stat = conn.prepareStatement("SELECT * FROM b_region WHERE en_name LIKE ?");
			stat.setString(1, "U%");
			rs = stat.executeQuery();
			while(rs.next()) {
				System.out.println("----------------"+rs.getString("en_name"));
			}
			rs.close();

//			stat2 = conn.prepareStatement("UPDATE b_region SET en_name=CONCAT(en_name, '_xxx') WHERE id=?");
//			stat2 = conn.prepareStatement("DELETE FROM b_region WHERE id=?");
//			stat2.setLong(1, 243);
//			int r = stat2.executeUpdate();
//			System.out.println("R----------------"+r);

			stat3 = conn.prepareStatement("SELECT * FROM b_region WHERE en_name LIKE ?");
			stat3.setString(1, "United%");
			rs = stat3.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getLong("id")+"==============="+rs.getString("en_name"));
			}

			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			if(stat!=null) {
				stat.close();
			}
			if(stat2!=null) {
				stat2.close();
			}
			if(stat3!=null) {
				stat3.close();
			}
			if(conn!=null) {
				conn.close();
			}
		}
	}

	private Connection createConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://47.93.89.0:3306/ducati", "admin", "nimda");
	}

	public static void main(String[] args) throws Exception {
//		new TransactionTest().opt1();
//		new TransactionTest().opt2();
		new TransactionTest().opt3();
	}
}