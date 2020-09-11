package com.nt.jdbc;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BlobRetrieveTestWithOracle {
	private static final String BLOB_RETRIEVE="SELECT PID,PNAME,PADDRS,PHOTOPATH FROM PERSONALL_INFO WHERE PID=?";
	public static void main(String[] args) {
		Scanner sc=null;
		Connection con=null;
		PreparedStatement ps=null;
		int pid=0;
		InputStream is=null;
		OutputStream os=null;
		byte[] buffer=null;
		int result=0;
		int byteRead=0;
		ResultSet rs=null;
		try {
			//Read input from end user
			sc=new Scanner(System.in);
			if(sc!=null)
			{
				System.out.println("Enter Person ID");
				pid=sc.nextInt();
			}//if
			//Register type1 Jdbc Driver
			Class.forName("oracle.jdbc.driver.OracleDriver");
		
		//Establish the Connection
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl","system","manager");
			//Create Prepared Statement Object
			if(con!=null)
				ps=con.prepareStatement(BLOB_RETRIEVE);
			if(ps!=null)
				ps.setInt(1, pid);
			if(ps!=null)
				rs=ps.executeQuery();
			if(rs.next())
			{
				System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3));
				is=rs.getBinaryStream(4);
				os=new FileOutputStream("new_image.jpg");
				if(os!=null && is!=null)
				{
					buffer=new byte[4096];
					while((byteRead=is.read(buffer))!=-1)
					{
						os.write(buffer,0,byteRead);
					}
					System.out.println("Blob value retrieve to new_image.jpg file");
				}//if
			}//if
		}//try
		catch(SQLException se)
		{
			se.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {
			//close JDBC objects
			try {
				if(rs!=null)
					rs.close();
			}
			catch(SQLException se)
			{
				se.printStackTrace();
			}
			try {
				if(ps!=null)
					ps.close();
			}
			catch(SQLException se)
			{
				se.printStackTrace();
			}
			try {
				if(con!=null)
					con.close();
			}
			catch(SQLException se)
			{
				se.printStackTrace();
			}
			try {
				if(sc!=null)
					sc.close();
			}
			catch(Exception se)
			{
				se.printStackTrace();
			}
			try {
				if(is!=null)
					is.close();
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
			try {
				if(os!=null)
					os.close();
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
		}//finally
		
		
		
		
	}//main
}//class
