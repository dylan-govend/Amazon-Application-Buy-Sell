package za.ac.ukzn.dbinterface;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JFrame;

public class ShipperMenu {

	private JFrame frmShipperMenu;
	
	private String user_email;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShipperMenu window = new ShipperMenu();
					window.frmShipperMenu.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ShipperMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmShipperMenu = new JFrame();
		frmShipperMenu.setTitle("Shipper Menu");
		frmShipperMenu.setBounds(100, 100, 700, 500);
		frmShipperMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmShipperMenu.setLocationRelativeTo(null);
		frmShipperMenu.getContentPane().setLayout(null);
	}
	
	public void setVisible(boolean visible) 
	{
		frmShipperMenu.setVisible(visible);
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	
	private static Connection getConnection() {
		final String DB_URL = "jdbc:mysql://localhost:3306/bam";
		final String DB_USERNAME = "root";
		final String DB_PASSWORD = "13082002dg";
		
		try {
			// load the driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
		}

		catch (Exception e) {
			System.out.println("Something went wrong while trying to establish a connection.");
			e.printStackTrace();
			return null;
		}
	}
}
