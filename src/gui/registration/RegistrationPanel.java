package gui.registration;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.util.List;
import java.util.UUID;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.RegistrationController;
import gui.events.EventHandler;
import model.Registration;

public class RegistrationPanel extends JPanel {
  private JTable table;
  private RegistrationController registrationController;
  private List<Registration> registrations;
  private RegistrationSearch searchField;

  /**
   * Create the panel.
   */
  public RegistrationPanel() {
    registrationController = new RegistrationController();

    setLayout(new BorderLayout(0, 0));

    createToolbar();
    createTable();
  }

  /**
   * Creates the toolbar
   */
  private void createToolbar() {
    JPanel toolbarPanel = new JPanel();
    FlowLayout fl_toolbarPanel = (FlowLayout) toolbarPanel.getLayout();
    fl_toolbarPanel.setAlignment(FlowLayout.LEFT);
    add(toolbarPanel, BorderLayout.NORTH);

    JButton btnBack = new JButton("");
    btnBack.setPreferredSize(new Dimension(40, 40));
    ImageIcon imgBack = new ImageIcon(new ImageIcon(getClass().getResource("/gui/assets/back.png")).getImage()
        .getScaledInstance(25, 25, Image.SCALE_DEFAULT));
    btnBack.setIcon(imgBack);
    btnBack.addActionListener((e) -> onBackClick());
    toolbarPanel.add(btnBack);

    JLabel lblNewLabel = new JLabel("Registrations");
    lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
    lblNewLabel.setBorder(new EmptyBorder(0, 10, 0, 12));
    toolbarPanel.add(lblNewLabel);

    searchField = new RegistrationSearch();
    toolbarPanel.add(searchField);
    EventHandler.addListener("on-registration-search", (value) -> {
      onSearch((String) value);
    });

    JButton btnNewRegistration = new JButton("+");
    btnNewRegistration.setFont(new Font("Tahoma", Font.BOLD, 11));
    btnNewRegistration.setPreferredSize(new Dimension(45, 45));
    toolbarPanel.add(btnNewRegistration);
    btnNewRegistration.addActionListener((e) -> {
      CardLayout cl = (CardLayout) getParent().getLayout();
      cl.show(getParent(), "createRegistrationPanel");
    });

    JButton btnRefresh = new JButton("Refresh");
    btnRefresh.setPreferredSize(new Dimension(80, 40));
    toolbarPanel.add(btnRefresh);
    btnRefresh.addActionListener((e) -> {
      rerenderTable();
    });
  }

  /**
   * Creates the table
   */
  private void createTable() {
    table = new JTable();
    table.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null }, },
        new String[] { "ID number", "Customer identity no", "Start date", "End date", "Edit", "Delete" }) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    });

    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    centerRenderer.setBackground(new Color(230, 230, 230));
    table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
    table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);

    table.addMouseListener(new java.awt.event.MouseAdapter() {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        int row = table.rowAtPoint(evt.getPoint());
        int col = table.columnAtPoint(evt.getPoint());
        String colName = table.getColumnName(col);
        if (row >= 0 && col >= 0 && colName == "Delete") {
          UUID id = (UUID) table.getModel().getValueAt(row, 0);
          int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this registration?",
              "Warning", JOptionPane.YES_NO_OPTION);

          if (dialogResult == JOptionPane.YES_OPTION) {
            try {
              registrationController.deleteRegistration(id);
            } catch (Exception e) {
              JOptionPane.showMessageDialog(null, e.getMessage());
            }
            rerenderTable();
          }
        }

        else if (row >= 0 && col >= 0 && colName == "Edit") {
          UUID id = (UUID) table.getModel().getValueAt(row, 0);
          CardLayout cl = (CardLayout) getParent().getLayout();
          EventHandler.fireEvent("edit-registration-panel-shown", id);
          cl.show(getParent(), "editRegistrationPanel");
        }
      }
    });

    EventHandler.addListener("refresh-registrations", (i) -> {
      rerenderTable();
    });

    rerenderTable();

    add(new JScrollPane(table), BorderLayout.CENTER);
  }

  /**
   * On back click
   */
  private void onBackClick() {
    CardLayout cl = (CardLayout) getParent().getLayout();
    cl.show(getParent(), "mainMenuPanel");
  }

  /**
   * Rerenders the table
   */
  private void rerenderTable() {
    searchField.reset();
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.setRowCount(0);
    try {
      registrations = registrationController.getAllRegistrations();
      for (Registration registration : registrations) {
        model.addRow(new Object[] { registration.getId(), registration.getCustomerIdentityNo(),
            registration.getStartDate(), registration.getEndDate(), "Edit", "Delete" });
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * On search
   * 
   * @param email The email to search for
   */
  private void onSearch(String email) {
    DefaultTableModel model = (DefaultTableModel) table.getModel();
    model.setRowCount(0);
    if (registrations == null)
      return;

    for (Registration registration : registrations) {
      if (registration.getCustomerIdentityNo().contains(email)) {
        model.addRow(new Object[] { registration.getId(), registration.getCustomerIdentityNo(),
            registration.getStartDate(), registration.getEndDate(), "Edit", "Delete" });
      }
    }
  }
}
