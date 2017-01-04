package ergasiajava;

import java.sql.*;
import ergasiajava.*;
import java.util.ArrayList;
import java.util.List;


public class InsertDao {

	public InsertDao() {

	}

	/* h vasiki me8odos pou 8a  xrhsimopoiei h omada tou giannh h opoia auksanei 
	  thn suxnothta ths leksis sto sugkekrimeno link */
	public void addFrequency(String link, String word) throws Exception {
		checkIfExestedWord(word);//elegxoume an uparxei h leksh sto pinaka leksewn etsi vste na thn pros8esoume se periptvsh pou den uparxei
		Database2 db = new Database2();
		Connection con = null;
		final String query2 = ("UPDATE frequency SET frequence = frequence + 1 WHERE idword=? and idlink=?;");
		final String query3 = ("INSERT INTO frequency(idlink,idword,frequence) VALUES (?,?,1);");

		  try{
			  db.open();
			  con = db.getConnection();

				  if( wordAndLinkExist(word,link)){
					 PreparedStatement stmt2 = con.prepareStatement(query2);
					 stmt2.setInt(1,findIdOfWord(word));
					 stmt2.setInt(2,findIdOfLink(link));
					 stmt2.executeUpdate();
					 stmt2.close();
				  }else{
					 PreparedStatement stmt3 = con.prepareStatement(query3);
					 stmt3.setInt(1,findIdOfLink(link));
					 stmt3.setInt(2,findIdOfWord(word));
					 stmt3.executeUpdate();
					 stmt3.close();
				  }

			  db.close();
		  } catch(Exception ex) {
		     System.out.println("Exception: " + ex.getMessage());
	      } finally {
	         if(con != null)
	     		con.close();
		  }
      }

	  //me thn me8odo auth elegxoume an  h sugkekrimenh leksh sto sugkekrimeno link uparxei ston pinaka frequency 
      public boolean wordAndLinkExist(String word,String link) throws Exception{
		  Database2 db = new Database2();
		  Connection con = null;
		try{
			db.open();
			con = db.getConnection();
			String query = ("SELECT * FROM frequency WHERE idword=? and idlink=?");
			PreparedStatement stm = con.prepareStatement(query);
			stm.setInt(1,findIdOfWord(word));
			stm.setInt(2,findIdOfLink(link));
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				stm.close();
				rs.close();
				db.close();
				return true;
			  } else {
				stm.close();
				rs.close();
				db.close();
				return false;
			  }
		} catch (Exception e) {
	     System.out.println("Error while executing selection query: <br>" + e.getMessage());
		  return true;
		} finally {

			if(con != null)
				con.close();

		}
	  }

	//me8odos pou pros8etei thn sugkekrimenh leksh ston pinaka leksewn se periptwsh pou den uparxei
	public void checkIfExestedWord(String word) throws Exception{
		Database2 db = new Database2();
		Connection con = null;
	    final String query = ("INSERT INTO word (name) VALUES (?);");
	  try{
		  db.open();
		  con = db.getConnection();
	
		  if(wordExist(word)==false){
			  PreparedStatement stmt = con.prepareStatement(query); //initializestmt1
			  stmt.setString(1, word);
			  stmt.executeUpdate();
			  stmt.close();
		  }
		  

	  } catch(Exception ex) {
	     System.out.println("Exception: " + ex.getMessage());
      } finally {
         if(con != null)
     		con.close();
		}
  }

  //me8odos pou elegxei an h sugkekrimenh leksh uparxei ston pinaka olwn twn leksewn.
  public boolean wordExist(String word) throws Exception{
		  Database2 db = new Database2();
		  Connection con = null;
		try{
			db.open();
			con = db.getConnection();
			String query = ("SELECT * FROM word WHERE idword=?");
			PreparedStatement stm = con.prepareStatement(query);
			stm.setInt(1,findIdOfWord(word));
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				stm.close();
				rs.close();
				db.close();
				return true;
			  } else {
				stm.close();
				rs.close();
				db.close();
				return false;
			  }
		} catch (Exception e) {
	     System.out.println("Error while executing selection query: <br>" + e.getMessage());
		  return true;
		} finally {

			if(con != null)
				con.close();

		}
	  }


// me8odos pou vriskei se poio id antistoixei to onoma tou sugkekrimenou link 
public int findIdOfLink(String link)throws Exception{
	Database2 db = new Database2();
	Connection con = null;
	final String query = ("SELECT * FROM link");
	  try{
		  db.open();
		  con = db.getConnection();
		  PreparedStatement stmt = con.prepareStatement(query);
		  ResultSet rs = stmt.executeQuery();
		  int id=0;
          while(rs.next()){
			  if(link.equals(rs.getString("name"))){
				  id = rs.getInt("idlink");
			      break;
			  }
		  }
		  stmt.close();
		  db.close();

		  return id;
	  } catch(Exception ex) {
	     System.out.println("Exception: " + ex.getMessage());
	     return -1;
      } finally {
         if(con != null)
     		con.close();
	  }
}
// me8odos pou vriskei se poio id antistoixei to onoma ths sugkekrimenhs leksis
public int findIdOfWord(String word) throws Exception {
	Database2 db = new Database2();
	Connection con = null;
	final String query = ("SELECT * FROM word");
	  try{
		  db.open();
		  con = db.getConnection();
		  PreparedStatement stmt = con.prepareStatement(query);
		  ResultSet rs = stmt.executeQuery();
		  int id = 0;
          while(rs.next()){
			  if(word.equals(rs.getString("name"))){
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
