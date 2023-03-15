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
import model.Registration;
import model.Session;

public class EditRegistrationPanel extends JPanel {
  private RegistrationController registrationController;

  List<Customer> customers;
  List<Employee> employees;
  List<Product> products;

  private UUID registrationId;

  private JComboBox<Customer> customerComboBox;
  private JComboBox<Employee> employeeComboBox;
  private DatePicker startDatePicker;
  private DatePicker endDatePicker;
  private JComboBox<Product> productComboBox1;
  private JComboBox<Product> productComboBox2;
  private JComboBox<Product> productComboBox3;

  private JButton btnSubmit;
  private JLabel mainHeaderLabel;

  /**
   * Create the panel.
   */
  public EditRegistrationPanel() {
    registrationController = new RegistrationController();

    setLayout(new BorderLayout(0, 0));

    createToolbar();
    createContent();

    onPanelVisible();
  }

  /**
   * Create the toolbar.
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

    mainHeaderLabel = new JLabel();
    mainHeaderLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
    mainHeaderLabel.setBorder(new EmptyBorder(0, 10, 0, 12));
    northPanel.add(mainHeaderLabel);
  }

  /**
   * Create the content.
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

    customers = new CustomerController().getAllCustomers();

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

    employees = new EmployeeController().getAllEmployees();

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
    gbc_txtProduct2.gridy = 6;
    contentPanel.add(productComboBox2, gbc_txtProduct2);
    productComboBox2.setSelectedIndex(-1);

    productComboBox3 = new JComboBox<Product>(products.toArray(new Product[products.size()]));
    GridBagConstraints gbc_txtProduct3 = new GridBagConstraints();
    gbc_txtProduct3.fill = GridBagConstraints.HORIZONTAL;
    gbc_txtProduct3.insets = new Insets(0, 0, 5, 5);
    gbc_txtProduct3.gridx = 2;
    gbc_txtProduct3.gridy = 7;
    contentPanel.add(productComboBox3, gbc_txtProduct3);
    productComboBox3.setSelectedIndex(-1);

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
   * Method to handle the submit button.
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

    try {
      UUID storeId = Session.getEmployeeStoreId();
      registrationController.updateRegistration(registrationId, customerIdentityNo, employeeIdentityNo, storeId,
          productBarcodes, startDate, endDate);
      JOptionPane.showMessageDialog(null, "Registration was updated successfully!");
      goBackToList();
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e.getMessage());
    }
  }

  /**
   * Method to go back to the list of registrations.
   */
  private void onPanelVisible() {
    EventHandler.addListener("edit-registration-panel-shown", (id) -> {
      try {
        Registration registration = registrationController.getRegistration((UUID) id);
        fillFields(registration);
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
      }
    });
  }

  /**
   * Go back to the list of registrations.
   */
  private void goBackToList() {
    resetFields();
    EventHandler.fireEvent("refresh-registrations", null);
    CardLayout cardLayout = (CardLayout) getParent().getLayout();
    cardLayout.show(getParent(), "registrationPanel");
  }

  /**
   * Method to fill the fields with the given registration.
   *
   * @param registration the registration to fill the fields with.
   */

  private void fillFields(Registration registration) {
    registrationId = registration.getId();
    mainHeaderLabel.setText("Edit Registration - " + registrationId);

    Customer customer = null;
    Employee employee = null;
    Product product1 = null;
    Product product2 = null;
    Product product3 = null;

    for (Customer c : customers) {
      if (c.getIdentityNo().equals(registration.getCustomerIdentityNo())) {
        customer = c;
        break;
      }
    }
    for (Employee e : employees) {
      if (e.getIdentityNo().equals(registration.getEmployeeIdentityNo())) {
        employee = e;
        break;
      }
    }
    if (!registration.getProductBarcodes().isEmpty()) {
      for (int i = 0; i < registration.getProductBarcodes().size(); i++) {

        if (i == 0 && registration.getProductBarcodes().get(i) != null) {
          for (Product p : products) {
            if (p.getBarcode().equals(registration.getProductBarcodes().get(i))) {
              product1 = p;
              productComboBox1.setSelectedItem(product1);
              break;
            }
          }
        }

        if (i == 1 && registration.getProductBarcodes().get(i) != null) {
          for (Product p : products) {
            if (p.getBarcode().equals(registration.getProductBarcodes().get(i))) {
              product2 = p;
              productComboBox2.setSelectedItem(product2);
              break;
            }
          }
        }

        if (i == 2 && registration.getProductBarcodes().get(i) != null) {
          for (Product p : products) {
            if (p.getBarcode().equals(registration.getProductBarcodes().get(i))) {
              product3 = p;
              productComboBox3.setSelectedItem(product3);
              break;
            }
          }
        }
      }

    }

    customerComboBox.setSelectedItem(customer);
    employeeComboBox.setSelectedItem(employee);

    startDatePicker.setDate(registration.getStartDate());
    endDatePicker.setDate(registration.getEndDate());

  }

  /**
   * Method to reset the fields.
   */
  private void resetFields() {
    registrationId = null;
    mainHeaderLabel.setText("");
    customerComboBox.setSelectedIndex(0);
    employeeComboBox.setSelectedIndex(0);
    startDatePicker.reset();
    endDatePicker.reset();
    productComboBox1.setSelectedIndex(-1);
    productComboBox2.setSelectedIndex(-1);
    productComboBox3.setSelectedIndex(-1);
  }
}
