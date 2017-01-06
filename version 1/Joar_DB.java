package joarLib;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Joar_DB {

	  private String errorMessages = "";
	  private Connection con = null;
	  private PreparedStatement stmt = null, stmt2 = null;
	  private ResultSet rs = null;

		private final String selectWordQuery = "SELECT * FROM joar_word WHERE word LIKE ? ORDER BY frequency DESC;";
		private final String selectSitewordQuery = "SELECT * FROM joar_siteword WHERE word=? ORDER BY frequency DESC;";

		private final String returnTrendingQuery = "SELECT * FROM joar_trending ORDER BY lastdate DESC, frequency DESC;";
		private final String selectTrendingQuery = "SELECT * FROM joar_trending WHERE word=?;";
		private final String importTrendingQuery = "INSERT INTO joar_trending (word, frequency, lastdate) VALUES (?, 1, CURDATE());";
		private final String updateTrendingQuery = "UPDATE joar_trending SET frequency = frequency + 1, lastdate = CURDATE() WHERE word=?;";


	  public String getErrorMessages() {
	  	return errorMessages;
  	  }

	  public void open() throws SQLException {
	    try {
	      // for JDBC driver to connect to mysql, the .newInstance() method
	      // can be ommited
	      Class.forName("com.mysql.jdbc.Driver").newInstance();
	    } catch (Exception e1) {
	      errorMessages = "MySQL Driver error: <br>" + e1.getMessage();
	      throw new SQLException(errorMessages);
	    }

	    try {
	      con = DriverManager.getConnection(
	          "jdbc:mysql://195.251.249.131:3306/ismgroup77",
	          "ismgroup77", "rx673g");
	    } catch (Exception e2) {
	      errorMessages = "Could not establish connection with the Database Server: <br>"
	          + e2.getMessage();
	      con = null;
	      throw new SQLException(errorMessages);
	    }

	  }

	  /**
	   * Ends the connection with the database Server. Closes all Statements and
	   * ResultSets. Finally, closes the connection with the Database Server.
	   *
	   * @throws SQLException
	   *             (with the appropriate message) if any error occured.
	   */
	  public void close() throws SQLException {
	    try {

	      if (stmt != null)
	        stmt.close();

				if (stmt2 != null)
					stmt2.close();

	      if (rs != null)
	        rs.close();

	      if (con != null)
	        con.close();

	    } catch (Exception e3) {
	      errorMessages = "Could not close connection with the Database Server: <br>"
	          + e3.getMessage();
	      throw new SQLException(errorMessages);
	    }
 	 }


	 public List<String> getData(String query) throws Exception {

		 if (con == null) {
			 errorMessages = "You must establish a connection first!";
			 throw new SQLException(errorMessages);
		 }

		 try {
			 List<String> list = new ArrayList<String>();

			 stmt = con.prepareStatement(selectWordQuery);
			 query = query + "%";
			 stmt.setString(1, query);
			 // execute query
			 rs = stmt.executeQuery();
			 while (rs.next()) {
				//  if (rs.getString("word").startsWith(query)) {
					 String words = new String(rs.getString("word"));
					 list.add(words);
				// 	}
			 }

			 rs.close();
			 stmt.close();

			return list;
		 } catch (Exception e) {
		throw new Exception("Error: " + e.getMessage());
	}
	}

		 /*&public List<Siteword> findWord(String word) throws Exception {

			 if (con == null) {
				 errorMessages = "You must establish a connection first!";
				 throw new SQLException(errorMessages);
			 }

			 try {
				 List<Siteword> list = new ArrayList<Siteword>();

				 stmt2 = con.prepareStatement(selectSitewordQuery);
				 // replacing the first ? with am, the second ? with
				 // name and the third ? with surname.
				 stmt2.setString(1, word);
				 // execute query
				 rs = stmt2.executeQuery();
				 while (rs.next()) {
					 Siteword words = new Siteword(rs.getString("site"), rs.getString("word"), rs.getInt("frequency"));
					 list.add(words);
				 }

				 rs.close();
				 stmt2.close();

				return list;
			 } catch (Exception e) {
			throw new Exception("Error: " + e.getMessage());
		}

}*/

	public List<Siteword> getSites(String query, int n, String[] words) throws Exception {

			 if (con == null) {
				 errorMessages = "You must establish a connection first!";
				 throw new SQLException(errorMessages);
		     }
			  try{
                  List<Siteword> list = new ArrayList<Siteword>();
				  PreparedStatement stmt = con.prepareStatement(query);
				  for (int i=0; i<=n-1; i++){
					  stmt.setString(i+1,words[i]);
				  }
				  ResultSet rs = stmt.executeQuery();
				  while(rs.next()){
					  Siteword sites = new Siteword(rs.getString("site"));
					  list.add(sites);
				  }
				  rs.close();
				  stmt.close();
	              return list;

			  } catch(Exception ex) {
			     throw new Exception("An error occured while getting sites from database: " + ex.getMessage());
		      }
      }

public boolean validTrending (String word) throws Exception {

	if (con == null) {
	errorMessages = "You must establish a connection first!";
	throw new SQLException(errorMessages);
	}

	try {
		stmt = con.prepareStatement(selectTrendingQuery);
		stmt.setString(1, word);
		// execute query
		rs = stmt.executeQuery();
		int c = 0;
		while (rs.next()) {
		c++;
		}
		if (c == 1) {
			stmt.close();
			rs.close();
			return true;
		} else {
			stmt.close();
			rs.close();
			return false;
		}
	} catch (Exception e) {
	throw new Exception("Error: " + e.getMessage());
	}
}

public List<Trending> findTrending() throws Exception {

	if (con == null) {
		errorMessages = "You must establish a connection first!";
		throw new SQLException(errorMessages);
	}

	try {
		List<Trending> list = new ArrayList<Trending>();

		stmt = con.prepareStatement(returnTrendingQuery);
		// execute query
		rs = stmt.executeQuery();
		while (rs.next()) {
			Trending trendings = new Trending(rs.getString("word"));
			list.add(trendings);
		}

		rs.close();
		stmt.close();

	 return list;
	} catch (Exception e) {
 throw new Exception("Error: " + e.getMessage());
}
}


	public void importTrending(String word) throws Exception {

		if (con == null) {
			errorMessages = "You must establish a connection first!";
			throw new SQLException(errorMessages);
		}

		try {

			stmt2 = con.prepareStatement(importTrendingQuery);
			stmt2.setString(1, word);
			// execute query
			stmt2.executeUpdate();
			stmt2.close();

		} catch (Exception e) {
	 throw new Exception("Error: " + e.getMessage());
 }
 }

 public void updateTrending(String word) throws SQLException {

	if (con == null) {
		errorMessages = "You must establish a connection first!";
		throw new SQLException(errorMessages);
	}

	try {
		stmt2 = con.prepareStatement(updateTrendingQuery);

		stmt2.setString(1, word);

		// execute query
		stmt2.executeUpdate();
		stmt2.close();

	} catch (Exception e4) {
		errorMessages = "Error while inserting student to the database: <br>"
				+ e4.getMessage();
		throw new SQLException(errorMessages);
	}
 }

}
