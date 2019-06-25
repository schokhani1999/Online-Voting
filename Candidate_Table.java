import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Candidate_Table extends JApplet implements ActionListener {

	JLabel lbCID, lbAID, lbEType;
	JTextField txCID, txAID;
	JComboBox<String> cbETypes;
	JButton btnS, btnC, btnR;
	
	public void init() {
		lbCID = new JLabel("Candidate ID");
		lbAID = new JLabel("Area ID");
		lbEType = new JLabel("Election Type");
		
		txCID = new JTextField(20);
		txAID = new JTextField(20);		 
		
		cbETypes = new JComboBox<String>();
		cbETypes.addItem("---- Election Type ----");
		cbETypes.addItem("Lok Sabha Elections");
		cbETypes.addItem("Vidhan Sabha Elections");
		
		btnS = new JButton("Submit");
		btnC = new JButton("Cancel");
		btnR = new JButton("Refresh");
		
		setLayout(new FlowLayout());
		
		add(lbCID);
		add(txCID);
		add(lbAID);
		add(txAID);
		add(lbEType);
		add(cbETypes);
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
				stmt.executeUpdate("create table if not exists CandidateTb(Candidate_ID varchar(50), Area_ID varchar(50), Election_Type varchar(100))");
				PreparedStatement pstmt = con.prepareStatement("insert into CandidateTb(Candidate_ID, Area_ID, Election_Type) values(?, ?, ?)");
				pstmt.setString(1, txCID.getText());
				pstmt.setString(2, txAID.getText());
				pstmt.setString(3, cbETypes.getSelectedItem().toString());
				pstmt.execute();
				
				con.close();
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		else if(src == btnR) {
			txCID.setText("");
			txAID.setText("");
			cbETypes.setSelectedIndex(0);
		}
		else if(src == btnC) {
			System.exit(-1);
		}
	}
}
