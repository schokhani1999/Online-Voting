import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class Party_Table extends JApplet implements ActionListener {

	JLabel lbName, lbLogo, lblogo, lbEstbYear, lbCoF, lbPres, lbtype, lbGovtRole;
	JTextField txName, txCoF, txPres;
	JComboBox<String> cbrole, cbEstbYear;
	JRadioButton rbN, rbR;
	ButtonGroup grp;
	JButton btnS, btnR, btnC, btnB;
	JFileChooser jfc;
	FileInputStream fin;
	File imgFile;
	
	public void init() {
		lbName = new JLabel("Name");
		lbLogo = new JLabel("Logo");
		lblogo = new JLabel();
		lblogo.getPreferredSize();
		lblogo.setBorder(BorderFactory.createLineBorder(Color.GRAY, 20));
		lbEstbYear = new JLabel("Established Year");
		lbCoF = new JLabel("Co-Founder");
		lbPres = new JLabel("President");
		lbtype = new JLabel("Party Type");
		lbGovtRole = new JLabel("Government Role");
		
		txName = new JTextField(20);
		txCoF = new JTextField(20);
		txPres = new JTextField(20);
		
		cbrole = new JComboBox<String>();
		cbrole.addItem("---- Role of Party ----");
		cbrole.addItem("Ruling Party");
		cbrole.addItem("Opposition Party");
		cbrole.addItem("Political Party");
		
		cbEstbYear = new JComboBox<String>();
		cbEstbYear.addItem("---- Established Year ----");
		for(int i = 1880; i <= 2015; i++) {
			cbEstbYear.addItem(i + "");
		}
		
		rbN = new JRadioButton("National");
		rbR = new JRadioButton("Regional");
		
		grp = new ButtonGroup();
		grp.add(rbN);
		grp.add(rbR);
		
		btnS = new JButton("Submit");
		btnR = new JButton("Refresh");
		btnC = new JButton("Cancel");
		btnB = new JButton("Browse..");
		
		setLayout(new FlowLayout());
		
		add(lbName);
		add(txName);
		add(lbLogo);
		add(lblogo);
		add(btnB);
		add(lbEstbYear);
		add(cbEstbYear);
		add(lbCoF);
		add(txCoF);
		add(lbPres);
		add(txPres);
		add(lbGovtRole);
		add(cbrole);
		add(lbtype);
		add(rbN);
		add(rbR);
		add(btnS);
		add(btnR);
		add(btnC);
		
		btnS.addActionListener(this);
		btnR.addActionListener(this);
		btnC.addActionListener(this);
		btnB.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		Object src = ae.getSource();
		
		String type = "Regional";
		if(rbN.isSelected())
			type = "National";
		
		if(src == btnS) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
				Statement stmt = con.createStatement();
				stmt.executeUpdate("create database if not exists ElectionDb");
				stmt.execute("use ElectionDb");
				stmt.executeUpdate("create table if not exists PartyTb(Name varchar(50), Logo longblob, est_year int, Co_Founder varchar(150), President varchar(50), Type varchar(20), Role varchar(50))");
				PreparedStatement pstmt = con.prepareStatement("insert into PartyTb(Name, Logo, est_year, Co_Founder, President, Type, Role) values(?, ?, ?, ?, ?, ?, ?)");
				pstmt.setString(1, txName.getText());
				fin = new FileInputStream(imgFile);
				pstmt.setBinaryStream(2, fin, (int)imgFile.length());
				pstmt.setInt(3, Integer.parseInt(cbEstbYear.getSelectedItem().toString()));
				pstmt.setString(4, txCoF.getText());
				pstmt.setString(5, txPres.getText());
				pstmt.setString(6, type);
				pstmt.setString(7, cbrole.getSelectedItem().toString());
				
				pstmt.executeUpdate();
				
				String party = txName.getText();
				stmt.executeUpdate("create table if not exists " + party + "Tb(Candidate_id varchar(50), Name varchar(50), Photo longblob, Role varchar(50), DoB date, Join_Year int, Belongs varchar(50), Gender varchar(50), Leader varchar(50))");
				
				con.close();				
			} catch (ClassNotFoundException | SQLException | FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(src == btnR) {
			txName.setText("");
			lblogo.setIcon(new ImageIcon());
			txCoF.setText("");
			txPres.setText("");
			grp.clearSelection();
			cbEstbYear.setSelectedIndex(0);
			cbrole.setSelectedIndex(0);
		}
		else if(src == btnC) {
			System.exit(-1);
		}
		else if(src == btnB) {
			jfc = new JFileChooser();
			int ans = jfc.showOpenDialog(null);
			if(ans == JFileChooser.APPROVE_OPTION) {
				imgFile = jfc.getSelectedFile();
				lblogo.setIcon(new ImageIcon(imgFile + ""));
			}
		}
	}
}
