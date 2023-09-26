package za.ac.ukzn.dbinterface;

import java.awt.EventQueue;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.sql.*;

public class User {

	private JFrame frmUser;
	private JTextField txtContact;
	private JTextField txtSurname;
	private JTextField txtName;
	private JButton btnAddress;
	private JButton btnSave;
	private JTextPane txtDisplay;
	private JLabel lblBackGround;

	private String user_email;
	private String contact;
	private String name;
	private String surname;
	private String userType;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User window = new User();
					window.frmUser.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public User() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initialize() {
		frmUser = new JFrame();
		frmUser.setTitle("BAMAZON User Page");
		frmUser.setBounds(100, 100, 500, 500);
		frmUser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUser.getContentPane().setLayout(null);
		frmUser.setLocationRelativeTo(null);
		frmUser.getContentPane().setLayout(null);

		ImageIcon userChange = new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\UserChangePage.png");
		ImageIcon shipperChange = new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\TransporterChangePage.png");
		ImageIcon shopChange = new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\ShopChangePage.png");
		ImageIcon registerShipper = new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\RegisterShipper.png");
		ImageIcon registerShop = new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\RegisterShop.png");

		JComboBox cmbUser = new JComboBox();
		cmbUser.setToolTipText("Do you want to buy, sell, ship, or manage using BAMAZON.");
		cmbUser.setBackground(new Color(255, 255, 255));
		cmbUser.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		cmbUser.setModel(new DefaultComboBoxModel(
				new String[] { "Select User Type", "Customer", "Seller", "Administrator", "Shipper" }));
		cmbUser.addItemListener((ItemListener) new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String selectedUser = (String) e.getItem();

					if (selectedUser == "Select User Type" || selectedUser == "Customer" || selectedUser.isEmpty()) {
						txtUserChange();
						lblBackGround.setIcon(userChange);
						setUserType("Customer");
					}

					else if (selectedUser == "Seller") {
						if (isUserType("seller", "Merchant", false)) {
							txtChangeShop();
							lblBackGround.setIcon(shopChange);
						}

						else {
							txtRegisterShop();
							lblBackGround.setIcon(registerShop);
						}

						setUserType("Seller");
					}

					else if (selectedUser == "Administrator") {
						txtUserChange();
						lblBackGround.setIcon(userChange);
						setUserType("User");
					}

					else {
						if (isUserType("shipper", "Transporter", false)) {
							txtChangeShipper();
							lblBackGround.setIcon(shipperChange);
						}

						else {
							txtRegisterShipper();
							lblBackGround.setIcon(registerShipper);
						}

						setUserType("User");
					}
				}
			}
		});
		cmbUser.setBounds(195, 202, 231, 25);
		frmUser.getContentPane().add(cmbUser);

		btnSave = new JButton("");
		btnSave.setToolTipText("Save Changes (Back to Main Menu)");
		btnSave.setBackground(new Color(255, 255, 255));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedUser = (String) cmbUser.getSelectedItem();

				if (selectedUser == "Select User Type" || selectedUser == "Customer") {
					registerUser("customer", true);

					if (isUserType("customer", "Customer", true)) {
						BuyMenu menu = new BuyMenu();
						menu.setVisible(true);
						menu.setUser_email(user_email);
						frmUser.setVisible(false);
					}

					else {
						return;
					}
				}

				else if (selectedUser == "Seller") {
					if (isUserType("seller", "Merchant", true)) {
						registerUser("seller", true);
						SellerMenu menu = new SellerMenu();
						menu.setVisible(true);
						menu.setUser_email(user_email);
						frmUser.setVisible(false);
					}

					else {
						registerUser("seller", false);
						return;
					}
				}

				else if (selectedUser == "Administrator") {
					registerUser("administrator", true);

					if (isUserType("administrator", "Admin", true)) {
						AdminMenu menu = new AdminMenu();
						menu.setVisible(true);
						menu.setUser_email(user_email);
						frmUser.setVisible(false);
					}

					else {
						return;
					}
				}

				else {
					if (isUserType("shipper", "Transporter", true)) {
						registerUser("shipper", true);
						ShipperMenu menu = new ShipperMenu();
						menu.setVisible(true);
						menu.setUser_email(user_email);
						frmUser.setVisible(false);
					}

					else {
						registerUser("shipper", false);
						return;
					}
				}
			}
		});

		btnSave.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\saveExitBTNicon.png"));
		frmUser.getContentPane().add(btnSave);

		txtDisplay = new JTextPane();
		txtDisplay.setText("Your current information:");
		txtDisplay.setBackground(new Color(255, 255, 255));
		txtDisplay.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		txtDisplay.setToolTipText("Your information should appear here.");
		txtDisplay.setEnabled(false);
		txtDisplay.setEditable(false);
		txtDisplay.setBounds(44, 242, 382, 93);
		frmUser.getContentPane().add(txtDisplay);
		btnSave.setBounds(326, 354, 100, 100);

		btnAddress = new JButton("");
		btnAddress.setToolTipText("Your Address");
		btnAddress.setBackground(new Color(255, 255, 255));
		btnAddress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddressBook book = new AddressBook();
				frmUser.setVisible(false);
				book.setVisible(true);
				book.setUser_email(user_email);
				book.updateDisplay();
			}
		});
		btnAddress.setBounds(44, 354, 100, 100);
		btnAddress.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\addressBTNicon.png"));
		frmUser.getContentPane().add(btnAddress);

		txtName = new JTextField();
		txtName.setToolTipText("Please enter your correct name.");
		txtName.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		txtName.setColumns(10);
		txtName.setBounds(241, 93, 185, 25);
		frmUser.getContentPane().add(txtName);

		txtSurname = new JTextField();
		txtSurname.setToolTipText("Please enter your correct surname.");
		txtSurname.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		txtSurname.setColumns(10);
		txtSurname.setBounds(162, 129, 264, 25);
		frmUser.getContentPane().add(txtSurname);

		txtContact = new JTextField();
		txtContact.setToolTipText("Please enter your correct contact number.");
		txtContact.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		txtContact.setBounds(186, 166, 240, 25);
		frmUser.getContentPane().add(txtContact);
		txtContact.setColumns(10);

		txtUserChange();

		lblBackGround = new JLabel("");
		lblBackGround.setBackground(new Color(0, 255, 255));
		lblBackGround.setHorizontalAlignment(SwingConstants.CENTER);
		lblBackGround.setBounds(0, 0, 488, 465);
		lblBackGround.setIcon(userChange);
		frmUser.getContentPane().add(lblBackGround);
	}

	void txtUserChange() {
		txtName.setBounds(201, 93, 225, 25);
		txtSurname.setBounds(186, 129, 240, 25);
		txtContact.setBounds(241, 166, 185, 25);
	}

	void txtRegisterShop() {
		txtName.setBounds(211, 93, 215, 25);
		txtSurname.setBounds(162, 129, 264, 25);
		txtContact.setBounds(186, 166, 240, 25);
	}

	void txtRegisterShipper() {
		txtName.setBounds(274, 93, 152, 25);
		txtSurname.setBounds(162, 129, 264, 25);
		txtContact.setBounds(186, 166, 240, 25);
	}

	void txtChangeShop() {
		txtName.setBounds(206, 93, 220, 25);
		txtSurname.setBounds(162, 129, 264, 25);
		txtContact.setBounds(186, 166, 240, 25);
	}

	void txtChangeShipper() {
		txtName.setBounds(267, 93, 160, 25);
		txtSurname.setBounds(162, 129, 264, 25);
		txtContact.setBounds(186, 166, 240, 25);
	}

	public void setVisible(boolean visible) {
		frmUser.setVisible(visible);
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	private String getValidValue(String value, String defaultValue) {
		return value.isEmpty() ? defaultValue : value;
	}

	public void updateDisplay() {
		try {
			// Establish connection
			Connection connect = getConnection();

			// Write query as string
			String query = "SELECT * FROM user_t WHERE email_address = ?";

			PreparedStatement statement = connect.prepareStatement(query);
			statement.setString(1, user_email);

			// Execute the query
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				String email = resultSet.getString("email_address");
				String name = resultSet.getString("first_name");
				String surname = resultSet.getString("last_name");
				String contact = resultSet.getString("contact_number");
				txtDisplay.setText("Your Information:\n" + "Email Address: " + email + "\nFirst Name: " + name
						+ "\nSurname: " + surname + "\nContact Number: " + contact);
				this.name = name;
				this.surname = surname;
				this.contact = contact;
			}

			// Close the connection
			connect.close();
		}

		catch (Exception e) {
			System.out.println("Something went wrong while trying to get user's information.");
			e.printStackTrace();
		}
	}

	boolean isUserType(String table, String customType, boolean hasDisplay) {
		try {
			Connection connect = getConnection();
			String query = "SELECT COUNT(*) FROM " + table + " WHERE  " + table + "_id = ?";
			PreparedStatement statement = connect.prepareStatement(query);
			statement.setString(1, user_email);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();

			// user is registered
			if (resultSet.getInt(1) > 0) {
				if (hasDisplay) {
					if (customType != "Customer")
						JOptionPane.showMessageDialog(null, "Welcome " + customType + ", " + name + "!");
					else
						JOptionPane.showMessageDialog(null,
								"Welcome, " + name + "! We hope you find what you looking for.");
				}

				return true;
			}

			// user is not registered
			else {
				if (hasDisplay && (txtName.getText()).isEmpty()) {
					String conv;

					if (customType == "administrator") {
						conv = "an";
					}

					else {
						conv = "a";
					}

					JOptionPane.showMessageDialog(null,
							"You are not registered as " + conv + " " + table + ". Please register.");

				}

				return false;
			}
		}

		catch (Exception e) {
			System.out.println("Something went wrong in trying to enter the user.");
			e.printStackTrace();
		}

		return false;
	}

	void registerUser(String table, boolean isRegistered) {
		String name = txtName.getText();
		String surname = txtSurname.getText();
		String contact = txtContact.getText();

		if (table == "seller" || table == "shipper") {
			if (isRegistered) {
				if (!txtName.getText().isEmpty()) {
					updateData(table, name);
				}
			}

			else {
				insertData(table, name);
			}

			return;
		}

		String validName = getValidValue(name, this.name);
		String validSurname = getValidValue(surname, this.surname);
		String validContact = getValidValue(contact, this.contact);
		updateUserData(validName, validSurname, validContact);
	}

	void insertData(String table, String name) {
		String typeConv = "user";

		if (table == "seller") {
			typeConv = "merchant";
		}

		else {
			typeConv = "transporter";
		}

		try {
			Connection connect = getConnection();
			String query = "SELECT COUNT(*) FROM " + table + " WHERE " + table + "_id = ?";
			PreparedStatement statement = connect.prepareStatement(query);
			statement.setString(1, user_email);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();

			// user already exists, display dialog box message
			if (resultSet.getInt(1) > 0) {
				JOptionPane.showMessageDialog(null, "User already exists!");
				txtName.setText("");
				txtSurname.setText("");
				txtContact.setText("");
				return;
			}

			// user does not exist, add user in database
			else {
				String insertQuery = "INSERT INTO " + table + "(" + table + "_id, " + table + "_name) VALUES(?, ?)";
				PreparedStatement insertStatement = connect.prepareStatement(insertQuery);

				insertStatement.setString(1, user_email);
				insertStatement.setString(2, name);
				insertStatement.executeUpdate();
				
				if (!txtName.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Your registration as a " + typeConv + " was successful.\nRegistered as: " + name);
					txtName.setText("");
					txtSurname.setText("");
					txtContact.setText("");
				}
			}
		}

		catch (Exception e) {
			System.out.println("Something went wrong in trying to enter the user.");
			e.printStackTrace();
		}
	}

	void updateData(String table, String name) {
		String typeConv = "user";

		if (table == "seller") {
			typeConv = "shop";
		}

		else {
			typeConv = "transporter";
		}

		if (name != null)
			try {
				Connection connect = getConnection();
				String query = "SELECT COUNT(*) FROM " + table + " WHERE " + table + "_id = ?";
				PreparedStatement statement = connect.prepareStatement(query);
				statement.setString(1, user_email);
				ResultSet resultSet = statement.executeQuery();
				resultSet.next();

				// user already exists, update details
				if (resultSet.getInt(1) > 0) {
					String insertQuery = "UPDATE " + table + " SET " + table + "_name = ? WHERE " + table + "_id = ?";
					PreparedStatement updateStatement = connect.prepareStatement(insertQuery);

					updateStatement.setString(1, name);
					updateStatement.setString(2, user_email);
					updateStatement.executeUpdate();

					if (!txtName.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null,
								"You have successfully changed your " + typeConv + " name to " + name + ".");
						txtName.setText("");
						txtSurname.setText("");
						txtContact.setText("");
					}
				}

				// user does not exist
				else {
					JOptionPane.showMessageDialog(null, "User does not exist!");
					txtName.setText("");
					txtSurname.setText("");
					txtContact.setText("");
					return;
				}
			}

			catch (Exception e) {
				System.out.println("Something went wrong in trying to enter the user.");
				e.printStackTrace();
			}
	}

	boolean checkNullText() {
		return txtName.getText().isEmpty() && txtSurname.getText().isEmpty() && txtContact.getText().isEmpty();
	}

	void updateUserData(String name, String surname, String contact) {
		try {
			Connection connect = getConnection();
			String query = "SELECT COUNT(*) FROM user_t WHERE email_address = ?";
			PreparedStatement statement = connect.prepareStatement(query);
			statement.setString(1, user_email);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();

			// user already exists, update details
			if (resultSet.getInt(1) > 0) {
				String updateQuery = "UPDATE user_t SET contact_number = ?, first_name = ?, last_name = ? WHERE email_address = ?";
				PreparedStatement updateStatement = connect.prepareStatement(updateQuery);

				updateStatement.setString(1, contact);
				updateStatement.setString(2, name);
				updateStatement.setString(3, surname);
				updateStatement.setString(4, user_email);
				updateStatement.executeUpdate();

				if (!checkNullText()) {
					JOptionPane.showMessageDialog(null, "Your changes were saved! Your current information is:"
							+ "\nName: " + name + "\nSurname: " + surname + "\nContact Number: " + contact);
				}

				this.name = name;
				this.surname = surname;
				this.contact = contact;
			}

			// user does not exist
			else {
				JOptionPane.showMessageDialog(null, "User does not exist!");
				txtName.setText("");
				txtSurname.setText("");
				txtContact.setText("");
				return;
			}
		}

		catch (Exception e) {
			System.out.println("Something went wrong in trying to update the user.");
			e.printStackTrace();
		}
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

	void setUserType(String userType) {
		this.userType = userType;
	}

	String getUserType() {
		return this.userType;
	}
}
