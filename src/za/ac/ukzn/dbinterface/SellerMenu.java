package za.ac.ukzn.dbinterface;

import java.awt.EventQueue;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.SystemColor;

public class SellerMenu {

	private JFrame frmSellerMenu;
	private JTextField txtSearch;
	private JPanel pnlAddProduct;
	private JPanel pnlRemoveProduct;
	private JTextField txtTitle;
	private JTextField txtPrice;
	private JTextField txtDesc;
	private JTextField txtQuantity;
	private JTextField txtWeight;
	private JLabel lblAddBG;
	private JLabel lblProductImage;
	private JScrollPane scrollPane;
	private JPanel pnlReview;

	@SuppressWarnings("rawtypes")
	private JComboBox cmbSize;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbType;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbMulti;
	@SuppressWarnings("rawtypes")
	private JComboBox cmbColour;

	private String user_email = "seller@bamazon.com";
	private String productType;
	private File selectedFile;
	private JTable tblProduct;
	private JLabel lblRemove;
	private JButton btnView;
	private JTable tblReview;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SellerMenu window = new SellerMenu();
					window.frmSellerMenu.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SellerMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		frmSellerMenu = new JFrame();
		frmSellerMenu.setTitle("Seller Menu");
		frmSellerMenu.setBounds(100, 100, 700, 500);
		frmSellerMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSellerMenu.setLocationRelativeTo(null);
		frmSellerMenu.getContentPane().setLayout(null);

		ImageIcon addPhysicalBG = new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\addPhysicalBG.png");
		ImageIcon addDigitalBG = new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\addDigitalBG.png");

		pnlReview = new JPanel();
		pnlReview.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		pnlReview.setBackground(new Color(246, 246, 246));
		pnlReview.setBounds(94, 60, 584, 394);
		frmSellerMenu.getContentPane().add(pnlReview);
		pnlReview.setLayout(null);

		JScrollPane scrollPaneReview = new JScrollPane();
		scrollPaneReview.setBounds(27, 60, 530, 265);
		pnlReview.add(scrollPaneReview);

		tblReview = new JTable();
		scrollPaneReview.setViewportView(tblReview);
		tblReview.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		tblReview.setFillsViewportHeight(true);
		tblReview.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));

		JButton btnInfo = new JButton("");
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnInfo.setBounds(502, 328, 55, 55);
		btnInfo.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\ViewBTNicon.png"));
		pnlReview.add(btnInfo);

		JLabel lblReview = new JLabel("");
		lblReview.setBounds(0, 0, 584, 394);
		lblReview.setBackground(SystemColor.menu);
		pnlReview.add(lblReview);

		pnlRemoveProduct = new JPanel();
		pnlRemoveProduct.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		pnlRemoveProduct.setBackground(new Color(246, 246, 246));
		pnlRemoveProduct.setBounds(94, 60, 584, 394);
		frmSellerMenu.getContentPane().add(pnlRemoveProduct);
		pnlRemoveProduct.setLayout(null);

		btnView = new JButton("");
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tblProduct.getSelectedRow();
				if (selectedRow != -1) {
					DefaultTableModel tableModel = (DefaultTableModel) tblProduct.getModel();
					int code = (int) tableModel.getValueAt(selectedRow, 0);
					ProductWindow product = new ProductWindow();
					product.setVisible(true);
					product.DisplayProduct(code);
				}
			}
		});
		btnView.setBounds(441, 328, 55, 55);
		btnView.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\ViewBTNicon.png"));
		pnlRemoveProduct.add(btnView);

		tblProduct = new JTable();
		tblProduct.setFillsViewportHeight(true);
		tblProduct.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		tblProduct.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		tblProduct.setBounds(45, 314, 485, 2);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 60, 530, 265);
		scrollPane.setViewportView(tblProduct);
		pnlRemoveProduct.add(scrollPane);

		txtSearch = new JTextField();
		txtSearch.setBounds(27, 19, 280, 30);
		txtSearch.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		txtSearch.setColumns(10);
		pnlRemoveProduct.add(txtSearch);

		JButton btnSearch = new JButton("");
		btnSearch.setBounds(317, 19, 30, 30);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchProduct(txtSearch.getText());
			}
		});
		btnSearch.setToolTipText("Search");
		btnSearch.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\SearchBTNimage.png"));
		pnlRemoveProduct.add(btnSearch);

		JButton btnRemove = new JButton("");
		btnRemove.setBounds(502, 328, 55, 55);
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tblProduct.getSelectedRow();
				if (selectedRow != -1) {
					int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this product?",
							"Confirm Delete", JOptionPane.YES_NO_OPTION);
					if (option == JOptionPane.YES_OPTION) {
						deleteEntry(selectedRow);
						JOptionPane.showMessageDialog(null, "Product successfully removed.");
					}

					else {
						JOptionPane.showMessageDialog(null, "Product was not removed.");
					}
				}
			}
		});
		btnRemove.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\RemoveBTNicon.png"));
		pnlRemoveProduct.add(btnRemove);

		lblRemove = new JLabel("");
		lblRemove.setBackground(SystemColor.menu);
		lblRemove.setBounds(0, 0, 584, 394);
		lblRemove.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\RemoveProductPage.png"));
		pnlRemoveProduct.add(lblRemove);

		DefaultTableModel tableModel = new DefaultTableModel();
		tblProduct.setModel(tableModel);

		pnlAddProduct = new JPanel();
		pnlAddProduct.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		pnlAddProduct.setBackground(new Color(246, 246, 246));
		pnlAddProduct.setBounds(94, 60, 584, 394);
		frmSellerMenu.getContentPane().add(pnlAddProduct);
		pnlAddProduct.setLayout(null);

		JButton btnSaveProduct = new JButton("");
		btnSaveProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddProduct();
				clearTextFields(pnlAddProduct);
				clearComboBoxes(pnlAddProduct);
				lblProductImage.setIcon(new ImageIcon(
						"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\defaultImageIcon.png"));
			}
		});
		btnSaveProduct.setToolTipText("Add this product to your shop.");
		btnSaveProduct.setForeground(Color.WHITE);
		btnSaveProduct.setBackground(Color.WHITE);
		btnSaveProduct.setBounds(471, 262, 75, 75);
		btnSaveProduct.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\addProductImageIcon.png"));
		pnlAddProduct.add(btnSaveProduct);

		JButton btnAddImage = new JButton("");
		btnAddImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Select Image");
				fileChooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg", "png"));

				int option = fileChooser.showOpenDialog(null);
				if (option == JFileChooser.APPROVE_OPTION) {
					selectedFile = fileChooser.getSelectedFile();
				}
				lblProductImage.setIcon(new ImageIcon(selectedFile.getAbsolutePath()));
			}
		});
		btnAddImage.setToolTipText("Add a product image.");
		btnAddImage.setForeground(Color.WHITE);
		btnAddImage.setBackground(Color.WHITE);
		btnAddImage.setBounds(346, 262, 75, 75);
		btnAddImage.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\addImageIcon.png"));
		pnlAddProduct.add(btnAddImage);

		lblProductImage = new JLabel("");
		lblProductImage.setToolTipText("Product Image");
		lblProductImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblProductImage.setBounds(346, 76, 200, 139);
		lblProductImage.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\defaultImageIcon.png"));
		pnlAddProduct.add(lblProductImage);

		cmbColour = new JComboBox();
		cmbColour.setToolTipText("What colour is this product?");
		cmbColour.setModel(new DefaultComboBoxModel(new String[] { "N/A", "Black", "Blue", "Brown", "Gray", "Green",
				"Orange", "Pink", "Purple", "Red", "Yellow" }));
		cmbColour.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		cmbColour.setBackground(new Color(255, 255, 255));
		cmbColour.setBounds(136, 315, 185, 22);
		pnlAddProduct.add(cmbColour);

		txtWeight = new JTextField();
		txtWeight.setToolTipText("How much does your product weigh?");
		txtWeight.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		txtWeight.setColumns(10);
		txtWeight.setBounds(137, 285, 185, 20);
		pnlAddProduct.add(txtWeight);

		cmbMulti = new JComboBox();
		cmbMulti.setToolTipText("Is it very big or very small?");
		cmbMulti.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3" }));
		cmbMulti.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		cmbMulti.setBackground(new Color(255, 255, 255));
		cmbMulti.setBounds(181, 255, 141, 22);
		pnlAddProduct.add(cmbMulti);

		cmbSize = new JComboBox();
		cmbSize.setToolTipText("How big is this product, small, medium, or large?");
		cmbSize.setModel(new DefaultComboBoxModel(new String[] { "N/A", "S", "M", "L" }));
		cmbSize.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		cmbSize.setBackground(new Color(255, 255, 255));
		cmbSize.setBounds(122, 225, 200, 22);
		pnlAddProduct.add(cmbSize);

		Component[] physicalComponents = { txtWeight, cmbColour };
		setComponentsEnabledVisible(physicalComponents, false);

		cmbType = new JComboBox();
		cmbType.setToolTipText("The type of product this is.");
		cmbType.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		cmbType.setBackground(new Color(255, 255, 255));
		cmbType.setModel(new DefaultComboBoxModel(new String[] { "Physical Product", "Digital Product" }));
		cmbType.addItemListener((ItemListener) new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String selectedType = (String) e.getItem();

					if (selectedType == "Physical Product") {
						setComponentsEnabledVisible(physicalComponents, true);
						lblAddBG.setIcon(addPhysicalBG);
						cmbSize.setBounds(122, 225, 200, 22);
						cmbMulti.setBounds(181, 255, 141, 22);
						revertCMBSize(cmbSize);
						revertCMBMulti(cmbMulti);
						setProductType(selectedType);
					}

					else {
						setComponentsEnabledVisible(physicalComponents, false);
						lblAddBG.setIcon(addDigitalBG);
						cmbSize.setBounds(147, 225, 175, 22);
						cmbMulti.setBounds(147, 255, 175, 22);
						changeCMBSize(cmbSize);
						changeCMBMulti(cmbMulti);
						setProductType(selectedType);
					}
				}
			}
		});
		cmbType.setBounds(122, 195, 200, 22);
		pnlAddProduct.add(cmbType);

		txtQuantity = new JTextField();
		txtQuantity.setToolTipText("How many of this product do you currently have?");
		txtQuantity.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		txtQuantity.setColumns(10);
		txtQuantity.setBounds(147, 166, 175, 20);
		pnlAddProduct.add(txtQuantity);

		txtDesc = new JTextField();
		txtDesc.setToolTipText("Describe your product.");
		txtDesc.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		txtDesc.setColumns(10);
		txtDesc.setBounds(162, 136, 160, 20);
		pnlAddProduct.add(txtDesc);

		txtPrice = new JTextField();
		txtPrice.setToolTipText("How much does it cost?");
		txtPrice.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		txtPrice.setColumns(10);
		txtPrice.setBounds(127, 106, 195, 20);
		pnlAddProduct.add(txtPrice);

		txtTitle = new JTextField();
		txtTitle.setToolTipText("What product is this?");
		txtTitle.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		txtTitle.setBounds(122, 76, 200, 20);
		pnlAddProduct.add(txtTitle);
		txtTitle.setColumns(10);

		lblAddBG = new JLabel("");
		lblAddBG.setBackground(new Color(240, 240, 240));
		lblAddBG.setBounds(0, 0, 584, 394);
		lblAddBG.setIcon(addPhysicalBG);
		pnlAddProduct.add(lblAddBG);

		JLabel lblLogo = new JLabel("");
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setBounds(264, 11, 174, 43);
		lblLogo.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\BAMAZON.png"));
		frmSellerMenu.getContentPane().add(lblLogo);

		JButton btnReview = new JButton("");
		btnReview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnlAddProduct.setEnabled(false);
				pnlAddProduct.setVisible(false);
				setPanelActivation(pnlAddProduct, false);

				pnlRemoveProduct.setEnabled(false);
				pnlRemoveProduct.setVisible(false);
				setPanelActivation(pnlRemoveProduct, false);

				pnlReview.setEnabled(true);
				pnlReview.setVisible(true);
				setPanelActivation(pnlReview, true);
			}
		});
		btnReview.setToolTipText("Your Product Review's and Rating's");
		btnReview.setForeground(Color.WHITE);
		btnReview.setBackground(Color.WHITE);
		btnReview.setBounds(10, 140, 75, 75);
		btnReview.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\ReviewIcon.png"));
		frmSellerMenu.getContentPane().add(btnReview);

		populateTable();
		JButton btnRemoveProduct = new JButton("");
		btnRemoveProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cmbType.setSelectedIndex(0);
				pnlAddProduct.setEnabled(false);
				pnlAddProduct.setVisible(false);
				setPanelActivation(pnlAddProduct, false);

				pnlRemoveProduct.setEnabled(true);
				pnlRemoveProduct.setVisible(true);
				setPanelActivation(pnlRemoveProduct, true);

				pnlReview.setEnabled(false);
				pnlReview.setVisible(false);
				setPanelActivation(pnlReview, false);
			}
		});
		btnRemoveProduct.setToolTipText("Remove Product (Remove Product From Sale)");
		btnRemoveProduct.setForeground(Color.WHITE);
		btnRemoveProduct.setBackground(Color.WHITE);
		btnRemoveProduct.setBounds(10, 220, 75, 75);
		btnRemoveProduct.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\removeProBTNicon.png"));
		frmSellerMenu.getContentPane().add(btnRemoveProduct);

		JButton btnAddProduct = new JButton("");
		btnAddProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pnlAddProduct.setEnabled(true);
				pnlAddProduct.setVisible(true);
				setPanelActivation(pnlAddProduct, true);

				pnlRemoveProduct.setEnabled(false);
				pnlRemoveProduct.setVisible(false);
				setPanelActivation(pnlRemoveProduct, false);

				pnlReview.setEnabled(false);
				pnlReview.setVisible(false);
				setPanelActivation(pnlReview, false);
			}
		});
		btnAddProduct.setToolTipText("Add Product (Add Product For Sale)");
		btnAddProduct.setForeground(Color.WHITE);
		btnAddProduct.setBackground(Color.WHITE);
		btnAddProduct.setBounds(10, 300, 75, 75);
		btnAddProduct.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\addProBTNIcon.png"));
		frmSellerMenu.getContentPane().add(btnAddProduct);

		JButton btnUser = new JButton("");
		btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User user = new User();
				frmSellerMenu.setVisible(false);
				user.setVisible(true);
				user.setUser_email(user_email);
				user.updateDisplay();
			}
		});
		btnUser.setToolTipText("Your Information");
		btnUser.setForeground(Color.WHITE);
		btnUser.setBackground(Color.WHITE);
		btnUser.setBounds(10, 380, 75, 75);
		btnUser.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\userBTNicon.png"));
		frmSellerMenu.getContentPane().add(btnUser);

		initializePanels();
	}

	public void initializePanels() {
		pnlAddProduct.setEnabled(true);
		pnlAddProduct.setVisible(true);
		setPanelActivation(pnlAddProduct, true);

		pnlRemoveProduct.setEnabled(false);
		pnlRemoveProduct.setVisible(false);
		setPanelActivation(pnlRemoveProduct, false);

		pnlReview.setEnabled(false);
		pnlReview.setVisible(false);
		setPanelActivation(pnlReview, false);
	}

	public void setComponentsEnabledVisible(Component[] components, boolean flag) {
		for (Component component : components) {
			component.setEnabled(flag);
			component.setVisible(flag);
		}
	}

	public void setPanelActivation(JPanel panel, boolean flag) {
		Component[] panelComponents = panel.getComponents();
		for (Component component : panelComponents) {
			component.setEnabled(flag);
			component.setVisible(flag);
		}
		panel.revalidate();
		panel.repaint();
	}

	public void clearTextFields(JPanel panel) {
		Component[] components = panel.getComponents();
		for (Component component : components) {
			if (component instanceof JTextField) {
				JTextField textField = (JTextField) component;
				textField.setText("");
			}
		}
	}

	public void clearComboBoxes(JPanel panel) {
		Component[] components = panel.getComponents();
		for (Component component : components) {
			if (component instanceof JComboBox) {
				JComboBox<?> comboBox = (JComboBox<?>) component;
				comboBox.setSelectedIndex(0);
			}
		}
	}

	public void revertCMBSize(JComboBox<String> comboBox) {
		comboBox.removeAllItems();

		comboBox.addItem("N/A");
		comboBox.addItem("S");
		comboBox.addItem("M");
		comboBox.addItem("L");
		comboBox.setToolTipText("How big is this product, small, medium, or large?");
	}

	public void changeCMBSize(JComboBox<String> comboBox) {
		comboBox.removeAllItems();

		comboBox.addItem("Text File (.txt)");
		comboBox.addItem("Music File (.mp3)");
		comboBox.addItem("Video File (.mp4)");
		comboBox.addItem("Ebook (.pdf)");
		comboBox.addItem("Software Application (.exe)");
		comboBox.addItem("Game (.exe)");
		comboBox.addItem("Document (.docx)");
		comboBox.addItem("Image File (.jpg)");
		comboBox.addItem("Audio Book (.mp3)");
		comboBox.addItem("Movie (.mp4)");
		comboBox.addItem("Presentation (.pptx)");
		comboBox.setToolTipText("What type of file is this?");
	}

	public void revertCMBMulti(JComboBox<String> comboBox) {
		comboBox.removeAllItems();

		comboBox.addItem("1");
		comboBox.addItem("2");
		comboBox.addItem("3");
		comboBox.setToolTipText("Is it very big or very small?");
	}

	public void changeCMBMulti(JComboBox<String> comboBox) {
		comboBox.removeAllItems();

		comboBox.addItem("5.0");
		comboBox.addItem("10.0");
		comboBox.addItem("15.0");
		comboBox.addItem("20.0");
		comboBox.addItem("25.0");
		comboBox.addItem("30.0");
		comboBox.setToolTipText("How large is this file?");
	}

	public void setVisible(boolean visible) {
		frmSellerMenu.setVisible(visible);
	}

	boolean checkNullText() {
		return txtTitle.getText().isEmpty() || txtPrice.getText().isEmpty() || txtDesc.getText().isEmpty()
				|| txtQuantity.getText().isEmpty();
	}

	private void AddProduct() {
		String title = txtTitle.getText();
		String price = txtPrice.getText();
		String desc = txtDesc.getText();
		String quantity = txtQuantity.getText();
		String type = getProductType();

		String size = (String) cmbSize.getSelectedItem();
		String multi = (String) cmbMulti.getSelectedItem();
		String weight = txtWeight.getText();
		String colour = (String) cmbColour.getSelectedItem();
		FileInputStream fis = null;

		if (selectedFile == null) {
			selectedFile = new File(
					"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\defaultImageIcon.png");
		}

		try {
			fis = new FileInputStream(selectedFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		lblProductImage.setIcon(new ImageIcon(selectedFile.getAbsolutePath()));

		if (!checkNullText()) {
			try {
				Connection connect = getConnection();

				String insertQuery = "INSERT product(title, price, product_description, product_type, picture, quantity, seller_id) "
						+ "VALUES(?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement insertStatement = connect.prepareStatement(insertQuery,
						Statement.RETURN_GENERATED_KEYS);

				insertStatement.setString(1, title);
				insertStatement.setDouble(2, Double.parseDouble(price));
				insertStatement.setString(3, desc);
				insertStatement.setString(4, type);
				insertStatement.setBinaryStream(5, fis);
				insertStatement.setInt(6, Integer.parseInt(quantity));
				insertStatement.setString(7, user_email);
				insertStatement.executeUpdate();

				ResultSet generatedKeys = insertStatement.getGeneratedKeys();
				int serialCode = -1;
				if (generatedKeys.next()) {
					serialCode = generatedKeys.getInt(1);
					DefaultTableModel tableModel = (DefaultTableModel) tblProduct.getModel();
					Object[] rowData = { serialCode, title, Double.parseDouble(price), type,
							Integer.parseInt(quantity) };
					tableModel.addRow(rowData);
					tblProduct.setModel(tableModel);
					tblProduct.repaint();
				} else {
					JOptionPane.showMessageDialog(null, "Failed to retrieve generated serial code.");
				}

				generatedKeys.close();
				insertStatement.close();

				if (serialCode != -1) {
					if (type.equals("P")) {
						String insertPhysicalQuery = "INSERT INTO physical_product VALUES (?, ?, ?, ?, ?)";
						PreparedStatement insertPhysicalStatement = connect.prepareStatement(insertPhysicalQuery);
						insertPhysicalStatement.setInt(1, serialCode);
						insertPhysicalStatement.setString(2, size);
						insertPhysicalStatement.setInt(3, Integer.parseInt(multi));
						insertPhysicalStatement.setDouble(4, Double.parseDouble(weight));
						insertPhysicalStatement.setString(5, colour);
						insertPhysicalStatement.executeUpdate();
						insertPhysicalStatement.close();
					}

					else if (type.equals("D")) {
						String insertDigitalQuery = "INSERT INTO digital_product(digital_serial_code, file_type) VALUES (?, ?)";
						PreparedStatement insertDigitalStatement = connect.prepareStatement(insertDigitalQuery);
						insertDigitalStatement.setInt(1, serialCode);
						insertDigitalStatement.setString(2, size);
						insertDigitalStatement.executeUpdate();
						insertDigitalStatement.close();
					}

					JOptionPane.showMessageDialog(null, "The product was entered successfully.");
				}

				connect.close();
			}

			catch (Exception e) {
				System.out.println("Something went wrong in trying to insert the product.");
				e.printStackTrace();
			}
		}

		else {
			JOptionPane.showMessageDialog(null, "Please enter the product information correctly.");
		}
	}

	public void populateTable() {
		try {
			// Establish the connection
			Connection connect = getConnection();

			// Execute the select statement
			String query = "SELECT * FROM product WHERE seller_id = ?";
			PreparedStatement statement = connect.prepareStatement(query);
			statement.setString(1, user_email);
			ResultSet rs = statement.executeQuery();

			// Clear the existing table data
			DefaultTableModel tableModel = (DefaultTableModel) tblProduct.getModel();
			tableModel.addColumn("Serial Code");
			tableModel.addColumn("Title");
			tableModel.addColumn("Price");
			tableModel.addColumn("Type");
			tableModel.addColumn("Quantity");

			// Iterate over the result set and populate the table
			while (rs.next()) {
				int code = rs.getInt("serial_code");
				String title = rs.getString("title");
				Double price = rs.getDouble("price");
				String type = rs.getString("product_type");
				int quantity = rs.getInt("quantity");

				Object[] rowData = { code, title, price, type, quantity };
				tableModel.addRow(rowData);
			}

			tblProduct.setModel(tableModel);
			tblProduct.repaint();
			connect.close();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Something went wrong in trying to get data.");
			e.printStackTrace();
		}
	}

	private void deleteEntry(int row) {
		try {
			Connection connect = getConnection();

			DefaultTableModel tableModel = (DefaultTableModel) tblProduct.getModel();
			int id = (int) tableModel.getValueAt(row, 0);
			String type = (String) tableModel.getValueAt(row, 3);

			String query = "DELETE FROM product WHERE seller_id = ? AND serial_code = ?";
			PreparedStatement statement = connect.prepareStatement(query);
			statement.setString(1, user_email);
			statement.setInt(2, id);

			statement.executeUpdate();
			statement.close();

			if (type == "P") {
				String physical_query = "DELETE FROM physical_product WHERE physical_serial_code = ?";
				PreparedStatement physical_statement = connect.prepareStatement(physical_query);
				physical_statement.setInt(1, id);
				physical_statement.executeUpdate();
				physical_statement.close();
			}

			else {
				String digital_query = "DELETE FROM digital_product WHERE digital_serial_code = ?";
				PreparedStatement digital_statement = connect.prepareStatement(digital_query);
				digital_statement.setInt(1, id);
				digital_statement.executeUpdate();
				digital_statement.close();
			}

			tableModel.removeRow(row);
			connect.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void searchProduct(String search) {
		try {
			Connection connect = getConnection();

			String query = "SELECT * FROM product WHERE title LIKE ? AND seller_id = ?";
			PreparedStatement statement = connect.prepareStatement(query);
			statement.setString(1, "%" + search + "%");
			statement.setString(2, user_email);
			ResultSet rs = statement.executeQuery();
			
			DefaultTableModel tableModel = (DefaultTableModel) tblProduct.getModel();
			tableModel.setRowCount(0);

			while (rs.next()) {
				int code = rs.getInt("serial_code");
				String title = rs.getString("title");
				Double price = rs.getDouble("price");
				String type = rs.getString("product_type");
				int quantity = rs.getInt("quantity");

				Object[] rowData = { code, title, price, type, quantity };
				tableModel.addRow(rowData);
			}
			
			tblProduct.setModel(tableModel);
			tblProduct.repaint();
			connect.close();
		} catch (Exception e) {
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

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getProductType() {
		if (this.productType != "Digital Product")
			return "P";
		else
			return "D";
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
}
