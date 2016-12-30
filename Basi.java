package teamjava;

import java.sql.*;

public class Basi {

	private final String dbname = "ismgroup77";
	private final String dbusername = "ismgroup77";
	private final String dbpassword = "rx673g";

	private Connection con = null;

	public Basi() {

	}

	public Connection getConnection() {
		return this.con;
	}

	public void open() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {

			throw new SQLException("MySQL Driver error: " + e.getMessage());
		}

		try {
			con = DriverManager.getConnection("jdbc:mysql://195.251.249.131:3306/" + dbname, dbusername, dbpassword);
		} catch (Exception e) {
			con = null;
			throw new SQLException("Could not establish connection with the Database Server: " + e.getMessage());
		}

	} // End of open

	public void close() throws SQLException {
		try {
			if (con != null)
				con.close();
		} catch (Exception e) {

			throw new SQLException("Could not close connection with the Database Server: " + e.getMessage());
		}

	}

}
