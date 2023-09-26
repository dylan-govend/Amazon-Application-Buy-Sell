package za.ac.ukzn.dbinterface;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.border.MatteBorder;

public class ProductWindow {

	private JFrame frmProduct;
	private JLabel lblProductPicture;
	private JTextPane tpProduct;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductWindow window = new ProductWindow();
					window.frmProduct.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ProductWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmProduct = new JFrame();
		frmProduct.setTitle("Product Info");
		frmProduct.setBounds(100, 100, 700, 500);
		frmProduct.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmProduct.setLocationRelativeTo(null);
		frmProduct.getContentPane().setLayout(null);

		tpProduct = new JTextPane();
		tpProduct.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tpProduct.setFont(new Font("Arial Narrow", Font.BOLD, 20));
		tpProduct.setEditable(false);
		tpProduct.setBounds(96, 75, 285, 364);
		frmProduct.getContentPane().add(tpProduct);

		lblProductPicture = new JLabel("");
		lblProductPicture.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		lblProductPicture.setHorizontalAlignment(SwingConstants.CENTER);
		lblProductPicture.setBounds(391, 75, 270, 182);
		frmProduct.getContentPane().add(lblProductPicture);

		JTextPane tpSeller = new JTextPane();
		tpSeller.setBounds(391, 268, 270, 171);
		frmProduct.getContentPane().add(tpSeller);

		JButton btnExit = new JButton("");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmProduct.dispose();
			}
		});
		btnExit.setToolTipText("Exit Button");
		btnExit.setForeground(Color.WHITE);
		btnExit.setBackground(Color.WHITE);
		btnExit.setBounds(11, 364, 75, 75);
		btnExit.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\ExitBTNicon.png"));
		frmProduct.getContentPane().add(btnExit);

		JLabel lblBG = new JLabel("");
		lblBG.setHorizontalAlignment(SwingConstants.CENTER);
		lblBG.setBounds(0, 0, 688, 465);
		lblBG.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\ProductInformationPage.png"));
		frmProduct.getContentPane().add(lblBG);
		DisplayProduct(1);
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

	public void setVisible(boolean visible) {
		frmProduct.setVisible(visible);
	}

	public void DisplayProduct(int code) {
		try {
			// Establish the connection
			Connection connect = getConnection();

			// Execute the select statement
			String query = "SELECT * FROM product WHERE serial_code = ?";
			PreparedStatement statement = connect.prepareStatement(query);
			statement.setInt(1, code);
			ResultSet rs = statement.executeQuery();

			// Iterate over the result set and populate the table
			while (rs.next()) {
				int serial_code = rs.getInt("serial_code");
				String title = rs.getString("title");
				Double price = rs.getDouble("price");
				String desc = rs.getString("product_description");
				int quantity = rs.getInt("quantity");
				Blob blob = rs.getBlob("picture");
				String seller_id = rs.getString("seller_id");
					
				tpProduct.setText("Product Information: \nTitle: " + title + "\nPrice: R" + price
						+ "\nDescription: " + desc + "\nStock: " + quantity + "\nSerial Code: " + serial_code);
				
				byte[] imageData = blob.getBytes(1, (int) blob.length());
				Image image = Toolkit.getDefaultToolkit().createImage(imageData);
				ImageIcon icon = new ImageIcon(image);
				lblProductPicture.setIcon(icon);
		
				DisplaySeller(seller_id);
			}

			connect.close();

		} catch (Exception e) {
			System.out.println("Something went wrong in trying to get product info.");
			e.printStackTrace();
		}
	}
	
	void DisplaySeller(String id)
	{
		try {
			Connection connect = getConnection();
			String query = "SELECT * FROM seller WHERE seller_id = ?";
			PreparedStatement statement = connect.prepareStatement(query);
			statement.setString(1, id);
			ResultSet rsSeller = statement.executeQuery();

			while (rsSeller.next()) {
				String sellerName = rsSeller.getString("seller_name");
				float rating = rsSeller.getFloat("seller_rating");
				
				String text = tpProduct.getText();
				tpProduct.setText(text + "\n\nSeller's Information: \nSeller's Name: " + sellerName + "\nSeller's Rating: " + rating);
			}
			
			connect.close();
		} catch (Exception e) {
			System.out.println("Something went wrong in trying to get seller info.");
			e.printStackTrace();
		}
	}
	
	void DisplayReviews(int code)
	{
		
	}
}
