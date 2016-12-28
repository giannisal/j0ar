package ergasiajava;

import java.sql.*;
import ergasiajava.*;


public class InsertDao {

	private String link;
	private String word;

	public InsertDao() {

	}

	public void addFrequency(String link, String word) throws Exception {
		Database db = new Database();
		Connection con = null;
		final String query = ("SELECT * FROM frequency GROUP BY idlink HAVING idlink = ?; ");
		final String query2 = ("UPDATE link SET frequence = (frequence + 1) WHERE idword=?;");
		final String query3 = ("INSERT INTO frequency(idlink,idword,frequence) VALUES (?,?,1);");
		checkIfExestedWord(word);

		  try{
			  db.open();
			  con = db.getConnection();
			  PreparedStatement stmt = con.prepareStatement(query);
			  stmt.setInt(1,findIdOfWord(word));
			  ResultSet rs = stmt.executeQuery();
			  stmt.close();
			  rs.close();
	          while(rs.next()){
				  if(findIdOfWord(word) == rs.getInt("idword")){
					 PreparedStatement stmt2 = con.prepareStatement(query2);
					 stmt2.setInt(1,findIdOfWord(word));
					 stmt2.executeUpdate();
					 stmt.close();
				  }else{
					 PreparedStatement stmt3 = con.prepareStatement(query3);
					 stmt3.setInt(1,findIdOfLink(link));
					 stmt3.setInt(2,findIdOfWord(word));
					 stmt3.executeUpdate();
					 stmt.close();
				  }
			  }
			  db.close();
		  } catch(Exception ex) {
		     System.out.println("Exception: " + ex.getMessage());
	      } finally {
	         if(con != null)
	     		con.close();
		  }
      }

	//me8odos pou elegxei an h leksh uparxei ston pinaka olwn twn leksewn
	public void checkIfExestedWord(String word) throws Exception{
		Database db = new Database();
		Connection con = null;
	    final String query = ("INSERT INTO Word (name)" + "VALUES (?);");
	  try{
		  db.open();
		  con = db.getConnection();
		  ResultSet rs = showTableWord();
		  while(rs.next()){
			  if(!(rs.getString("name") == word)){
				  PreparedStatement stmt = con.prepareStatement(query); //initializestmt1
		          stmt.setString(1, word);
				  stmt.executeUpdate();
			  }
		  }

	  } catch(Exception ex) {
	     System.out.println("Exception: " + ex.getMessage());
      } finally {
         if(con != null)
     		con.close();
		}
  }

 //me8odos pou epistrefei ton pinaka olwn twn leksewn
public ResultSet showTableWord() throws Exception{
	Database db = new Database();
	Connection con = null;
	final String query = ("SELECT * FROM word");
	  try{
		  db.open();
		  con = db.getConnection();
		  Statement stmt = con.createStatement();
		  ResultSet rs = stmt.executeQuery(query);
		  stmt.close();
		  db.close();
		  rs.close();
          return rs;
	  } catch(Exception ex) {
	     System.out.println("Exception: " + ex.getMessage());
	     return null;
      } finally {
         if(con != null)
     		con.close();
	  }

}

// me8odos pou vriskei se poio id antistoixei to onoma tou link pou stal8ike
public int findIdOfLink(String link)throws Exception{
	Database db = new Database();
	Connection con = null;
	final String query = ("SELECT * FROM link");
	  try{
		  db.open();
		  con = db.getConnection();
		  Statement stmt = con.createStatement();
		  ResultSet rs = stmt.executeQuery(query);
		  int id=0;
          while(rs.next()){
			  if(link == rs.getString("name")){
				  id = rs.getInt("idlink");
			      break;
			  }
		  }
		  stmt.close();
		  db.close();
		  rs.close();
		  return id;
	  } catch(Exception ex) {
	     System.out.println("Exception: " + ex.getMessage());
	     return -1;
      } finally {
         if(con != null)
     		con.close();
	  }
}
// me8odos pou vriskei se poio id antistoixei to onoma ths leksis pou stal8ike
public int findIdOfWord(String word) throws Exception {
	Database db = new Database();
	Connection con = null;
	final String query = ("SELECT * FROM word");
	  try{
		  db.open();
		  con = db.getConnection();
		  Statement stmt = con.createStatement();
		  ResultSet rs = stmt.executeQuery(query);
		  int id = 0;
          while(rs.next()){
			  if(word == rs.getString("name")){
				  id = rs.getInt("idword");
			      break;
			  }
		  }
		  stmt.close();
		  db.close();
		  rs.close();
		  return id;
	  } catch(Exception ex) {
	     System.out.println("Exception: " + ex.getMessage());
	     return -1;
      } finally {
         if(con != null)
     		con.close();
		}
}



}
