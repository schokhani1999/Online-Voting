import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Booth_Table extends JApplet implements ActionListener {

	JLabel lbBN, lbAdd, lbAID;
	JTextField txBN, txAdd, txAID;
	JButton btnS, btnC, btnR;
	
	public void init() {
		lbBN = new JLabel("Booth No.");
		lbAdd = new JLabel("Booth Address");
		lbAID = new JLabel("Area ID");
		
		txBN = new JTextField(20);
		txAdd = new JTextField(20);
		txAID = new JTextField(20);
		
		btnS = new JButton("Submit");
		btnC = new JButton("Cancel");
		btnR = new JButton("Refresh");
		
		setLayout(new FlowLayout());
		
		add(lbBN);
		add(txBN);
		add(lbAdd);
		add(txAdd);
		add(lbAID);
		add(txAID);
		add(btnS);
		add(btnR);
		add(btnC);
		
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
				stmt.executeUpdate("create table if not exists BoothTb(Booth_No varchar(20), Address varchar(255), Area_ID varchar(50))");
				PreparedStatement pstmt = con.prepareStatement("insert into BoothTb(Booth_No, Address, Area_ID) value(?, ?, ?)");
				pstmt.setString(1, txBN.getText());
				pstmt.setString(2, txAdd.getText());
				pstmt.setString(3, txAID.getText());
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
			txAID.setText("");
		}
	}

}
