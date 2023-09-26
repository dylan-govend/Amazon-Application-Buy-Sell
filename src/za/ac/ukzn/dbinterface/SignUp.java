package za.ac.ukzn.dbinterface;

import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Color;
import java.sql.*;

public class SignUp {

	private JFrame frmBamazonSignup;
	private JTextField txtName;
	private JTextField txtSurname;
	private JTextField txtNumber;
	private JTextField txtEmail;
	private JTextField txtPassword;

	private static final String DB_URL = "jdbc:mysql://localhost:3306/bam";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "13082002dg";
	private static String user_email;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp window = new SignUp();
					window.frmBamazonSignup.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SignUp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBamazonSignup = new JFrame();
		frmBamazonSignup.setTitle("BAMAZON Sign-up");
		frmBamazonSignup.setBounds(100, 100, 510, 530);
		frmBamazonSignup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBamazonSignup.setLocationRelativeTo(null);
		frmBamazonSignup.getContentPane().setLayout(null);

		txtPassword = new JTextField();
		txtPassword.setToolTipText("Please enter a password to protect your account.");
		txtPassword.setColumns(10);
		txtPassword.setBounds(169, 319, 241, 20);
		frmBamazonSignup.getContentPane().add(txtPassword);

		txtEmail = new JTextField();
		txtEmail.setToolTipText("Please enter your correct email-address.");
		txtEmail.setColumns(10);
		txtEmail.setBounds(141, 269, 269, 20);
		frmBamazonSignup.getContentPane().add(txtEmail);

		txtNumber = new JTextField();
		txtNumber.setToolTipText("Please enter your correct contact number.");
		txtNumber.setColumns(10);
		txtNumber.setBounds(210, 219, 200, 20);
		frmBamazonSignup.getContentPane().add(txtNumber);

		txtSurname = new JTextField();
		txtSurname.setToolTipText("Please enter your correct surname.");
		txtSurname.setColumns(10);
		txtSurname.setBounds(157, 170, 253, 20);
		frmBamazonSignup.getContentPane().add(txtSurname);

		txtName = new JTextField();
		txtName.setToolTipText("Please enter your correct name.");
		txtName.setBounds(141, 120, 269, 20);
		frmBamazonSignup.getContentPane().add(txtName);
		txtName.setColumns(10);
		
		JButton btnSignUp = new JButton("");
		btnSignUp.setToolTipText("Sign Up");
		btnSignUp.setBackground(new Color(0, 0, 0, 0));
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertUser();
			}
		});
		btnSignUp.setBounds(34, 386, 204, 60);
		btnSignUp.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\SignUpBTN.png"));
		frmBamazonSignup.getContentPane().add(btnSignUp);

		JButton btnLogin = new JButton("");
		btnLogin.setToolTipText("Login");
		btnLogin.setBackground(new Color(0, 0, 0, 0));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login loginPage = new Login();
				frmBamazonSignup.setVisible(false);
                loginPage.setVisible(true);
			}
		});
		btnLogin.setBounds(259, 386, 204, 60);
		btnLogin.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\LoginBTN.png"));
		frmBamazonSignup.getContentPane().add(btnLogin);

		JLabel lblSignInImage = new JLabel("");
		lblSignInImage.setBounds(0, 0, 498, 495);
		lblSignInImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignInImage.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\SignUpImage.png"));
		frmBamazonSignup.getContentPane().add(lblSignInImage);
	}

	public void setVisible(boolean visible) 
	{
		frmBamazonSignup.setVisible(visible);
	}

	private void insertUser() 
	{
	    String namePattern = "^[a-zA-Z]+$";
	    String contactNumberPattern = "^[0-9]{10}$";
	    String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

		String email = txtEmail.getText();
		String password = txtPassword.getText();
		String number = txtNumber.getText();
		String name = txtName.getText();
		String surname = txtSurname.getText();

		// perform check
		if (email.isEmpty() || password.isEmpty() || number.isEmpty() || name.isEmpty() || surname.isEmpty()
				|| (password.length() < 8) || (!name.matches(namePattern)) || (!surname.matches(namePattern)) 
				|| (!email.matches(emailPattern)) || (!number.matches(contactNumberPattern))) 
		{
			JOptionPane.showMessageDialog(null, "Please enter your details correctly.");
			//clear text fields
			txtEmail.setText(""); 
			txtPassword.setText(""); 
			txtNumber.setText(""); 
			txtName.setText(""); 
			txtSurname.setText(""); 
			return;
		}

		try 
		{
			Connection connect = getConnection();
			String query = "SELECT COUNT(*) FROM user_t WHERE email_address = ?";
			PreparedStatement statement = connect.prepareStatement(query);
			statement.setString(1, email);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			
			//user already exists, display dialog box message
			if (resultSet.getInt(1) > 0) 
			{
				JOptionPane.showMessageDialog(null, "User already exists!");
				//clear text fields
				txtEmail.setText("");
				txtPassword.setText(""); 
				txtNumber.setText(""); 
				txtName.setText(""); 
				txtSurname.setText(""); 
				return;
			}
			
			//user does not exist, add user in database
			else 
			{
				String userQuery = "INSERT INTO user_t(email_address, user_password, contact_number, first_name, last_name) "
						+ "VALUES (?, ?, ?, ?, ?)";
				String customerQuery = "INSERT INTO customer(customer_id) VALUES(?)";
				PreparedStatement insertStatement = connect.prepareStatement(userQuery);
				PreparedStatement otherInsertStatement = connect.prepareStatement(customerQuery);

				insertStatement.setString(1, email);
				insertStatement.setString(2, password);
				insertStatement.setString(3, number);
				insertStatement.setString(4, name);
				insertStatement.setString(5, surname);
				otherInsertStatement.setString(1, email);
				insertStatement.executeUpdate();
				otherInsertStatement.executeUpdate();
				
				setUser_email(email);	
				JOptionPane.showMessageDialog(null, "User inserted successfully!");
				
				User user = new User();
				frmBamazonSignup.setVisible(false);
                user.setVisible(true);
                user.setUser_email(email);
                user.updateDisplay();
			}
		}

		catch (Exception e) 
		{
			System.out.println("Something went wrong in trying to enter the user.");
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
		SignUp.user_email = user_email;
	}
}
