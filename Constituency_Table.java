import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Constituency_Table extends JApplet implements ActionListener {
	
	JLabel lbConstituency;
	JTextField txConstituency;
	JComboBox<String> cbCity;
	JButton btnIns;
	
	public void init() {
		lbConstituency = new JLabel("Constituency");
		txConstituency = new JTextField(20);
		cbCity = new JComboBox<String>();
		cbCity.addItem("---- City ----");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
			Statement stmt = con.createStatement();
			stmt.executeUpdate("create database if not exists ElectionDb");
			stmt.execute("use ElectionDb");
			ResultSet rs = stmt.executeQuery("select distinct City_Name from CityTb");
			while(rs.next()) {
				cbCity.addItem(rs.getString("City_Name"));
			}
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		btnIns = new JButton("Insert");
		
		setLayout(new FlowLayout());
		
		add(cbCity);
		add(lbConstituency);
		add(txConstituency);
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
			stmt.executeUpdate("create table if not exists " + City + "Tb(Constituency_Name varchar(50))");
			PreparedStatement pstmt = con.prepareStatement("insert into "+City+"Tb(Constituency_Name) values(?)");
			pstmt.setString(1, txConstituency.getText());
			pstmt.execute();
		
			con.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

