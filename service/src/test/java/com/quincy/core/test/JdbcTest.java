package com.quincy.core.test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class JdbcTest {
	public void test() throws Exception {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://47.93.89.0:3306/ducati", "admin", "nimda");

			doQuery(conn);
//			doUpdate(conn);
		} catch (Exception e) {
			throw e;
		} finally {
			if(conn!=null) {
				conn.close();
			}
		}
	}
	
	private void doQuery(Connection conn) throws SQLException {
		conn.setAutoCommit(false);
//		conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
		PreparedStatement stat = null;
		ResultSet rs = null;
		try {
			stat = conn.prepareStatement("SELECT * FROM sub_test;");
			ResultSetMetaData md = stat.getMetaData();
			int columnCount = md.getColumnCount();
			for(int i=1;i<=columnCount;i++) {
				System.out.println(md.getColumnName(i)+"---"+md.getColumnTypeName(i)+"---"+md.getColumnClassName(i));
			}
//			stat = conn.prepareStatement("SELECT * FROM sub_test");
//			stat = conn.prepareStatement("SELECT * FROM b_region WHERE id<=20");
//			stat = conn.prepareStatement("SELECT * FROM b_region WHERE id=7");
//			stat = conn.prepareStatement("SELECT * FROM b_region WHERE id=8");
//			stat = conn.prepareStatement("SELECT * FROM singer", PreparedStatement.RETURN_GENERATED_KEYS);
//			stat = conn.prepareStatement("SELECT * FROM singer", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, ResultSet.HOLD_CURSORS_OVER_COMMIT);
			//sql, resultSetType, resultSetConcurrency, resultSetHoldability
//			rs = stat.getGeneratedKeys();
			/*
			rs = stat.executeQuery();
			while(rs.next()) {
				Object eee = rs.getObject("eee");
				Object fff = rs.getObject("fff");
				System.out.println(eee.getClass().getName()+"---"+fff.getClass().getName()+"--"+eee+"---"+fff);
//				rs.updateString(1, "aaa2");
//				rs.updateRow();
//				rs.insertRow();
//				rs.moveToCurrentRow();
			}
			stat.close();
			rs.close();
			*/
			System.err.println("=======================");
//			stat = conn.prepareStatement("SELECT * FROM b_region WHERE id<=20");
			stat = conn.prepareStatement("SELECT * FROM b_region WHERE id=8");
			rs = stat.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getLong("id")+"---"+rs.getString("en_name"));
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
			conn.commit();
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
//		byte[] buf = {32, 32, 32, 83, 69, 76, 69, 67, 84, 32, 42, 32, 70, 82, 79, 77, 32, 115, 105, 110, 103, 101, 114};
//		String s = new String(buf);
//		System.out.println(s);
//		System.out.println(-1&0xff);
//		System.out.println(Math.pow(2, 15)+Math.pow(2, 14)+Math.pow(2, 13)+Math.pow(2, 12)+Math.pow(2, 11)+Math.pow(2, 10)+Math.pow(2, 9)+Math.pow(2, 8));
//		System.out.println(Math.pow(2, 15)+Math.pow(2, 14)+Math.pow(2, 13)+Math.pow(2, 12)+Math.pow(2, 10)+Math.pow(2, 9)+Math.pow(2, 8)+255);
//		System.out.println(new String(new byte[]{53, 46, 54, 46, 51, 48}));
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		Random random = new Random();
		for(int i=0;i<200;i++) {
//			int r = random.nextInt(1024, 4095);
//			int r = ThreadLocalRandom.current().nextInt(1024, 8191);
//			int r = ThreadLocalRandom.current().nextInt(128, 511);
//			int r = ThreadLocalRandom.current().nextInt(16, 63);
			int r = ThreadLocalRandom.current().nextInt(131072, 524287);
			int remainder = r%8;
			Integer count = map.get(remainder);
			map.put(remainder, (count==null?0:count)+1);
			System.out.println(r+"-------"+remainder);
		}
		System.out.println("====================");	
		for(Entry<Integer, Integer> e:map.entrySet()) {
			System.out.println(e.getKey()+"-------"+e.getValue());
		}
//		System.out.println(Math.pow(2, 19));
		System.out.println(1024%8+"---"+8191%8);
//		try {
//			new JdbcTest().test();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
