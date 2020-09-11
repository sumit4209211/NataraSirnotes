package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PersonAgeCalculatorForOracle {
	 private static final String   GET_DOB_BY_PID="SELECT (SYSDATE-DOB)/365 FROM PERSON_DATE_TAB WHERE PID=?";
	public static void main(String[] args) {
		Scanner  sc=null;
		int pid=0;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		float age=0.0f;
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("enter Person Id::");
				pid=sc.nextInt();
			}
			//register JDBc driver s/w
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create PReparedStatement object
			if(con!=null)
				ps=con.prepareStatement(GET_DOB_BY_PID);
			//set values query params
			if(ps!=null)
				ps.setInt(1, pid);
			//send and execute SQL Query in DB s/w
			if(ps!=null) 
				rs=ps.executeQuery();
			//process the ResultSEt 
			if(rs!=null) {
			   if(rs.next()) {
				 age=rs.getFloat(1);
				 System.out.println("Person Age:::"+age);
			  }//if
			   else {
				   System.out.println("Record not found");
			   }
			}//if
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
				if(rs!=null)
					rs.close();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			try {
				if(ps!=null)
					ps.close();
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
			
		}
		
	}

}
