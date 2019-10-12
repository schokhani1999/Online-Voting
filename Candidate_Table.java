import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.io.*;


public class Candidate_Table extends JApplet implements ActionListener,ItemListener {

	JLabel lbCID,lbEType,lbName;
	JTextField txCID,txname;
	JComboBox<String> cbETypes,cbState,cbCity,cbConstituency;
	JButton btnS, btnC, btnR;
	
	public void init() {
		lbCID = new JLabel("Candidate ID");
		lbEType = new JLabel("Election Type");
		lbName=new JLabel("Candidate Name");
		
		txCID = new JTextField(20);
		txname=new JTextField(50);
		
		cbETypes = new JComboBox<String>();
		cbETypes.addItem("---- Election Type ----");
		cbState=new JComboBox<String>();
		cbState.addItem("---- State ----");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
			Statement stmt = con.createStatement();
			stmt.executeUpdate("create database if not exists ElectionDb");
			stmt.execute("use ElectionDb");
			ResultSet rs = stmt.executeQuery("select distinct State_Name from StateTb");
			while(rs.next()) {
				cbState.addItem(rs.getString("State_Name"));
			}
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		cbCity=new JComboBox<String>();
		cbCity.addItem("---- City ----");
		cbConstituency=new JComboBox<String>();
		cbConstituency.addItem("---- Constituency ----");
		cbETypes.addItem("Lok Sabha Elections");
		cbETypes.addItem("Vidhan Sabha Elections");
		
		btnS = new JButton("Submit");
		btnC = new JButton("Cancel");
		btnR = new JButton("Refresh");
		
		setLayout(new FlowLayout());
		
		add(lbCID);
		add(txCID);
		add(lbName);
		add(txname);
		add(new JLabel("State"));
		add(cbState);
		add(new JLabel("City"));
		add(cbCity);
		add(new JLabel("Constituency"));
		add(cbConstituency);
		add(lbEType);
		add(cbETypes);
		add(btnS);
		add(btnR);
		add(btnC);
		
		btnS.addActionListener(this);
		btnC.addActionListener(this);
		btnR.addActionListener(this);
		this.cbState.addItemListener(this);
		this.cbCity.addItemListener(this);
		this.cbConstituency.addItemListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		Object src = ae.getSource();
		
		if(src == btnS) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
				Statement stmt = con.createStatement();
				stmt.executeUpdate("create database if not exists ElectionDb");
				stmt.execute("use ElectionDb");
				stmt.executeUpdate("create table if not exists CandidateTb(Candidate_name varchar(50),Candidate_ID varchar(50), Area_ID varchar(50), Election_Type varchar(100))");
				PreparedStatement pstmt = con.prepareStatement("insert into CandidateTb(Candidate_ID, Area_ID, Election_Type,Candidate_name) values(?,?, ?, ?)");
				pstmt.setString(1, txCID.getText());
				pstmt.setString(2, cbConstituency.getSelectedItem().toString());
				pstmt.setString(3, cbETypes.getSelectedItem().toString());
				pstmt.setString(4, txname.getText());
				pstmt.execute();
				
				con.close();
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		else if(src == btnR) {
			txCID.setText("");
			cbETypes.setSelectedIndex(0);
		}
		else if(src == btnC) {
			System.exit(-1);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent ie) {
		if(ie.getStateChange()== ItemEvent.DESELECTED)return;
		Object src = ie.getSource();
		
		if(src==cbState)
		{
			if(cbCity.getItemCount()>1)
			{
				cbCity.removeAllItems();
				cbCity.addItem("---- City ----");
			}
			if(cbState.getSelectedIndex()==0) return;
			String State=(String)cbState.getSelectedItem();
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
				Statement stmt = con.createStatement();
				stmt.executeUpdate("create database if not exists ElectionDb");
				stmt.execute("use ElectionDb");
				ResultSet rs = stmt.executeQuery("select distinct City_Name from "+ State +"tb");
				while(rs.next()) {
					cbCity.addItem(rs.getString("City_Name"));
				}
				con.close();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		else
		{
			if(cbConstituency.getItemCount()>1)
			{
				cbConstituency.removeAllItems();
				cbConstituency.addItem("---- Constituency ----");
			}
					
			if(cbCity.getSelectedIndex()==0 ) return;
			
			String City=(String)cbCity.getSelectedItem();
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
				Statement stmt = con.createStatement();
				stmt.executeUpdate("create database if not exists ElectionDb");
				stmt.execute("use ElectionDb");
				ResultSet rs = stmt.executeQuery("select distinct Constituency_Name from "+ City +"tb");
				while(rs.next()) {
					cbConstituency.addItem(rs.getString("Constituency_Name"));
				}
				con.close();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
}
