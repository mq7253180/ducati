package com.quincy.core.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionTest2 {
	public void opt(String sql1, String sql2) throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Connection conn = null;
				PreparedStatement stat = null;
				PreparedStatement stat2 = null;
				try {
					conn = createConnection();
					conn.setAutoCommit(false);
					conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
					stat = conn.prepareStatement(sql1);
					Thread.sleep(500);
					stat2 = conn.prepareStatement(sql2);
					System.out.println("R----------"+stat.executeUpdate()+"------------"+stat2.executeUpdate());
					conn.commit();
				} catch (Exception e) {
					try {
						conn.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				} finally {
					try {
						if(stat!=null)
							stat.close();
						if(stat2!=null)
							stat2.close();
						if(conn!=null)
							conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	private static Connection createConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://47.93.89.0:3306/ducati", "admin", "nimda");
	}

	public static void main(String[] args) throws Exception {
//		String baseSql1 = "UPDATE test SET aaa=CONCAT(aaa, '_xxx') WHERE id=";
//		String baseSql2 = "UPDATE test SET aaa=CONCAT(aaa, '_www') WHERE id=";
//		String sql1 = baseSql1+3;
//		String sql2 = baseSql2+5;
//		String sql3 = baseSql1+5;
//		String sql4 = baseSql2+3;
//		String sql3 = baseSql1+3;
//		String sql4 = baseSql2+5;

		int bbb = 15;
		int id = 1;
		String sql1 = "DELETE FROM test WHERE id="+id+";";
		String sql2 = "INSERT INTO test VALUES(11, 'aaa11', "+bbb+");";
		String sql3 = "DELETE FROM test WHERE bbb="+bbb+";";
		String sql4 = "INSERT INTO test VALUES(12, 'aaa12', "+bbb+");";
		TransactionTest2 t = new TransactionTest2();
		t.opt(sql1, sql2);
		Thread.sleep(200);
		t.opt(sql3, sql4);
	}
}