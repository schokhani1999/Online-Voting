import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Voter_Table extends JApplet implements ActionListener, ItemListener {

	JLabel lbVID, lbPhoto, lbPh, lbName, lbFName, lbGen, lbDob, lbAdd, lbState, lbCity, lbDate, lbAID;
	JTextField txVID, txName, txFName, txAdd, txAID, txCity;
	JComboBox<String> cbD, cbM, cbY, cbState, cbDD, cbMM, cbYY;
	JRadioButton rbM, rbF;
	ButtonGroup grpGen;
	JButton btnS, btnC, btnR, btnB;
	
	public void init() {
		lbVID = new JLabel("Voter ID");
		lbPhoto = new JLabel("Photo");
		lbPh = new JLabel();
		lbPh.getPreferredSize();
		lbPh.setBorder(BorderFactory.createLineBorder(Color.GRAY, 20));
		lbName = new JLabel("Voter Name");
		lbFName = new JLabel("Father Name");
		lbGen = new JLabel("Gender");
		lbDob = new JLabel("Date of Birth");
		lbAdd = new JLabel("Address");
		lbState = new JLabel("State");
		lbCity = new JLabel("City");
		lbDate = new JLabel("Date");
		lbAID = new JLabel("Area ID");
		
		txVID = new JTextField(20);
		txName = new JTextField(20);
		txFName = new JTextField(20);
		txAdd = new JTextField(20);
		txAID = new JTextField(20);
		txCity = new JTextField(20);
		
		cbD = new JComboBox<String>();
		cbD.addItem("---- Day ----");
		
		cbM = new JComboBox<String>();
		cbM.addItem("---- Month ----");
		for(int i = 1; i < 13; i++)
			cbM.addItem(i + "");
		
		cbY = new JComboBox<String>();
		cbY.addItem("---- Year ----");
		for(int i = 1950; i <= 2001; i++)
			cbY.addItem(i + "");
		
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
		
		cbDD = new JComboBox<String>();
		cbDD.addItem("---- Day ----");
		
		cbMM = new JComboBox<String>();
		cbMM.addItem("---- Month ----");
		for(int i = 1; i < 13; i++)
			cbMM.addItem(i + "");
		
		cbYY = new JComboBox<String>();
		cbYY.addItem("---- Year ----");
		for(int i = 1950; i <= 2001; i++)
			cbYY.addItem(i + "");
		
		rbM = new JRadioButton("Male");
		rbF = new JRadioButton("Female");
		
		grpGen = new ButtonGroup();
		grpGen.add(rbM);
		grpGen.add(rbF);
		
		btnS = new JButton("Submit");
		btnC = new JButton("Cancel");
		btnR = new JButton("Refresh");
		btnB = new JButton("Browse..");
		
		setLayout(new FlowLayout());
		
		add(lbVID);
		add(txVID);
		add(lbPhoto);
		add(lbPh);
		add(lbName);
		add(txName);
		add(lbFName);
		add(txFName);
		add(lbGen);
		add(rbM);
		add(rbF);
		add(lbDob);
		add(cbD);
		add(cbM);
		add(cbY);
		add(lbAdd);
		add(txAdd);
		add(lbState);
		add(cbState);
		add(lbCity);
		add(txCity);
		add(lbDate);
		add(cbDD);
		add(cbMM);
		add(cbYY);
		add(lbAID);
		add(txAID);
		add(btnS);
		add(btnR);
		add(btnC);
		
		btnS.addActionListener(this);
		btnC.addActionListener(this);
		btnR.addActionListener(this);
		btnB.addActionListener(this);
		
		cbM.addItemListener(this);
		cbY.addItemListener(this);
		cbMM.addItemListener(this);
		cbYY.addItemListener(this);
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
				stmt.executeUpdate("create table if not exists VoterTb(Voter_ID varchar(50), Voter_Photo longblob, Voter_Name varchar(50), Father_Name varchar(80), Gender varchar(20), DoB date, Address varchar(250), State varchar(50), City varchar(50), Voter_ID_Date date, Area_ID varchar(50))");
				PreparedStatement pstmt = con.prepareStatement("insert into VoterTb(Voter_ID, Voter_Photo, Voter_Name, Father_Name, Gender, DoB, Address, State, City, Voter_ID_Date, Area_ID) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				pstmt.setString(1, txVID.getText());
				pstmt.setString(2, "");
				pstmt.setString(3, txName.getText());
				pstmt.setString(4, txFName.getText());
				String g = "Male";
				if(rbF.isSelected())
					g = "Female";
				pstmt.setString(5, g);
				int dd = Integer.parseInt(cbD.getSelectedItem().toString());
				int mm = Integer.parseInt(cbM.getSelectedItem().toString());;
				int yyyy = Integer.parseInt(cbY.getSelectedItem().toString());;
				Date dob = new Date(yyyy - 1900, mm - 1, dd);
				pstmt.setDate(6, dob);
				pstmt.setString(7, txAdd.getText());
				pstmt.setString(8, cbState.getSelectedItem().toString());
				pstmt.setString(9, txCity.getText());
				dd = Integer.parseInt(cbD.getSelectedItem().toString());
				mm = Integer.parseInt(cbM.getSelectedItem().toString());;
				yyyy = Integer.parseInt(cbY.getSelectedItem().toString());;
				Date voter_date = new Date(yyyy - 1900, mm - 1, dd);
				pstmt.setDate(10, voter_date);
				pstmt.setString(11, txAID.getText());
				
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
			txVID.setText("");
			txName.setText("");
			txFName.setText("");
			txAdd.setText("");
			txAID.setText("");
			txCity.setText("");
			
			lbPh.setIcon(new ImageIcon());
			
			cbD.setSelectedIndex(0);
			cbM.setSelectedIndex(0);
			cbY.setSelectedIndex(0);
			cbState.setSelectedIndex(0);
			cbDD.setSelectedIndex(0);
			cbMM.setSelectedIndex(0);
			cbYY.setSelectedIndex(0);
			
			grpGen.clearSelection();
		}
		else if(src == btnB) {
			
		}
	}	
	@Override
	public void itemStateChanged(ItemEvent ie) {
		// TODO Auto-generated method stub
		if(ie.getStateChange() == ItemEvent.DESELECTED) return;
				
		Object src = ie.getSource();
		
		if(cbY.getSelectedIndex() != 0 && cbM.getSelectedIndex() != 0) {
			int m = Integer.parseInt(cbM.getSelectedItem().toString()); 
			int y = Integer.parseInt(cbY.getSelectedItem().toString());
			int d = 0;
			
			if(m == 2) {
				if(y % 4 == 0) {
					d = 29;
				}
				else {
					d = 28;
				}
			}
			else if(m == 4 || m == 6 || m == 9 || m == 11) {
				d = 30;
			}
			else {
				d = 31;
			}
			
			for(int i = 1; i <= d; i++) {
				cbD.addItem(i + "");
			}
		}
		
		if(cbYY.getSelectedIndex() != 0 && cbMM.getSelectedIndex() != 0) {
			int m = Integer.parseInt(cbM.getSelectedItem().toString()); 
			int y = Integer.parseInt(cbY.getSelectedItem().toString());
			int d = 0;
			
			if(m == 2) {
				if(y % 4 == 0) {
					d = 29;
				}
				else {
					d = 28;
				}
			}
			else if(m == 4 || m == 6 || m == 9 || m == 11) {
				d = 30;
			}
			else {
				d = 31;
			}
			
			for(int i = 1; i <= d; i++) {
				cbDD.addItem(i + "");
			}
		}
	}
}
