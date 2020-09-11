//SelectTest.java
import java.sql.*;  //jdbc api

public class SelectTest
{
	public static void main(String args[])throws Exception{
		  // register jdbc driver  with DriverManager service
		        //Class.forName("oracle.jdbc.driver.OracleDriver");  //optional in latest jdbc driver s/ws  to take the
				                                                                                                          // advantage of auto loading jdbc driver class
           //Establish the connection
		   Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe",
			                                                                                              "system", "manager");
		   //create Statement object
		   Statement st=con.createStatement();
		   //send and execute SQL Query in Db s/w
		       ResultSet rs=st.executeQuery("SELECT * FROM  STUDENT");
			   //process the ResultSet object
			   while(rs.next()!=false){
                //System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getFloat(4));
				//System.out.println(rs.getInt("sno")+"  "+rs.getString("sname")+"  "+rs.getString("sadd")+"  "+rs.getFloat("avg"));
                System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getString(4));
               }
			   //close jdbc objs
			     rs.close();
				 st.close();
				 con.close();
			
			  
    }//main
}//class
