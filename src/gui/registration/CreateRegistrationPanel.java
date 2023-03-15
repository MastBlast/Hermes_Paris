package gui.registration;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import controller.CustomerController;
import controller.EmployeeController;
import controller.ProductController;
import controller.RegistrationController;
import gui.components.DatePicker;
import gui.events.EventHandler;
import model.Customer;
import model.Employee;
import model.Product;
import model.Session;

public class CreateRegistrationPanel extends JPanel {
  private RegistrationController registrationController;

  private JComboBox<Customer> customerComboBox;
  private JComboBox<Employee> employeeComboBox;
  private JComboBox<Product> productComboBox1;
  private JComboBox<Product> productComboBox2;
  private JComboBox<Product> productComboBox3;
  private DatePicker startDatePicker;
  private DatePicker endDatePicker;

  private JButton btnAddProduct;
  private int productCounter = 0;

  private JButton btnSubmit;

  /**
   * Create the panel.
   */
  public CreateRegistrationPanel() {
    registrationController = new RegistrationController();

    setLayout(new BorderLayout(0, 0));

    createToolbar();
    createContent();
  }

  /**
   * Creates the toolbar.
   */
  private void createToolbar() {
    JPanel northPanel = new JPanel();
    northPanel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
    northPanel.setPreferredSize(new Dimension(0, 51));
    add(northPanel, BorderLayout.NORTH);

    FlowLayout insideNorthPanel = new FlowLayout(FlowLayout.LEFT, 10, 11);
    insideNorthPanel.setAlignOnBaseline(true);
    northPanel.setLayout(insideNorthPanel);

    JButton btnBack = new JButton("");
    ImageIcon imgBack = new ImageIcon(new ImageIcon(getClass().getResource("/gui/assets/back.png")).getImage()
        .getScaledInstance(25, 25, Image.SCALE_DEFAULT));
    btnBack.setIcon(imgBack);
    btnBack.addActionListener(e -> {
      goBackToList();
      resetFields();
    });
    northPanel.add(btnBack);

    JLabel lblNewLabel = new JLabel("Create Registration");
    lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
    lblNewLabel.setBorder(new EmptyBorder(0, 10, 0, 12));
    northPanel.add(lblNewLabel);

  }

  /**
   * Creates the content.
   */
  private void createContent() {
    JPanel contentPanel = new JPanel();
    add(contentPanel, BorderLayout.CENTER);
    GridBagLayout gbl_contentPanel = new GridBagLayout();
    gbl_contentPanel.columnWidths = new int[] { 56, 0, 0, 0, 0 };
    gbl_contentPanel.rowHeights = new int[] { 40, 16, 16, 16, 16, 16, 0, 0, 0 };
    gbl_contentPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };
    gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
    contentPanel.setLayout(gbl_contentPanel);

    JLabel lblCustomersName = new JLabel("Customer:");
    lblCustomersName.setFont(new Font("Arial", Font.BOLD, 15));
    GridBagConstraints gbc_lblCustomersName = new GridBagConstraints();
    gbc_lblCustomersName.anchor = GridBagConstraints.WEST;
    gbc_lblCustomersName.fill = GridBagConstraints.VERTICAL;
    gbc_lblCustomersName.insets = new Insets(0, 0, 5, 5);
    gbc_lblCustomersName.gridx = 1;
    gbc_lblCustomersName.gridy = 1;
    contentPanel.add(lblCustomersName, gbc_lblCustomersName);

    List<Customer> customers = new CustomerController().getAllCustomers();

    customerComboBox = new JComboBox<Customer>(customers.toArray(new Customer[customers.size()]));
    GridBagConstraints gbc_txtCustomerId = new GridBagConstraints();
    gbc_txtCustomerId.fill = GridBagConstraints.HORIZONTAL;
    gbc_txtCustomerId.insets = new Insets(0, 0, 5, 5);
    gbc_txtCustomerId.gridx = 2;
    gbc_txtCustomerId.gridy = 1;
    contentPanel.add(customerComboBox, gbc_txtCustomerId);

    JLabel lblEmployeeName = new JLabel("Employee:");
    lblEmployeeName.setFont(new Font("Arial", Font.BOLD, 15));
    GridBagConstraints gbc_lblEmployeesName = new GridBagConstraints();
    gbc_lblEmployeesName.anchor = GridBagConstraints.WEST;
    gbc_lblEmployeesName.fill = GridBagConstraints.VERTICAL;
    gbc_lblEmployeesName.insets = new Insets(0, 0, 5, 5);
    gbc_lblEmployeesName.gridx = 1;
    gbc_lblEmployeesName.gridy = 2;
    contentPanel.add(lblEmployeeName, gbc_lblEmployeesName);

    List<Employee> employees = new EmployeeController().getAllEmployees();

    employeeComboBox = new JComboBox<Employee>(employees.toArray(new Employee[employees.size()]));
    GridBagConstraints gbc_txtEmployeeId = new GridBagConstraints();
    gbc_txtEmployeeId.fill = GridBagConstraints.HORIZONTAL;
    gbc_txtEmployeeId.insets = new Insets(0, 0, 5, 5);
    gbc_txtEmployeeId.gridx = 2;
    gbc_txtEmployeeId.gridy = 2;
    contentPanel.add(employeeComboBox, gbc_txtEmployeeId);

    JLabel lblStartDate = new JLabel("Start date:");
    lblStartDate.setFont(new Font("Arial", Font.BOLD, 15));
    GridBagConstraints gbc_lblStartDate = new GridBagConstraints();
    gbc_lblStartDate.insets = new Insets(0, 0, 5, 5);
    gbc_lblStartDate.anchor = GridBagConstraints.WEST;
    gbc_lblStartDate.gridx = 1;
    gbc_lblStartDate.gridy = 3;
    contentPanel.add(lblStartDate, gbc_lblStartDate);

    startDatePicker = new DatePicker();
    GridBagConstraints gbc_txtStartDate = new GridBagConstraints();
    gbc_txtStartDate.fill = GridBagConstraints.HORIZONTAL;
    gbc_txtStartDate.insets = new Insets(0, 0, 5, 5);
    gbc_txtStartDate.gridx = 2;
    gbc_txtStartDate.gridy = 3;
    contentPanel.add(startDatePicker, gbc_txtStartDate);

    JLabel lblEndDate = new JLabel("End date:");
    lblEndDate.setFont(new Font("Arial", Font.BOLD, 15));
    GridBagConstraints gbc_lblEndDate = new GridBagConstraints();
    gbc_lblEndDate.insets = new Insets(0, 0, 5, 5);
    gbc_lblEndDate.anchor = GridBagConstraints.WEST;
    gbc_lblEndDate.fill = GridBagConstraints.VERTICAL;
    gbc_lblEndDate.gridx = 1;
    gbc_lblEndDate.gridy = 4;
    contentPanel.add(lblEndDate, gbc_lblEndDate);

    endDatePicker = new DatePicker();
    GridBagConstraints gbc_txtEndDate = new GridBagConstraints();
    gbc_txtEndDate.fill = GridBagConstraints.HORIZONTAL;
    gbc_txtEndDate.insets = new Insets(0, 0, 5, 5);
    gbc_txtEndDate.gridx = 2;
    gbc_txtEndDate.gridy = 4;
    contentPanel.add(endDatePicker, gbc_txtEndDate);

    JLabel lblProductBarcode = new JLabel("Products:");
    lblProductBarcode.setFont(new Font("Arial", Font.BOLD, 15));
    GridBagConstraints gbc_lblProductBarcode = new GridBagConstraints();
    gbc_lblProductBarcode.insets = new Insets(0, 0, 5, 5);
    gbc_lblProductBarcode.gridx = 1;
    gbc_lblProductBarcode.anchor = GridBagConstraints.WEST;
    gbc_lblProductBarcode.fill = GridBagConstraints.VERTICAL;
    gbc_lblProductBarcode.gridy = 5;
    contentPanel.add(lblProductBarcode, gbc_lblProductBarcode);

    List<Product> products = null;
    try {
      products = new ProductController().getAllProducts();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    productComboBox1 = new JComboBox<Product>(products.toArray(new Product[products.size()]));
    GridBagConstraints gbc_txtProduct1 = new GridBagConstraints();
    gbc_txtProduct1.fill = GridBagConstraints.HORIZONTAL;
    gbc_txtProduct1.insets = new Insets(0, 0, 5, 5);
    gbc_txtProduct1.gridx = 2;
    gbc_txtProduct1.gridy = 5;
    contentPanel.add(productComboBox1, gbc_txtProduct1);
    productComboBox1.setSelectedIndex(-1);

    productComboBox2 = new JComboBox<Product>(products.toArray(new Product[products.size()]));
    GridBagConstraints gbc_txtProduct2 = new GridBagConstraints();
    gbc_txtProduct2.fill = GridBagConstraints.HORIZONTAL;
    gbc_txtProduct2.insets = new Insets(0, 0, 5, 5);
    gbc_txtProduct2.gridx = 2;
    gbc_txtProduct2.gridy = 7;
    productComboBox2.setSelectedIndex(-1);

    productComboBox3 = new JComboBox<Product>(products.toArray(new Product[products.size()]));
    GridBagConstraints gbc_txtProduct3 = new GridBagConstraints();
    gbc_txtProduct3.fill = GridBagConstraints.HORIZONTAL;
    gbc_txtProduct3.insets = new Insets(0, 0, 5, 5);
    gbc_txtProduct3.gridx = 2;
    gbc_txtProduct3.gridy = 6;
    productComboBox3.setSelectedIndex(-1);

    btnAddProduct = new JButton("+");
    btnAddProduct.addActionListener((e) -> {
      if (productCounter == 1) {
        contentPanel.add(productComboBox2, gbc_txtProduct2);
        contentPanel.revalidate();
        contentPanel.repaint();
      } else if (productCounter == 0) {
        productCounter++;
        contentPanel.add(productComboBox3, gbc_txtProduct3);
        contentPanel.revalidate();
        contentPanel.repaint();
      }
    });

    btnAddProduct.setForeground(Color.BLACK);
    btnAddProduct.setBackground(Color.LIGHT_GRAY);
    GridBagConstraints gbc_btnAddProduct = new GridBagConstraints();
    gbc_btnAddProduct.insets = new Insets(0, 0, 5, 5);
    gbc_btnAddProduct.gridx = 3;
    gbc_btnAddProduct.gridy = 5;
    contentPanel.add(btnAddProduct, gbc_btnAddProduct);

    btnSubmit = new JButton("Submit");
    btnSubmit.setPreferredSize(new Dimension(80, 21));
    GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
    gbc_btnSubmit.insets = new Insets(0, 0, 0, 5);
    gbc_btnSubmit.gridx = 2;
    gbc_btnSubmit.gridy = 8;
    contentPanel.add(btnSubmit, gbc_btnSubmit);
    btnSubmit.addActionListener((e) -> {
      onSubmit();
    });
  }

  /**
   * This method is called when the submit button is pressed.
   */
  private void onSubmit() {
    String customerIdentityNo = ((Customer) customerComboBox.getSelectedItem()).getIdentityNo();
    String employeeIdentityNo = ((Employee) employeeComboBox.getSelectedItem()).getIdentityNo();
    LocalDate startDate = startDatePicker.getDate();
    LocalDate endDate = endDatePicker.getDate();
    List<String> productBarcodes = new ArrayList<>();
    if (productComboBox1.getSelectedIndex() != -1) {
      productBarcodes.add(((Product) productComboBox1.getSelectedItem()).getBarcode());
    }
    if (productComboBox2.getSelectedIndex() != -1) {
      productBarcodes.add(((Product) productComboBox2.getSelectedItem()).getBarcode());
    }
    if (productComboBox3.getSelectedIndex() != -1) {
      productBarcodes.add(((Product) productComboBox3.getSelectedItem()).getBarcode());
    }

    UUID storeId = Session.getEmployeeStoreId();

    try {
      registrationController.createRegistration(customerIdentityNo, employeeIdentityNo, storeId, productBarcodes,
          startDate, endDate);
      JOptionPane.showMessageDialog(null, "Registration created successfully!");
      goBackToList();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e.getMessage());
    }
  }

  /**
   * On back button press, go back to the list of registrations.
   */
  private void goBackToList() {
    resetFields();
    EventHandler.fireEvent("refresh-registrations", null);
    CardLayout cardLayout = (CardLayout) getParent().getLayout();
    cardLayout.show(getParent(), "registrationPanel");
  }

  /**
   * This method is called when the reset button is pressed.
   */
  private void resetFields() {
    customerComboBox.setSelectedIndex(0);
    employeeComboBox.setSelectedIndex(0);
    startDatePicker.reset();
    endDatePicker.reset();
    productComboBox1.setSelectedIndex(-1);
    productComboBox2.setSelectedIndex(-1);
    productComboBox3.setSelectedIndex(-1);
  }
}
