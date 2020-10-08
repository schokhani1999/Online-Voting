import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class State_Table extends JApplet implements ActionListener {
	
	JLabel lbState;
	JTextField txState;
	JButton btnIns;
	
	public void init() {
		lbState = new JLabel("State");
		txState = new JTextField(20);
		btnIns = new JButton("Insert");
		
		setLayout(new FlowLayout());
		
		add(lbState);
		add(txState);
		add(btnIns);
		
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
			stmt.executeUpdate("create table if not exists StateTb(State_Name varchar(50))");
			PreparedStatement pstmt = con.prepareStatement("insert into StateTb(State_Name) values(?)");
			pstmt.setString(1, txState.getText());
			pstmt.execute();
			
			String city = txState.getText();
			stmt.executeUpdate("create table if not exists " + city + "Tb(City_Name varchar(50))");
			
			con.close();
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
