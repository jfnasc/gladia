package br.com.sisfashion.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

	static Properties props;

	static {
		try {
			InputStream in = ConnectionFactory.class.getResourceAsStream("/sisfashion.properties");
			props = new Properties();
			props.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws Exception {
		Connection connection = null;

		try {
			// load the sqlite-JDBC driver using the current class loader
			Class.forName("org.sqlite.JDBC");

			// create a database connection
			connection = DriverManager.getConnection("jdbc:sqlite:" + props.getProperty("datasource.file"));
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		return connection;
	}
}
