import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Booth_Table extends JApplet implements ActionListener,ItemListener {

	JLabel lbBN, lbAdd, lbstate,lbcity,lbConstituency;
	JTextField txBN, txAdd;
	JComboBox<String> cbState,cbCity,cbConstituency;
	JButton btnS, btnC, btnR;
	
	public void init() {
		lbBN = new JLabel("Booth No.");
		lbAdd = new JLabel("Booth Address");
		lbstate=new JLabel("State");
		lbConstituency=new JLabel("Constituency");
		lbcity=new JLabel("City");
		
		txBN = new JTextField(20);
		txAdd = new JTextField(20);
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
		add(lbstate);
		add(cbState);
		
		cbCity = new JComboBox<String>();
		cbCity.addItem("---- City ----");
		
		
		cbConstituency=new JComboBox<String>();
		cbConstituency.addItem("---- Constituency ----");
		
		btnS = new JButton("Submit");
		btnC = new JButton("Cancel");
		btnR = new JButton("Refresh");
		
		setLayout(new FlowLayout());
		
		add(lbcity);
		add(cbCity);
		add(lbConstituency);
		add(cbConstituency);
		add(lbBN);
		add(txBN);
		add(lbAdd);
		add(txAdd);
		add(btnS);
		add(btnR);
		add(btnC);
		
		this.cbState.addItemListener(this);
		this.cbCity.addItemListener(this);
		//this.cbConstituency.addItemListener(this);
		
		btnS.addActionListener(this);
		btnC.addActionListener(this);
		btnR.addActionListener(this);
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
			
		//		String City=(String)cbCity.getSelectedItem();
				String Constituency=((String) cbConstituency.getSelectedItem()).replaceAll(" ", "_");
				stmt.executeUpdate("create table if not exists " +  Constituency + "Tb(Booth_id varchar(20),booth_address varchar(100))");
				//stmt.executeUpdate("create table if not exists " + City + "Tb(Constituency_Name varchar(50),Area_id varchar(20))");
				PreparedStatement pstmt = con.prepareStatement("insert into "+Constituency+"Tb(Booth_id,booth_address) values(?,?)");
				pstmt.setString(1, txBN.getText());
				pstmt.setString(2, txAdd.getText());
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
			txBN.setText("");
			txAdd.setText("");
		}
	}

	@Override
	public void itemStateChanged(ItemEvent ie) {
		// TODO Auto-generated method stub
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
