package com.Balaram.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

/*create or replace procedure p_authentication(user in varchar,pwd in varchar,result out varchar)
as
  cnt number(4);
begin
  select count(*) into cnt from userlist where username=user and password=pwd;
  if(cnt<>0) then
      result:='VALID CREDENTIALS';
  else
     result:='INVALID CREDENTIALS';
   end if;
end;
/*/



public class CsTest3 {
	private static final String CALL_QUERY="{CALL P_AUTHENTICATION(?,?,?) }";
	public static void main(String[] args) {
		Scanner sc=null;
		String user=null,pass=null;
		Connection con=null;
		CallableStatement cs=null;
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("Enter username::");
				user=sc.nextLine();
				System.out.println("Enter Password::");
				pass=sc.nextLine();
				
			}
			//register JDBC driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish  the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "System","balaram");
			//create CallableStatement  object
			if(con!=null)
				cs=con.prepareCall(CALL_QUERY);
			//register OUT params with JDBC data types
			if(cs!=null)
				cs.registerOutParameter(3, Types.VARCHAR);
			//set values to IN params
			if(cs!=null) {
				cs.setString(1, user);
				cs.setString(2,pass);
			}
			//execute PL/SQL procedure
			if(cs!=null)
				cs.execute();
			//gather results  from  OUT params
			if(cs!=null)
				System.out.println("Result is::"+cs.getString(3));
			
			
		}//try
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch(ClassNotFoundException cnf) {
			cnf.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			//close jdbc objs
			try {
				if(cs!=null)
					cs.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(con!=null)
					con.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(sc!=null)
					sc.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}//finally
		
	}//main

}//class
