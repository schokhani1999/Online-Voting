import java.awt.event.*;
import java.sql.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Area_Table extends JApplet implements ActionListener,ItemListener {
	
	JLabel lbAID, lbState, lbCity, lbNoB,lbcity,lbConstituency;
	JTextField txAID,  txNoB;
	JComboBox<String> cbState,cbCity,cbConstituency;
	JButton btnS, btnC, btnR;
	
	public void init() {
		lbAID = new JLabel("Area ID");
		lbState = new JLabel("State");
		lbCity = new JLabel("City");
		lbConstituency=new JLabel("Constituency");
		lbNoB = new JLabel("Number of Booths");
		
		txAID = new JTextField(20);
		txNoB = new JTextField(20);
		
		cbState = new JComboBox<String>();
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
		
		cbConstituency=new JComboBox<String>();
		cbConstituency.addItem("---- Constituency ----");
		
		cbCity = new JComboBox<String>();
		cbCity.addItem("---- City ----");
	
		
		btnS = new JButton("Submit");
		btnC = new JButton("Cancel");
		btnR = new JButton("Refresh");
		
		setLayout(new FlowLayout());
		
		add(lbState);
		add(cbState);
		add(lbCity);
		add(cbCity);
		add(lbConstituency);
		add(cbConstituency);
		add(lbAID);
		add(txAID);
		add(lbNoB);
		add(txNoB);		
		add(btnS);
		add(btnR);
		add(btnC);
		
		
		btnS.addActionListener(this);
		btnC.addActionListener(this);
		btnR.addActionListener(this);
		this.cbState.addItemListener(this);
		this.cbCity.addItemListener(this);
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
				stmt.executeUpdate("create table if not exists AreaTb(Area_ID varchar(50), State varchar(50), City varchar(50), Area varchar(255), No_of_Booths int)");
				PreparedStatement pstmt = con.prepareStatement("insert into AreaTb(Area_ID, State, City, Area, No_of_Booths) values(?, ?, ?, ?, ?)");
				pstmt.setString(1, txAID.getText());
				pstmt.setString(2, cbState.getSelectedItem().toString());
				pstmt.setString(3, cbCity.getSelectedItem().toString());
				pstmt.setString(4, cbConstituency.getSelectedItem().toString());
				pstmt.setInt(5, Integer.parseInt(txNoB.getText()));
				pstmt.execute();
				
				con.close();
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(src == btnC) {
			System.exit(-1);
		}
		else if(src == btnR) {
			txAID.setText("");
			cbState.setSelectedIndex(0);
	//		txCity.setText("");
		//	txArea.setText("");
			txNoB.setText("");
		}
	}

	@Override
	public void itemStateChanged(ItemEvent ie) {
	if(ie.getStateChange()== ItemEvent.DESELECTED)return;
			
			Object src=ie.getSource();
			
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
