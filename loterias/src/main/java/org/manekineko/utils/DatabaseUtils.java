/**
 * 
 */
package org.manekineko.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author josen
 * 
 */
public class DatabaseUtils {

	static MessageDigest md = null;

	static {
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println(DatabaseUtils.hash("Flavio"));
	}

	/**
	 * 
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public static String hash(String s) {
		byte[] mdbytes = md.digest(s.getBytes());

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < mdbytes.length; i++) {
			sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
		}

		return sb.toString();
	}
	
	/**
	 * 
	 * @param conn
	 */
	public static void close(Connection conn) {
		close(null, null, conn);
	}

	/**
	 * 
	 * @param stmt
	 * @param conn
	 */
	public static void close(Statement stmt, Connection conn) {
		close(null, stmt, conn);
	}
	
	/**
	 * 
	 * @param rs
	 * @param stmt
	 * @param conn
	 */
	public static void close(ResultSet rs, Statement stmt, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param stmt
	 * @param conn
	 */
	public static void close(Statement stmt) {
		close(null, stmt, null);
	}

}
