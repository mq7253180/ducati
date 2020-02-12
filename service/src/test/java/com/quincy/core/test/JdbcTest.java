package com.quincy.core.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTest {
	public void test() throws Exception {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/quincy1", "quincy", "ycniuq");

//			doQuery(conn);
			doUpdate(conn);
		} catch (Exception e) {
			throw e;
		} finally {
			if(conn!=null) {
				conn.close();
			}
		}
	}
	
	private void doQuery(Connection conn) throws SQLException {
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			stat = conn.prepareStatement("SELECT * FROM singer");
//			stat = conn.prepareStatement("SELECT * FROM singer", PreparedStatement.RETURN_GENERATED_KEYS);
//			stat = conn.prepareStatement("SELECT * FROM singer", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			//sql, resultSetType, resultSetConcurrency, resultSetHoldability
//			rs = stat.getGeneratedKeys();
			rs = stat.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getString("name")+"---"+rs.getInt("sex"));
//				rs.updateString(1, "aaa2");
//				rs.updateRow();
//				rs.insertRow();
//				rs.moveToCurrentRow();
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if(rs!=null) {
				rs.close();
			}
			if(stat!=null) {
				stat.close();
			}
		}
	}
	
	private void doUpdate(Connection conn) throws SQLException {
		PreparedStatement stat = null;
		try {
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(2);
			stat = conn.prepareStatement("UPDATE singer SET name=? WHERE id=?");
			stat.setLong(2, 1);
			stat.setString(1, "aaa2");
			stat.executeQuery();
			conn.commit();
			int i = stat.executeUpdate();
			System.out.println(i);
		} catch (SQLException e) {
			conn.rollback();
			throw e;
		} finally {
			if(stat!=null) {
				stat.close();
			}
		}
	}
	
	public static void main(String[] args) {
//		byte[] buf = {83, 69, 76, 69, 67, 84, 32, 42, 32, 70, 82, 79, 77, 32, 115, 105, 110, 103, 101, 114};
//		byte[] buf = {58, 1, 0, 1, -113, -94, 58, 0, -1, -1, -1, 0, 33, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 113, 117, 105, 110, 99, 121, 0, 20, -92, -105, -31, 74, 19, 66, -29, 47, 24, 57, -96, -16, -109, 122, 106, -74, 73, -94, -2, -11, 113, 117, 105, 110, 99, 121, 49, 0, 109, 121, 115, 113, 108, 95, 110, 97, 116, 105, 118, 101, 95, 112, 97, 115, 115, 119, 111, 114, 100, 0, -78, 16, 95, 114, 117, 110, 116, 105, 109, 101, 95, 118, 101, 114, 115, 105, 111, 110, 8, 49, 46, 56, 46, 48, 95, 57, 50, 15, 95, 99, 108, 105, 101, 110, 116, 95, 118, 101, 114, 115, 105, 111, 110, 18, 64, 77, 89, 83, 81, 76, 95, 67, 74, 95, 86, 69, 82, 83, 73, 79, 78, 64, 12, 95, 99, 108, 105, 101, 110, 116, 95, 110, 97, 109, 101, 28, 64, 77, 89, 83, 81, 76, 95, 67, 74, 95, 68, 73, 83, 80, 76, 65, 89, 95, 80, 82, 79, 68, 95, 78, 65, 77, 69, 64, 15, 95, 99, 108, 105, 101, 110, 116, 95, 108, 105, 99, 101, 110, 115, 101, 23, 64, 77, 89, 83, 81, 76, 95, 67, 74, 95, 76, 73, 67, 69, 78, 83, 69, 95, 84, 89, 80, 69, 64, 15, 95, 114, 117, 110, 116, 105, 109, 101, 95, 118, 101, 110, 100, 111, 114, 18, 79, 114, 97, 99, 108, 101, 32, 67, 111, 114, 112, 111, 114, 97, 116, 105, 111, 110, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
//		byte[] buf = {74, 0, 0, 0, 10, 53, 46, 54, 46, 51, 48, 0, 16, 0, 0, 0, 105, 100, 59, 55, 66, 38, 53, 117, 0, -1, -9, 8, 2, 0, 127, -128, 21, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 114, 90, 77, 81, 50, 67, 59, 60, 91, 55, 63, 117, 0, 109, 121, 115, 113, 108, 95, 110, 97, 116, 105, 118, 101, 95, 112, 97, 115, 115, 119, 111, 114, 100, 0};
		byte[] buf = {32, 32, 32, 83, 69, 76, 69, 67, 84, 32, 42, 32, 70, 82, 79, 77, 32, 115, 105, 110, 103, 101, 114};
		String s = new String(buf);
		System.out.println(s);
//		System.out.println(-1&0xff);
//		System.out.println(Math.pow(2, 15)+Math.pow(2, 14)+Math.pow(2, 13)+Math.pow(2, 12)+Math.pow(2, 11)+Math.pow(2, 10)+Math.pow(2, 9)+Math.pow(2, 8));
//		System.out.println(Math.pow(2, 15)+Math.pow(2, 14)+Math.pow(2, 13)+Math.pow(2, 12)+Math.pow(2, 10)+Math.pow(2, 9)+Math.pow(2, 8)+255);
//		System.out.println(new String(new byte[]{53, 46, 54, 46, 51, 48}));
		try {
			new JdbcTest().test();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
