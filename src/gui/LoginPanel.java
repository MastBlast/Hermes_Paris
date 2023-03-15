package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.CardLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.EmployeeController;
import gui.events.EventHandler;

import java.awt.Font;

public class LoginPanel extends JPanel implements ActionListener {
  private EmployeeController employeeController;

  private JLabel lblNewLabel = new JLabel("Log in");
  private JPanel centeredContent = new JPanel();
  private JPanel formContent = new JPanel();
  private JLabel userLabel = new JLabel("Username: ");
  private JLabel passwordLabel = new JLabel("Password: ");
  private JTextField userTextField = new JTextField();
  private JPasswordField passwordField = new JPasswordField();
  private JButton loginButton = new JButton("Log in");
  private JCheckBox showPassword = new JCheckBox("Show Password");

  private GridBagLayout gridBagLayout = new GridBagLayout();
  private GridBagConstraints gbc = new GridBagConstraints();
  private GridBagConstraints gbc_1 = new GridBagConstraints();
  private GridBagConstraints gbc_2 = new GridBagConstraints();
  private GridBagConstraints gbc_3 = new GridBagConstraints();
  private GridBagConstraints gbc_4 = new GridBagConstraints();
  private GridBagConstraints gbc_5 = new GridBagConstraints();

  /**
   * Create the LoginPanel
   */
  public LoginPanel() {
    employeeController = new EmployeeController();

    setLayoutManager();
    setLocationAndSize();
    addComponentsToContainer();
    addActionEvent();
  }

  /**
   * Set the layout manager
   */
  public void setLayoutManager() {
    setLayout(new GridBagLayout());
    formContent.setLayout(gridBagLayout);
  }

  /**
   * Set the location and size of the components
   */
  public void setLocationAndSize() {
    gridBagLayout.columnWidths = new int[] { 128, 128 };
    gridBagLayout.rowHeights = new int[] { 29, 0, 30, 0 };
    gridBagLayout.columnWeights = new double[] { 0.0, 0.0 };
    gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };

    gbc.fill = GridBagConstraints.BOTH;
    gbc.insets = new Insets(0, 0, 5, 5);
    gbc.gridx = 0;
    gbc.gridy = 1;

    gbc_1.fill = GridBagConstraints.BOTH;
    gbc_1.insets = new Insets(0, 0, 5, 5);
    gbc_1.gridx = 0;
    gbc_1.gridy = 2;

    gbc_2.fill = GridBagConstraints.BOTH;
    gbc_2.insets = new Insets(0, 0, 5, 0);
    gbc_2.gridx = 1;
    gbc_2.gridy = 1;

    gbc_3.fill = GridBagConstraints.BOTH;
    gbc_3.insets = new Insets(0, 0, 5, 0);
    gbc_3.gridx = 1;
    gbc_3.gridy = 2;

    gbc_4.gridwidth = 2;
    gbc_4.fill = GridBagConstraints.BOTH;
    gbc_4.insets = new Insets(0, 0, 5, 0);
    gbc_4.gridx = 0;
    gbc_4.gridy = 3;

    gbc_5.fill = GridBagConstraints.BOTH;
    gbc_5.gridx = 0;
    gbc_5.gridy = 4;
    gbc_5.gridwidth = 2;
  }

  /**
   * Add the components to the container
   */
  public void addComponentsToContainer() {
    add(centeredContent, new GridBagConstraints());
    centeredContent.add(formContent);

    GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
    gbc_lblNewLabel.gridwidth = 2;
    gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
    gbc_lblNewLabel.gridx = 0;
    gbc_lblNewLabel.gridy = 0;
    lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
    formContent.add(lblNewLabel, gbc_lblNewLabel);
    formContent.add(userLabel, gbc);
    formContent.add(userTextField, gbc_2);
    formContent.add(passwordLabel, gbc_1);
    formContent.add(passwordField, gbc_3);
    formContent.add(showPassword, gbc_4);
    formContent.add(loginButton, gbc_5);
  }

  /**
   * Add the action event
   */
  public void addActionEvent() {
    loginButton.addActionListener(this);
    showPassword.addActionListener(this);
  }

  /**
   * On action performed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == loginButton) {
      String userText;
      String pwdText;
      userText = userTextField.getText();
      pwdText = String.valueOf(passwordField.getPassword());
      if (employeeController.authenticate(userText, pwdText)) {
        EventHandler.fireEvent("user-logged-in", null);
        showMainMenu();
        resetFields();
      } else {
        JOptionPane.showMessageDialog(this, "Invalid Username or Password");
      }
    } else if (e.getSource() == showPassword) {
      if (showPassword.isSelected()) {
        passwordField.setEchoChar((char) 0);
      } else {
        passwordField.setEchoChar('*');
      }
    }
  }

  /**
   * Show the main menu
   */
  private void showMainMenu() {
    CardLayout cl = (CardLayout) getParent().getLayout();
    cl.show(getParent(), "mainMenuPanel");
  }

  /**
   * Reset the fields
   */
  private void resetFields() {
    userTextField.setText("");
    passwordField.setText("");
    showPassword.setSelected(false);
    passwordField.setEchoChar('*');
  }
}
