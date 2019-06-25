import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class NCP_Table extends JApplet implements ActionListener, ItemListener {

	JLabel lbCid, lbRole, lbPhoto, lbName, lbDob, lbJoin, lbBelong, lbGender, lbLeader;
	JTextField txCid, txName, txBelong;
	JComboBox<String> cbD, cbM, cbY, cbJY, cbRole;
	JRadioButton rbGM, rbGF, rbS, rbR, rbN;
	ButtonGroup grpGen, grpLead;
	JButton btnS, btnC, btnR, btnB;
	JFileChooser jfc;
	FileInputStream fin;
	File imgFile;
	
	public void init() {
		
		lbCid = new JLabel("Candidate ID");
		lbRole = new JLabel("Candidate Role");
		lbPhoto = new JLabel();
		lbPhoto.getPreferredSize();
		lbPhoto.setBorder(BorderFactory.createLineBorder(Color.GRAY, 20));
		lbName = new JLabel("Candidate Name");
		lbDob = new JLabel("DoB");
		lbJoin = new JLabel("Party Joining Year");
		lbBelong = new JLabel("Belongs From");
		lbGender = new JLabel("Gender");
		lbLeader = new JLabel("Leader Type");
		
		txCid = new JTextField(20);
		txName = new JTextField(20);
		txBelong = new JTextField(20);
		
		cbD = new JComboBox<String>();
		cbD.addItem("---- Day ----");
		cbM = new JComboBox<String>();
		cbM.addItem("---- Month ----");
		for(int i = 1; i <= 12; i++) {
			cbM.addItem(i + "");
		}
		cbY = new JComboBox<String>();
		cbY.addItem("---- Year ----");
		for(int i = 1950; i <= 2000; i++) {
			cbY.addItem(i + "");
		}
		cbJY = new JComboBox<String>();
		cbJY.addItem("---- Joining Year ----");
		for(int i = 1950; i <= 2000; i++) {
			cbJY.addItem(i + "");
		}
		cbRole = new JComboBox<String>();
		cbRole.addItem("---- Candidate Role ----");
		cbRole.addItem("Prime Minister");
		cbRole.addItem("Central Minister");
		cbRole.addItem("State Minister");
		cbRole.addItem("MP(Lok Sabha)");
		cbRole.addItem("MLA(Vidhan Sabha)");
		cbRole.addItem("MP(Rajya Sabha)");
		cbRole.addItem("Member");
		
		rbGM = new JRadioButton("Male");
		rbGF = new JRadioButton("Female");
		rbS = new JRadioButton("State");
		rbR = new JRadioButton("Regional");
		rbN = new JRadioButton("National");
		
		grpGen = new ButtonGroup();
		grpGen.add(rbGM);
		grpGen.add(rbGF);
		grpLead = new ButtonGroup();
		grpLead.add(rbR);
		grpLead.add(rbS);
		grpLead.add(rbN);
		
		btnS = new JButton("Submit");
		btnC = new JButton("Cancel");
		btnR = new JButton("Refresh");
		btnB = new JButton("Browse..");
		
		setLayout(new FlowLayout());
		add(lbCid);
		add(txCid);
		add(lbName);
		add(txName);
		add(lbPhoto);
		add(btnB);
		add(lbRole);
		add(cbRole);
		add(lbDob);
		add(cbD);
		add(cbM);
		add(cbY);
		add(lbJoin);
		add(cbJY);
		add(lbBelong);
		add(txBelong);
		add(lbGender);
		add(rbGM);
		add(rbGF);
		add(lbLeader);
		add(rbN);
		add(rbS);
		add(rbR);
		add(btnS);
		add(btnR);
		add(btnC);
		
		cbM.addItemListener(this);
		cbY.addItemListener(this);
		
		btnS.addActionListener(this);
		btnC.addActionListener(this);
		btnR.addActionListener(this);
		btnB.addActionListener(this);
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
				stmt.executeUpdate("create table if not exists NCPTb(Candidate_ID varchar(50), Name varchar(150), Photo longblob, Role varchar(100), DoB date, Join_Year int, Belongs varchar(100), Gender varchar(50), Leader varchar(50))");
				PreparedStatement pstmt = con.prepareStatement("insert into NCPTb(Candidate_ID, Name, Photo, Role, DoB, Join_Year, Belongs, Gender, Leader) values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
				pstmt.setString(1, txCid.getText());
				fin = new FileInputStream(imgFile);
				pstmt.setBinaryStream(3, fin, (int)imgFile.length());
				pstmt.setString(2, txName.getText());
				pstmt.setString(4, cbRole.getSelectedItem().toString());
				int dd = Integer.parseInt(cbD.getSelectedItem().toString());
				int mm = Integer.parseInt(cbM.getSelectedItem().toString());;
				int yyyy = Integer.parseInt(cbY.getSelectedItem().toString());;
				Date dob = new Date(yyyy - 1900, mm - 1, dd);
				pstmt.setDate(5, dob);
				pstmt.setInt(6, Integer.parseInt(cbJY.getSelectedItem().toString()));
				pstmt.setString(7, txBelong.getText());
				String gender = "Male";
				if(rbGF.isSelected())
					gender = "Female";
				pstmt.setString(8, gender);
				String leader = "National";
				if(rbS.isSelected())
					leader = "State";
				else if(rbR.isSelected())
					leader = "Regional";
				pstmt.setString(9, leader);
				pstmt.executeUpdate();
				
				con.close();				
			} catch (ClassNotFoundException | SQLException | FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(src == btnR) {
			txCid.setText("");
			txName.setText("");
			txBelong.setText("");
			
			cbD.removeAllItems();
			cbD.addItem("---- Day ----");
			cbM.setSelectedIndex(0);
			cbY.setSelectedIndex(0);
			cbRole.setSelectedIndex(0);
			cbJY.setSelectedIndex(0);
			
			grpGen.clearSelection();
			grpLead.clearSelection();
		}
		else if(src == btnC) {
			System.exit(-1);
		}
		else if(src == btnB) {
			jfc = new JFileChooser();
			int ans = jfc.showOpenDialog(null);
			if(ans == JFileChooser.APPROVE_OPTION) {
				imgFile = jfc.getSelectedFile();
				lbPhoto.setIcon(new ImageIcon(imgFile + ""));
			}
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
	}
}
