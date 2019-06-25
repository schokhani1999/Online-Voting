import java.awt.event.*;
import java.sql.*;
import java.awt.*;
import javax.swing.*;

public class Area_Table extends JApplet implements ActionListener {
	
	JLabel lbAID, lbState, lbCity, lbArea, lbNoB;
	JTextField txAID, txCity, txArea, txNoB;
	JComboBox<String> cbState;
	JButton btnS, btnC, btnR;
	
	public void init() {
		lbAID = new JLabel("Area ID");
		lbState = new JLabel("State");
		lbCity = new JLabel("City");
		lbArea = new JLabel("Area");
		lbNoB = new JLabel("Number of Booths");
		
		txAID = new JTextField(20);
		txCity = new JTextField(20);
		txArea = new JTextField(20);
		txNoB = new JTextField(20);
		
		cbState = new JComboBox<String>();
		cbState.addItem("---- State ----");
		cbState.addItem("Andhra Pradesh");
		cbState.addItem("Arunachal Pradesh");
		cbState.addItem("Assam");
		cbState.addItem("Bihar");
		cbState.addItem("Chhattisgarh");
		cbState.addItem("Goa");
		cbState.addItem("Gujrat");
		cbState.addItem("Haryana");
		cbState.addItem("Himacal Pradesh");
		cbState.addItem("Jammu & Kashmir");
		cbState.addItem("Jharkhand");
		cbState.addItem("Karnataka");
		cbState.addItem("Kerala");
		cbState.addItem("Madhya Pradesh");
		cbState.addItem("Maharashtra");
		cbState.addItem("Manipur");
		cbState.addItem("Meghalaya");
		cbState.addItem("Mizoram");
		cbState.addItem("Nagaland");
		cbState.addItem("Odisha");
		cbState.addItem("Punjab");
		cbState.addItem("Rajasthan");
		cbState.addItem("Sikkim");
		cbState.addItem("Tamil Nadu");
		cbState.addItem("Telangana");
		cbState.addItem("Tripura");
		cbState.addItem("Uttar Pradesh");
		cbState.addItem("Uttarakhand");
		cbState.addItem("West Bengal");
		
		btnS = new JButton("Submit");
		btnC = new JButton("Cancel");
		btnR = new JButton("Refresh");
		
		setLayout(new FlowLayout());
		
		add(lbAID);
		add(txAID);
		add(lbState);
		add(cbState);
		add(lbCity);
		add(txCity);
		add(lbArea);
		add(txArea);
		add(lbNoB);
		add(txNoB);		
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
				stmt.executeUpdate("create table if not exists AreaTb(Area_ID varchar(50), State varchar(50), City varchar(50), Area varchar(255), No_of_Booths int)");
				PreparedStatement pstmt = con.prepareStatement("insert into AreaTb(Area_ID, State, City, Area, No_of_Booths) values(?, ?, ?, ?, ?)");
				pstmt.setString(1, txAID.getText());
				pstmt.setString(2, cbState.getSelectedItem().toString());
				pstmt.setString(3, txCity.getText());
				pstmt.setString(4, txArea.getText());
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
			txCity.setText("");
			txArea.setText("");
			txNoB.setText("");
		}
	}
}
