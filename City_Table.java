import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class City_Table extends JApplet implements ActionListener {
	
	JLabel lbCity;
	JTextField txCity;
	JComboBox<String> cbState;
	JButton btnIns;
	
	public void init() {
		lbCity = new JLabel("City");
		txCity = new JTextField(20);
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
		
		btnIns = new JButton("Insert");
		
		setLayout(new FlowLayout());
		
		add(cbState);
		add(lbCity);
		add(txCity);
		add(btnIns);
		
	//	cbState.addItemListener(this);
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
			String State=(String)cbState.getSelectedItem();
			stmt.executeUpdate("create table if not exists " +  txCity.getText() + "Tb(Constituency_Name varchar(50))");
			stmt.executeUpdate("create table if not exists " + State + "Tb(City_Name varchar(50))");
			PreparedStatement pstmt = con.prepareStatement("insert into "+State+"Tb(City_Name) values(?)");
			pstmt.setString(1, txCity.getText());
			pstmt.execute();
		
			con.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

