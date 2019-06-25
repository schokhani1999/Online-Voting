import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class City_Table extends JApplet implements ActionListener, ItemListener {
	
	JLabel lbCity;
	JTextField txCity;
	JComboBox<String> cbState;
	JButton btnIns;
	
	public void init() {
		lbCity = new JLabel("City");
		txCity = new JTextField(20);
		cbState = new JComboBox<String>();
		cbState.addItem("---- State ----");
		btnIns = new JButton("Insert");
		
		setLayout(new FlowLayout());
		
		add(cbState);
		add(lbCity);
		add(txCity);
		add(btnIns);
		
		cbState.addItemListener(this);
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
			stmt.executeUpdate("create table if not exists CityTb(City_Name varchar(50))");
			PreparedStatement pstmt = con.prepareStatement("insert into CityTb(City_Name) values(?)");
			pstmt.setString(1, txCity.getText());
			pstmt.execute();
			
			String city = txCity.getText();
			stmt.executeUpdate("create table if not exists " + city + "Tb(City_Name varchar(50))");
			
			con.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent ie) {
		// TODO Auto-generated method stub
		if(ie.getStateChange() != ItemEvent.DESELECTED) return;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
			Statement stmt = con.createStatement();
			stmt.executeUpdate("create database if not exists ElectionDb");
			stmt.execute("use ElectionDb");
			ResultSet rs = stmt.executeQuery("select State_Name from StateTb");
			while(rs.next()) {
				cbState.addItem(rs.getString("State_Name"));
			}
			con.close();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
