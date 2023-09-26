package za.ac.ukzn.dbinterface;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class Start {

	private JFrame frmBamazonStart;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Start window = new Start();
					window.frmBamazonStart.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Start() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBamazonStart = new JFrame();
		frmBamazonStart.setTitle("BAMAZON Start");
		frmBamazonStart.setBounds(100, 100, 510, 500);
		frmBamazonStart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBamazonStart.setLocationRelativeTo(null);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setToolTipText("Click anywhere on the screen.");
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\StartUpImage.png"));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignUp signUpPage = new SignUp();
                // Make the new page/form visible
				frmBamazonStart.setVisible(false);
                signUpPage.setVisible(true);
			}
		});
		frmBamazonStart.getContentPane().add(btnNewButton, BorderLayout.CENTER);
	}

}
