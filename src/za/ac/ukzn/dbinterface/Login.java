package za.ac.ukzn.dbinterface;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class Login {

	private JFrame frmBamazonLogin;
	private JTextField txtPassword;
	private JTextField txtEmail;
	
	private static final String DB_URL = "jdbc:mysql://localhost:3306/bam";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "13082002dg";
	private static String user_email;
	private JButton btnSignUp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frmBamazonLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frmBamazonLogin = new JFrame();
		frmBamazonLogin.setTitle("BAMAZON Login");
		frmBamazonLogin.setBounds(100, 100, 510, 530);
		frmBamazonLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBamazonLogin.getContentPane().setLayout(null);
		frmBamazonLogin.setLocationRelativeTo(null);
		frmBamazonLogin.getContentPane().setLayout(null);
		
		btnSignUp = new JButton("");
		btnSignUp.setToolTipText("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignUp sign = new SignUp();
                // Make the new page/form visible
				frmBamazonLogin.setVisible(false);
                sign.setVisible(true);
			}
		});
		btnSignUp.setBackground(new Color(0, 0, 0, 0));
		btnSignUp.setBounds(34, 388, 204, 60);
		btnSignUp.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\SignUpBTN.png"));
		frmBamazonLogin.getContentPane().add(btnSignUp);
	
		txtPassword = new JTextField();
		txtPassword.setToolTipText("Please enter the password you associated with your BAMAZON account and email.");
		txtPassword.setColumns(10);
		txtPassword.setBounds(144, 226, 294, 20);
		frmBamazonLogin.getContentPane().add(txtPassword);
		
		txtEmail = new JTextField();
		txtEmail.setToolTipText("Please enter the email associated to your BAMAZON account.");
		txtEmail.setColumns(10);
		txtEmail.setBounds(117, 179, 321, 20);
		frmBamazonLogin.getContentPane().add(txtEmail);
		
		JButton btnLogin = new JButton("");
		btnLogin.setToolTipText("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verifyUser();
			}
		});
		btnLogin.setBackground(new Color(0, 0, 0, 0));
		btnLogin.setBounds(262, 388, 204, 60);
		btnLogin.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\LoginBTN.png"));
		frmBamazonLogin.getContentPane().add(btnLogin);
		
		JLabel lblSignInImage = new JLabel("");
		lblSignInImage.setBounds(0, 0, 498, 495);
		lblSignInImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignInImage.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\LoginPageImage.png"));
		frmBamazonLogin.getContentPane().add(lblSignInImage);
	}
	
	public void setVisible(boolean visible) 
	{
		frmBamazonLogin.setVisible(visible);
	}
	
	private void verifyUser() 
	{
		String email = txtEmail.getText();
		String password = txtPassword.getText();
		String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

		// perform null check
		if (email.isEmpty() || password.isEmpty() || (password.length() < 8) || (!email.matches(emailPattern)))
		{
			JOptionPane.showMessageDialog(null, "Please enter your details correctly.");
			//clear text fields
			txtEmail.setText(""); 
			txtPassword.setText(""); 
			return;
		}

		try 
		{
			Connection connect = getConnection();
			String query = "SELECT COUNT(*) FROM user_t WHERE email_address = ? AND user_password = ?";
			PreparedStatement statement = connect.prepareStatement(query);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			
			//user already exists, display dialog box message
			if (resultSet.getInt(1) > 0) 
			{
				JOptionPane.showMessageDialog(null, "Login Successful!");
				setUser_email(email);
				
				//clear text field
				txtEmail.setText("");
				txtPassword.setText(""); 
				
				//go to user menu
				User user = new User();
				frmBamazonLogin.setVisible(false);
                user.setVisible(true);
                user.setUser_email(email);
                user.updateDisplay();
			}
			
			else
			{
				JOptionPane.showMessageDialog(null, "Wrong username or password entered!");
				//clear text fields
				txtEmail.setText("");
				txtPassword.setText(""); 
				return;
			}
		}

		catch (Exception e) 
		{
			System.out.println("Something went wrong in trying to verify the user.");
			e.printStackTrace();
		}
	}
	
	private static Connection getConnection() {
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

	public static String getUser_email() {
		return user_email;
	}

	public static void setUser_email(String user_email) {
		Login.user_email = user_email;
	}
}
