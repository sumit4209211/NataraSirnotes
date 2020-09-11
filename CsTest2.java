package com.Balaram.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Scanner;

public class CsTest2 {
   private static final String CALL_QUERY="{CALL p_get_empDetails(?,?,?,?) }";
	public static void main(String[] args) {
		Scanner sc=null;
		int no=0;
		Connection con=null;
		CallableStatement cs=null;
	  try {
		  //read inputs
		  sc=new Scanner(System.in);
		  if(sc!=null) {
			  System.out.println("Enter Employee number::");
			  no=sc.nextInt();
		  }
		  //register JDBC driver s/w
		  Class.forName("oracle.jdbc.driver.OracleDriver");
		  //establish the connection
		  con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","balaram");
		  	//create CallableStatement object	
		  if(con!=null)
			  cs=con.prepareCall(CALL_QUERY);
		  //register OUT params with jDBC types
		  if(cs!=null) {
			  cs.registerOutParameter(2, Types.VARCHAR);
			  cs.registerOutParameter(3, Types.VARCHAR);
			  cs.registerOutParameter(4, Types.INTEGER);
			  }
		  
		  // set values to query params
		  if(cs!=null) 
			  cs.setInt(1, no);
		  //CALL PL/SQL Procedure
		  if(cs!=null)
			  cs.execute();
		  //gather results from OUt params
		  if(cs!=null) {
			  System.out.println("Emp name ::"+cs.getString(2));
			  System.out.println("Emp Desg ::"+cs.getString(3));
			  System.out.println("Emp salary ::"+cs.getInt(4));
		  }
			  
		  		
		  
	  }//try
	  catch(SQLException se) {
		  if(se.getErrorCode()==1403)
			  System.out.println("Internal problem....no data found");
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
