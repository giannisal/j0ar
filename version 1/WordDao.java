package joarLib;

import java.sql.*;
import ergasiajava.*;
import java.util.ArrayList;
import java.util.List;


public class WordDao {

	public WordDao() {

	}

	public List<Sites> getSites(String query, int n, String[] words) throws Exception {
		Database2 db = new Database2();
		Connection con = null;
        List<Sites> list = new ArrayList<Sites>();
		  try{

			  db.open();
			  con = db.getConnection();
			  PreparedStatement stmt = con.prepareStatement(query);
			  for (int i=0; i<=n-1; i++){
				  stmt.setString(i+1,words[i]);
			  }
			  ResultSet rs = stmt.executeQuery();
			  while(rs.next()){  
				  Sites sites = new Sites(rs.getString("site"));
				  list.add(sites);	  
			  }
			  rs.close();
			  stmt.close();
			  db.close();
              return list;

		  } catch(Exception ex) {
		     throw new Exception("An error occured while getting sites from database: " + ex.getMessage());
	      } finally {
	         if(con != null)
	     		con.close();
		  }
      }

}