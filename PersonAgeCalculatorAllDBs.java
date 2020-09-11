package com.nt.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class PersonAgeCalculatorAllDBs {
	 private static final String   GET_DOB_BY_PID="SELECT DOB FROM PERSON_DATE_TAB WHERE PID=?";
	public static void main(String[] args) {
		Scanner  sc=null;
		int pid=0;
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		float age=0.0f;
		java.util.Date dob=null,sysDate=null;
		
		try {
			//read inputs
			sc=new Scanner(System.in);
			if(sc!=null) {
				System.out.println("enter Person Id::");
				pid=sc.nextInt();
			}
			/*	//register JDBc driver s/w
				Class.forName("com.mysql.cj.jdbc.Driver");
				//establish the connection
				con=DriverManager.getConnection("jdbc:mysql:///ntaj412db","root","root");*/
			
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
				   dob=rs.getDate(1);  //DOB
				   sysDate=new Date(); // sys date
				   age=((sysDate.getTime())-(dob.getTime()))/(1000.0f*60.0f*60.0f*24.0f*365.0f);
				   System.out.println("Person age::"+age);
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
			
		}//finally
		
	}//main
}//class
