import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Epic_no extends JApplet implements ActionListener
{
	JButton btsubmit,btrefresh;
	JLabel lbepic;
	JTextField txepic;
	public void init()
	{
		Color c1=new Color(53, 45, 26);
		setBackground(c1);
		setLayout(new FlowLayout());
		
		lbepic=new JLabel("Enter your epic number");
		txepic=new JTextField(10);
		add(lbepic);
		add(txepic);
		
		btsubmit=new JButton("Submit");
		btrefresh=new JButton("Refresh");
		add(btsubmit);
		add(btrefresh);
	}
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
