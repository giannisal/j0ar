package teamjava;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
public class Noname {
	public static List searchresult(String w) throws Exception {
		try {
			List<Link> list1 = new ArrayList<Link>();
			Connection con = null;
			ResultSet rs = null;
			ResultSet rs2 = null;
			ResultSet result = null;
			PreparedStatement sqlst = null;
			PreparedStatement sqlst2 = null;
			PreparedStatement sqlst3 = null;//mporoyme na ta enosoume se ena an den masxrisimevoun pouthena ta alla
// prepared statements
             Basi db = new Basi();
			db.open();
			con = db.getConnection();
			String st1 = "SELECT id FROM  words WHERE word=?;";
			sqlst = con.prepareStatement(st1);
			sqlst.setString(1,w);
			rs = sqlst.executeQuery();
			while(rs.next()){
				String st2 = "SELECT idurl from frequency where idworld=? AND fr>5;";//an theloyme na emfanizoume mono tis lekseis me sixnotita panw apo 5
			    sqlst2 = con.prepareStatement(st2);
			    sqlst2.setInt(1,rs.getInt("worldid"));
				rs2 = sqlst2.executeQuery();
		    }
		    while(rs2.next()){
			   String st3 = "SELECT url from url where id=?;";
			   sqlst3 = con.prepareStatement(st3);
			   sqlst3.setInt(1,rs2.getInt("id"));
   			   result= sqlst3.executeQuery();
		       Link b = new Link(result.getInt("id"),result.getString("name"));
			   list1.add(b);
	    	 }
			 return list1;
	    }catch (Exception e){
			 throw new Exception ("Error" + e.getMessage());
	    }
}
}