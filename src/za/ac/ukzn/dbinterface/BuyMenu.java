package za.ac.ukzn.dbinterface;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class BuyMenu {

	private JFrame frmBuyMenu;
	private JTextField txtSearch;
	private JTable tblAllProducts;
	private JTable tblOrder;
	private JTable tblReturn;
	private JScrollPane spBuy;
	private JPanel pnlBuyMenu;
	private JPanel pnlCart;
	private JPanel pnlWish;
	private JPanel pnlOrder;
	private JPanel pnlReturn;
	private JPanel[] panels;
	private JTable tblCart;
	private JLabel txtTotalPrice;
	private JLabel txtTotalProduct;
	private JTable tblWish;

	private String user_email;
	private int cart_key = -1;
	private int wish_key = -1;
	private int numProducts = 0;
	private double totalPrice = 0;
	private int order_key = -1;
	private int return_key = -1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BuyMenu window = new BuyMenu();
					window.frmBuyMenu.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public BuyMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		DefaultTableModel tableModel = new DefaultTableModel();
		frmBuyMenu = new JFrame();
		frmBuyMenu.getContentPane().setBackground(new Color(246, 246, 246));
		frmBuyMenu.setTitle("BAMAZON Purchase");
		// setBounds(width, height)
		frmBuyMenu.setBounds(100, 100, 700, 500);
		frmBuyMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBuyMenu.setLocationRelativeTo(null);
		frmBuyMenu.getContentPane().setLayout(null);

		pnlReturn = new JPanel();
		pnlReturn.setLayout(null);
		pnlReturn.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		pnlReturn.setBackground(new Color(246, 246, 246));
		pnlReturn.setBounds(94, 61, 584, 394);
		frmBuyMenu.getContentPane().add(pnlReturn);

		tblReturn = new JTable();
		tblReturn.setBounds(20, 275, 479, -193);
		pnlReturn.add(tblReturn);

		JScrollPane spReturn = new JScrollPane();
		spReturn.setBounds(20, 60, 537, 257);
		pnlReturn.add(spReturn);

		JButton btnCancleReturn = new JButton("");
		btnCancleReturn.setToolTipText("Pay Order");
		btnCancleReturn.setBackground(Color.WHITE);
		btnCancleReturn.setBounds(437, 328, 55, 55);
		pnlReturn.add(btnCancleReturn);

		JButton btnReturnInfo = new JButton("");
		btnReturnInfo.setToolTipText("Order Info");
		btnReturnInfo.setBackground(Color.WHITE);
		btnReturnInfo.setBounds(502, 328, 55, 55);
		pnlReturn.add(btnReturnInfo);

		JLabel lblReturnLogo = new JLabel("");
		lblReturnLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblReturnLogo.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		lblReturnLogo.setBounds(186, 0, 184, 65);
		lblReturnLogo.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\ReturnPageLogo.png"));
		pnlReturn.add(lblReturnLogo);

		pnlOrder = new JPanel();
		pnlOrder.setLayout(null);
		pnlOrder.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		pnlOrder.setBackground(new Color(246, 246, 246));
		pnlOrder.setBounds(94, 61, 584, 394);
		frmBuyMenu.getContentPane().add(pnlOrder);

		tblOrder = new JTable();
		tblOrder.setFillsViewportHeight(true);
		tblOrder.setBounds(23, 353, 476, -271);
		tblOrder.setModel(tableModel);

		JScrollPane spOrder = new JScrollPane();
		spOrder.setBounds(23, 60, 537, 257);
		spOrder.setViewportView(tblOrder);
		pnlOrder.add(spOrder);

		JButton btnCancleOrder = new JButton("");
		btnCancleOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tblOrder.getSelectedRow();
				if (selectedRow != -1) {
					CancleOrder(selectedRow);
				}
			}
		});
		btnCancleOrder.setToolTipText("Cancle Order");
		btnCancleOrder.setBackground(Color.WHITE);
		btnCancleOrder.setBounds(375, 328, 55, 55);
		btnCancleOrder.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\CartRemoveAllBTN.png"));
		pnlOrder.add(btnCancleOrder);

		JButton btnPayOrder = new JButton("");
		btnPayOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tblOrder.getSelectedRow();
				if (selectedRow != -1) {
					PayOrder(selectedRow);
				}
			}
		});
		btnPayOrder.setToolTipText("Pay Order");
		btnPayOrder.setBackground(Color.WHITE);
		btnPayOrder.setBounds(440, 328, 55, 55);
		btnPayOrder.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\PayIcon.png"));
		pnlOrder.add(btnPayOrder);

		JButton btnOrderInfo = new JButton("");
		btnOrderInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tblOrder.getSelectedRow();
				if (selectedRow != -1) {
					PopulateOrderTableProduct(selectedRow);
				}
			}
		});
		btnOrderInfo.setBackground(new Color(255, 255, 255));
		btnOrderInfo.setToolTipText("Order Info");
		btnOrderInfo.setBounds(505, 328, 55, 55);
		btnOrderInfo.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\ViewBTNicon.png"));
		pnlOrder.add(btnOrderInfo);

		JLabel lblOrderLogo = new JLabel("");
		lblOrderLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblOrderLogo.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		lblOrderLogo.setBounds(189, 0, 184, 65);
		lblOrderLogo.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\OrderPageLogo.png"));
		pnlOrder.add(lblOrderLogo);

		JButton btnReturnOrder = new JButton("");
		btnReturnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnReturnOrder.setToolTipText("Return Order");
		btnReturnOrder.setBackground(Color.WHITE);
		btnReturnOrder.setBounds(310, 328, 55, 55);
		btnReturnOrder.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\ReturnOrderBTNicon.png"));
		pnlOrder.add(btnReturnOrder);

		pnlWish = new JPanel();
		pnlWish.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		pnlWish.setBackground(new Color(246, 246, 246));
		pnlWish.setBounds(94, 61, 584, 394);
		frmBuyMenu.getContentPane().add(pnlWish);
		pnlWish.setLayout(null);

		JScrollPane spWish = new JScrollPane();
		spWish.setBounds(23, 60, 537, 257);
		pnlWish.add(spWish);

		tblWish = new JTable();
		spWish.setViewportView(tblWish);
		tblWish.setFillsViewportHeight(true);
		tblWish.setModel(tableModel);

		JButton btnWishRemove = new JButton("");
		btnWishRemove.setToolTipText("Remove Product From WishList");
		btnWishRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tblWish.getSelectedRow();
				if (selectedRow != -1) {
					removeProductFromWish(selectedRow);
				}
			}
		});
		btnWishRemove.setBounds(375, 328, 55, 55);
		btnWishRemove.setBackground(Color.WHITE);
		btnWishRemove.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\CartRemoveAllBTN.png"));
		pnlWish.add(btnWishRemove);

		JButton btnWishAddToCart = new JButton("");
		btnWishAddToCart.setToolTipText("Add Product To Cart");
		btnWishAddToCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tblWish.getSelectedRow();
				if (selectedRow != -1) {
					insertProductInCart(selectedRow, tblWish);
				}
			}
		});
		btnWishAddToCart.setBounds(440, 328, 55, 55);
		btnWishAddToCart.setBackground(Color.WHITE);
		btnWishAddToCart.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\addProductToCartBTNicon.png"));
		pnlWish.add(btnWishAddToCart);

		JButton btnWishInfo = new JButton("");
		btnWishInfo.setToolTipText("Product Info");
		btnWishInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tblWish.getSelectedRow();
				if (selectedRow != -1) {
					DefaultTableModel tableModel = (DefaultTableModel) tblWish.getModel();
					int code = (int) tableModel.getValueAt(selectedRow, 0);
					ProductWindow product = new ProductWindow();
					product.setVisible(true);
					product.DisplayProduct(code);
				}
			}
		});
		btnWishInfo.setBounds(505, 328, 55, 55);
		btnWishInfo.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\ViewBTNicon.png"));
		pnlWish.add(btnWishInfo);

		JLabel lblWishListLogo = new JLabel("");
		lblWishListLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblWishListLogo.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		lblWishListLogo.setBounds(189, 0, 184, 65);
		lblWishListLogo.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\WishListLogo.png"));
		pnlWish.add(lblWishListLogo);

		pnlCart = new JPanel();
		pnlCart.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		pnlCart.setBackground(new Color(246, 246, 246));
		pnlCart.setBounds(94, 61, 584, 394);
		frmBuyMenu.getContentPane().add(pnlCart);
		pnlCart.setLayout(null);

		JLabel lblCartPageLogo = new JLabel("");
		lblCartPageLogo.setBounds(189, 0, 184, 65);
		lblCartPageLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblCartPageLogo.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		lblCartPageLogo.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\CartPageLabel.png"));
		pnlCart.add(lblCartPageLogo);

		JScrollPane spCart = new JScrollPane();
		spCart.setBounds(23, 63, 537, 257);
		pnlCart.add(spCart);

		tblCart = new JTable();
		tblCart.setFillsViewportHeight(true);
		spCart.setViewportView(tblCart);

		txtTotalProduct = new JLabel("Total Products:");
		txtTotalProduct.setBounds(23, 331, 184, 14);
		txtTotalProduct.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		pnlCart.add(txtTotalProduct);

		txtTotalPrice = new JLabel("Total Price:");
		txtTotalPrice.setBounds(23, 356, 184, 14);
		txtTotalPrice.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		pnlCart.add(txtTotalPrice);

		JButton btnCartInfo = new JButton("");
		btnCartInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tblCart.getSelectedRow();
				if (selectedRow != -1) {
					DefaultTableModel tableModel = (DefaultTableModel) tblCart.getModel();
					int code = (int) tableModel.getValueAt(selectedRow, 0);
					ProductWindow product = new ProductWindow();
					product.setVisible(true);
					product.DisplayProduct(code);
				}
			}
		});
		btnCartInfo.setBounds(505, 331, 55, 55);
		btnCartInfo.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\ViewBTNicon.png"));
		pnlCart.add(btnCartInfo);

		JButton btnCartOrder = new JButton("");
		btnCartOrder.setBackground(new Color(255, 255, 255));
		btnCartOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertProductInOrder();
			}
		});
		btnCartOrder.setBounds(440, 331, 55, 55);
		btnCartOrder.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\CartOrderBTN.png"));
		pnlCart.add(btnCartOrder);

		JButton btnCartRemoveAll = new JButton("");
		btnCartRemoveAll.setBackground(new Color(255, 255, 255));
		btnCartRemoveAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tblCart.getSelectedRow();
				if (selectedRow != -1) {
					removeProductFromCart(selectedRow);
				}
			}
		});
		btnCartRemoveAll.setBounds(375, 331, 55, 55);
		btnCartRemoveAll.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\CartRemoveAllBTN.png"));
		pnlCart.add(btnCartRemoveAll);

		JButton cartRemoveOne = new JButton("");
		cartRemoveOne.setBackground(new Color(255, 255, 255));
		cartRemoveOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tblCart.getSelectedRow();
				if (selectedRow != -1) {
					removeOneProductFromCart(selectedRow);
				}
			}
		});
		cartRemoveOne.setBounds(310, 331, 55, 55);
		cartRemoveOne.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\CartRemoveOneBTN.png"));
		pnlCart.add(cartRemoveOne);

		pnlBuyMenu = new JPanel();
		pnlBuyMenu.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		pnlBuyMenu.setBackground(new Color(246, 246, 246));
		pnlBuyMenu.setBounds(94, 61, 584, 394);
		frmBuyMenu.getContentPane().add(pnlBuyMenu);
		pnlBuyMenu.setLayout(null);

		txtSearch = new JTextField();
		txtSearch.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		txtSearch.setBounds(20, 15, 280, 30);
		pnlBuyMenu.add(txtSearch);
		txtSearch.setColumns(10);

		JButton btnSearch = new JButton("");
		btnSearch.setToolTipText("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchProduct(txtSearch.getText());
			}
		});
		btnSearch.setBounds(310, 15, 30, 30);
		btnSearch.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\SearchBTNimage.png"));
		pnlBuyMenu.add(btnSearch);
		spBuy = new JScrollPane();
		spBuy.setBounds(20, 60, 537, 257);
		pnlBuyMenu.add(spBuy);

		tblAllProducts = new JTable();
		tblAllProducts.setFillsViewportHeight(true);
		tblAllProducts.setModel(tableModel);
		spBuy.setViewportView(tblAllProducts);

		JComboBox cmbSort = new JComboBox();
		cmbSort.setModel(new DefaultComboBoxModel(
				new String[] { "Sort Product", "Alphabetical Order", "Rating (Lowest To Highest)",
						"Rating (Highest To Lowest)", "Price (Lowest To Highest)", "Price (Highest To Lowest)" }));
		cmbSort.setBackground(new Color(255, 255, 255));
		cmbSort.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		cmbSort.setBounds(385, 15, 172, 27);
		cmbSort.addItemListener((ItemListener) new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					/*
					 * Sort Product Alphabetical Order Rating (Lowest To Highest) Rating (Highest To
					 * Lowest) Price (Lowest To Highest) Price (Highest To Lowest)
					 */
					String sort = (String) e.getItem();

					if (sort == "Alphabetical Order") {
						populateSortedProductTable("SELECT * FROM product ORDER BY title ASC");
					}

					else if (sort == "Rating (Lowest To Highest)") {
						populateSortedProductTable("SELECT * FROM product ORDER BY price DESC");
					}

					else if (sort == "Rating (Highest To Lowest)") {
						populateSortedProductTable("SELECT * FROM product ORDER BY price DESC");
					}

					else if (sort == "Price (Lowest To Highest)") {
						populateSortedProductTable("SELECT * FROM product ORDER BY price ASC");
					}

					else if (sort == "Price (Highest To Lowest)") {
						populateSortedProductTable("SELECT * FROM product ORDER BY price DESC");
					}

					else {
						populateProductTable();
					}
				}
			}
		});
		pnlBuyMenu.add(cmbSort);

		JButton btnInfo = new JButton("");
		btnInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tblAllProducts.getSelectedRow();
				if (selectedRow != -1) {
					DefaultTableModel tableModel = (DefaultTableModel) tblAllProducts.getModel();
					int code = (int) tableModel.getValueAt(selectedRow, 0);
					ProductWindow product = new ProductWindow();
					product.setVisible(true);
					product.DisplayProduct(code);
				}
			}
		});
		btnInfo.setBounds(500, 328, 55, 55);
		btnInfo.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\ViewBTNicon.png"));
		pnlBuyMenu.add(btnInfo);

		JButton btnAddWish = new JButton("");
		btnAddWish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tblAllProducts.getSelectedRow();
				if (selectedRow != -1) {
					insertProductInWishList(selectedRow);
				}
			}
		});
		btnAddWish.setBounds(435, 328, 55, 55);
		btnAddWish.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\addProductToWishBTNicon.png"));
		pnlBuyMenu.add(btnAddWish);

		JButton btnAddCart = new JButton("");
		btnAddCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tblAllProducts.getSelectedRow();
				if (selectedRow != -1) {
					insertProductInCart(selectedRow, tblAllProducts);
				}
			}
		});
		btnAddCart.setBounds(370, 328, 55, 55);
		btnAddCart.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\addProductToCartBTNicon.png"));
		pnlBuyMenu.add(btnAddCart);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 307, 540, -235);

		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(250, 11, 174, 43);
		frmBuyMenu.getContentPane().add(lblLogo);
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\BAMAZON.png"));
		frmBuyMenu.getContentPane().setLayout(null);

		JButton btnCart = new JButton("");
		btnCart.setToolTipText("Cart");
		btnCart.setBackground(new Color(255, 255, 255));
		btnCart.setForeground(new Color(255, 255, 255));
		btnCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initializePanel();
				setPanelActivation(pnlCart, true);
				populateCartTable();

				if (hasObject("SELECT * FROM cart WHERE customer_id = ?")) {
					calculateCartTotal();
				}
			}
		});
		btnCart.setBounds(10, 60, 75, 75);
		btnCart.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\cartBTNicon.png"));
		frmBuyMenu.getContentPane().add(btnCart);

		JButton btnWishList = new JButton("");
		btnWishList.setToolTipText("Wish Lists");
		btnWishList.setBackground(new Color(255, 255, 255));
		btnWishList.setForeground(new Color(255, 255, 255));
		btnWishList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initializePanel();
				setPanelActivation(pnlWish, true);
				populateWishListTable();
			}
		});
		btnWishList.setBounds(10, 140, 75, 75);
		btnWishList.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\wishBTNicon.png"));
		frmBuyMenu.getContentPane().add(btnWishList);

		JButton btnOrder = new JButton("");
		btnOrder.setToolTipText("Orders");
		btnOrder.setBackground(new Color(255, 255, 255));
		btnOrder.setForeground(new Color(255, 255, 255));
		btnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initializePanel();
				setPanelActivation(pnlOrder, true);
				populateOrderTable();
			}
		});
		btnOrder.setBounds(10, 220, 75, 75);
		btnOrder.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\orderBTNicon.png"));
		frmBuyMenu.getContentPane().add(btnOrder);

		JButton btnReturn = new JButton("");
		btnReturn.setToolTipText("Returns");
		btnReturn.setBackground(new Color(255, 255, 255));
		btnReturn.setForeground(new Color(255, 255, 255));
		btnReturn.setBounds(10, 300, 75, 75);
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initializePanel();
				setPanelActivation(pnlReturn, true);
			}
		});
		btnReturn.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\returnBTNicon.png"));
		frmBuyMenu.getContentPane().add(btnReturn);

		JButton btnUser = new JButton("");
		btnUser.setToolTipText("Your Information");
		btnUser.setBackground(new Color(255, 255, 255));
		btnUser.setForeground(new Color(255, 255, 255));
		btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User user = new User();
				frmBuyMenu.setVisible(false);
				user.setVisible(true);
				user.setUser_email(user_email);
				user.updateDisplay();
			}
		});
		btnUser.setBounds(10, 380, 75, 75);
		btnUser.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\userBTNicon.png"));
		frmBuyMenu.getContentPane().add(btnUser);

		JButton btnHome = new JButton("");
		btnHome.setToolTipText("Click Here to See Product Menu.");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				initializePanel();
				setPanelActivation(pnlBuyMenu, true);
				populateProductTable();
			}
		});
		btnHome.setBounds(181, 11, 256, 43);
		btnHome.setOpaque(false);
		btnHome.setContentAreaFilled(false);
		btnHome.setBorderPainted(false);
		frmBuyMenu.getContentPane().add(btnHome);

		JLabel lblHomePageLogo = new JLabel("");
		lblHomePageLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblHomePageLogo.setBounds(201, 5, 61, 54);
		lblHomePageLogo.setIcon(new ImageIcon(
				"C:\\Users\\govdy\\Documents\\Work\\COMP313 - Computer Systems\\Practicals\\BAMAZON\\Images\\HomePageLogo.png"));
		frmBuyMenu.getContentPane().add(lblHomePageLogo);

		initializeComponents();
	}

	void initializeComponents() {
		populateProductTable();
		JPanel[] newPanels = { pnlBuyMenu, pnlCart, pnlWish, pnlOrder, pnlReturn };
		panels = newPanels;
		initializePanel();
		setPanelActivation(pnlBuyMenu, true);
	}

	public void setPanelActivation(JPanel panel, boolean flag) {
		Component[] panelComponents = panel.getComponents();
		for (Component component : panelComponents) {
			component.setEnabled(flag);
			component.setVisible(flag);
		}

		panel.setVisible(flag);
		panel.setEnabled(flag);
	}

	public void initializePanel() {
		for (JPanel panel : panels) {
			setPanelActivation(panel, false);
		}
	}

	public void populateCartTable() {
		if (hasObject("SELECT * FROM cart WHERE customer_id = ?")) {
			cart_key = getID("SELECT cart_id FROM cart WHERE customer_id = ?", "cart");

			try {
				// Establish the connection
				Connection connect = getConnection();

				// Execute the select statement
				String query = "SELECT cart_detail.serial_code, title, product_price, product_description, product_quantity FROM cart_detail, product WHERE cart_detail.serial_code = product.serial_code AND cart_id = ?";
				PreparedStatement statement = connect.prepareStatement(query);
				statement.setInt(1, cart_key);
				ResultSet rs = statement.executeQuery();

				// Clear the existing table data
				DefaultTableModel tableModel = (DefaultTableModel) tblCart.getModel();
				tableModel.setRowCount(0);
				tableModel.setColumnCount(0);
				tableModel.addColumn("Serial Code");
				tableModel.addColumn("Title");
				tableModel.addColumn("Price");
				tableModel.addColumn("Description");
				tableModel.addColumn("Quantity");

				// Iterate over the result set and populate the table
				while (rs.next()) {
					int code = rs.getInt("serial_code");
					String title = rs.getString("title");
					Double price = rs.getDouble("product_price");
					String description = rs.getString("product_description");
					int quantity = rs.getInt("product_quantity");

					Object[] rowData = { code, title, price, description, quantity };
					tableModel.addRow(rowData);
				}

				tblCart.setModel(tableModel);
				tblCart.repaint();
				connect.close();

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Something went wrong in trying to get data.");
				e.printStackTrace();
			}
		}

		else {
			createObject("INSERT INTO cart(customer_id) VALUES(?)", "Cart");
			return;
		}
	}

	public void populateWishListTable() {

		if (hasObject("SELECT * FROM wish_list WHERE customer_id = ?")) {
			wish_key = getID("SELECT wish_list_id FROM wish_list WHERE customer_id = ?", "wish_list");

			try {
				// Establish the connection
				Connection connect = getConnection();

				// Execute the select statement
				String query = "SELECT wish_list_detail.serial_code, product.title, product.price FROM wish_list_detail, product WHERE wish_list_detail.serial_code = product.serial_code AND wish_list_id = ?";
				PreparedStatement statement = connect.prepareStatement(query);
				statement.setInt(1, wish_key);
				ResultSet rs = statement.executeQuery();

				// Clear the existing table data
				DefaultTableModel tableModel = (DefaultTableModel) tblWish.getModel();
				tableModel.setRowCount(0);
				tableModel.setColumnCount(0);
				tableModel.addColumn("Serial Code");
				tableModel.addColumn("Title");
				tableModel.addColumn("Price");

				// Iterate over the result set and populate the table
				while (rs.next()) {
					int code = rs.getInt("serial_code");
					String title = rs.getString("title");
					Double price = rs.getDouble("price");

					Object[] rowData = { code, title, price };
					tableModel.addRow(rowData);
				}

				tblWish.setModel(tableModel);
				tblWish.repaint();
				connect.close();

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Something went wrong in trying to get data from wish_list.");
				e.printStackTrace();
			}
		}

		else {
			createObject("INSERT INTO wish_list(customer_id) VALUES(?)", "Wish");
			return;
		}
	}

	public void populateOrderTable() {
		if (hasObject("SELECT * FROM order_t WHERE customer_id = ?")) {
			try {
				// Establish the connection
				Connection connect = getConnection();

				// Execute the select statement
				String query = "SELECT * FROM order_t WHERE customer_id = ? ORDER BY order_date, order_number DESC";
				PreparedStatement statement = connect.prepareStatement(query);
				statement.setString(1, user_email);
				ResultSet rs = statement.executeQuery();

				// Clear the existing table data
				DefaultTableModel tableModel = (DefaultTableModel) tblOrder.getModel();
				tableModel.setRowCount(0);
				tableModel.setColumnCount(0);
				tableModel.addColumn("Order Number");
				tableModel.addColumn("Date Ordered");
				tableModel.addColumn("Order's Total Price");
				tableModel.addColumn("Number of Product(s) Ordered");
				tableModel.addColumn("Status");

				// Iterate over the result set and populate the table
				while (rs.next()) {
					int id = rs.getInt("order_number");
					Date date = rs.getDate("order_date");
					Double price = rs.getDouble("order_total_price");
					int numTotal = rs.getInt("total_product_num");
					String status = rs.getString("order_status");

					Object[] rowData = { id, date, price, numTotal, status };
					tableModel.addRow(rowData);
				}

				tblOrder.setModel(tableModel);
				tblOrder.repaint();
				connect.close();

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Something went wrong in trying to get data from wish_list.");
				e.printStackTrace();
			}
		}

		else {
			// Clear the existing table data
			DefaultTableModel tableModel = (DefaultTableModel) tblOrder.getModel();
			tableModel.setRowCount(0);
			tableModel.setColumnCount(0);
			tableModel.addColumn("Order Number");
			tableModel.addColumn("Date Ordered");
			tableModel.addColumn("Order's Total Price");
			tableModel.addColumn("Number of Product(s) Ordered");
			tableModel.addColumn("Status");
			return;
		}
	}
	
	void PopulateOrderTableProduct(int row)
	{
		int orderNumber = (int) tblOrder.getValueAt(row, 0);
		
		// Clear the existing table data
		DefaultTableModel tableModel = (DefaultTableModel) tblOrder.getModel();
		tableModel.setRowCount(0);
		tableModel.setColumnCount(0);
		tableModel.addColumn("Product Serial Code");
		tableModel.addColumn("Product Price");
		tableModel.addColumn("Product Quantity");

		try {
			Connection connection = getConnection();
			String updateQuery = "SELECT * FROM order_detail WHERE order_number = ?";
			PreparedStatement statement = connection.prepareStatement(updateQuery);
			statement.setInt(1, orderNumber);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next())
			{
				int code = rs.getInt("serial_code");
				Double price = rs.getDouble("product_price");
				int numTotal = rs.getInt("product_quantity");

				Object[] rowData = { code, price, numTotal };
				tableModel.addRow(rowData);
			}

			tblOrder.setModel(tableModel);
			tblOrder.repaint();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void populateProductTable() {
		try {
			// Establish the connection
			Connection connect = getConnection();

			// Execute the select statement
			String query = "SELECT * FROM product";
			PreparedStatement statement = connect.prepareStatement(query);
			ResultSet rs = statement.executeQuery();

			// Clear the existing table data
			DefaultTableModel tableModel = (DefaultTableModel) tblAllProducts.getModel();
			tableModel.setRowCount(0);
			tableModel.setColumnCount(0);
			tableModel.addColumn("Serial Code");
			tableModel.addColumn("Title");
			tableModel.addColumn("Price");
			tableModel.addColumn("Description");
			tableModel.addColumn("Quantity");

			// Iterate over the result set and populate the table
			while (rs.next()) {
				int code = rs.getInt("serial_code");
				String title = rs.getString("title");
				Double price = rs.getDouble("price");
				String description = rs.getString("product_description");
				int quantity = rs.getInt("quantity");

				Object[] rowData = { code, title, price, description, quantity };
				tableModel.addRow(rowData);
			}

			tblAllProducts.setModel(tableModel);
			tblAllProducts.repaint();
			connect.close();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Something went wrong in trying to get data.");
			e.printStackTrace();
		}
	}

	public void CancleOrder(int row) {
		int orderNumber = (int) tblOrder.getValueAt(row, 0);

		try {
			Connection connection = getConnection();
			String updateQuery = "UPDATE order_t SET order_status = 'Cancelled' WHERE order_number = ?";
			PreparedStatement statement = connection.prepareStatement(updateQuery);
			statement.setInt(1, orderNumber);
			int rowsAffected = statement.executeUpdate();

			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(null, "Order cancelled successfully.", "Success",
						JOptionPane.INFORMATION_MESSAGE);
				tblOrder.setValueAt("Cancelled", row, 4);
			} else {
				JOptionPane.showMessageDialog(null, "Failed to cancel the order.", "Error", JOptionPane.ERROR_MESSAGE);
			}

			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void PayOrder(int row) {
		int orderNumber = (int) tblOrder.getValueAt(row, 0);

		try {
			Connection connection = getConnection();
			String updateQuery = "UPDATE order_t SET order_status = 'Processing order' WHERE order_number = ?";
			PreparedStatement statement = connection.prepareStatement(updateQuery);
			statement.setInt(1, orderNumber);
			int rowsAffected = statement.executeUpdate();

			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(null, "Paid for order successfully.", "Success",
						JOptionPane.INFORMATION_MESSAGE);
				tblOrder.setValueAt("Processing order", row, 4);
			} else {
				JOptionPane.showMessageDialog(null, "Failed to pay for the order.", "Error", JOptionPane.ERROR_MESSAGE);
			}

			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void ProcessOrder(int row) {
		int orderNumber = (int) tblOrder.getValueAt(row, 0);

		try {
			Connection connection = getConnection();
			String updateQuery = "UPDATE order_t SET order_status = 'Processed' WHERE order_number = ?";
			PreparedStatement statement = connection.prepareStatement(updateQuery);
			statement.setInt(1, orderNumber);
			int rowsAffected = statement.executeUpdate();

			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(null, "Paid for order successfully.", "Success",
						JOptionPane.INFORMATION_MESSAGE);
				tblOrder.setValueAt("Processed", row, 4);
			} else {
				JOptionPane.showMessageDialog(null, "Failed to pay for the order.", "Error", JOptionPane.ERROR_MESSAGE);
			}

			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void populateSortedProductTable(String query) {
		try {
			// Establish the connection
			Connection connect = getConnection();
			PreparedStatement statement = connect.prepareStatement(query);
			ResultSet rs = statement.executeQuery();

			// Clear the existing table data
			DefaultTableModel tableModel = (DefaultTableModel) tblAllProducts.getModel();
			tableModel.setRowCount(0);
			tableModel.setColumnCount(0);
			tableModel.addColumn("Serial Code");
			tableModel.addColumn("Title");
			tableModel.addColumn("Price");
			tableModel.addColumn("Description");
			tableModel.addColumn("Quantity");

			// Iterate over the result set and populate the table
			while (rs.next()) {
				int code = rs.getInt("serial_code");
				String title = rs.getString("title");
				Double price = rs.getDouble("price");
				String description = rs.getString("product_description");
				int quantity = rs.getInt("quantity");

				Object[] rowData = { code, title, price, description, quantity };
				tableModel.addRow(rowData);
			}

			tblAllProducts.setModel(tableModel);
			tblAllProducts.repaint();
			connect.close();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Something went wrong in trying to get data.");
			e.printStackTrace();
		}
	}

	public void searchProduct(String search) {
		try {
			Connection connect = getConnection();

			String query = "SELECT * FROM product WHERE title LIKE ?";
			PreparedStatement statement = connect.prepareStatement(query);
			statement.setString(1, "%" + search + "%");
			ResultSet rs = statement.executeQuery();

			DefaultTableModel tableModel = (DefaultTableModel) tblAllProducts.getModel();
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

			tblAllProducts.setModel(tableModel);
			tblAllProducts.repaint();
			connect.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void insertProductInCart(int row, JTable table) {
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		int code = (int) tableModel.getValueAt(row, 0);
		Double price = (Double) tableModel.getValueAt(row, 2);

		if (hasObject("SELECT * FROM cart WHERE customer_id = ?")) {
			cart_key = getID("SELECT cart_id FROM cart WHERE customer_id = ?", "cart");
		}

		else {
			createObject("INSERT INTO cart(customer_id) VALUES(?)", "Cart");
		}

		if (getProductSellerQuantity(code) > 0) {
			if (isProductInCart(code)) {
				try {
					Connection connect = getConnection();
					String query = "UPDATE cart_detail SET product_quantity = ? WHERE cart_id = ? AND serial_code = ?";
					PreparedStatement statement = connect.prepareStatement(query);
					statement.setInt(1, getProductCartQuantity(code));
					statement.setInt(2, cart_key);
					statement.setInt(3, code);
					statement.executeUpdate();
					connect.close();
				}

				catch (Exception e) {
					System.out.println("Something went wrong in trying to update the product.");
					e.printStackTrace();
				}
			} else {
				try {
					Connection connect = getConnection();
					String query = "INSERT INTO cart_detail(product_quantity, product_price, cart_id, serial_code) VALUES(?,?,?,?)";
					PreparedStatement statement = connect.prepareStatement(query);
					statement.setInt(1, 1);
					statement.setDouble(2, price);
					statement.setInt(3, cart_key);
					statement.setInt(4, code);
					statement.executeUpdate();
					connect.close();
				}

				catch (Exception e) {
					System.out.println("Something went wrong in trying to insert the product.");
					e.printStackTrace();
				}
			}

			calculateCartTotal();
			JOptionPane.showMessageDialog(null, "Product successfully added to cart.");
		}

		else {
			JOptionPane.showMessageDialog(null, "Sorry, the product has been sold out. Try again next time.");
			return;
		}
	}

	void insertProductInOrder() {
		createOrder();
		try {
			Connection connect = getConnection();
			String selectQuery = "SELECT * FROM cart_detail WHERE cart_id = ?";
			PreparedStatement getStatement = connect.prepareStatement(selectQuery);
			getStatement.setInt(1, cart_key);
			ResultSet resultSet = getStatement.executeQuery();

			List<CartDetail> cartDetails = new ArrayList<>();

			while (resultSet.next()) {
				int quantity = resultSet.getInt("product_quantity");
				double price = resultSet.getDouble("product_price");
				int serialCode = resultSet.getInt("serial_code");

				CartDetail cartDetail = new CartDetail(quantity, price, serialCode);
				cartDetails.add(cartDetail);
			}

			String deleteQuery = "DELETE FROM cart_detail WHERE cart_id = ?";
			PreparedStatement deleteStatement = connect.prepareStatement(deleteQuery);
			deleteStatement.setInt(1, cart_key);
			deleteStatement.executeUpdate();

			// insert cart details into the order_detail table
			for (CartDetail cartDetail : cartDetails) {
				// Retrieve product details from cart detail instance
				int quantity = cartDetail.getQuantity();
				double price = cartDetail.getPrice();
				int serialCode = cartDetail.getCode();

				// insert product details into the order_detail table
				String insertQuery = "INSERT INTO order_detail (product_quantity, product_price, order_number, serial_code) "
						+ "VALUES (?, ?, ?, ?)";
				PreparedStatement preparedStatement = connect.prepareStatement(insertQuery);
				preparedStatement.setInt(1, quantity);
				preparedStatement.setDouble(2, price);
				preparedStatement.setInt(3, order_key);
				preparedStatement.setInt(4, serialCode);
				preparedStatement.executeUpdate();
			}

			// stock reduce
			for (CartDetail cartDetail : cartDetails) {
				int quantity = cartDetail.getQuantity();
				int serialCode = cartDetail.getCode();

				String reduceQuery = "UPDATE product SET quantity = ? WHERE serial_code = ?";
				PreparedStatement updateStatement = connect.prepareStatement(reduceQuery);
				updateStatement.setInt(1, (getProductSellerQuantity(serialCode) - quantity));
				updateStatement.setInt(2, serialCode);
				updateStatement.executeUpdate();
			}

			populateCartTable();
			totalPrice = 0;
			numProducts = 0;
			calculateCartTotal();

			JOptionPane.showMessageDialog(null, "Order was placed successfully.");
		}

		catch (Exception e) {
			System.out.println("Something went wrong in trying to insert the product.");
			e.printStackTrace();
		}
	}

	void createOrder() {
		try {
			Connection connect = getConnection();
			String query = "INSERT INTO order_t(order_total_price, total_product_num, customer_id) VALUES(?,?,?)";
			PreparedStatement statement = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setDouble(1, totalPrice);
			statement.setInt(2, numProducts);
			statement.setString(3, user_email);
			statement.executeUpdate();

			ResultSet generatedKeys = statement.getGeneratedKeys();

			if (generatedKeys.next()) {
				order_key = generatedKeys.getInt(1);
			} else {
				System.out.println("Failed to retrive object key.");
			}

			connect.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void insertProductInWishList(int row) {
		DefaultTableModel tableModel = (DefaultTableModel) tblAllProducts.getModel();
		int code = (int) tableModel.getValueAt(row, 0);

		if (hasObject("SELECT * FROM wish_list WHERE customer_id = ?")) {
			wish_key = getID("SELECT wish_list_id FROM wish_list WHERE customer_id = ?", "wish_list");
		}

		else {
			createObject("INSERT INTO wish_list(customer_id) VALUES(?)", "Wish");
		}

		if (isProductInWishList(code)) {
			return;
		} else {
			try {
				Connection connect = getConnection();
				String query = "INSERT INTO wish_list_detail(wish_list_id, serial_code) VALUES(?,?)";
				PreparedStatement statement = connect.prepareStatement(query);
				statement.setInt(1, wish_key);
				statement.setInt(2, code);
				statement.executeUpdate();
				connect.close();
			}

			catch (Exception e) {
				System.out.println("Something went wrong in trying to insert the product in wish_list.");
				e.printStackTrace();
			}
		}

		calculateCartTotal();
		JOptionPane.showMessageDialog(null, "Product successfully added to wish list.");
	}

	boolean isProductInCart(int code) {
		try {
			Connection connect = getConnection();
			String query = "SELECT COUNT(*) FROM cart_detail WHERE serial_code = ? AND cart_id = ?";
			PreparedStatement statement = connect.prepareStatement(query);
			statement.setInt(1, code);
			statement.setInt(2, cart_key);
			ResultSet resultSet = statement.executeQuery();

			int count = 0;
			if (resultSet.next()) {
				count = resultSet.getInt(1);
			}

			resultSet.close();
			return count > 0;

		} catch (Exception e) {
			System.out.println("Something went wrong in trying to check if the product is in the cart.");
			e.printStackTrace();
		}

		return false;
	}

	boolean isProductInWishList(int code) {
		try {
			Connection connect = getConnection();
			String query = "SELECT COUNT(*) FROM wish_list_detail WHERE serial_code = ? AND wish_list_id = ?";
			PreparedStatement statement = connect.prepareStatement(query);
			statement.setInt(1, code);
			statement.setInt(2, wish_key);
			ResultSet resultSet = statement.executeQuery();

			int count = 0;
			if (resultSet.next()) {
				count = resultSet.getInt(1);
			}

			resultSet.close();
			return count > 0;

		} catch (Exception e) {
			System.out.println("Something went wrong in trying to check if the product is in the wish_list.");
			e.printStackTrace();
		}

		return false;
	}

	int getProductCartQuantity(int code) {
		int productQuantity = 0;
		try {
			Connection connect = getConnection();
			String selectQuery = "SELECT product_quantity FROM cart_detail WHERE cart_id = ? AND serial_code = ?";
			PreparedStatement selectStatement = connect.prepareStatement(selectQuery);
			selectStatement.setInt(1, cart_key);
			selectStatement.setInt(2, code);
			ResultSet selectResult = selectStatement.executeQuery();

			if (selectResult.next()) {
				productQuantity = selectResult.getInt("product_quantity") + 1;
			}

			selectResult.close();
		} catch (Exception e) {
			System.out.println("Something went wrong while trying to check the product quantity from seller.");
			e.printStackTrace();
		}
		return productQuantity;
	}

	int getProductSellerQuantity(int code) {
		int productQuantity = 0;
		try {
			Connection connect = getConnection();
			String selectQuery = "SELECT quantity FROM product WHERE serial_code = ?";
			PreparedStatement selectStatement = connect.prepareStatement(selectQuery);
			selectStatement.setInt(1, code);
			ResultSet selectResult = selectStatement.executeQuery();

			if (selectResult.next()) {
				productQuantity = selectResult.getInt("quantity");
			}
			selectResult.close();
		} catch (Exception e) {
			System.out.println("Something went wrong while trying to check the product quantity from seller.");
			e.printStackTrace();
		}
		return productQuantity;
	}

	boolean hasObject(String query) {
		try {
			Connection connect = getConnection();
			PreparedStatement statement = connect.prepareStatement(query);
			statement.setString(1, user_email);
			ResultSet resultSet = statement.executeQuery();
			return resultSet.next();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	void createObject(String query, String objectType) {
		try {
			Connection connect = getConnection();
			// String query = "INSERT INTO cart(customer_id) VALUES(?)";
			PreparedStatement statement = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, user_email);
			statement.executeUpdate();

			ResultSet generatedKeys = statement.getGeneratedKeys();

			if (generatedKeys.next()) {
				if (objectType == "Cart")
					cart_key = generatedKeys.getInt(1);
				else if (objectType == "Wish")
					wish_key = generatedKeys.getInt(1);
				else
					return_key = generatedKeys.getInt(1);
			} else {
				System.out.println("Failed to retrive object key.");
			}

			connect.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	int getID(String query, String object) {
		try {
			Connection connect = getConnection();
			// String query = "SELECT cart_id FROM cart WHERE customer_id = ?";
			PreparedStatement statement = connect.prepareStatement(query);
			statement.setString(1, user_email);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				return resultSet.getInt(object + "_id");
			}

			connect.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	void calculateCartTotal() {
		try {
			Connection connect = getConnection();
			String query = "SELECT SUM(product_quantity) AS total_products, SUM(product_quantity * product_price) AS total_cost FROM cart_detail WHERE cart_id = ?";
			PreparedStatement statement = connect.prepareStatement(query);
			statement.setInt(1, cart_key);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				int totalProducts = resultSet.getInt("total_products");
				double totalCost = resultSet.getDouble("total_cost");
				numProducts = totalProducts;
				totalPrice = totalCost;

				txtTotalPrice.setText("Total Products: " + totalProducts);
				txtTotalProduct.setText("Total Cost: R" + totalCost);

				updateCart(totalProducts, totalCost);
			}

			connect.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void updateCart(int numProduct, double numPrice) {
		try {
			Connection connect = getConnection();
			String query = "UPDATE cart SET total_product_num = ?, total_price = ? WHERE cart_id = ?";
			PreparedStatement statement = connect.prepareStatement(query);
			statement.setInt(1, numProduct);
			statement.setDouble(2, numPrice);
			statement.setInt(3, cart_key);
			statement.executeUpdate();
			connect.close();
		}

		catch (Exception e) {
			System.out.println("Something went wrong in trying to update the cart.");
			e.printStackTrace();
		}
	}

	void removeProductFromCart(int row) {
		DefaultTableModel tableModel = (DefaultTableModel) tblCart.getModel();
		int code = (int) tableModel.getValueAt(row, 0);

		try {
			Connection connect = getConnection();
			String query = "DELETE FROM cart_detail WHERE cart_id = ? AND serial_code = ?";
			PreparedStatement statement = connect.prepareStatement(query);
			statement.setInt(1, cart_key);
			statement.setInt(2, code);
			statement.executeUpdate();
			connect.close();

			calculateCartTotal();
			populateCartTable();
		}

		catch (Exception e) {
			System.out.println("Something went wrong in trying to remove the product from cart.");
			e.printStackTrace();
		}
	}

	void removeProductFromWish(int row) {
		DefaultTableModel tableModel = (DefaultTableModel) tblWish.getModel();
		int code = (int) tableModel.getValueAt(row, 0);

		try {
			Connection connect = getConnection();
			String query = "DELETE FROM wish_list_detail WHERE wish_list_id = ? AND serial_code = ?";
			PreparedStatement statement = connect.prepareStatement(query);
			statement.setInt(1, wish_key);
			statement.setInt(2, code);
			statement.executeUpdate();
			connect.close();

			populateWishListTable();
		}

		catch (Exception e) {
			System.out.println("Something went wrong in trying to remove the product from cart.");
			e.printStackTrace();
		}
	}

	void removeOneProductFromCart(int row) {
		DefaultTableModel tableModel = (DefaultTableModel) tblCart.getModel();
		int code = (int) tableModel.getValueAt(row, 0);

		if (getProductCartQuantity(code) > 2) {
			try {
				Connection connect = getConnection();
				String query = "UPDATE cart_detail SET product_quantity = ? WHERE cart_id = ? AND serial_code = ?";
				PreparedStatement statement = connect.prepareStatement(query);
				statement.setInt(1, getProductCartQuantity(code) - 2);
				statement.setInt(2, cart_key);
				statement.setInt(3, code);
				statement.executeUpdate();
				connect.close();

				calculateCartTotal();
				populateCartTable();
			}

			catch (Exception e) {
				System.out.println("Something went wrong in trying to remove one product from cart.");
				e.printStackTrace();
			}
		}

		else {
			removeProductFromCart(row);
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

	public void setVisible(boolean visible) {
		frmBuyMenu.setVisible(visible);
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
}
