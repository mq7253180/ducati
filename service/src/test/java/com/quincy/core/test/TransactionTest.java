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
		PreparedStatement stat2 = null;
		PreparedStatement stat3 = null;
		PreparedStatement stat4 = null;
		try {
			conn = this.createConnection();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
//			conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
			stat = conn.prepareStatement("SELECT * FROM test WHERE bbb BETWEEN ? AND ?");
			stat.setInt(1, 12);
			stat.setInt(2, 21);
			ResultSet rs = stat.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getInt("id")+"---"+rs.getString("aaa")+"---"+rs.getString("bbb"));
			}

			stat2 = conn.prepareStatement("UPDATE test SET aaa=CONCAT(aaa, '_xxx') WHERE id=?;");
			stat2.setInt(1, 9);
			System.out.println("UPDATE======"+stat2.executeUpdate());

			stat.setInt(1, 7);
			stat.setInt(2, 21);
			rs = stat.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getInt("id")+"---"+rs.getString("aaa")+"---"+rs.getString("bbb"));
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			if(stat!=null)
				stat.close();
			if(stat2!=null)
				stat2.close();
			if(stat3!=null)
				stat3.close();
			if(stat4!=null)
				stat4.close();
			if(conn!=null)
				conn.close();
		}
	}

	public void opt2() throws Exception {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = this.createConnection();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
//			conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
//			stat = conn.prepareStatement("UPDATE test SET aaa=CONCAT(aaa, '_xxx') WHERE id=?;");
//			stat.setLong(1, 9);
//			stat = conn.prepareStatement("UPDATE test SET bbb=bbb+5 WHERE aaa=?;");
//			stat.setString(1, "aaa8");
//			stat = conn.prepareStatement("UPDATE test SET aaa=CONCAT(aaa, '_xxx') WHERE bbb BETWEEN 12 AND 21;");
//			stat = conn.prepareStatement("UPDATE test SET aaa=CONCAT(aaa, '_xxx');");
			stat = conn.prepareStatement("UPDATE sub_test SET eee=CONCAT(eee, '_xxx') WHERE fff=21;");
			System.out.println("----------------"+stat.executeUpdate());
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			if(stat!=null)
				stat.close();
			if(conn!=null)
				conn.close();
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
			stat.setString(1, "Ug%");
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
			stat3.setString(1, "U%");
			rs = stat3.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getLong("id")+"==============="+rs.getString("en_name"));
			}

			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			if(stat!=null)
				stat.close();
			if(stat2!=null)
				stat2.close();
			if(stat3!=null)
				stat3.close();
			if(conn!=null)
				conn.close();
		}
	}

	public void opt4() throws Exception {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = this.createConnection();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			stat = conn.prepareStatement("UPDATE test SET aaa=CONCAT(aaa, '_xxx') WHERE bbb BETWEEN 12 AND 21;");
//			stat = conn.prepareStatement("UPDATE test SET aaa=CONCAT(aaa, '_xxx') WHERE bbb BETWEEN 10 AND 25;");
//			stat = conn.prepareStatement("UPDATE test SET aaa=CONCAT(aaa, '_xxx') WHERE id=1;");
//			stat = conn.prepareStatement("UPDATE test SET aaa=CONCAT(aaa, '_xxx') WHERE bbb=15;");
//			stat = conn.prepareStatement("UPDATE test SET aaa=CONCAT(aaa, '_xxx') WHERE bbb=17;");
//			stat = conn.prepareStatement("UPDATE test SET aaa=CONCAT(aaa, '_xxx') WHERE bbb BETWEEN 15 AND 15;");
//			stat = conn.prepareStatement("INSERT INTO test VALUES(11, 'aaa11', 17);");
//			stat = conn.prepareStatement("DELETE FROM test WHERE bbb=15;");
//			stat = conn.prepareStatement("DELETE FROM test WHERE bbb=17;");
//			stat = conn.prepareStatement("DELETE FROM test WHERE id=1;");
//			stat = conn.prepareStatement("DELETE FROM test WHERE bbb BETWEEN 10 AND 25;");
			System.out.println("R----------"+stat.executeUpdate());
//			stat = conn.prepareStatement("SELECT * FROM test WHERE bbb BETWEEN 10 AND 25 ORDER BY bbb;");
//			stat = conn.prepareStatement("SELECT * FROM test WHERE id=11;");
//			ResultSet rs = stat.executeQuery();
//			while(rs.next())
//				System.out.println(rs.getString("aaa")+"---"+rs.getInt("bbb"));
//			rs.close();
//			System.out.println("--------------------");
//			rs = stat.executeQuery();
//			while(rs.next())
//				System.out.println(rs.getString("aaa")+"---"+rs.getInt("bbb"));
//			rs.close();
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			throw e;
		} finally {
			if(stat!=null)
				stat.close();
			if(conn!=null)
				conn.close();
		}
	}

	private Connection createConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://47.93.89.0:3306/ducati", "admin", "nimda");
	}

	public static void main(String[] args) throws Exception {
		new TransactionTest().opt2();
	}
}