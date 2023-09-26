package za.ac.ukzn.dbinterface;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

public class AddressBook {

	private JFrame frmAddressBook;
	private JLabel lblBackGround;
	private JTextField txtStreet;
	private JTextField txtSuburb;
	private JTextField txtCity;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbProvince;
	private JTextField txtCode;
	private JButton btnSave;
	private JTextPane txtDisplay;

	private String user_email;
	private String street;
	private String suburb;
	private String city;
	private String province;
	private String code;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddressBook window = new AddressBook();
					window.frmAddressBook.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AddressBook() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		frmAddressBook = new JFrame();
		frmAddressBook.setTitle("User Address Book");
		frmAddressBook.setBounds(100, 100, 500, 500);
		frmAddressBook.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAddressBook.getContentPane().setLayout(null);
		frmAddressBook.setLocationRelativeTo(null);
		frmAddressBook.getContentPane().setLayout(null);

		btnSave = new JButton("");
		btnSave.setToolTipText("Save Changes (Back to Main Menu)");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertUpdateAddress();
				User user = new User();
				frmAddressBook.setVisible(false);
				user.setVisible(true);
				user.setUser_email(user_email);
				user.updateDisplay();
			}
		});

		txtDisplay = new JTextPane();
		txtDisplay.setToolTipText("Your information should appear here.");
		txtDisplay.setText("Current Address:");
		txtDisplay.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		txtDisplay.setEnabled(false);
		txtDisplay.setEditable(false);
		txtDisplay.setBackground(Color.WHITE);
		txtDisplay.setBounds(40, 285, 283, 158);
		frmAddressBook.getContentPane().add(txtDisplay);

		btnSave.setBackground(Color.WHITE);
		btnSave.setBounds(340, 343, 100, 100);
		btnSave.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\saveExitBTNicon.png"));
		frmAddressBook.getContentPane().add(btnSave);

		txtCode = new JTextField();
		txtCode.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		txtCode.setColumns(10);
		txtCode.setBounds(143, 238, 297, 25);
		frmAddressBook.getContentPane().add(txtCode);

		cmbProvince = new JComboBox();
		cmbProvince.setBackground(new Color(255, 255, 255));
		cmbProvince.setModel(new DefaultComboBoxModel(new String[] { "Select Province", "Eastern Cape", "Free State",
				"Gauteng", "KwaZulu-Natal", "Limpopo", "Mpumalanga", "Northern Cape", "North West", "Western Cape" }));
		cmbProvince.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		cmbProvince.setBounds(122, 202, 318, 25);
		frmAddressBook.getContentPane().add(cmbProvince);

		txtCity = new JTextField();
		txtCity.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		txtCity.setColumns(10);
		txtCity.setBounds(151, 166, 289, 25);
		frmAddressBook.getContentPane().add(txtCity);

		txtSuburb = new JTextField();
		txtSuburb.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		txtSuburb.setColumns(10);
		txtSuburb.setBounds(109, 130, 331, 25);
		frmAddressBook.getContentPane().add(txtSuburb);

		txtStreet = new JTextField();
		txtStreet.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		txtStreet.setColumns(10);
		txtStreet.setBounds(169, 94, 270, 25);
		frmAddressBook.getContentPane().add(txtStreet);

		lblBackGround = new JLabel("");
		lblBackGround.setHorizontalAlignment(SwingConstants.CENTER);
		lblBackGround.setBounds(0, 0, 488, 465);
		lblBackGround.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\addressPageIcon.png"));
		frmAddressBook.getContentPane().add(lblBackGround);
	}

	public void setVisible(boolean visible) {
		frmAddressBook.setVisible(visible);
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

	boolean hasAddressBook() {
		try {
			Connection connect = getConnection();
			String query = "SELECT COUNT(*) FROM address_book WHERE user_id = ?";
			PreparedStatement statement = connect.prepareStatement(query);
			statement.setString(1, user_email);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();

			// user is an administrator
			if (resultSet.getInt(1) > 0) {
				return true;
			}

			// user is not an administrator
			else {
				return false;
			}
		}

		catch (Exception e) {
			System.out.println("Something went wrong in trying to find the user.");
			e.printStackTrace();
		}

		return false;
	}

	private String getProvince(String province, boolean isInsert) {
		if (isInsert) {
			if (province == "Select Province") {
				return this.province;
			}

			else {
				return province;
			}
		}

		if (province == "Select Province") {
			JOptionPane.showMessageDialog(null, "Please select a province");
			return "Province unspecified.";
		}

		else {
			return province;
		}
	}

	private boolean checkNullText() {
		return (txtStreet.getText()).isEmpty() && (txtSuburb.getText()).isEmpty() && (txtCity.getText()).isEmpty()
				&& ((String)cmbProvince.getSelectedItem() == "Select Province") && (txtCode.getText()).isEmpty();
	}

	public void insertUpdateAddress() {
		String street = txtStreet.getText();
		String suburb = txtSuburb.getText();
		String city = txtCity.getText();
		String province = (String) cmbProvince.getSelectedItem();
		String code = txtCode.getText();

		String validStreet = getValidValue(street, this.street);
		String validSuburb = getValidValue(suburb, this.suburb);
		String validCity = getValidValue(city, this.city);
		String validProvince = getValidValue(province, this.province);
		String validCode = getValidValue(code, this.code);

		if (!checkNullText())
			try {
				Connection connect = getConnection();
				// user already exists, update details
				if (hasAddressBook()) {
					String updateQuery = "UPDATE address_book " + "SET street_address = ?, "
							+ "suburb = ?, " + "city_town = ?, " + "province = ?, " + "postal_code = ? "
							+ "WHERE user_id = ?";
					PreparedStatement updateStatement = connect.prepareStatement(updateQuery);
					
					updateStatement.setString(1, validStreet);
					updateStatement.setString(2, validSuburb);
					updateStatement.setString(3, validCity);
					updateStatement.setString(4, getProvince(validProvince, true));
					updateStatement.setString(5, validCode);
					updateStatement.setString(6, user_email);
					updateStatement.executeUpdate();

					JOptionPane.showMessageDialog(null,
							"You have successfully updated your Address Book. "
									+ "Your current information is as follows:" + "\nStreet Address: " + validStreet
									+ "\nSuburb: " + validSuburb + "\nCity or Town: " + validCity + "\nProvince: "
									+ validProvince + "\nPostal Code: " + validCode);
				}

				else {
					String insertQuery = "INSERT INTO address_book (street_address, suburb, city_town, province, postal_code, user_id) "
							+ " VALUES (?, ?, ?, ?, ?, ?)";
					PreparedStatement insertStatement = connect.prepareStatement(insertQuery);

					insertStatement.setString(1, street);
					insertStatement.setString(2, suburb);
					insertStatement.setString(3, city);
					insertStatement.setString(4, getProvince(province, false));
					insertStatement.setString(5, code);
					insertStatement.setString(6, user_email);
					insertStatement.executeUpdate();

					JOptionPane.showMessageDialog(null,
							"You have successfully created an Address Book. "
									+ "Your current information is as follows:" + "\nStreet Address: " + street
									+ "\nSuburb: " + suburb + "\nCity or Town: " + city + "\nProvince: "
									+ getProvince(province, false) + "\nPostal Code: " + code);
				}
			}

			catch (Exception e) {
				System.out.println("Something went wrong in trying to update the user.");
				e.printStackTrace();
			}
		
		else {
			return;
		}
	}

	public void updateDisplay() {
		if (hasAddressBook()) {
			try {
				// Establish connection
				Connection connect = getConnection();

				// Write query as string
				String query = "SELECT * FROM address_book WHERE user_id = ?";

				PreparedStatement statement = connect.prepareStatement(query);
				statement.setString(1, user_email);

				// Execute the query
				ResultSet resultSet = statement.executeQuery();

				while (resultSet.next()) 
				{
					String street = resultSet.getString("street_address");
					String suburb = resultSet.getString("suburb");
					String city = resultSet.getString("city_town");
					String province = resultSet.getString("province");
					String code = resultSet.getString("postal_code");
					this.street = street;
					this.suburb = suburb;
					this.city = city;
					this.province = province;
					this.code = code;
					txtDisplay.setText("Current address:" + "\nStreet Address: " + street + "\nSuburb: " + suburb
							+ "\nCity or Town: " + city + "\nProvince: " + province + "\nPostal Code: " + code);
				}

				// Close the connection
				connect.close();
			}

			catch (Exception e) {
				System.out.println("Something went wrong while trying to get user's information.");
				e.printStackTrace();
			}
		}

		else {
			txtDisplay.setText("Currently we do not have your address. Please provide your address details.");
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
}
