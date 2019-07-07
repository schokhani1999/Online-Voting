import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Constituency_Table extends JApplet implements ActionListener,ItemListener{
	
	JLabel lbConstituency,lbAID;
	JTextField txConstituency,txAID;
	JComboBox<String> cbCity,cbState;
	JButton btnIns;
	
	public void init() {
		lbConstituency = new JLabel("Constituency");
		lbAID=new JLabel("Area Id");
		
		txConstituency = new JTextField(20);
		txAID=new JTextField(20);
		
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
		add(cbState);
		this.cbState.addItemListener(this);
		
		
		cbCity = new JComboBox<String>();
		cbCity.addItem("---- City ----");
		
		btnIns = new JButton("Insert");
		
		setLayout(new FlowLayout());
		
		add(cbCity);
		add(lbConstituency);
		add(txConstituency);
		add(lbAID);
		add(txAID);
		add(btnIns);
		
	//	cbCity.addItemListener(this);
		btnIns.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
			Statement stmt = con.createStatement();
			stmt.executeUpdate("create database if not exists ElectionDb");
			stmt.execute("use ElectionDb");
		
			String City=(String)cbCity.getSelectedItem();
			String Constituency=(String) txConstituency.getText().replaceAll(" ", "_");
			stmt.executeUpdate("create table if not exists " +  Constituency + "Tb(Booth_id varchar(20),booth_address varchar(100))");
			stmt.executeUpdate("create table if not exists " + City + "Tb(Constituency_Name varchar(50),Area_id varchar(20))");
			PreparedStatement pstmt = con.prepareStatement("insert into "+City+"Tb(Constituency_Name,Area_id) values(?,?)");
			pstmt.setString(1, txConstituency.getText());
			pstmt.setString(2, txAID.getText());
			pstmt.execute();
		
			con.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent ie) {
		if(cbState.getSelectedIndex()==0 || ie.getStateChange()== ItemEvent.DESELECTED)
			return;
		else
		{
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
		validate();
	}
}

