import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
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
