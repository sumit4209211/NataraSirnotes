package com.nt.jdbc;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GUIFrontEndApp extends JFrame implements ActionListener,WindowListener {
	private static final String  INSERT_STUDENT_QUERY="INSERT INTO STUDENT VALUES(SEQ_SNO.NEXTVAL,?,?,?)";
	private  JLabel lname,laddrs,lavg,lresult;
	private JTextField tname,taddrs,tavg;
	private JButton btn;
	private Connection con;
	private PreparedStatement ps;
	//constructor
	public GUIFrontEndApp() {
		System.out.println("GUIFrontEndApp.0-param constructor");
		setTitle("Student Registration");
		setLayout(new FlowLayout());
		setSize(400,200);
		
		
		//add comps
		lname=new JLabel("student name::");
		add(lname);
		
		tname=new JTextField(10);
		add(tname);
		
		laddrs=new JLabel("Student Address::");
		add(laddrs);
		
		taddrs=new JTextField(10);
		add(taddrs);
		
		lavg=new JLabel("Student Marks Avg::");
		add(lavg);
		
		tavg=new JTextField(10);
		add(tavg);
		
		btn=new JButton("register");
		add(btn);
		btn.addActionListener(this);
		
		lresult=new JLabel("");
		add(lresult);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initializeJdbc();
		this.addWindowListener(this);
	}
	
	private void initializeJdbc() {
		System.out.println("GUIFrontEndApp.initializeJdbc()");
		try {
			//register JDBc driver class
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//establish the connection
			con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","manager");
			//create PreparedStatement obj having pre-compiled SQL Query
			ps=con.prepareStatement(INSERT_STUDENT_QUERY);
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
	}
	
	
	
	public static void main(String[] args) {
		System.out.println("GUIFrontEndApp.main(-)");
		new GUIFrontEndApp();
	}


	@Override
	public void actionPerformed(ActionEvent ae) {
		System.out.println("GUIFrontEndApp.actionPerformed(-)");
		String name=null,addrs=null;
		float avg=0.0f;
		int result=0;
		try {
			//read values from text boxes
			name=tname.getText();
			addrs=taddrs.getText();
			avg=Float.parseFloat(tavg.getText());
			//set values to Query params
			ps.setString(1, name);
			ps.setString(2,addrs);
			ps.setFloat(3,avg);
			//execute the Query
			result=ps.executeUpdate();
			//process the result
			if(result==0) {
				lresult.setText("Registration failed");
				lresult.setForeground(Color.RED);
			}
			else {
				lresult.setText("Registration Succeded");
				lresult.setForeground(Color.GREEN);
			}
			
		}//try
		catch(SQLException se) {
			lresult.setText("Registration failed");
			lresult.setForeground(Color.RED);
			se.printStackTrace();
		}
		catch(Exception e) {
			lresult.setText("Unknown Internal Problem");
			lresult.setForeground(Color.RED);
			e.printStackTrace();
		}
		
		//empty the text box values
		tname.setText("");
		taddrs.setText("");
		tavg.setText("");
		
	}//actionPerformed(-)

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.out.println("GUIFrontEndApp.windowClosing(-)");
		//close jdbc objs
		try {
			if(ps!=null)
			   ps.close();
		}//try
		catch(SQLException se) {
			se.printStackTrace();
		}
		try {
			if(con!=null)
			   con.close();
		}//try
		catch(SQLException se) {
			se.printStackTrace();
		}
		
	}//windowClosing(-)

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}//class
