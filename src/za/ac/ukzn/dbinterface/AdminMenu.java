package za.ac.ukzn.dbinterface;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JFrame;

public class AdminMenu {

	private JFrame frmAdministratorMenu;

	private String user_email;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMenu window = new AdminMenu();
					window.frmAdministratorMenu.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AdminMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAdministratorMenu = new JFrame();
		frmAdministratorMenu.setTitle("Administrator Menu");
		frmAdministratorMenu.setBounds(100, 100, 700, 500);
		frmAdministratorMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAdministratorMenu.setLocationRelativeTo(null);
		frmAdministratorMenu.getContentPane().setLayout(null);
	}
	
	public void setVisible(boolean visible) 
	{
		frmAdministratorMenu.setVisible(visible);
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
